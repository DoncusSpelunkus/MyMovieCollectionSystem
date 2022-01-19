package be;

import java.util.ArrayList;
import java.util.List;

public class Categories {

    // This makes sure we have a template for the categories.

    private String name;
    private List<Movies> moviesList = new ArrayList<>();
    private final int categoryID;

    public Categories(String name, int id){
        this.name = name;
        this.categoryID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setMovieList(List<Movies> moviesList){
        this.moviesList = moviesList;
    }

    public List<Movies> getMoviesList(){
        return moviesList;
    }

    public String toString(){
        return name;
    }
}
