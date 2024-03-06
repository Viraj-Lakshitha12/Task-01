package lk.ijse.gdse.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lk.ijse.gdse.demo.util.Navigation;
import lk.ijse.gdse.demo.util.Routes;
import lk.ijse.gdse.demo.util.ViewLoader;

import java.io.IOException;
import java.net.URL;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private BorderPane pane;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        ViewLoader.loadNewView(event,"/lk/ijse/gdse/demo/Inventory-view.fxml","Inventory from");
    }


    @FXML
    void showRegisterStage(MouseEvent event) {

    }

}
