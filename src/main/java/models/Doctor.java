package models;

import javafx.beans.property.*;

public class Doctor {
    private final StringProperty name;
    private final StringProperty specialization;
    private final StringProperty location;
    private final DoubleProperty fee;
    private final StringProperty availability;

    public Doctor(String name, String specialization, String location,
                  double fee, String availability) {
        this.name = new SimpleStringProperty(name);
        this.specialization = new SimpleStringProperty(specialization);
        this.location = new SimpleStringProperty(location);
        this.fee = new SimpleDoubleProperty(fee);
        this.availability = new SimpleStringProperty(availability);
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
}