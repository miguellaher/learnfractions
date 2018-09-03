package com.example.laher.learnfractions.service;

import android.util.Log;

import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class ExamStatService {
    private static final String TAG = "ExamStatService";
    public static void postStats(Service service, Student student, ChapterExam chapterExam){
        RequestParams requestParams = new RequestParams();
        String id = Util.generateId();
        requestParams.put("stat_id", id);
        requestParams.put("student_id", student.getId());
        requestParams.put("teacher_code", student.getTeacher_code());
        requestParams.put("exam_title", chapterExam.getExamTitle());
        requestParams.put("score", chapterExam.getTotalScore());
        requestParams.put("item_size", chapterExam.getTotalItems());
        requestParams.put("time_spent", chapterExam.getTimeSpent());
        StringBuilder string_seatWorks = new StringBuilder();
        for (SeatWork seatWork : chapterExam.getSeatWorkStats()){
            string_seatWorks.append(seatWork.getTopicName());
            string_seatWorks.append(":");
            string_seatWorks.append(seatWork.getSeatWorkNum());
            string_seatWorks.append(":");
            string_seatWorks.append(seatWork.getItems_size());
            string_seatWorks.append(";");
        }
        StringBuilder string_seatWorksStats = new StringBuilder();
        for (SeatWork seatWork : chapterExam.getSeatWorkStats()){
            string_seatWorksStats.append(seatWork.getTopicName());
            string_seatWorksStats.append(":");
            string_seatWorksStats.append(seatWork.getSeatWorkNum());
            string_seatWorksStats.append(":");
            string_seatWorksStats.append(seatWork.getCorrect());
            string_seatWorksStats.append(":");
            string_seatWorksStats.append(seatWork.getTimeSpent());
            string_seatWorksStats.append(";");
        }
        requestParams.put("seat_works", string_seatWorks);
        requestParams.put("seat_works_stats", string_seatWorksStats);
        Log.d(TAG, "stat_id: " + id + "; student_id: " + student.getId() + "; teacher_code: " + student.getTeacher_code()
                + "; exam_title: " + chapterExam.getExamTitle()
                + "; score: " + chapterExam.getTotalScore()
                + "; item_size: " + chapterExam.getTotalItems()
                + "; time_spent: " + chapterExam.getTimeSpent()
                + "; string_seatWorks: " + string_seatWorks
                + "; string_seatWorksStats: " + string_seatWorksStats);
        service.post("http://jabahan.com/learnfractions/exam_stat/create.php", requestParams);
        service.execute();
    }

    public static void getStudentStats(Service service, Student student){
        RequestParams requestParams = new RequestParams();
        requestParams.put("teacher_code", student.getTeacher_code());
        requestParams.put("student_id", student.getId());
        service.get("http://jabahan.com/learnfractions/exam_stat/getStats.php", requestParams);
        service.execute();
    }

    public static void getAllStats(String teacher_code, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("teacher_code", teacher_code);
        service.get("http://jabahan.com/learnfractions/exam_stat/getAllStats.php", requestParams);
        service.execute();
    }
}
