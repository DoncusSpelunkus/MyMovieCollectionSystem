package gui.Controller;
import be.Movies;
import gui.Model.CategoriesModel;
import gui.Model.MoviesModel;
import be.Categories;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.FormatterClosedException;
import java.util.ResourceBundle;

public class MainController implements Initializable {

        @FXML
        private TableColumn<?, ?> amountColumn;

        @FXML
        private TableColumn<?, ?> avgRatingColumn;

        @FXML
        private TableView<Categories> categoriesView;

        @FXML
        private TableColumn<?, ?> categoryNameColumn;

        @FXML
        private Button deleteCategoryBtn;

        @FXML
        private Button deleteMovieBtn;

        @FXML
        private TableColumn<?, ?> directorColumn;

        @FXML
        private Button editCategoryBtn;

        @FXML
        private Button editMovieBtn;

        @FXML
        private Label errorlabel1;

        @FXML
        private TableColumn<?, ?> movieNameColumn;

        @FXML
        private TableColumn<?, ?> lastViewedColumn;

        @FXML
        private TableColumn<?, ?> ratingColumn;

        @FXML
        private TableView<Movies> moviesView;

        @FXML
        private Button newCategoryBtn;

        @FXML
        private Button newMovieBtn;

        @FXML
        private TextField searchField;



    private ObservableList<Categories> observableListCategories = FXCollections.observableArrayList();
    private ObservableList<Movies> observableListMovies = FXCollections.observableArrayList();
    private MainController mainController;
    private CategoriesModel categoriesModel;
    private MoviesModel moviesModel;

    public void setController(MainController mainController){
            this.mainController = mainController;
    }

    public MainController() {
        moviesModel = new MoviesModel();
        categoriesModel = new CategoriesModel();
        observableListCategories = categoriesModel.getAllCategories();
        observableListMovies = moviesModel.getAllMovies();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateCategoriesView();
        populateMoviesView();
    }

    @FXML
    private void deleteCategoryBtn (ActionEvent actionEvent) {
        categoriesModel.deleteCategory(categoriesView.getSelectionModel().getSelectedItem());

    }

    @FXML
    private void deleteMovieBtn (ActionEvent actionEvent) {
        moviesModel.deleteMovie(moviesView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void newCategoryBtn (ActionEvent actionEvent) throws IOException, SQLServerException {
        setupCategoriesWindow(false);
    }

    @FXML
    private void editCategoryBtn (ActionEvent actionEvent) throws IOException, SQLServerException {
        setupCategoriesWindow(true);
    }
    @FXML
    private void setupCategoriesWindow(boolean edit) throws IOException, SQLServerException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../view/AddCategories.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        AddCategoriesController addCategories = fxmlLoader.getController();
        addCategories.setController(this);
        if (edit){
            fxmlLoader.<AddCategoriesController>getController().setEdit(categoriesView.getSelectionModel().getSelectedItem());
        }
        fxmlLoader.<AddCategoriesController>getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void newMovieBtn (ActionEvent actionEvent) throws IOException, SQLServerException {
        setupMoviesWindow(false);
    }

    @FXML
    private void editMovieBtn (ActionEvent actionEvent) throws SQLServerException, IOException {
        setupMoviesWindow(true);
    }

    @FXML
    private void setupMoviesWindow(boolean edit) throws IOException, SQLServerException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../view/AddMovies.fxml"));
        Parent root = fxmlLoader.load();
        AddMoviesController addMovies = fxmlLoader.getController();
        addMovies.setMyController(this);
        if(edit){
            fxmlLoader.<AddMoviesController>getController().setEdit(moviesView.getSelectionModel().getSelectedItem());
        }
        fxmlLoader.<AddMoviesController>getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void refreshMovieList() throws SQLServerException{
        moviesView.setItems(moviesModel.getAllMovies());
    }

    private void populateCategoriesView(){
        try {
            categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            categoriesView.setItems(observableListCategories);
        }
        catch(NullPointerException e) {
            System.out.println(e);
        }
    }

    private void populateMoviesView(){
        try {
            movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            lastViewedColumn.setCellValueFactory(new PropertyValueFactory<>("lastview"));
            ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

            moviesView.setItems(observableListMovies);
        }
        catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    public void refreshCategory() throws SQLServerException {
        if(categoriesView.getSelectionModel().getSelectedItem() != null){
            int toSet = categoriesView.getSelectionModel().getSelectedIndex();

            categoriesView.setItems(categoriesModel.getAllCategories());

            categoriesView.getSelectionModel().select(toSet);
        }
        else{
            errorlabel1.setText("Could not refresh playlist, please select one");
        }
    }
}
