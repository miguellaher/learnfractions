package com.example.laher.learnfractions.ordering_similar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.laher.learnfractions.R;

public class OrderingSimilarExercise2Activity extends AppCompatActivity {
    //GUI
    TextView txtNum1, txtNum2, txtNum3, txtDenom1, txtDenom2, txtDenom3, txtScore, txtInstruction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_similar_exercise2);
        //GUI
        txtNum1 = (TextView) findViewById(R.id.os2_txtNum1);
        txtNum2 = (TextView) findViewById(R.id.os2_txtNum2);
        txtNum3 = (TextView) findViewById(R.id.os2_txtNum3);
        txtDenom1 = (TextView) findViewById(R.id.os2_txtDenom1);
        txtDenom2 = (TextView) findViewById(R.id.os2_txtDenom2);
        txtDenom3 = (TextView) findViewById(R.id.os2_txtDenom3);
        txtScore = (TextView) findViewById(R.id.os2_txtScore);
        txtInstruction = (TextView) findViewById(R.id.os2_txtInstruction);


    }

}
