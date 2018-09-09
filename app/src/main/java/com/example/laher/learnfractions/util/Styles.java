package com.example.laher.learnfractions.util;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.laher.learnfractions.R;

import java.util.ArrayList;
import java.util.Collections;

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
    public static void paintWhite(TextView v){
        v.setTextColor(Color.rgb(255,255,255));
    }

    public static void bgPaintBurlyWood(TextView v){
        v.setBackgroundColor(Color.rgb(222,184,135));
    }
    public static void bgPaintWhite(TextView v){
        v.setBackgroundColor(Color.rgb(255,255,255));
    }

    //MAIN COLORS
    public static void bgPaintMainBlue(TextView v){
        v.setBackgroundColor(Color.rgb(33,150,243));
    }

    public static void bgPaintMainYellow(TextView v){
        v.setBackgroundColor(Color.rgb(255,193,7));
    }

    public static void bgPaintMainOrange(TextView v){
        v.setBackgroundColor(Color.rgb(241,105,60));
    }

    public static void bgPaintMainBlueGreen(TextView v){
        v.setBackgroundColor(Color.rgb(13,139,156));
    }

    public static void bgPaintRandomMain(TextView v){
        int random = (int) (Math.random() * 4 + 1);
        if (random == 1){
            bgPaintMainBlue(v);
        } else if (random == 2){
            bgPaintMainYellow(v);
        } else if (random == 3){
            bgPaintMainOrange(v);
        } else if (random == 4){
            bgPaintMainBlueGreen(v);
        }

    }

    public static void bgPaintRandomMainSet1(TextView v){
        int random = (int) (Math.random() * 2 + 1);
        if (random == 1){
            bgPaintMainBlue(v);
        } else if (random == 2){
            bgPaintMainYellow(v);
        }

    }

    public static void bgPaintRandomMainSet2(TextView v){
        int random = (int) (Math.random() * 2 + 1);
        if (random == 1){
            bgPaintMainOrange(v);
        } else if (random == 2){
            bgPaintMainBlueGreen(v);
        }

    }

    public static void bgPaintRandomMainSet2(ConstraintLayout constraintLayout){
        int random = (int) (Math.random() * 2 + 1);
        if (random == 1){
            bgPaintMainOrange(constraintLayout);
        } else if (random == 2){
            bgPaintMainBlueGreen(constraintLayout);
        }

    }

    public static void bgPaintMainBlue(ConstraintLayout constraintLayout) {
        constraintLayout.setBackgroundColor(Color.rgb(33,150,243));
    }

    public static void bgPaintMainYellow(ConstraintLayout constraintLayout) {
        constraintLayout.setBackgroundColor(Color.rgb(255,193,7));
    }

    public static void bgPaintMainOrange(ConstraintLayout constraintLayout) {
        constraintLayout.setBackgroundColor(Color.rgb(241,105,60));
    }

    public static void bgPaintMainBlueGreen(ConstraintLayout constraintLayout) {
        constraintLayout.setBackgroundColor(Color.rgb(13,139,156));
    }

    public static void bgPaintMainOrange(LinearLayout linearLayout) {
        linearLayout.setBackgroundColor(Color.rgb(241,105,60));
    }

    public static void bgPaintMainBlueGreen(LinearLayout linearLayout) {
        linearLayout.setBackgroundColor(Color.rgb(13,139,156));
    }

    public static void removeListener(View v){
        v.setOnClickListener(null);
        v.setClickable(false);
    }

    public static void error(TextView v){
        paintRed(v);
        shakeAnimate(v);
    }

    public static int getColor(int r, int g, int b){
        return Color.rgb(r,g,b);
    }

    public static int getRandomFritsImageResource(){
        ArrayList<Integer> resources = new ArrayList<>();

        int adventureFrits = R.drawable.safari_frits_pic;
        int gentleFrits = R.drawable.gentle_frits_pic;
        int kidFrits = R.drawable.kid_frits_pic;
        int safariFrits = R.drawable.safari_frits_pic;
        int summerFrits = R.drawable.summer_frits_pic;

        resources.add(adventureFrits);
        resources.add(gentleFrits);
        resources.add(kidFrits);
        resources.add(safariFrits);
        resources.add(summerFrits);

        Collections.shuffle(resources);

        return resources.get(0);
    }

    public static void bgPaintMainBlue(LinearLayout linearLayout) {
        linearLayout.setBackgroundColor(Color.rgb(33,150,243));
    }

    public static void bgPaintMainYellow(LinearLayout linearLayout) {
        linearLayout.setBackgroundColor(Color.rgb(255,193,7));
    }

    public static void bgPaintMainBlue(RelativeLayout relativeLayout) {
        relativeLayout.setBackgroundColor(Color.rgb(33,150,243));
    }

    public static void bgPaintMainYellow(RelativeLayout relativeLayout) {
        relativeLayout.setBackgroundColor(Color.rgb(255,193,7));
    }

    public static void bgPaintMainOrange(RelativeLayout relativeLayout) {
        relativeLayout.setBackgroundColor(Color.rgb(241,105,60));
    }

    public static void bgPaintMainBlueGreen(RelativeLayout relativeLayout) {
        relativeLayout.setBackgroundColor(Color.rgb(13,139,156));
    }
}
