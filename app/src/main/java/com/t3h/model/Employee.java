package com.t3h.model;

/**
 * Created by vaio on 11/7/2016.
 */

public class Employee {
    private int code;
    private String userName;
    private String password;

    public Employee(int code, String userName, String password) {
        this.code = code;
        this.userName = userName;
        this.password = password;
    }

    public int getCode() {
        return code;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
