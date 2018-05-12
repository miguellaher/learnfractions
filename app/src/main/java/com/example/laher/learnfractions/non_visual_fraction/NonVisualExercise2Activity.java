package com.example.laher.learnfractions.non_visual_fraction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;
import com.example.laher.learnfractions.fractionmeaning.FractionMeaningExercise2Activity;

import java.util.ArrayList;
import java.util.Collections;

public class NonVisualExercise2Activity extends AppCompatActivity {
    Button btnBack, btnNext;
    TextView txtTitle;

    Button btnCheck;
    TextView txtNumerator, txtDenominator, txtInstruction, txtScore;
    EditText inputAnswer;
    int num, denom, consecutiveRights, consecutiveWrongs;
    ArrayList<String> instructions;
    public final String INSTRUCTION_NUM = "type the numerator";
    public final String INSTRUCTION_DENOM = "type the denominator";
    public final String TITLE = "NON-VISUAL";
    final Handler handler = new Handler();

    int requiredConsecutiveCorrects = 8;
    int maxConsecutiveWrongs = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_visual_exercise2);

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
                Intent intent = new Intent(NonVisualExercise2Activity.this, TopicsMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        btnNext.setText("DONE");

        txtNumerator = (TextView) findViewById(R.id.b2_txtNumerator);
        txtDenominator = (TextView) findViewById(R.id.b2_txtDenominator);
        txtInstruction = (TextView) findViewById(R.id.b2_txtInstruction);
        txtScore = (TextView) findViewById(R.id.b2_txtScore);
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        inputAnswer = (EditText) findViewById(R.id.b2_inputAnswer);
        inputAnswer.getText().clear();
        inputAnswer.setOnKeyListener(new InputAnswerListener());
        btnCheck = (Button) findViewById(R.id.b2_btnCheck);
        btnCheck.setOnClickListener(new BtnChkListener());
        btnCheck.setEnabled(false);

        instructions = new ArrayList<>();
        instructions.add(INSTRUCTION_NUM);
        instructions.add(INSTRUCTION_DENOM);
        go();
    }
    public void go(){
        reset();
        generateFraction();
        generateInstruction();
    }

    public void generateFraction(){
        num = (int) (Math.random() * 9 + 1);
        denom = (int) (Math.random() * 9 + 1);
        while (denom==num) {
            denom = (int) (Math.random() * 9 + 1);
        }
        setTxtFraction();
    }
    public void setTxtFraction(){
        txtNumerator.setText(String.valueOf(num));
        txtDenominator.setText(String.valueOf(denom));
    }
    public void generateInstruction(){
        Collections.shuffle(instructions);
        txtInstruction.setText(instructions.get(0));
    }
    public void correct(){
        consecutiveRights++;
        consecutiveWrongs = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        inputAnswer.setEnabled(false);
        btnCheck.setEnabled(false);
        txtInstruction.setText("correct");
        if (consecutiveRights >= requiredConsecutiveCorrects){
            txtInstruction.setText("Finish");
            inputAnswer.getText().clear();
            btnNext.setEnabled(true);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    inputAnswer.setEnabled(true);
                    btnCheck.setEnabled(true);
                    go();
                }
            }, 2000);
        }
    }
    public void wrong(){
        consecutiveWrongs++;
        consecutiveRights = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        inputAnswer.setEnabled(false);
        btnCheck.setEnabled(false);
        txtInstruction.setText("wrong");
        if (consecutiveWrongs >= maxConsecutiveWrongs){
            txtInstruction.setText("go back to activity 1");
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    inputAnswer.setEnabled(true);
                    btnCheck.setEnabled(true);
                    go();
                }
            }, 2000);
        }
    }
    public void reset(){
        inputAnswer.getText().clear();
        btnCheck.setEnabled(false);
    }

    public class BtnChkListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (inputAnswer.getText().toString().trim().length() != 0) {
                if (instructions.get(0) == INSTRUCTION_NUM) {
                    //if(inputAnswer.getText() == txtNumerator.getText()){
                    if (Integer.parseInt(String.valueOf(inputAnswer.getText())) == num) {
                        correct();
                    } else {
                        wrong();
                    }
                } else if (instructions.get(0) == INSTRUCTION_DENOM) {
                    if (Integer.parseInt(String.valueOf(inputAnswer.getText())) == denom) {
                        correct();
                    } else {
                        wrong();
                    }
                }
            }
        }
    }
    public class InputAnswerListener implements View.OnKeyListener{
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (inputAnswer.getText().toString().trim().length() == 0){
                btnCheck.setEnabled(false);
            } else {
                btnCheck.setEnabled(true);
            }
            return false;
        }
    }
}
