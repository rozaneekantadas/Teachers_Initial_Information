package com.example.teachersinformations;

public class SaveData {

    private String initial, name, department, contact, email, imageUrl;

    public SaveData(){

    }

    public SaveData(String initial, String name, String department, String contact, String email) {
        this.initial = initial;
        this.name = name;
        this.department = department;
        this.contact = contact;
        this.email = email;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
