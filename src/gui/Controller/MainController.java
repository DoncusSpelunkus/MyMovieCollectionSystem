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
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

        @FXML
        private TableView<Movies> moviesInCategory;

        @FXML
        private TableColumn<?, ?> moviesInCategoryName;

        @FXML
        private TableView<Categories> categoriesView;

        @FXML
        private TableColumn<?, ?> categoryNameColumn;

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
        private TextField searchField;



    private ObservableList<Categories> observableListCategories;
    private ObservableList<Movies> observableListMovies;
    private StartupController startupController;
    private CategoriesModel categoriesModel;
    private MoviesModel moviesModel;
    private TableView.TableViewSelectionModel<Categories> selectionModel;
    private int currentCategory;
    private Movies currentlySelectedMovie;

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
    private void addToCategoryPress (ActionEvent actionEvent) { // add movie to the category
        try{
            if (moviesView.getSelectionModel().getSelectedIndex() != -1) {
                moviesModel.addToCategory(categoriesView.getSelectionModel().getSelectedItem(), moviesView.getSelectionModel().getSelectedItem());
                refreshCategory();
                fillCurrentCategory();
            }
        else{
                errorLabel1.setText("Error: No movie or Category selected, please select one of each");
            }
        } catch (NullPointerException | SQLServerException ex){
            errorLabel1.setText("Error: Nullpointerexception or SQLServerException detected");
        }
    }

    @FXML
    private void removeCategoryBtn (ActionEvent actionEvent) { // Removes the movie from the category
        try {
            if (moviesInCategory.getSelectionModel().getSelectedIndex() != -1){
                moviesModel.deleteFromCategories(categoriesView.getSelectionModel().getSelectedItem(), moviesInCategory.getSelectionModel().getSelectedItem());
                refreshCategory();
                fillCurrentCategory();
            }
            else{
                errorLabel1.setText("Error: No movie or category selected");
            }
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void deleteCategoryBtn (ActionEvent actionEvent) throws SQLServerException { // Deletes the selected category
        if(categoriesView.getSelectionModel().getSelectedItem() != null){
        categoriesModel.deleteCategory(categoriesView.getSelectionModel().getSelectedItem());
        refreshCategory();
        }
        else{
            errorLabel1.setText("Error: No category selected");
        }
    }

    @FXML
    private void deleteMovieBtn (ActionEvent actionEvent) throws SQLServerException { // Deletes the selected movie
        if(moviesView.getSelectionModel().getSelectedItem() != null){
        moviesModel.deleteMovie(moviesView.getSelectionModel().getSelectedItem());
        refreshMovieList();
        }
        else{
            errorLabel1.setText("Error: No movie selected");
        }
    }

    @FXML
    private void newCategoryBtn (ActionEvent actionEvent) throws IOException, SQLServerException { // opens the new category in non-edit mode
        setupCategoriesWindow(false);
    }

    @FXML
    private void editCategoryBtn (ActionEvent actionEvent) throws IOException, SQLServerException { // opens the new category in edit mode
        if(categoriesView.getSelectionModel().getSelectedItem() != null) {
            setupCategoriesWindow(true);
        }
        else{
            errorLabel1.setText("Error: No category selected");
        }
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
    public void categorySelect(MouseEvent mouseEvent){ // gets the currently selected category
        currentCategory = selectionModel.getSelectedIndex();
        fillCurrentCategory();
    }

    @FXML
    public void movieListSelect(MouseEvent mouseEvent){ // sets the currentlySelectedMovie from the movielist
        currentlySelectedMovie = moviesView.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void moviesInCategorySelect(MouseEvent mouseEvent){ // set the currentlySelectedMovie from the movies in category list
        currentlySelectedMovie = moviesInCategory.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void playMovieBtn (ActionEvent actionEvent) throws IOException { // Gets the currently selected item to run the play method
        try {
        if(currentlySelectedMovie != null){
        playMovies();
        }
        else{
                errorLabel1.setText("Error: No movie selected");
        }
        }
        catch(IllegalArgumentException a) {
            errorLabel1.setText("Error: File is not on this computer");
        }
    }
    public void playMovies() throws IOException { // Plays the movie with the systems mediaplayer
        File file = new File(currentlySelectedMovie.getFilelink());
        Desktop.getDesktop().open(file);
        moviesModel.editMovie(currentlySelectedMovie, currentlySelectedMovie.getName(), currentlySelectedMovie.getRating(), currentlySelectedMovie.getPRating(), currentlySelectedMovie.getFilelink(), Date.valueOf(LocalDate.now()));
    }

    @FXML // opens the new movie menu in non-edit mode
    private void newMovieBtn (ActionEvent actionEvent) throws IOException, SQLServerException {
        setupMoviesWindow(false);
    }

    @FXML // opens the new movie menu in edit mode
    private void editMovieBtn (ActionEvent actionEvent) throws SQLServerException, IOException {
        if (currentlySelectedMovie != null) {
            setupMoviesWindow(true);
        }
        else {
            errorLabel1.setText("Error: No movie selected");
        }
    }

    @FXML
    private void setupMoviesWindow(boolean edit) throws IOException, SQLServerException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../view/AddMovies.fxml"));
        Parent root = fxmlLoader.load();
        AddMoviesController addMovies = fxmlLoader.getController();
        addMovies.setMyController(this);
        if(edit){
            fxmlLoader.<AddMoviesController>getController().setEdit(currentlySelectedMovie);
        }
        fxmlLoader.<AddMoviesController>getController();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private void populateCategoriesView(){ // populates the categories view and adds a searchfilter
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
            errorLabel1.setText("Hey you got a: Nullpointerexception at population of categories view");
        }
    }

    private void populateMoviesInCategoryView(){ // populates the movies in category view
        try {
            moviesInCategoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
        }
        catch (NullPointerException e){
            errorLabel1.setText("Hey you got a: Nullpointerexception at population of movies in categories view");
        }
    }

    private void populateMoviesView(){ // populates the movies view and adds a searchfilter
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
                } else if (movie.getRatingToString().toLowerCase().indexOf(searchWord) > -1) {
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
            errorLabel1.setText("Hey you got a: Nullpointerexception at population of movies view");
        }
    }

    public void refreshMovieList() throws SQLServerException { // Refreshes the movie view
        try{ moviesView.setItems(moviesModel.getAllMovies());
    } catch (Exception e) {
            errorLabel1.setText("Error: Could not refresh Movie list");
        }
    }

        public void refreshCategory() throws SQLServerException { // refreshes the category view
        try{ categoriesView.setItems(categoriesModel.getAllCategories());
    } catch (Exception e) {
            errorLabel1.setText("Error: Could not refresh Category list");
        }
    }

    public void setErrorLabel1(String textToDisplay){ // Sets the text in the error label
        errorLabel1.setText(textToDisplay);
    }

    private void fillCurrentCategory(){ // fills the current category view with a list from the category selected
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
            errorLabel1.setText("Hey you got a: Nullpointerexception filling the current movie in categories list");
        }
    }
}
