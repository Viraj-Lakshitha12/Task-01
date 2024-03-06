package lk.ijse.gdse.demo.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import lk.ijse.gdse.demo.util.PasswordUtils;
import lk.ijse.gdse.demo.util.ViewLoader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private BorderPane pane;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;
    private final HttpClient httpClient = HttpClient.newHttpClient();

//    check username password
    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        String usernameText = txtUsername.getText();
        String passwordText = txtPassword.getText();

        try {
            String userEndpoint = "http://localhost:8080/api/user/" + usernameText;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(userEndpoint))
                    .header("Content-Type", "application/json")
                    .GET().build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            // Parse the JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());

            // Extract hashed password from the response
            String hashedPassword = jsonNode.get("data").get("password").asText();

            // Check if the entered password matches the stored hashed password
            if (PasswordUtils.verifyPassword(passwordText, hashedPassword)) {
                // Passwords match, proceed to the next view
                ViewLoader.loadNewView(event, "/lk/ijse/gdse/demo/Inventory-view.fxml", "Inventory from");
            } else {
                // Passwords do not match, show an alert or handle it accordingly
                showAlert(null, "Login Failed", "Invalid username or password.");
            }

        } catch (Exception e) {
            showAlert(null, "Login Failed", "Error occurred during user login.");
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
    void showRegisterStage(MouseEvent event) {
        ViewLoader.loadNewView(event, "/lk/ijse/gdse/demo/Register-view.fxml", "Register from");
    }

}
