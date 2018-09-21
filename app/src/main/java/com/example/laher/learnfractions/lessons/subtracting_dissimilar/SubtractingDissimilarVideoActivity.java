package com.example.laher.learnfractions.lessons.subtracting_dissimilar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class SubtractingDissimilarVideoActivity extends LessonVideo {
    Context context = this;
    public String TITLE = "Subtracting Dissimilar Fractions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTxtTitle(TITLE);
        setMContext(context);
        String path = "http://jabahan.com/learnfractions/videos/subtracting_dissimilar.mp4";
        Uri uri = Uri.parse(path);
        setUri(uri);
    }
}
