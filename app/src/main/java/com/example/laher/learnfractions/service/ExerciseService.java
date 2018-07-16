package com.example.laher.learnfractions.service;

import android.util.Log;

import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class ExerciseService {
    private static final String TAG = "EXERCISE_SERVICE";
    public static void create(Teacher teacher, Exercise exercise, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("exercise_id", Util.generateId());
        requestParams.put("teacher_id", teacher.getId());
        requestParams.put("teacher_code", teacher.getTeacher_code());
        requestParams.put("topic_name", exercise.getTopicName());
        requestParams.put("exercise_num", exercise.getExerciseNum());
        requestParams.put("required_corrects", exercise.getRequiredCorrects());
        if(exercise.getMaxErrors() > 0) {
            if (exercise.isRc_consecutive()) {
                requestParams.put("rc_consecutive", "1");
            } else {
                requestParams.put("rc_consecutive", "0");
            }
            requestParams.put("max_errors", exercise.getMaxErrors());
            if (exercise.isMe_consecutive()) {
                requestParams.put("me_consecutive", "1");
            } else {
                requestParams.put("me_consecutive", "0");
            }
        }
        service.post("http://jabahan.com/learnfractions/exercise/create.php", requestParams);
        service.execute();
    }

    public static void getUpdate(Exercise exercise, Student student, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("teacher_code", student.getTeacher_code());
        Log.d(TAG, "teacher code:" +student.getTeacher_code());
        requestParams.put("topic_name", exercise.getTopicName());
        Log.d(TAG, "topic name:" +exercise.getTopicName());
        requestParams.put("exercise_num", exercise.getExerciseNum());
        Log.d(TAG, "exercise num:" +exercise.getExerciseNum());
        service.get("http://jabahan.com/learnfractions/exercise/getUpdate.php",requestParams);
        service.execute();
    }

}
