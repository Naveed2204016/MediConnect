module com.example.mediconnect {
    requires javafx.controls;
    requires javafx.fxml;

    opens controllers to javafx.fxml; // 👈 necessary for FXML to access controller classes
    exports mediconnect; // if you have Main class here
}
