package models;

public class EmergencyRequest {

    private String patientId;
    private String details;
    private String contact;

    public EmergencyRequest(String patientId, String details, String contact) {
        this.patientId = patientId;
        this.details = details;
        this.contact = contact;
    }

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
}
