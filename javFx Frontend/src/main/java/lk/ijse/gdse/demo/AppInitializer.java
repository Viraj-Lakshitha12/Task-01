package lk.ijse.gdse.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL resource = this.getClass().getResource("Category-view.fxml");
        Parent window = FXMLLoader.load(resource);
        Scene scene = new Scene(window);
        stage.setScene(scene);
        stage.setTitle("Category Form");
        stage.centerOnScreen();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
