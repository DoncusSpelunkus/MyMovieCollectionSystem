package gui.Controller;
import be.Movies;
import gui.Model.MoviesModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class StartupController implements Initializable {

    @FXML
    private Label errorLabel3;

    @FXML
    private CheckListView<Movies> checkListView;


    private ObservableList<Movies> displayList;
    private MoviesModel moviesModel;

    public StartupController(){
        moviesModel = new MoviesModel();
    }

    @FXML
    private void goToMainBTNPRess(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../view/Main.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        MainController main = fxmlLoader.getController();
        main.setController(this);
        fxmlLoader.<AddCategoriesController>getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Node n = (Node) event.getSource();
        Stage stage1 = (Stage) n.getScene().getWindow();
        stage1.close();
    }

    @FXML
    private void confirmBTNPress(ActionEvent event) throws IOException {
        ObservableList<Movies> deletionList = checkListView.getCheckModel().getCheckedItems();
        for (int i = 0; i < deletionList.size(); i++) {
            moviesModel.deleteMovie(deletionList.get(i));
        }
        errorLabel3.setText("Nice, we deleted those movies for ya");
        makeList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            makeList();
        } catch (IOException e) {
            errorLabel3.setText("You got an error at: Initialization");
        }
    }

    private void makeList() throws IOException {
        ObservableList<Movies> listOfMovies = moviesModel.getAllMovies();
        displayList = FXCollections.observableArrayList();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < listOfMovies.size(); i++) {
            LocalDate dateOfMovie = listOfMovies.get(i).getLastview().toLocalDate();
            if (dateOfMovie.isBefore(today.minusYears(20))){
                displayList.add(listOfMovies.get(i));
            }
        }
        checkListView.setItems(displayList);
    }
}
