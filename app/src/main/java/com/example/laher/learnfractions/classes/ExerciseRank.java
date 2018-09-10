package com.example.laher.learnfractions.classes;

import android.support.annotation.NonNull;

import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.parent_activities.LessonExercise;

public class ExerciseRank implements Comparable<ExerciseRank>{
    private Student student;
    private int totalErrors;
    private int totalItemsSize;
    private long totalTimeSpent;
    private double averageError;
    private int exercisesDone;

    public int getTotalErrors() {
        return totalErrors;
    }

    public int getTotalItemsSize() {
        return totalItemsSize;
    }

    public long getTotalTimeSpent() {
        return totalTimeSpent;
    }

    private double getAverageError() {
        return averageError;
    }

    public int getExercisesDone() {
        return exercisesDone;
    }

    public Student getStudent() {
        return student;
    }

    public ExerciseRank(Student student) {
        this.student = student;
        //seatWorksStats = new ArrayList<>();
        totalErrors = 0;
        totalItemsSize = 0;
        totalTimeSpent = 0;
        exercisesDone = 0;
    }

    public void addExercise(LessonExercise lessonExercise){
        int errors = lessonExercise.getTotalWrongs();
        int itemsSize = lessonExercise.getItemsSize();
        long timeSpent = lessonExercise.getTimeSpent();

        totalErrors = totalErrors + errors;
        totalItemsSize = totalItemsSize + itemsSize;
        totalTimeSpent = totalTimeSpent + timeSpent;

        averageError = (double) totalErrors / totalItemsSize;

        exercisesDone++;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ExerciseRank){
            ExerciseRank seatWorkRank = (ExerciseRank) obj;

            Student objStudent = seatWorkRank.getStudent();
            Student thisStudent = this.getStudent();

            return thisStudent.equals(objStudent);
        }
        return super.equals(obj);
    }

    @Override
    public int compareTo(@NonNull ExerciseRank o) {
        int thisExercisesDone = this.getExercisesDone();
        int oExercisesDone = o.getExercisesDone();

        if (thisExercisesDone>oExercisesDone){
            return -1;
        } else if (thisExercisesDone<oExercisesDone){
            return 1;
        } else {
            double thisAvgError = this.getAverageError();
            double oAvgError = o.getAverageError();

            if (thisAvgError<oAvgError){
                return -1;
            } else if (thisAvgError>oAvgError){
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
