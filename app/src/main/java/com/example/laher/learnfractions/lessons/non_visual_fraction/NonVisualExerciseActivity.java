package com.example.laher.learnfractions.lessons.non_visual_fraction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Collections;

public class NonVisualExerciseActivity extends AppCompatActivity {
    Context mContext = this;

    Exercise exercise;
    final int EXERCISE_NUM = 1;

    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "NON-VISUAL";

    TextView txtNumerator, txtDenominator, txtInstruction, txtScore;
    int num, denom, correct, error;
    ArrayList<String> instructions;
    public final String INSTRUCTION_NUM = "click the numerator";
    public final String INSTRUCTION_DENOM = "click the denominator";
    final Handler handler = new Handler();

    int requiredCorrects;
    int maxErrors;
    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_visual_exercise);
        exercise = LessonArchive.getLesson(AppConstants.NON_VISUAL_FRACTION).getExercises().get(EXERCISE_NUM-1);


        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NonVisualExerciseActivity.this, NonVisualVideoActivity.class);
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
                Intent intent = new Intent(NonVisualExerciseActivity.this, NonVisualExercise2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        setTxtScore();

        instructions = new ArrayList<>();
        instructions.add(INSTRUCTION_NUM);
        instructions.add(INSTRUCTION_DENOM);
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
    public void go() {
        resetColor();
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
        txtInstruction.setText(AppConstants.CORRECT);
        if (errorsShouldBeConsecutive) {
            error = 0;
        }
        correct++;
        setTxtScore();
        txtNumerator.setOnClickListener(null);
        txtDenominator.setOnClickListener(null);
        if (correct == requiredCorrects){
            txtInstruction.setText(AppConstants.FINISHED_EXERCISE);

            btnNext.setEnabled(true);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    txtNumerator.setOnClickListener(new TxtFractionListener());
                    txtDenominator.setOnClickListener(new TxtFractionListener());
                    go();
                }
            }, 2000);
        }
    }

    public void setTxtScore(){
        txtScore.setText(AppConstants.SCORE(correct,requiredCorrects));
    }
    public void wrong(){
        txtInstruction.setText(AppConstants.ERROR);
        if (correctsShouldBeConsecutive) {
            correct = 0;
        }
        error++;
        setTxtScore();
        txtNumerator.setOnClickListener(null);
        txtDenominator.setOnClickListener(null);
        if (error == maxErrors){
            if (errorsShouldBeConsecutive) {
                txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(error));
            } else {
                txtInstruction.setText(AppConstants.FAILED(error));
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(NonVisualExerciseActivity.this,
                            NonVisualVideoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 3000);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    txtNumerator.setOnClickListener(new TxtFractionListener());
                    txtDenominator.setOnClickListener(new TxtFractionListener());
                    go();
                }
            }, 2000);
        }
    }
    public void resetColor() {
        txtNumerator.setTextColor(Color.rgb(0,0,0));
        txtDenominator.setTextColor(Color.rgb(0,0,0));
    }
    public class TxtFractionListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.b1_txtNumerator){
                if (txtInstruction.getText()==INSTRUCTION_NUM){
                    txtNumerator.setTextColor(Color.rgb(0,255,0));
                    correct();
                } else {
                    txtNumerator.setTextColor(Color.rgb(255,0,0));
                    wrong();
                }
            } else if (v.getId() == R.id.b1_txtDenominator){
                if (txtInstruction.getText()==INSTRUCTION_DENOM){
                    txtDenominator.setTextColor(Color.rgb(0,255,0));
                    correct();
                } else {
                    txtDenominator.setTextColor(Color.rgb(255,0,0));
                    wrong();
                }
            }
        }
    }
}
