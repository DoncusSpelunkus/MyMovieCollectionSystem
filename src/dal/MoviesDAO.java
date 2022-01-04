package dal;

import be.Categories;
import be.Movies;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.List;

public class MoviesDAO {
    private final static DatabaseConnector db = new DatabaseConnector();

    // Method used to get all the movies from the database.
    public List<Movies> getAllMovies() {
        List<Movies> movieList = FXCollections.observableArrayList();
        try (Connection connection = db.getConnection()) {
            String sqlStatement = "SELECT * FROM Movie";
            Statement statement = connection.createStatement();
            if (statement.execute(sqlStatement)) {
                ResultSet rs = statement.getResultSet();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    float rating = rs.getFloat("rating");
                    String filelink = rs.getString("filelink");
                    Date lastview = rs.getDate("lastview");

                    Movies movie = new Movies(id, name,rating,filelink,lastview);// Creating a movie object from the retrieved values
                    movieList.add(movie); // Adding the movie to  list
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
        return movieList;
    }

    // Method used for adding movies from user input into the database.
    public Movies addMovies(String name, float rating, String filelink, Date lastview) {
        String sqlStatement = "INSERT INTO Movie(name, rating, filelink, lastview) VALUES (?,?,?,?)";
        try(Connection connection = db.getConnection()){
            PreparedStatement pstm = connection.prepareStatement(sqlStatement);
            pstm.setString(1, name);
            pstm.setFloat(2, rating);
            pstm.setString(3, filelink);
            pstm.setDate(4, lastview);
            pstm.addBatch(); // Adding to the statement
            pstm.executeBatch(); // Executing the added parameters, and  executing the statement
        } catch(SQLException ex) {
            System.out.println(ex);
            return null;
        }
        Movies movie = new Movies(1,name,rating,filelink,lastview,); // Creating a new movie object
        return movie;
    }

    // Method used for editing the movies in the database.
    public Movies editMovies(Movies selectedMovie, String name, float rating, String filelink, Date lastview) {
        try (Connection connection = db.getConnection()) {
            String query = "UPDATE Movie set name = ?,rating = ?,filelink = ?,lastview = ?, WHERE id = ?";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, name);
            pstm.setFloat(2, rating);
            pstm.setString(3, filelink);
            pstm.setDate(4, lastview);
            pstm.setInt(5, selectedMovie.getID());
            pstm.executeUpdate();
            return new Movies(name,rating,filelink,lastview,selectedMovie.getID());
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    // Method used for deleting the movies in the database. ATTENTION SHOULD USE ID TO IDENTIFY SONG.
    public void deleteMovie(Movies selectedMovie){
        try(Connection connection = db.getConnection()){
            String query = "DELETE FROM Movie WHERE id = ?";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1,selectedMovie.getID());
            pstm.executeUpdate(); // Executing the statement
        } catch(SQLException ex){
            System.out.println(ex);
        }
    }
}
