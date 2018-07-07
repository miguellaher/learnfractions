package com.example.laher.learnfractions.lessons.adding_similar;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.archive.LessonArchive;
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

public class AddingSimilarExerciseActivity extends AppCompatActivity {
    Context mContext = this;

    Exercise exercise;
    final int EXERCISE_NUM = 1;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Adding Fractions";
    //FRACTION EQUATION GUI
    TextView txtNum1, txtNum2, txtDenom1, txtDenom2, txtSign, txtScore, txtInstruction;
    EditText inputNum, inputDenom;
    Button btnCheck;
    ConstraintLayout clChoices;
    //VARIABLES
    FractionQuestion fractionQuestion;
    ArrayList<FractionQuestion> fractionQuestions;
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
        setContentView(R.layout.activity_fraction_equation);
        exercise = LessonArchive.getLesson(AppConstants.ADDING_SIMILAR).getExercises().get(EXERCISE_NUM-1);

        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddingSimilarExerciseActivity.this,
                        AddingSimilarVideoActivity.class);
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
                Intent intent = new Intent(AddingSimilarExerciseActivity.this,
                        TopicsMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        btnNext.setText(AppConstants.DONE);
        //FRACTION EQUATION GUI
        txtNum1 = (TextView) findViewById(R.id.fe_txtNum1);
        txtNum2 = (TextView) findViewById(R.id.fe_txtNum2);
        txtDenom1 = (TextView) findViewById(R.id.fe_txtDenom1);
        txtDenom2 = (TextView) findViewById(R.id.fe_txtDenom2);
        txtSign = (TextView) findViewById(R.id.fe_txtSign);
        txtScore = (TextView) findViewById(R.id.fe_txtScore);
        txtInstruction = (TextView) findViewById(R.id.fe_txtInstruction);
        inputNum = (EditText) findViewById(R.id.fe_inputNum);
        inputDenom = (EditText) findViewById(R.id.fe_inputDenom);
        btnCheck = (Button) findViewById(R.id.fe_btnCheck);
        btnCheck.setOnClickListener(new BtnCheckListener());
        clChoices = findViewById(R.id.fe_clChoices);
        clChoices.setVisibility(View.INVISIBLE);

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
        setQuestions();
        setGuiFractions();
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
        disableInputFraction();
        btnCheck.setEnabled(false);
        if (correct >= requiredCorrects){
            txtInstruction.setText(AppConstants.FINISHED_LESSON);
            btnNext.setEnabled(true);
        } else {
            txtInstruction.setText(AppConstants.CORRECT);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextQuestion();
                }
            }, 2000);
        }
    }
    public void nextQuestion(){
        questionNum++;
        setGuiFractions();
        clearInputFraction();
        enableInputFraction();
        btnCheck.setEnabled(true);
    }
    public void wrong(){
        error++;
        if (correctsShouldBeConsecutive) {
            correct = 0;
        }
        setTxtScore();
        disableInputFraction();
        btnCheck.setEnabled(false);
        if (error >= maxErrors){
            if (errorsShouldBeConsecutive) {
                txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(error));
            } else {
                txtInstruction.setText(AppConstants.FAILED(error));
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(AddingSimilarExerciseActivity.this,
                            AddingSimilarVideoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 4000);
        } else {
            txtInstruction.setText(AppConstants.ERROR);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (correctsShouldBeConsecutive) {
                        go();
                        enableInputFraction();
                        btnCheck.setEnabled(true);
                    } else {
                        addQuestion();
                        nextQuestion();
                    }
                }
            }, 2000);
        }
    }
    public void startUp(){
        setTxtScore();
        txtInstruction.setText("Add the two numerators for the new numerator and copy the similar denominator for the new denominator.");
        clearInputFraction();
    }
    public void clearInputFraction(){
        inputNum.setText("");
        inputDenom.setText("");
        inputNum.requestFocus();
    }
    public void setQuestions(){
        questionNum = 0;
        fractionQuestions = new ArrayList<>();
        for(int i = 0; i < requiredCorrects; i++){
            addQuestion();
        }
    }
    public void addQuestion(){
        fractionQuestion = new FractionQuestion(FractionQuestion.ADDING_SIMILAR);
        fractionQuestions.add(fractionQuestion);
    }
    public void setGuiFractions(){
        txtInstruction.setText("Solve the equation.");
        txtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getNumerator()));
        txtNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getNumerator()));
        txtDenom1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
        txtDenom2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
    }
    public void shakeAnimate(View view){
        ObjectAnimator.ofFloat(view, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                .setDuration(1000)
                .start();
    }
    public void shakeInputFraction(){
        shakeAnimate(inputNum);
        shakeAnimate(inputDenom);
    }
    public void disableInputFraction(){
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
    }
    public void enableInputFraction(){
        inputNum.setEnabled(true);
        inputDenom.setEnabled(true);
    }
    public class BtnCheckListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {

            if (inputNum.getText().toString().trim().length() != 0 &&
                    inputDenom.getText().toString().trim().length() != 0 ) {
                if (Integer.valueOf(String.valueOf(inputNum.getText()))
                        == fractionQuestions.get(questionNum).getFractionThree().getNumerator()
                        && Integer.valueOf(String.valueOf(inputDenom.getText()))
                                == fractionQuestions.get(questionNum).getFractionThree().getDenominator()) {
                    correct();
                } else {
                    shakeInputFraction();
                    wrong();
                }
            } else {
                shakeInputFraction();
            }
        }
    }
}
