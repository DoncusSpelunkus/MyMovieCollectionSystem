package gui.Controller;


import be.Categories;
import be.Movies;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import gui.Model.CategoriesModel;
import gui.Model.MoviesModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.sql.Date;
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



    private MoviesModel moviesModel;
    private CategoriesModel categoriesModel;
    private ObservableList<Categories> categories;
    private MainController mainController;
    private Movies selectedMovie;
    private boolean isEditing = false;
    private MediaPlayer mediaPlayer;
    private float ratingNo;
    private float pRatingNo;

    public AddMoviesController() {
        moviesModel = new MoviesModel();
        categoriesModel = new CategoriesModel();
        categories = categoriesModel.getAllCategories();
    }

    @FXML
    private void chooseFileBTNPress(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        String path = file.getPath();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Movie Files", "*.mp4", "*.mpeg4"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File filestring = new File(path);
        filePathText.setText(filestring.getAbsolutePath());
    }

    @FXML
    public void closeAMWindow(ActionEvent event){
        stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    public void setMyController(MainController mainController) {
        this.mainController = mainController;
    }

    public void addMovie(ActionEvent event) throws SQLServerException {
        String name = movieTitle.getText().trim();
        String rating = ratingText.getText().trim();
        String prating = personalRatingText.getText().trim();
        convertTextToFloat(rating);
        convertPTextToFloat(prating);
        if (!isEditing) {
            if (ratingNo >= 0.0 && ratingNo <= 10.0) {
                if (pRatingNo >= 0.0 && pRatingNo <= 10.0) {
                    if (name.length() > 0 && name.length() < 50 && filePathText != null && filePathText.getText().length() != 0) {
                    moviesModel.addMovie(name, ratingNo, pRatingNo, filePathText.getText(), Date.valueOf(LocalDate.now()));
                    addInitCategories();
                    movieTitle.clear();
                    filePathText.clear();
                    mainController.setErrorLabel1("Succesfully added song, congrats");
                    stage = (Stage) anchorPane.getScene().getWindow();
                    stage.close();
                    } else {
                        errorLabel2.setText("Something went wrong, try again");
                    }
                } else {
                    errorLabel2.setText("Invalid input: Personal rating must have a valid number between 0.0 and 10.0");
                }
            } else {
                errorLabel2.setText("Invalid input: Rating must have a valid number between 0.0 and 10.0");
            }
        } else {
            moviesModel.editMovie(selectedMovie, name, ratingNo, pRatingNo, filePathText.getText(), (Date) Date.from(Instant.now()));
            stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
        }
        mainController.refreshMovieList();
    }

    private void convertTextToFloat(String rating){
        try{
            float ratingTemp = Float.parseFloat(rating);
            ratingNo = ratingTemp;
        }
        catch (NumberFormatException e){
            errorLabel2.setText("Invalid input: Rating must have a valid number between 0.0 and 10.0");
        }
    }

    private void convertPTextToFloat(String prating){
        try{
            float pRatingTemp = Float.parseFloat(prating);
            pRatingNo = pRatingTemp;
        }
        catch (NumberFormatException e){
            errorLabel2.setText("Invalid input: Personal rating must have a valid number between 0.0 and 10.0");
        }
    }

    public void setEdit(Movies movie){
        if(movie != null){
            selectedMovie = movie;
            isEditing = true;
            movieTitle.setText(selectedMovie.getName());
            ratingText.setText(selectedMovie.getRatingToString());
            personalRatingText.setText(selectedMovie.getPRatingToString());
            filePathText.setText(selectedMovie.getFilelink());
            addMovieBtn.setText("Edit");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkCombo.getItems().addAll(categories);
    }

    private void addInitCategories(){
        List<Categories> selectedCategories = checkCombo.getCheckModel().getCheckedItems();
        for (int i = 0; i < selectedCategories.size(); i++) {
            moviesModel.addToCategory(selectedCategories.get(i), moviesModel.getCurrentMovie());
        }
    }
}