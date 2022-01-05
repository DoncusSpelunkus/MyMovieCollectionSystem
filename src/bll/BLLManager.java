package bll;

import be.Movies;
import dal.DALFacade;
import dal.DALManager;

import java.util.Date;

public class BLLManager implements BLLFacade{

    private DALFacade dalFacade;


    public BLLManager(){
        dalFacade = new DALManager();
    }

    @Override
    public Movies addMovie(String name, float rating, String filelink, Date lastview) {
        return null;
    }

    @Override
    public int getCurrentMovieID() {
        return dalFacade.getCurrentMovieID();
    }
}
