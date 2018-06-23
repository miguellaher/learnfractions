package com.example.laher.learnfractions.util;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Styles {
    public static void shakeAnimate(View view){
        ObjectAnimator.ofFloat(view, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                .setDuration(1000)
                .start();
    }
    public static void paintGreen(TextView v){
        v.setTextColor(Color.rgb(0,255,0));
    }
    public static void paintRed(TextView v){
        v.setTextColor(Color.rgb(255,0,0));
    }
    public static void paintBlack(TextView v){
        v.setTextColor(Color.rgb(0,0,0));
    }

    public static void bgPaintBurlyWood(TextView v){
        v.setBackgroundColor(Color.rgb(222,184,135));
    }
    public static void bgPaintWhite(TextView v){
        v.setBackgroundColor(Color.rgb(255,255,255));
    }

    public static void removeListener(View v){
        v.setOnClickListener(null);
        v.setClickable(false);
    }

    public static void error(TextView v){
        paintRed(v);
        shakeAnimate(v);
    }
}
