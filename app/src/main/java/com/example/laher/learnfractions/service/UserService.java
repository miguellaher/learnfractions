package com.example.laher.learnfractions.service;

import com.example.laher.learnfractions.model.User;
import com.example.laher.learnfractions.util.Encryptor;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class UserService {

    public static void register(final User user, Service service){
        RequestParams requestParams = new RequestParams();
        user.setId(Util.generateId());
        requestParams.put("id", user.getId());
        requestParams.put("username", user.getUsername());
        String encrypted_password = Encryptor.encrypt(user.getPassword());
        requestParams.put("password", encrypted_password);
        requestParams.put("age", String.valueOf(user.getAge()));
        requestParams.put("gender", user.getGender());
        requestParams.put("security_question", user.getSecurity_question());
        String encrypted_security_answer = Encryptor.encrypt(user.getSecurity_answer());
        requestParams.put("security_answer", encrypted_security_answer);
        service.post("http://jabahan.com/learnfractions/user/create.php", requestParams);
        service.execute();
    }

}
