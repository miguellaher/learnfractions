package com.example.laher.learnfractions.ordering_similar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
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

public class OrderingSimilarExercise2Activity extends AppCompatActivity {
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Ordering Fractions";
    //GUI
    TextView txtNum1, txtNum2, txtNum3, txtDenom1, txtDenom2, txtDenom3, txtScore, txtInstruction;
    ConstraintLayout clFraction1, clFraction2, clFraction3;
    //VARIABLES
    Fraction fraction1, fraction2, fraction3;
    FractionQuestion fractionQuestion;
    ArrayList<FractionQuestion> fractionQuestions;
    int questionNum;

    int consecutiveRights, consecutiveWrongs;
    int requiredConsecutiveCorrects = 10;
    int maxConsecutiveWrongs = 4;
    int clicks;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_similar_exercise2);
        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderingSimilarExercise2Activity.this,
                        OrderingSimilarExerciseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CHANGE INTENT PARAMS
                Intent intent = new Intent(OrderingSimilarExercise2Activity.this,
                        TopicsMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        btnNext.setText("DONE");
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        //GUI
        txtNum1 = (TextView) findViewById(R.id.os2_txtNum1);
        txtNum2 = (TextView) findViewById(R.id.os2_txtNum2);
        txtNum3 = (TextView) findViewById(R.id.os2_txtNum3);
        txtDenom1 = (TextView) findViewById(R.id.os2_txtDenom1);
        txtDenom2 = (TextView) findViewById(R.id.os2_txtDenom2);
        txtDenom3 = (TextView) findViewById(R.id.os2_txtDenom3);
        txtScore = (TextView) findViewById(R.id.os2_txtScore);
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        txtInstruction = (TextView) findViewById(R.id.os2_txtInstruction);
        clFraction1 = (ConstraintLayout) findViewById(R.id.os2_clFraction1);
        clFraction2 = (ConstraintLayout) findViewById(R.id.os2_clFraction2);
        clFraction3 = (ConstraintLayout) findViewById(R.id.os2_clFraction3);

        go();
    }
    public void go(){
        setFractionQuestions();
    }
    public void setFractionQuestions(){
        fractionQuestions = new ArrayList<>();
        questionNum = 0;
        for (int i = 0; i < requiredConsecutiveCorrects; i++){
            fractionQuestion = new FractionQuestion(FractionQuestion.ORDERING_SIMILAR);
            fractionQuestions.add(fractionQuestion);
        }
        setGuiFractions();
    }
    public void setFractions(){
        fraction1 = fractionQuestions.get(questionNum).getFractionOne();
        fraction2 = fractionQuestions.get(questionNum).getFractionTwo();
        fraction3 = fractionQuestions.get(questionNum).getFractionThree();
    }
    public void setGuiFractions(){
        clicks = 0;
        setFractions();
        txtInstruction.setText("click from least to greatest");
        txtNum1.setText(String.valueOf(fraction1.getNumerator()));
        txtNum2.setText(String.valueOf(fraction2.getNumerator()));
        txtNum3.setText(String.valueOf(fraction3.getNumerator()));
        txtDenom1.setText(String.valueOf(fraction1.getDenominator()));
        txtDenom2.setText(String.valueOf(fraction2.getDenominator()));
        txtDenom3.setText(String.valueOf(fraction3.getDenominator()));
        setClFractionsListener();
        resetTxtNumsColor();
    }
    public void resetTxtNumsColor(){
        txtNum1.setTextColor(Color.rgb(128,128,128));
        txtNum2.setTextColor(Color.rgb(128,128,128));
        txtNum3.setTextColor(Color.rgb(128,128,128));
        txtDenom1.setTextColor(Color.rgb(128,128,128));
        txtDenom2.setTextColor(Color.rgb(128,128,128));
        txtDenom3.setTextColor(Color.rgb(128,128,128));
    }
    public void removeClFractionsListener(){
        clFraction1.setOnClickListener(null);
        clFraction2.setOnClickListener(null);
        clFraction3.setOnClickListener(null);
    }
    public void setClFractionsListener(){
        clFraction1.setOnClickListener(new ClFractionListener());
        clFraction2.setOnClickListener(new ClFractionListener());
        clFraction3.setOnClickListener(new ClFractionListener());
    }
    public void correct(){
        consecutiveRights++;
        consecutiveWrongs = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        removeClFractionsListener();
        if (consecutiveRights >= requiredConsecutiveCorrects){
            btnNext.setEnabled(true);
            txtInstruction.setText("finish");
        } else {
            txtInstruction.setText("correct");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    questionNum++;
                    setGuiFractions();
                }
            }, 2000);
        }
    }
    public void wrong(){
        consecutiveWrongs++;
        consecutiveRights = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        removeClFractionsListener();
        if (consecutiveWrongs >= maxConsecutiveWrongs){
            txtInstruction.setText("go back");
        } else {
            txtInstruction.setText("wrong ");
            clicks = 0;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    go();
                }
            }, 2000);
        }
    }
    public class ClFractionListener implements ConstraintLayout.OnClickListener{
        TextView num, denom;
        @Override
        public void onClick(View v) {
            if (v.getId()==clFraction1.getId()){
                num = txtNum1;
                denom = txtDenom1;
                check(fraction1);
            }
            if (v.getId()==clFraction2.getId()){
                num = txtNum2;
                denom = txtDenom2;
                check(fraction2);
            }
            if (v.getId()==clFraction3.getId()){
                num = txtNum3;
                denom = txtDenom3;
                check(fraction3);
            }
            if (clicks>=3){
                correct();
            }
        }
        public void check(Fraction fraction){
            if (fraction.equals(fractionQuestions.get(questionNum).getFractions().get(clicks))){
                setTextColor(0,255,0);
                clicks++;
            } else {
                setTextColor(255,0,0);
                wrong();
            }
        }
        public void setTextColor(int r, int g, int b){
            num.setTextColor(Color.rgb(r, g, b));
            denom.setTextColor(Color.rgb(r, g, b));
        }
    }
}
