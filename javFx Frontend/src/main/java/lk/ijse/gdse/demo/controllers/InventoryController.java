package lk.ijse.gdse.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.demo.util.Navigation;
import lk.ijse.gdse.demo.util.Routes;

import java.io.IOException;

public class InventoryController {
    @FXML
    private ComboBox<?> cmbApprovalStatus;

    @FXML
    private ComboBox<?> cmbItemId;

    @FXML
    private ComboBox<?> cmbStatus;

    @FXML
    private TableColumn<?, ?> colApproval_status;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<?> tblView;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtQty;

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

    @FXML
    void btnSave(ActionEvent event) {

    }

    @FXML
    void btnSupplier(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.SUPPLIER, pane);
    }

    @FXML
    void btnUnit(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.UNIT, pane);
    }

    @FXML
    void btnUpdate(ActionEvent event) {

    }

}
