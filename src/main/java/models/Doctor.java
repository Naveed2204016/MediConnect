package models;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

import java.time.LocalTime;

public class Doctor {
    private StringProperty name;
    private StringProperty specialization;
    private StringProperty qualification;
    private StringProperty location;
    private DoubleProperty fee;
    private int patientslot;
    private int emergencyslot;
    private StringProperty availability;
    private StringProperty hospital;
    private StringProperty city;
    private StringProperty contactno;
    private StringProperty emailad;
    private StringProperty password;
    private ObjectProperty<LocalTime> starttime;
    private ObjectProperty<LocalTime> endtime;

    // First constructor
    public Doctor(String name, String specialization, String qualification, String location,
                  double fee, String availability) {
        this.name = new SimpleStringProperty(name);
        this.specialization = new SimpleStringProperty(specialization);
        this.qualification = new SimpleStringProperty(qualification);
        this.location = new SimpleStringProperty(location);
        this.fee = new SimpleDoubleProperty(fee);
        this.availability = new SimpleStringProperty(availability);

        // Initialize unused properties safely
        this.hospital = new SimpleStringProperty("");
        this.city = new SimpleStringProperty("");
        this.contactno = new SimpleStringProperty("");
        this.emailad = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.starttime = new SimpleObjectProperty<>();
        this.endtime = new SimpleObjectProperty<>();
    }

    // Second constructor
    public Doctor(String name, String specialization, String qualification, String hospital,
                  String city, LocalTime st, LocalTime ed, double fee,
                  String contactno, String emailad) {
        this.name = new SimpleStringProperty(name);
        this.specialization = new SimpleStringProperty(specialization);
        this.qualification = new SimpleStringProperty(qualification);
        this.hospital = new SimpleStringProperty(hospital);
        this.city = new SimpleStringProperty(city);
        this.fee = new SimpleDoubleProperty(fee);
        this.contactno = new SimpleStringProperty(contactno);
        this.emailad = new SimpleStringProperty(emailad);
        this.starttime = new SimpleObjectProperty<>(st);
        this.endtime = new SimpleObjectProperty<>(ed);

        // Initialize other optional properties
        this.location = new SimpleStringProperty("");
        this.availability = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
    }

    public Doctor() {
        this.name = new SimpleStringProperty("");
        this.specialization = new SimpleStringProperty("");
        this.qualification = new SimpleStringProperty("");
        this.location = new SimpleStringProperty("");
        this.fee = new SimpleDoubleProperty(0.0);
        this.availability = new SimpleStringProperty("");
        this.hospital = new SimpleStringProperty("");
        this.city = new SimpleStringProperty("");
        this.contactno = new SimpleStringProperty("");
        this.emailad = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.starttime = new SimpleObjectProperty<>();
        this.endtime = new SimpleObjectProperty<>();
        this.patientslot = 0;
        this.emergencyslot = 0;
    }

    // Getters and setters
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }
    public StringProperty nameProperty() { return name; }

    public String getSpecialization() { return specialization.get(); }
    public void setSpecialization(String specialization) { this.specialization.set(specialization); }
    public StringProperty specializationProperty() { return specialization; }

    public String getQualification() { return qualification.get(); }
    public void setQualification(String qualification) { this.qualification.set(qualification); }
    public StringProperty qualificationProperty() { return qualification; }

    public String getLocation() { return location.get(); }
    public void setLocation(String location) { this.location.set(location); }
    public StringProperty locationProperty() { return location; }

    public double getFee() { return fee.get(); }
    public void setFee(double fee) { this.fee.set(fee); }
    public DoubleProperty feeProperty() { return fee; }

    public int getPatientslot() { return patientslot; }
    public void setPatientslot(int patientslot) { this.patientslot = patientslot; }
public int getEmergencyslot() { return emergencyslot; }
    public void setEmergencyslot(int emergencyslot) { this.emergencyslot = emergencyslot; }
    public String getAvailability() { return availability.get(); }
    public void setAvailability(String availability) { this.availability.set(availability); }
    public StringProperty availabilityProperty() { return availability; }

    public String getHospital() { return hospital.get(); }
    public void setHospital(String hospital) { this.hospital.set(hospital); }
    public StringProperty hospitalProperty() { return hospital; }

    public String getCity() { return city.get(); }
    public void setCity(String city) { this.city.set(city); }
    public StringProperty cityProperty() { return city; }

    public String getContactNumber() { return contactno.get(); }
    public void setContactNumber(String contactNumber) { this.contactno.set(contactNumber); }
    public StringProperty contactnoProperty() { return contactno; }

    public String getEmail() { return emailad.get(); }
    public void setEmail(String email) { this.emailad.set(email); }
    public StringProperty emailadProperty() { return emailad; }

    public String getPassword() { return password.get(); }
    public void setPassword(String password) { this.password.set(password); }
    public StringProperty passwordProperty() { return password; }

    public LocalTime getStarttime() { return starttime.get(); }
    public void setStarttime(LocalTime starttime) { this.starttime.set(starttime);
    }

    public ObservableValue<String> qualificationproperty() {
    return qualification;
    }

    public ObservableValue<String> hospitalproperty() {
    return  hospital;
    }

    public ObservableValue<String> cityproperty() {
    return city;
    }

    public ObservableValue<String> contactnoproperty() {
    return contactno;
    }

    public ObservableValue<String> emailadproperty() {
        return emailad;
    }
}

