package com.example.laher.learnfractions.model;

public class BaseModel {
    private String id;
    private String username;
    private String password;
    private String security_question;
    private String security_answer;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getSecurity_question() {
        return security_question;
    }

    public void setSecurity_question(String security_question) {
        this.security_question = security_question;
    }

    public String getSecurity_answer() {
        return security_answer;
    }

    public void setSecurity_answer(String security_answer) {
        this.security_answer = security_answer;
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
}
