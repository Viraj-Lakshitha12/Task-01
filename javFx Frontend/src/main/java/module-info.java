module lk.ijse.gdse.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires jbcrypt;

    opens lk.ijse.gdse.demo to javafx.fxml;
    opens lk.ijse.gdse.demo.controllers to javafx.fxml;
    opens lk.ijse.gdse.demo.dto to com.fasterxml.jackson.databind;

    exports lk.ijse.gdse.demo;
    exports lk.ijse.gdse.demo.controllers;
    exports lk.ijse.gdse.demo.dto;
}
