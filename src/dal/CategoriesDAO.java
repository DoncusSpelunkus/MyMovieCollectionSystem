package dal;

import be.Categories;
import be.Movies;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDAO {
    private final static DatabaseConnector db = new DatabaseConnector();
    private final CatMovieDAO catMovieDAO = new CatMovieDAO();

    // Method used to getting all the playlists from the database.
    public List<Category> getAllCategories() {
        List<Category> allCategories = new ArrayList<>();
        try (Connection connection = db.getConnection()) {
            String sqlStatement = "SELECT * FROM dbo.Category";
            Statement statement = connection.createStatement();
            if (statement.execute(sqlStatement)) {
                ResultSet rs = statement.getResultSet();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    Category category = new Category(id, name);
                    category.setCategorylist(CatMovieDAO.getCategoryMovie(id));
                    allCategories.add(category);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
        return allPlayList;
    }

    // Method used to update playlists in the database.
    public Playlist updatePlaylist(Playlist selectedPlaylist, String name) {
        try (Connection connection = db.getConnection()) {
            String query = "UPDATE playlistDatabase set title = ? WHERE id = ?";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, name);
            pstm.setInt(2, selectedPlaylist.getID());
            pstm.executeUpdate();
            return new Playlist(name,selectedPlaylist.getID());
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    // Method used for making a new playlist in the database.
    public Playlist makePlaylist(String title) {
        String sqlStatement = "INSERT INTO dbo.playlistDatabase(title) VALUES(?)";
        try (Connection con = db.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(sqlStatement);
            pstm.setString(1,title);
            pstm.addBatch();
            pstm.executeBatch();
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
        Playlist playlist = new Playlist(title,1);
        return playlist;
    }

    // Method used for deleting a playlist in the database.
    public void deletePlaylist(Playlist selectedPlaylist){
        try(Connection con = db.getConnection()){
            String sql = "DELETE from dbo.playlistDatabase Where id =?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1,selectedPlaylist.getID());
            pstm.execute();
        } catch(SQLException ex){
            System.out.println(ex);
        }
    }
}