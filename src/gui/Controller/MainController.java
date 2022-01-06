package gui.Controller;
import gui.Model.CategoriesModel;
import gui.Model.MoviesModel;
import be.Categories;
import com.microsoft.sqlserver.jdbc.SQLServerException;
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
        private Label label;
        @FXML
        private TableColumn<?, ?> movieNameColumn;
        @FXML
        private TableView<?> moviesView;
        @FXML
        private Button newCategoryBtn;
        @FXML
        private Button newMovieBtn;
        @FXML
        private TableColumn<?, ?> ratingColumn;
        @FXML
        private TextField searchField;
        @FXML
        void deleteCategory(ActionEvent event) {
        }
        @FXML
        void deleteMovies(ActionEvent event) {
        }
        @FXML
        void openAddCategories(ActionEvent event) {
        }
        @FXML
        void openAddMovies(ActionEvent event) {
        }

    private MainController mainController;

    public void setController(MainController mainController){
            this.mainController = mainController;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void deleteCategoryBtn (ActionEvent actionEvent) {
        CategoriesModel.deleteCategoryBtn(categoriesView.getSelectionModel().getSelectedItem());
        categoriesView.getItems().remove(categoriesView.getSelectionModel().getSelectedIndex());

    }

    @FXML
    private void deleteMovieBtn (ActionEvent actionEvent) {
        MoviesModel.deleteMovieBtn(moviesView.getSelectionModel().getSelectedItem());
        moviesView.getItems().remove(moviesView.getSelectionModel().getSelectedIndex());

    }

    @FXML
    private void newCategoryBtn (ActionEvent actionEvent) throws IOException, SQLServerException {
        setupCategoriesWindow(false);
    }
    /*
     addCategories.setEdit(this.categoriesView.getSelectionModel().getSelectedItem());*/
    @FXML
    private void editCategoryBtn (ActionEvent actionEvent) throws IOException, SQLServerException {
        setupCategoriesWindow(true);
    }
    private void setupCategoriesWindow(boolean edit) throws IOException, SQLServerException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../view/AddCategories.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        AddCategoriesController addCategories = fxmlLoader.getController();
        addCategories.setController(this);
        if (edit){
            fxmlLoader.<addCategories>getController().setEdit(categoriesView.getSelectionModel().getSelectedItem());
        }
        fxmlLoader.<addCategories>getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    private void newMovieBtn (ActionEvent actionEvent) throws IOException {


    }

    @FXML
    private void editMovieBtn (ActionEvent actionEvent) {

    }





}
