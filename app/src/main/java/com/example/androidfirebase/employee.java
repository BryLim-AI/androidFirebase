package com.example.androidfirebase;

public class employee {
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    private String emailAddress;

    public employee(){
        //Construcor for firebase;
    }

    public employee(String fullName, String emailAddress){
        this.fullName = fullName;
        this.emailAddress = emailAddress;
    }

}
