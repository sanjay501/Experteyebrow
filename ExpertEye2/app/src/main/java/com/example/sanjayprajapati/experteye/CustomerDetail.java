package com.example.sanjayprajapati.experteye;

/**
 * Created by sanjayprajapati on 6/13/18.
 */

public class CustomerDetail {
    String phone;
    String FirstName, LastName,email_address,notes;


    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public CustomerDetail(String phone, String firstName, String lastName, String email, String notes) {
        this.phone = phone;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.email_address=email;

        this.notes=notes;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}

