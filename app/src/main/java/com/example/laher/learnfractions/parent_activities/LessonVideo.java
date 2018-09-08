package com.example.laher.learnfractions.parent_activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.dialog_layout.ConfirmationDialog;
import com.example.laher.learnfractions.util.AppCache;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

public class LessonVideo extends AppCompatActivity {
    private Context context;
    private VideoView video;
    private Uri uri;

    //TOOLBAR
    private Button buttonBack;
    private Button buttonNext;
    private TextView txtTitle;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
        go();
    }

    public void setTitle(String title){
        txtTitle.setText(title);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        video = findViewById(R.id.videoView);

        ConstraintLayout toolbar = findViewById(R.id.constraintLayoutToolbar);

        buttonBack = findViewById(R.id.btnBack);
        buttonBack.setOnClickListener(new ButtonBackListener());
        if (Util.randomBoolean()){
            Styles.bgPaintMainBlue(buttonBack);
            Styles.bgPaintMainYellow(toolbar);
        } else {
            Styles.bgPaintMainYellow(buttonBack);
            Styles.bgPaintMainBlue(toolbar);
        }

        LinearLayout linearLayout = findViewById(R.id.activity_video_linearLayout);

        buttonNext = findViewById(R.id.btnNext);
        buttonNext.setEnabled(false);
        buttonNext.setOnClickListener(new ButtonNextListener());
        if (Util.randomBoolean()){
            Styles.bgPaintMainOrange(buttonNext);
            Styles.bgPaintMainBlueGreen(linearLayout);
        } else {
            Styles.bgPaintMainBlueGreen(buttonNext);
            Styles.bgPaintMainOrange(linearLayout);
        }

        txtTitle = findViewById(R.id.txtTitle);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void go(){
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.small); //SAMPLE VIDEO
        video.setVideoURI(uri); // TEST
        //video.setVideoURI(this.uri);
        video.setOnCompletionListener(new VideoListener());
        video.setOnTouchListener(new VideoListener());
        video.requestFocus();
        video.start();
    }

    public class VideoListener implements MediaPlayer.OnCompletionListener, View.OnTouchListener{
        @Override
        public void onCompletion(MediaPlayer mp) {
            buttonNext.setEnabled(true);
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v instanceof VideoView){
                VideoView video = (VideoView) v;
                if (video.isPlaying()){
                    video.pause();
                } else {
                    video.start();
                }
            }
            return false;
        }
    }

    private class ButtonBackListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            final ConfirmationDialog confirmationDialog = new ConfirmationDialog(context,"Are you sure you want to go back?");
            confirmationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (confirmationDialog.isConfirmed()){
                        AppCache.setBackClicked(true);
                        finish();
                    }
                }
            });
            confirmationDialog.show();
        }
    }

    private class ButtonNextListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            AppCache.setNextClicked(true);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        buttonBack.performClick();
    }
}
