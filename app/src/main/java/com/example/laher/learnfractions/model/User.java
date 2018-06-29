package com.example.laher.learnfractions.model;

import java.util.ArrayList;

public class User extends BaseModel {
    private int age;
    private String gender;
    private ArrayList<String> activitiesDone;

    public ArrayList<String> getActivitiesDone() {
        return activitiesDone;
    }
    public void setActivitiesDone(ArrayList<String> activitiesDone) {
        this.activitiesDone = activitiesDone;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) { this.gender = gender; }

    public User(){

    }
    public User(String username, int age, String gender){
        setUsername(username);
        setAge(age);
        setGender(gender);
    }

}
