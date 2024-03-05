package lk.ijse.gdse.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;
import lk.ijse.gdse.demo.dto.Inventory;
import lk.ijse.gdse.demo.dto.Item;
import lk.ijse.gdse.demo.util.Navigation;
import lk.ijse.gdse.demo.util.Routes;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static lk.ijse.gdse.demo.util.ConnectToBackend.connectBackend;

public class InventoryController {

    public DatePicker datePicker;
    public TableColumn colViewItemId;
    @FXML
    private ComboBox<String> cmbApprovalStatus;

    @FXML
    private ComboBox<String> cmbItemId;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TableColumn<?, ?> colApproval_status;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colItemStatus;

    @FXML
    private TableColumn<?, ?> colItemUnit;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<Inventory> tblView;

    @FXML
    private TableView<Item> tblViewItems;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtQty;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    public void initialize() {

        addValidationListener(txtId);
        addNumericValidationListener(txtQty);
        tblView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                handleTableClick();
            }
        });
        ObservableList<String> statusList = FXCollections.observableArrayList("Active", "Inactive");
        cmbStatus.setItems(statusList);
        ObservableList<String> list = FXCollections.observableArrayList("Pending", "Approved");
        cmbApprovalStatus.setItems(list);
        setCellValueFactory();
        cmbItemId.setItems(fetchDataForComboBox("http://localhost:8080/api/items/getIds"));
        loadDataAndSetToTable();
    }

    private void handleTableClick() {
        // Get the selected inventory item from the table
        Inventory selectedInventory = tblView.getSelectionModel().getSelectedItem();

        if (selectedInventory != null) {
            txtId.setText(String.valueOf(selectedInventory.getId()));
            datePicker.setValue(selectedInventory.getReceivedDate());
            txtQty.setText(String.valueOf(selectedInventory.getReceivedQty()));
            cmbApprovalStatus.setValue(selectedInventory.getApprovalStatus());
            cmbStatus.setValue(selectedInventory.getStatus());

            String itemId = String.valueOf(selectedInventory.getItem().getId());
            cmbItemId.setValue(itemId);


        }
    }

    private void addValidationListener(TextField textField) {
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if (change.isAdded()) {
                if (!change.getText().matches("\\d*")) {
                    return null; // Reject non-numeric input
                }
            }
            return change;
        });

        textField.setTextFormatter(textFormatter);
    }

    private void addNumericValidationListener(TextField textField) {
        TextFormatter<Integer> textFormatter = new TextFormatter<>(
                new IntegerStringConverter(), 0, c -> {
            if (c.getControlNewText().matches("\\d*")) {
                return c;
            } else {
                return null;
            }
        });
        textField.setTextFormatter(textFormatter);
    }
    private void setCellValueFactory() {
        colViewItemId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colItemUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colItemStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colItemId.setCellValueFactory(new PropertyValueFactory<>("item"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("receivedDate"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("receivedQty"));
        colApproval_status.setCellValueFactory(new PropertyValueFactory<>("approvalStatus"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


    }

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


    @FXML
    void btnCategory(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.CATEGORY, pane);
    }

    @FXML
    void btnDelete(ActionEvent event) {
    }

    @FXML
    void btnNavigateInventrory(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.INVENTORY, pane);
    }

    @FXML
    void btnNavigationItem(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.ITEM, pane);
    }


//    save inventory
    @FXML
    void btnSave(ActionEvent event) {
        if (!isInputValid()) {
            return;
        }

        Item item = cmbOnActionItemId(event);
        Long id = Long.parseLong(txtId.getText());
        LocalDate receivedDate = datePicker.getValue();
        int receivedQty = Integer.parseInt(txtQty.getText());
        String approvalStatus = cmbApprovalStatus.getValue();
        String status = cmbStatus.getValue();

        Inventory inventory = new Inventory(id, receivedDate, receivedQty, approvalStatus, status, item);

        String inventoryJson = null;
        try {
            inventoryJson = objectMapper.writeValueAsString(inventory);
            HttpResponse<String> response = connectBackend("http://localhost:8080/api/inventory", "POST", inventoryJson);
            if (response.statusCode() == 200) {
                showAlert(response, "Save Successful", "Inventory has been successfully Saved.");
            }
            loadDataAndSetToTable();
        } catch (IOException e) {
            System.out.println("Error converting Item to JSON: " + e.getMessage());
        }
    }

    @FXML
    void btnSupplier(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.SUPPLIER,pane);
    }

    @FXML
    void btnUnit(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.UNIT,pane);
    }

    //update inventory
    @FXML
    void btnUpdate(ActionEvent event) {
        if (!isInputValid()) {
            return;
        }

        Item item = cmbOnActionItemId(event);
        Long id = Long.parseLong(txtId.getText());
        LocalDate receivedDate = datePicker.getValue();
        int receivedQty = Integer.parseInt(txtQty.getText());
        String approvalStatus = cmbApprovalStatus.getValue();
        String status = cmbStatus.getValue();

        Inventory inventory = new Inventory(id, receivedDate, receivedQty, approvalStatus, status, item);

        String inventoryJson = null;
        try {
            inventoryJson = objectMapper.writeValueAsString(inventory);
            HttpResponse<String> response = connectBackend("http://localhost:8080/api/inventory", "PUT", inventoryJson);
            if (response.statusCode() == 200) {
                showAlert(response, "Update Successful", "Inventory has been successfully Updated.");
            }
            loadDataAndSetToTable();
        } catch (IOException e) {
            System.out.println("Error converting Inventory to JSON: " + e.getMessage());
        }
    }

    private boolean isInputValid() {
        return validateControl(txtId) &&
                validateControl(txtQty) &&
                validateControl(cmbApprovalStatus) &&
                validateControl(cmbStatus) &&
                validateControl(datePicker);
    }

//    validation
    private boolean validateControl(Control control) {
        if (control instanceof TextInputControl) {
            return validateTextInputControl((TextInputControl) control);
        } else if (control instanceof ComboBox<?>) {
            return validateComboBox((ComboBox<?>) control);
        } else if (control instanceof DatePicker) {
            return validateDatePicker((DatePicker) control);
        }
        return false;
    }

//    validate txt inputs
    private boolean validateTextInputControl(TextInputControl textInputControl) {
        String text = textInputControl.getText().trim();
        if (text.isEmpty()) {
            setInvalidStyle(textInputControl);
            return false;
        } else {
            setValidStyle(textInputControl);
            return true;
        }
    }

//    validate combobox
    private boolean validateComboBox(ComboBox<?> comboBox) {
        Object value = comboBox.getValue();
        if (value == null || value.toString().trim().isEmpty()) {
            setInvalidStyle(comboBox);
            return false;
        } else {
            setValidStyle(comboBox);
            return true;
        }
    }

//    validate date picker
    private boolean validateDatePicker(DatePicker datePicker) {
        LocalDate value = datePicker.getValue();
        if (value == null) {
            setInvalidStyle(datePicker);
            return false;
        } else {
            setValidStyle(datePicker);
            return true;
        }
    }

    private void setInvalidStyle(Control control) {
        control.setStyle("-fx-border-color: red;");
    }

    private void setValidStyle(Control control) {
        control.setStyle("-fx-border-color: green;");
    }

//    load all data for the table
    private void loadDataAndSetToTable() {
        List<Inventory> itemList = fetchDataFromBackend();
        updateTableView(itemList);
    }

//    update table
    private void updateTableView(List<Inventory> itemList) {
        ObservableList<Inventory> observableList = FXCollections.observableArrayList(itemList);
        tblView.setItems(observableList);
    }

    private List<Inventory> fetchDataFromBackend() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/inventory"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());  // Registering the JavaTimeModule

            Inventory[] inventories = objectMapper.readValue(response.body(), Inventory[].class);
            return Arrays.asList(inventories);

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    private Item fetchItemData(String url) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            Item data = objectMapper.readValue(response.body(), Item.class);
            ObservableList<Item> itemList = FXCollections.observableArrayList(data);

            tblViewItems.setItems(itemList);
            return data;

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        return null;
    }

    public Item cmbOnActionItemId(ActionEvent actionEvent) {
        String cmbUnitValue = cmbItemId.getValue();
        return fetchItemData("http://localhost:8080/api/items/findById/" + cmbUnitValue);
    }

    private void showAlert(HttpResponse<String> response, String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}
