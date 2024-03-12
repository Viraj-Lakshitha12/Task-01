package lk.ijse.gdse.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import lk.ijse.gdse.demo.dto.User;
import lk.ijse.gdse.demo.util.PasswordUtils;
import lk.ijse.gdse.demo.util.ViewLoader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RegisterController {

    @FXML
    private Button loginButton;

    @FXML
    private BorderPane pane;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String usernameText = txtUsername.getText();
        String passwordText = txtPassword.getText();

        if (usernameText.isEmpty() || passwordText.isEmpty()) {
            showAlert(null, "Login Failed", "Enter your details");
            return;
        }
        String hashedPassword = PasswordUtils.hashPassword(passwordText);

        User user = new User(usernameText, hashedPassword);

        try {
            String registerEndpoint = "http://localhost:8080/api/user";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(registerEndpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(userDTOToJson(user)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                showAlert(response, "Registration Successful", "User registration successful.");
                ViewLoader.loadNewView(event, "/lk/ijse/gdse/demo/Login-view.fxml", "Login from");

            } else {
                showAlert(response, "Registration Failed", "Something went wrong.");
            }

        } catch (Exception e) {
            showAlert(null, "Registration Failed", "Error occurred during user registration.");
        }
    }


    private String userDTOToJson(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(user);
        } catch (Exception e) {
            return "{}";
        }
    }


    private void showAlert(HttpResponse<String> response, String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    void showLoginStage(MouseEvent event) {
        ViewLoader.loadNewView(event, "/lk/ijse/gdse/demo/Login-view.fxml", "Login from");
    }

}
