package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Test {
    private int id;
    private  StringProperty testName;
    private  StringProperty hospital;
    private  StringProperty fee;
    private  StringProperty timeSlot;
    private  StringProperty instructions;

    public Test(int id, String testName, String hospital, String fee, String timeSlot, String instructions) {
        this.id = id;
        this.testName = new SimpleStringProperty(testName);
        this.hospital = new SimpleStringProperty(hospital);
        this.fee = new SimpleStringProperty(fee);
        this.timeSlot = new SimpleStringProperty(timeSlot);
        this.instructions =new SimpleStringProperty(instructions);
    }

    public int getId() { return id; }
    public String getTestName() { return testName.get(); }
    public String getHospital() { return hospital.get(); }
    public String getFee() { return fee.get(); }
    public String getTimeSlot() { return timeSlot.get(); }
    public String getInstructions() { return instructions.get(); }


    public StringProperty getTestNameproperty() { return testName; }
    public StringProperty getHospitalproperty() { return hospital; }
    public StringProperty getFeeproperty() { return fee; }
    public StringProperty getTimeSlotproperty() { return timeSlot; }
    public StringProperty getInstructionsproperty() { return instructions; }



    public void setTestName(String testName) { this.testName = new SimpleStringProperty(testName); }
    public void setHospital(String hospital) { this.hospital = new SimpleStringProperty(hospital); }
    public void setFee(String fee) { this.fee = new SimpleStringProperty(fee); }
    public void setTimeSlot(String timeSlot) { this.timeSlot = new SimpleStringProperty(timeSlot); }
    public void setInstructions(String instructions) { this.instructions = new SimpleStringProperty(instructions); }
}
