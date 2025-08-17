package models;

import javafx.beans.property.*;

import java.sql.Time;

public class DiagnosticTest {
    private int testid;
    private String name;
    private String hospital_name;
    private double fee;
    private String instruction;
    private Time test_time_slot;
    public DiagnosticTest(int testid, String name, String hospital_name, double fee, String instruction, Time test_time_slot) {
        this.testid = testid;
        this.name = name;
        this.hospital_name = hospital_name;
        this.fee = fee;
        this.instruction = instruction;
        this.test_time_slot = test_time_slot;
    }

    public int getTestid() {
        return testid;
    }
    public void setTestid(int testid) {
        this.testid = testid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHospital_name() {
        return hospital_name;
    }
    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }
    public double getFee() {
        return fee;
    }
    public void setFee(double fee) {
        this.fee = fee;
    }
    public String getInstruction() {
        return instruction;
    }
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
    public Time getTest_time_slot() {
        return test_time_slot;
    }
    public void setTest_time_slot(Time test_time_slot) {
        this.test_time_slot = test_time_slot;
    }
}
