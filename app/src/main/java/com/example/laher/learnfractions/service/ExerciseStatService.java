package com.example.laher.learnfractions.service;

import android.util.Log;

import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.model.ExerciseStat;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class ExerciseStatService {
    private static final String TAG = "Exercise_Stat";
    public static void postStats(Student student, ExerciseStat exerciseStat, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("stat_id", Util.generateId());
        requestParams.put("student_id", student.getId());
        requestParams.put("teacher_code", student.getTeacher_code());
        requestParams.put("topic_name", exerciseStat.getTopicName());
        requestParams.put("exercise_num", exerciseStat.getExerciseNum());
        if (exerciseStat.isDone()){
            requestParams.put("done", "1");
        } else {
            requestParams.put("done", "0");
        }
        requestParams.put("time_spent", exerciseStat.getTime_spent());
        requestParams.put("stat_errors", exerciseStat.getErrors());
        requestParams.put("required_corrects", exerciseStat.getRequiredCorrects());
        if (exerciseStat.isRc_consecutive()){
            requestParams.put("rc_consecutive", "1");
        } else {
            requestParams.put("rc_consecutive", "0");
        }
        requestParams.put("max_errors", exerciseStat.getMaxErrors());
        if (exerciseStat.isMe_consecutive()){
            requestParams.put("me_consecutive", "1");
        } else {
            requestParams.put("me_consecutive", "0");
        }
        Log.d(TAG, "ATTRIBUTES: teacher_code: " + student.getTeacher_code() + "; student_id: " + student.getId() + "topic_name: " +
                exerciseStat.getTopicName() + "; exercise_num: " + exerciseStat.getExerciseNum() + "; done: " + exerciseStat.isDone() +
                "; time_spent: " + exerciseStat.getTime_spent() + "; errors: " + exerciseStat.getErrors() + "; required_corrects: " +
                exerciseStat.getRequiredCorrects() + "; rc_consecutive: " + exerciseStat.isRc_consecutive() + "; max_errors: " +
                exerciseStat.getMaxErrors() + "; me_consecutive: " + exerciseStat.isMe_consecutive());
        service.post("http://jabahan.com/learnfractions/e_stat/create.php", requestParams);
        service.execute();
    }
    public static void post(LessonExercise exercise, Service service){
        RequestParams params = new RequestParams();
        Student student = exercise.getStudent();


        String teacher_code = student.getTeacher_code();
        String student_id = student.getId();
        String exercise_id = exercise.getId();
        String title = exercise.getExerciseTitle();

        long timeSpent = exercise.getTimeSpent();
        int totalWrongs = exercise.getTotalWrongs();

        int items_size = exercise.getItemsSize();
        boolean rcc = exercise.isCorrectsShouldBeConsecutive();
        int max_wrong = exercise.getMaxWrong();
        boolean mec = exercise.isWrongsShouldBeConsecutive();

        Range range = exercise.getRange();
        int minimum = range.getMinimum();
        int maximum = range.getMaximum();

        String strTimeSpent = String.valueOf(timeSpent);
        String strTotalWrongs = String.valueOf(totalWrongs);
        Log.d(TAG, "total wrongs: " + strTotalWrongs);
        String strItemsSize = String.valueOf(items_size);
        String strRCC = "0";
        if (rcc){
            strRCC = "1";
        }
        String strMaxWrong = String.valueOf(max_wrong);
        String strMEC = "1";
        if (!mec){
            strMEC = "0";
        }
        String strMinimum = String.valueOf(minimum);
        String strMaximum = String.valueOf(maximum);

        params.put("stat_id", Util.generateId());
        params.put("teacher_code", teacher_code);
        params.put("student_id", student_id);
        params.put("exercise_id", exercise_id);
        params.put("title", title);

        params.put("time_spent", strTimeSpent);
        params.put("total_wrongs", strTotalWrongs);
        params.put("required_corrects", strItemsSize);
        params.put("rc_consecutive", strRCC);
        params.put("max_errors", strMaxWrong);
        params.put("me_consecutive", strMEC);

        params.put("minimum", strMinimum);
        params.put("maximum", strMaximum);

        service.post("http://jabahan.com/learnfractions/e_stat/insert.php", params);
        service.execute();
    }
    public static void getAllStats(String teacher_code, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("teacher_code", teacher_code);
        service.get("http://jabahan.com/learnfractions/e_stat/getAllStats.php", requestParams);
        service.execute();
    }
}

