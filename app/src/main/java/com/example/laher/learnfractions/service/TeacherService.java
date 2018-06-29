package com.example.laher.learnfractions.service;

import com.example.laher.learnfractions.model.Exercise;
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

    public static void postExercise(Teacher teacher, Exercise exercise, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("exercise_id", Util.generateId());
        requestParams.put("teacher_id", teacher.getId());
        requestParams.put("topic_name", exercise.getTopicName());
        requestParams.put("exercise_num", exercise.getExerciseNum());
        requestParams.put("required_corrects", exercise.getRequiredCorrects());
        if (exercise.isRc_consecutive()){
            requestParams.put("rc_consecutive", "1");
        } else {
            requestParams.put("rc_consecutive", "0");
        }
        requestParams.put("max_errors", exercise.getMaxErrors());
        if (exercise.isMe_consecutive()){
            requestParams.put("me_consecutive", "1");
        } else {
            requestParams.put("me_consecutive", "0");
        }
        service.post("http://jabahan.com/learnfractions/exercise/create.php", requestParams);
        service.execute();
    }
}


















