package com.math_app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class Login {

    public PasswordField password;
    public TextField username;
    public Button grade_two, grade_three, grade_one, grade_k, grade_four;
    public ImageView first_avatar, second_avatar;


    private User user;

    @FXML
    public void initialize() {
        user = new User(username.getText(), password.getText());

        // Assign handlers to grade buttons
        grade_k.setOnAction(event -> user.setGrade(0));
        grade_one.setOnAction(event -> user.setGrade(1));
        grade_two.setOnAction(event -> user.setGrade(2));
        grade_three.setOnAction(event -> user.setGrade(3));
        grade_four.setOnAction(event -> user.setGrade(4));

        // Assign handlers to avatar images
        first_avatar.setOnMouseClicked(event -> user.setAvatar(first_avatar));
        second_avatar.setOnMouseClicked(event -> user.setAvatar(second_avatar));
    }
}
