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

    // Method used to getting all the categories from the database.
    public List<Categories> getAllCategories() {
        List<Categories> allCategories = new ArrayList<>();
        try (Connection connection = db.getConnection()) {
            String sqlStatement = "SELECT * FROM Category";
            Statement statement = connection.createStatement();
            if (statement.execute(sqlStatement)) {
                ResultSet rs = statement.getResultSet();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    Categories category = new Categories(name, id);
                    category.setCategorieslist(CatMovieDAO.getCategoriesMovie(id));
                    allCategories.add(category);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
        return allCategories;
    }

    // Method used to update categories in the database.
    public Categories updatePlaylist(Categories selectedCategories, String name) {
        try (Connection connection = db.getConnection()) {
            String query = "UPDATE Category set name = ? WHERE id = ?";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, name);
            pstm.setInt(2, selectedCategories.getCategoryID());
            pstm.executeUpdate();
            return new Categories(name,selectedCategories.getCategoryID());
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    // Method used for making a new categories in the database.
    public Categories makeCategories(String name) {
        String sqlStatement = "INSERT INTO Category(name) VALUES(?)";
        try (Connection con = db.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(sqlStatement);
            pstm.setString(1,name);
            pstm.addBatch();
            pstm.executeBatch();
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
        Categories categories = new Categories(name,1);
        return categories;
    }

    // Method used to update categories in the database.
    public Categories updateCategories(Categories selectedCategories, String name) {
        try (Connection connection = db.getConnection()) {
            String query = "UPDATE Category set name = ? WHERE id = ?";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, name);
            pstm.setInt(2, selectedCategories.getID());
            pstm.executeUpdate();
            return new Categories(name,selectedCategories.getID());
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    // Method used for deleting a categories in the database.
    public void deleteCategories(Categories selectedCategories){
        try(Connection con = db.getConnection()){
            String sql = "DELETE from Category Where id =?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1,selectedCategories.getCategoryID());
            pstm.execute();
        } catch(SQLException ex){
            System.out.println(ex);
        }
    }
}