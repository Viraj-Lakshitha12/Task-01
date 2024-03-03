package lk.ijse.gdse.demo.util;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Navigation {
    private static AnchorPane pane;

    public static void navigate(Routes route, AnchorPane pane) throws IOException {
        Navigation.pane = pane;
        Navigation.pane.getChildren().clear();
        Stage window = (Stage) Navigation.pane.getScene().getWindow();

        switch (route) {
            case UNIT:
                window.setTitle("Supplier Manage Form");
                initUI("Unit-view.fxml");
                break;
            case SUPPLIER:
                window.setTitle("Supplier Manage Form");
                initUI("Supplier-view.fxml");
                break;
            case CATEGORY:
                window.setTitle("Supplier Manage Form");
                initUI("Category-view.fxml");
                break;
            default:
                new Alert(Alert.AlertType.ERROR, "Not suitable UI found!").show();
        }
    }

    private static void initUI(String location) throws IOException {
        Navigation.pane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(Navigation.class
                .getResource("/lk/ijse/gdse/demo/" + location))));

    }
}
