module lk.ijse.gdse.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;


    opens lk.ijse.gdse.demo to javafx.fxml;
    exports lk.ijse.gdse.demo;
    exports lk.ijse.gdse.demo.controllers;
    opens lk.ijse.gdse.demo.controllers to javafx.fxml;
}
