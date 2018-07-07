package com.example.laher.learnfractions.lessons.ordering_similar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestion;
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

public class OrderingSimilarExercise2Activity extends AppCompatActivity {
    Context mContext = this;

    Exercise exercise;
    final int EXERCISE_NUM = 2;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Ordering Fractions";
    //GUI
    TextView txtNum1, txtNum2, txtNum3, txtDenom1, txtDenom2, txtDenom3, txtScore, txtInstruction;
    ConstraintLayout clFraction1, clFraction2, clFraction3;
    //VARIABLES
    Fraction fraction1, fraction2, fraction3;
    FractionQuestion fractionQuestion;
    ArrayList<FractionQuestion> fractionQuestions;
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
        setContentView(R.layout.activity_ordering_similar_exercise2);
        exercise = LessonArchive.getLesson(AppConstants.ORDERING_SIMILAR).getExercises().get(EXERCISE_NUM-1);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderingSimilarExercise2Activity.this,
                        OrderingSimilarExerciseActivity.class);
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
                Intent intent = new Intent(OrderingSimilarExercise2Activity.this,
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
        txtNum1 = findViewById(R.id.os2_txtNum1);
        txtNum2 = findViewById(R.id.os2_txtNum2);
        txtNum3 = findViewById(R.id.os2_txtNum3);
        txtDenom1 = findViewById(R.id.os2_txtDenom1);
        txtDenom2 = findViewById(R.id.os2_txtDenom2);
        txtDenom3 = findViewById(R.id.os2_txtDenom3);
        txtScore = findViewById(R.id.os2_txtScore);
        setTxtScore();
        txtInstruction = findViewById(R.id.os2_txtInstruction);
        clFraction1 = findViewById(R.id.os2_clFraction1);
        clFraction2 = findViewById(R.id.os2_clFraction2);
        clFraction3 = findViewById(R.id.os2_clFraction3);

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
    }
    public void setFractionQuestions(){
        fractionQuestions = new ArrayList<>();
        questionNum = 0;
        for (int i = 0; i < requiredCorrects; i++){
            fractionQuestion = new FractionQuestion(FractionQuestion.ORDERING_SIMILAR);
            fractionQuestions.add(fractionQuestion);
        }
        setGuiFractions();
    }
    public void setFractions(){
        fraction1 = fractionQuestions.get(questionNum).getFractionOne();
        fraction2 = fractionQuestions.get(questionNum).getFractionTwo();
        fraction3 = fractionQuestions.get(questionNum).getFractionThree();
    }
    public void setGuiFractions(){
        clicks = 0;
        setFractions();
        txtInstruction.setText("Click from least to greatest.");
        txtNum1.setText(String.valueOf(fraction1.getNumerator()));
        txtNum2.setText(String.valueOf(fraction2.getNumerator()));
        txtNum3.setText(String.valueOf(fraction3.getNumerator()));
        txtDenom1.setText(String.valueOf(fraction1.getDenominator()));
        txtDenom2.setText(String.valueOf(fraction2.getDenominator()));
        txtDenom3.setText(String.valueOf(fraction3.getDenominator()));
        setClFractionsListener();
        resetTxtNumsColor();
    }
    public void resetTxtNumsColor(){
        txtNum1.setTextColor(Color.rgb(128,128,128));
        txtNum2.setTextColor(Color.rgb(128,128,128));
        txtNum3.setTextColor(Color.rgb(128,128,128));
        txtDenom1.setTextColor(Color.rgb(128,128,128));
        txtDenom2.setTextColor(Color.rgb(128,128,128));
        txtDenom3.setTextColor(Color.rgb(128,128,128));
    }
    public void removeClFractionsListener(){
        clFraction1.setOnClickListener(null);
        clFraction2.setOnClickListener(null);
        clFraction3.setOnClickListener(null);
    }
    public void setClFractionsListener(){
        clFraction1.setOnClickListener(new ClFractionListener());
        clFraction2.setOnClickListener(new ClFractionListener());
        clFraction3.setOnClickListener(new ClFractionListener());
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
        removeClFractionsListener();
        if (correct >= requiredCorrects){
            btnNext.setEnabled(true);
            txtInstruction.setText(AppConstants.FINISHED_LESSON);
        } else {
            txtInstruction.setText(AppConstants.CORRECT);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    questionNum++;
                    setGuiFractions();
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
        removeClFractionsListener();
        if (error >= maxErrors){
            if (errorsShouldBeConsecutive) {
                txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(error));
            } else {
                txtInstruction.setText(AppConstants.FAILED(error));
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(OrderingSimilarExercise2Activity.this,
                            OrderingSimilarExerciseActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 3000);
        } else {
            txtInstruction.setText(AppConstants.ERROR);
            clicks = 0;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (correctsShouldBeConsecutive) {
                        go();
                    } else {
                        fractionQuestion = new FractionQuestion(FractionQuestion.ORDERING_SIMILAR);
                        fractionQuestions.add(fractionQuestion);
                        questionNum++;
                        setGuiFractions();
                    }
                }
            }, 2000);
        }
    }
    public class ClFractionListener implements ConstraintLayout.OnClickListener{
        TextView num, denom;
        @Override
        public void onClick(View v) {
            if (v.getId()==clFraction1.getId()){
                num = txtNum1;
                denom = txtDenom1;
                check(fraction1);
            }
            if (v.getId()==clFraction2.getId()){
                num = txtNum2;
                denom = txtDenom2;
                check(fraction2);
            }
            if (v.getId()==clFraction3.getId()){
                num = txtNum3;
                denom = txtDenom3;
                check(fraction3);
            }
            if (clicks>=3){
                correct();
            }
        }
        public void check(Fraction fraction){
            if (fraction.equals(fractionQuestions.get(questionNum).getFractions().get(clicks))){
                setTextColor(0,255,0);
                clicks++;
            } else {
                setTextColor(255,0,0);
                wrong();
            }
        }
        public void setTextColor(int r, int g, int b){
            num.setTextColor(Color.rgb(r, g, b));
            denom.setTextColor(Color.rgb(r, g, b));
        }
    }
}
