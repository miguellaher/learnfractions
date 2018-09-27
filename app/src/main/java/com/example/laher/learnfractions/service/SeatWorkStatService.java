package com.example.laher.learnfractions.service;

import android.content.Context;

import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class SeatWorkStatService {
    public static void postStat(Context context, SeatWork seatWork, Service service){
        RequestParams params = new RequestParams();

        String id = Util.generateId();

        String userType = Storage.load(context, Storage.USER_TYPE);

        String teacher_code = null;
        String student_id = null;
        if (userType.equals(AppConstants.STUDENT)) {
            teacher_code = Storage.load(context, Storage.TEACHER_CODE);
            student_id = Storage.load(context, Storage.STUDENT_ID);
        }

        String user_code = null;
        String user_id = null;
        if (userType.equals(AppConstants.USER)){
            user_code = AppConstants.USER_CODE;
            user_id = Storage.load(context, Storage.USER_ID);
        }

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

        if (teacher_code!=null) {
            params.put("teacher_code", teacher_code);
        } else if (user_code!=null){
            params.put("teacher_code", user_code);
        }

        if (student_id!=null) {
            params.put("student_id", student_id);
        } else if (user_id!=null){
            params.put("student_id", user_id);
        }

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
    public static void getStats(Context context, String studentID, Service service){
        RequestParams params = new RequestParams();

        String teacherCode = Storage.load(context, Storage.TEACHER_CODE);

        params.put("teacher_code", teacherCode);
        params.put("student_id", studentID);

        service.get("http://jabahan.com/learnfractions/sw_stat/getStats.php", params);
        service.execute();
    }
    public static void getStats(Context context, Service service){
        RequestParams params = new RequestParams();

        String userType = Storage.load(context, Storage.USER_TYPE);

        String teacher_code = null;
        String student_id = null;
        if (userType.equals(AppConstants.STUDENT)) {
            teacher_code = Storage.load(context, Storage.TEACHER_CODE);
            student_id = Storage.load(context, Storage.STUDENT_ID);
        }
        String user_code = null;
        String user_id = null;
        if (userType.equals(AppConstants.USER)) {
            user_code = AppConstants.USER_CODE;
            user_id = Storage.load(context, Storage.USER_ID);
        }

        if (teacher_code!=null) {
            params.put("teacher_code", teacher_code);
        }
        if (student_id!=null) {
            params.put("student_id", student_id);
        }

        if (user_code!=null){
            params.put("teacher_code", user_code);
        }
        if (user_id!=null){
            params.put("student_id", user_id);
        }

        service.get("http://jabahan.com/learnfractions/sw_stat/getStats.php", params);
        service.execute();
    }
    public static void getAllStats(Context context, Service service){
        RequestParams params = new RequestParams();

        String userType = Storage.load(context,Storage.USER_TYPE);

        if (userType.equals(AppConstants.USER)){
            String userCode = AppConstants.USER_CODE;

            params.put("teacher_code", userCode);
        } else { // if user type is either a student or teacher
            String teacherCode = Storage.load(context, Storage.TEACHER_CODE);

            params.put("teacher_code", teacherCode);
        }

        service.get("http://jabahan.com/learnfractions/sw_stat/getAllStats.php", params);
        service.execute();
    }
}