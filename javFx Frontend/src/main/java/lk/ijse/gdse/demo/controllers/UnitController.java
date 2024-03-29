package lk.ijse.gdse.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.demo.dto.Unit;
import lk.ijse.gdse.demo.util.ViewLoader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static lk.ijse.gdse.demo.controllers.LoginController.jwtToken;

public class UnitController {

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<Unit> tblView;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;
    @FXML
    public ComboBox cmbStatus;



    public void initialize() {
        setCellValueFactory();
        loadDataAndSetToTable();
        tblView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                handleTableClick();
            }
        });
        ObservableList<String> statusList = FXCollections.observableArrayList("Active", "Inactive");
        cmbStatus.setItems(statusList);

    }

    private void handleTableClick() {
        Unit selectedCategory = tblView.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            txtId.setText(selectedCategory.getId());
            txtCode.setText(selectedCategory.getCode());
            txtName.setText(selectedCategory.getName());
            cmbStatus.setValue(selectedCategory.getStatus());
        }
    }

    private void setCellValueFactory() {
        // Set cell value factory for each column
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadDataAndSetToTable() {
        // Fetch data from backend and update the table
        List<Unit> unitList = fetchDataFromBackend();
        updateTableView(unitList);
    }

    private List<Unit> fetchDataFromBackend() {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/unit"))
                    .header("Authorization",jwtToken)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            Unit[] unit = objectMapper.readValue(response.body(), Unit[].class);
            return Arrays.asList(unit);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    private void updateTableView(List<Unit> unitList) {
        // Update the TableView with the fetched data
        ObservableList<Unit> observableList = FXCollections.observableArrayList(unitList);
        tblView.setItems(observableList);
    }


    private boolean isInputValid() {
        return validateTextField(txtId) &&
                validateTextField(txtCode) &&
                validateTextField(txtName);
    }

    private boolean validateTextField(TextField textField) {
        String text = textField.getText().trim();
        boolean isValid = !text.isEmpty();
        if (!isValid) {
            setInvalidStyle(textField);
        } else {
            setValidStyle(textField);
        }
        return isValid;
    }


    private void setInvalidStyle(Control control) {
        control.setStyle("-fx-border-color: red;");
    }

    private void setValidStyle(Control control) {
        control.setStyle("-fx-border-color: green;");
    }


    // Delete unit
    @FXML
    void btnDelete(ActionEvent event) {
        if (txtId.getText().isEmpty()) {
            showAlert("Error", "Enter Id");
            return;
        }
        String idText = txtId.getText();

        try {
            if (idText.isEmpty()) {
                showAlert("Error", "Please enter a valid unit ID for deletion.");
                return;
            }

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/unit/" + idText))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            showAlert("Delete Successful", "Unit has been successfully deleted.");
            loadDataAndSetToTable();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Update unit
    @FXML
    void btnUpdate(ActionEvent event) {
        if (!isInputValid()) {
            showAlert(null, "Error", "Invalid data. Please check the highlighted fields.");
            return;
        }

        if (txtCode.getText().isEmpty() || txtName.getText().isEmpty()) {
            showAlert("Error", "Enter All Details");
            return;
        }
        Unit unitDTO = new Unit(
                txtId.getText(),
                txtCode.getText(),
                txtName.getText(),
                cmbStatus.getValue().toString()
        );
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/unit"))
                    .header("Content-Type", "application/json")
                    .header("Authorization",jwtToken)
                    .PUT(HttpRequest.BodyPublishers.ofString(unitDTOToJson(unitDTO)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                showAlert("Update Successful", "Unit has been successfully updated.");
            }
            loadDataAndSetToTable();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Save unit
    @FXML
    void btnSave(ActionEvent event) {
        if (!isInputValid()) {
            showAlert(null, "Error", "Invalid data. Please check the highlighted fields.");
            return;
        }

        if (txtCode.getText().isEmpty() || txtName.getText().isEmpty() ) {
            showAlert("Error", "Enter All Details");
            return;
        }
        // Create the Unit object
        Unit unitDTO = new Unit(
                null,
                txtCode.getText(),
                txtName.getText(),
                cmbStatus.getValue().toString()
        );
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/unit"))
                    .header("Authorization",jwtToken)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(unitDTOToJson(unitDTO)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            showAlert("Save Successful", "Unit has been successfully saved.");
            loadDataAndSetToTable();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String unitDTOToJson(Unit unitDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(unitDTO);
        } catch (Exception e) {
            return "{}";
        }
    }

    private void showAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void btnCategory(ActionEvent actionEvent) throws IOException {
        ViewLoader.loadNewView(actionEvent, "/lk/ijse/gdse/demo/Category-view.fxml", "Category from");
    }

    public void btnUnit(ActionEvent actionEvent) throws IOException {
        ViewLoader.loadNewView(actionEvent, "/lk/ijse/gdse/demo/Unit-view.fxml", "Unit from");
    }

    public void btnSupplier(ActionEvent actionEvent) throws IOException {
        ViewLoader.loadNewView(actionEvent, "/lk/ijse/gdse/demo/Supplier-view.fxml", "Supplier from");
    }

    public void btnNavigationItem(ActionEvent actionEvent) throws IOException {
        ViewLoader.loadNewView(actionEvent, "/lk/ijse/gdse/demo/Item-view.fxml", "Item from");
    }

    public void btnNavigateInventory(ActionEvent actionEvent) throws IOException {
        ViewLoader.loadNewView(actionEvent, "/lk/ijse/gdse/demo/Inventory-view.fxml", "Inventory from");
    }

    private void showAlert(HttpResponse<String> response, String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}
