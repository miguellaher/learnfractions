package com.example.laher.learnfractions.service;

import android.content.Context;

import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class SeatWorkService {
    public static void insert(Context context, SeatWork seatWork, Service service){
        RequestParams requestParams = new RequestParams();

        String id = Util.generateId();
        String seatWorkID = seatWork.getId();
        String teacherCode = Storage.load(context, Storage.TEACHER_CODE);
        String topicName = seatWork.getTopicName();
        int itemSize = seatWork.getItems_size();
        Range range = seatWork.getRange();
        int minimum = range.getMinimum();
        int maximum = range.getMaximum();

        requestParams.put("sw_id", id);
        requestParams.put("seatwork_id", seatWorkID);
        requestParams.put("teacher_code", teacherCode);
        requestParams.put("topic_name", topicName);
        requestParams.put("item_size", itemSize);
        requestParams.put("r_minimum", minimum);
        requestParams.put("r_maximum", maximum);

        service.post("http://jabahan.com/learnfractions/seatwork/insert.php",requestParams);
        service.execute();
    }

    public static void getUpdates(Context context, Service service){
        RequestParams requestParams = new RequestParams();
        String teacherCode = Storage.load(context, Storage.TEACHER_CODE);
        requestParams.put("teacher_code", teacherCode);
        service.get("http://jabahan.com/learnfractions/seatwork/getUpdates.php", requestParams);
        service.execute();
    }
}