package controllers;

//import com.sun.jdi.connect.spi.Connection;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.DiagnosticTest;
import models.Doctor;
import models.Test;
import java.io.IOException;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminLoadTestController {

    @FXML
    private TableView<DiagnosticTest> testsTable;
    @FXML
    private TableColumn<DiagnosticTest, String> nameCol;
    @FXML
    private TableColumn<DiagnosticTest, String> hospitalCol;
    @FXML
    private TableColumn<DiagnosticTest, Double> feeCol;
    @FXML
    private TableColumn<DiagnosticTest, String> timeslotcol;
    @FXML
    private TableColumn<DiagnosticTest, String> instructioncol;
    @FXML
    private TextField testnamefield;
    @FXML
    private TextField hospitalnamefield;
    @FXML
    private TextField testNameField1;
    @FXML
    private TextField hospitalField1;
    @FXML
    private TextField feeField;
    @FXML
    private TextField timeSlotField;
    @FXML
    private TextArea instructionArea;
    private ObservableList<DiagnosticTest> testData = FXCollections.observableArrayList();
    private Connection conn;
    private int userID;

    public void setUserId(int userID) {
        this.userID = userID;
        loadtests();
    }

    @FXML
    private void handleadd(ActionEvent e1) {
        String name = testNameField1.getText().trim();
        String hospital_name = hospitalField1.getText().trim();
        Double fee = Double.parseDouble(feeField.getText().trim());
        String instructions = instructionArea.getText().trim();
        String input = timeSlotField.getText().trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime localTime = LocalTime.parse(input, formatter);

        Time test_time_slot = Time.valueOf(localTime);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection connection = DBConnection.getConnection();
            String query = "INSERT INTO test(name,hospital_name,fee,instructions,test_time_slot) VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, hospital_name);
            preparedStatement.setDouble(3, fee);
            preparedStatement.setString(4, instructions);
            preparedStatement.setTime(5, test_time_slot);
            int cnt = preparedStatement.executeUpdate();
            if (cnt > 0) {
                System.out.println("Successfully inserted");
            } else {
                showalert("Couldn't insert");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    public void initialize() {

        nameCol.setCellValueFactory(Data -> new javafx.beans.property.SimpleStringProperty(Data.getValue().getName()));
        hospitalCol.setCellValueFactory(Data -> new javafx.beans.property.SimpleStringProperty(Data.getValue().getHospital_name()));
        feeCol.setCellValueFactory(Data -> new javafx.beans.property.SimpleDoubleProperty(Data.getValue().getFee()).asObject());
        timeslotcol.setCellValueFactory(data -> {
            java.sql.Time time = data.getValue().getTest_time_slot(); // assuming returns TIME
            String formattedTime = (time != null)
                    ? time.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"))
                    : "";
            return new javafx.beans.property.SimpleStringProperty(formattedTime);
        });
        instructioncol.setCellValueFactory(data -> {
            String instruction = data.getValue().getInstruction();
            return new javafx.beans.property.SimpleStringProperty(instruction != null ? instruction : "N/A");
        });

        testsTable.setEditable(true);

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        hospitalCol.setCellFactory(TextFieldTableCell.forTableColumn());
        feeCol.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.DoubleStringConverter()));
        timeslotcol.setCellFactory(TextFieldTableCell.forTableColumn());
        instructioncol.setCellFactory(TextFieldTableCell.forTableColumn());

        nameCol.setOnEditCommit(event -> {
            DiagnosticTest test = event.getRowValue();
            test.setName(event.getNewValue());
        });

        hospitalCol.setOnEditCommit(event -> {
            DiagnosticTest test = event.getRowValue();
            test.setHospital_name(event.getNewValue());
        });

        feeCol.setOnEditCommit(event -> {
            DiagnosticTest test = event.getRowValue();
            test.setFee(event.getNewValue());
        });

        timeslotcol.setOnEditCommit(event -> {
            DiagnosticTest test = event.getRowValue();
            test.setTest_time_slot(Time.valueOf(LocalTime.parse(event.getNewValue(), DateTimeFormatter.ofPattern("hh:mm a"))));
        });

        instructioncol.setOnEditCommit(event -> {
            DiagnosticTest test = event.getRowValue();
            test.setInstruction(event.getNewValue());
        });


        testsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        TableColumn<DiagnosticTest, Void> actionCol = new TableColumn<>("Action");
        actionCol.setPrefWidth(100);
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button addButton = new Button("Remove");

            {
                addButton.setOnAction(event -> {
                    DiagnosticTest test = getTableView().getItems().get(getIndex());
                    removetest(test);
                });
                addButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(addButton);
                }
            }
        });

        testsTable.getColumns().add(actionCol);
        testsTable.setItems(testData);
    }

    public void loadtests() {
        testData.clear();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DBConnection.getConnection(); //DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM Test LIMIT 5";
            PreparedStatement pmt = connection.prepareStatement(query);
            ResultSet resultSet = pmt.executeQuery();
            while (resultSet.next()) {
                int testId = resultSet.getInt("test_id");
                String name = resultSet.getString("name");
                String hospitalName = resultSet.getString("hospital_name");
                double fee = resultSet.getDouble("fee");
                java.sql.Time timeSlot = resultSet.getTime("test_time_slot");
                String instruction = resultSet.getString("instructions");

                DiagnosticTest test = new DiagnosticTest(testId, name, hospitalName, fee, instruction, timeSlot);
                testData.add(test);
            }

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        testsTable.setItems(testData);
    }

    public void handleSearch() {
        String testName = testnamefield.getText().trim();
        String hospital = hospitalnamefield.getText().trim();

        if (testName.isEmpty()) {
            showalert("Input Required,Please enter at least test name");
            return;
        }
        testData.clear();
        if (hospital.isEmpty()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DBConnection.getConnection();
                String query = "SELECT * FROM Test WHERE LOWER(name)=?";
                PreparedStatement pmt = connection.prepareStatement(query);
                pmt.setString(1, testName);
                ResultSet resultSet = pmt.executeQuery();
                while (resultSet.next()) {
                    int testId = resultSet.getInt("test_id");
                    String name = resultSet.getString("name");
                    String hospitalName = resultSet.getString("hospital_name");
                    double fee = resultSet.getDouble("fee");
                    java.sql.Time timeSlot = resultSet.getTime("test_time_slot");
                    String instruction = resultSet.getString("instructions");

                    DiagnosticTest test = new DiagnosticTest(testId, name, hospitalName, fee, instruction, timeSlot);
                    testData.add(test);
                }

            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DBConnection.getConnection(); //DriverManager.getConnection(url, username, password);
                String query = "SELECT * FROM Test WHERE LOWER(name)=? and hospital_name=?";
                PreparedStatement pmt = connection.prepareStatement(query);
                pmt.setString(1, testName);
                pmt.setString(2, hospital);
                ResultSet resultSet = pmt.executeQuery();
                while (resultSet.next()) {
                    int testId = resultSet.getInt("test_id");
                    String name = resultSet.getString("name");
                    String hospitalName = resultSet.getString("hospital_name");
                    double fee = resultSet.getDouble("fee");
                    java.sql.Time timeSlot = resultSet.getTime("test_time_slot");
                    String instruction = resultSet.getString("instructions");

                    DiagnosticTest test = new DiagnosticTest(testId, name, hospitalName, fee, instruction, timeSlot);
                    testData.add(test);
                }

            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        testsTable.setItems(testData);
    }


    private void removetest(DiagnosticTest test) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete?");
        alert.setContentText("Delete \"" + test.getName() + "\" from \"" + test.getHospital_name() + "\" hospital?");

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    Connection connection = DBConnection.getConnection();
                    String query = "DELETE FROM Test WHERE test_id = ?";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, test.getTestid());
                    int affected = ps.executeUpdate();

                    if (affected > 0) {
                        testData.remove(test);
                        System.out.println("Test removed successfully.");
                    } else {
                        showalert("Could not delete test from database.");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    showalert("Error: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    private void handleUpdateSelected(ActionEvent event) {
        ObservableList<DiagnosticTest> selectedTests = testsTable.getSelectionModel().getSelectedItems();

        if (selectedTests.isEmpty()) {
            showalert("Please select at least one test to update.");
            return;
        }

        try {
            Connection connection = DBConnection.getConnection();
            String query = "UPDATE Test SET name=?, hospital_name=?, fee=?, test_time_slot=?, instructions=? WHERE test_id=?";
            PreparedStatement ps = connection.prepareStatement(query);

            for (DiagnosticTest test : selectedTests) {
                ps.setString(1, test.getName());
                ps.setString(2, test.getHospital_name());
                ps.setDouble(3, test.getFee());
                ps.setTime(4, test.getTest_time_slot());
                ps.setString(5, test.getInstruction());
                ps.setInt(6, test.getTestid());
                ps.addBatch();
            }

            int[] updated = ps.executeBatch();
            showalert(updated.length + " test(s) updated successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            showalert("Error updating tests: " + e.getMessage());
        }
    }



    public void showalert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


