package com.example.laher.learnfractions.lessons.comparing_similar_fractions;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

public class ComparingSimilarExerciseActivity extends AppCompatActivity {
    Context mContext = this;

    Exercise exercise;
    final int EXERCISE_NUM = 1;
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Comparing Similar";
    //GUI
    TextView txtNum1, txtNum2, txtScore, txtCompareSign, txtInstruction;
    Button btnGreater, btnEquals, btnLess;
    //VARIABLES
    int num1, num2, correct, error;
    public final String GREATER_THAN = ">";
    public final String EQUAL_TO = "=";
    public final String LESS_THAN = "<";
    final Handler handler = new Handler();

    int requiredCorrects;
    int maxErrors;
    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparing_similar_exercise);
        exercise = LessonArchive.getLesson(AppConstants.COMPARING_SIMILAR_FRACTIONS).getExercises().get(EXERCISE_NUM-1);

        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComparingSimilarExerciseActivity.this,
                        ComparingSimilarVideoActivity.class);
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
                Intent intent = new Intent(ComparingSimilarExerciseActivity.this, ComparingSimilarExercise2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        //GUI
        txtNum1 = (TextView) findViewById(R.id.c1_txtNum1);
        txtNum2 = (TextView) findViewById(R.id.c1_txtNum2);
        txtCompareSign = (TextView) findViewById(R.id.c1_txtCompareSign);
        txtScore = (TextView) findViewById(R.id.c1_txtScore);
        setTxtScore();
        txtInstruction = (TextView) findViewById(R.id.c1_txtInstruction);

        btnGreater = (Button) findViewById(R.id.c1_btnGreater);
        btnEquals = (Button) findViewById(R.id.c1_btnEquals);
        btnLess = (Button) findViewById(R.id.c1_btnLess);
        btnGreater.setOnClickListener(new BtnListener());
        btnEquals.setOnClickListener(new BtnListener());
        btnLess.setOnClickListener(new BtnListener());


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
        txtCompareSign.setText("_");
        txtInstruction.setText("compare the two numbers");
        generateNumbers();
    }
    public void generateNumbers(){
        num1 = (int) (Math.random() * 9 + 1);
        num2 = (int) (Math.random() * 9 + 1);
        setNumbers();
    }
    public void setNumbers(){
        txtNum1.setText(String.valueOf(num1));
        txtNum2.setText(String.valueOf(num2));
    }
    public class BtnListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == btnGreater.getId()){
                txtCompareSign.setText(GREATER_THAN);
                check(GREATER_THAN);
            }
            if (v.getId() == btnEquals.getId()){
                txtCompareSign.setText(EQUAL_TO);
                check(EQUAL_TO);
            }
            if (v.getId() == btnLess.getId()){
                txtCompareSign.setText(LESS_THAN);
                check(LESS_THAN);
            }
        }
    }
    public void check(String compareSign){
        if (compareSign == GREATER_THAN){
            if (num1 > num2){
                correct();
            } else {
                wrong();
            }
        }
        if (compareSign == EQUAL_TO){
            if (num1 == num2){
                correct();
            } else {
                wrong();
            }
        }
        if (compareSign == LESS_THAN){
            if (num1 < num2){
                correct();
            } else {
                wrong();
            }
        }
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
        btnGreater.setEnabled(false);
        btnEquals.setEnabled(false);
        btnLess.setEnabled(false);
        txtInstruction.setText(AppConstants.CORRECT);
        if (correct >= requiredCorrects){
            btnNext.setEnabled(true);
            txtInstruction.setText(AppConstants.FINISHED_EXERCISE);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnGreater.setEnabled(true);
                    btnEquals.setEnabled(true);
                    btnLess.setEnabled(true);
                    go();
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
        btnGreater.setEnabled(false);
        btnEquals.setEnabled(false);
        btnLess.setEnabled(false);
        txtInstruction.setText(AppConstants.ERROR);
        if (error >= maxErrors){
            if (errorsShouldBeConsecutive) {
                txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(error));
            } else {
                txtInstruction.setText(AppConstants.FAILED(error));
            }

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(ComparingSimilarExerciseActivity.this,
                            ComparingSimilarVideoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 3000);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnGreater.setEnabled(true);
                    btnEquals.setEnabled(true);
                    btnLess.setEnabled(true);
                    go();
                }
            }, 2000);
        }
    }

}