package models;

import javafx.beans.property.*;

public class DiagnosticTest {
    private final IntegerProperty testId = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty hospital = new SimpleStringProperty();
    private final DoubleProperty fee = new SimpleDoubleProperty();

    public DiagnosticTest(int testId, String name, String hospital, double fee) {
        this.testId.set(testId);
        this.name.set(name);
        this.hospital.set(hospital);
        this.fee.set(fee);
    }

    // Property getters
    public IntegerProperty testIdProperty() { return testId; }
    public StringProperty nameProperty() { return name; }
    public StringProperty hospitalProperty() { return hospital; }
    public DoubleProperty feeProperty() { return fee; }

    // Regular getters
    public int getTestId() { return testId.get(); }
    public String getName() { return name.get(); }
    public String getHospital() { return hospital.get(); }
    public double getFee() { return fee.get(); }
}
