package gui.Controller;


import be.Categories;
import be.Movies;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import gui.Model.CategoriesModel;
import gui.Model.MoviesModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class AddMoviesController implements Initializable {

    Stage stage;

    @FXML
    private Button addMovieBtn;

    @FXML
    private TextField movieTitle;

    @FXML
    private TextField filePathText;

    @FXML
    private Label errorLabel2;

    @FXML
    private TextField ratingText;

    @FXML
    private TextField personalRatingText;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private CheckComboBox<Categories> checkCombo;

    @FXML
    private Label checkComboLabel;

    private MoviesModel moviesModel;
    private CategoriesModel categoriesModel;
    private ObservableList<Categories> categories;
    private MainController mainController;
    private Movies selectedMovie;
    private boolean isEditing = false;
    private float ratingNo;
    private float pRatingNo;

    public AddMoviesController() {
        moviesModel = new MoviesModel();
        categoriesModel = new CategoriesModel();
        categories = categoriesModel.getAllCategories();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkCombo.getItems().addAll(categories); // sets the items inside the checkcombobox
    }

    public void setMyController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML // Method to open the file explorer and making the filepath into a string
    private void chooseFileBTNPress(ActionEvent event) {
        try{
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Movie Files", "*.mp4", "*.mpeg4","*.mov"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        String path = file.getPath();
        File filestring = new File(path);
        filePathText.setText(filestring.getAbsolutePath());
        }
        catch(NullPointerException e) {
            errorLabel2.setText("Hey you got a: Nullpointerexception file is null");
        }
    }

    @FXML // Closes the addmovies window
    private void closeAMWindow(ActionEvent event){
        stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addMovie(ActionEvent event) throws SQLServerException { // The method for when you push the add button
        String name = movieTitle.getText().trim();
        String rating = ratingText.getText().trim();
        String prating = personalRatingText.getText().trim(); // Gets the text from the fields to the appropriate variable
        convertTextToFloat(rating);
        convertPTextToFloat(prating);
        if (!isEditing) { // Checks if the window is in edit mode
            if (ratingNo >= 0.0 && ratingNo <= 10.0) { // The 3 if statements here check that the inputted values are valid
                if (pRatingNo >= 0.0 && pRatingNo <= 10.0) {
                    if (name.length() > 0 && name.length() < 51 && filePathText != null && filePathText.getText().length() != 0) {
                    moviesModel.addMovie(name, ratingNo, pRatingNo, filePathText.getText(), Date.valueOf(LocalDate.now()));
                    addInitCategories(); // runs the method to match up initial categories
                    mainController.setErrorLabel1("Successfully added movie, congrats"); // Displays the message in the main window and closes the addmovies window
                    stage = (Stage) anchorPane.getScene().getWindow();
                    stage.close();
                    } else {
                        errorLabel2.setText("Name and file path has to not be empty (Name is max 50 chars), try again");
                    }
                } else {
                    errorLabel2.setText("Invalid input: Personal rating must have a valid number between 0.0 and 10.0");
                }
            } else {
                errorLabel2.setText("Invalid input: Rating must have a valid number between 0.0 and 10.0");
            }
        } else { // This runs if the window is in edit mode
            moviesModel.editMovie(selectedMovie, name, ratingNo, pRatingNo, filePathText.getText(), Date.valueOf(LocalDate.now()));
            stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
        }
        mainController.refreshMovieList();
    }

    private void convertTextToFloat(String rating){ // Converts rating field string into a float
        try{
            float ratingTemp = Float.parseFloat(rating);
            ratingNo = ratingTemp;
        }
        catch (NumberFormatException e){
            errorLabel2.setText("Invalid input: Rating must have a valid number between 0.0 and 10.0");
        }
    }

    private void convertPTextToFloat(String prating){ // Converts prating field string into a float
        try{
            float pRatingTemp = Float.parseFloat(prating);
            pRatingNo = pRatingTemp;
        }
        catch (NumberFormatException e){
            errorLabel2.setText("Invalid input: Personal rating must have a valid number between 0.0 and 10.0");
        }
    }

    public void setEdit(Movies movie){ // Sets the window to edit mode and pulls the needed info about the selected movie on the view
        if(movie != null){
            selectedMovie = movie;
            isEditing = true;
            checkCombo.setVisible(false);
            checkComboLabel.setVisible(false);
            movieTitle.setText(selectedMovie.getName());
            ratingText.setText(selectedMovie.getRatingToString());
            personalRatingText.setText(selectedMovie.getPRatingToString());
            filePathText.setText(selectedMovie.getFilelink());
            errorLabel2.setText("Edit mode: Enabled");
            addMovieBtn.setText("Edit");
        }
        else {
            errorLabel2.setText("You must select a movie to be able to edit");
        }
    }


    private void addInitCategories() throws SQLServerException { // The method finds the newly created movie and matches it with catgories selected in the checkcombobox
        List<Categories> selectedCategories = checkCombo.getCheckModel().getCheckedItems();
        List<Movies> listToGetLastestMovie = moviesModel.getAllMovies();
        Comparator<Movies> sortedList = Comparator.comparing(Movies::getMovieID);
        listToGetLastestMovie.sort(sortedList);
        Movies currentMovie = listToGetLastestMovie.get(listToGetLastestMovie.size()-1);
        for (int i = 0; i < selectedCategories.size(); i++) {
            moviesModel.addToCategory(selectedCategories.get(i), currentMovie);
        }
        mainController.refreshCategory();
    }
}