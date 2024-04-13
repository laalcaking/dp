module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}