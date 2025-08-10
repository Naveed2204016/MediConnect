package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RequestItem {
    private final StringProperty request;
    private final StringProperty status;

    public RequestItem(String request, String status) {
        this.request = new SimpleStringProperty(request);
        this.status = new SimpleStringProperty(status);
    }

    public StringProperty requestProperty() {
        return request;
    }

    public String getRequest() {
        return request.get();
    }

    public void setRequest(String request) {
        this.request.set(request);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
