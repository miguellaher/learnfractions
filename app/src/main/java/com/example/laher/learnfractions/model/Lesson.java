package com.example.laher.learnfractions.model;

public class Lesson {
    String title;
    Class startingActivity;
    boolean enabled;

    public Lesson(String title, Class startingActivity) {
        this.title = title;
        this.startingActivity = startingActivity;
        enabled = true;
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
