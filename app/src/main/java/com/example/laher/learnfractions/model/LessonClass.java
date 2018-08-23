package com.example.laher.learnfractions.model;

import java.util.ArrayList;

public class LessonClass {
    private String id;
    private String title;
    private Class startingActivity;
    private boolean enabled;
    private ArrayList<ExerciseStat> exercises;

    public ArrayList<ExerciseStat> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<ExerciseStat> exercises) {
        this.exercises = exercises;
    }

    public LessonClass(String title, Class startingActivity) {
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
