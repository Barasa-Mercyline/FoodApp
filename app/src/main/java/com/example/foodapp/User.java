package com.example.foodapp;

public class User {
    private String userName;
    private String email;
    private String phoneNum;
    private String regNo;
    private String accType;
    public User(){
    }

    public User(String userName, String email, String phoneNum, String regNo, String accType) {
        this.userName = userName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.regNo = regNo;
        this.accType = accType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }
}

