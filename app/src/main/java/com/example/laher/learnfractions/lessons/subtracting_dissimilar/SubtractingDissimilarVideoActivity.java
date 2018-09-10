package com.example.laher.learnfractions.lessons.subtracting_dissimilar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class SubtractingDissimilarVideoActivity extends LessonVideo {
    Context context = this;
    public String TITLE = "Subtracting Dissimilar Fractions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE);
        setmContext(context);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.small); //SAMPLE VIDEO
        setUri(uri);
    }
}
