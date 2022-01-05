package bll;

import be.Categories;
import be.Movies;
import dal.DALFacade;
import dal.DALManager;

import java.sql.Date;
import java.util.List;

public class BLLManager implements BLLFacade{

    private DALFacade dalFacade;


    public BLLManager(){
        dalFacade = new DALManager();
    }

    @Override
    public List<Movies> getAllMovies(){
        return dalFacade.getAllMovies();
    }

    @Override
    public void deleteMovie(Movies selectedMovie){
        dalFacade.deleteMovie(selectedMovie);
    }

    @Override
    public void editMovie(Movies selectedMovie, String name, float rating, String filelink, Date lastview){
        dalFacade.editMovies(selectedMovie, name, rating, filelink, lastview);
    }

    @Override
    public void addMovie(String name, float rating, String filelink, Date lastview) {
        dalFacade.addMovies(name, rating, filelink, lastview);
    }

    @Override
    public Movies getCurrentMovie() {
        return dalFacade.getCurrentMovie();
    }

    @Override
    public void addToCategory(Categories selectedCategory, Movies selectedMovies){
        dalFacade.addToCategories(selectedCategory, selectedMovies);
    }
}
