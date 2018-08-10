package com.example.laher.learnfractions.model;

import android.support.annotation.NonNull;

public class ExerciseStat extends Exercise implements Comparable<ExerciseStat> {
    private Student student;
    private int students_answered;

    public int getStudents_answered() {
        return students_answered;
    }

    public void setStudents_answered(int students_answered) {
        this.students_answered = students_answered;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ExerciseStat(String title, int i, int i1, boolean b, int i2, boolean b1) {
        super(title,i,i1,b,i2,b1);
    }

    public ExerciseStat(String title, int i, int i1) {
        super(title,i,i1);
    }

    public ExerciseStat() {

    }

    public void incrementError(){
        int error = getErrors();
        error++;
        setErrors(error);
    }

    @Override
    public int compareTo(@NonNull ExerciseStat o) {
        int errors1 = getErrors();
        int errors2 = o.getErrors();
        if (errors1>errors2){
            return 1;
        } else if (errors1<errors2){
            return -1;
        } else {
            long time1 = getTime_spent();
            long time2 = o.getTime_spent();
            if (time1>time2){
                return 1;
            } else if (time1<time2){
                return -1;
            }
        }
        return 0;
    }
}
