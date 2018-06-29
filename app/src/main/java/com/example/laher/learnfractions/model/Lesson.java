package com.example.laher.learnfractions.model;

import java.util.ArrayList;

public class Lesson {
    String id;
    String title;
    Class startingActivity;
    boolean enabled;

    ArrayList<Exercise> exercises;

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Lesson(String title, Class startingActivity) {
        this.title = title;
        this.startingActivity = startingActivity;
        enabled = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getStartingActivity() {
        return startingActivity;
    }

    public void setStartingActivity(Class startingActivity) {
        this.startingActivity = startingActivity;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
