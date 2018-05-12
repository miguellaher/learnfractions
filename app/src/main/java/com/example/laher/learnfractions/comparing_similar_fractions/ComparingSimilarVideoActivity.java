package com.example.laher.learnfractions.comparing_similar_fractions;

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
import com.example.laher.learnfractions.non_visual_fraction.NonVisualExercise2Activity;
import com.example.laher.learnfractions.non_visual_fraction.NonVisualExerciseActivity;
import com.example.laher.learnfractions.non_visual_fraction.NonVisualVideoActivity;

public class ComparingSimilarVideoActivity extends AppCompatActivity {
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Comparing Similar";

    VideoView video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CHANGE INTENT PARAMS
                Intent intent = new Intent(ComparingSimilarVideoActivity.this, ComparingSimilarExerciseActivity.class);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);

        video = (VideoView) findViewById(R.id.videoView);

        go();
    }
    public void go(){
        //btnNext.setVisibility(Button.INVISIBLE);
        btnNext.setEnabled(false);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.small); //SAMPLE VIDEO
        video.setVideoURI(uri);
        video.setMediaController(new MediaController(this));
        video.requestFocus();
        video.start();
        video.setOnCompletionListener(new ComparingSimilarVideoActivity.VideoOnCompletionListener());
    }

    public class VideoOnCompletionListener implements MediaPlayer.OnCompletionListener{
        @Override
        public void onCompletion(MediaPlayer mp) {
            //btnNext.setVisibility(Button.VISIBLE);
            btnNext.setEnabled(true);
        }
    }
    @Override
    protected void onRestart() {
        video.start();
        super.onRestart();
    }
}
