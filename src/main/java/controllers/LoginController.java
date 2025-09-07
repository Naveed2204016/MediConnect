package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import db.DBConnection;

import java.io.IOException;
import java.sql.*;

public class LoginController {

    @FXML private TextField userIdField;
    @FXML private PasswordField passwordField;

    private String role;

    public void setRole(String role) {
        this.role = role;
        System.out.println("Role set to: " + role);
    }

    @FXML
    private void handleLogin() {
        String userId = userIdField.getText().trim();
        String password1 = passwordField.getText().trim();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        if(role.equals("patient")) {
            try (Connection connection = DBConnection.getConnection()) {
                String query = "SELECT password FROM Patient WHERE patient_id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, Integer.parseInt(userId));
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String str = resultSet.getString("password");
                    if (str.equals(password1)) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/patient_dashboard.fxml"));
                        Scene scene = new Scene(loader.load());
                        DashboardPatientController dashboardPatientController = loader.getController();
                        dashboardPatientController.setUserID(Integer.parseInt(userId));
                        Stage stage = (Stage) userIdField.getScene().getWindow();
                        stage.setScene(scene);
                    } else {
                        showAlert("Incorrect Password!");
                    }
                } else {
                    showAlert("User id not found!");
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
        else if(role.equals("admin")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin_dashboard.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) userIdField.getScene().getWindow();
                stage.setScene(scene);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        else if(role.equals("doctor")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Doctors_dashboard.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) userIdField.getScene().getWindow();
                stage.setScene(scene);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        else if(role.equals("assistant")) {
            try (Connection connection = DBConnection.getConnection()) {
                String query = "SELECT password FROM assistant WHERE assistant_id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, Integer.parseInt(userId));
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String dbPass = resultSet.getString("password");
                    if (dbPass.equals(password1)) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AssistantDashboard.fxml"));
                        Scene scene = new Scene(loader.load());

                        AssistantDashboardController dashboardAssistantController = loader.getController();
                        dashboardAssistantController.setUserID(Integer.parseInt(userId));
                        dashboardAssistantController.setAssistantId(Integer.parseInt(userId)); // dynamic assistant_id

                        Stage stage = (Stage) userIdField.getScene().getWindow();
                        stage.setScene(scene);
                    } else {
                        showAlert("Incorrect Password!");
                    }
                } else {
                    showAlert("Assistant ID not found!");
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Attempted login for role: " + role);
        System.out.println("User ID: " + userId);
        System.out.println("Password: " + password1);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Login Error");
        alert.setHeaderText("Authentication Failed");
        alert.setContentText(message);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/warning.png")));
        alert.getDialogPane().setStyle("-fx-font-size: 14px; -fx-font-family: 'Segoe UI';");
        alert.showAndWait();
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) userIdField.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
