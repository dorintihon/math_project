package com.math_app;


import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class Menu {

    @FXML
    public ImageView playButton;

    @FXML
    public void initialize() {
        playButton.setOnMouseClicked(event -> {
            // Get the current stage (window) from the playButton
            Stage stage = (Stage) playButton.getScene().getWindow();

            // Call the changeScene method with the necessary parameters
            SceneChanger.changeScene(stage, "map.fxml", "Map");
        });
    }


}
