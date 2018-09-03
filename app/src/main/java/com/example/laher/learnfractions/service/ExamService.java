package com.example.laher.learnfractions.service;

import android.util.Log;

import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class ExamService {
    private static final String TAG = "EXAM_SERVICE";
    public static void postExam(Service service, ChapterExam chapterExam, Teacher teacher){
        RequestParams requestParams = new RequestParams();
        String id = Util.generateId();
        requestParams.put("exam_id", id);
        requestParams.put("teacher_code", teacher.getTeacher_code());
        requestParams.put("exam_title", chapterExam.getExamTitle());
        StringBuilder string_seatWorks = new StringBuilder();
        for (SeatWork seatWork : chapterExam.getSeatWorks()){
            string_seatWorks.append(seatWork.getTopicName());
            string_seatWorks.append(":");
            string_seatWorks.append(seatWork.getSeatWorkNum());
            string_seatWorks.append(":");
            string_seatWorks.append(seatWork.getItems_size());
            string_seatWorks.append(";");
        }
        requestParams.put("seat_works", string_seatWorks);
        Log.d(TAG, "exam_id: " + id + "; teacher_code: " + teacher.getTeacher_code()
                + "; exam_title: " + chapterExam.getExamTitle()
                + "; string_seatWorks: " + string_seatWorks);
        service.post("http://jabahan.com/learnfractions/exam/create.php", requestParams);
        service.execute();
    }
    public static void getExams(Service service, String teacher_code){
        RequestParams requestParams = new RequestParams();
        requestParams.put("teacher_code", teacher_code);
        service.get("http://jabahan.com/learnfractions/exam/getUpdates.php", requestParams);
        service.execute();
    }
}
