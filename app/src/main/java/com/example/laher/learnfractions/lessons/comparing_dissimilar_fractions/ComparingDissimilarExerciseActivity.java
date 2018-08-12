package com.example.laher.learnfractions.lessons.comparing_dissimilar_fractions;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.ComparingDissimilarQuestion;
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

public class ComparingDissimilarExerciseActivity extends AppCompatActivity {
    Context mContext = this;
    private static final String TAG = "CD_E1";

    Exercise exercise;
    ExerciseStat mExerciseStat;
    final int EXERCISE_NUM = 1;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Comparing Dissimilar";
    //MULTIPLICATION DIALOG
    Dialog multiplicationDialog;
    View mdView;
    TextView diagTxtMultiplicand, diagTxtMultiplier;
    EditText diagInputProduct;
    Button dialogBtnCheck;
    //GUI
    TextView txtScore, txtProduct1, txtProduct2, txtNum1, txtNum2, txtDenom1, txtDenom2, txtInstruction;
    //VARIABLES
    ArrayList<Integer> stepsIdList;

    ArrayList<ComparingDissimilarQuestion> mComparingDissimilarQuestions;
    ComparingDissimilarQuestion mComparingDissimilarQuestion;
    int mQuestionNum;

    int correct;
    final Handler handler = new Handler();

    int requiredCorrects;
    long startingTime, endingTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparing_dissimilar_exercise);
        exercise = LessonArchive.getLesson(AppConstants.COMPARING_DISSIMILAR_FRACTIONS).getExercises().get(EXERCISE_NUM-1);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComparingDissimilarExerciseActivity.this,
                        ComparingDissimilarVideoActivity.class);
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
                Intent intent = new Intent(ComparingDissimilarExerciseActivity.this,
                        ComparingDissimilarExercise2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        //MULTIPLICATION DIALOG
        mdView = getLayoutInflater().inflate(R.layout.layout_dialog_equation, null);
        multiplicationDialog = new Dialog(ComparingDissimilarExerciseActivity.this);
        multiplicationDialog.setOnDismissListener(new DiagMultiplicationListener());
        multiplicationDialog.setOnShowListener(new DiagMultiplicationListener());
        multiplicationDialog.setTitle("Multiplication Equation");
        multiplicationDialog.setContentView(mdView);
        diagTxtMultiplicand = mdView.findViewById(R.id.md_txtMultiplicand);
        diagTxtMultiplier = mdView.findViewById(R.id.md_txtMultiplier);
        diagInputProduct = mdView.findViewById(R.id.md_inputProduct);
        diagInputProduct.setOnEditorActionListener(new InputListener());
        dialogBtnCheck = mdView.findViewById(R.id.md_btnCheck);
        dialogBtnCheck.setOnClickListener(new DiagBtnCheckListener());
        //GUI
        txtScore = findViewById(R.id.d1_txtScore);
        setTxtScore();
        txtProduct1 = findViewById(R.id.d1_txtProduct1);
        txtProduct2 = findViewById(R.id.d1_txtProduct2);
        txtNum1 = findViewById(R.id.d1_txtNum1);
        txtNum2 = findViewById(R.id.d1_txtNum2);
        txtDenom1 = findViewById(R.id.d1_txtDenom1);
        txtDenom2 = findViewById(R.id.d1_txtDenom2);
        resetTxtFractionsColor();
        txtInstruction = findViewById(R.id.d1_txtInstruction);
        stepsIdList = new ArrayList<>();



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
                        Util.toast(mContext,"Exercise updated.");
                        Exercise updatedExercise = new ExerciseStat();
                        updatedExercise.setRequiredCorrects(Integer.valueOf(response.optString("required_corrects")));
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
        setup();
        setFractionQuestions();
    }
    public void setup(){
        txtInstruction.setText("Click a denominator.");
    }
    public void setFractionQuestions(){
        mQuestionNum = 1;
        mComparingDissimilarQuestions = new ArrayList<>();
        for (int i = 0; i < requiredCorrects; i++){
            ComparingDissimilarQuestion comparingDissimilarQuestion = new ComparingDissimilarQuestion();
            while (mComparingDissimilarQuestions.contains(comparingDissimilarQuestion)){
                comparingDissimilarQuestion = new ComparingDissimilarQuestion();
            }
            mComparingDissimilarQuestions.add(comparingDissimilarQuestion);
        }
        setGuiFractions();
    }
    public void setGuiFractions(){
        mComparingDissimilarQuestion = mComparingDissimilarQuestions.get(mQuestionNum-1);
        Fraction fraction1 = mComparingDissimilarQuestion.getFraction1();
        Fraction fraction2 = mComparingDissimilarQuestion.getFraction2();
        int numerator1 = fraction1.getNumerator();
        int numerator2 = fraction2.getNumerator();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        txtNum1.setText(String.valueOf(numerator1));
        txtDenom1.setText(String.valueOf(denominator1));
        txtNum2.setText(String.valueOf(numerator2));
        txtDenom2.setText(String.valueOf(denominator2));
        txtNum1.setOnClickListener(new TxtFractionListener());
        txtNum2.setOnClickListener(new TxtFractionListener());
        txtDenom1.setOnClickListener(new TxtFractionListener());
        txtDenom2.setOnClickListener(new TxtFractionListener());
        txtProduct1.setVisibility(TextView.INVISIBLE);
        txtProduct2.setVisibility(TextView.INVISIBLE);
    }
    public void shakeAnimate(TextView textview){
        ObjectAnimator.ofFloat(textview, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                .setDuration(1000)
                .start();
    }
    public void diagInputProduct(int num, int denom){
        txtInstruction.setText("Get the product of the clicked numbers.");

        diagTxtMultiplicand.setText(String.valueOf(denom));
        diagTxtMultiplier.setText(String.valueOf(num));
        multiplicationDialog.show();
    }
    public void resetTxtFractionsColor(){
        txtNum1.setTextColor(Color.rgb(128,128,128));
        txtNum2.setTextColor(Color.rgb(128,128,128));
        txtDenom1.setTextColor(Color.rgb(128,128,128));
        txtDenom2.setTextColor(Color.rgb(128,128,128));
    }
    public void removeTxtListeners(){
        txtNum1.setOnClickListener(null);
        txtNum2.setOnClickListener(null);
        txtDenom1.setOnClickListener(null);
        txtDenom2.setOnClickListener(null);
    }
    public void setTxtScore(){
        txtScore.setText(AppConstants.SCORE(correct,requiredCorrects));
    }
    public void correct(){
        stepsIdList.clear();
        removeTxtListeners();
        correct++;
        setTxtScore();
        txtInstruction.setText(AppConstants.CORRECT);

        if (correct >= requiredCorrects) {
            endingTime = System.currentTimeMillis();
            if (!Storage.isEmpty()) {
                setFinalAttributes();
            }
            btnNext.setEnabled(true);
            txtInstruction.setText(AppConstants.FINISHED_EXERCISE);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mQuestionNum++;
                    setGuiFractions();
                }
            }, 2000);
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
    public class TxtFractionListener implements TextView.OnClickListener {
        @Override
        public void onClick(View v) {
            stepsIdList.add(v.getId());
            Fraction fraction1 = mComparingDissimilarQuestion.getFraction1();
            Fraction fraction2 = mComparingDissimilarQuestion.getFraction2();
            int numerator1 = fraction1.getNumerator();
            int numerator2 = fraction2.getNumerator();
            int denominator1 = fraction1.getDenominator();
            int denominator2 = fraction2.getDenominator();
            if (stepsIdList.size() == 1){
                if (stepsIdList.get(0) == txtDenom1.getId()){
                    txtDenom1.setTextColor(Color.rgb(0,255,0));
                    txtInstruction.setText("Click the numerator of the second fraction.");
                } else if (stepsIdList.get(0) == txtDenom2.getId()){
                    txtDenom2.setTextColor(Color.rgb(0,255,0));
                    txtInstruction.setText("Click the numerator of the first fraction.");
                } else {
                    //wrong
                    shakeAnimate(txtDenom1);
                    shakeAnimate(txtDenom2);
                    stepsIdList.remove(0);
                }
            }
            if (stepsIdList.size() == 2){
                if (stepsIdList.get(0) == txtDenom2.getId()){
                    if (stepsIdList.get(1) == txtNum1.getId()) {
                        txtNum1.setTextColor(Color.rgb(0, 255, 0));
                        //diagInputProduct(Integer.valueOf((String) txtNum1.getText()),
                        //        Integer.valueOf((String) txtDenom2.getText()));
                        diagInputProduct(numerator1, denominator2);
                    } else {
                        shakeAnimate(txtNum1);
                        stepsIdList.remove(1);
                    }
                } else if (stepsIdList.get(0) == txtDenom1.getId()){
                    if (stepsIdList.get(1) == txtNum2.getId()) {
                        txtNum2.setTextColor(Color.rgb(0, 255, 0));
                        //diagInputProduct(Integer.valueOf((String) txtNum2.getText()),
                        //        Integer.valueOf((String) txtDenom1.getText()));
                        diagInputProduct(numerator2, denominator1);
                    } else {
                        shakeAnimate(txtNum2);
                        stepsIdList.remove(1);
                    }
                }
            }
            if (stepsIdList.size()==3){
                if (stepsIdList.get(0) == txtDenom1.getId()){
                    if (stepsIdList.get(1) == txtNum2.getId()){
                        if (stepsIdList.get(2) == txtDenom2.getId()){
                            txtDenom2.setTextColor(Color.rgb(0, 255, 0));
                            txtInstruction.setText("Click the numerator of the first fraction.");
                        } else {
                            shakeAnimate(txtDenom2);
                            stepsIdList.remove(2);
                        }
                    }
                } else if (stepsIdList.get(0) == txtDenom2.getId()){
                    if (stepsIdList.get(1) == txtNum1.getId()){
                        if (stepsIdList.get(2) == txtDenom1.getId()){
                            txtDenom1.setTextColor(Color.rgb(0, 255, 0));
                            txtInstruction.setText("Click the numerator of the second fraction.");
                        } else {
                            shakeAnimate(txtDenom1);
                            stepsIdList.remove(2);
                        }
                    }

                }
            }
            if (stepsIdList.size()==4){
                if (stepsIdList.get(0) == txtDenom1.getId()){
                    if (stepsIdList.get(1) == txtNum2.getId()){
                        if (stepsIdList.get(2) == txtDenom2.getId()){
                            if (stepsIdList.get(3) == txtNum1.getId()){
                                txtNum1.setTextColor(Color.rgb(0, 255, 0));
                                diagInputProduct(numerator1, denominator2);
                            } else {
                                shakeAnimate(txtNum1);
                                stepsIdList.remove(3);
                            }
                        }
                    }
                } else if (stepsIdList.get(0) == txtDenom2.getId()){
                    if (stepsIdList.get(1) == txtNum1.getId()){
                        if (stepsIdList.get(2) == txtDenom1.getId()){
                            if (stepsIdList.get(3) == txtNum2.getId()){
                                txtNum2.setTextColor(Color.rgb(0, 255, 0));
                                diagInputProduct(numerator2, denominator1);
                            } else {
                                shakeAnimate(txtNum2);
                                stepsIdList.remove(3);
                            }
                        }
                    }
                }
            }
        }
    }
    public class DiagBtnCheckListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            int product = Integer.valueOf((String) diagTxtMultiplicand.getText())
                    * Integer.valueOf((String) diagTxtMultiplier.getText());
            try {
                if (!diagInputProduct.getText().toString().matches("")) {
                    if (Integer.valueOf(String.valueOf(diagInputProduct.getText())) == product) {
                        if (stepsIdList.get(stepsIdList.size() - 1) == txtNum2.getId()) {
                            if (stepsIdList.get(stepsIdList.size() - 2) == txtDenom1.getId()) {
                                txtProduct2.setText(diagInputProduct.getText());
                                txtProduct2.setVisibility(TextView.VISIBLE);
                            }
                        } else if (stepsIdList.get(stepsIdList.size() - 1) == txtNum1.getId()) {
                            if (stepsIdList.get(stepsIdList.size() - 2) == txtDenom2.getId()) {
                                txtProduct1.setText(diagInputProduct.getText());
                                txtProduct1.setVisibility(TextView.VISIBLE);
                            }
                        }
                        multiplicationDialog.dismiss();
                        if (txtProduct1.getVisibility() != TextView.VISIBLE || txtProduct2.getVisibility() != TextView.VISIBLE) {
                            txtInstruction.setText("Click the other denominator.");
                        }
                        if (txtProduct1.getVisibility() == TextView.VISIBLE && txtProduct2.getVisibility() == TextView.VISIBLE) {
                            correct();
                        }
                    } else {
                        shakeAnimate(diagInputProduct);
                    }
                } else {
                    shakeAnimate(diagInputProduct);
                }
            } catch (Exception e){
                e.printStackTrace();
                shakeAnimate(diagInputProduct);
            }
        }
    }
    public class DiagMultiplicationListener implements Dialog.OnDismissListener, DialogInterface.OnShowListener{
        @Override
        public void onDismiss(DialogInterface dialog) {
            resetTxtFractionsColor();
            diagInputProduct.setText("");
            if (stepsIdList.size()==2){
                if (txtProduct1.getVisibility()==TextView.INVISIBLE && txtProduct2.getVisibility()==TextView.INVISIBLE){
                    stepsIdList.clear();
                    txtInstruction.setText("Click a denominator.");
                }
            }
            if (stepsIdList.size()==4){
                if (txtProduct1.getVisibility()==TextView.INVISIBLE || txtProduct2.getVisibility()==TextView.INVISIBLE){
                    stepsIdList.remove(stepsIdList.size()-1);
                    stepsIdList.remove(stepsIdList.size()-1);
                }
            }
        }

        @Override
        public void onShow(DialogInterface dialog) {
            diagInputProduct.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
        }
    }

    public class InputListener implements TextView.OnEditorActionListener{
        int product = Integer.valueOf((String) diagTxtMultiplicand.getText())
                * Integer.valueOf((String) diagTxtMultiplier.getText());
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){
                dialogBtnCheck.performClick();
                if (!diagInputProduct.getText().toString().matches("")) {
                    if (Integer.valueOf(String.valueOf(diagInputProduct.getText())) != product) {
                        return true;
                    }
                } else {
                    shakeAnimate(diagInputProduct);
                }
            }
            return false;
        }
    }
}
