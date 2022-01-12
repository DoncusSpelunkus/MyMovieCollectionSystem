package dal;

import be.Categories;
import be.Movies;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatMovieDAO {
    private final static DatabaseConnector db = new DatabaseConnector();

    // Method used to getting all the movies in the categories from the database.
    public List<Movies> getMovieCategories(int categoryid) {
        List<Movies> newMovieList = new ArrayList();
        try (Connection con = db.getConnection()) {
            String query = "SELECT * FROM CatMovie INNER JOIN Movie ON CatMovie.movieid = Movie.id WHERE CatMovie.categoryid = ?";
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setInt(1, categoryid);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Movies movie = new Movies(rs.getInt("movieid"), rs.getString("name"), rs.getFloat("rating"), rs.getFloat("prating"), rs.getString("filelink"), rs.getDate("lastview"));
                newMovieList.add(movie);
            }
            return newMovieList;
        } catch (SQLServerException ex) {
            System.out.println(ex);
            return null;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    // Method used to delete movies from categories in the database.
    public void deleteMovieFromAllCategories(Movies selectedMovie) {
        try (Connection con = db.getConnection()) {
            String query = "DELETE from CatMovie WHERE movieid = ?";
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setInt(1, selectedMovie.getMovieID());
            pstm.execute();
        } catch (SQLServerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    // Method used to add movies to categories in the database.
    public Movies addToCategories(Categories selectedCategories, Movies selectedMovie) {
        String sql = "INSERT INTO CatMovie(categoryid,movieid) VALUES (?,?)";
        try (Connection con = db.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, selectedCategories.getCategoryID());
            pstm.setInt(2, selectedMovie.getMovieID());
            pstm.addBatch();
            pstm.executeBatch();
            return selectedMovie;
        } catch (SQLServerException ex) {
            System.out.println(ex);
            return null;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    // Method to delete a Movie from a Category.
    public void deleteFromCategories(Categories selectedCategories, Movies selectedMovie) {
        try (Connection con = db.getConnection()) {
            String query = "DELETE from CatMovie WHERE categoryID = ? AND movieID = ?";
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setInt(1, selectedCategories.getCategoryID());
            pstm.setInt(2, selectedMovie.getMovieID());
            pstm.execute();
        } catch (SQLServerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}