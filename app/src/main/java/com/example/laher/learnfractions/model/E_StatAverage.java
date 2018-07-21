package com.example.laher.learnfractions.model;

import android.support.annotation.NonNull;

import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.util.Util;

public class E_StatAverage extends Exercise implements Comparable<E_StatAverage>{
    private static final String TAG = "E_StatAverage";
    private double error_average;
    private long time_spent_average;
    private int students_answered;

    public E_StatAverage(String topicName, int exerciseNum) {
        super(topicName, exerciseNum);
    }

    public double getError_average() {
        return error_average;
    }

    private void setErrorAverage() {
        double avg = getErrors() / getStudents_answered();
        avg = Util.round(avg, 2);
        this.error_average = avg;
    }

    public long getTime_spent_average() {
        return time_spent_average;
    }

    private void setTime_spent_average() {
        long avg = getTime_spent() / students_answered;
        this.time_spent_average = avg;
    }

    public int getStudents_answered() {
        return students_answered;
    }

    public void setStudents_answered(int students_answered) {
        this.students_answered = students_answered;
    }

    public void addStats(ExerciseStat exerciseStat){
        int errors = getErrors() + exerciseStat.getErrors();
        setErrors(errors);
        long time_spent = getTime_spent() + exerciseStat.getTime_spent();
        setTime_spent(time_spent);
        students_answered = students_answered + 1;
        setErrorAverage();
        setTime_spent_average();
    }

    @Override
    public int compareTo(@NonNull E_StatAverage o) {
        int o1Position = 0;
        int o2Position = 0;
        int i = 0;
        for (Lesson lesson : LessonArchive.getAllLessons()){
            if (o.getTopicName().equals(lesson.getTitle())){
                o1Position = i;
            }
            if (this.getTopicName().equals(lesson.getTitle())){
                if (this.getExerciseNum()>o.getExerciseNum()){
                    i++;
                } else if (this.getExerciseNum()<o.getExerciseNum()){
                    i--;
                }
                o2Position = i;
            }
            i++;
        }
        if (o1Position>o2Position){
            return -1;
        }
        if (o1Position<o2Position){
            return 1;
        }
        return 0;
    }
}
