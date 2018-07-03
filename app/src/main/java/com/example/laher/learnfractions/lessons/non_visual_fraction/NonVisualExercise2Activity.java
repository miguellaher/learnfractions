package com.example.laher.learnfractions.lessons.non_visual_fraction;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;
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

public class NonVisualExercise2Activity extends AppCompatActivity {
    Context mContext = this;

    Exercise exercise;
    final int EXERCISE_NUM = 2;

    Button btnBack, btnNext;
    TextView txtTitle;

    Button btnCheck;
    TextView txtNumerator, txtDenominator, txtInstruction, txtScore;
    EditText inputAnswer;
    int num, denom, correct, error;
    ArrayList<String> instructions;
    public final String INSTRUCTION_NUM = "Type the numerator.";
    public final String INSTRUCTION_DENOM = "Type the denominator.";
    public final String TITLE = "NON-VISUAL";
    final Handler handler = new Handler();

    int requiredCorrects;
    int maxErrors;
    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_visual_exercise2);
        exercise = LessonArchive.getLesson(AppConstants.NON_VISUAL_FRACTION).getExercises().get(EXERCISE_NUM-1);
        requiredCorrects = exercise.getRequiredCorrects();
        maxErrors = exercise.getMaxErrors();
        correctsShouldBeConsecutive = exercise.isRc_consecutive();
        errorsShouldBeConsecutive = exercise.isMe_consecutive();


        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NonVisualExercise2Activity.this,
                        NonVisualExerciseActivity.class);
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
                Intent intent = new Intent(NonVisualExercise2Activity.this, TopicsMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        btnNext.setText(AppConstants.DONE);

        txtNumerator = (TextView) findViewById(R.id.b2_txtNumerator);
        txtDenominator = (TextView) findViewById(R.id.b2_txtDenominator);
        txtInstruction = (TextView) findViewById(R.id.b2_txtInstruction);
        txtScore = (TextView) findViewById(R.id.b2_txtScore);
        setTxtScore();
        inputAnswer = (EditText) findViewById(R.id.b2_inputAnswer);
        inputAnswer.getText().clear();
        inputAnswer.setOnKeyListener(new InputAnswerListener());
        btnCheck = (Button) findViewById(R.id.b2_btnCheck);
        btnCheck.setOnClickListener(new BtnChkListener());
        btnCheck.setEnabled(false);

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
    public void setTxtScore(){
        txtScore.setText(AppConstants.SCORE(correct,requiredCorrects));
    }
    public void correct(){
        correct++;
        if (errorsShouldBeConsecutive) {
            error = 0;
        }
        setTxtScore();
        inputAnswer.setEnabled(false);
        btnCheck.setEnabled(false);
        txtInstruction.setText(AppConstants.CORRECT);
        if (correct >= requiredCorrects){
            txtInstruction.setText(AppConstants.FINISHED_LESSON);
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
        error++;
        if (correctsShouldBeConsecutive) {
            correct = 0;
        }
        setTxtScore();
        inputAnswer.setEnabled(false);
        btnCheck.setEnabled(false);
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
                    Intent intent = new Intent(NonVisualExercise2Activity.this,
                            NonVisualExerciseActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 3000);
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
