module com.example.mediconnect {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.compiler;
    requires jdk.jdi;

    opens controllers to javafx.fxml; // 👈 necessary for FXML to access controller classes
    exports mediconnect; // if you have Main class here
}
