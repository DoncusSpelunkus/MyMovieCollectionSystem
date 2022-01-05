package gui.Controller;


import be.Categories;
import be.Movies;
import gui.Model.MoviesModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

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

    private final MoviesModel moviesModel;
    private MainController mainController;
    private Movies selectedMovie;
    private Categories category1;
    private Categories category2;
    private Categories category3;
    private boolean isEditing = false;


    public AddMoviesController() {
        moviesModel = new MoviesModel();
    }

    public void setMyController() {
        this.mainController = mainController;
    }

    public void addMovie(ActionEvent event) {

        String name = movieTitle.getText().trim();
        String rating = ratingText.getText().trim();
        float ratingNo = Float.parseFloat(rating);
        if (!isEditing) {
            if (ratingNo >= 0.0 && ratingNo <= 10.0) {
                if (name.length() > 0 && name.length() < 50 && filePathText != null && filePathText.getText().length() != 0) {
                    moviesModel.addMovie(name, ratingNo, filePathText.getText(), Date.from(Instant.now()));
                    moviesModel.addToCategory(category1Combo.getSelectionModel().getSelectedItem());
                    moviesModel.addToCategory(category2Combo.getSelectionModel().getSelectedItem());
                    moviesModel.addToCategory(category3Combo.getSelectionModel().getSelectedItem());
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
            moviesModel.editMovie(selectedMovie, name, ratingNo, filePathText.getText(), Date.from(Instant.now()));
            moviesModel.editMoviesCategory(selectedMovie, category1Combo.getSelectionModel().getSelectedItem());
        }
    }

    public void setEdit(Movies movie){
        if(movie != null){
            selectedMovie = movie;
            isEditing = true;
            movieTitle.setText(selectedMovie.getName());
            ratingText.setText(selectedMovie.getRatingToString());
            filePathText.setText(selectedMovie.getFilelink());
            category3Combo.getSelectionModel().select(selectedMovie.getCategory3());
            category2Combo.getSelectionModel().select(selectedMovie.getCategory2());
            category1Combo.getSelectionModel().select(selectedMovie.getCategory1());
        }
        else {
            errorLabel2.setText("Error: No movie selected");
        }
    }
}
