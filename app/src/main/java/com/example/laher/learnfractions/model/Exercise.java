package com.example.laher.learnfractions.model;

public class Exercise {
    private String topicName;

    private int exerciseNum;
    private int requiredCorrects;
    private boolean rc_consecutive;
    private int maxErrors;
    private boolean me_consecutive;
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
        if (time_spent >= 1000) {
            return time_spent / 1000;
        }
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

    public Exercise(String topicName, int exerciseNum, int requiredCorrects, boolean rc_consecutive, int maxErrors, boolean me_consecutive) {
        this.topicName = topicName;
        this.exerciseNum = exerciseNum;
        this.requiredCorrects = requiredCorrects;
        this.rc_consecutive = rc_consecutive;
        this.maxErrors = maxErrors;
        this.me_consecutive = me_consecutive;
    }

    public Exercise(String topicName, int exerciseNum, int requiredCorrects) {
        this.topicName = topicName;
        this.exerciseNum = exerciseNum;
        this.requiredCorrects = requiredCorrects;
    }

    public Exercise(){}

    public Exercise(String topicName, int exerciseNum) {
        this.topicName = topicName;
        this.exerciseNum = exerciseNum;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getExerciseNum() {
        return exerciseNum;
    }

    public void setExerciseNum(int exerciseNum) {
        this.exerciseNum = exerciseNum;
    }

    public int getRequiredCorrects() {
        return requiredCorrects;
    }

    public void setRequiredCorrects(int requiredCorrects) {
        this.requiredCorrects = requiredCorrects;
    }

    public boolean isRc_consecutive() {
        return rc_consecutive;
    }

    public void setRc_consecutive(boolean rc_consecutive) {
        this.rc_consecutive = rc_consecutive;
    }

    public int getMaxErrors() {
        return maxErrors;
    }

    public void setMaxErrors(int maxErrors) {
        this.maxErrors = maxErrors;
    }

    public boolean isMe_consecutive() {
        return me_consecutive;
    }

    public void setMe_consecutive(boolean me_consecutive) {
        this.me_consecutive = me_consecutive;
    }
}
