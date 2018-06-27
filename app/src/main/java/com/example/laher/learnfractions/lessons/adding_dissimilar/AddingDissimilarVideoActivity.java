package com.example.laher.learnfractions.lessons.adding_dissimilar;

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
import com.example.laher.learnfractions.TopicsMenuActivity;

public class AddingDissimilarVideoActivity extends AppCompatActivity {
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Adding Fractions";

    VideoView video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddingDissimilarVideoActivity.this,
                        TopicsMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CHANGE INTENT PARAMS
                Intent intent = new Intent(AddingDissimilarVideoActivity.this,
                        AddingDissimilarExerciseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);

        video = (VideoView) findViewById(R.id.videoView);

        go();
    }
    public void go(){
        btnNext.setEnabled(false);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.small); //SAMPLE VIDEO
        video.setVideoURI(uri);
        video.setMediaController(new MediaController(this));
        video.requestFocus();
        video.start();
        video.setOnCompletionListener(new VideoOnCompletionListener());
    }

    public class VideoOnCompletionListener implements MediaPlayer.OnCompletionListener{
        @Override
        public void onCompletion(MediaPlayer mp) {
            //btnNext.setVisibility(Button.VISIBLE);
            btnNext.setEnabled(true);
        }
    }
}
