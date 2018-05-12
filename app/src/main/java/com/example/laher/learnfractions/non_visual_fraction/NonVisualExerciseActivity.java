package com.example.laher.learnfractions.non_visual_fraction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.fractionmeaning.FractionMeaningExercise2Activity;
import com.example.laher.learnfractions.fractionmeaning.FractionMeaningExerciseActivity;

import java.util.ArrayList;
import java.util.Collections;

public class NonVisualExerciseActivity extends AppCompatActivity {
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "NON-VISUAL";

    TextView txtNumerator, txtDenominator, txtInstruction, txtScore;
    int num, denom, consecutiveRights, consecutiveWrongs;
    ArrayList<String> instructions;
    public final String INSTRUCTION_NUM = "click the numerator";
    public final String INSTRUCTION_DENOM = "click the denominator";

    int requiredConsecutiveCorrects = 8;
    int maxConsecutiveWrongs = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_visual_exercise);

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
                Intent intent = new Intent(NonVisualExerciseActivity.this, NonVisualExercise2Activity.class);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);

        txtNumerator = (TextView) findViewById(R.id.b1_txtNumerator);
        txtDenominator = (TextView) findViewById(R.id.b1_txtDenominator);
        txtInstruction = (TextView) findViewById(R.id.b1_txtInstruction);
        txtNumerator.setOnClickListener(new TxtFractionListener());
        txtDenominator.setOnClickListener(new TxtFractionListener());
        txtScore = (TextView) findViewById(R.id.b1_txtScore);
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);

        instructions = new ArrayList<>();
        instructions.add(INSTRUCTION_NUM);
        instructions.add(INSTRUCTION_DENOM);
        go();
    }
    public void go(){
        generateFraction();
        generateInstruction();
    }
    public void generateFraction(){
        num = (int) (Math.random() * 9 + 1);
        denom = (int) (Math.random() * 9 + 1);
        while (denom<num) {
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
        txtInstruction.setText("correct");
        consecutiveWrongs = 0;
        consecutiveRights++;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        if (consecutiveRights == requiredConsecutiveCorrects){
            txtInstruction.setText("done");
            txtNumerator.setOnClickListener(null);
            txtDenominator.setOnClickListener(null);
            btnNext.setEnabled(true);
        } else {
            go();
        }
    }
    public void wrong(){
        txtInstruction.setText("wrong");
        consecutiveRights = 0;
        consecutiveWrongs++;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        if (consecutiveWrongs == maxConsecutiveWrongs){
            txtInstruction.setText("go back to video");
        } else {
            go();
        }
    }
    public class TxtFractionListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.b1_txtNumerator){
                if (txtInstruction.getText()==INSTRUCTION_NUM){
                    correct();
                } else {
                    wrong();
                }
            } else if (v.getId() == R.id.b1_txtDenominator){
                if (txtInstruction.getText()==INSTRUCTION_DENOM){
                    correct();
                } else {
                    wrong();
                }
            }
        }
    }
}
