package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NotificationItem {
    private final StringProperty message;

    public NotificationItem(String message) {
        this.message = new SimpleStringProperty(message);
    }

    public StringProperty messageProperty() {
        return message;
    }

    public String getMessage() {
        return message.get();
    }

    public void setMessage(String message) {
        this.message.set(message);
    }
}
