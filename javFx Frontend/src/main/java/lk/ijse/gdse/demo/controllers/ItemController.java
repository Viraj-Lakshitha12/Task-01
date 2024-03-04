package lk.ijse.gdse.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.demo.dto.Category;
import lk.ijse.gdse.demo.dto.Item;
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
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void initialize() {
        setCellValueFactory();
        loadDataAndSetToTable();
        // Set items to ComboBoxes
        cmbCategory.setItems(fetchDataForComboBox("http://localhost:8080/api/categories/getIds"));
        cmbUnit.setItems(fetchDataForComboBox("http://localhost:8080/api/unit/getIds"));
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

    private Unit fetchDataForSaveUnit(String url) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            Unit data = objectMapper.readValue(response.body(), Unit.class);
            return data;

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        return null;
    }

    private Category fetchDataForSaveCategory(String url) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            Category data = objectMapper.readValue(response.body(), Category.class);
            return data;

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        return null;
    }

    @FXML
    void btnSaveItem(ActionEvent event) {
        String categoryValue = cmbCategory.getValue();
        String cmbUnitValue = cmbUnit.getValue();

        Unit unit = fetchDataForSaveUnit("http://localhost:8080/api/unit/getUnitById/" + cmbUnitValue);
        Category category = fetchDataForSaveCategory("http://localhost:8080/api/categories/findById/" + categoryValue);
        System.out.println(unit);
        System.out.println(category);

        if (category == null || unit == null) {
            System.out.println("Error fetching category or unit data.");
            return;
        }

        // Create an Item object with category and unit
        Item item = new Item();
        item.setId(txtId.getText());
        item.setCode(txtCode.getText());
        item.setName(txtName.getText());
        item.setCategory(category);
        item.setUnit(unit);
        item.setStatus(cmbStatus.getValue());

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
        loadDataAndSetToTable();
        if (response.statusCode() == 200) {
            showAlert(response, "Save Successful", "Item has been successfully Saved.");
        }
    }


    @FXML
    void btnDeleteItem(ActionEvent event) {
        String idText = txtId.getText();

        try {
            if (idText.isEmpty()) {
                showAlert(null, "Error", "Please enter a valid Item ID for deletion.");
                return;
            }
            HttpRequest.Builder builder = HttpRequest.newBuilder();
            builder.uri(URI.create("http://localhost:8080/api/items/" + idText));
            builder.header("Content-Type", "application/json");
            builder.DELETE();
            HttpRequest request = builder
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


    @FXML
    void btnUpdateItem(ActionEvent event) {
//        // Create an Item object
//        Item item = new Item(txtId.getText(), txtCode.getText(), txtName.getText(),
//                cmbCategory.getValue(), cmbUnit.getValue(), cmbStatus.getValue());
//
//        System.out.println(item);
//        // Convert the Item object to a JSON string
//        ObjectMapper objectMapper = new ObjectMapper();
//        String itemJson;
//        try {
//            itemJson = objectMapper.writeValueAsString(item);
//        } catch (Exception e) {
//            System.out.println("Error converting Item to JSON: " + e.getMessage());
//            return;
//        }
//        // Send the JSON string to the backend
//        HttpResponse<String> response = connectBackend("http://localhost:8080/api/items", "PUT", itemJson);
//        loadDataAndSetToTable();
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

    private void showAlert(HttpResponse<String> response, String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}
