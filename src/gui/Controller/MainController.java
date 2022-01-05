package gui.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController {


    public class PleaseProvideControllerClassName {

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

    }

}
