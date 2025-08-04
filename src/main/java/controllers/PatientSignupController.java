package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class PatientSignupController {

    @FXML private TextField nameField;
    @FXML private DatePicker dobPicker;
    @FXML private TextField contactField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button backButton;
    private static final String url="jdbc:mysql://127.0.0.1:3306/mediconnect";
    private static final String username="root";
    private static final String password="backend#8";
    @FXML
    private void handleSignup() {
        String name = nameField.getText().trim();
        LocalDate localDate=dobPicker.getValue();
        Date dob= Date.valueOf(localDate);
        String contact = contactField.getText().trim();
        String email = emailField.getText().trim();
        String password1 = passwordField.getText().trim();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try
        {
            Connection connection= DriverManager.getConnection(url,username,password);
            String query="INSERT INTO patient(name,date_of_birth,contact_number,email,password) VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setDate(2,dob);
            preparedStatement.setString(3,contact);
            preparedStatement.setString(4,email);
            preparedStatement.setString(5,password1);
            int cnt=preparedStatement.executeUpdate();
            if(cnt>0)
            {
                System.out.println("Successfully inserted");
            }
            else
            {
                System.out.println("Error..");
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/patient_dashboard.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) backButton.getScene().getWindow();
                stage.setScene(scene);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        // TEMP: For now, just print the value;

        // TODO: Validate and insert into database
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


}