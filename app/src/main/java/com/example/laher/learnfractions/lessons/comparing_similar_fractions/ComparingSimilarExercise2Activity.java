package com.example.laher.learnfractions.lessons.comparing_similar_fractions;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestion;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;

import java.util.ArrayList;

public class ComparingSimilarExercise2Activity extends AppCompatActivity {
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Comparing Similar";
    //GUI
    TextView txtNum1, txtNum2, txtDenom1, txtDenom2, txtCompareSign, txtScore, txtInstruction;
    Button btnGreater, btnEquals, btnLess;
    //VARIABLES
    int questionNum, consecutiveRights, consecutiveWrongs;
    public final String GREATER_THAN = ">";
    public final String EQUAL_TO = "=";
    public final String LESS_THAN = "<";
    final Handler handler = new Handler();
    Fraction fractionOne, fractionTwo;
    FractionQuestion fractionQuestion;
    ArrayList<FractionQuestion> fractionQuestions;
    String strAnswer;

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
                Intent intent = new Intent(ComparingSimilarExercise2Activity.this,
                        ComparingSimilarExerciseActivity.class);
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
                Intent intent = new Intent(ComparingSimilarExercise2Activity.this, TopicsMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        txtInstruction = (TextView) findViewById(R.id.c2_txtInstruction);

        btnGreater = (Button) findViewById(R.id.c2_btnGreater);
        btnEquals = (Button) findViewById(R.id.c2_btnEqual);
        btnLess = (Button) findViewById(R.id.c2_btnLess);
        btnGreater.setText(FractionQuestion.ANSWER_GREATER);
        btnEquals.setText(FractionQuestion.ANSWER_EQUAL);
        btnLess.setText(FractionQuestion.ANSWER_LESS);
        btnGreater.setOnClickListener(new BtnListener());
        btnEquals.setOnClickListener(new BtnListener());
        btnLess.setOnClickListener(new BtnListener());
        //VARIABLES
        fractionOne = new Fraction();
        fractionTwo = new Fraction();
        fractionQuestion = new FractionQuestion();
        fractionQuestions = new ArrayList<>();

        go();
    }
    public void go(){
        setFractionQuestions();
    }
    public void setFractionQuestions(){
        fractionQuestions = new ArrayList<>();
        questionNum = 0;
        for(int i = 0; i < requiredConsecutiveCorrects; i++){
            fractionQuestion = new FractionQuestion(FractionQuestion.COMPARING_SIMILAR);
            fractionQuestions.add(fractionQuestion);
        }
        setTxtFractions();
    }
    public void setTxtFractions(){
        txtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getNumerator()));
        txtNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getNumerator()));
        txtDenom1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
        txtDenom2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
        strAnswer = fractionQuestions.get(questionNum).getAnswer();
        txtInstruction.setText("compare the two fractions");
        txtCompareSign.setText("_");
    }
    public void enableButtons(boolean bool){
        btnGreater.setEnabled(bool);
        btnEquals.setEnabled(bool);
        btnLess.setEnabled(bool);
    }
    public void correct(){
        consecutiveRights++;
        consecutiveWrongs = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        enableButtons(false);
        txtInstruction.setText("correct");
        if (consecutiveRights >= requiredConsecutiveCorrects){
            btnNext.setEnabled(true);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    questionNum++;
                    setTxtFractions();
                    enableButtons(true);
                }
            }, 2000);
        }
    }
    public void wrong(){
        consecutiveWrongs++;
        consecutiveRights = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        enableButtons(false);
        txtInstruction.setText("wrong");
        if (consecutiveWrongs >= maxConsecutiveWrongs){
            txtInstruction.setText("You had " + consecutiveWrongs + " consecutive wrongs." +
                    " Preparing to start previous exercise.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(ComparingSimilarExercise2Activity.this,
                            ComparingSimilarExerciseActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 3000);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    enableButtons(true);
                    go();
                }
            }, 2000);
        }
    }
    public class BtnListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            String s = b.getText().toString();
            txtCompareSign.setText(s);
            if (s == fractionQuestions.get(questionNum).getAnswer()){
                correct();
            } else {
                wrong();
            }
        }
    }
}
