package com.example.laher.learnfractions.lessons.solving_mixed;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class SolvingMixedVideoActivity extends LessonVideo {
    Context context = this;
    public String TITLE = "Adding and Subtracting with Mixed Fractions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE);
        setContext(context);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.small); //SAMPLE VIDEO
        setUri(uri);
    }
}
