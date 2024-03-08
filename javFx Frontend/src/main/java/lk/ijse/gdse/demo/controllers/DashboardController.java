package lk.ijse.gdse.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashboardController {

    public AnchorPane pane2;

    @FXML
    void btnNavigateInventory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/gdse/demo/Inventory-view.fxml"));
            AnchorPane inventoryPane = loader.load();
            pane2.getChildren().setAll(inventoryPane);
            AnchorPane.setLeftAnchor(inventoryPane, (pane2.getWidth() - inventoryPane.getWidth()) / 7);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void btnNavigateItem(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/gdse/demo/Item-view.fxml"));
            AnchorPane inventoryPane = loader.load();
            pane2.getChildren().setAll(inventoryPane);
            AnchorPane.setLeftAnchor(inventoryPane, (pane2.getWidth() - inventoryPane.getWidth()) / 7);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnNavigateCategory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/gdse/demo/Category-view.fxml"));
            AnchorPane inventoryPane = loader.load();
            pane2.getChildren().setAll(inventoryPane);
            AnchorPane.setLeftAnchor(inventoryPane, (pane2.getWidth() - inventoryPane.getWidth()) / 7);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnNavigateSupplier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/gdse/demo/Supplier-view.fxml"));
            AnchorPane inventoryPane = loader.load();
            pane2.getChildren().setAll(inventoryPane);
            AnchorPane.setLeftAnchor(inventoryPane, (pane2.getWidth() - inventoryPane.getWidth()) / 7);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnNavigateUnit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/gdse/demo/Unit-view.fxml"));
            AnchorPane inventoryPane = loader.load();
            pane2.getChildren().setAll(inventoryPane);
            AnchorPane.setLeftAnchor(inventoryPane, (pane2.getWidth() - inventoryPane.getWidth()) / 7);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ... (other methods for navigation to different forms)
}
