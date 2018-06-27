package com.example.laher.learnfractions.service;

import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.model.Lesson;
import com.example.laher.learnfractions.util.Util;
import com.loopj.android.http.RequestParams;

public class LessonService {

    public static void postLessons(Service service){
        RequestParams requestParams = new RequestParams();
        for (int i = 0; i < LessonArchive.getAllLessons().size(); i++){
            Lesson lesson = LessonArchive.getAllLessons().get(i);
            requestParams.put(Util.generateId(), lesson.getTitle());
        }
        service.post("http://jabahan.com/learnfractions/lesson/insert.php",requestParams);
        service.execute();
    }

    public static void updateLessons(Service service){
        String url = "http://jabahan.com/learnfractions/lesson/getAll.php";
        service.get(url, new RequestParams());
        service.execute();
    }

}
