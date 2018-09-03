package com.example.laher.learnfractions.model;

import android.support.annotation.NonNull;

import com.example.laher.learnfractions.parent_activities.SeatWork;

public class Student_SW_Progress extends SeatWork implements Comparable<Student_SW_Progress>{
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public int compareTo(@NonNull Student_SW_Progress o) {
        if (getCorrect()>o.getCorrect()){
            return -1;
        } else if (getCorrect()<o.getCorrect()){
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
