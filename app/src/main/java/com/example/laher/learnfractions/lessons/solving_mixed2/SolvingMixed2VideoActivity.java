package com.example.laher.learnfractions.lessons.solving_mixed2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class SolvingMixed2VideoActivity extends LessonVideo {
    Context context = this;
    public String TITLE = "Multiplying and Dividing with Mixed Fractions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE);
        setMContext(context);
        String path = "http://jabahan.com/learnfractions/videos/mixed2.mp4";
        Uri uri = Uri.parse(path);
        setUri(uri);
    }
}
