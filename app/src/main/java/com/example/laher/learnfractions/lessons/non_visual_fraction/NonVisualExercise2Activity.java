package com.example.laher.learnfractions.lessons.non_visual_fraction;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;
import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.model.ExerciseStat;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.ExerciseStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class NonVisualExercise2Activity extends AppCompatActivity {
    Context mContext = this;
    private static final String TAG = "NV_E2";

    Exercise exercise;
    ExerciseStat mExerciseStat;
    final int EXERCISE_NUM = 2;

    Button btnBack, btnNext;
    TextView txtTitle;

    Button btnCheck;
    TextView txtNumerator, txtDenominator, txtInstruction, txtInstruction2, txtScore;
    EditText inputAnswer;
    int num, denom, correct, error;
    ArrayList<String> instructions;
    public final String INSTRUCTION_NUM = "Type the numerator.";
    public final String INSTRUCTION_DENOM = "Type the denominator.";
    public final String TITLE = "NON-VISUAL";
    final Handler handler = new Handler();

    int requiredCorrects;
    int maxErrors;

    long startingTime, endingTime;

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


        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NonVisualExercise2Activity.this,
                        NonVisualExerciseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = findViewById(R.id.btnNext);
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
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        btnNext.setText(AppConstants.DONE);

        txtNumerator = findViewById(R.id.b2_txtNumerator);
        txtDenominator = findViewById(R.id.b2_txtDenominator);
        txtInstruction = findViewById(R.id.b2_txtInstruction);
        txtInstruction2 = findViewById(R.id.b2_txtInstruction2);
        txtScore = findViewById(R.id.b2_txtScore);
        setTxtScore();
        inputAnswer = findViewById(R.id.b2_inputAnswer);
        inputAnswer.getText().clear();
        inputAnswer.setOnFocusChangeListener(new InputAnswerListener());
        inputAnswer.setOnEditorActionListener(new InputAnswerListener());
        inputAnswer.addTextChangedListener(new InputAnswerListener());
        btnCheck = findViewById(R.id.b2_btnCheck);
        btnCheck.setOnClickListener(new BtnChkListener());
        btnCheck.setEnabled(false);

        instructions = new ArrayList<>();
        instructions.add(INSTRUCTION_NUM);
        instructions.add(INSTRUCTION_DENOM);



        setAttributes((ExerciseStat) exercise);
        if (!Storage.isEmpty()) {
            checkUpdate();
        }

        startingTime = System.currentTimeMillis();
        go();
    }

    public void setAttributes(ExerciseStat exerciseAtt){//REPLACE
        requiredCorrects = exerciseAtt.getRequiredCorrects();
        maxErrors = exerciseAtt.getMaxErrors();
        correctsShouldBeConsecutive = exerciseAtt.isRc_consecutive();
        errorsShouldBeConsecutive = exerciseAtt.isMe_consecutive();
        mExerciseStat = exerciseAtt;
        mExerciseStat.setTopicName(exercise.getTopicName());
        mExerciseStat.setExerciseNum(exercise.getExerciseNum());
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
                            Exercise updatedExercise = new ExerciseStat();
                            Log.d(TAG, "rc:" + response.optString("required_corrects"));
                            Log.d(TAG, "rcc:" + response.optString("rc_consecutive"));
                            Log.d(TAG, "me:" + response.optString("max_errors"));
                            Log.d(TAG, "mec:" + response.optString("me_consecutive"));
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
                            setAttributes((ExerciseStat) updatedExercise);
                            startingTime = System.currentTimeMillis();
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
        if (txtInstruction.getText().toString().equals(INSTRUCTION_NUM)){
            txtInstruction2.setText(AppConstants.NUMERATOR);
        } else {
            txtInstruction2.setText(AppConstants.DENOMINATOR);
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
        inputAnswer.setEnabled(false);
        btnCheck.setEnabled(false);
        txtInstruction.setText(AppConstants.CORRECT);
        if (correct >= requiredCorrects){
            endingTime = System.currentTimeMillis();
            if (!Storage.isEmpty()) {
                setFinalAttributes();
            }
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
        mExerciseStat.incrementError();
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
            Styles.shakeAnimate(inputAnswer);
            Styles.paintRed(inputAnswer);
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
    private void setFinalAttributes(){//COPY
        Service service = new Service("Posting exercise stats...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try{
                    Log.d(TAG, "post execute");
                    Log.d(TAG, "message: " + response.optString("message"));
                    btnNext.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mExerciseStat.setDone(true);
        mExerciseStat.setTime_spent(endingTime-startingTime);
        Student student = new Student();
        student.setId(Storage.load(mContext, Storage.STUDENT_ID));
        student.setTeacher_code(Storage.load(mContext, Storage.TEACHER_CODE));
        Log.d(TAG, "ATTRIBUTES: teacher_code: " + student.getTeacher_code() + "; student_id: " + student.getId() + "topic_name: " +
                mExerciseStat.getTopicName() + "; exercise_num: " + mExerciseStat.getExerciseNum() + "; done: " + mExerciseStat.isDone() +
                "; time_spent: " + mExerciseStat.getTime_spent() + "; errors: " + mExerciseStat.getErrors() + "; required_corrects: " +
                mExerciseStat.getRequiredCorrects() + "; rc_consecutive: " + mExerciseStat.isRc_consecutive() + "; max_errors: " +
                mExerciseStat.getMaxErrors() + "; me_consecutive: " + mExerciseStat.isMe_consecutive());
        ExerciseStatService.postStats(student,mExerciseStat,service);
    }
    public void reset(){
        inputAnswer.getText().clear();
        Styles.paintBlack(inputAnswer);
        btnCheck.setEnabled(false);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
    }

    public class BtnChkListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (inputAnswer.getText().toString().trim().length() != 0) {
                if (instructions.get(0).equals(INSTRUCTION_NUM)) {
                    //if(inputAnswer.getText() == txtNumerator.getText()){
                    if (Integer.parseInt(String.valueOf(inputAnswer.getText())) == num) {
                        correct();
                    } else {
                        wrong();
                    }
                } else if (instructions.get(0).equals(INSTRUCTION_DENOM)) {
                    if (Integer.parseInt(String.valueOf(inputAnswer.getText())) == denom) {
                        correct();
                    } else {
                        wrong();
                    }
                }
            }
        }
    }
    public class InputAnswerListener implements View.OnFocusChangeListener, TextView.OnEditorActionListener, TextWatcher{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus){
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){
                btnCheck.performClick();
                return false;
            }
            return false;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (inputAnswer.getText().toString().trim().length() == 0){
                btnCheck.setEnabled(false);
            } else {
                btnCheck.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
