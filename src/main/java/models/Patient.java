package models;

import java.time.LocalDate;

public class Patient {
    private String name;
    private LocalDate dob;
    private String contactNumber;
    private String email;
    private String password;
    private String pastvisit;private int id;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
  public void setpid(int d){this.id=d;}
    public int getpid(){return id;}
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
 public String getPastvisit() { return pastvisit; }
    public void setPastvisit(String pastvisit) { this.pastvisit = pastvisit; }
}