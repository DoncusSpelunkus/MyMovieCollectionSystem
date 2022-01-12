package gui.Controller;
import be.Movies;
import gui.Model.MoviesModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class StartupController implements Initializable {
    @FXML
    private Label errorLabel3;

    @FXML
    private TableView<Movies> startupTable;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> ratingColumn;

    @FXML
    private TableColumn<?, ?> lastViewColumn;

    @FXML
    private Button noBtn;

    @FXML
    private Button yesBtn;

    private ObservableList<Movies> listOfMovies;
    private MoviesModel moviesModel;
    private TableView.TableViewSelectionModel<Movies> selectionModel;

    public StartupController(){
        moviesModel = new MoviesModel();
        listOfMovies = moviesModel.getAllMovies();
        startupTable = new TableView<Movies>();
        selectionModel = startupTable.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    void no(ActionEvent event) throws IOException {
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
    }

    @FXML
    void yes(ActionEvent event) {
        ObservableList<Movies> listToBeDeleted = selectionModel.getSelectedItems();
        for (int i = 0; i < listToBeDeleted.size(); i++) {
            moviesModel.deleteMovie(listToBeDeleted.get(i));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("PRating"));
        lastViewColumn.setCellValueFactory(new PropertyValueFactory<>("lastview"));


        startupTable.setItems(listOfMovies);
    }
}
