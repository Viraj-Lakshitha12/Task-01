package lk.ijse.gdse.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.demo.dto.Category;
import lk.ijse.gdse.demo.util.Navigation;
import lk.ijse.gdse.demo.util.Routes;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryControllers {

    public AnchorPane pane;
    @FXML
    private TableColumn<Category, String> colCode;

    @FXML
    private TableColumn<Category, String> colId;

    @FXML
    private TableColumn<Category, String> colName;

    @FXML
    private TableColumn<Category, String> colStatus;

    @FXML
    private TableView<Category> tblView;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtStatus;

    public void initialize() {
        setCellValueFactory();
        loadDataAndSetToTable();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
    }

    private void loadDataAndSetToTable() {
        List<Category> categoryList = fetchDataFromBackend();
        updateTableView(categoryList);
    }

    private List<Category> fetchDataFromBackend() {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/categories"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            Category[] categories = objectMapper.readValue(response.body(), Category[].class);
            return Arrays.asList(categories);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void updateTableView(List<Category> categoryList) {
        ObservableList<Category> observableList = FXCollections.observableArrayList(categoryList);
        tblView.setItems(observableList);
    }

    //delete category
    @FXML
    void btnDelete(ActionEvent event) {
        if (txtId.getText().isEmpty()) {
            showAlert(null, "Error", "Enter Id");
            return;
        }
        String idText = txtId.getText();

        try {
            if (idText.isEmpty()) {
                showAlert(null, "Error", "Please enter a valid category ID for deletion.");
                return;
            }

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/categories/" + idText))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
            if (response.statusCode() == 200) {
                showAlert(response, "Delete Successful", "Category has been successfully deleted.");
            }
            loadDataAndSetToTable();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    // Update category
    @FXML
    void btnUpdate(ActionEvent event) {
        if (txtId.getText().isEmpty() || txtCode.getText().isEmpty() || txtName.getText().isEmpty() || txtStatus.getText().isEmpty()) {
            showAlert(null, "Error", "Enter All Details");
            return;
        }

        loadDataAndSetToTable();
        Category categoryDTO = new Category(
                txtId.getText(),
                txtCode.getText(),
                txtName.getText(),
                txtStatus.getText()
        );
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/categories"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(categoryDTOToJson(categoryDTO)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            showAlert(response, "update Successful", "Category has been successfully updated.");
            loadDataAndSetToTable();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Save category
    @FXML
    void btnSave(ActionEvent event) {
        if (txtCode.getText().isEmpty() || txtName.getText().isEmpty() || txtStatus.getText().isEmpty()) {
            showAlert(null, "Error", "Enter All Details");
            return;
        }

        loadDataAndSetToTable();
        // Create the Category object
        Category categoryDTO = new Category(
                null,
                txtCode.getText(),
                txtName.getText(),
                txtStatus.getText()
        );
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/categories"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(categoryDTOToJson(categoryDTO)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            showAlert(response, "Save Successful", "Category has been successfully saved.");
            loadDataAndSetToTable();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private String categoryDTOToJson(Category categoryDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(categoryDTO);
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

    public void btnCategory(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CATEGORY, pane);
    }

    public void btnUnit(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.UNIT, pane);
    }

    public void btnSupplier(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER, pane);
    }
}
