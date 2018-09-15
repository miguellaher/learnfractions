package com.example.laher.learnfractions.service;

import com.example.laher.learnfractions.model.User;
import com.example.laher.learnfractions.util.Encryptor;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class UserService {

    public static void register(User user, Service service){
        RequestParams params = new RequestParams();

        String id = Util.generateId();
        String userName = user.getUsername();
        String password = user.getPassword();
        String encryptedPassword = Encryptor.encrypt(password);
        int age = user.getAge();
        String strAge = String.valueOf(age);
        String gender = user.getGender();
        String securityQuestion = user.getSecurity_question();
        String securityAnswer = user.getSecurity_answer();
        String encryptedSecurityAnswer = Encryptor.encrypt(securityAnswer);

        params.put("id", id);
        params.put("username", userName);
        params.put("password", encryptedPassword);
        params.put("age", strAge);
        params.put("gender", gender);
        params.put("security_question", securityQuestion);
        params.put("security_answer", encryptedSecurityAnswer);

        service.post("http://jabahan.com/learnfractions/user/create.php", params);
        service.execute();
    }

    public static void getAllUsers(Service service){
        service.get("http://jabahan.com/learnfractions/user/getAllUsers.php", new RequestParams());
        service.execute();
    }

    public static void changeUserPassword(User user, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("username", user.getUsername());
        String encrypted_password = Encryptor.encrypt(user.getPassword());
        requestParams.put("new_password", encrypted_password);
        service.post("http://jabahan.com/learnfractions/user/changeUserPassword.php", requestParams);
        service.execute();
    }

    public static void login(User user, Service service){
        RequestParams params = new RequestParams();
        String userName = user.getUsername();
        String password = user.getPassword();
        String encryptedPassword = Encryptor.encrypt(password);

        params.put("username", userName);
        params.put("password", encryptedPassword);

        service.get("http://jabahan.com/learnfractions/user/login.php", params);
        service.execute();
    }

}
