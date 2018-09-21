package com.example.laher.learnfractions.lessons.comparing_fractions;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class ComparingFractionsVideoActivity extends LessonVideo {
    Context context = this;
    public String TITLE = "Comparing Fractions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTxtTitle(TITLE);
        setMContext(context);
        String path = "http://jabahan.com/learnfractions/videos/comparing_fractions.mp4";
        Uri uri = Uri.parse(path);
        setUri(uri);
    }
}
