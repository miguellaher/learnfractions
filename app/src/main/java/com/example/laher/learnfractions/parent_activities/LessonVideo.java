package com.example.laher.learnfractions.parent_activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.laher.learnfractions.dialog_layout.MessageDialog;
import com.example.laher.learnfractions.util.AppCache;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

public class LessonVideo extends AppCompatActivity {
    private Context mContext;
    private VideoView mVideo;
    private ProgressDialog mProgressDialog;
    private Uri uri;

    //TOOLBAR
    private Button buttonBack;
    private Button buttonNext;
    private TextView txtTitle;

    public void setMContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setUri(Uri uri) {
        this.uri = uri;

        if (isNetworkAvailable()) {
            go();
        } else {
            String message = "Connect to the internet\nto watch video tutorial";
            MessageDialog messageDialog = new MessageDialog(mContext, message);
            messageDialog.show();
            messageDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    buttonNext.setEnabled(true);
                }
            });
        }
    }

    public void setTitle(String title){
        txtTitle.setText(title);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mVideo = findViewById(R.id.videoView);

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
        mVideo.setVideoURI(this.uri);
        mVideo.setOnCompletionListener(new VideoListener());
        mVideo.setOnTouchListener(new VideoListener());
        mVideo.requestFocus();

        mVideo.setOnPreparedListener(new VideoListener());

        mVideo.setOnInfoListener(new MediaPlayerListener());

        mVideo.setOnErrorListener(new VideoListener());

        mProgressDialog = new ProgressDialog(mContext);
        String message = "Loading...";
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        mVideo.start();
        buttonNext.setEnabled(true);
    }

        public class VideoListener implements MediaPlayer.OnCompletionListener, View.OnTouchListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener{
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

        @Override
        public void onPrepared(MediaPlayer mp) {
            mProgressDialog.dismiss();
        }

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            mProgressDialog.dismiss();
            String message = "Video playing error.";
            MessageDialog messageDialog = new MessageDialog(mContext, message);
            messageDialog.show();
            messageDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    buttonNext.setEnabled(true);
                }
            });
            return true;
        }
    }

    private class MediaPlayerListener implements MediaPlayer.OnInfoListener{
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            switch (what) {
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    mProgressDialog.show();
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    mProgressDialog.cancel();
                    break;
            }
            return false;
        }
    }

    private class ButtonBackListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            final ConfirmationDialog confirmationDialog = new ConfirmationDialog(mContext,"Are you sure you want to go back?");
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
