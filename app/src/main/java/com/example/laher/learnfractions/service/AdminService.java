package com.example.laher.learnfractions.service;

import com.example.laher.learnfractions.model.Admin;
import com.example.laher.learnfractions.util.Encryptor;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class AdminService {

    public static void register(final Admin admin, Service service){
        RequestParams requestParams = new RequestParams();
        admin.setId(Util.generateId());
        requestParams.put("id", admin.getId());
        requestParams.put("username", admin.getUsername());
        String password = Encryptor.encrypt(admin.getPassword());
        requestParams.put("password", password);
        service.post("http://jabahan.com/learnfractions/admin/create.php",requestParams);
        service.execute();
    }

    public static void login(final Admin admin, Service service){
        RequestParams requestParams = new RequestParams();
        requestParams.put("username", admin.getUsername());
        String password = Encryptor.encrypt(admin.getPassword());
        requestParams.put("password", password);
        String url = "http://jabahan.com/learnfractions/admin/login.php?username="+admin.getUsername()+"&password="+password;
        service.get(url, requestParams);
        service.execute();
    }

}
