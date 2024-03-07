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
import lk.ijse.gdse.demo.dto.User;
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
    public static String jwtToken;

    //    check username password
    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        String usernameText = txtUsername.getText();
        String passwordText = txtPassword.getText();

        try {
            String userEndpoint = "http://localhost:8080/api/user/login";

            User user = new User();
            user.setUserName(usernameText);
            user.setPassword(passwordText);

            // Convert User object to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(user);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(userEndpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper responseMapper = new ObjectMapper();
                JsonNode jsonResponse = responseMapper.readTree(response.body());
                String token = jsonResponse.get("data").asText();
                jwtToken = token;
                ViewLoader.loadNewView(event, "/lk/ijse/gdse/demo/Inventory-view.fxml", "Inventory from");
            }

        } catch (Exception e) {
            showAlert("Login Failed", "Error occurred during user login.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    void showRegisterStage(MouseEvent event) {
        ViewLoader.loadNewView(event, "/lk/ijse/gdse/demo/Register-view.fxml", "Register from");
    }

}
