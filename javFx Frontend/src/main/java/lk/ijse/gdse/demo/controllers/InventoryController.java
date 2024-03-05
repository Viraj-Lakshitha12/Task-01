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

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class InventoryController {

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
    private TableColumn<?, ?> colItemCategory;

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
    private TableView<?> tblView;

    @FXML
    private TableView<Item> tblViewItems;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtQty;

    public void initialize(){
        ObservableList<String> statusList = FXCollections.observableArrayList("Active", "Inactive");
        cmbStatus.setItems(statusList);
        ObservableList<String> list = FXCollections.observableArrayList("Pending", "Approved");
        cmbApprovalStatus.setItems(list);
        setCellValueFactory();
        cmbItemId.setItems(fetchDataForComboBox("http://localhost:8080/api/items/getIds"));
    }
    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colItemUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colItemStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
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
    void btnCategory(ActionEvent event) {

    }

    @FXML
    void btnDelete(ActionEvent event) {

    }

    @FXML
    void btnNavigateInventrory(ActionEvent event) {

    }

    @FXML
    void btnNavigationItem(ActionEvent event) {

    }

    @FXML
    void btnSave(ActionEvent event) {

    }

    @FXML
    void btnSupplier(ActionEvent event) {

    }

    @FXML
    void btnUnit(ActionEvent event) {

    }

    @FXML
    void btnUpdate(ActionEvent event) {

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
        return fetchItemData("http://localhost:8080/api/unit/getUnitById/" + cmbUnitValue);
    }
}
