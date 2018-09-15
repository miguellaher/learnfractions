package com.example.laher.learnfractions.service;

import android.content.Context;

import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class ExamService {
    public static void postExam(Context context, ChapterExam chapterExam, Service service){
        RequestParams params = new RequestParams();

        String id = Util.generateId();
        String teacherCode = Storage.load(context, Storage.TEACHER_CODE);
        int examNumber = chapterExam.getExamNumber();
        String strExamNumber = String.valueOf(examNumber);
        String examTitle = chapterExam.getExamTitle();
        String compiledSeatWorks = chapterExam.getCompiledSeatWorks();

        params.put("id", id);
        params.put("teacher_code", teacherCode);
        params.put("exam_number", strExamNumber);
        params.put("exam_title", examTitle);
        params.put("seat_works", compiledSeatWorks);

        service.post("http://jabahan.com/learnfractions/exam/insert.php", params);
        service.execute();
    }
    public static void getExams(Context context, Service service){
        RequestParams params = new RequestParams();

        String userType = Storage.load(context, Storage.USER_TYPE);

        String teacher_code = null;
        if (userType.equals(AppConstants.STUDENT)) {
            teacher_code = Storage.load(context, Storage.TEACHER_CODE);
        }
        String user_code = null;
        if (userType.equals(AppConstants.USER)) {
            user_code = AppConstants.USER_CODE;
        }

        if (teacher_code!=null) {
            params.put("teacher_code", teacher_code);
        } else if (user_code!=null){
            params.put("teacher_code", user_code);
        }

        service.get("http://jabahan.com/learnfractions/exam/getUpdates.php", params);
        service.execute();
    }
}
