package com.example.laher.learnfractions.model;

import android.support.annotation.NonNull;

public class ExamStat extends ChapterExam implements Comparable<ExamStat> {
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public long getTimeSpent() {
        return super.getTimeSpent() / 1000;
    }

    @Override
    public int compareTo(@NonNull ExamStat o) {
        if (getTotalScore()>o.getTotalScore()){
            return -1;
        } else if (getTotalScore()<o.getTotalScore()){
            return 1;
        } else {
            if (getTimeSpent()<o.getTimeSpent()){
                return -1;
            } else if (getTimeSpent()>o.getTimeSpent()){
                return 1;
            }
        }
        return 0;
    }
}
