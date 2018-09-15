package com.example.laher.learnfractions.lessons.ordering_similar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class OrderingSimilarVideoActivity extends LessonVideo {
    Context context = this;
    public String TITLE = "Ordering Similar Fractions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE);
        setMContext(context);
        String path = "http://jabahan.com/learnfractions/videos/ordering_similar.mp4";
        Uri uri = Uri.parse(path);
        setUri(uri);
    }
}
