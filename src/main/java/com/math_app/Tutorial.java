package com.math_app;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;

public class Tutorial {

    public ImageView backButton;
    @FXML
    private WebView webView;

    public void initialize() {
        WebEngine webEngine = webView.getEngine();
        // Use the embed URL, which you can obtain from the YouTube video's share option
        String videoUrl = "https://www.youtube.com/embed/L0Oq1xqCQss?autoplay=1"; // Link to the video
        webEngine.load(videoUrl);

        backButton.setOnMouseClicked(event -> {
            // Get the current stage (window) from the playButton
            Stage stage = (Stage) backButton.getScene().getWindow();

            webView.getEngine().load("about:blank");

            // Call the changeScene method with the necessary parameters
            SceneChanger.changeScene(stage, "map.fxml", "Map");

        });
    }
}
