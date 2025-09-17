package models;

import java.sql.Date;

public class emergency_request {
    public int reqest_id;
    public String doctor_name;
    public int p_id;
    public int a_id;
    public int d_id;
    public String symptoms;
    public Date request_date;
    public Date tentative_date;
    public String status;
    public String response_seen;
    public emergency_request( int reqest_id,String doctor_name,int p_id, int a_id, int d_id, String symptoms, Date request_date, Date tentative_date, String status,String response_seen) {
        this.reqest_id = reqest_id;
        this.doctor_name=doctor_name;
        this.p_id = p_id;
        this.a_id = a_id;
        this.d_id = d_id;
        this.symptoms = symptoms;
        this.request_date = request_date;
        this.tentative_date = tentative_date;
        this.status = status;
        this.response_seen=response_seen;
    }
    public String getDoctor_name() {
        return doctor_name;
    }
    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }
    public int getReqest_id() {
        return reqest_id;
    }
    public void setReqest_id(int reqest_id) {
        this.reqest_id = reqest_id;
    }
    public int getP_id() {
        return p_id;
    }
    public void setP_id(int p_id) {
        this.p_id = p_id;
    }
    public int getA_id() {
        return a_id;
    }
    public void setA_id(int a_id) {
        this.a_id = a_id;
    }
    public int getD_id() {
        return d_id;
    }
    public void setD_id(int d_id) {
        this.d_id = d_id;
    }
    public String getSymptoms() {
        return symptoms;
    }
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
    public Date getRequest_date() {
        return request_date;
    }
    public void setRequest_date(Date request_date) {
        this.request_date = request_date;
    }
    public Date getTentative_date() {
        return tentative_date;
    }
    public void setTentative_date(Date tentative_date) {
        this.tentative_date = tentative_date;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getResponse_seen() {
        return response_seen;
    }
    public void setResponse_seen(String response_seen) {
        this.response_seen = response_seen;
    }
}
