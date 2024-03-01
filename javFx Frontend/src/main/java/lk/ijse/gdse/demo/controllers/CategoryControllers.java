package lk.ijse.gdse.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.gdse.demo.dto.Category;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CategoryControllers {

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableView<?> tblView;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtStatus;

    @FXML
    void btnDelete(ActionEvent event) {
        // Implement delete logic if needed
    }

    @FXML
    void btnUpdate(ActionEvent event) {
        // Implement update logic if needed
    }

    @FXML
    void btnSave(ActionEvent event) {
        // Create a CategoryDTO object
        Category categoryDTO = new Category();
        categoryDTO.setId(txtId.getText());
        categoryDTO.setCode(txtCode.getText());
        categoryDTO.setName(txtName.getText());
        categoryDTO.setStatus(txtStatus.getText());

        // Send the CategoryDTO to the backend in a background task
        sendDataToBackend(categoryDTO);
    }

    private void sendDataToBackend(Category categoryDTO) {
        try {
            // Create an HttpClient
            HttpClient httpClient = HttpClient.newHttpClient();

            // Set up the request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/categories"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(categoryDTOToJson(categoryDTO)))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            //show alert
            if (response != null && response.statusCode() == 200) {
                // Show an alert indicating successful save
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Save Successful");
                alert.setContentText("Data has been successfully saved.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("something went wrong");
                alert.setContentText("please try again...");
                alert.showAndWait();
            }

            // Print the response if needed
            System.out.println(response.body());


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private String categoryDTOToJson(Category categoryDTO) {
        return String.format("{\"id\":\"%s\",\"code\":\"%s\",\"name\":\"%s\",\"status\":\"%s\"}",
                categoryDTO.getId(), categoryDTO.getCode(), categoryDTO.getName(), categoryDTO.getStatus());
    }

}
