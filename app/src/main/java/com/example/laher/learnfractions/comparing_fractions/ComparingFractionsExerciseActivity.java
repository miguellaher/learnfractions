package com.example.laher.learnfractions.comparing_fractions;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.Fraction;
import com.example.laher.learnfractions.R;

public class ComparingFractionsExerciseActivity extends AppCompatActivity {
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Comparing Fractions";
    //GUI
    TextView txtNum1, txtNum2, txtDenom1, txtDenom2, txtScore, txtInstruction;
    Button btnSimilar, btnDissimilar;
    //VARIABLES
    Fraction fractionOne, fractionTwo;
    int consecutiveRights, consecutiveWrongs;

    int requiredConsecutiveCorrects = 10;
    int maxConsecutiveWrongs = 3;

    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparing_fractions_exercise);
        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComparingFractionsExerciseActivity.this,
                        ComparingFractionsVideoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CHANGE INTENT PARAMS
                Intent intent = new Intent(ComparingFractionsExerciseActivity.this,
                        ComparingFractionsExercise2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        //GUI
        txtNum1 = (TextView) findViewById(R.id.e1_txtNum1);
        txtNum2 = (TextView) findViewById(R.id.e1_txtNum2);
        txtDenom1 = (TextView) findViewById(R.id.e1_txtDenom1);
        txtDenom2 = (TextView) findViewById(R.id.e1_txtDenom2);
        txtScore = (TextView) findViewById(R.id.e1_txtScore);
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        txtInstruction = (TextView) findViewById(R.id.e1_txtInstruction);
        btnSimilar = (Button) findViewById(R.id.e1_btnSimilar);
        btnDissimilar = (Button) findViewById(R.id.e1_btnDissimilar);
        btnSimilar.setOnClickListener(new BtnChoiceListener());
        btnDissimilar.setOnClickListener(new BtnChoiceListener());
        //VARIABLES
        fractionOne = new Fraction();
        fractionTwo = new Fraction();

        go();
    }
    public void go(){
        setFractionList();
        txtInstruction.setText("Determine whether the pair is dissimilar or similar.");
        btnSimilar.setEnabled(true);
        btnDissimilar.setEnabled(true);
    }
    public void setFractionList(){
        if (Math.random() > 0.5) {
            fractionOne.generateRandFraction(9);
            fractionTwo.generateRandFraction(9);
            while (fractionOne.getDenominator() != fractionTwo.getDenominator() &&
                    fractionOne.getNumerator() != fractionTwo.getNumerator()){ //SIMILAR
                fractionOne.generateRandFraction(9);
            }
        } else {
            fractionOne.generateRandFraction(9);
            fractionTwo.generateRandFraction(9);
            while (fractionOne.getDenominator() == fractionTwo.getDenominator() ||
                    fractionOne.getNumerator() == fractionTwo.getNumerator()){ //DISSIMILAR
                fractionOne.generateRandFraction(9);
            }
        }
        setTxtFraction();
    }
    public void setTxtFraction() {
        txtNum1.setText(String.valueOf(fractionOne.getNumerator()));
        txtDenom1.setText(String.valueOf(fractionOne.getDenominator()));
        txtNum2.setText(String.valueOf(fractionTwo.getNumerator()));
        txtDenom2.setText(String.valueOf(fractionTwo.getDenominator()));
    }
    public void correct(){
        consecutiveRights++;
        consecutiveWrongs = 0;
        txtInstruction.setText("correct");
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        btnSimilar.setEnabled(false);
        btnDissimilar.setEnabled(false);
        if (consecutiveRights>=requiredConsecutiveCorrects){
            txtInstruction.setText("finish");
            btnNext.setEnabled(true);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    go();
                }
            }, 2000);
        }
    }
    public void wrong(){
        consecutiveWrongs++;
        consecutiveRights = 0;
        txtInstruction.setText("wrong");
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        btnSimilar.setEnabled(false);
        btnDissimilar.setEnabled(false);
        if (consecutiveWrongs>=maxConsecutiveWrongs){
            txtInstruction.setText("You had " + consecutiveWrongs + " consecutive wrongs." +
                    " Preparing to watch video again.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(ComparingFractionsExerciseActivity.this,
                            ComparingFractionsVideoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 3000);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    go();
                }
            }, 2000);
        }
    }
    public class BtnChoiceListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.e1_btnSimilar){
                if (fractionOne.getDenominator() == fractionTwo.getDenominator() ||
                        fractionOne.getNumerator() == fractionTwo.getNumerator()){
                    correct();
                } else {
                    wrong();
                }
            }
            if (v.getId() == R.id.e1_btnDissimilar){
                if (fractionOne.getDenominator() != fractionTwo.getDenominator() &&
                        fractionOne.getNumerator() != fractionTwo.getNumerator()){
                    correct();
                } else {
                    wrong();
                }
            }
        }
    }
}
