package com.example.laher.learnfractions.lessons.classifying_fractions;

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
import com.example.laher.learnfractions.fraction_util.FractionClass;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;
import com.example.laher.learnfractions.fraction_util.MixedFraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.ClassifyingFractionQuestion;
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

import java.util.ArrayList;
import java.util.Collections;

public class ClassifyingFractionsExerciseActivity extends AppCompatActivity {
    Context mContext = this;
    private static final String TAG = "CF_E1";

    Exercise exercise;
    ExerciseStat mExerciseStat;
    final int EXERCISE_NUM = 1;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Classifying Fraction";
    //GUI
    TextView txtNum, txtDenom, txtWholeNum, txtScore, txtInstruction;
    Button btnProper, btnImproper, btnMixed;
    //VARIABLES
    ArrayList<ClassifyingFractionQuestion> mClassifyingFractionQuestions;
    ClassifyingFractionQuestion mClassifyingFractionQuestion;
    int mQuestionNum;

    int correct, error;
    int requiredCorrects;
    int maxErrors;
    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;

    long startingTime, endingTime;

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifying_fractions_exercise);
        exercise = LessonArchive.getLesson(AppConstants.CLASSIFYING_FRACTIONS).getExercises().get(EXERCISE_NUM-1);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassifyingFractionsExerciseActivity.this,
                        ClassifyingFractionsVideoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = findViewById(R.id.btnNext);
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
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        btnNext.setText(AppConstants.DONE);
        //GUI
        txtNum = findViewById(R.id.clF_txtNum);
        txtDenom = findViewById(R.id.clF_txtDenom);
        txtWholeNum = findViewById(R.id.clF_txtWholeNum);
        txtScore = findViewById(R.id.clF_txtScore);
        txtInstruction = findViewById(R.id.clF_txtInstruction);
        btnProper = findViewById(R.id.clF_btnProper);
        btnImproper = findViewById(R.id.clF_btnImproper);
        btnMixed = findViewById(R.id.clF_btnMixed);
        btnProper.setOnClickListener(new BtnAnswerListener());
        btnImproper.setOnClickListener(new BtnAnswerListener());
        btnMixed.setOnClickListener(new BtnAnswerListener());

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
            endingTime = System.currentTimeMillis();
            if (!Storage.isEmpty()) {
                setFinalAttributes();
            }
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
        mQuestionNum++;
        setGuiFraction();
    }
    public void wrong(){
        error++;
        mExerciseStat.incrementError();
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
                        mClassifyingFractionQuestion = mClassifyingFractionQuestions.get(mQuestionNum-1);
                        ClassifyingFractionQuestion classifyingFractionQuestion = new ClassifyingFractionQuestion();
                        Fraction fraction = mClassifyingFractionQuestion.getFraction();
                        String modifier = fraction.getModifier();
                        if (modifier.equals(Fraction.MIXED)){
                            classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.MIXED);
                            while (mClassifyingFractionQuestions.contains(classifyingFractionQuestion)){
                                classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.MIXED);
                            }
                            mClassifyingFractionQuestions.add(classifyingFractionQuestion);
                        } else {
                            if (modifier.equals(Fraction.PROPER)){
                                classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.PROPER);
                            } else if (modifier.equals(Fraction.IMPROPER)){
                                classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.IMPROPER);
                            }
                            mClassifyingFractionQuestions.add(classifyingFractionQuestion);
                        }
                        nextQuestion();
                    }
                }
            },2000);
        }
    }
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
    public void startUp(){
        setTxtScore();
    }
    public void enableButtons(Boolean bool){
        btnProper.setEnabled(bool);
        btnImproper.setEnabled(bool);
        btnMixed.setEnabled(bool);
    }
    public void setFractionQuestions(){
        mQuestionNum = 1;
        mClassifyingFractionQuestions = new ArrayList<>();
        for (int i = 0; i < requiredCorrects; i++){
            ClassifyingFractionQuestion classifyingFractionQuestion;
            if (i >= (requiredCorrects*2)/3){
                classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.PROPER);
            } else if (i >= (requiredCorrects/3)){
                classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.IMPROPER);
            } else {
                classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.MIXED);
            }
            while (mClassifyingFractionQuestions.contains(classifyingFractionQuestion)){
                if (i >= (requiredCorrects*2)/3){
                    classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.PROPER);
                } else if (i >= (requiredCorrects/3)){
                    classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.IMPROPER);
                } else {
                    classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.MIXED);
                }
            }
            mClassifyingFractionQuestions.add(classifyingFractionQuestion);
        }
        setGuiFraction();
    }
    public void setGuiFraction(){
        txtInstruction.setText("Classify the fraction.");
        mClassifyingFractionQuestion = mClassifyingFractionQuestions.get(mQuestionNum-1);
        Fraction fraction = mClassifyingFractionQuestion.getFraction();
        int numerator = fraction.getNumerator();
        int denominator = fraction.getDenominator();
        String modifier = fraction.getModifier();
        if (modifier.equals(Fraction.MIXED)){
            MixedFraction mixedFraction = (MixedFraction) fraction;
            int wholeNumber = mixedFraction.getWholeNumber();
            String strWholeNumber = String.valueOf(wholeNumber);
            txtWholeNum.setText(strWholeNumber);
        } else {
            txtWholeNum.setText("");
        }
        txtNum.setText(String.valueOf(numerator));
        txtDenom.setText(String.valueOf(denominator));
    }
    public void check(String modifier){
        mClassifyingFractionQuestion = mClassifyingFractionQuestions.get(mQuestionNum-1);
        Fraction fraction = mClassifyingFractionQuestion.getFraction();
        String correctModifier = fraction.getModifier();
        if (modifier.equals(correctModifier)){
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
