package com.example.laher.learnfractions.lessons.dividing_fractions;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class DividingFractionsVideoActivity extends LessonVideo {
    Context context = this;
    public String TITLE = "Dividing Fractions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTxtTitle(TITLE);
        setMContext(context);
        String path = "http://jabahan.com/learnfractions/videos/dividing.mp4";
        Uri uri = Uri.parse(path);
        setUri(uri);
    }
}
