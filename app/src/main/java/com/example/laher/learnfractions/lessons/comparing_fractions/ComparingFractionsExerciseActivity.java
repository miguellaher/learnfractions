package com.example.laher.learnfractions.lessons.comparing_fractions;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.model.ExerciseStat;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.ExerciseStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;

import org.json.JSONObject;

public class ComparingFractionsExerciseActivity extends AppCompatActivity {
    Context mContext = this;
    private static final String TAG = "CF_E1";

    Exercise exercise;
    ExerciseStat mExerciseStat;
    final int EXERCISE_NUM = 1;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Comparing Fractions";
    //GUI
    TextView txtNum1, txtNum2, txtDenom1, txtDenom2, txtScore, txtInstruction;
    Button btnSimilar, btnDissimilar;
    //VARIABLES
    Fraction fractionOne, fractionTwo;

    int correct, error;
    int requiredCorrects;
    int maxErrors;

    long startingTime, endingTime;

    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;

    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        exercise = LessonArchive.getLesson(AppConstants.COMPARING_FRACTIONS).getExercises().get(EXERCISE_NUM-1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparing_fractions_exercise);
        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComparingFractionsExerciseActivity.this,
                        ComparingFractionsVideoActivity.class);
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
                Intent intent = new Intent(ComparingFractionsExerciseActivity.this,
                        ComparingFractionsExercise2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        //GUI
        txtNum1 = findViewById(R.id.e1_txtNum1);
        txtNum2 = findViewById(R.id.e1_txtNum2);
        txtDenom1 = findViewById(R.id.e1_txtDenom1);
        txtDenom2 = findViewById(R.id.e1_txtDenom2);
        txtScore = findViewById(R.id.e1_txtScore);
        setTxtScore();
        txtInstruction = findViewById(R.id.e1_txtInstruction);
        btnSimilar = findViewById(R.id.e1_btnSimilar);
        btnDissimilar = findViewById(R.id.e1_btnDissimilar);
        btnSimilar.setOnClickListener(new BtnChoiceListener());
        btnDissimilar.setOnClickListener(new BtnChoiceListener());
        //VARIABLES
        fractionOne = new Fraction();
        fractionTwo = new Fraction();

        setAttributes((ExerciseStat) exercise);
        if (!Storage.isEmpty()) {
            checkUpdate();
        }
        startingTime = System.currentTimeMillis();

        go();
    }

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
                        if (response.optString("message") != null && response.optString("message").equals("Exercise not found.")){
                        } else {
                            Exercise updatedExercise = new ExerciseStat();
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
        setFractionList();
        txtInstruction.setText("Determine whether the pair is dissimilar or similar.");
        btnSimilar.setEnabled(true);
        btnDissimilar.setEnabled(true);
    }
    public void setFractionList(){
        if (Math.random() > 0.5) {
            fractionOne.generateRandFraction(9);
            fractionTwo.generateRandFraction(9);
            while (fractionOne.getDenominator() != fractionTwo.getDenominator() &&
                    fractionOne.getNumerator() != fractionTwo.getNumerator()){ //SIMILAR
                fractionOne.generateRandFraction(9);
            }
        } else {
            fractionOne.generateRandFraction(9);
            fractionTwo.generateRandFraction(9);
            while (fractionOne.getDenominator() == fractionTwo.getDenominator() ||
                    fractionOne.getNumerator() == fractionTwo.getNumerator()){ //DISSIMILAR
                fractionOne.generateRandFraction(9);
            }
        }
        setTxtFraction();
    }
    public void setTxtFraction() {
        txtNum1.setText(String.valueOf(fractionOne.getNumerator()));
        txtDenom1.setText(String.valueOf(fractionOne.getDenominator()));
        txtNum2.setText(String.valueOf(fractionTwo.getNumerator()));
        txtDenom2.setText(String.valueOf(fractionTwo.getDenominator()));
    }
    public void setTxtScore(){
        txtScore.setText(AppConstants.SCORE(correct,requiredCorrects));
    }
    public void correct(){
        correct++;
        if (errorsShouldBeConsecutive) {
            error = 0;
        }
        txtInstruction.setText(AppConstants.CORRECT);
        setTxtScore();
        btnSimilar.setEnabled(false);
        btnDissimilar.setEnabled(false);
        if (correct >= requiredCorrects){
            endingTime = System.currentTimeMillis();
            setFinalAttributes();
            txtInstruction.setText(AppConstants.FINISHED_EXERCISE);
            btnNext.setEnabled(true);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
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
        txtInstruction.setText(AppConstants.ERROR);
        setTxtScore();
        btnSimilar.setEnabled(false);
        btnDissimilar.setEnabled(false);
        if (error >= maxErrors){
            if (errorsShouldBeConsecutive) {
                txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(error));
            } else {
                txtInstruction.setText(AppConstants.FAILED(error));
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(ComparingFractionsExerciseActivity.this,
                            ComparingFractionsVideoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 3000);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    go();
                }
            }, 2000);
        }
    }
    public class BtnChoiceListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.e1_btnSimilar){
                if (fractionOne.getDenominator() == fractionTwo.getDenominator() ||
                        fractionOne.getNumerator() == fractionTwo.getNumerator()){
                    correct();
                } else {
                    wrong();
                }
            }
            if (v.getId() == R.id.e1_btnDissimilar){
                if (fractionOne.getDenominator() != fractionTwo.getDenominator() &&
                        fractionOne.getNumerator() != fractionTwo.getNumerator()){
                    correct();
                } else {
                    wrong();
                }
            }
        }
    }
    private void setFinalAttributes(){
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
}
