package com.example.sanjayprajapati.experteye;

/**
 * Created by sanjayprajapati on 6/26/18.
 */

public class Reciept {
    int SaleID;
    String Phone;
    String Name;
    String Date,Time,  Location, Cashier,ServiceDescription;
    int Total,Discount,NetAmount, Cash, Card;
    public Reciept(){}

    public Reciept(int saleID, String phone, String name, String date, String time, String location, String cashier, String serviceDescription, int total, int discount, int netAmount, int cash, int card) {
        SaleID = saleID;
        Phone = phone;
        Name = name;
        Date = date;
        Time = time;
        Location = location;
        Cashier = cashier;
        ServiceDescription = serviceDescription;
        Total = total;
        Discount = discount;
        NetAmount = netAmount;
        Cash = cash;
        Card = card;
    }

    public int getSaleID() {
        return SaleID;
    }

    public void setSaleID(int saleID) {
        SaleID = saleID;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getCashier() {
        return Cashier;
    }

    public void setCashier(String cashier) {
        Cashier = cashier;
    }

    public String getServiceDescription() {
        return ServiceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        ServiceDescription = serviceDescription;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    public int getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(int netAmount) {
        NetAmount = netAmount;
    }

    public int getCash() {
        return Cash;
    }

    public void setCash(int cash) {
        Cash = cash;
    }

    public int getCard() {
        return Card;
    }

    public void setCard(int card) {
        Card = card;
    }
}
