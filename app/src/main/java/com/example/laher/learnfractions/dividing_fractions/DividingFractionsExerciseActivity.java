package com.example.laher.learnfractions.dividing_fractions;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.Fraction;
import com.example.laher.learnfractions.FractionQuestion;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

public class DividingFractionsExerciseActivity extends AppCompatActivity {
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Dividing Fractions";
    //GUI
    TextView txtNum1, txtNum2, txtNum3, txtNum4, txtDenom1, txtDenom2, txtDenom3, txtDenom4, txtEquation1, txtEquation2
            , txtScore, txtInstruction, txtSign1, txtSign2;
    EditText inputNum, inputDenom;
    Button btnCheck;
    //VARIABLES
    Fraction fraction;
    FractionQuestion fractionQuestion;
    ArrayList<FractionQuestion> fractionQuestions;
    int questionNum;
    int consecutiveRights, consecutiveWrongs;
    int requiredConsecutiveCorrects = 10;
    int maxConsecutiveWrongs = 5;
    ArrayList<Integer> viewIds;
    final Handler handler = new Handler();
    ColorStateList defaultColor;
    TextView txtContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_dissimilar_equation);
        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DividingFractionsExerciseActivity.this,
                        DividingFractionsVideoActivity.class);
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
                Intent intent = new Intent(DividingFractionsExerciseActivity.this,
                        TopicsMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        btnNext.setText("DONE");
        //GUI
        txtNum1 = findViewById(R.id.adsm_txtNum1);
        txtNum2 = findViewById(R.id.adsm_txtNum2);
        txtNum3 = findViewById(R.id.adsm_txtNum3);
        txtNum4 = findViewById(R.id.adsm_txtNum4);
        txtDenom1 = findViewById(R.id.adsm_txtDenom1);
        txtDenom2 = findViewById(R.id.adsm_txtDenom2);
        txtDenom3 = findViewById(R.id.adsm_txtDenom3);
        txtDenom4 = findViewById(R.id.adsm_txtDenom4);
        txtEquation1 = findViewById(R.id.adsm_txtEquation1);
        txtEquation2 = findViewById(R.id.adsm_txtEquation2);
        txtEquation1.setVisibility(TextView.INVISIBLE);
        txtEquation2.setVisibility(TextView.INVISIBLE);
        txtScore = findViewById(R.id.adsm_txtScore);
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        txtInstruction = findViewById(R.id.adsm_txtInstruction);
        inputNum = findViewById(R.id.adsm_inputNum);
        inputDenom = findViewById(R.id.adsm_inputDenom);
        btnCheck = findViewById(R.id.adsm_btnCheck);
        txtSign1 = findViewById(R.id.adsm_txtSign1);
        txtSign2 = findViewById(R.id.adsm_txtSign2);
        txtSign1.setText("÷");
        txtSign2.setText("x");
        defaultColor = txtScore.getTextColors();
        btnCheck.setOnClickListener(new BtnCheckListener());

        go();
    }
    public void go(){
        setFractionQuestions();
        setFractionGui();
        setUp();
    }
    public void correct(){
        consecutiveRights++;
        consecutiveWrongs = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        setAnswerEnabled(false);
        setTxtFractionListener(false);
        if (consecutiveRights>=requiredConsecutiveCorrects){
            btnNext.setEnabled(true);
            txtInstruction.setText("Finished.");
        } else {
            txtInstruction.setText("Correct.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    questionNum++;
                    setFractionGui();
                    setUp();
                }
            }, 2000);
        }
    }
    public void wrong(){
        consecutiveWrongs++;
        consecutiveRights = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        setAnswerEnabled(false);
        setTxtFractionListener(false);
        if (consecutiveWrongs>=maxConsecutiveWrongs){
            txtInstruction.setText("You had " + consecutiveWrongs + " consecutive wrongs. Preparing to watch" +
                    " video again.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(DividingFractionsExerciseActivity.this,
                            DividingFractionsVideoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }, 4000);
        } else {
            txtInstruction.setText("Wrong.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    go();
                }
            }, 2000);
        }
    }
    public void setFractionQuestions(){
        fractionQuestions = new ArrayList<>();
        questionNum = 0;
        for (int i = 0; i < requiredConsecutiveCorrects; i++){
            fractionQuestion = new FractionQuestion(FractionQuestion.DIVIDING_FRACTIONS);
            fractionQuestions.add(fractionQuestion);
        }
    }
    public void setFractionGui(){
        txtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getNumerator()));
        txtNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getNumerator()));
        txtDenom1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
        txtDenom2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
        txtNum3.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getNumerator()));
        txtDenom3.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
    }
    public void setUp(){
        inputNum.setText("");
        inputDenom.setText("");
        txtNum4.setText("    ");
        txtDenom4.setText("    ");
        txtNum4.setBackgroundColor(Color.rgb(255,255,255));
        txtDenom4.setBackgroundColor(Color.rgb(255,255,255));
        setAnswerEnabled(false);
        paintContainer();
        setTxtFractionListener(true);
        txtInstruction.setText("Click the right number to put in the colored space.");
    }
    public void setAnswerEnabled(boolean b){
        inputNum.setEnabled(b);
        inputDenom.setEnabled(b);
        btnCheck.setEnabled(b);
    }
    public void paintContainer(){
        int random = (int) (Math.random() * 2 + 1);
        if (random==1){
            if (txtNum4.getText().toString().trim().matches("")) {
                Styles.bgpaintBurlyWood(txtNum4);
                txtContainer = txtNum4;
            } else if (txtDenom4.getText().toString().trim().matches("")) {
                Styles.bgpaintBurlyWood(txtDenom4);
                txtContainer = txtDenom4;
            }
        } else if (random==2){
            if (txtDenom4.getText().toString().trim().matches("")) {
                Styles.bgpaintBurlyWood(txtDenom4);
                txtContainer = txtDenom4;
            } else if (txtNum4.getText().toString().trim().matches("")) {
                Styles.bgpaintBurlyWood(txtNum4);
                txtContainer = txtNum4;
            }
        }
    }
    public void setTxtFractionListener(boolean b){
        if (b){
            txtNum2.setOnClickListener(new TxtFractionListener());
            txtDenom2.setOnClickListener(new TxtFractionListener());
        } else {
            txtNum2.setOnClickListener(null);
            txtDenom2.setOnClickListener(null);
        }
        txtNum2.setClickable(b);
        txtDenom2.setClickable(b);
    }
    public void readyCheck(){
        try {
            if (Integer.valueOf(String.valueOf(txtDenom4.getText()))
                    == fractionQuestions.get(questionNum).getFractionTwo().getNumerator()
                    && Integer.valueOf(String.valueOf(txtNum4.getText()))
                    == fractionQuestions.get(questionNum).getFractionTwo().getDenominator()) {
                setTxtFractionListener(false);
                inputNum.setEnabled(true);
                inputDenom.setEnabled(true);
                btnCheck.setEnabled(true);
                inputNum.requestFocus();
                txtInstruction.setText("Solve the equation.");
            }
        }catch (NumberFormatException e){}
    }
    public class TxtFractionListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            if (txtContainer == txtNum4 && t.getId() == txtDenom2.getId()){
                txtNum4.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
                txtNum4.setBackgroundColor(Color.rgb(255,255,255));
                paintContainer();
                readyCheck();
            } else if (txtContainer == txtDenom4 && t.getId() == txtNum2.getId()){
                txtDenom4.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getNumerator()));
                txtDenom4.setBackgroundColor(Color.rgb(255,255,255));
                paintContainer();
                readyCheck();
            } else {
                wrong();
            }
        }
    }
    private class BtnCheckListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!inputNum.getText().toString().matches("")){
                if (!inputDenom.getText().toString().matches("")) {
                    if (Integer.valueOf(String.valueOf(inputNum.getText()))
                            ==fractionQuestions.get(questionNum).getFractionAnswer().getNumerator()
                            &&Integer.valueOf(String.valueOf(inputDenom.getText()))
                            ==fractionQuestions.get(questionNum).getFractionAnswer().getDenominator()){
                        correct();
                    } else {
                        wrong();
                    }
                } else {
                    Styles.shakeAnimate(inputDenom);
                }
            } else {
                Styles.shakeAnimate(inputNum);
            }
        }
    }
}
