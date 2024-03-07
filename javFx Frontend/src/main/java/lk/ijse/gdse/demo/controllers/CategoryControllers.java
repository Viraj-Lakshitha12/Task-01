package lk.ijse.gdse.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;
import lk.ijse.gdse.demo.dto.Category;
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

public class CategoryControllers {

    public AnchorPane pane;
    public ComboBox cmbStatus;
    @FXML
    private TableColumn<Category, String> colCode;

    @FXML
    private TableColumn<Category, String> colId;

    @FXML
    private TableColumn<Category, String> colName;

    @FXML
    private TableColumn<Category, String> colStatus;

    @FXML
    private TableView<Category> tblView;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtStatus;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void initialize() {
        setCellValueFactory();
        loadDataAndSetToTable();
        addNumericValidationListener(txtId);
        ObservableList<String> statusList = FXCollections.observableArrayList("Active", "Inactive");
        cmbStatus.setItems(statusList);

        tblView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                handleTableClick();
            }
        });
    }

    private void handleTableClick() {
        Category selectedCategory = tblView.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            txtId.setText(selectedCategory.getId());
            txtCode.setText(selectedCategory.getCode());
            txtName.setText(selectedCategory.getName());
            cmbStatus.setValue(selectedCategory.getStatus());
        }
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
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
    }

    private void loadDataAndSetToTable() {
        List<Category> categoryList = fetchDataFromBackend();
        updateTableView(categoryList);
    }

    private List<Category> fetchDataFromBackend() {
        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/categories"))
                    .header("Authorization",jwtToken)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            Category[] categories = objectMapper.readValue(response.body(), Category[].class);
            return Arrays.asList(categories);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void updateTableView(List<Category> categoryList) {
        ObservableList<Category> observableList = FXCollections.observableArrayList(categoryList);
        tblView.setItems(observableList);
    }

    //delete category
    @FXML
    void btnDelete(ActionEvent event) {
        if (txtId.getText().isEmpty()) {
            showAlert(null, "Error", "Enter Id");
            return;
        }
        String idText = txtId.getText();

        try {
            if (idText.isEmpty()) {
                showAlert(null, "Error", "Please enter a valid category ID for deletion.");
                return;
            }
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/categories/" + idText))
                    .header("Content-Type", "application/json")
                    .header("Authorization",jwtToken)
                    .DELETE()
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


    // Update category
    @FXML
    void btnUpdate(ActionEvent event) {
        if (txtId.getText().isEmpty() || txtCode.getText().isEmpty() || txtName.getText().isEmpty()) {
            showAlert(null, "Error", "Enter All Details");
            return;
        }

        loadDataAndSetToTable();
        Category categoryDTO = new Category(
                txtId.getText(),
                txtCode.getText(),
                txtName.getText(),
                cmbStatus.getValue().toString()
        );
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/categories"))
                    .header("Content-Type", "application/json")
                    .header("Authorization",jwtToken)
                    .PUT(HttpRequest.BodyPublishers.ofString(categoryDTOToJson(categoryDTO)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            showAlert(response, "update Successful", "Category has been successfully updated.");
            loadDataAndSetToTable();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateControl(Control control) {
        if (control instanceof TextInputControl) {
            return validateTextInputControl((TextInputControl) control);
        }
        return false;
    }

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

    private void setInvalidStyle(Control control) {
        control.setStyle("-fx-border-color: red;");
    }

    private void setValidStyle(Control control) {
        control.setStyle("-fx-border-color: green;");
    }

    private boolean isInputValid() {
        boolean isIdValid = validateControl(txtId);
        boolean isCodeValid = validateControl(txtCode);
        boolean isNameValid = validateControl(txtName);

        if (!isIdValid) {
            setInvalidStyle(txtId);
        }

        if (!isCodeValid) {
            setInvalidStyle(txtCode);
        }

        if (!isNameValid) {
            setInvalidStyle(txtName);
        }


        return isIdValid && isCodeValid && isNameValid;
    }

    // Save category
    @FXML
    void btnSave(ActionEvent event) {
        if (!isInputValid()) {
            return;
        }

        if (txtCode.getText().isEmpty() || txtName.getText().isEmpty()) {
            showAlert(null, "Error", "Enter All Details");
            return;
        }

        loadDataAndSetToTable();
        // Create the Category object
        Category categoryDTO = new Category(
                null,
                txtCode.getText(),
                txtName.getText(),
                cmbStatus.getValue().toString()
        );
        System.out.println(categoryDTO);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/categories"))
                    .header("Content-Type", "application/json")
                    .header("Authorization",jwtToken)
                    .POST(HttpRequest.BodyPublishers.ofString(categoryDTOToJson(categoryDTO)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            showAlert(response, "Save Successful", "Category has been successfully saved.");
            loadDataAndSetToTable();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private String categoryDTOToJson(Category categoryDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(categoryDTO);
        } catch (Exception e) {
            return "{}";
        }
    }

    private void showAlert(HttpResponse<String> response, String title, String contentText) {
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
