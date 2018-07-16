package com.example.laher.learnfractions.service;

import android.util.Log;

import com.example.laher.learnfractions.model.ExerciseStat;
import com.example.laher.learnfractions.model.Student;
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
}

