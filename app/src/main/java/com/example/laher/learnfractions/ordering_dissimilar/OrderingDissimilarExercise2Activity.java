package com.example.laher.learnfractions.ordering_dissimilar;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.FractionQuestion;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;

import java.util.ArrayList;

public class OrderingDissimilarExercise2Activity extends AppCompatActivity {
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Ordering Fractions";
    //GUI
    TextView txtNum1, txtNum2, txtNum3, txtNum4, txtNum5, txtNum6, txtDenom1, txtDenom2, txtDenom3, txtDenom4, txtDenom5, txtDenom6;
    TextView txtScore, txtInstruction, txtEquation1, txtEquation2, txtEquation3;
    ConstraintLayout clFraction1, clFraction2, clFraction3, clFraction4, clFraction5, clFraction6;
    //VARIABLES
    FractionQuestion fractionQuestion;
    ArrayList<FractionQuestion> fractionQuestions;
    int questionNum;
    int consecutiveRights, consecutiveWrongs;
    int requiredConsecutiveCorrects = 10;
    int maxConsecutiveWrongs = 3;
    int clicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_dissimilar_exercise2);
        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(OrderingDissimilarExercise2Activity.this,
                        OrderingDissimilarExerciseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
                go();
            }
        });
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CHANGE INTENT PARAMS
                Intent intent = new Intent(OrderingDissimilarExercise2Activity.this,
                        TopicsMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        //GUI
        txtNum1 = (TextView) findViewById(R.id.od_txtNum1);
        txtNum2 = (TextView) findViewById(R.id.od_txtNum2);
        txtNum3 = (TextView) findViewById(R.id.od_txtNum3);
        txtNum4 = (TextView) findViewById(R.id.od_txtNum4);
        txtNum5 = (TextView) findViewById(R.id.od_txtNum5);
        txtNum6 = (TextView) findViewById(R.id.od_txtNum6);
        txtDenom1 = (TextView) findViewById(R.id.od_txtDenom1);
        txtDenom2 = (TextView) findViewById(R.id.od_txtDenom2);
        txtDenom3 = (TextView) findViewById(R.id.od_txtDenom3);
        txtDenom4 = (TextView) findViewById(R.id.od_txtDenom4);
        txtDenom5 = (TextView) findViewById(R.id.od_txtDenom5);
        txtDenom6 = (TextView) findViewById(R.id.od_txtDenom6);
        txtEquation1 = (TextView) findViewById(R.id.od_txtEquation1);
        txtEquation2 = (TextView) findViewById(R.id.od_txtEquation2);
        txtEquation3 = (TextView) findViewById(R.id.od_txtEquation3);
        txtScore = (TextView) findViewById(R.id.od_txtScore);
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        txtInstruction = (TextView) findViewById(R.id.od_txtInstruction);
        clFraction1 = (ConstraintLayout) findViewById(R.id.od_fraction1);
        clFraction2 = (ConstraintLayout) findViewById(R.id.od_fraction2);
        clFraction3 = (ConstraintLayout) findViewById(R.id.od_fraction3);
        clFraction4 = (ConstraintLayout) findViewById(R.id.od_fraction4);
        clFraction5 = (ConstraintLayout) findViewById(R.id.od_fraction5);
        clFraction6 = (ConstraintLayout) findViewById(R.id.od_fraction6);

        go();
    }
    public void go(){
        setQuestions();
        setGuiFractionSet1();
        startup();
    }
    public void startup(){
        txtInstruction.setText("Click the denominators and get the lcd (least common denominator).");
        hideGuiFractionSet2();
        hideEquations();
        resetVariables();
        setFractionSet1TxtDenomsListner();
    }
    public void resetVariables(){
        consecutiveRights = 0;
        consecutiveWrongs = 0;
        clicks = 0;
    }
    public void setQuestions(){
        fractionQuestions = new ArrayList<>();
        questionNum = 0;
        for (int i = 0; i < requiredConsecutiveCorrects; i++){
            fractionQuestion = new FractionQuestion(FractionQuestion.ORDERING_DISSIMILAR);
            fractionQuestions.add(fractionQuestion);
        }
    }
    public void setGuiFractionSet1(){
        txtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getNumerator()));
        txtNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getNumerator()));
        txtNum3.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionThree().getNumerator()));
        txtDenom1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
        txtDenom2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
        txtDenom3.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionThree().getDenominator()));
    }
    public void hideGuiFractionSet2(){
        clFraction4.setVisibility(ConstraintLayout.INVISIBLE);
        clFraction5.setVisibility(ConstraintLayout.INVISIBLE);
        clFraction6.setVisibility(ConstraintLayout.INVISIBLE);
    }
    public void hideEquations(){
        txtEquation1.setVisibility(TextView.INVISIBLE);
        txtEquation2.setVisibility(TextView.INVISIBLE);
        txtEquation3.setVisibility(TextView.INVISIBLE);
    }
    public void setFractionSet1TxtDenomsListner(){
        txtDenom1.setOnClickListener(new TxtDenomListener());
        txtDenom2.setOnClickListener(new TxtDenomListener());
        txtDenom3.setOnClickListener(new TxtDenomListener());
    }
    public class TxtDenomListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            t.setTextColor(Color.rgb(0,255,0));
            //CONTINUE
            if (clicks>=3){
                //LAUNCH LCM DIALOG
                txtInstruction.setText("" + clicks);
            }
        }
    }
}