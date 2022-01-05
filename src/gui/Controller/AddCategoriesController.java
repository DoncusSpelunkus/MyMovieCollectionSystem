package gui.Controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
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

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public void addCategory(ActionEvent actionEvent) throws SQLServerException {
        String name = categoryName.getText().trim();
        if(name.length() > 0 && name.length() < 50) {
            if (!isEditing) {

            }
            else {

            }
        }
    }

    public void closeACWindow(ActionEvent actionEvent) {

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();

        }
    }
