package models;

import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.sql.Date;

public class Appointment {
    private int appointment_id;
    private String doctorName;
    private String contactNumber;
    private Date appointmentDate;
    public Appointment(int appointment_id, String doctorName, String contactNumber, Date appointmentDate) {
        this.appointment_id = appointment_id;
        this.doctorName = doctorName;
        this.contactNumber = contactNumber;
        this.appointmentDate = appointmentDate;
    }
    public int getAppointmentId() {
        return appointment_id;
    }
    public void setAppointmentId(int appointment_id) {
        this.appointment_id = appointment_id;
    }
    public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    public Date getAppointmentDate() {
        return appointmentDate;
    }
    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

}
