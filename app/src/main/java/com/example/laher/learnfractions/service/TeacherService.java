package com.example.laher.learnfractions.service;

import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.util.Encryptor;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class TeacherService {

    public static void register(Teacher teacher, Service service){
        RequestParams requestParams = new RequestParams();
        teacher.setId(Util.generateId());
        requestParams.put("id", teacher.getId());
        requestParams.put("username", teacher.getUsername());
        String encrypted_password = Encryptor.encrypt(teacher.getPassword());
        requestParams.put("password", encrypted_password);
        requestParams.put("security_question", teacher.getSecurity_question());
        String encrypted_security_answer = Encryptor.encrypt(teacher.getSecurity_answer());
        requestParams.put("security_answer", encrypted_security_answer);
        String encrypted_teacher_code = Encryptor.encrypt(teacher.getPassword());
        requestParams.put("teacher_code", encrypted_teacher_code);
        service.post("http://jabahan.com/learnfractions/teacher/create.php", requestParams);
        service.execute();
    }
}
