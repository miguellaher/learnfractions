package com.example.laher.learnfractions.comparing_dissimilar_fractions;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.Fraction;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;
import com.example.laher.learnfractions.comparing_similar_fractions.ComparingSimilarExerciseActivity;
import com.example.laher.learnfractions.comparing_similar_fractions.ComparingSimilarVideoActivity;

import java.util.ArrayList;

public class ComparingDissimilarExercise2Activity extends AppCompatActivity {
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
    int consecutiveRights, consecutiveWrongs;
    final Handler handler = new Handler();

    public final String GREATER_THAN = ">";
    public final String EQUAL_TO = "=";
    public final String LESS_THAN = "<";

    int requiredConsecutiveCorrects = 5;
    int maxConsecutiveWrongs = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparing_dissimilar_exercise2);
        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComparingDissimilarExercise2Activity.this,
                        ComparingSimilarExerciseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        btnNext.setText("DONE");
        //MULTIPLICATION DIALOG
        mdView = getLayoutInflater().inflate(R.layout.layout_dialog_multiplication, null);
        multiplicationDialog = new Dialog(ComparingDissimilarExercise2Activity.this);
        multiplicationDialog.setTitle("Multiplication Equation");
        multiplicationDialog.setContentView(mdView);
        diagTxtMultiplicand = (TextView) mdView.findViewById(R.id.md_txtMultiplicand);
        diagTxtMultiplier = (TextView) mdView.findViewById(R.id.md_txtMultiplier);
        diagInputProduct = (EditText) mdView.findViewById(R.id.md_inputProduct);
        dialogBtnCheck = (Button) mdView.findViewById(R.id.md_btnCheck);
        dialogBtnCheck.setOnClickListener(new DiagBtnCheckListener());
        //GUI
        txtScore = (TextView) findViewById(R.id.d2_txtScore);
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
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

        go();
    }
    public void go(){
        setup();
    }
    public void setup(){
        enableBtnCompareSign(false);
        setFractions();
        txtCompareSign.setText("_");
        txtInstruction.setText("Click a denominator");
    }
    public void enableBtnCompareSign(boolean bool){
        btnGreater.setEnabled(bool);
        btnEquals.setEnabled(bool);
        btnLess.setEnabled(bool);
    }
    public void setFractions(){
        fractionOne.generateRandFraction(9);
        fractionTwo.generateRandFraction(9);
        while (fractionOne.getDenominator()==fractionTwo.getDenominator() &&
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
        int width = (int) (getResources().getDisplayMetrics().widthPixels);
        int height = (int) (getResources().getDisplayMetrics().heightPixels*0.3);

        txtInstruction.setText("Get the product of the clicked numbers.");

        diagTxtMultiplicand.setText(String.valueOf(denom));
        diagTxtMultiplier.setText(String.valueOf(num));
        multiplicationDialog.getWindow().setLayout(width,height);
        multiplicationDialog.setCancelable(false);
        multiplicationDialog.setCanceledOnTouchOutside(false);
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
    public void correct(){
        stepsIdList.clear();
        removeTxtListeners();
        enableBtnCompareSign(false);
        consecutiveRights++;
        consecutiveWrongs = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        txtInstruction.setText("correct");

        if (consecutiveRights >= requiredConsecutiveCorrects) {
            btnNext.setEnabled(true);
            txtInstruction.setText("lesson finished");
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
        consecutiveWrongs++;
        consecutiveRights = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        txtInstruction.setText("wrong");

        if (consecutiveWrongs >= maxConsecutiveWrongs) {
            txtInstruction.setText("You had " + consecutiveWrongs + " consecutive wrongs." +
                    " Preparing to start previous exercise.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(ComparingDissimilarExercise2Activity.this,
                            ComparingDissimilarExerciseActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
            if (Integer.valueOf(String.valueOf(diagInputProduct.getText()))==product){
                if (stepsIdList.get(stepsIdList.size()-1) == txtNum2.getId()) {
                    if (stepsIdList.get(stepsIdList.size()-2) == txtDenom1.getId()) {
                        txtProduct2.setText(diagInputProduct.getText());
                        txtProduct2.setVisibility(TextView.VISIBLE);
                    }
                } else if (stepsIdList.get(stepsIdList.size()-1) == txtNum1.getId()) {
                    if (stepsIdList.get(stepsIdList.size()-2) == txtDenom2.getId()) {
                        txtProduct1.setText(diagInputProduct.getText());
                        txtProduct1.setVisibility(TextView.VISIBLE);
                    }
                }
                resetTxtFractionsColor();
                diagInputProduct.setText("");
                multiplicationDialog.dismiss();
            } else {
                shakeAnimate(diagInputProduct);
            }
            if(txtProduct1.getVisibility() == TextView.VISIBLE
                    && txtProduct2.getVisibility() == TextView.VISIBLE){
                enableBtnCompareSign(true);
                txtInstruction.setText("Compare the two products");
            } else {
                txtInstruction.setText("Click the other denominator");
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
}