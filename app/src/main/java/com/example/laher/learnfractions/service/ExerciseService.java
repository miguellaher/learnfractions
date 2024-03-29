package com.example.laher.learnfractions.service;

import android.content.Context;

import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class ExerciseService {
    //private static final String TAG = "EXERCISE_SERVICE";
    public static void post(Context context, LessonExercise lessonExercise, Service service){
        RequestParams requestParams = new RequestParams();
        String id = lessonExercise.getId();
        String title = lessonExercise.getExerciseTitle();
        String teacher_code = Storage.load(context, Storage.TEACHER_CODE);
        int itemsSize = lessonExercise.getItemsSize();
        int maxItemsSize = lessonExercise.getMaxItemSize();
        int maxWrong = lessonExercise.getMaxWrong();
        Range range = lessonExercise.getRange();
        int minimum = range.getMinimum();
        int maximum = range.getMaximum();
        boolean isCorrectsShouldBeConsecutive = lessonExercise.isCorrectsShouldBeConsecutive();
        boolean isWrongsShouldBeConsecutive = lessonExercise.isWrongsShouldBeConsecutive();
        boolean isRangeEditable = lessonExercise.isRangeEditable();
        requestParams.put("table_id", Util.generateId());
        requestParams.put("exercise_id", id);
        requestParams.put("teacher_code", teacher_code);
        requestParams.put("title", title);
        requestParams.put("required_corrects", itemsSize);
        requestParams.put("max_items", maxItemsSize);
        requestParams.put("max_errors", maxWrong);
        if (isCorrectsShouldBeConsecutive) {
            requestParams.put("rc_consecutive", "1");
        } else {
            requestParams.put("rc_consecutive", "0");
        }
        if (isWrongsShouldBeConsecutive) {
            requestParams.put("me_consecutive", "1");
        } else {
            requestParams.put("me_consecutive", "0");
        }
        if (isRangeEditable) {
            requestParams.put("r_editable", "1");
        } else {
            requestParams.put("r_editable", "0");
        }
        requestParams.put("minimum", minimum);
        requestParams.put("maximum", maximum);
        service.post("http://jabahan.com/learnfractions/exercise/insert.php", requestParams);
        service.execute();
    }

    public static void getUpdate(Context context, LessonExercise exercise, Service service){
        RequestParams params = new RequestParams();
        String teacher_code = Storage.load(context, Storage.TEACHER_CODE);
        String id = exercise.getId();
        params.put("exercise_id", id);
        params.put("teacher_code", teacher_code);
        service.get("http://jabahan.com/learnfractions/exercise/getUpdate.php", params);
        service.execute();
    }

    public static void getUpdates(Context context, Service service){
        RequestParams params = new RequestParams();
        String teacher_code = Storage.load(context,Storage.TEACHER_CODE);
        params.put("teacher_code", teacher_code);
        service.get("http://jabahan.com/learnfractions/exercise/getUpdates.php", params);
        service.execute();
    }

    public static void getUpdate(Exercise exercise, Student student, Service service) {

    }
}
