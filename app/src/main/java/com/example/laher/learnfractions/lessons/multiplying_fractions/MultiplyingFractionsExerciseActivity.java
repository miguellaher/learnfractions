package com.example.laher.learnfractions.lessons.multiplying_fractions;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.fraction_util.FractionQuestion;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;

import java.util.ArrayList;

public class MultiplyingFractionsExerciseActivity extends AppCompatActivity {
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Multiplying Fractions";
    //FRACTION EQUATION GUI
    TextView txtNum1, txtNum2, txtDenom1, txtDenom2, txtSign, txtScore, txtInstruction;
    EditText inputNum, inputDenom;
    Button btnCheck;
    //VARIABLES
    FractionQuestion fractionQuestion;
    ArrayList<FractionQuestion> fractionQuestions;
    int questionNum;
    int consecutiveRights, consecutiveWrongs;
    int requiredConsecutiveCorrects = 10;
    int maxConsecutiveWrongs = 3;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_equation);
        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MultiplyingFractionsExerciseActivity.this,
                        MultiplyingFractionsVideoActivity.class);
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
                Intent intent = new Intent(MultiplyingFractionsExerciseActivity.this,
                        TopicsMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        btnNext.setText("DONE");
        //FRACTION EQUATION GUI
        txtNum1 = (TextView) findViewById(R.id.fe_txtNum1);
        txtNum2 = (TextView) findViewById(R.id.fe_txtNum2);
        txtDenom1 = (TextView) findViewById(R.id.fe_txtDenom1);
        txtDenom2 = (TextView) findViewById(R.id.fe_txtDenom2);
        txtSign = (TextView) findViewById(R.id.fe_txtSign);
        txtSign.setText("x");
        txtScore = (TextView) findViewById(R.id.fe_txtScore);
        txtInstruction = (TextView) findViewById(R.id.fe_txtInstruction);
        inputNum = (EditText) findViewById(R.id.fe_inputNum);
        inputDenom = (EditText) findViewById(R.id.fe_inputDenom);
        btnCheck = (Button) findViewById(R.id.fe_btnCheck);
        btnCheck.setOnClickListener(new BtnCheckListener());

        go();
    }
    public void go(){
        setQuestions();
        setGuiFractions();
        startUp();
    }
    public void correct(){
        consecutiveRights++;
        consecutiveWrongs = 0;
        txtScore.setText(consecutiveRights + "/" + requiredConsecutiveCorrects);
        disableInputFraction();
        btnCheck.setEnabled(false);
        if (consecutiveRights >= requiredConsecutiveCorrects){
            txtInstruction.setText("Finished.");
            btnNext.setEnabled(true);
        } else {
            txtInstruction.setText("Correct.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    questionNum++;
                    setGuiFractions();
                    clearInputFraction();
                    enableInputFraction();
                    btnCheck.setEnabled(true);
                }
            }, 2000);
        }
    }
    public void wrong(){
        consecutiveWrongs++;
        consecutiveRights = 0;
        txtScore.setText(consecutiveRights + "/" + requiredConsecutiveCorrects);
        disableInputFraction();
        btnCheck.setEnabled(false);
        if (consecutiveWrongs >= maxConsecutiveWrongs){
            txtInstruction.setText("You had " + consecutiveWrongs + " consecutive wrongs. Preparing to watch video again.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MultiplyingFractionsExerciseActivity.this,
                            MultiplyingFractionsVideoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 4000);
        } else {
            txtInstruction.setText("Wrong.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    go();
                    enableInputFraction();
                    btnCheck.setEnabled(true);
                }
            }, 2000);
        }
    }
    public void startUp(){
        txtScore.setText(consecutiveRights + "/" + requiredConsecutiveCorrects);
        txtInstruction.setText("Multiply the numerators and denominators.");
        clearInputFraction();
    }
    public void clearInputFraction(){
        inputNum.setText("");
        inputDenom.setText("");
        inputNum.requestFocus();
    }
    public void setQuestions(){
        questionNum = 0;
        fractionQuestions = new ArrayList<>();
        for(int i = 0; i < requiredConsecutiveCorrects; i++){
            fractionQuestion = new FractionQuestion(FractionQuestion.MULTIPLYING_FRACTIONS);
            fractionQuestions.add(fractionQuestion);
        }
    }
    public void setGuiFractions(){
        txtInstruction.setText("Solve the equation.");
        txtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getNumerator()));
        txtNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getNumerator()));
        txtDenom1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
        txtDenom2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
    }
    public void shakeAnimate(View view){
        ObjectAnimator.ofFloat(view, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                .setDuration(1000)
                .start();
    }
    public void shakeInputFraction(){
        shakeAnimate(inputNum);
        shakeAnimate(inputDenom);
    }
    public void disableInputFraction(){
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
    }
    public void enableInputFraction(){
        inputNum.setEnabled(true);
        inputDenom.setEnabled(true);
    }
    public class BtnCheckListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {

            if (inputNum.getText().toString().trim().length() != 0 &&
                    inputDenom.getText().toString().trim().length() != 0 ) {
                if (Integer.valueOf(String.valueOf(inputNum.getText()))
                        == fractionQuestions.get(questionNum).getFractionAnswer().getNumerator()
                        && Integer.valueOf(String.valueOf(inputDenom.getText()))
                        == fractionQuestions.get(questionNum).getFractionAnswer().getDenominator()) {
                    correct();
                } else {
                    shakeInputFraction();
                    wrong();
                }
            } else {
                shakeInputFraction();
            }
        }
    }
}
