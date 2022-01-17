package gui.Controller;
import be.Movies;
import gui.Model.CategoriesModel;
import gui.Model.MoviesModel;
import be.Categories;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
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
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

        @FXML
        private TableView<Movies> moviesInCategory;

        @FXML
        private TableColumn<?, ?> moviesInCategoryName;

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
        private Label errorLabel1;

        @FXML
        private TableColumn<?, ?> movieNameColumn;

        @FXML
        private TableColumn<?, ?> lastViewedColumn;

        @FXML
        private TableColumn<?, ?> ratingColumn;

        @FXML
        private TableColumn<?, ?> ratingColumn1;

        @FXML
        private TableView<Movies> moviesView;

        @FXML
        private Button newCategoryBtn;

        @FXML
        private Button newMovieBtn;

        @FXML
        private TextField searchField;



    private ObservableList<Categories> observableListCategories;
    private ObservableList<Movies> observableListMovies;
    private StartupController startupController;
    private CategoriesModel categoriesModel;
    private MoviesModel moviesModel;
    private TableView.TableViewSelectionModel<Categories> selectionModel;
    private int currentCategory;

    public void setController(StartupController StartupController){
            this.startupController = StartupController;
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
        populateMoviesInCategoryView();
        selectionModel = categoriesView.getSelectionModel();
    }

    @FXML
    private void addToCategoryPress (ActionEvent actionEvent) {
        try{
            if (moviesView.getSelectionModel().getSelectedIndex() != -1) {
                moviesModel.addToCategory(categoriesView.getSelectionModel().getSelectedItem(), moviesView.getSelectionModel().getSelectedItem());
                refreshCategory();
                fillCurrentPlaylist();
            }}
        catch (NullPointerException | SQLServerException ex){
            errorLabel1.setText("error: No movie or Category selected, please select one of each");
        }
    }

    @FXML
    private void removeCategoryBtn (ActionEvent actionEvent) {
        try {
            if (moviesInCategory.getSelectionModel().getSelectedIndex() != -1){
                moviesModel.deleteFromCategories(categoriesView.getSelectionModel().getSelectedItem(), moviesInCategory.getSelectionModel().getSelectedItem());
                refreshCategory();
                fillCurrentPlaylist();
            }
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void deleteCategoryBtn (ActionEvent actionEvent) throws SQLServerException {
        categoriesModel.deleteCategory(categoriesView.getSelectionModel().getSelectedItem());
        refreshCategory();
    }

    @FXML
    private void deleteMovieBtn (ActionEvent actionEvent) throws SQLServerException {
        moviesModel.deleteMovie(moviesView.getSelectionModel().getSelectedItem());
        refreshMovieList();
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
    public void categorySelect(MouseEvent mouseEvent){
        currentCategory = selectionModel.getSelectedIndex();
        fillCurrentPlaylist();
    }

    @FXML
    public void mouseClicked(MouseEvent e){
        if (e.getClickCount() == 2 && !e.isConsumed()) {
            e.consume();
        }

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

    private void populateCategoriesView(){
        try {
            categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            categoriesView.setItems(observableListCategories);

            //initial filtered list
            FilteredList<Categories> searchFilter = new FilteredList<>(observableListCategories, b -> true);
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchFilter.setPredicate(categories -> {

                    // if search value is empty then it displays the songs as it is.
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchWord = newValue.toLowerCase();
                    if (categories.getName().toLowerCase().indexOf(searchWord) > -1) {
                        return true; // data will change if song found
                    } else
                        return false;
                });
            });
            SortedList<Categories> sortedData = new SortedList<>(searchFilter);
            // binds the sorted result set with the table view;
            sortedData.comparatorProperty().bind(categoriesView.comparatorProperty());
            categoriesView.setItems(sortedData);
        }
        catch(NullPointerException e) {
            errorLabel1.setText("Hey you got a: Nullpointerexception...congrats");
        }
    }

    private void populateMoviesInCategoryView(){
        try {
            moviesInCategoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
        }
        catch (NullPointerException e){
            errorLabel1.setText("Hey you got a: Nullpointerexception...congrats");
        }
    }

    private void populateMoviesView(){
        try {
            movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            lastViewedColumn.setCellValueFactory(new PropertyValueFactory<>("lastview"));
            ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
            ratingColumn1.setCellValueFactory(new PropertyValueFactory<>("PRating"));

            moviesView.setItems(observableListMovies);

            //initial filtered list
            FilteredList<Movies> searchFilter = new FilteredList<>(observableListMovies, b -> true);
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchFilter.setPredicate(movie -> {

                // if search value is empty then it displays the songs as it is.
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchWord = newValue.toLowerCase();
                if (movie.getName().toLowerCase().indexOf(searchWord) > -1) {
                    return true; // data will change if song found
                } else
                    return false;
                });
            });
            SortedList<Movies> sortedData = new SortedList<>(searchFilter);
            // binds the sorted result set with the table view;
            sortedData.comparatorProperty().bind(moviesView.comparatorProperty());
            moviesView.setItems(sortedData);
        }
        catch (NullPointerException e) {
            errorLabel1.setText("Hey you got a: Nullpointerexception...congrats");
        }
    }

    public void refreshMovieList() throws SQLServerException{
        moviesView.setItems(moviesModel.getAllMovies());
    }

    public void refreshCategory() throws SQLServerException {
        categoriesView.setItems(categoriesModel.getAllCategories());
    }

    private void fillCurrentPlaylist(){
        categoriesView.getSelectionModel().select(currentCategory);
        try{ List<Movies> moviesInList = categoriesView.getSelectionModel().getSelectedItem().getMoviesList();
            if(moviesInList.size() != 0) {
                for (int i = moviesInList.size() - 1; i >= 0; i--) { // for loop for getting each element of the playlist into the tableview and sets the ID for each one
                    moviesInList.get(i).setIDinsideList(moviesInList.size() - i);
                    moviesInCategory.setItems(FXCollections.observableArrayList(moviesInList));
                }
            }
            else {
                moviesInCategory.getItems().clear();
            }
        }
        catch(NullPointerException ex){
            errorLabel1.setText("Hey you got a: Nullpointerexception...congrats");
        }
    }
}
