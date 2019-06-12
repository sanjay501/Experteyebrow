package com.example.sanjayprajapati.experteye;

/**
 * Created by sanjayprajapati on 6/13/18.
 */

public class Customer {
    String phone;
    String FirstName, LastName;

    public Customer(String phone, String firstName, String lastName) {
        this.phone = phone;
        FirstName = firstName;
        LastName = lastName;
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

