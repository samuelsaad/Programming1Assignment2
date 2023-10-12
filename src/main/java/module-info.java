module com.example.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.assignment2 to javafx.fxml;
    exports com.example.assignment2;
    exports com.example.assignment2.controller;
    opens com.example.assignment2.controller to javafx.fxml;
}