package models;

import javafx.beans.property.*;

public class Doctor {
    private  StringProperty name;
    private  StringProperty specialization;
    private  StringProperty qualification;
    private  StringProperty location;
    private  DoubleProperty fee;
    private  StringProperty availability;
    private StringProperty hospital;
    private StringProperty city;
    private StringProperty contactno;
    private StringProperty emailad;

    public Doctor(String name, String specialization,String qualification, String location,
                  double fee, String availability) {
        this.name = new SimpleStringProperty(name);
        this.specialization = new SimpleStringProperty(specialization);
        this.location = new SimpleStringProperty(location);
        this.qualification=new SimpleStringProperty(qualification);
        this.fee = new SimpleDoubleProperty(fee);
        this.availability = new SimpleStringProperty(availability);
    }


    public Doctor(String name, String specialization,String qualification, String hospital,String city,Double fee,
                  String contactno,String emailad) {
        this.name = new SimpleStringProperty(name);
        this.specialization = new SimpleStringProperty(specialization);
        this.qualification=new SimpleStringProperty(qualification);
        this.hospital = new SimpleStringProperty(hospital);
        this.city = new SimpleStringProperty(city);
        this.fee = new SimpleDoubleProperty(fee);
        this.contactno = new SimpleStringProperty(contactno);
        this.emailad=new SimpleStringProperty(emailad);
    }

    // Getters
    public String getName() { return name.get(); }
    public String getSpecialization() { return specialization.get(); }
    public String getLocation() { return location.get(); }
    public double getFee() { return fee.get(); }
    public String getAvailability() { return availability.get(); }

    // Property getters (for JavaFX binding)
    public StringProperty nameProperty() { return name; }
    public StringProperty specializationProperty() { return specialization; }
    public StringProperty locationProperty() { return location; }
    public DoubleProperty feeProperty() { return fee; }
    public StringProperty availabilityProperty() { return availability; }
    public StringProperty qualificationproperty() {return qualification; }
    public StringProperty hospitalproperty() {return hospital; }
    public StringProperty cityproperty() {return city; }
    public StringProperty contactnoproperty() {return contactno; }
    public StringProperty emailadproperty() {return emailad; }

}