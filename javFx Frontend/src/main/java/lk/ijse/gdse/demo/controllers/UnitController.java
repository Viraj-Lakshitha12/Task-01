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
import lk.ijse.gdse.demo.dto.Unit;
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

public class UnitController {

    public AnchorPane pane;
    @FXML
    private TableColumn<Unit, String> colCode;

    @FXML
    private TableColumn<Unit, String> colId;

    @FXML
    private TableColumn<Unit, String> colName;

    @FXML
    private TableColumn<Unit, String> colStatus;

    @FXML
    private TableView<Unit> tblView;

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


    // Delete unit
    @FXML
    void btnDelete(ActionEvent event) {
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
        Unit unitDTO = new Unit(
                txtId.getText(),
                txtCode.getText(),
                txtName.getText(),
                txtStatus.getText()
        );
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/unit"))
                    .header("Content-Type", "application/json")
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
        // Create the Unit object
        Unit unitDTO = new Unit(
                null,
                txtCode.getText(),
                txtName.getText(),
                txtStatus.getText()
        );
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/unit"))
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
        Navigation.navigate(Routes.CATEGORY,pane);
    }

    public void btnUnit(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.UNIT,pane);
    }

    public void btnSupplier(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER,pane);
    }
}
