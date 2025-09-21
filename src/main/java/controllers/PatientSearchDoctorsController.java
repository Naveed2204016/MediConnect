package controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.Doctor;
import db.DBConnection;
import models.doctor1;

import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class PatientSearchDoctorsController {

    @FXML private TextField nameField;
    @FXML private TextField hospitalField;
    @FXML private TextField cityField;
    @FXML private TextField specializationField;
    @FXML private TableView<doctor1> doctorsTable;
    @FXML private TableColumn<doctor1, String> nameCol;
    @FXML private TableColumn<doctor1, String> specializationCol;
    @FXML private TableColumn<doctor1, String> qualificationCol;
    @FXML private TableColumn<doctor1, String> hospitalCol;
    @FXML private TableColumn<doctor1, String> cityCol;
    @FXML private TableColumn<doctor1, String> stCol;
    @FXML private TableColumn<doctor1, String> edCol;
    @FXML private TableColumn<doctor1, String> contactCol;
    @FXML private TableColumn<doctor1, String> emailCol;
    @FXML private TableColumn<doctor1, Double> feeCol;
    private int userId;

    ObservableList<doctor1> doctors= FXCollections.observableArrayList();


    public void setUserId(int userId) {
        this.userId = userId;
        loadDoctorsData();
    }
    @FXML
    public void initialize() {
        // Set up table columns
        nameCol.setCellValueFactory(Data -> new javafx.beans.property.SimpleStringProperty(Data.getValue().getName()));
        specializationCol.setCellValueFactory(Data->new javafx.beans.property.SimpleStringProperty(Data.getValue().getSpecialization()));
        qualificationCol.setCellValueFactory(Data->new javafx.beans.property.SimpleStringProperty(Data.getValue().getQualification()));
        hospitalCol.setCellValueFactory(Data->new javafx.beans.property.SimpleStringProperty(Data.getValue().getHospital()));
        cityCol.setCellValueFactory(Data->new javafx.beans.property.SimpleStringProperty(Data.getValue().getCity()));
        stCol.setCellValueFactory(data -> {
             java.sql.Time time = data.getValue().getStartTime(); // assuming returns TIME
             String formattedTime = (time != null)
                ? time.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"))
                : "";
        return new javafx.beans.property.SimpleStringProperty(formattedTime);
    });
        edCol.setCellValueFactory(data-> {
            java.sql.Time time = data.getValue().getEndTime(); // assuming returns TIME
            String formattedTime = (time != null)
                ? time.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"))
                : "";
            return new javafx.beans.property.SimpleStringProperty(formattedTime);
        });
        contactCol.setCellValueFactory(Data->new javafx.beans.property.SimpleStringProperty(Data.getValue().getContact_number()));
        emailCol.setCellValueFactory(Data->new javafx.beans.property.SimpleStringProperty(Data.getValue().getEmail()));
        feeCol.setCellValueFactory(Data->new javafx.beans.property.SimpleDoubleProperty(Data.getValue().getFees()).asObject());


        TableColumn<doctor1, Void> actionCol = new TableColumn<>("Action");
        actionCol.setPrefWidth(120);
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button bookButton = new Button("Book");

            {
                bookButton.setOnAction(event -> {
                    doctor1 doctor = getTableView().getItems().get(getIndex());
                    handleBookingOptions(doctor);
                });
                bookButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(bookButton);
                }
            }
        });
        doctorsTable.getColumns().add(actionCol);

    }

    public void loadDoctorsData(){
        doctors.clear();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DBConnection.getConnection();
            String Query="SELECT * FROM (Doctor as d,Location as l) WHERE d.doctor_id=l.d_id";
            PreparedStatement pmt= connection.prepareStatement(Query);
            ResultSet rs = pmt.executeQuery();
            while(rs.next()) {
                int doctorId = rs.getInt("doctor_id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                String qualification = rs.getString("qualification");
                String hospital = rs.getString("hospital");
                String city = rs.getString("city");
                Time startTime = rs.getTime("start_time");
                Time endTime = rs.getTime("End_time");
                double fees = rs.getDouble("fees");
                String contact_number = rs.getString("contact_number");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int capaity_per_day = rs.getInt("capacity_per_day");
                int emergency_slots_per_day = rs.getInt("emergency_slots_per_day");
                doctor1 doc = new doctor1(doctorId, name, specialization, qualification, hospital, city, startTime, endTime, fees, contact_number, email, capaity_per_day, emergency_slots_per_day, password);
                doctors.add(doc);
            }

            doctorsTable.setItems(doctors);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleSearch() {
        String name1= nameField.getText().trim();
        String specialization1 = specializationField.getText().trim();
        String hospital1 = hospitalField.getText().trim();
        String city1 = cityField.getText().trim();
        doctors.clear();
        if(specialization1.isEmpty() || city1.isEmpty())
        {
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill specialization and city fields");
            alert.showAndWait();
            return;
        }
        else if(name1.isEmpty() && hospital1.isEmpty())
        {
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DBConnection.getConnection();
                String Query="SELECT * FROM (Doctor as d,Location as l) WHERE d.doctor_id=l.d_id AND d.specialization=? AND l.city=?";
                PreparedStatement pmt= connection.prepareStatement(Query);
                pmt.setString(1, specialization1);
                pmt.setString(2, city1);

                ResultSet rs = pmt.executeQuery();
                while(rs.next()) {
                    int doctorId = rs.getInt("doctor_id");
                    String name = rs.getString("name");
                    String specialization = rs.getString("specialization");
                    String qualification = rs.getString("qualification");
                    String hospital = rs.getString("hospital");
                    String city = rs.getString("city");
                    Time startTime = rs.getTime("start_time");
                    Time endTime = rs.getTime("End_time");
                    double fees = rs.getDouble("fees");
                    String contact_number = rs.getString("contact_number");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    int capaity_per_day = rs.getInt("capacity_per_day");
                    int emergency_slots_per_day = rs.getInt("emergency_slots_per_day");
                    doctor1 doc = new doctor1(doctorId, name, specialization, qualification, hospital, city, startTime, endTime, fees, contact_number, email, capaity_per_day, emergency_slots_per_day, password);
                    doctors.add(doc);
                }
                doctorsTable.setItems(doctors);
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else if(name1.isEmpty())
        {
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DBConnection.getConnection();
                String Query="SELECT * FROM (Doctor as d,Location as l) WHERE d.doctor_id=l.d_id AND d.specialization=? AND l.hospital=? AND l.city=?";
                PreparedStatement pmt= connection.prepareStatement(Query);
                pmt.setString(1, specialization1);
                pmt.setString(2, hospital1);
                pmt.setString(3, city1);

                ResultSet rs = pmt.executeQuery();
                while(rs.next()) {
                    int doctorId = rs.getInt("doctor_id");
                    String name = rs.getString("name");
                    String specialization = rs.getString("specialization");
                    String qualification = rs.getString("qualification");
                    String hospital = rs.getString("hospital");
                    String city = rs.getString("city");
                    Time startTime = rs.getTime("start_time");
                    Time endTime = rs.getTime("End_time");
                    double fees = rs.getDouble("fees");
                    String contact_number = rs.getString("contact_number");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    int capaity_per_day = rs.getInt("capacity_per_day");
                    int emergency_slots_per_day = rs.getInt("emergency_slots_per_day");
                    doctor1 doc = new doctor1(doctorId, name, specialization, qualification, hospital, city, startTime, endTime, fees, contact_number, email, capaity_per_day, emergency_slots_per_day, password);
                    doctors.add(doc);
                }
                doctorsTable.setItems(doctors);
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else if(hospital1.isEmpty())
        {
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DBConnection.getConnection();
                String Query="SELECT * FROM (Doctor as d,Location as l) WHERE d.doctor_id=l.d_id AND d.name=? AND d.specialization=? AND l.city=?";
                PreparedStatement pmt= connection.prepareStatement(Query);
                pmt.setString(1, name1);
                pmt.setString(2, specialization1);
                pmt.setString(3, city1);

                ResultSet rs = pmt.executeQuery();
                while(rs.next()) {
                    int doctorId = rs.getInt("doctor_id");
                    String name = rs.getString("name");
                    String specialization = rs.getString("specialization");
                    String qualification = rs.getString("qualification");
                    String hospital = rs.getString("hospital");
                    String city = rs.getString("city");
                    Time startTime = rs.getTime("start_time");
                    Time endTime = rs.getTime("End_time");
                    double fees = rs.getDouble("fees");
                    String contact_number = rs.getString("contact_number");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    int capaity_per_day = rs.getInt("capacity_per_day");
                    int emergency_slots_per_day = rs.getInt("emergency_slots_per_day");
                    doctor1 doc = new doctor1(doctorId, name, specialization, qualification, hospital, city, startTime, endTime, fees, contact_number, email, capaity_per_day, emergency_slots_per_day, password);
                    doctors.add(doc);
                }
                doctorsTable.setItems(doctors);
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DBConnection.getConnection();
                String Query = "SELECT * FROM (Doctor as d,Location as l) WHERE d.doctor_id=l.d_id AND d.name=? AND d.specialization=? AND l.hospital=? AND l.city=?";
                PreparedStatement pmt = connection.prepareStatement(Query);
                pmt.setString(1, name1);
                pmt.setString(2, specialization1);
                pmt.setString(3, hospital1);
                pmt.setString(4, city1);

                ResultSet rs = pmt.executeQuery();
                while (rs.next()) {
                    int doctorId = rs.getInt("doctor_id");
                    String name = rs.getString("name");
                    String specialization = rs.getString("specialization");
                    String qualification = rs.getString("qualification");
                    String hospital = rs.getString("hospital");
                    String city = rs.getString("city");
                    Time startTime = rs.getTime("start_time");
                    Time endTime = rs.getTime("End_time");
                    double fees = rs.getDouble("fees");
                    String contact_number = rs.getString("contact_number");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    int capaity_per_day = rs.getInt("capacity_per_day");
                    int emergency_slots_per_day = rs.getInt("emergency_slots_per_day");
                    doctor1 doc = new doctor1(doctorId, name, specialization, qualification, hospital, city, startTime, endTime, fees, contact_number, email, capaity_per_day, emergency_slots_per_day, password);
                    doctors.add(doc);
                }
                doctorsTable.setItems(doctors);
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleBookingOptions(doctor1 doctor) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Book Appointment");
        alert.setHeaderText("Choose booking type for " + doctor.getName());
        ButtonType normal = new ButtonType("Normal Appointment");
        ButtonType emergency = new ButtonType("Emergency Request");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(normal, emergency, cancel);

        alert.showAndWait().ifPresent(choice -> {
            if (choice == normal) {
                suggestNormalAppointment(doctor);
            } else if (choice == emergency) {
                handleEmergencyRequest(doctor);
            }
        });
    }

    private void suggestNormalAppointment(doctor1 doctor) {
        // Example query (pseudo-code)
        // SELECT COUNT(*) FROM Appointment WHERE doctor_id=? AND appointment_date=?
        int d_id=doctor.getDoctorId();
        int capacity=0;
        int bookedCount=0;
        int toadd=0;
        LocalDate tentativeDate=LocalDate.now();// If < capacity → suggest that date, otherwise move to next date.
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DBConnection.getConnection();
            String Query="SELECT capacity_per_day FROM Doctor WHERE doctor_id=?";
            PreparedStatement pmt= connection.prepareStatement(Query);
            pmt.setInt(1, d_id);
            ResultSet rs = pmt.executeQuery();
            if(rs.next())
            {
                capacity = rs.getInt("capacity_per_day");
            }
            pmt.close();
            String CountQuery="SELECT COUNT(*) FROM appointment WHERE d_id=? AND Status=? AND appointment_date>?";
            pmt= connection.prepareStatement(CountQuery);
            pmt.setInt(1, d_id);
            pmt.setString(2, "Confirmed"); // Assuming 'Confirmed' is the status
            pmt.setDate(3, Date.valueOf(LocalDate.now()));
            ResultSet countRs = pmt.executeQuery();
            if(countRs.next()) {
                bookedCount = countRs.getInt(1);
            }
            pmt.close();
            toadd=(bookedCount/capacity);
            tentativeDate = LocalDate.now().plusDays(toadd+1);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        final LocalDate tentativeDateFinal = tentativeDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formatted = tentativeDate.format(formatter);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Tentative Appointment");
        alert.setHeaderText("Doctor " + doctor.getName());
        alert.setContentText("Next available slot: " + formatted + "\nDo you want to confirm?");

        ButtonType confirm = new ButtonType("Confirm");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirm, cancel);

        alert.showAndWait().ifPresent(choice -> {
            if (choice == confirm) {
                // Insert into Appointment table
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DBConnection.getConnection();
                    String query="SELECT assistant_id FROM Assistant WHERE d_id=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, d_id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    int assistantId = 0;
                    if (resultSet.next()) {
                        assistantId = resultSet.getInt("assistant_id");
                    }
                    preparedStatement.close();
                    String insertquery="INSERT INTO Appointment (d_id,p_id,a_id,appointment_date,Status) VALUES (?,?,?,?,?)";
                    preparedStatement = connection.prepareStatement(insertquery);
                    preparedStatement.setInt(1, d_id);
                    preparedStatement.setInt(2, userId);
                    preparedStatement.setInt(3, assistantId);
                    preparedStatement.setDate(4, Date.valueOf(tentativeDateFinal));
                    preparedStatement.setString(5, "Confirmed");
                    int cnt = preparedStatement.executeUpdate();
                    if(cnt>0) {
                        showAlert("Appointment Confirmed", "Your appointment with Dr. " + doctor.getName() + " is confirmed for " + formatted);
                    } else {
                        showAlert("Error", "Failed to confirm the appointment. Please try again.");
                    }
                    preparedStatement.close();
                    connection.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }catch (SQLException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        });
    }




    private void handleEmergencyRequest(doctor1 doctor) {
        final int[] assistantId = {0};
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection= DBConnection.getConnection();
            String query="SELECT assistant_id FROM Assistant WHERE d_id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,doctor.getDoctorId());
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                assistantId[0]=resultSet.getInt("assistant_id");
            }
            preparedStatement.close();
            connection.close();
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }



        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Emergency Request");
        dialog.setHeaderText("Enter your symptoms for " + doctor.getName());

        ButtonType submitButton = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButton, cancelButton);

        TextArea symptomsField = new TextArea();
        symptomsField.setPromptText("Enter your symptoms");
        symptomsField.setWrapText(true);
        symptomsField.setPrefRowCount(6);   // makes it large enough
        symptomsField.setPrefColumnCount(30);

        VBox content = new VBox(10);
        content.getChildren().add(symptomsField);
        dialog.getDialogPane().setContent(content);

        // Return the symptoms when submitted
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButton) {
                return symptomsField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(symptoms -> {
            if (symptoms != null && !symptoms.trim().isEmpty()) {
                // call your future DB method here
                instoDatabase(doctor,assistantId[0],symptoms);

            } else {
                showAlert("Error", "Symptoms cannot be empty.");
            }
        });


    }

    public void instoDatabase(doctor1 doctor,int a_id,String symptoms)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DBConnection.getConnection();
            String query="INSERT INTO Emergency_request (p_id,a_id,d_id,symptoms,request_date,tentative_date,status,response_seen) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,a_id);
            preparedStatement.setInt(3,doctor.getDoctorId());
            preparedStatement.setString(4,symptoms);
            preparedStatement.setDate(5, Date.valueOf(LocalDate.now()));
            preparedStatement.setDate(6, null);
            preparedStatement.setString(7,"Pending");
            preparedStatement.setString(8,"Not Seen");
            int cnt=preparedStatement.executeUpdate();
            if(cnt>0) {
                showAlert("Emergency Request", "Your emergency request has been sent for " + doctor.getName());
            }
            else
            {
                showAlert("Error", "Failed to send the emergency request. Please try again.");
            }

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}