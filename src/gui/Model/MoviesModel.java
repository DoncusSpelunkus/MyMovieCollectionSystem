package gui.Model;

import be.Categories;
import be.Movies;
import bll.BLLFacade;
import bll.BLLManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;

public class MoviesModel {

    private ObservableList<Movies> movies;
    private BLLFacade logiclayer;

    public MoviesModel(){
        movies = FXCollections.observableArrayList();
        logiclayer = new BLLManager();
    }

    public void addMovie(String name, float rating, float prating, String filelink, Date lastview){
        logiclayer.addMovie(name, rating, prating, filelink, lastview);
    }

    public ObservableList<Movies> getAllMovies() {
        movies = FXCollections.observableArrayList();
        movies.addAll(logiclayer.getAllMovies());
        return movies;
    }

    public void deleteMovie(Movies selectedMovie){
        logiclayer.deleteMovie(selectedMovie);
    }

    public void editMovie(Movies selectedMovie, String name, float rating, float prating, String filelink, Date lastview){
        logiclayer.editMovie(selectedMovie, name, rating, prating, filelink, lastview);
    }

    public void addToCategory(Categories selectedCategory, Movies selectedMovie){
        logiclayer.addToCategory(selectedCategory, selectedMovie);
    }

    public void deleteFromCategories(Categories selectedCategory, Movies selectedMovie){
        logiclayer.deleteFromCategories(selectedCategory,selectedMovie);
    }
}

