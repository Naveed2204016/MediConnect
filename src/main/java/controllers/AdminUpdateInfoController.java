package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.admin;
import db.DBConnection;

import java.sql.*;

public class AdminUpdateInfoController {

    private int userID;
    @FXML
    TextField nameField;
    @FXML
    TextField passwordField;
    @FXML
    TextField confirmPasswordField;

    private admin ad=new admin();

    public void setUserId(int userID) {
        this.userID = userID;
        System.out.println("yo");
        loaddata();
    }

    public void loaddata()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DBConnection.getConnection();
            String query="SELECT admin_id,name,password from admin where admin_id=?";
            PreparedStatement pmt=connection.prepareStatement(query);
            pmt.setInt(1,userID);
            ResultSet rs=pmt.executeQuery();
            if(rs.next())
            {
                ad.setadmin_id(rs.getInt("admin_id"));
                ad.setname(rs.getString("name"));
                ad.setpassword(rs.getString("password"));
            }
            else
            {
                System.out.println("Admin not found");
            }
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        nameField.setText(ad.getname());
    }

    @FXML
    private void handleSave()
    {
        if(nameField.getText().isEmpty())
        {
            showalert("Missing Information..");
            return;
        }

        if(!passwordField.getText().equals(confirmPasswordField.getText()))
        {
            showalert("Passwords don't match..");
            return;
        }

        ad.setname(nameField.getText());
        if(!passwordField.getText().isEmpty())
        {
            ad.setpassword(passwordField.getText());
        }
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DBConnection.getConnection();
            String query="update admin set name=?,password=? where admin_id=?";
            PreparedStatement pmt=connection.prepareStatement(query);
            pmt.setString(1, ad.getname());
            pmt.setString(2,ad.getPassword());
            pmt.setInt(3,userID);
            int rs=pmt.executeUpdate();
            if(rs>0)
            {
                System.out.println("Information updated successfully");
                showalert1("Information updated successfully!");
            }
            else
            {
                System.out.println("Error..");
            }
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleCancel()
    {
        nameField.setText(ad.getname());
        passwordField.clear();
        confirmPasswordField.clear();
    }

    public void showalert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showalert1(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
