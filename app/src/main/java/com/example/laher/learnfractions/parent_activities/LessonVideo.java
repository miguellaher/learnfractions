package com.example.laher.learnfractions.parent_activities;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.dialog_layout.ConfirmationDialog;
import com.example.laher.learnfractions.util.AppCache;

public class LessonVideo extends AppCompatActivity {
    private Context context;
    private VideoView video;
    private Uri uri;

    //TOOLBAR
    private Button buttonBack, buttonNext;
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
        buttonBack = findViewById(R.id.btnBack);
        buttonBack.setOnClickListener(new ButtonBackListener());
        buttonNext = findViewById(R.id.btnNext);
        buttonNext.setEnabled(false);
        buttonNext.setOnClickListener(new ButtonNextListener());
        txtTitle = findViewById(R.id.txtTitle);
    }

    private void go(){
        video.setVideoURI(this.uri);
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
