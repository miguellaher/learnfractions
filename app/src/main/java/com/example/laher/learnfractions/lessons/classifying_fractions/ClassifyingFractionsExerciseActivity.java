package com.example.laher.learnfractions.lessons.classifying_fractions;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;
import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ClassifyingFractionsExerciseActivity extends AppCompatActivity {
    Context mContext = this;

    Exercise exercise;
    final int EXERCISE_NUM = 1;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Classifying Fractions";
    //GUI
    TextView txtNum, txtDenom, txtWholeNum, txtScore, txtInstruction;
    Button btnProper, btnImproper, btnMixed;
    //VARIABLES
    Fraction fraction;
    ArrayList<Fraction> fractions;
    int questionNum;
    int correct, error;
    int requiredCorrects;
    int maxErrors;
    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifying_fractions_exercise);
        exercise = LessonArchive.getLesson(AppConstants.CLASSIFYING_FRACTIONS).getExercises().get(EXERCISE_NUM-1);

        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassifyingFractionsExerciseActivity.this,
                        ClassifyingFractionsVideoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CHANGE INTENT PARAMS
                Intent intent = new Intent(ClassifyingFractionsExerciseActivity.this,
                        TopicsMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        btnNext.setText(AppConstants.DONE);
        //GUI
        txtNum = (TextView) findViewById(R.id.clF_txtNum);
        txtDenom = (TextView) findViewById(R.id.clF_txtDenom);
        txtWholeNum = (TextView) findViewById(R.id.clF_txtWholeNum);
        txtScore = (TextView) findViewById(R.id.clF_txtScore);
        txtInstruction = (TextView) findViewById(R.id.clF_txtInstruction);
        btnProper = (Button) findViewById(R.id.clF_btnProper);
        btnImproper = (Button) findViewById(R.id.clF_btnImproper);
        btnMixed = (Button) findViewById(R.id.clF_btnMixed);
        btnProper.setOnClickListener(new BtnAnswerListener());
        btnImproper.setOnClickListener(new BtnAnswerListener());
        btnMixed.setOnClickListener(new BtnAnswerListener());

        setAttributes(exercise);
        checkUpdate();

        go();
    }

    public void setAttributes(Exercise exerciseAtt){
        requiredCorrects = exerciseAtt.getRequiredCorrects();
        maxErrors = exerciseAtt.getMaxErrors();
        correctsShouldBeConsecutive = exerciseAtt.isRc_consecutive();
        errorsShouldBeConsecutive = exerciseAtt.isMe_consecutive();
        setTxtScore();
    }
    public void checkUpdate(){
        if (Storage.load(mContext, Storage.USER_TYPE).equals(AppConstants.STUDENT)){
            Service service = new Service("Checking for updates...", mContext, new ServiceResponse() {
                @Override
                public void postExecute(JSONObject response) {
                    try {
                        if (response.optString("message") != null && response.optString("message").equals("Exercise not found.")){
                        } else {
                            Util.toast(mContext,"Exercise updated.");
                            Exercise updatedExercise = new Exercise();
                            updatedExercise.setRequiredCorrects(Integer.valueOf(response.optString("required_corrects")));
                            if (response.optString("rc_consecutive").equals("1")) {
                                updatedExercise.setRc_consecutive(true);
                            } else {
                                updatedExercise.setRc_consecutive(false);
                            }
                            updatedExercise.setMaxErrors(Integer.valueOf(response.optString("max_errors")));
                            if (response.optString("me_consecutive").equals("1")) {
                                updatedExercise.setMe_consecutive(true);
                            } else {
                                updatedExercise.setMe_consecutive(false);
                            }
                            setAttributes(updatedExercise);
                        }
                    } catch (Exception e){e.printStackTrace();}
                }
            });
            Student student = new Student();
            student.setTeacher_code(Storage.load(mContext, Storage.TEACHER_CODE));
            ExerciseService.getUpdate(exercise, student, service);
        }
    }
    public void go(){
        setFractionQuestions();
        setGuiFraction();
        startUp();
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
        enableButtons(false);
        if (correct >=requiredCorrects){
            txtInstruction.setText(AppConstants.FINISHED_LESSON);
            btnNext.setEnabled(true);
        } else {
            txtInstruction.setText(AppConstants.CORRECT);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextQuestion();
                }
            },2000);
        }
    }
    public void nextQuestion(){
        enableButtons(true);
        questionNum++;
        setGuiFraction();
    }
    public void wrong(){
        error++;
        if (correctsShouldBeConsecutive) {
            correct = 0;
        }
        setTxtScore();
        enableButtons(false);
        if (error >=maxErrors){
            if (errorsShouldBeConsecutive) {
                txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(error));
            } else {
                txtInstruction.setText(AppConstants.FAILED(error));
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(ClassifyingFractionsExerciseActivity.this,
                            ClassifyingFractionsVideoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            },4000);
        } else {
            txtInstruction.setText(AppConstants.ERROR);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    enableButtons(true);
                    if (correctsShouldBeConsecutive) {
                        go();
                    } else {
                        if (fractions.get(questionNum).getContext()==Fraction.PROPER){
                            fraction = new Fraction(Fraction.PROPER);
                        } else if (fractions.get(questionNum).getContext()==Fraction.IMPROPER){
                            fraction = new Fraction(Fraction.IMPROPER);
                        } else if (fractions.get(questionNum).getContext()==Fraction.MIXED){
                            fraction = new Fraction(Fraction.MIXED);
                        }
                        fractions.add(fraction);
                        nextQuestion();
                    }
                }
            },2000);
        }
    }
    public void startUp(){
        setTxtScore();
    }
    public void enableButtons(Boolean bool){
        btnProper.setEnabled(bool);
        btnImproper.setEnabled(bool);
        btnMixed.setEnabled(bool);
    }
    public void setFractionQuestions(){
        questionNum = 0;
        fractions = new ArrayList<>();
        for (int i = 0; i < requiredCorrects; i++){
            if (i < (requiredCorrects/3)){
                fraction = new Fraction(Fraction.PROPER);
            } else if (i < ((requiredCorrects*2)/3)){
                fraction = new Fraction(Fraction.IMPROPER);
            } else {
                fraction = new Fraction(Fraction.MIXED);
            }
            fractions.add(fraction);
        }
        Collections.shuffle(fractions);
    }
    public void setGuiFraction(){
        if (Fraction.MIXED == fractions.get(questionNum).getContext()){
            txtWholeNum.setText(String.valueOf(fractions.get(questionNum).getWholeNum()));
        } else {
            txtWholeNum.setText("");
        }
        txtNum.setText(String.valueOf(fractions.get(questionNum).getNumerator()));
        txtDenom.setText(String.valueOf(fractions.get(questionNum).getDenominator()));
    }
    public void check(String context){
        if (context == fractions.get(questionNum).getContext()){
            correct();
        } else {
            wrong();
        }
    }
    public class BtnAnswerListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId()==btnProper.getId()){
                check(Fraction.PROPER);
            } else if (v.getId()==btnImproper.getId()){
                check(Fraction.IMPROPER);
            } else if (v.getId()==btnMixed.getId()){
                check(Fraction.MIXED);
            }
        }
    }
}
