package com.example.laher.learnfractions.lessons.comparing_dissimilar_fractions;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class ComparingDissimilarVideoActivity extends LessonVideo {
    Context context = this;
    public String TITLE = "Comparing Dissimilar Fractions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE);
        setmContext(context);
        String path = "http://jabahan.com/learnfractions/videos/comparing_dissimilar.mp4";
        Uri uri = Uri.parse(path);
        setUri(uri);
    }
}
