package com.example.laher.learnfractions.service;

import android.content.Context;
import android.util.Log;

import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class SeatWorkStatService {
    private static final String TAG = "SW_STAT_SERVICE";
    public static void postStat(Context context, SeatWork seatWork, Service service){
        RequestParams params = new RequestParams();

        String id = Util.generateId();
        String teacher_code = Storage.load(context, Storage.TEACHER_CODE);
        String student_id = Storage.load(context, Storage.STUDENT_ID);
        String seatwork_id = seatWork.getId();
        int itemsSize = seatWork.getItems_size();
        String strItemSize = String.valueOf(itemsSize);

        Range range = seatWork.getRange();
        int minimum = range.getMinimum();
        int maximum = range.getMaximum();
        String strMinimum = String.valueOf(minimum);
        String strMaximum = String.valueOf(maximum);

        String topicName = seatWork.getTopicName();
        int score = seatWork.getCorrect();
        String strScore = String.valueOf(score);
        long timeSpent = seatWork.getTimeSpent();
        String strTimeSpent = String.valueOf(timeSpent);

        params.put("stat_id", id);
        params.put("teacher_code", teacher_code);
        params.put("student_id", student_id);
        params.put("seatwork_id", seatwork_id);
        params.put("items_size", strItemSize);
        params.put("r_minimum", strMinimum);
        params.put("r_maximum", strMaximum);
        params.put("topic_name", topicName);
        params.put("score", strScore);
        params.put("time_spent", strTimeSpent);

        service.post("http://jabahan.com/learnfractions/sw_stat/insert.php",params);
        service.execute();
    }
    public static void getStats(Student student, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("teacher_code", student.getTeacher_code());
        requestParams.put("student_id", student.getId());
        Log.d(TAG, "getStats(): teacher_code: " + student.getTeacher_code());
        Log.d(TAG, "getStats(): student_id: " + student.getId());
        service.get("http://jabahan.com/learnfractions/sw_stat/getStats.php", requestParams);
        service.execute();
    }
    public static void getAllStats(String teacher_code, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("teacher_code", teacher_code);
        service.get("http://jabahan.com/learnfractions/sw_stat/getAllStats.php", requestParams);
        service.execute();
    }
}