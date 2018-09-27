package com.example.laher.learnfractions.service;

import android.content.Context;

import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class ExamStatService {
    public static void postStats(Context context, ChapterExam chapterExam, Service service){
        RequestParams params = new RequestParams();
        String id = Util.generateId();

        String userType = Storage.load(context, Storage.USER_TYPE);

        String studentID = null;
        String teacherCode = null;
        if (userType.equals(AppConstants.STUDENT)) {
            studentID = Storage.load(context, Storage.STUDENT_ID);
            teacherCode = Storage.load(context, Storage.TEACHER_CODE);
        }

        String userID = null;
        String userCode = null;
        if (userType.equals(AppConstants.USER)){
            userID = Storage.load(context, Storage.USER_ID);
            userCode = AppConstants.USER_CODE;
        }

        int examNumber = chapterExam.getExamNumber();
        String strExamNumber = String.valueOf(examNumber);
        String examTitle = chapterExam.getExamTitle();
        String compiledSeatWorksStats = chapterExam.getCompiledSeatWorksStats();
        String compiledSeatWorks = chapterExam.getCompiledSeatWorks();

        params.put("stat_id", id);

        if (studentID!=null) {
            params.put("student_id", studentID);
        } else if (userID!=null) {
            params.put("student_id", userID);
        }

        if (teacherCode!=null) {
            params.put("teacher_code", teacherCode);
        } else if (userCode!=null){
            params.put("teacher_code", userCode);
        }

        params.put("exam_number", strExamNumber);
        params.put("exam_title", examTitle);
        params.put("seat_works_stats", compiledSeatWorksStats);
        params.put("seatworks", compiledSeatWorks);

        service.post("http://jabahan.com/learnfractions/exam_stat/insert.php", params);
        service.execute();
    }
    public static void getStudentStats(Context context, String studentID, Service service){
        RequestParams params = new RequestParams();

        String teacherCode = Storage.load(context, Storage.TEACHER_CODE);

        params.put("teacher_code", teacherCode);
        params.put("student_id", studentID);

        service.get("http://jabahan.com/learnfractions/exam_stat/getStudentStats.php", params);
        service.execute();
    }

    public static void getStudentStats(Context context, Service service){
        RequestParams params = new RequestParams();

        String userType = Storage.load(context, Storage.USER_TYPE);

        String teacherCode = null;
        String studentId = null;
        if (userType.equals(AppConstants.STUDENT)) {
            teacherCode = Storage.load(context, Storage.TEACHER_CODE);
            studentId = Storage.load(context, Storage.STUDENT_ID);
        }
        String userCode = null;
        String userID = null;
        if (userType.equals(AppConstants.USER)) {
            userCode = AppConstants.USER_CODE;
            userID = Storage.load(context, Storage.USER_ID);
        }

        if (teacherCode!=null) {
            params.put("teacher_code", teacherCode);
        } else if (userCode!=null){
            params.put("teacher_code", userCode);
        }

        if (studentId!=null) {
            params.put("student_id", studentId);
        } else if (userID!=null){
            params.put("student_id", userID);
        }

        service.get("http://jabahan.com/learnfractions/exam_stat/getStudentStats.php", params);
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

        service.get("http://jabahan.com/learnfractions/exam_stat/getAllStats.php", params);
        service.execute();
    }
}
