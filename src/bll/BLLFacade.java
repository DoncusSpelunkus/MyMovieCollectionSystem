package bll;

import be.Movies;

import java.util.Date;

public interface BLLFacade {
    Movies addMovie(String name, float rating, String filelink, Date lastview);

    int getCurrentMovieID();
}
