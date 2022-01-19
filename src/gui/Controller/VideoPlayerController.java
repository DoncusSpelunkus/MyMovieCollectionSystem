package gui.Controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.MediaView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class VideoPlayerController implements Initializable {


    @FXML
    private Label titleMovie;

    @FXML
    private Label warning;

    @FXML
    private Button closeButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button playButton;

    @FXML
    private MediaView mediaView;

    public VideoPlayerController(MediaView mediaView) {
        this.mediaView = mediaView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

