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
import lk.ijse.gdse.demo.util.ViewLoader;

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
    private Label lblStatus;

    @FXML
    private Label lblUnitCode;
    @FXML
    private Label lblUnitName;

    @FXML
    private Label lblCategoryCode;

    @FXML
    private Label lblCategoryName;

    @FXML
    private Label lblCategoryStatus;


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

    private boolean isInputValid() {
        return validateTextField(txtId) &&
                validateTextField(txtCode) &&
                validateTextField(txtName) &&
                validateComboBox(cmbCategory) &&
                validateComboBox(cmbUnit) &&
                validateComboBox(cmbStatus);
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

    private boolean validateComboBox(ComboBox<String> comboBox) {
        boolean isValid = comboBox.getValue() != null;
        if (!isValid) {
            setInvalidStyle(comboBox);
        } else {
            setValidStyle(comboBox);
        }
        return isValid;
    }

    private void setInvalidStyle(Control control) {
        control.setStyle("-fx-border-color: red;");
    }

    private void setValidStyle(Control control) {
        control.setStyle("-fx-border-color: green;");
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
            lblUnitCode.setText(data.getCode());
            lblStatus.setText(data.getStatus());
            lblUnitName.setText(data.getName());
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
            lblCategoryStatus.setText((data.getStatus()));
            lblCategoryCode.setText(data.getCode());
            lblCategoryName.setText(data.getName());
            return data;

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        return null;
    }

    private boolean validateAllFields() {
        boolean isIdValid = validateTextField(txtId);
        boolean isCodeValid = validateTextField(txtCode);
        boolean isNameValid = validateTextField(txtName);
        boolean isCategoryValid = validateComboBox(cmbCategory);
        boolean isUnitValid = validateComboBox(cmbUnit);
        boolean isStatusValid = cmbStatus.getValue() != null;

        return isIdValid && isCodeValid && isNameValid && isCategoryValid && isUnitValid && isStatusValid;
    }

    @FXML
    void btnSaveItem(ActionEvent event) {
        if (!validateAllFields()) {
            showAlert(null, "Error", "Invalid data. Please check the highlighted fields.");
            return;
        }
        Category category = cmbOnActionCategory(event);
        Unit unit = cmbOnActionUnit(event);

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
        HttpResponse<String> response = connectBackend("http://localhost:8080/api/items", "PUT", itemJson);
        loadDataAndSetToTable();
        if (response.statusCode() == 200) {
            showAlert(response, "Update Successful", "Item has been successfully updated.");
        }
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
        ViewLoader.loadNewView(actionEvent, "/lk/ijse/gdse/demo/Item-view.fxml", "Item from");
    }

    public void btnNavigateInventrory(ActionEvent actionEvent) throws IOException {
        ViewLoader.loadNewView(actionEvent, "/lk/ijse/gdse/demo/Inventory-view.fxml", "Inventory from");

    }

    public void btnSupplier(ActionEvent actionEvent) throws IOException {
        ViewLoader.loadNewView(actionEvent, "/lk/ijse/gdse/demo/Supplier-view.fxml", "Supplier from");
    }

    public void btnUnit(ActionEvent actionEvent) throws IOException {
        ViewLoader.loadNewView(actionEvent, "/lk/ijse/gdse/demo/Unit-view.fxml", "Unit from");
    }

    public void btnCategory(ActionEvent actionEvent) throws IOException {
        ViewLoader.loadNewView(actionEvent, "/lk/ijse/gdse/demo/Category-view.fxml", "Category from");
    }

    private void showAlert(HttpResponse<String> response, String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public Unit cmbOnActionUnit(ActionEvent actionEvent) {
        String cmbUnitValue = cmbUnit.getValue();
        return fetchDataForSaveUnit("http://localhost:8080/api/unit/getUnitById/" + cmbUnitValue);
    }

    public Category cmbOnActionCategory(ActionEvent actionEvent) {
        String categoryValue = cmbCategory.getValue();
        return fetchDataForSaveCategory("http://localhost:8080/api/categories/findById/" + categoryValue);
    }
}
