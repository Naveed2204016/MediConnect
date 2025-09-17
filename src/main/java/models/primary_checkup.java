package models;

public class primary_checkup {
    String name;
    String contact;
    int id;
    String blood_pressure;
    int age;
    int weight;
    primary_checkup(String name, String contact, int id, String blood_pressure, int age, int weight) {
        this.name = name;
        this.contact = contact;
        this.id = id;
        this.blood_pressure = blood_pressure;
        this.age = age;
        this.weight = weight;
    }
    public primary_checkup(String name, String blood_pressure, int age, int weight) {
        this.name = name;
        this.blood_pressure = blood_pressure;
        this.age = age;
        this.weight = weight;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getBlood_pressure() {
        return blood_pressure;
    }
    public void setBlood_pressure(String blood_pressure) {
        this.blood_pressure = blood_pressure;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
}
