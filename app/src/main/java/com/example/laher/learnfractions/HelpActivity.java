package com.example.laher.learnfractions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.laher.learnfractions.dialog_layout.MessageDialog;
import com.example.laher.learnfractions.parent_activities.MainFrame;
import com.example.laher.learnfractions.util.ActivityUtil;

public class HelpActivity extends MainFrame {
    private Context mContext = this;

    //ACTIVITY
    VideoView video;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        video = findViewById(R.id.help_video);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(video);
        video.setMediaController(mediaController);

        String path = "http://jabahan.com/learnfractions/videos/help.mp4";
        Uri uri = Uri.parse(path);
        video.setVideoURI(uri);

        video.requestFocus();

        video.setOnCompletionListener(new VideoListener());

        video.setOnPreparedListener(new VideoListener());

        video.setOnInfoListener(new MediaPlayerListener());

        video.setOnErrorListener(new VideoListener());

        mProgressDialog = new ProgressDialog(mContext);
        String message = "Loading...";
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        video.start();
    }

    public class VideoListener implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener{
        @Override
        public void onCompletion(MediaPlayer mp) {
            ActivityUtil.playBgMusicMediaPlayer(mContext);
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            ActivityUtil.stopBgMusicMediaPlayer();
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
                    onBackPressed();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtil.playBgMusicMediaPlayer(mContext);
    }
}
