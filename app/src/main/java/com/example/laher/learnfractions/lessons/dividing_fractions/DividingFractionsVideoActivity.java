package com.example.laher.learnfractions.lessons.dividing_fractions;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.LessonsMenuActivity;
import com.example.laher.learnfractions.parent_activities.LessonVideo;

public class DividingFractionsVideoActivity extends LessonVideo {
    Context context = this;
    public String TITLE = "Dividing Fractions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(TITLE);
        setContext(context);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.small); //SAMPLE VIDEO
        setUri(uri);
    }
}
