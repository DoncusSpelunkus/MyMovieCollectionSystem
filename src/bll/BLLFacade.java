package bll;

import be.Categories;
import be.Movies;

import java.sql.Date;

public interface BLLFacade {
    void addMovie(String name, float rating, String filelink, Date lastview);

    Movies getCurrentMovie();

    void addToCategory(Categories selectedCategory, Movies selectedMovie);
}
