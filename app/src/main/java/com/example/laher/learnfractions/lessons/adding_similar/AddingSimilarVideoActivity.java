package com.example.laher.learnfractions.lessons.adding_similar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class AddingSimilarVideoActivity extends LessonVideo {
    Context context = this;
    public String TITLE = "Adding Similar Fractions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTxtTitle(TITLE);
        setMContext(context);
        String path = "http://jabahan.com/learnfractions/videos/add_similar.mp4";
        Uri uri = Uri.parse(path);
        setUri(uri);
    }
}
