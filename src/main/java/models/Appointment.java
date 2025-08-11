package models;

import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Appointment {
    private int appointmentId;
    private StringProperty doctorName;
    private StringProperty hospital;
    private StringProperty appointmentDate;

    public Appointment(int appointmentId, String doctorName, String hospital, String appointmentDate) {
        this.appointmentId=appointmentId;
        this.doctorName =new SimpleStringProperty(doctorName);
        this.hospital =new SimpleStringProperty( hospital);
        this.appointmentDate = new SimpleStringProperty(appointmentDate);
    }


    public int getAppointmentId() { return appointmentId; }
    public String getDoctorName() { return doctorName.getValue(); }
    public String getHospital() { return hospital.getValue(); }
    public String getAppointmentDate() { return appointmentDate.getValue(); }

    public StringProperty getDoctorNameproperty() { return doctorName; }
    public StringProperty getHospitalproperty() { return hospital; }
    public StringProperty getAppointmentDateproperty() { return appointmentDate; }
}
