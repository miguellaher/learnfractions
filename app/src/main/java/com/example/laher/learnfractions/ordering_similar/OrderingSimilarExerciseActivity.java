package com.example.laher.learnfractions.ordering_similar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.Question;
import com.example.laher.learnfractions.R;

import java.util.ArrayList;

public class OrderingSimilarExerciseActivity extends AppCompatActivity {
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Ordering Fractions";
    //GUI
    TextView txtNum1, txtNum2, txtNum3, txtScore, txtInstruction;
    //VARIABLES
    Question question;
    ArrayList<Question> questions;
    int questionNum;
    int consecutiveRights, consecutiveWrongs;
    int requiredConsecutiveCorrects = 10;
    int maxConsecutiveWrongs = 3;
    int clicks;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_similar_exercise);
        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderingSimilarExerciseActivity.this,
                        OrderingSimilarVideoActivity.class);
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
                Intent intent = new Intent(OrderingSimilarExerciseActivity.this,
                        OrderingSimilarExercise2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        //GUI
        txtNum1 = (TextView) findViewById(R.id.os_txtNum1);
        txtNum2 = (TextView) findViewById(R.id.os_txtNum2);
        txtNum3 = (TextView) findViewById(R.id.os_txtNum3);
        txtScore = (TextView) findViewById(R.id.os_txtScore);
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        txtInstruction = (TextView) findViewById(R.id.os_txtInstruction);

        go();
    }
    public void go(){
        setQuestions();
    }
    public void setQuestions(){
        questionNum = 0;
        questions = new ArrayList<>();
        for(int i = 0; i < requiredConsecutiveCorrects; i++) {
            question = new Question(Question.ORDERING_SIMILAR);
            questions.add(question);
        }
        setTxtNums();
    }
    public void setTxtNums(){
        clicks = 0;
        resetTxtNumsColor();
        txtNum1.setText(String.valueOf(questions.get(questionNum).getNum1()));
        txtNum2.setText(String.valueOf(questions.get(questionNum).getNum2()));
        txtNum3.setText(String.valueOf(questions.get(questionNum).getNum3()));
        setTxtNumsListener();
        txtInstruction.setText("Click from least to greatest.");
    }
    public void resetTxtNumsColor(){
        txtNum1.setTextColor(Color.rgb(128,128,128));
        txtNum2.setTextColor(Color.rgb(128,128,128));
        txtNum3.setTextColor(Color.rgb(128,128,128));
    }
    public void setTxtNumsListener(){
        txtNum1.setOnClickListener(new TxtNumsListener());
        txtNum2.setOnClickListener(new TxtNumsListener());
        txtNum3.setOnClickListener(new TxtNumsListener());
    }
    public void removeTxtNumsListener(){
        txtNum1.setOnClickListener(null);
        txtNum2.setOnClickListener(null);
        txtNum3.setOnClickListener(null);
    }
    public void correct(){
        consecutiveRights++;
        consecutiveWrongs = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        txtInstruction.setText("correct");
        removeTxtNumsListener();
        if (consecutiveRights>=requiredConsecutiveCorrects){
            txtInstruction.setText("finish");
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    questionNum++;
                    setTxtNums();
                }
            }, 2000);
        }
    }
    public void wrong(){
        consecutiveWrongs++;
        consecutiveRights = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        txtInstruction.setText("wrong");
        removeTxtNumsListener();
        if (consecutiveRights>=maxConsecutiveWrongs){
            txtInstruction.setText("go back");
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    go();
                }
            }, 2000);
        }
    }
    public class TxtNumsListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            t.setTextColor(Color.rgb(0,255,0));
            String s = t.getText().toString();
            if (s == String.valueOf(questions.get(questionNum).getIntegerNumAt(clicks))){
                clicks++;
                if (clicks>=3) {
                    correct();
                }
            } else {
                t.setTextColor(Color.rgb(255,0,0));
                wrong();
            }
        }
    }
}
