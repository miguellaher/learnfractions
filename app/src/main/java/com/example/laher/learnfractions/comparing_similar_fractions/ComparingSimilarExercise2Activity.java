package com.example.laher.learnfractions.comparing_similar_fractions;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;

public class ComparingSimilarExercise2Activity extends AppCompatActivity {
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Comparing Similar";
    //GUI
    TextView txtNum1, txtNum2, txtDenom1, txtDenom2, txtCompareSign, txtScore;
    Button btnGreater, btnEqual, btnLess;
    //VARIABLES
    int num1, num2, denom, consecutiveRights, consecutiveWrongs;
    public final String GREATER_THAN = ">";
    public final String EQUAL_TO = "=";
    public final String LESS_THAN = "<";
    final Handler handler = new Handler();

    int requiredConsecutiveCorrects = 8;
    int maxConsecutiveWrongs = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparing_similar_exercise2);
        //TOOLBAR
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
                Intent intent = new Intent(ComparingSimilarExercise2Activity.this, TopicsMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        btnNext.setText("DONE");
        //GUI
        txtNum1 = (TextView) findViewById(R.id.c2_num1);
        txtNum2 = (TextView) findViewById(R.id.c2_num2);
        txtDenom1 = (TextView) findViewById(R.id.c2_denom1);
        txtDenom2 = (TextView) findViewById(R.id.c2_denom2);
        txtCompareSign = (TextView) findViewById(R.id.c2_compareSign);
        txtScore = (TextView) findViewById(R.id.c2_txtScore);
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);

        btnGreater = (Button) findViewById(R.id.c2_btnGreater);
        btnEqual = (Button) findViewById(R.id.c2_btnEqual);
        btnLess = (Button) findViewById(R.id.c2_btnLess);
        btnGreater.setOnClickListener(new BtnListener());
        btnEqual.setOnClickListener(new BtnListener());
        btnLess.setOnClickListener(new BtnListener());

        go();
    }
    public void go(){
        txtCompareSign.setText("_");
        generateFractions();
    }
    public void generateFractions(){
        num1 = (int) (Math.random() * 9 + 1);
        num2 = (int) (Math.random() * 9 + 1);
        denom = (int) (Math.random() * 9 + 1);
        setTxtFractions();
    }
    public void setTxtFractions(){
        txtNum1.setText(String.valueOf(num1));
        txtNum2.setText(String.valueOf(num2));
        txtDenom1.setText(String.valueOf(denom));
        txtDenom2.setText(String.valueOf(denom));
    }
    public void check(String compareSign){
        if(compareSign == GREATER_THAN){
            if (num1 > num2){
                correct();
            } else {
                wrong();
            }
        }
        if(compareSign == EQUAL_TO){
            if (num1 == num2){
                correct();
            } else {
                wrong();
            }
        }
        if(compareSign == LESS_THAN){
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
        btnGreater.setEnabled(false);
        btnEqual.setEnabled(false);
        btnLess.setEnabled(false);
        if (consecutiveRights >= requiredConsecutiveCorrects){
            btnNext.setEnabled(true);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnGreater.setEnabled(true);
                    btnEqual.setEnabled(true);
                    btnLess.setEnabled(true);
                    go();
                }
            }, 2000);
        }
    }
    public void wrong(){
        consecutiveWrongs++;
        consecutiveRights = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        btnGreater.setEnabled(false);
        btnEqual.setEnabled(false);
        btnLess.setEnabled(false);
        if (consecutiveRights >= requiredConsecutiveCorrects){
        } else {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnGreater.setEnabled(true);
                    btnEqual.setEnabled(true);
                    btnLess.setEnabled(true);
                    go();
                }
            }, 2000);
        }
    }
    public class BtnListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == btnGreater.getId()){
                txtCompareSign.setText(">");
                check(GREATER_THAN);
            }
            if (v.getId() == btnEqual.getId()){
                txtCompareSign.setText("=");
                check(EQUAL_TO);
            }
            if (v.getId() == btnLess.getId()){
                txtCompareSign.setText("<");
                check(LESS_THAN);
            }
        }
    }
}
