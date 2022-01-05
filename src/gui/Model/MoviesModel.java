package gui.Model;

import be.Movies;
import bll.BLLFacade;
import bll.BLLManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MoviesModel {

    private ObservableList<Movies> movies;
    private BLLFacade logiclayer;

    public MoviesModel(){
        movies = FXCollections.observableArrayList();
        logiclayer = new BLLManager();
    }
}
