package gui.Controller;
import gui.Model.CategoriesModel;
import gui.Model.MoviesModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


public class StartupController {
    @FXML
    private Label errorLabel3;

    @FXML
    private ListView<?> listOfMovies;

    @FXML
    private Button noBtn;

    @FXML
    private Button yesBtn;

    @FXML
    void no(ActionEvent event) {
        Stage stage = (Stage) noBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void yes(ActionEvent event) {
        MoviesModel.yesBtn(listOfMovies.getSelectionModel().getSelectedItem());
        listOfMovies.getItems().remove(listOfMovies.getSelectionModel().getSelectedIndex());

    }

}
