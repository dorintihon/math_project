module com.example.math_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires jbcrypt;
    requires dotenv.java;


    opens com.math_app to javafx.fxml;
    exports com.math_app;
}