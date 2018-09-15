package com.example.laher.learnfractions.lessons.multiplying_fractions;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class MultiplyingFractionsVideoActivity extends LessonVideo {
    Context context = this;
    public String TITLE = "Multiplying Fractions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE);
        setMContext(context);
        String path = "http://jabahan.com/learnfractions/videos/multiplying.mp4";
        Uri uri = Uri.parse(path);
        setUri(uri);
    }
}
