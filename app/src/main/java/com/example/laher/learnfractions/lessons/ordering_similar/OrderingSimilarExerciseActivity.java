package com.example.laher.learnfractions.lessons.ordering_similar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.fraction_util.Question;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.util.AppConstants;

import java.util.ArrayList;

public class OrderingSimilarExerciseActivity extends AppCompatActivity {
    Exercise exercise;
    final int EXERCISE_NUM = 1;

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
    int correct, error;
    int requiredCorrects;
    int maxErrors;
    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;
    int clicks;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_similar_exercise);
        exercise = LessonArchive.getLesson(AppConstants.ORDERING_SIMILAR).getExercises().get(EXERCISE_NUM-1);
        requiredCorrects = exercise.getRequiredCorrects();
        maxErrors = exercise.getMaxErrors();
        correctsShouldBeConsecutive = exercise.isRc_consecutive();
        errorsShouldBeConsecutive = exercise.isMe_consecutive();

        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderingSimilarExerciseActivity.this,
                        OrderingSimilarVideoActivity.class);
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
                Intent intent = new Intent(OrderingSimilarExerciseActivity.this,
                        OrderingSimilarExercise2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        setTxtScore();
        txtInstruction = (TextView) findViewById(R.id.os_txtInstruction);

        go();
    }
    public void go(){
        setQuestions();
    }
    public void setQuestions(){
        questionNum = 0;
        questions = new ArrayList<>();
        for(int i = 0; i < requiredCorrects; i++) {
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
    public void setTxtScore(){
        txtScore.setText(AppConstants.SCORE(correct,requiredCorrects));
    }
    public void correct(){
        correct++;
        if (errorsShouldBeConsecutive) {
            error = 0;
        }
        setTxtScore();
        txtInstruction.setText(AppConstants.CORRECT);
        removeTxtNumsListener();
        if (correct >= requiredCorrects){
            txtInstruction.setText(AppConstants.FINISHED_EXERCISE);
            btnNext.setEnabled(true);
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
        error++;
        if (correctsShouldBeConsecutive) {
            correct = 0;
        }
        setTxtScore();
        txtInstruction.setText(AppConstants.ERROR);
        removeTxtNumsListener();
        if (correct >= maxErrors){
            if (errorsShouldBeConsecutive) {
                txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(error));
            } else {
                txtInstruction.setText(AppConstants.FAILED(error));
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(OrderingSimilarExerciseActivity.this,
                            OrderingSimilarVideoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 3000);

        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (correctsShouldBeConsecutive) {
                        go();
                    } else {
                        question = new Question(Question.ORDERING_SIMILAR);
                        questions.add(question);
                        questionNum++;
                        setTxtNums();
                    }
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
