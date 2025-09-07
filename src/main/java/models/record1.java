package models;

import javafx.beans.property.*;

import java.time.LocalDate;


import javafx.beans.property.*;
import java.time.LocalDate;

public class record1 {
    private final IntegerProperty recordId;
    private final IntegerProperty pId;
    private final IntegerProperty dId;
    private final ObjectProperty<LocalDate> visitDate;
    private final StringProperty diagnosis;
    private final StringProperty treatment;
    private final StringProperty notes;
    private final StringProperty prescribedTest;
    private final StringProperty patientname;

    // Full Constructor
    public record1(int recordId, int pId, int dId, LocalDate visitDate,
                   String diagnosis, String treatment, String notes, String prescribedTest, String patientname) {
        this.recordId = new SimpleIntegerProperty(recordId);
        this.pId = new SimpleIntegerProperty(pId);
        this.dId = new SimpleIntegerProperty(dId);
        this.visitDate = new SimpleObjectProperty<>(visitDate);
        this.diagnosis = new SimpleStringProperty(diagnosis);
        this.treatment = new SimpleStringProperty(treatment);
        this.notes = new SimpleStringProperty(notes);
        this.prescribedTest = new SimpleStringProperty(prescribedTest);
        this.patientname = new SimpleStringProperty(patientname);
    }

    // Constructor without recordId and dId
    public record1(int pId, LocalDate visitDate,
                   String diagnosis, String treatment, String notes, String prescribedTest, String patientname) {
        this.recordId = new SimpleIntegerProperty(0); // default 0
        this.pId = new SimpleIntegerProperty(pId);
        this.dId = new SimpleIntegerProperty(0); // default 0
        this.visitDate = new SimpleObjectProperty<>(visitDate);
        this.diagnosis = new SimpleStringProperty(diagnosis);
        this.treatment = new SimpleStringProperty(treatment);
        this.notes = new SimpleStringProperty(notes);
        this.prescribedTest = new SimpleStringProperty(prescribedTest);
        this.patientname = new SimpleStringProperty(patientname);
    }

    // --- Getters, Setters, Properties ---

    public int getRecordId() { return recordId.get(); }
    public void setRecordId(int value) { recordId.set(value); }
    public IntegerProperty recordIdProperty() { return recordId; }

    public int getPId() { return pId.get(); }
    public void setPId(int value) { pId.set(value); }
    public IntegerProperty pIdProperty() { return pId; }

    public int getDId() { return dId.get(); }
    public void setDId(int value) { dId.set(value); }
    public IntegerProperty dIdProperty() { return dId; }

    public LocalDate getVisitDate() { return visitDate.get(); }
    public void setVisitDate(LocalDate value) { visitDate.set(value); }
    public ObjectProperty<LocalDate> visitDateProperty() { return visitDate; }

    public String getDiagnosis() { return diagnosis.get(); }
    public void setDiagnosis(String value) { diagnosis.set(value); }
    public StringProperty diagnosisProperty() { return diagnosis; }

    public String getTreatment() { return treatment.get(); }
    public void setTreatment(String value) { treatment.set(value); }
    public StringProperty treatmentProperty() { return treatment; }

    public String getNotes() { return notes.get(); }
    public void setNotes(String value) { notes.set(value); }
    public StringProperty notesProperty() { return notes; }

    public String getPrescribedTest() { return prescribedTest.get(); }
    public void setPrescribedTest(String value) { prescribedTest.set(value); }
    public StringProperty prescribedTestProperty() { return prescribedTest; }

    public String getPatientname() { return patientname.get(); }
    public void setPatientname(String value) { patientname.set(value); }
    public StringProperty patientnameProperty() { return patientname; }
}



