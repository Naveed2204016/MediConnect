package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.DiagnosticTest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class BookDiagnosticTestController {

    @FXML private TextField testNameField;
    @FXML private TextField hospitalField;
    @FXML private TableView<DiagnosticTest> testsTable;
    @FXML private TableColumn<DiagnosticTest, String> nameCol;
    @FXML private TableColumn<DiagnosticTest, String> hospitalCol;
    @FXML private TableColumn<DiagnosticTest, Double> feeCol;
    @FXML private TableColumn<DiagnosticTest,String> timeslotcol;
    @FXML private TableColumn<DiagnosticTest,String> instructioncol;
    @FXML private ListView<String> selectedTestsList;
    @FXML private Label totalLabel;
    @FXML private DatePicker testDatePicker;
    @FXML private Button paymentButton;
    private static final String url="jdbc:mysql://127.0.0.1:3306/mediconnect";
    private static final String username="root";
    private static final String password="backend#8";

    private List<DiagnosticTest> selectedTests = new ArrayList<>();
    private double totalAmount = 0.0;
    private ObservableList<DiagnosticTest> testData = FXCollections.observableArrayList();
    private int userId;

    @FXML
    public void initialize() {

        testDatePicker.setValue(LocalDate.now().plusDays(1));

        // Configure table columns
        nameCol.setCellValueFactory(Data->new javafx.beans.property.SimpleStringProperty(Data.getValue().getName()));
        hospitalCol.setCellValueFactory(Data->new javafx.beans.property.SimpleStringProperty(Data.getValue().getHospital_name()));
        feeCol.setCellValueFactory(Data->new javafx.beans.property.SimpleDoubleProperty(Data.getValue().getFee()).asObject());
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

        testsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        TableColumn<DiagnosticTest, Void> actionCol = new TableColumn<>("Action");
        actionCol.setPrefWidth(100);
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button addButton = new Button("Add");

            {
                addButton.setOnAction(event -> {
                    DiagnosticTest test = getTableView().getItems().get(getIndex());
                    addTestToSelection(test);
                });
                addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
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
    public void setUserId(int userId) {
        this.userId = userId;
        loadtests();
    }

    public void loadtests(){
        testData.clear();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
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

                DiagnosticTest test = new DiagnosticTest(testId, name, hospitalName, fee,instruction,timeSlot);
                testData.add(test);
            }

            }catch(ClassNotFoundException e)
            {
                System.out.println(e.getMessage());
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
        testsTable.setItems(testData);
    }
    @FXML
    private void handleSearch() {
        String testName = testNameField.getText().trim();
        String hospital = hospitalField.getText().trim();

        if (testName.isEmpty() || hospital.isEmpty()) {
            showAlert("Input Required", "Please enter at least test name and hospital name");
            return;
        }

        testData.clear();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM Test WHERE name=? and hospital_name=?";
            PreparedStatement pmt = connection.prepareStatement(query);
            pmt.setString(1, testName);
            pmt.setString(2,hospital);
            ResultSet resultSet = pmt.executeQuery();
            while (resultSet.next()) {
                int testId = resultSet.getInt("test_id");
                String name = resultSet.getString("name");
                String hospitalName = resultSet.getString("hospital_name");
                double fee = resultSet.getDouble("fee");
                java.sql.Time timeSlot = resultSet.getTime("test_time_slot");
                String instruction = resultSet.getString("instructions");

                DiagnosticTest test = new DiagnosticTest(testId, name, hospitalName, fee,instruction,timeSlot);
                testData.add(test);
            }

        }catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        testsTable.setItems(testData);
    }

    private void addTestToSelection(DiagnosticTest test) {
        if (!selectedTests.contains(test)) {
            selectedTests.add(test);
            selectedTestsList.getItems().add(test.getName() + " - $" + test.getFee());
            totalAmount += test.getFee();
            updatePaymentButton();
        }
    }

    @FXML
    private void handlePayment() {
        // Validate date
       /* if (testDatePicker.getValue() == null ||
                testDatePicker.getValue().isBefore(LocalDate.now())) {
            showAlert("Invalid Date", "Please select today or a future date");
            return;
        }

        // Validate selection
        if (selectedTests.isEmpty()) {
            showAlert("No Tests Selected", "Please select at least one test");
            return;
        }

        // Process payment (simulated)
        showAlert("Success", String.format(
                "Booked %d tests for %s\nTotal: $%.2f",
                selectedTests.size(),
                testDatePicker.getValue().toString(),
                totalAmount
        ));*/

        resetForm();
    }

    private void updatePaymentButton() {
        paymentButton.setText(String.format("Proceed to Payment ($%.2f)", totalAmount));
        totalLabel.setText(String.format("Total: $%.2f", totalAmount));
    }

    private void resetForm() {
        selectedTests.clear();
        selectedTestsList.getItems().clear();
        totalAmount = 0.0;
        updatePaymentButton();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}