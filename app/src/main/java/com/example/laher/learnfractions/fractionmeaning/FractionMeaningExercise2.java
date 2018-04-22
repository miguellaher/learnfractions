package com.example.laher.learnfractions.fractionmeaning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;

import java.util.ArrayList;

public class FractionMeaningExercise2 extends AppCompatActivity {
    ImageView imgBox1, imgBox2, imgBox3, imgBox4, imgBox5, imgBox6, imgBox7, imgBox8, imgBox9;
    TextView txtScore, txtInstruction;
    ArrayList<String> instructions;
    Button btnTest;
    int num, denom, consecutiveRights, consecutiveWrongs;
    int requiredConsecutiveCorrects = 6;
    int maxConsecutiveWrongs = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_meaning_exercise2);
        imgBox1 = (ImageView) findViewById(R.id.a1_imgBox1);
        imgBox2 = (ImageView) findViewById(R.id.a1_imgBox2);
        imgBox3 = (ImageView) findViewById(R.id.a1_imgBox3);
        imgBox4 = (ImageView) findViewById(R.id.a1_imgBox4);
        imgBox5 = (ImageView) findViewById(R.id.a1_imgBox5);
        imgBox6 = (ImageView) findViewById(R.id.a1_imgBox6);
        imgBox7 = (ImageView) findViewById(R.id.a1_imgBox7);
        imgBox8 = (ImageView) findViewById(R.id.a1_imgBox8);
        imgBox9 = (ImageView) findViewById(R.id.a1_imgBox9);
    }
}
