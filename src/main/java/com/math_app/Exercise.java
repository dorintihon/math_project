package com.math_app;

import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;

public class Exercise {
    public ImageView backButton;

    public void initialize() {


        backButton.setOnMouseClicked(event -> {
            // Get the current stage (window) from the playButton
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Call the changeScene method with the necessary parameters
            SceneChanger.changeScene(stage, "map.fxml", "Map");
        });
    }
}
