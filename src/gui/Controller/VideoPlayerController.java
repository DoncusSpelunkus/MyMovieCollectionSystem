package gui.Controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

public class VideoPlayerController implements Initializable {

    @FXML
    private MediaView mediaView;

    public VideoPlayerController(MediaView mediaView) {
        this.mediaView = mediaView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

