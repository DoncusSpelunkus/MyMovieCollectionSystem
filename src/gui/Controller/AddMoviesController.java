package gui.Controller;


import be.Categories;
import be.Movies;
import gui.Model.MoviesModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionModel;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

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
                    moviesModel.addMovie(name, ratingNo, filePathText.getText(), (Date) Date.from(Instant.now()));
                    addCategoriesToMovie();
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

    public void setCategory1Combo(ActionEvent event){
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

    public void setCategory2Combo(ActionEvent event){
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

    public void setCategory3Combo(ActionEvent event){
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
            category3Combo.getSelectionModel().select(selectedMovie.getCategory3());
            category2Combo.getSelectionModel().select(selectedMovie.getCategory2());
            category1Combo.getSelectionModel().select(selectedMovie.getCategory1());
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
