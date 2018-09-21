package com.example.laher.learnfractions.lessons.fraction_meaning;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class FractionMeaningVideoActivity extends LessonVideo {
    Context context = this;
    public final String TITLE = "Fraction Meaning";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTxtTitle(TITLE);
        setMContext(context);
        String path = "http://jabahan.com/learnfractions/videos/fraction_meaning.mp4";
        Uri uri = Uri.parse(path);
        setUri(uri);
    }

}
