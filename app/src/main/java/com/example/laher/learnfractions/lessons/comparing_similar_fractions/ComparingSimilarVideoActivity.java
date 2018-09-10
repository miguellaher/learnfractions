package com.example.laher.learnfractions.lessons.comparing_similar_fractions;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class ComparingSimilarVideoActivity extends LessonVideo {
    Context context = this;
    public String TITLE = "Comparing Similar Fractions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE);
        setmContext(context);
        String path = "http://jabahan.com/learnfractions/videos/comparing_similar.mp4";
        Uri uri = Uri.parse(path);
        setUri(uri);
    }
}
