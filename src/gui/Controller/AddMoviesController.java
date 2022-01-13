package gui.Controller;


import be.Movies;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import gui.Model.MoviesModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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

    private MoviesModel moviesModel;
    private MainController mainController;
    private Movies selectedMovie;
    private boolean isEditing = false;
    private MediaPlayer mediaPlayer;
    private float ratingNo;
    private float pRatingNo;

    public AddMoviesController() {
        moviesModel = new MoviesModel();
    }

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
                    movieTitle.clear();
                    filePathText.clear();
                    errorLabel2.setText("Succesfully added song, congrats");
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
}