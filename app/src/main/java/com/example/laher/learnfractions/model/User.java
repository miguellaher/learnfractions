package com.example.laher.learnfractions.model;

import com.example.laher.learnfractions.util.AppConstants;

public class User extends BaseModel {
    private int age;
    private String gender;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
    public void setGender(String gender) {
        if (gender.equals(AppConstants.MALE) || gender.equals(AppConstants.FEMALE)) {
            this.gender = gender;
        }
    }

    public User(){

    }
    public User(String username, int age, String gender){
        setUsername(username);
        setAge(age);
        setGender(gender);
    }

}
