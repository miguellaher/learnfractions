package com.example.laher.learnfractions.model;

import android.support.annotation.NonNull;

public class ExamStatAverage extends ChapterExam implements Comparable<ExamStatAverage>{
    private double avgScore;
    private long avgTimeSpent;
    private int students_answered;

    public int getStudents_answered() {
        return students_answered;
    }

    public void setStudents_answered(int students_answered) {
        this.students_answered = students_answered;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }

    public long getAvgTimeSpent() {
        return avgTimeSpent;
    }

    public void setAvgTimeSpent(long avgTimeSpent) {
        this.avgTimeSpent = avgTimeSpent;
    }

    public void setAverageScore(){
        double totalScore = (double) getTotalScore();
        double d_students_answered = (double) getStudents_answered();
        double avg = totalScore / d_students_answered;
        setAvgScore(avg);
    }

    public void addScore(int score){
        int totalScore = getTotalScore();
        totalScore = totalScore + score;
        setTotalScore(totalScore);
        setAverageScore();
    }

    public void setAverageTimeSpent(){
        long timeSpent = getTimeSpent();
        long l_students_answered = (long) getStudents_answered();
        long avgTimeSpent = timeSpent / l_students_answered;
        setAvgTimeSpent(avgTimeSpent);
    }

    public void addTimeSpent(long timeSpent){
        long totalTimeSpent = getTimeSpent();
        totalTimeSpent = totalTimeSpent + timeSpent;
        setTimeSpent(totalTimeSpent);
        setAverageTimeSpent();
    }

    public void addStats(ExamStat examStat){
        this.students_answered++;
        setExamTitle(examStat.getExamTitle());
        addScore(examStat.getTotalScore());
        addTimeSpent(examStat.getTimeSpent());
        setTotalItems(examStat.getTotalItems());
    }

    @Override
    public int compareTo(@NonNull ExamStatAverage o) {
        String o1 = this.getExamTitle();
        String o2 = o.getExamTitle();
        int compare = o1.compareTo(o2);
        if (compare < 0){
            return -1;
        } else if (compare > 0){
            return 1;
        }
        return 0;
    }
}
