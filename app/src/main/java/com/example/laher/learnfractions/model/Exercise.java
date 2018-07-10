package com.example.laher.learnfractions.model;

public class Exercise {
    String topicName;

    int exerciseNum;
    int requiredCorrects;
    boolean rc_consecutive;
    int maxErrors;
    boolean me_consecutive;

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
