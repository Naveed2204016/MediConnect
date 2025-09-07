package controllers;

import db.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.*;
import java.io.IOException;

public class AdminSignupController {

    @FXML private TextField nameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button backButton;
    private int userID;

    @FXML
    private void handleSignup() {
        String name = nameField.getText().trim();
        String password = passwordField.getText().trim();
        if(name.isEmpty() || password.isEmpty()) {
            showAlert("Please fill in all fields.");
        }
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection connection= DBConnection.getConnection();
            String query = "INSERT INTO Admin (name, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Admin registered successfully.");
                nameField.clear();
                passwordField.clear();
            } else {
                showAlert("Error registering admin. Please try again.");
            }
            try {
                String query2="SELECT admin_id FROM Admin ORDER BY admin_id DESC LIMIT 1";
                PreparedStatement preparedStatement2=connection.prepareStatement(query2);
                ResultSet resultSet=preparedStatement2.executeQuery();
                if(resultSet.next())
                {
                    userID=resultSet.getInt("admin_id");
                }
                else
                {
                    System.out.println("Error fetching admin ID.");
                }
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin_dashboard.fxml"));
                Scene scene = new Scene(loader.load());
                DashboardAdminController dashboardAdminController = loader.getController();
                dashboardAdminController.setUserID(userID);
                Stage stage = (Stage) backButton.getScene().getWindow();
                stage.setScene(scene);
            }catch(IOException e)
            {
                e.printStackTrace();
            }

        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
