package bll;

import be.Categories;
import be.Movies;
import dal.DALFacade;
import dal.DALManager;

import java.sql.Date;

public class BLLManager implements BLLFacade{

    private DALFacade dalFacade;


    public BLLManager(){
        dalFacade = new DALManager();
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
