package com.example.laher.learnfractions.classifying_fractions;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.Fraction;
import com.example.laher.learnfractions.FractionQuestion;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;

import java.util.ArrayList;
import java.util.Collections;

public class ClassifyingFractionsExerciseActivity extends AppCompatActivity {
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Classifying Fractions";
    //GUI
    TextView txtNum, txtDenom, txtWholeNum, txtScore, txtInstruction;
    Button btnProper, btnImproper, btnMixed;
    //VARIABLES
    Fraction fraction;
    ArrayList<Fraction> fractions;
    int questionNum;
    int consecutiveRights, consecutiveWrongs;
    int requiredConsecutiveCorrects = 10;
    int maxConsecutiveWrongs = 3;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifying_fractions_exercise);
        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassifyingFractionsExerciseActivity.this,
                        ClassifyingFractionsVideoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CHANGE INTENT PARAMS
                Intent intent = new Intent(ClassifyingFractionsExerciseActivity.this,
                        TopicsMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        //GUI
        txtNum = (TextView) findViewById(R.id.clF_txtNum);
        txtDenom = (TextView) findViewById(R.id.clF_txtDenom);
        txtWholeNum = (TextView) findViewById(R.id.clF_txtWholeNum);
        txtScore = (TextView) findViewById(R.id.clF_txtScore);
        txtInstruction = (TextView) findViewById(R.id.clF_txtInstruction);
        btnProper = (Button) findViewById(R.id.clF_btnProper);
        btnImproper = (Button) findViewById(R.id.clF_btnImproper);
        btnMixed = (Button) findViewById(R.id.clF_btnMixed);
        btnProper.setOnClickListener(new BtnAnswerListener());
        btnImproper.setOnClickListener(new BtnAnswerListener());
        btnMixed.setOnClickListener(new BtnAnswerListener());

        go();
    }
    public void go(){
        setFractionQuestions();
        setGuiFraction();
        startUp();
    }
    public void correct(){
        consecutiveRights++;
        consecutiveWrongs = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        enableButtons(false);
        if (consecutiveRights>=requiredConsecutiveCorrects){
            txtInstruction.setText("finished");
            btnNext.setEnabled(true);
        } else {
            txtInstruction.setText("correct");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    enableButtons(true);
                    questionNum++;
                    setGuiFraction();
                }
            },2000);
        }
    }
    public void wrong(){
        consecutiveWrongs++;
        consecutiveRights = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        enableButtons(false);
        if (consecutiveWrongs>=maxConsecutiveWrongs){
            txtInstruction.setText("You had " + consecutiveWrongs + " consecutive wrongs. Preparing to watch" +
                    " video again.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(ClassifyingFractionsExerciseActivity.this,
                            ClassifyingFractionsVideoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            },4000);
        } else {
            txtInstruction.setText("wrong");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    enableButtons(true);
                    go();
                }
            },2000);
        }
    }
    public void startUp(){
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
    }
    public void enableButtons(Boolean bool){
        btnProper.setEnabled(bool);
        btnImproper.setEnabled(bool);
        btnMixed.setEnabled(bool);
    }
    public void setFractionQuestions(){
        questionNum = 0;
        fractions = new ArrayList<>();
        for (int i = 0; i < requiredConsecutiveCorrects; i++){
            if (i < (requiredConsecutiveCorrects/3)){
                fraction = new Fraction(Fraction.PROPER);
            } else if (i < ((requiredConsecutiveCorrects*2)/3)){
                fraction = new Fraction(Fraction.IMPROPER);
            } else {
                fraction = new Fraction(Fraction.MIXED);
            }
            fractions.add(fraction);
        }
        Collections.shuffle(fractions);
    }
    public void setGuiFraction(){
        if (Fraction.MIXED == fractions.get(questionNum).getContext()){
            txtWholeNum.setText(String.valueOf(fractions.get(questionNum).getWholeNum()));
        } else {
            txtWholeNum.setText("");
        }
        txtNum.setText(String.valueOf(fractions.get(questionNum).getNumerator()));
        txtDenom.setText(String.valueOf(fractions.get(questionNum).getDenominator()));
    }
    public void check(String context){
        if (context == fractions.get(questionNum).getContext()){
            correct();
        } else {
            wrong();
        }
    }
    public class BtnAnswerListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId()==btnProper.getId()){
                check(Fraction.PROPER);
            } else if (v.getId()==btnImproper.getId()){
                check(Fraction.IMPROPER);
            } else if (v.getId()==btnMixed.getId()){
                check(Fraction.MIXED);
            }
        }
    }
}
