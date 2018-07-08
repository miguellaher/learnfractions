package com.example.laher.learnfractions.service;

import com.example.laher.learnfractions.model.SeatWork;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class SeatWorkService {
    public static void updateSeatWork(Teacher teacher, SeatWork seatWork, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("sw_id", Util.generateId());
        requestParams.put("teacher_id", teacher.getId());
        requestParams.put("teacher_code", teacher.getTeacher_code());
        requestParams.put("topic_name", seatWork.getTopicName());
        requestParams.put("sw_num", seatWork.getSeatWorkNum());
        requestParams.put("item_size", seatWork.getItems_size());
        service.post("http://jabahan.com/learnfractions/seatwork/create.php",requestParams);
        service.execute();
    }

    public static void getUpdates(String teacher_code, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("teacher_code", teacher_code);
        service.get("http://jabahan.com/learnfractions/seatwork/getUpdates.php", requestParams);
        service.execute();
    }
}