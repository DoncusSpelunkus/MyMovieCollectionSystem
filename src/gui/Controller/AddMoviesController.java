package gui.Controller;


import be.Categories;
import be.Movies;
import gui.Model.MoviesModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.sql.Date;

public class AddMoviesController {

    Stage stage;

    @FXML
    private ComboBox<Categories> category1Combo;

    @FXML
    private ComboBox<Categories> category2Combo;

    @FXML
    private ComboBox<Categories> category3Combo;

    @FXML
    private TextField movieTitle;

    @FXML
    private TextField filePathText;

    @FXML
    private Label errorLabel2;

    @FXML
    private TextField ratingText;

    private MoviesModel moviesModel;
    private MainController mainController;
    private Movies selectedMovie;
    private Categories category1;
    private Categories category2;
    private Categories category3;
    private boolean isEditing = false;
    private ObservableList<Categories> categories;
    private MediaPlayer mediaPlayer;



    @FXML
    private void chooseFileBTNPress(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop"));
        fileChooser.setTitle("Select movie");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Movie Files", "*.mp4", "*.mpeg4"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            filePathText.setText(selectedFile.getAbsolutePath());
            mediaPlayer = new MediaPlayer(new Media(new File(selectedFile.getAbsolutePath()).toURI().toString()));
        }
    }

    public AddMoviesController() {
        moviesModel = new MoviesModel();
        category1Combo.setItems(categories);
        category1Combo.setVisibleRowCount(categories.size());
        category2Combo.setVisible(false);
        category2Combo.setItems(categories);
        category2Combo.setVisibleRowCount(categories.size());
        category3Combo.setVisible(false);
        category3Combo.setItems(categories);
        category3Combo.setVisibleRowCount(categories.size());
    }

    public void setMyController(MainController mainController) {
        this.mainController = mainController;
    }

    public void addMovie(ActionEvent event) {

        String name = movieTitle.getText().trim();
        String rating = ratingText.getText().trim();
        float ratingNo = Float.parseFloat(rating);
        if (!isEditing) {
            if (ratingNo >= 0.0 && ratingNo <= 10.0) {
                if (name.length() > 0 && name.length() < 50 && filePathText != null && filePathText.getText().length() != 0) {
                    moviesModel.addMovie(name, ratingNo, filePathText.getText(), (Date) Date.from(Instant.now()));
                    if(!isEditing) {
                        addCategoriesToMovie();
                    }
                    movieTitle.clear();
                    filePathText.clear();
                    errorLabel2.setText("Succesfully added song, congrats");
                } else {
                    errorLabel2.setText("Something went wrong, try again");
                }
            } else {
                errorLabel2.setText("Invalid input: Rating must have a valid number between 0.0 and 10.0");
            }
        } else {
            moviesModel.editMovie(selectedMovie, name, ratingNo, filePathText.getText(), (Date) Date.from(Instant.now()));
        }
    }

    public void onSelectionCombo1(ActionEvent event){
        if(category1Combo.getSelectionModel().getSelectedItem() != null){
            category2Combo.setVisible(true);
            category2Combo.setEditable(true);
            category1 = category1Combo.getSelectionModel().getSelectedItem();
            categories.remove(category1);

        }
        else if (category1Combo.getSelectionModel().getSelectedItem() == null){
            category2Combo.setVisible(false);
            category2Combo.setEditable(false);
            categories.add(category1);
        }
    }

    public void onSelectionCombo2(ActionEvent event){
        if(category2Combo.getSelectionModel().getSelectedItem() != null){
            category1Combo.setEditable(false);
            category3Combo.setVisible(true);
            category3Combo.setEditable(true);
            category2 = category2Combo.getSelectionModel().getSelectedItem();
            categories.remove(category2);
        }
        else if (category2Combo.getSelectionModel().getSelectedItem() == null) {
            category3Combo.setVisible(false);
            category3Combo.setEditable(false);
            categories.add(category2);
        }
    }

    public void onSelectionCombo3(ActionEvent event){
        if(category3Combo.getSelectionModel().getSelectedItem() != null){
            category2Combo.setEditable(false);
            category3 = category3Combo.getSelectionModel().getSelectedItem();
            categories.remove(category3);
        }
        else if (category2Combo.getSelectionModel().getSelectedItem() == null) {
            categories.add(category3);
        }
    }

    public void setEdit(Movies movie){
        if(movie != null){
            selectedMovie = movie;
            isEditing = true;
            movieTitle.setText(selectedMovie.getName());
            ratingText.setText(selectedMovie.getRatingToString());
            filePathText.setText(selectedMovie.getFilelink());
            category1Combo.setEditable(false);
            category2Combo.setEditable(false);
            category3Combo.setEditable(false);
        }
        else {
            errorLabel2.setText("Error: No movie selected");
        }
    }

    private void addCategoriesToMovie(){
        if(category1Combo != null) {
            moviesModel.addToCategory(category1Combo.getSelectionModel().getSelectedItem(), moviesModel.getCurrentMovie());
        }
        if(category2Combo != null) {
            moviesModel.addToCategory(category2Combo.getSelectionModel().getSelectedItem(), moviesModel.getCurrentMovie());
        }
        if(category3Combo != null) {
            moviesModel.addToCategory(category3Combo.getSelectionModel().getSelectedItem(), moviesModel.getCurrentMovie());
        }
    }
}
