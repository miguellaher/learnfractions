package com.example.laher.learnfractions.non_visual_fraction;

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
import com.example.laher.learnfractions.fractionmeaning.FractionMeaningExerciseActivity;

public class NonVisualVideoActivity extends AppCompatActivity {
    VideoView video;

    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Non-Visual";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        video = (VideoView) findViewById(R.id.videoView);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnBack.setOnClickListener(new BtnBackListener());
        btnNext.setOnClickListener(new BtnNextListener());
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
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
        video.setOnCompletionListener(new VideoOnCompletionListener());
    }

    public class VideoOnCompletionListener implements MediaPlayer.OnCompletionListener{
        @Override
        public void onCompletion(MediaPlayer mp) {
            //btnNext.setVisibility(Button.VISIBLE);
            btnNext.setEnabled(true);
        }
    }

    public class BtnBackListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    public class BtnNextListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(NonVisualVideoActivity.this, NonVisualExerciseActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
