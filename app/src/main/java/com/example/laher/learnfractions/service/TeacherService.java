package com.example.laher.learnfractions.service;

import android.content.Context;

import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.util.Encryptor;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class TeacherService {

    public static void register(Teacher teacher, Service service){
        RequestParams params = new RequestParams();

        String id = Util.generateId();
        String username = teacher.getUsername();
        String password = teacher.getPassword();
        String securityQuestion = teacher.getSecurity_question();
        String securityAnswer = teacher.getSecurity_answer();
        String teacherCode = teacher.getTeacher_code();

        params.put("id", id);
        params.put("username", username);

        String encrypted_password = Encryptor.encrypt(password);
        params.put("password", encrypted_password);

        params.put("security_question", securityQuestion);

        String encrypted_security_answer = Encryptor.encrypt(securityAnswer);
        params.put("security_answer", encrypted_security_answer);

        String encrypted_teacher_code = Encryptor.encrypt(teacherCode);
        params.put("teacher_code", encrypted_teacher_code);

        service.post("http://jabahan.com/learnfractions/teacher/create.php", params);
        service.execute();
    }

    public static void login(Teacher teacher, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("username", teacher.getUsername());
        String encrypted_password = Encryptor.encrypt(teacher.getPassword());
        requestParams.put("password", encrypted_password);
        service.get("http://jabahan.com/learnfractions/teacher/login.php", requestParams);
        service.execute();
    }

    public static void getAllTeachers(Service service){
        service.get("http://jabahan.com/learnfractions/teacher/getAllTeachers.php", new RequestParams());
        service.execute();
    }

    public static void changeUserPassword(Teacher teacher, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("username", teacher.getUsername());
        String encrypted_password = Encryptor.encrypt(teacher.getPassword());
        requestParams.put("new_password", encrypted_password);
        service.post("http://jabahan.com/learnfractions/teacher/changeTeacherPassword.php", requestParams);
        service.execute();
    }

    public static void checkTeacherCode(Teacher teacher, Service service){
        RequestParams params = new RequestParams();

        String teacherCode = teacher.getTeacher_code();

        String encrypted_teacher_code = Encryptor.encrypt(teacherCode);
        params.put("teacher_code", encrypted_teacher_code);

        service.get("http://jabahan.com/learnfractions/teacher/checkTeacherCode.php", params);
        service.execute();
    }

    public static void getAllStudents(Context context, Service service){
        RequestParams params = new RequestParams();

        String teacherCode = Storage.load(context, Storage.TEACHER_CODE);

        params.put("teacher_code", teacherCode);

        service.get("http://jabahan.com/learnfractions/teacher/getAllStudents.php", params);
        service.execute();
    }

}


















