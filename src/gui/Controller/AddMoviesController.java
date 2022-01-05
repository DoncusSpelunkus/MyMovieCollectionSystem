package gui.Controller;


import be.Movies;
import gui.Model.MoviesModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.awt.*;

public class AddMoviesController {

    Stage stage;

    @FXML
    private TextField movieTitle;

    @FXML
    private TextField filePathText;

    @FXML
    private TextField errorLabel2;

    @FXML
    private TextField ratingText;

    private final MoviesModel moviesModel;
    private MainController mainController;
    private Movies movie;


    public AddMoviesController(){
        moviesModel = new MoviesModel();
    }

    public void setMyController(){
        this.mainController = mainController;
    }

    public void addMovie(ActionEvent event){

        String name = movieTitle.getText().trim();
        if(!isEditing){
            if (name.length() > 0 && name.length() < 50 && filePathText != null && filePathText.getText().length() != 0){
                MoviesModel.addMovie(name, rating, filePathText.getText());

                MoviesModel.addToCategory()
                movieTitle.clear();
                filePathText.clear();
                moviesModel.getAllSongs().setAll();

                mainController.refreshSongList();
                errorLabel2.setText("Succesfully added song, congrats");


            }
            else {
                errorLabel2.setText("Something went wrong, try again");
            }
        }
        else{
            moviesModel.editSongs(songToBeEdited, length, name, artistString.getText(), categoryString.getText(), filePathString.getText());
        }

    }


}
