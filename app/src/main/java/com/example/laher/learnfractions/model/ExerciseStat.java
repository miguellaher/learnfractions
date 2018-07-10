package com.example.laher.learnfractions.model;

public class ExerciseStat extends Exercise {
    private boolean done;
    private long time_spent;
    private int errors;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public long getTime_spent() {
        return time_spent;
    }

    public void setTime_spent(long time_spent) {
        this.time_spent = time_spent;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public void incrementError(){
        this.errors++;
    }
}
