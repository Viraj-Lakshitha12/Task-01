package lk.ijse.gdse.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.demo.dto.Supplier;
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

public class SupplierController {

    public AnchorPane pane;
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableView<Supplier> tblView;

    @FXML
    private TextField txtAddress;

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
        Supplier selectedSupplier = tblView.getSelectionModel().getSelectedItem();

        if (selectedSupplier != null) {
            txtId.setText(String.valueOf(selectedSupplier.getId()));
            txtCode.setText(selectedSupplier.getSupplierCode());
            txtName.setText(selectedSupplier.getName());
            txtAddress.setText(selectedSupplier.getAddress());
            cmbStatus.setValue(selectedSupplier.getStatus());
        }
    }

    private void loadDataAndSetToTable() {
        List<Supplier> supplierList = fetchDataFromBackend();
        updateTableView(supplierList);
    }

    //    get all data
    private List<Supplier> fetchDataFromBackend() {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/suppliers"))
                    .header("Authorization", jwtToken)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            Supplier[] suppliers = objectMapper.readValue(response.body(), Supplier[].class);
            return Arrays.asList(suppliers);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void updateTableView(List<Supplier> suppliers) {
        ObservableList<Supplier> observableList = FXCollections.observableArrayList(suppliers);
        tblView.setItems(observableList);
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("supplierCode"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }


    //delete supplier
    @FXML
    void btnDelete(ActionEvent event) {
        if (txtId.getText().isEmpty()) {
            showAlert("Error", "Enter All Details");
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
                    .uri(URI.create("http://localhost:8080/api/suppliers/" + idText))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            showAlert("Delete Successful", "Supplier has been successfully deleted.");
            loadDataAndSetToTable();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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


    //save supplier
    @FXML
    void btnSave(ActionEvent event) {
        if (!isInputValid()) {
            showAlert("Error", "Invalid data. Please check the highlighted fields.");
            return;
        }
        if (txtId.getText().isEmpty() || txtCode.getText().isEmpty() || txtName.getText().isEmpty() ||
                txtAddress.getText().isEmpty()) {
            showAlert("Error", "Enter All Details");
            return;
        }
        Supplier supplierDTO = new Supplier(
                null,
                txtCode.getText(),
                txtName.getText(),
                txtAddress.getText(),
                cmbStatus.getValue().toString()
        );
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/suppliers"))
                    .header("Authorization", jwtToken)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(supplierDTOToJson(supplierDTO)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                showAlert("Save Successful", "Supplier has been successfully saved.");
            }
            loadDataAndSetToTable();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String supplierDTOToJson(Supplier supplierDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(supplierDTO);
        } catch (Exception e) {
            return "{}";
        }
    }

    //update supplier
    @FXML
    void btnUpdate(ActionEvent event) {
        if (txtId.getText().isEmpty() || txtCode.getText().isEmpty() || txtName.getText().isEmpty() ||
                txtAddress.getText().isEmpty()) {
            showAlert("Error", "Enter All Details");
            return;
        }

        Supplier supplierDTO = new Supplier(
                Long.parseLong(txtId.getText()),
                txtCode.getText(),
                txtName.getText(),
                txtAddress.getText(),
                cmbStatus.getValue().toString()

        );
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/suppliers"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", jwtToken)
                    .PUT(HttpRequest.BodyPublishers.ofString(supplierDTOToJson(supplierDTO)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            if (response.statusCode() == 200) {
                showAlert("Update Successful", "Supplier has been successfully updated.");
                loadDataAndSetToTable();
            }
            loadDataAndSetToTable();

        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    public void btnNavigateInventrory(ActionEvent actionEvent) throws IOException {
        ViewLoader.loadNewView(actionEvent, "/lk/ijse/gdse/demo/Inventory-view.fxml", "Inventory from");
    }
}
