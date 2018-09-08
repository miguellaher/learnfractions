package com.example.laher.learnfractions.classes;

import android.support.annotation.NonNull;

import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.parent_activities.SeatWork;

public class SeatWorkRank implements Comparable<SeatWorkRank>{
    private Student student;
    private int totalScore;
    private int totalItemsSize;
    private long totalTimeSpent;
    private double averageScore;
    //private ArrayList<SeatWork> seatWorksStats;
    private int swDone;

    public int getTotalItemsSize() {
        return totalItemsSize;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public long getTotalTimeSpent() {
        return totalTimeSpent;
    }

    private double getAverageScore() {
        return averageScore;
    }

    public int getSwDone() {
        return swDone;
    }

    public Student getStudent() {
        return student;
    }

    public SeatWorkRank(Student student) {
        this.student = student;
        //seatWorksStats = new ArrayList<>();
        totalScore = 0;
        totalItemsSize = 0;
        totalTimeSpent = 0;
        swDone = 0;
    }

    public void addSeatWork(SeatWork seatWork){
        int score = seatWork.getCorrect();
        int itemsSize = seatWork.getItems_size();
        long timeSpent = seatWork.getTimeSpent();

        totalScore = totalScore + score;
        totalItemsSize = totalItemsSize + itemsSize;
        totalTimeSpent = totalTimeSpent + timeSpent;

        averageScore = (double) totalScore / totalItemsSize;

        //seatWorksStats.add(seatWork);
        swDone++;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SeatWorkRank){
            SeatWorkRank seatWorkRank = (SeatWorkRank) obj;

            Student objStudent = seatWorkRank.getStudent();
            Student thisStudent = this.getStudent();

            return thisStudent.equals(objStudent);
        }
        return super.equals(obj);
    }

    @Override
    public int compareTo(@NonNull SeatWorkRank o) {
        int thisSWDone = this.getSwDone();
        int oSWDone = o.getSwDone();

        if (thisSWDone>oSWDone){
            return -1;
        } else if (thisSWDone<oSWDone){
            return 1;
        } else {
            double thisAvgScore = this.getAverageScore();
            double oAvgScore = o.getAverageScore();

            if (thisAvgScore>oAvgScore){
                return -1;
            } else if (thisAvgScore<oAvgScore){
                return 1;
            } else {
                long thisTotalTimeSpent = this.getTotalTimeSpent();
                long oTotalTimeSpent = o.getTotalTimeSpent();

                if (thisTotalTimeSpent<oTotalTimeSpent){
                    return -1;
                } else if (thisTotalTimeSpent>oTotalTimeSpent){
                    return 1;
                }
            }
        }
        return 0;
    }
}
