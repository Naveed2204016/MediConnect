package models;

import java.time.LocalDateTime;

public class EmergencyRequest {

    private String patientId;
    private String details;
    private String contact;
    private LocalDateTime requestDate;
    private LocalDateTime tentativeDate; // fixed typo
    private String status;

    public EmergencyRequest(String patientId, String details, String contact,
                            LocalDateTime requestDate, LocalDateTime tentativeDate, String status) {
        this.patientId = patientId;
        this.details = details;
        this.contact = contact;
        this.requestDate = requestDate;
        this.tentativeDate = tentativeDate;
        this.status = status;
    }

    // Getters and Setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDateTime getTentativeDate() { // fixed getter name
        return tentativeDate;
    }

    public void setTentativeDate(LocalDateTime tentativeDate) { // fixed setter name
        this.tentativeDate = tentativeDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
