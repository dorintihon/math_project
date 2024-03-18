module com.example.math_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    opens com.math_app to javafx.fxml;
    exports com.math_app;
}