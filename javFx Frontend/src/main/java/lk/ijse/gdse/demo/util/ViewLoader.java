package lk.ijse.gdse.demo.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewLoader {
    public static void loadNewView(ActionEvent event, String url, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewLoader.class.getResource(url));
            Parent window = loader.load();

            // Get the stage of the current view
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Open the new view
            Stage newStage = new Stage();
            newStage.setTitle(title);
            Scene scene = new Scene(window);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
