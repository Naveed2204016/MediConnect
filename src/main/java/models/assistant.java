package models;

public class assistant {
    private int assistantID;
    private int doctorID;
    private String name;
    private String contactNumber;
    private String email;
    private String password;
    public assistant(int assistantID, int doctorID, String name, String contactNumber, String email, String password) {
        this.assistantID = assistantID;
        this.doctorID = doctorID;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.password = password;
    }
    public void setassistantID(int assistantID) {
        this.assistantID = assistantID;
    }
    public Integer getAssistantID() {
        return assistantID;
    }
    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }
    public int getDoctorID() {
        return doctorID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    public String getContactNumber() {
        return contactNumber;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
}
