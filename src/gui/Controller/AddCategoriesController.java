package gui.Controller;

import be.Categories;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import gui.Model.CategoriesModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCategoriesController {

    @FXML
    private Button addCategoryBtn;

    @FXML
    private Button cancelBtn2;

    @FXML
    private TextField categoryName;

    Stage stage;
    private boolean isEditing = false;
    private MainController controller;
    private final CategoriesModel categoriesModel;
    private Categories categoriesToBeEdited;

    public AddCategoriesController(){
        categoriesModel = new CategoriesModel();
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public void addCategory(ActionEvent actionEvent) throws SQLServerException {
        String name = categoryName.getText().trim();
        if(name.length() > 0 && name.length() < 50) {
            if (!isEditing) {
                categoriesModel.addCategory(name);
            }
            else {
                categoriesModel.editCategory(categoriesToBeEdited, name);
            }
        }
        controller.refreshCategory();
        Node n = (Node) actionEvent.getSource();
        stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void setEdit(Categories selectedCategory) throws SQLServerException{
        if(selectedCategory != null){
            categoriesToBeEdited = selectedCategory;
            isEditing = true;
            categoryName.setText(selectedCategory.getName());
            addCategoryBtn.setText("Edit");
        }
        controller.refreshCategory();
    }

    public void closeACWindow(ActionEvent actionEvent) {

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();

        }
    }

