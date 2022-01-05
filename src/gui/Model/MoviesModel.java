package gui.Model;

import be.Movies;
import bll.BLLFacade;
import bll.BLLManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class MoviesModel {

    private ObservableList<Movies> movies;
    private BLLFacade logiclayer;
    private Movies currentMovie;

    public MoviesModel(){
        movies = FXCollections.observableArrayList();
        logiclayer = new BLLManager();
    }

    public void addMovie(String name, float rating, String filelink, Date lastview){
        logiclayer.addMovie(name, rating, filelink, lastview);
    }

    public int getCurrentMovieID(){
        return logiclayer.getCurrentMovieID();
    }

    public ObservableList<Movies> getMovies() {
        movies = FXCollections.observableArrayList();
        movies.addAll(logiclayer.getAllMovies);
        return movies;
    }

    public void deleteMovie(Movies selectedMovie){
        logiclayer.deleteMovie(selectedMovie);
    }

    public void editMovie(Movies selectedMovie, String name, float rating, String filelink, Date lastview){
        logiclayer.editMovie(selectedMovie, name, rating, filelink, lastview);
    }
}

