package com.example.laher.learnfractions.classes;

import android.support.annotation.NonNull;

import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.parent_activities.SeatWork;

public class ChapterExamRank implements Comparable<ChapterExamRank>{
    private Student student;
    private int totalScore;
    private int totalItemsSize;
    private long totalTimeSpent;
    private double averageScore;
    private int examDone;

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

    public int getExamDone() {
        return examDone;
    }

    public Student getStudent() {
        return student;
    }

    public ChapterExamRank(Student student) {
        this.student = student;
        //seatWorksStats = new ArrayList<>();
        totalScore = 0;
        totalItemsSize = 0;
        totalTimeSpent = 0;
        examDone = 0;
    }

    public void addChapterExam(ChapterExam chapterExam){
        int score = chapterExam.getTotalScore();
        int itemsSize = chapterExam.getTotalItems();
        long timeSpent = chapterExam.getTimeSpent();

        totalScore = totalScore + score;
        totalItemsSize = totalItemsSize + itemsSize;
        totalTimeSpent = totalTimeSpent + timeSpent;

        averageScore = (double) totalScore / totalItemsSize;

        examDone++;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChapterExamRank){
            ChapterExamRank seatWorkRank = (ChapterExamRank) obj;

            Student objStudent = seatWorkRank.getStudent();
            Student thisStudent = this.getStudent();

            return thisStudent.equals(objStudent);
        }
        return super.equals(obj);
    }

    @Override
    public int compareTo(@NonNull ChapterExamRank o) {
        int thisExamDone = this.getExamDone();
        int oExamDone = o.getExamDone();

        if (thisExamDone>oExamDone){
            return -1;
        } else if (thisExamDone<oExamDone){
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
