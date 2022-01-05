package bll;

import be.Categories;
import be.Movies;

import java.sql.Date;
import java.util.List;

public interface BLLFacade {
    void addMovie(String name, float rating, String filelink, Date lastview);

    Movies getCurrentMovie();

    void addToCategory(Categories selectedCategory, Movies selectedMovie);

    List<Movies> getAllMovies();

    void deleteMovie(Movies selectedMovie);

    void editMovie(Movies selectedMovie, String name, float rating, String filelink, Date lastview);
}
