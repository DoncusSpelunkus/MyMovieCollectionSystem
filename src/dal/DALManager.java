package dal;

import be.Categories;
import be.Movies;

import java.sql.Date;
import java.util.List;

public class DALManager implements DALFacade {

    private MoviesDAO moviesDAO;
    private CategoriesDAO categoriesDAO;
    private CatMovieDAO catMovieDAO;

    public DALManager(){
        moviesDAO = new MoviesDAO();
        categoriesDAO = new CategoriesDAO();
        catMovieDAO = new CatMovieDAO();
    }

    @Override
    public List<Movies> getAllMovies() {
        return moviesDAO.getAllMovies();
    }

    @Override
    public void deleteMovie(Movies selectedMovie) {
        moviesDAO.deleteMovie(selectedMovie);
        catMovieDAO.deleteMovieFromAllCategories(selectedMovie);
    }

    @Override
    public Movies editMovies(Movies selectedMovie, String name, float rating, float prating, String filelink, Date lastview) {
        return moviesDAO.editMovies(selectedMovie, name, rating, prating, filelink, lastview);
    }

    @Override
    public void addMovies(String name, float rating, float prating, String filelink, Date lastview) {
        moviesDAO.addMovies(name, rating, prating, filelink, lastview);
    }

    @Override
    public void deleteCategories(Categories selectedCategories) {
        categoriesDAO.deleteCategories(selectedCategories);
    }

    @Override
    public Categories makeCategories(String name) {
        return categoriesDAO.makeCategories(name);
    }

    @Override
    public List<Categories> getAllCategories() {
        return categoriesDAO.getAllCategories();
    }

    @Override
    public Categories updateCategories(Categories selectedCategories, String name) {
        return categoriesDAO.updateCategories(selectedCategories, name);
    }

    @Override
    public Movies addToCategories(Categories selectedCategories, Movies selectedMovie) {
        return catMovieDAO.addToCategories(selectedCategories, selectedMovie);
    }

    @Override
    public void deleteFromCategories(Categories selectedCategories, Movies selectedMovie){
        catMovieDAO.deleteFromCategories(selectedCategories, selectedMovie);
    }

    @Override
    public void deleteMovieFromAllCategories(Movies selectedMovie){
        catMovieDAO.deleteMovieFromAllCategories(selectedMovie);
    }

}