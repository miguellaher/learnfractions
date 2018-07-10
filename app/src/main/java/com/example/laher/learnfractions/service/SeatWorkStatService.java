package com.example.laher.learnfractions.service;

import android.util.Log;

import com.example.laher.learnfractions.model.SeatWork;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class SeatWorkStatService {
    private static final String TAG = "SW_STAT_SERVICE";
    public static void postStat(SeatWork seatWork, Student student, Service service){
        RequestParams requestParams = new RequestParams();
        String id = Util.generateId();
        requestParams.put("stat_id", id);
        requestParams.put("teacher_code",student.getTeacher_code());
        requestParams.put("student_id",student.getId());
        requestParams.put("topic_name",seatWork.getTopicName());
        requestParams.put("sw_num",seatWork.getSeatWorkNum());
        requestParams.put("score",seatWork.getCorrect());
        requestParams.put("item_size",seatWork.getItems_size());
        int time_spent_seconds = (int)(seatWork.getTimeSpent()/1000);
        requestParams.put("time_spent",time_spent_seconds);
        service.post("http://jabahan.com/learnfractions/sw_stat/postStat.php",requestParams);
        Log.d(TAG, "id: " + id);
        Log.d(TAG, "teacher_code: " + student.getTeacher_code());
        Log.d(TAG, "student_id: " + student.getId());
        Log.d(TAG, "topic_name: " + seatWork.getTopicName());
        Log.d(TAG, "sw_num: " + seatWork.getSeatWorkNum());
        Log.d(TAG, "score: " + seatWork.getCorrect());
        Log.d(TAG, "item_size: " + seatWork.getItems_size());
        Log.d(TAG, "time_spent: " + time_spent_seconds);
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