package com.example.laher.learnfractions.lessons.non_visual_fraction;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class NonVisualVideoActivity extends LessonVideo {
    Context context = this;
    public String TITLE = "Non-Visual Fractions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE);
        setMContext(context);
        String path = "http://jabahan.com/learnfractions/videos/non_visual.mp4";
        Uri uri = Uri.parse(path);
        setUri(uri);
    }
}
