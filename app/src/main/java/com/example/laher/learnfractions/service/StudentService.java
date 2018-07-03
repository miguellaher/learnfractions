package com.example.laher.learnfractions.service;

import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.model.User;
import com.example.laher.learnfractions.util.Encryptor;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class StudentService {

    public static void checkTeacherCode(String teacher_code, Service service){
        RequestParams requestParams = new RequestParams();
        String encrypted_teacher_code = Encryptor.encrypt(teacher_code);
        requestParams.put("teacher_code", encrypted_teacher_code);
        service.get("http://jabahan.com/learnfractions/student/checkTeacherCode.php", requestParams);
        service.execute();
    }

    public static void register(final Student student, Service service){
        RequestParams requestParams = new RequestParams();
        student.setId(Util.generateId());
        requestParams.put("id", student.getId());
        requestParams.put("username", student.getUsername());
        String encrypted_password = Encryptor.encrypt(student.getPassword());
        requestParams.put("password", encrypted_password);
        requestParams.put("age", String.valueOf(student.getAge()));
        requestParams.put("gender", student.getGender());
        requestParams.put("security_question", student.getSecurity_question());
        String encrypted_security_answer = Encryptor.encrypt(student.getSecurity_answer());
        requestParams.put("security_answer", encrypted_security_answer);
        String encrypted_teacher_code = Encryptor.encrypt(student.getTeacher_code());
        requestParams.put("teacher_code", encrypted_teacher_code);
        service.post("http://jabahan.com/learnfractions/student/create.php", requestParams);
        service.execute();
    }

    public static void getAllUsers(Service service){
        service.get("http://jabahan.com/learnfractions/student/getAllStudents.php", new RequestParams());
        service.execute();
    }

    public static void changeUserPassword(Student student, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("username", student.getUsername());
        String encrypted_password = Encryptor.encrypt(student.getPassword());
        requestParams.put("new_password", encrypted_password);
        service.post("http://jabahan.com/learnfractions/student/changeStudentPassword.php", requestParams);
        service.execute();
    }

    public static void login(Student student, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("username", student.getUsername());
        String encrypted_password = Encryptor.encrypt(student.getPassword());
        requestParams.put("password", encrypted_password);
        service.get("http://jabahan.com/learnfractions/student/login.php", requestParams);
        service.execute();
    }

}
