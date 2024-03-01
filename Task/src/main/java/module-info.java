module lk.ijse.gdse.task {
    requires javafx.controls;
    requires javafx.fxml;

    opens lk.ijse.gdse.task to javafx.fxml;
    exports lk.ijse.gdse.task;
}
