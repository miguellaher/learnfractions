package com.example.laher.learnfractions.fractionmeaning;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.laher.learnfractions.R;

public class FractionMeaningVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_meaning_video);
        VideoView v = (VideoView) findViewById(R.id.fractionMeaningVid);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.small);
        v.setVideoURI(uri);
        v.setMediaController(new MediaController(this));
        v.requestFocus();
        v.start();
    }
}
