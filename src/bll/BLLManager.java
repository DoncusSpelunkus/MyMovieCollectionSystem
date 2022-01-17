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
    public void addMovie(String name, float rating, float prating, String filelink, Date lastview) {
        dalFacade.addMovies(name, rating, prating, filelink, lastview);
    }

    @Override
    public void editMovie(Movies selectedMovie, String name, float rating, float prating, String filelink, Date lastview){
        dalFacade.editMovies(selectedMovie, name, rating, prating, filelink, lastview);
    }

    @Override
    public void deleteMovie(Movies selectedMovie){
        dalFacade.deleteMovie(selectedMovie);
        dalFacade.deleteMovieFromAllCategories(selectedMovie);
    }

    public List<Categories> getAllCategories(){
        return dalFacade.getAllCategories();
    }

    @Override
    public Categories makeCategories(String name) {
        return dalFacade.makeCategories(name);
    }

    @Override
    public Categories updateCategories(Categories selectedCategories, String name) {
        return dalFacade.updateCategories(selectedCategories, name);
    }

    @Override
    public void deleteCategories(Categories selectedCategories) {
        dalFacade.deleteCategories(selectedCategories);
    }


    @Override
    public void addToCategory(Categories selectedCategory, Movies selectedMovies){
        dalFacade.addToCategories(selectedCategory, selectedMovies);
    }

    @Override
    public void deleteFromCategories(Categories selectedCategories, Movies selectedMovie){
        dalFacade.deleteFromCategories(selectedCategories, selectedMovie);
    }

}
