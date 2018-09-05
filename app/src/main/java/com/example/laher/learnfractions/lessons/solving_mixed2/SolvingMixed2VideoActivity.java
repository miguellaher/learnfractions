package com.example.laher.learnfractions.lessons.solving_mixed2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class SolvingMixed2VideoActivity extends LessonVideo {
    Context context = this;
    public String TITLE = "Multiplying and Dividing with Mixed Fractions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE);
        setContext(context);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mixed2); //SAMPLE VIDEO
        setUri(uri);
    }
}
