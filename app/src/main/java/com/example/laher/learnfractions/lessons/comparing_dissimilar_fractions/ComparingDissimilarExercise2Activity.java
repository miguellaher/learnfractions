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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;
import com.example.laher.learnfractions.lessons.comparing_similar_fractions.ComparingSimilarExerciseActivity;
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

public class ComparingDissimilarExercise2Activity extends AppCompatActivity {
    Context mContext = this;

    Exercise exercise;
    final int EXERCISE_NUM = 2;
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
    TextView txtScore, txtProduct1, txtProduct2, txtNum1, txtNum2, txtDenom1, txtDenom2, txtCompareSign, txtInstruction;
    Button btnGreater, btnEquals, btnLess;
    //VARIABLES
    ArrayList<Integer> stepsIdList;
    Fraction fractionOne, fractionTwo;
    int correct, error;
    final Handler handler = new Handler();

    public final String GREATER_THAN = ">";
    public final String EQUAL_TO = "=";
    public final String LESS_THAN = "<";

    int requiredCorrects;
    int maxErrors;
    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparing_dissimilar_exercise2);
        exercise = LessonArchive.getLesson(AppConstants.COMPARING_DISSIMILAR_FRACTIONS).getExercises().get(EXERCISE_NUM-1);


        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComparingDissimilarExercise2Activity.this,
                        ComparingSimilarExerciseActivity.class);
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
                Intent intent = new Intent(ComparingDissimilarExercise2Activity.this, TopicsMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        btnNext.setText(AppConstants.DONE);
        //MULTIPLICATION DIALOG
        mdView = getLayoutInflater().inflate(R.layout.layout_dialog_equation, null);
        multiplicationDialog = new Dialog(ComparingDissimilarExercise2Activity.this);
        multiplicationDialog.setOnDismissListener(new DiagMultiplicationListener());
        multiplicationDialog.setTitle("Multiplication Equation");
        multiplicationDialog.setContentView(mdView);
        diagTxtMultiplicand = (TextView) mdView.findViewById(R.id.md_txtMultiplicand);
        diagTxtMultiplier = (TextView) mdView.findViewById(R.id.md_txtMultiplier);
        diagInputProduct = (EditText) mdView.findViewById(R.id.md_inputProduct);
        dialogBtnCheck = (Button) mdView.findViewById(R.id.md_btnCheck);
        dialogBtnCheck.setOnClickListener(new DiagBtnCheckListener());
        //GUI
        txtScore = (TextView) findViewById(R.id.d2_txtScore);
        setTxtScore();
        txtProduct1 = (TextView) findViewById(R.id.d2_txtProduct1);
        txtProduct2 = (TextView) findViewById(R.id.d2_txtProduct2);
        txtNum1 = (TextView) findViewById(R.id.d2_txtNum1);
        txtNum2 = (TextView) findViewById(R.id.d2_txtNum2);
        txtDenom1 = (TextView) findViewById(R.id.d2_txtDenom1);
        txtDenom2 = (TextView) findViewById(R.id.d2_txtDenom2);
        txtCompareSign = (TextView) findViewById(R.id.d2_txtCompareSign);
        resetTxtFractionsColor();
        txtInstruction = (TextView) findViewById(R.id.d2_txtInstruction);
        stepsIdList = new ArrayList<>();

        btnGreater = (Button) findViewById(R.id.d2_btnGreater);
        btnEquals = (Button) findViewById(R.id.d2_btnEquals);
        btnLess = (Button) findViewById(R.id.d2_btnLess);
        btnGreater.setOnClickListener(new BtnListener());
        btnEquals.setOnClickListener(new BtnListener());
        btnLess.setOnClickListener(new BtnListener());
        //VARIABLES
        fractionOne = new Fraction();
        fractionTwo = new Fraction();

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
        setup();
    }
    public void setup(){
        enableBtnCompareSign(false);
        setFractions();
        txtCompareSign.setText("_");
        txtInstruction.setText("Click a denominator.");
    }
    public void enableBtnCompareSign(boolean bool){
        btnGreater.setEnabled(bool);
        btnEquals.setEnabled(bool);
        btnLess.setEnabled(bool);
    }
    public void setFractions(){
        fractionOne.generateRandFraction(9);
        fractionTwo.generateRandFraction(9);
        while (fractionOne.getDenominator()==fractionTwo.getDenominator() ||
                fractionOne.getNumerator()==fractionTwo.getNumerator()){
            fractionTwo.generateRandFraction(9);
        }
        setGuiFractions();
    }
    public void setGuiFractions(){
        txtNum1.setText(String.valueOf(fractionOne.getNumerator()));
        txtDenom1.setText(String.valueOf(fractionOne.getDenominator()));
        txtNum2.setText(String.valueOf(fractionTwo.getNumerator()));
        txtDenom2.setText(String.valueOf(fractionTwo.getDenominator()));
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
        enableBtnCompareSign(false);
        correct++;
        if (errorsShouldBeConsecutive) {
            error = 0;
        }
        setTxtScore();
        txtInstruction.setText(AppConstants.CORRECT);

        if (correct >= requiredCorrects) {
            btnNext.setEnabled(true);
            txtInstruction.setText(AppConstants.FINISHED_LESSON);
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
        stepsIdList.clear();
        removeTxtListeners();
        enableBtnCompareSign(false);
        error++;
        if (correctsShouldBeConsecutive) {
            correct = 0;
        }
        setTxtScore();
        txtInstruction.setText(AppConstants.ERROR);

        if (error >= maxErrors) {
            if (errorsShouldBeConsecutive) {
                txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(error));
            } else {
                txtInstruction.setText(AppConstants.FAILED(error));
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(ComparingDissimilarExercise2Activity.this,
                            ComparingDissimilarExerciseActivity.class);
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
    public void check(String compareSign){
        if (compareSign == GREATER_THAN){
            if (Integer.valueOf(String.valueOf(txtProduct1.getText())) > Integer.valueOf(String.valueOf(txtProduct2.getText()))) {
                correct();
            } else {
                wrong();
            }
        }
        if (compareSign == EQUAL_TO){
            if (Integer.valueOf(String.valueOf(txtProduct1.getText())) == Integer.valueOf(String.valueOf(txtProduct2.getText()))) {
                correct();
            } else {
                wrong();
            }
        }
        if (compareSign == LESS_THAN){
            if (Integer.valueOf(String.valueOf(txtProduct1.getText())) < Integer.valueOf(String.valueOf(txtProduct2.getText()))) {
                correct();
            } else {
                wrong();
            }
        }
    }
    public class TxtFractionListener implements TextView.OnClickListener {
        @Override
        public void onClick(View v) {
            stepsIdList.add(v.getId());
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
                        diagInputProduct(fractionOne.getNumerator(),fractionTwo.getDenominator());
                    } else {
                        shakeAnimate(txtNum1);
                        stepsIdList.remove(1);
                    }
                } else if (stepsIdList.get(0) == txtDenom1.getId()){
                    if (stepsIdList.get(1) == txtNum2.getId()) {
                        txtNum2.setTextColor(Color.rgb(0, 255, 0));
                        //diagInputProduct(Integer.valueOf((String) txtNum2.getText()),
                        //        Integer.valueOf((String) txtDenom1.getText()));
                        diagInputProduct(fractionTwo.getNumerator(),fractionOne.getDenominator());
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
                                diagInputProduct(fractionOne.getNumerator(),fractionTwo.getDenominator());
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
                                diagInputProduct(fractionTwo.getNumerator(),fractionOne.getDenominator());
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
                } else {
                    shakeAnimate(diagInputProduct);
                }
                if (txtProduct1.getVisibility() == TextView.VISIBLE
                        && txtProduct2.getVisibility() == TextView.VISIBLE) {
                    enableBtnCompareSign(true);
                    txtInstruction.setText("Compare the two products.");
                } else {
                    txtInstruction.setText("Click the other denominator.");
                }
            } else {
                shakeAnimate(diagInputProduct);
            }
        }
    }
    public class BtnListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == btnGreater.getId()){
                txtCompareSign.setText(GREATER_THAN);
                check(GREATER_THAN);
            }
            if (v.getId() == btnEquals.getId()){
                txtCompareSign.setText(EQUAL_TO);
                check(EQUAL_TO);
            }
            if (v.getId() == btnLess.getId()){
                txtCompareSign.setText(LESS_THAN);
                check(LESS_THAN);
            }
        }
    }
    public class DiagMultiplicationListener implements Dialog.OnDismissListener{
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
    }
}
