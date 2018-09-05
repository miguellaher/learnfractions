package com.example.laher.learnfractions.lessons.multiplying_fractions;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class MultiplyingFractionsVideoActivity extends LessonVideo {
    Context context = this;
    public String TITLE = "Multiplying Fractions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE);
        setContext(context);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.multiplying_fractions); //SAMPLE VIDEO
        setUri(uri);
    }
}
