package com.example.laher.learnfractions.comparing_similar_fractions;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;

public class ComparingSimilarExerciseActivity extends AppCompatActivity {
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Comparing Similar";
    //GUI
    TextView txtNum1, txtNum2, txtScore, txtCompareSign;
    Button btnGreater, btnEquals, btnLess;
    //VARIABLES
    int num1, num2, consecutiveRights, consecutiveWrongs;
    public final String GREATER_THAN = ">";
    public final String EQUAL_TO = "=";
    public final String LESS_THAN = "<";
    final Handler handler = new Handler();

    int requiredConsecutiveCorrects = 5;
    int maxConsecutiveWrongs = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparing_similar_exercise);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CHANGE INTENT PARAMS
                Intent intent = new Intent(ComparingSimilarExerciseActivity.this, ComparingSimilarExerciseActivity.class);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);

        txtNum1 = (TextView) findViewById(R.id.c1_txtNum1);
        txtNum2 = (TextView) findViewById(R.id.c1_txtNum2);
        txtCompareSign = (TextView) findViewById(R.id.c1_txtCompareSign);
        txtScore = (TextView) findViewById(R.id.c1_txtScore);
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);

        btnGreater = (Button) findViewById(R.id.c1_btnGreater);
        btnEquals = (Button) findViewById(R.id.c1_btnEquals);
        btnLess = (Button) findViewById(R.id.c1_btnLess);
        go();
    }
    public void go(){
        txtCompareSign.setText("_");
        generateNumbers();
        setButtons();
    }
    public void generateNumbers(){
        num1 = (int) (Math.random() * 9 + 1);
        num2 = (int) (Math.random() * 9 + 1);
        setNumbers();
    }
    public void setNumbers(){
        txtNum1.setText(String.valueOf(num1));
        txtNum2.setText(String.valueOf(num2));
    }
    public void setButtons(){
        btnGreater.setOnClickListener(new BtnListener());
        btnEquals.setOnClickListener(new BtnListener());
        btnLess.setOnClickListener(new BtnListener());
    }
    public class BtnListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == btnGreater.getId()){
                txtCompareSign.setText(GREATER_THAN);
                check(GREATER_THAN);
            }
            if (v.getId() == btnEquals.getId()){
                txtCompareSign.setText(EQUAL_TO);
                check(EQUAL_TO);
            }
            if (v.getId() == btnLess.getId()){
                txtCompareSign.setText(LESS_THAN);
                check(LESS_THAN);
            }
        }
    }
    public void check(String compareSign){
        if (compareSign == GREATER_THAN){
            if (num1 > num2){
                correct();
            } else {
                wrong();
            }
        }
        if (compareSign == EQUAL_TO){
            if (num1 == num2){
                correct();
            } else {
                wrong();
            }
        }
        if (compareSign == LESS_THAN){
            if (num1 < num2){
                correct();
            } else {
                wrong();
            }
        }
    }
    public void correct(){
        consecutiveRights++;
        consecutiveWrongs = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (consecutiveRights >= requiredConsecutiveCorrects){
                    btnGreater.setEnabled(false);
                    btnEquals.setEnabled(false);
                    btnLess.setEnabled(false);
                    btnNext.setEnabled(true);
                } else {
                    go();
                }
            }
        }, 2000);
    }
    public void wrong(){
        consecutiveWrongs++;
        consecutiveRights = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (consecutiveWrongs >= maxConsecutiveWrongs){
                    btnGreater.setEnabled(false);
                    btnEquals.setEnabled(false);
                    btnLess.setEnabled(false);
                } else {
                    go();
                }
            }
        }, 2000);
    }
}