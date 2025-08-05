package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.DiagnosticTest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookDiagnosticTestController {

    @FXML private TextField testNameField;
    @FXML private TextField hospitalField;
    @FXML private TableView<DiagnosticTest> testsTable;
    @FXML private TableColumn<DiagnosticTest, String> nameCol;
    @FXML private TableColumn<DiagnosticTest, String> hospitalCol;
    @FXML private TableColumn<DiagnosticTest, Double> feeCol;
    @FXML private ListView<String> selectedTestsList;
    @FXML private Label totalLabel;
    @FXML private DatePicker testDatePicker;
    @FXML private Button paymentButton;

    private List<DiagnosticTest> selectedTests = new ArrayList<>();
    private double totalAmount = 0.0;
    private ObservableList<DiagnosticTest> testData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up date picker
        testDatePicker.setValue(LocalDate.now().plusDays(1));

        // Configure table columns
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        hospitalCol.setCellValueFactory(cellData -> cellData.getValue().hospitalProperty());
        feeCol.setCellValueFactory(cellData -> cellData.getValue().feeProperty().asObject());

        // Add action column with "Add" buttons
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

    @FXML
    private void handleSearch() {
        String testName = testNameField.getText().trim();
        String hospital = hospitalField.getText().trim();

        if (testName.isEmpty()) {
            showAlert("Input Required", "Please enter at least test name");
            return;
        }

        // Simulate database search (replace with actual DB call)
        testData.clear();
        testData.addAll(
                new DiagnosticTest(1, "Blood Test", "City Hospital", 25.0),
                new DiagnosticTest(2, "X-Ray", "General Clinic", 50.0),
                new DiagnosticTest(3, "MRI Scan", "City Hospital", 200.0)
        );
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
        if (testDatePicker.getValue() == null ||
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
        ));

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