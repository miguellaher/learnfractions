package com.example.laher.learnfractions.lessons.fraction_meaning;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class FractionMeaningExercise2Activity extends AppCompatActivity {
    Context mContext = this;
    //COPY AND CHANGE VALUE
    private static final String TAG = "FM_E2";
    Exercise exercise;
    //COPY
    ExerciseStat mExerciseStat;
    final int EXERCISE_NUM = 2;

    ImageView imgBox1, imgBox2, imgBox3, imgBox4, imgBox5, imgBox6, imgBox7, imgBox8, imgBox9;
    EditText inputNum, inputDenom;
    TextView txtScore, txtInstruction;
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Fraction Meaning";
    Button btnOK;
    ConstraintLayout clChoices;
    int num, denom, correct, error;
    int maxInputLength = 3;

    int requiredCorrects;
    int maxErrors;

    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;

    //COPY
    long startingTime, endingTime;

    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_meaning_exercise2);
        clChoices = findViewById(R.id.a1_clChoices);
        clChoices.setVisibility(View.INVISIBLE);
        imgBox1 = findViewById(R.id.a1_imgBox1);
        imgBox2 = findViewById(R.id.a1_imgBox2);
        imgBox3 = findViewById(R.id.a1_imgBox3);
        imgBox4 = findViewById(R.id.a1_imgBox4);
        imgBox5 = findViewById(R.id.a1_imgBox5);
        imgBox6 = findViewById(R.id.a1_imgBox6);
        imgBox7 = findViewById(R.id.a1_imgBox7);
        imgBox8 = findViewById(R.id.a1_imgBox8);
        imgBox9 = findViewById(R.id.a1_imgBox9);

        inputNum = findViewById(R.id.a1_numerator);
        inputDenom = findViewById(R.id.a1_denominator);
        inputNum.setOnKeyListener(new InputListener());
        inputNum.setOnFocusChangeListener(new InputListener());
        inputNum.setOnEditorActionListener(new InputListener());
        inputDenom.setOnKeyListener(new InputListener());
        inputDenom.setOnFocusChangeListener(new InputListener());
        inputDenom.setOnEditorActionListener(new InputListener());
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        inputNum.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(maxInputLength)
        });
        inputDenom.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(maxInputLength)
        });

        btnOK = findViewById(R.id.a1_btnOk);
        btnOK.setOnClickListener(new BtnOkListener());
        btnOK.setEnabled(false);

        txtScore = findViewById(R.id.a1_txtScore);
        txtInstruction = findViewById(R.id.a1_txtInstruction);

        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        btnBack.setOnClickListener(new BtnBackListener());
        btnNext.setOnClickListener(new BtnNextListener());
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        btnNext.setText(AppConstants.DONE);


        exercise = LessonArchive.getLesson(AppConstants.FRACTION_MEANING).getExercises().get(EXERCISE_NUM - 1);
        //REPLACE
        setAttributes((ExerciseStat) exercise);
        if (!Storage.isEmpty()) {
            checkUpdate();
        }
        startingTime = System.currentTimeMillis();
        go();
    }

    //REPLACE
    public void setAttributes(ExerciseStat exerciseAtt){
        Log.d(TAG, "set attributes");
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
                        Log.d(TAG, "*service post execute");
                        //REPLACE
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
                        //REPLACE
                        setAttributes((ExerciseStat) updatedExercise);
                        startingTime = System.currentTimeMillis();
                    } catch (Exception e){e.printStackTrace();}
                }
            });
            Student student = new Student();
            student.setTeacher_code(Storage.load(mContext, Storage.TEACHER_CODE));
            ExerciseService.getUpdate(exercise, student, service);
        }
    }
    public void go(){
        btnNext.setText(AppConstants.DONE);
        btnNext.setEnabled(false);

        generateFraction();
        setBoxes(num, denom);
        inputNum.setEnabled(true);
        inputDenom.setEnabled(true);
        inputNum.setText("");
        inputDenom.setText("");
        setTxtScore();
        btnOK.setEnabled(false);
        inputNum.requestFocus();
    }
    public void reset(){
        btnOK.setEnabled(false);
        txtInstruction.setText("Fill in the blanks.");
        generateFraction();
        setBoxes(num, denom);
        inputNum.getText().clear();
        inputDenom.getText().clear();
        inputNum.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
    }
    public void generateFraction(){
        num = (int) (Math.random() * 9 + 1);
        denom = (int) (Math.random() * 9 + 1);
        while (denom<num) {
            denom = (int) (Math.random() * 9 + 1);
        }
    }
    public void setBoxes(int num, int denom){
        denom = denom - num;
        ArrayList<Integer> imageList = new ArrayList<>();
        for (int i = 1; i <= num; i++){
            imageList.add(R.drawable.chocolate);
        }
        for (int i = 1; i <= denom; i++){
            imageList.add(R.drawable.chocosmudge);
        }
        for (int i = imageList.size(); i <= 9; i++){
            imageList.add(0);
        }
        imgBox1.setImageResource(imageList.get(0));
        imgBox2.setImageResource(imageList.get(1));
        imgBox3.setImageResource(imageList.get(2));
        imgBox4.setImageResource(imageList.get(3));
        imgBox5.setImageResource(imageList.get(4));
        imgBox6.setImageResource(imageList.get(5));
        imgBox7.setImageResource(imageList.get(6));
        imgBox8.setImageResource(imageList.get(7));
        imgBox9.setImageResource(imageList.get(8));
    }
    public void setTxtScore(){
        txtScore.setText(AppConstants.SCORE(correct,requiredCorrects));
    }
    public void finishExercise(){
        txtInstruction.setText(AppConstants.FINISHED_LESSON);
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        btnOK.setEnabled(false);
        btnNext.setEnabled(true);
    }
    public class BtnOkListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if ((Integer.parseInt(String.valueOf(inputNum.getText())) == num) &&
                    (Integer.parseInt(String.valueOf(inputDenom.getText())) == denom)){
                if (errorsShouldBeConsecutive) {
                    error = 0;
                }
                correct++;
                setTxtScore();
                txtInstruction.setText(AppConstants.CORRECT);
                inputNum.setEnabled(false);
                inputDenom.setEnabled(false);
                btnOK.setEnabled(false);
                if (correct >= requiredCorrects){
                    //COPY
                    endingTime = System.currentTimeMillis();
                    if (!Storage.isEmpty()) {
                        setFinalAttributes();
                    }
                    finishExercise();
                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            inputNum.setEnabled(true);
                            inputDenom.setEnabled(true);
                            reset();
                        }
                    }, 2000);
                }
            } else {
                if (correctsShouldBeConsecutive) {
                    correct = 0;
                }
                error++;
                //COPY
                mExerciseStat.incrementError();
                setTxtScore();
                txtInstruction.setText(AppConstants.ERROR);
                inputNum.setEnabled(false);
                inputDenom.setEnabled(false);
                btnOK.setEnabled(false);
                if (error >= maxErrors){
                    if (errorsShouldBeConsecutive) {
                        txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(error));
                    } else {
                        txtInstruction.setText(AppConstants.FAILED(error));
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(FractionMeaningExercise2Activity.this,
                                    FractionMeaningExerciseActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }, 3000);
                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            inputNum.setEnabled(true);
                            inputDenom.setEnabled(true);
                            reset();
                        }
                    }, 2000);
                }
            }
        }
    }
    //COPY
    private void setFinalAttributes(){
        Service service = new Service("Posting exercise stats...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try{
                    Log.d(TAG, "post execute");
                    Log.d(TAG, "message: " + response.optString("message"));
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


    public class InputListener implements View.OnKeyListener, View.OnFocusChangeListener, TextView.OnEditorActionListener{

        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if ((inputNum.getText().toString().trim().length() == 0)
                    || (inputDenom.getText().toString().trim().length() == 0)){
                btnOK.setEnabled(false);
            } else {
                //txtInstruction.setText(inputNum.getText().toString().trim().length() + "/" + inputDenom.getText().toString().trim().length());
                btnOK.setEnabled(true);
            }
            return false;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus){
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){
                btnOK.performClick();
            }
            return false;
        }
    }

    public class BtnBackListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(FractionMeaningExercise2Activity.this,
                    FractionMeaningExerciseActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public class BtnNextListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(FractionMeaningExercise2Activity.this, TopicsMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


}
