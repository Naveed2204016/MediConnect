package controllers;

//import com.sun.jdi.connect.spi.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Doctor;
import models.Test;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminLoadTestController {

    @FXML private TableView<Test> tableView;
    @FXML private TableColumn<Test, String> colTestName;
    @FXML private TableColumn<Test, String> colHospital;
    @FXML private TableColumn<Test, String> colFee;
    @FXML private TableColumn<Test, String> colTimeSlot;
    @FXML private TableColumn<Test, String> colInstructions;
    @FXML private TextField testnamefield;
    @FXML private TextField hospitalnamefield;
    private static final String url="jdbc:mysql://127.0.0.1:3306/mediconnect";
    private static final String username="root";
    private static final String password="backend#8";

    private Connection conn;

    @FXML
    private void handleadd(ActionEvent e) {

    }

    private ObservableList<Test> sampletests = FXCollections.observableArrayList(
            new Test(1, "Blood Test", "Dhanmondi", "600", "Mon-Fri 9AM-5PM","Fasting not required"),
            new Test(2, "ECG", "Gulshan", "200", "Mon-Fri 9AM-5PM","Stay calm before test")
    );
    @FXML
    public void initialize() {
        connectToDatabase();

        colTestName.setCellValueFactory(cellData -> cellData.getValue().getTestNameproperty());
        colHospital.setCellValueFactory(cellData -> cellData.getValue().getHospitalproperty());
        colFee.setCellValueFactory(cellData -> cellData.getValue().getFeeproperty());
        colTimeSlot.setCellValueFactory(cellData -> cellData.getValue().getTimeSlotproperty());
        colInstructions.setCellValueFactory(cellData -> cellData.getValue().getInstructionsproperty());


        tableView.setItems(sampletests);
        makeTableEditable();
        //loadAllTests();
        }


        private void makeTableEditable() {
            tableView.setEditable(true);

            colTestName.setCellFactory(TextFieldTableCell.forTableColumn());
            colHospital.setCellFactory(TextFieldTableCell.forTableColumn());
            colFee.setCellFactory(TextFieldTableCell.forTableColumn());
            colTimeSlot.setCellFactory(TextFieldTableCell.forTableColumn());
            colInstructions.setCellFactory(TextFieldTableCell.forTableColumn());

            colTestName.setOnEditCommit(e -> updateColumn(e, "test_name"));
            colHospital.setOnEditCommit(e -> updateColumn(e, "hospital"));
            colFee.setOnEditCommit(e -> updateColumn(e, "fee"));
            colTimeSlot.setOnEditCommit(e -> updateColumn(e, "time_slot"));
            colInstructions.setOnEditCommit(e -> updateColumn(e, "instructions"));
        }

        private <T> void updateColumn(TableColumn.CellEditEvent<Test, T> e, String columnName) {
            Test test = e.getRowValue();
            T newValue = e.getNewValue();
            try {
                String sql = "UPDATE tests SET " + columnName + " = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, newValue.toString());
                stmt.setInt(2, test.getId());
                stmt.executeUpdate();
                stmt.close();

                // Update in the object
                switch (columnName) {
                    case "test_name" -> test.setTestName(newValue.toString());
                    case "hospital" -> test.setHospital(newValue.toString());
                    case "fee" -> test.setFee(newValue.toString());
                    case "time_slot" -> test.setTimeSlot(newValue.toString());
                    case "instructions" -> test.setInstructions(newValue.toString());
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        @FXML
        private void onSearch() {
            String test = testnamefield.getText();
            String hospital=hospitalnamefield.getText();
            List<Test> results = searchTestsFromDatabase(test,hospital);
            tableView.setItems(FXCollections.observableArrayList(results));
        }

        @FXML
        private void onRemoveSelected() {
            Test selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                deleteTestFromDatabase(selected.getId());
                tableView.getItems().remove(selected);
            }
        }

        @FXML
        private void onUpdateSelected() {
            Test selected = tableView.getSelectionModel().getSelectedItem();
            System.out.println(selected.getFee());
            if (selected != null) {
                updateTestInDatabase(selected);
            }
        }

        private void loadAllTests() {
            tableView.setItems(FXCollections.observableArrayList(searchTestsFromDatabase("","")));
        }

        private List<Test> searchTestsFromDatabase(String test,String hospital) {
            List<Test> list = new ArrayList<>();
            try {
                String sql = "SELECT * FROM tests WHERE test_name LIKE ? OR hospital LIKE ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, "%" + test + "%");
                stmt.setString(2, "%" + hospital + "%");
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    list.add(new Test(
                            rs.getInt("id"),
                            rs.getString("test_name"),
                            rs.getString("hospital"),
                            rs.getString("fee"),
                            rs.getString("time_slot"),
                            rs.getString("instructions")
                    ));
                }
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }

        private void deleteTestFromDatabase(int id) {
            try {
                String sql = "DELETE FROM tests WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void updateTestInDatabase(Test test) {
            try {
                String sql = "UPDATE tests SET test_name=?, hospital=?, fee=?, time_slot=?, instructions=? WHERE id=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, test.getTestName());
                stmt.setString(2, test.getHospital());
                stmt.setString(3, test.getFee());
                stmt.setString(4, test.getTimeSlot());
                stmt.setString(5, test.getInstructions());
                stmt.setInt(6, test.getId());
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void connectToDatabase() {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        try {
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
                e.printStackTrace();
        }
        }


}
