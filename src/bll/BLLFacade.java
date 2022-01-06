package bll;

import be.Categories;
import be.Movies;

import java.sql.Date;
import java.util.List;

public interface BLLFacade {
    List<Movies> getAllMovies();

    void addMovie(String name, float rating, String filelink, Date lastview);

    void editMovie(Movies selectedMovie, String name, float rating, String filelink, Date lastview);

    void deleteMovie(Movies selectedMovie);

    List<Categories> getAllCategories();

    Categories makeCategories(String name);

    Categories updateCategories(Categories selectedCategories, String name);

    void deleteCategories(Categories selectedCategories);

    void addToCategory(Categories selectedCategory, Movies selectedMovie);

    void deleteFromCategories(Categories selectedCategories, Movies selectedMovie);

    Movies getCurrentMovie();
}
