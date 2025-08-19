package models;
import java.sql.Time;

public class doctor1 {
    private int doctorId;
    private String name;
    private String specialization;
    private String qualification;
    private String hospital;
    private String city;
    private Time startTime;
    private Time endTime;
    private double fees;
    private String contact_number;
    private String email;
    private int capacity_per_day;
    private int emergency_slots_per_day;
    private String password;
    public doctor1(int doctorId, String name, String specialization, String qualification, String hospital, String city, Time startTime, Time endTime, double fees, String contact_number, String email, int capacity_per_day, int emergency_slots_per_day, String password) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.qualification = qualification;
        this.hospital = hospital;
        this.city = city;
        this.startTime = startTime;
        this.endTime = endTime;
        this.fees = fees;
        this.contact_number = contact_number;
        this.email = email;
        this.capacity_per_day = capacity_per_day;
        this.emergency_slots_per_day = emergency_slots_per_day;
        this.password = password;
    }
    public int getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    public String getQualification() {
        return qualification;
    }
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    public String getHospital() {
        return hospital;
    }
    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Time getStartTime() {
        return startTime;
    }
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    public Time getEndTime() {
        return endTime;
    }
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    public double getFees() {
        return fees;
    }
    public void setFees(double fees) {
        this.fees = fees;
    }
    public String getContact_number() {
        return contact_number;
    }
    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getCapacity_per_day() {
        return capacity_per_day;
    }
    public void setCapacity_per_day(int capacity_per_day) {
        this.capacity_per_day = capacity_per_day;
    }
    public int getEmergency_slots_per_day() {
        return emergency_slots_per_day;
    }
    public void setEmergency_slots_per_day(int emergency_slots_per_day) {
        this.emergency_slots_per_day = emergency_slots_per_day;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
