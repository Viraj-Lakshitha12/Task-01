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
import lk.ijse.gdse.demo.dto.Supplier;
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
    private TextField txtStatus;

    public void initialize() {
        setCellValueFactory();
        loadDataAndSetToTable();
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

    //save supplier
    @FXML
    void btnSave(ActionEvent event) {
        if (txtId.getText().isEmpty() || txtCode.getText().isEmpty() || txtName.getText().isEmpty() ||
                txtAddress.getText().isEmpty() || txtStatus.getText().isEmpty()) {
            showAlert("Error", "Enter All Details");
            return;
        }
        Supplier supplierDTO = new Supplier(
                null,
                txtCode.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtStatus.getText()
        );
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/suppliers"))
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
                txtAddress.getText().isEmpty() || txtStatus.getText().isEmpty()) {
            showAlert("Error", "Enter All Details");
            return;
        }

        Supplier supplierDTO = new Supplier(
                Long.parseLong(txtId.getText()),
                txtCode.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtStatus.getText()
        );
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/suppliers"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(supplierDTOToJson(supplierDTO)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                showAlert("Update Successful", "Supplier has been successfully updated.");
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
        Navigation.navigate(Routes.CATEGORY, pane);
    }

    public void btnUnit(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.UNIT, pane);
    }

    public void btnSupplier(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER, pane);
    }

    public void btnNavigationItem(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ITEM, pane);

    }

    public void btnNavigateInventrory(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.INVENTORY, pane);

    }
}
