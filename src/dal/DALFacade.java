package dal;

import be.Categories;
import be.Movies;

import java.sql.Date;
import java.util.List;

public interface DALFacade {

    List<Movies> getAllMovies();

    Movies addMovies(String name, float rating, String filelink, Date lastview);

    Movies editMovies(Movies selectedMovie, String name, float rating, String filelink, Date lastview);

    void deleteMovie(Movies selectedMovie);

    List<Categories> getAllCategories();

    Categories makeCategories(String name);

    Categories updateCategories(Categories selectedCategories, String name);

    void deleteCategories(Categories selectedCategories);

    void deleteMovieFromAllCategories(Movies selectedMovie);

    Movies addToCategories(Categories selectedCategories, Movies selectedMovie);

    void deleteFromCategories(Categories selectedCategories, Movies selectedMovie);

    int getCurrentMovieID();
}
