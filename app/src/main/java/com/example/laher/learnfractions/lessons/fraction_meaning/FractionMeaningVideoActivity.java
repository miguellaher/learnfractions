package com.example.laher.learnfractions.lessons.fraction_meaning;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class FractionMeaningVideoActivity extends LessonVideo {
    Context context = this;
    public final String TITLE = "Fraction Meaning";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE);
        setContext(context);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.small); //SAMPLE VIDEO
        setUri(uri);
    }

}
