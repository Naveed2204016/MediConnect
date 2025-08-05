package models;

import javafx.beans.property.*;

public class Appointment {
    private int appointmentId;
    private String doctorName;
    private String hospital;
    private String appointmentDate;

    public Appointment(int appointmentId, String doctorName, String hospital, String appointmentDate) {
        this.appointmentId = appointmentId;
        this.doctorName = doctorName;
        this.hospital = hospital;
        this.appointmentDate = appointmentDate;
    }

    public int getAppointmentId() { return appointmentId; }
    public String getDoctorName() { return doctorName; }
    public String getHospital() { return hospital; }
    public String getAppointmentDate() { return appointmentDate; }
}
