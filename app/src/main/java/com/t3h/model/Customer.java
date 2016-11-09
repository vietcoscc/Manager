package com.t3h.model;

/**
 * Created by vaio on 11/7/2016.
 */

public class Customer {
    private int code;
    private String name;
    private String dateOfBirth;
    private int phoneNumber;
    private String company;
    private String position;
    private String address;
    private String type;

    public Customer(int code, String name, String dateOfBirth, int phoneNumber, String company, String position, String address, String type) {
        this.code = code;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.position = position;
        this.address = address;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getCompany() {
        return company;
    }

    public String getPosition() {
        return position;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return code+"_"+name+"_"+dateOfBirth+"_"+"0"+phoneNumber+"_"+company+"_"+position+"_"+address+""+type;
    }
}
