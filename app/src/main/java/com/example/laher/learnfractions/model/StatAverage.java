package com.example.laher.learnfractions.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.util.Util;

import java.util.Comparator;

public class StatAverage extends SeatWork implements Comparable<StatAverage>{
    private static final String TAG = "StatAverage";
    private double score_average;
    private long time_spent_average;
    private int students_answered;

    public StatAverage(String topicName, int seatWorkNum) {
        super(topicName, seatWorkNum);
    }

    public double getScore_average() {
        return score_average;
    }

    public void setScore_average() {
        double num = getItems_size() * getStudents_answered();
        double avg = (getCorrect() / num) * 100;
        avg = Util.round(avg, 2);
        this.score_average = avg;
    }

    public long getTime_spent_average() {
        return time_spent_average;
    }

    public void setTime_spent_average() {
        long avg = getTimeSpent() / students_answered;
        this.time_spent_average = avg;
    }

    public int getStudents_answered() {
        return students_answered;
    }

    public void setStudents_answered(int students_answered) {
        this.students_answered = students_answered;
    }

    public void addStats(SeatWork seatWork){
        int corrects = getCorrect() + seatWork.getCorrect();
        setCorrect(corrects);
        long time_spent = getTimeSpent() + seatWork.getTimeSpent();
        setTimeSpent(time_spent);
        setItems_size(seatWork.getItems_size());
        students_answered = students_answered + 1;
        setScore_average();
        setTime_spent_average();
    }

    @Override
    public int compareTo(@NonNull StatAverage o) {
        int o1Position = 0;
        int o2Position = 0;
        int i = 0;
        for (Lesson lesson : LessonArchive.getAllLessons()){
            if (o.getTopicName().equals(lesson.getTitle())){
                o1Position = i;
            }
            if (this.getTopicName().equals(lesson.getTitle())){
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
