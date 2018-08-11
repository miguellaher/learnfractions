package com.example.laher.learnfractions.lessons.comparing_similar_fractions;

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
import com.example.laher.learnfractions.fraction_util.FractionQuestion;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;
import com.example.laher.learnfractions.fraction_util.fraction_questions.ComparingSimilarQuestion;
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

public class ComparingSimilarExercise2Activity extends AppCompatActivity {
    private static final String TAG = "CS_E2";
    Context mContext = this;

    Exercise exercise;
    ExerciseStat mExerciseStat;
    final int EXERCISE_NUM = 2;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Comparing Similar";
    //GUI
    TextView txtNum1, txtNum2, txtDenom1, txtDenom2, txtCompareSign, txtScore, txtInstruction;
    Button btnGreater, btnEquals, btnLess;
    //VARIABLES
    int correct, error;
    final Handler handler = new Handler();
    Fraction fractionOne, fractionTwo;

    ComparingSimilarQuestion mComparingSimilarQuestion;
    ArrayList<ComparingSimilarQuestion> mComparingSimilarQuestions;
    int mQuestionNum;

    String strAnswer;

    int requiredCorrects;
    int maxErrors;

    long startingTime, endingTime;

    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparing_similar_exercise2);
        exercise = LessonArchive.getLesson(AppConstants.COMPARING_SIMILAR_FRACTIONS).getExercises().get(EXERCISE_NUM-1);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComparingSimilarExercise2Activity.this,
                        ComparingSimilarExerciseActivity.class);
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
                Intent intent = new Intent(ComparingSimilarExercise2Activity.this, TopicsMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        btnNext.setText(AppConstants.DONE);
        //GUI
        txtNum1 = findViewById(R.id.c2_num1);
        txtNum2 = findViewById(R.id.c2_num2);
        txtDenom1 = findViewById(R.id.c2_denom1);
        txtDenom2 = findViewById(R.id.c2_denom2);
        txtCompareSign = findViewById(R.id.c2_compareSign);
        txtScore = findViewById(R.id.c2_txtScore);
        setTxtScore();
        txtInstruction = findViewById(R.id.c2_txtInstruction);

        btnGreater = findViewById(R.id.c2_btnGreater);
        btnEquals = findViewById(R.id.c2_btnEqual);
        btnLess = findViewById(R.id.c2_btnLess);
        btnGreater.setText(FractionQuestion.ANSWER_GREATER);
        btnEquals.setText(FractionQuestion.ANSWER_EQUAL);
        btnLess.setText(FractionQuestion.ANSWER_LESS);
        btnGreater.setOnClickListener(new BtnListener());
        btnEquals.setOnClickListener(new BtnListener());
        btnLess.setOnClickListener(new BtnListener());
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
                    } catch (Exception e){e.printStackTrace();}
                }
            });
            Student student = new Student();
            student.setTeacher_code(Storage.load(mContext, Storage.TEACHER_CODE));
            ExerciseService.getUpdate(exercise, student, service);
        }
    }
    public void go(){
        generateFractionQuestion();
    }
    private void generateFractionQuestion(){
        mQuestionNum = 1;
        mComparingSimilarQuestions = new ArrayList<>();
        for (int i = 0; i < requiredCorrects; i++){
            ComparingSimilarQuestion comparingSimilarQuestion = new ComparingSimilarQuestion();
            while (mComparingSimilarQuestions.contains(comparingSimilarQuestion)){
                comparingSimilarQuestion = new ComparingSimilarQuestion();
            }
            mComparingSimilarQuestions.add(comparingSimilarQuestion);
        }
        setTxtFractions();
    }
    public void setTxtFractions(){
        mComparingSimilarQuestion = mComparingSimilarQuestions.get(mQuestionNum-1);
        int numerator1 = mComparingSimilarQuestion.getNumerator1();
        int numerator2 = mComparingSimilarQuestion.getNumerator2();
        int denominator = mComparingSimilarQuestion.getDenominator();
        txtNum1.setText(String.valueOf(numerator1));
        txtNum2.setText(String.valueOf(numerator2));
        txtDenom1.setText(String.valueOf(denominator));
        txtDenom2.setText(String.valueOf(denominator));
        strAnswer = String.valueOf(mComparingSimilarQuestion.getNumeratorAnswer());
        txtInstruction.setText("Compare the two fractions.");
        txtCompareSign.setText("_");
    }
    public void enableButtons(boolean bool){
        btnGreater.setEnabled(bool);
        btnEquals.setEnabled(bool);
        btnLess.setEnabled(bool);
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
        txtInstruction.setText(AppConstants.CORRECT);
        if (correct >= requiredCorrects){
            endingTime = System.currentTimeMillis();
            if (!Storage.isEmpty()) {
                setFinalAttributes();
            }
            btnNext.setEnabled(true);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mQuestionNum++;
                    setTxtFractions();
                    enableButtons(true);
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
        enableButtons(false);
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
                    Intent intent = new Intent(ComparingSimilarExercise2Activity.this,
                            ComparingSimilarExerciseActivity.class);
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
                        ComparingSimilarQuestion comparingSimilarQuestion = new ComparingSimilarQuestion();
                        while (mComparingSimilarQuestions.contains(comparingSimilarQuestion)){
                            comparingSimilarQuestion = new ComparingSimilarQuestion();
                        }
                        mComparingSimilarQuestions.add(comparingSimilarQuestion);
                        mQuestionNum++;
                        setTxtFractions();
                    }
                    enableButtons(true);
                }
            }, 2000);
        }
    }
    public class BtnListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            String s = b.getText().toString();
            txtCompareSign.setText(s);
            if (s.equals(strAnswer)){
                correct();
            } else {
                wrong();
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
