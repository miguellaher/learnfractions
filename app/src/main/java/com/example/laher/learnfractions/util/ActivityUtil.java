package com.example.laher.learnfractions.util;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.laher.learnfractions.LearnFractionsApp;
import com.example.laher.learnfractions.R;

public class ActivityUtil {
    private static MediaPlayer avatarMediaPlayer;
    private static MediaPlayer bgMusicMediaPlayer;
    private static MediaPlayer mediaPlayer;

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void playBgMusicMediaPlayer(Context context){
        if (bgMusicMediaPlayer == null) {
            bgMusicMediaPlayer = MediaPlayer.create(context, R.raw.main_soundtrack);
            bgMusicMediaPlayer.setLooping(true);
            bgMusicMediaPlayer.start();
        }
    }

    public static void stopBgMusicMediaPlayer(){
        if (bgMusicMediaPlayer !=null){
            bgMusicMediaPlayer.stop();
            bgMusicMediaPlayer.release();
            bgMusicMediaPlayer = null;
        }
    }

    public static void playMediaPlayer(Context context, int id){
        mediaPlayer = MediaPlayer.create(context, id);
        mediaPlayer.start();
    }

    public static void playAvatarMediaPlayer(Context context, int id){
        if (LearnFractionsApp.isAppVisible()) {
            stopAvatarMediaPlayer();
            setMainMediaPlayerVolume(30);
            avatarMediaPlayer = MediaPlayer.create(context, id);
            avatarMediaPlayer.setOnCompletionListener(new AvatarMediaPlayerListener());
            avatarMediaPlayer.start();
        }
    }

    public static void stopAvatarMediaPlayer(){
        if (avatarMediaPlayer !=null) {
            avatarMediaPlayer.stop();
            avatarMediaPlayer.release();
            avatarMediaPlayer = null;
            setMainMediaPlayerVolume(99);
        }
    }

    public static void muteBgMusicMediaPlayer(){
        setMainMediaPlayerVolume(0);
    }

    public static void unmuteBgMusicMediaPlayer(){
        setMainMediaPlayerVolume(99);
    }

    private static void setMainMediaPlayerVolume(int volume){
        if (bgMusicMediaPlayer !=null){
            if (bgMusicMediaPlayer.isPlaying()){
                int maxVolume = 100;

                float log1=(float)(Math.log(maxVolume-volume)/Math.log(maxVolume));
                bgMusicMediaPlayer.setVolume(1-log1, 1-log1);
            }
        }
    }

    private static class AvatarMediaPlayerListener implements MediaPlayer.OnCompletionListener{
        @Override
        public void onCompletion(MediaPlayer mp) {
            setMainMediaPlayerVolume(99);
        }
    }
}
