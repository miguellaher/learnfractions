package com.example.laher.learnfractions.model;

import java.util.ArrayList;

public class User extends BaseModel {
    private String username;
    private String password;
    private String age;
    private String gender;
    private String activitiesCode;
    private ArrayList<String> activitiesDone;

    public ArrayList<String> getActivitiesDone() {
        return activitiesDone;
    }
    public void setActivitiesDone(ArrayList<String> activitiesDone) {
        this.activitiesDone = activitiesDone;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) { this.gender = gender; }

}
