package gui.Controller;
import gui.Model.CategoriesModel;
import gui.Model.MoviesModel;
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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

        @FXML
        private TableColumn<?, ?> amountColumn;
        @FXML
        private TableColumn<?, ?> avgRatingColumn;
        @FXML
        private TableView<?> categoriesView;
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
    private void editCategoryBtn (ActionEvent actionEvent) {

    }

    @FXML
    private void editMovieBtn (ActionEvent actionEvent) {

    }

    @FXML
    private void newCategoryBtn (ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("gui/View/AddCategories.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("New Category");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void newMovieBtn (ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("gui/View/AddMovies.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("New Movie");
        stage.setScene(new Scene(root));
        stage.show();

    }

}
