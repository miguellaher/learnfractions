package com.example.laher.learnfractions.lessons.ordering_dissimilar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class OrderingDissimilarVideoActivity extends LessonVideo {
    Context context = this;
    public String TITLE = "Ordering Dissimilar Fractions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE);
        setmContext(context);
        String path = "http://jabahan.com/learnfractions/videos/ordering_dissimilar.mp4";
        Uri uri = Uri.parse(path);
        setUri(uri);
    }
}
