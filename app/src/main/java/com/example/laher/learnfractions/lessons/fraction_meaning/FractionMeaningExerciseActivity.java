package com.example.laher.learnfractions.lessons.fraction_meaning;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
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
import java.util.Collections;

public class FractionMeaningExerciseActivity extends AppCompatActivity {
    private static final String TAG = "FM_E1";
    Context mContext = this;
    Exercise exercise;
    ExerciseStat mExerciseStat;
    //GUI
    ImageView imgBox1, imgBox2, imgBox3, imgBox4, imgBox5, imgBox6, imgBox7, imgBox8, imgBox9;
    Button btnChoice1, btnChoice2, btnChoice3, btnChoice4;
    TextView txtScore, txtInstruction;
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Fraction Meaning";

    //VARIBALES
    ArrayList<String> instructions;
    String strCorrectAns;
    int num, denom, correct, error;
    public final String INSTRUCTION_DENOM = "Click how many parts the whole is divided into.";
    public final String INSTRUCTION_NUM = "Click how many parts we have.";
    int requiredCorrects;
    int maxErrors;
    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;
    long startingTime, endingTime;

    private final int EXERCISE_NUM = 1;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_meaning_exercise);
        imgBox1 = findViewById(R.id.a_imgBox1);
        imgBox2 = findViewById(R.id.a_imgBox2);
        imgBox3 = findViewById(R.id.a_imgBox3);
        imgBox4 = findViewById(R.id.a_imgBox4);
        imgBox5 = findViewById(R.id.a_imgBox5);
        imgBox6 = findViewById(R.id.a_imgBox6);
        imgBox7 = findViewById(R.id.a_imgBox7);
        imgBox8 = findViewById(R.id.a_imgBox8);
        imgBox9 = findViewById(R.id.a_imgBox9);
        btnChoice1 = findViewById(R.id.btnChoice1);
        btnChoice2 = findViewById(R.id.btnChoice2);
        btnChoice3 = findViewById(R.id.btnChoice3);
        btnChoice4 = findViewById(R.id.btnChoice4);
        btnChoice1.setOnClickListener(new BtnChoiceListener());
        btnChoice2.setOnClickListener(new BtnChoiceListener());
        btnChoice3.setOnClickListener(new BtnChoiceListener());
        btnChoice4.setOnClickListener(new BtnChoiceListener());
        txtInstruction = findViewById(R.id.txtInstruction);
        txtScore = findViewById(R.id.txtScore);
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        btnBack.setOnClickListener(new BtnBackListener());
        btnNext.setOnClickListener(new BtnNextListener());
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);

        instructions = new ArrayList<String>();
        instructions.add(INSTRUCTION_DENOM);
        instructions.add(INSTRUCTION_NUM);
        correct = 0;
        error = 0;

        exercise = LessonArchive.getLesson(AppConstants.FRACTION_MEANING).getExercises().get(EXERCISE_NUM - 1);
        mExerciseStat = (ExerciseStat) exercise;
        setAttributes(exercise);
        checkUpdate();

        startingTime = System.currentTimeMillis();
        go();
    }

    public void setAttributes(Exercise exerciseAtt){
        Log.d(TAG, "set attributes");
        requiredCorrects = exerciseAtt.getRequiredCorrects();
        maxErrors = exerciseAtt.getMaxErrors();
        correctsShouldBeConsecutive = exerciseAtt.isRc_consecutive();
        errorsShouldBeConsecutive = exerciseAtt.isMe_consecutive();
        showScore();
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
                            Log.d(TAG, "*service post execute");
                            Exercise updatedExercise = new Exercise();
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
        btnNext.setEnabled(false);

        generateFraction();
        setBoxes(num, denom);
        setTxtInstruction();
        setButtonChoices(Integer.valueOf(strCorrectAns));
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
    public void setButtonChoices(int correctAnswer){
        strCorrectAns = String.valueOf(correctAnswer);
        ArrayList<Integer> choiceNums = new ArrayList<>();
        //FOUR CHOICES
        choiceNums.add(correctAnswer);
        int randomNum;
        for(int i = 0; i < 3; i++){
            randomNum = (int) (Math.random() * 9 + 1);
            while (choiceNums.contains(randomNum)){
                randomNum = (int) (Math.random() * 9 + 1);
            }
            choiceNums.add(randomNum);
        }
        Collections.shuffle(choiceNums);
        btnChoice1.setText(String.valueOf(choiceNums.get(0)));
        btnChoice2.setText(String.valueOf(choiceNums.get(1)));
        btnChoice3.setText(String.valueOf(choiceNums.get(2)));
        btnChoice4.setText(String.valueOf(choiceNums.get(3)));
    }
    public void setTxtInstruction(){
        Collections.shuffle(instructions);
        txtInstruction.setText(instructions.get(0));
        if(instructions.get(0).equals(INSTRUCTION_DENOM)){
            strCorrectAns = String.valueOf(denom);
        } else if (instructions.get(0).equals(INSTRUCTION_NUM)){
            strCorrectAns = String.valueOf(num);
        }
    }
    public void generateFraction(){
        num = (int) (Math.random() * 9 + 1);
        denom = (int) (Math.random() * 9 + 1);
        while (denom<num) {
            denom = (int) (Math.random() * 9 + 1);
        }
    }
    public void finishExercise(){
        btnChoice1.setEnabled(false);
        btnChoice2.setEnabled(false);
        btnChoice3.setEnabled(false);
        btnChoice4.setEnabled(false);
        btnNext.setEnabled(true);
        setFinalAttributes();
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
        mExerciseStat.getTopicName() + "; exercise_num: " + mExerciseStat.getExerciseNum() + "; done:" + mExerciseStat.isDone() +
        "; time_spent: " + mExerciseStat.getTime_spent() + "; errors: " + mExerciseStat.getErrors() + "; required_corrects: " +
        mExerciseStat.getRequiredCorrects() + "; rc_consecutive: " + mExerciseStat.isRc_consecutive() + "; max_errors" +
        mExerciseStat.getMaxErrors() + "; me_consecutive: " + mExerciseStat.isMe_consecutive());
        ExerciseStatService.postStats(student,mExerciseStat,service);
    }

    public void showScore(){
        Log.d(TAG, "c:"+correct);
        Log.d(TAG, "rc:"+requiredCorrects);
        txtScore.setText(AppConstants.SCORE(correct,requiredCorrects));
    }
    public class BtnChoiceListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Button choice = (Button) view;
            if (choice.getText() == strCorrectAns) {
                //FOR TESTS
                //txtInstruction.setText("correct " + choice.getText() + " / " + strCorrectAns + " " + correct);
                if (errorsShouldBeConsecutive) {
                    error = 0;
                }
                correct++;
                showScore();
                txtInstruction.setText(AppConstants.CORRECT);
                btnChoice1.setEnabled(false);
                btnChoice2.setEnabled(false);
                btnChoice3.setEnabled(false);
                btnChoice4.setEnabled(false);
                if (correct >= requiredCorrects) {
                    endingTime = System.currentTimeMillis();
                    txtInstruction.setText(AppConstants.FINISHED_EXERCISE);
                    finishExercise();
                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btnChoice1.setEnabled(true);
                            btnChoice2.setEnabled(true);
                            btnChoice3.setEnabled(true);
                            btnChoice4.setEnabled(true);

                            instructions.remove(0);
                            if (instructions.size() == 0) {
                                instructions.add(INSTRUCTION_DENOM);
                                instructions.add(INSTRUCTION_NUM);
                                go();
                            } else {
                                setTxtInstruction();
                                setButtonChoices(Integer.parseInt(strCorrectAns));
                            }
                        }
                    }, 2000);

                }
            } else {
                if (correctsShouldBeConsecutive) {
                    correct = 0;
                }
                error++;
                mExerciseStat.incrementError();
                txtInstruction.setText(AppConstants.ERROR);
                showScore();
                btnChoice1.setEnabled(false);
                btnChoice2.setEnabled(false);
                btnChoice3.setEnabled(false);
                btnChoice4.setEnabled(false);
                if (error >= maxErrors) {
                    if (errorsShouldBeConsecutive) {
                        txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(error));
                    } else {
                        txtInstruction.setText(AppConstants.FAILED(error));
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(FractionMeaningExerciseActivity.this,
                                    FractionMeaningVideoActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }, 3000);
                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btnChoice1.setEnabled(true);
                            btnChoice2.setEnabled(true);
                            btnChoice3.setEnabled(true);
                            btnChoice4.setEnabled(true);

                            instructions.clear();
                            instructions.add(INSTRUCTION_DENOM);
                            instructions.add(INSTRUCTION_NUM);
                            go();
                        }
                    }, 2000);
                }
                //FOR TESTS
                //txtInstruction.setText("error " + choice.getText() + " / " + strCorrectAns);
            }
        }
    }

    public class BtnBackListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(FractionMeaningExerciseActivity.this,
                    FractionMeaningVideoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public class BtnNextListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(FractionMeaningExerciseActivity.this,
                    FractionMeaningExercise2Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
