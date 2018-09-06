package com.example.laher.learnfractions.service;

import android.content.Context;

import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class ExamStatService {
    public static void postStats(Context context, ChapterExam chapterExam, Service service){
        RequestParams params = new RequestParams();
        String id = Util.generateId();
        String studentID = Storage.load(context,Storage.STUDENT_ID);
        String teacherCode = Storage.load(context,Storage.TEACHER_CODE);
        int examNumber = chapterExam.getExamNumber();
        String strExamNumber = String.valueOf(examNumber);
        String examTitle = chapterExam.getExamTitle();
        String compiledSeatWorksStats = chapterExam.getCompiledSeatWorksStats();
        String compiledSeatWorks = chapterExam.getCompiledSeatWorks();

        params.put("stat_id", id);
        params.put("student_id", studentID);
        params.put("teacher_code", teacherCode);
        params.put("exam_number", strExamNumber);
        params.put("exam_title", examTitle);
        params.put("seat_works_stats", compiledSeatWorksStats);
        params.put("seatworks", compiledSeatWorks);

        service.post("http://jabahan.com/learnfractions/exam_stat/insert.php", params);
        service.execute();
    }

    public static void getStudentStats(Context context, Service service){
        RequestParams params = new RequestParams();
        String teacherCode = Storage.load(context,Storage.TEACHER_CODE);
        String studentId = Storage.load(context, Storage.STUDENT_ID);

        params.put("teacher_code", teacherCode);
        params.put("student_id", studentId);

        service.get("http://jabahan.com/learnfractions/exam_stat/getStudentStats.php", params);
        service.execute();
    }

    public static void getAllStats(String teacher_code, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("teacher_code", teacher_code);
        service.get("http://jabahan.com/learnfractions/exam_stat/getAllStats.php", requestParams);
        service.execute();
    }
}
