package lk.ijse.gdse.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.demo.dto.Item;
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

import static lk.ijse.gdse.demo.util.ConnectToBackend.connectBackend;

public class ItemController {

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private ComboBox<String> cmbUnit;

    @FXML
    private TableColumn<Item, String> colCategory;

    @FXML
    private TableColumn<Item, String> colCode;

    @FXML
    private TableColumn<Item, String> colId;

    @FXML
    private TableColumn<Item, String> colName;

    @FXML
    private TableColumn<Item, String> colStatus;

    @FXML
    private TableColumn<Item, String> colUnit;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<Item> tblView;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    public void initialize() {
        setCellValueFactory();
        loadDataAndSetToTable();
        // Set items to ComboBoxes
        cmbCategory.setItems(fetchDataForComboBox("http://localhost:8080/api/categories/getNames"));
        cmbUnit.setItems(fetchDataForComboBox("http://localhost:8080/api/unit/getNames"));
        ObservableList<String> statusList = FXCollections.observableArrayList("Active", "Inactive");
        cmbStatus.setItems(statusList);
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
    }

    private void loadDataAndSetToTable() {
        List<Item> itemList = fetchDataFromBackend();
        updateTableView(itemList);
    }

    private List<Item> fetchDataFromBackend() {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/items"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            Item[] itemList = objectMapper.readValue(response.body(), Item[].class);
            return Arrays.asList(itemList);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void updateTableView(List<Item> itemList) {
        ObservableList<Item> observableList = FXCollections.observableArrayList(itemList);
        tblView.setItems(observableList);
    }

    @FXML
    void btnDeleteItem(ActionEvent event) {
        // Implement delete logic here
    }

    @FXML
    void btnNavigateCategory(ActionEvent event) {
        // Implement navigation to category view logic here
    }

    @FXML
    void btnNavigateSupplier(ActionEvent event) {
        // Implement navigation to supplier view logic here
    }

    @FXML
    void btnNavigateUnit(ActionEvent event) {
        // Implement navigation to unit view logic here
    }

    @FXML
    void btnSaveItem(ActionEvent event) {
        // Create an Item object
        Item item = new Item(txtId.getId(), txtCode.getText(), txtName.getText(),
                cmbCategory.getValue(), cmbUnit.getValue(), cmbStatus.getValue());

        // Convert the Item object to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String itemJson;
        try {
            itemJson = objectMapper.writeValueAsString(item);
        } catch (Exception e) {
            System.out.println("Error converting Item to JSON: " + e.getMessage());
            return;
        }

        // Send the JSON string to the backend
        HttpResponse<String> response = connectBackend("http://localhost:8080/api/items", "POST", itemJson);

        // Handle the response
        System.out.println(response.body());
    }

    @FXML
    void btnUpdateItem(ActionEvent event) {
        // Create an Item object
        Item item = new Item(txtId.getText(), txtCode.getText(), txtName.getText(),
                cmbCategory.getValue(), cmbUnit.getValue(), cmbStatus.getValue());

        System.out.println(item);
        // Convert the Item object to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String itemJson;
        try {
            itemJson = objectMapper.writeValueAsString(item);
        } catch (Exception e) {
            System.out.println("Error converting Item to JSON: " + e.getMessage());
            return;
        }
        // Send the JSON string to the backend
        HttpResponse<String> response = connectBackend("http://localhost:8080/api/items", "PUT", itemJson);
        loadDataAndSetToTable();
    }

    // all data for combobox
    private ObservableList<String> fetchDataForComboBox(String url) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            String[] data = objectMapper.readValue(response.body(), String[].class);
            return FXCollections.observableArrayList(data);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return FXCollections.observableArrayList();
        }
    }

    public void btnNavigationItem(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ITEM, pane);

    }

    public void btnNavigateInventrory(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.INVENTORY, pane);

    }

    public void btnSupplier(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER, pane);
    }

    public void btnUnit(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.UNIT, pane);

    }

    public void btnCategory(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CATEGORY, pane);

    }
}
