package gui.Controller;

import be.Categories;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import gui.Model.CategoriesModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCategoriesController {

    public Label errorLabel4;
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

    public AddCategoriesController() {
        categoriesModel = new CategoriesModel();
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    @FXML
    private void addCategory(ActionEvent actionEvent) throws SQLServerException { // adds the new category
        String name = categoryName.getText().trim();
        if (name.length() > 0 && name.length() < 50) {
            if (!isEditing) { // checks if in edit mode
                categoriesModel.addCategory(name);
                Node n = (Node) actionEvent.getSource();
                stage = (Stage) n.getScene().getWindow();
                stage.close();
            } else {
                categoriesModel.editCategory(categoriesToBeEdited, name);
                Node n = (Node) actionEvent.getSource();
                stage = (Stage) n.getScene().getWindow();
                stage.close();
            }
        } else {
            errorLabel4.setText("A name haven't been input, please input a name");
        }
        controller.refreshCategory();
    }

    @FXML
    private void closeACWindow(ActionEvent actionEvent) { // closes the window
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }


    public void setEdit(Categories selectedCategory) throws SQLServerException { // sets up edit mode
        if (selectedCategory != null) {
            categoriesToBeEdited = selectedCategory;
            isEditing = true;
            categoryName.setText(selectedCategory.getName());
            errorLabel4.setText("Edit mode: Enabled");
            addCategoryBtn.setText("Edit");
        }
        controller.refreshCategory();
    }
}

