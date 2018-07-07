package com.example.laher.learnfractions.seatworks;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.SeatworkListActivity;
import com.example.laher.learnfractions.TopicsMenuActivity;
import com.example.laher.learnfractions.dialog_layout.SeatWorkStatDialog;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.lessons.comparing_similar_fractions.ComparingSimilarExerciseActivity;
import com.example.laher.learnfractions.model.SeatWork;
import com.example.laher.learnfractions.util.AppConstants;

import java.util.ArrayList;

public class ComparingDissimilarSeatWork extends SeatWork {
    Context mContext = this;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Comparing Dissimilar";
    //GUI
    TextView txtItemIndicator, txtProduct1, txtProduct2, txtNum1, txtNum2, txtDenom1, txtDenom2, txtCompareSign, txtInstruction;
    Button btnGreater, btnEquals, btnLess;
    //VARIABLES
    ArrayList<Integer> stepsIdList;
    Fraction fractionOne, fractionTwo;
    long startingTime;

    public final String GREATER_THAN = ">";
    public final String EQUAL_TO = "=";
    public final String LESS_THAN = "<";

    public ComparingDissimilarSeatWork(String topicName, int seatworkNum) {
        super(topicName, seatworkNum);
    }

    public ComparingDissimilarSeatWork() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparing_dissimilar_exercise2);


        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComparingDissimilarSeatWork.this,
                        SeatworkListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        //GUI
        txtItemIndicator = findViewById(R.id.d2_txtScore);
        updateItemIndicator(txtItemIndicator);
        txtProduct1 = findViewById(R.id.d2_txtProduct1);
        txtProduct2 = findViewById(R.id.d2_txtProduct2);
        txtNum1 = findViewById(R.id.d2_txtNum1);
        txtNum2 = findViewById(R.id.d2_txtNum2);
        txtDenom1 = findViewById(R.id.d2_txtDenom1);
        txtDenom2 = findViewById(R.id.d2_txtDenom2);
        txtCompareSign = findViewById(R.id.d2_txtCompareSign);
        resetTxtFractionsColor();
        txtInstruction = findViewById(R.id.d2_txtInstruction);
        stepsIdList = new ArrayList<>();

        btnGreater = findViewById(R.id.d2_btnGreater);
        btnEquals = findViewById(R.id.d2_btnEquals);
        btnLess = findViewById(R.id.d2_btnLess);
        btnGreater.setOnClickListener(new BtnListener());
        btnEquals.setOnClickListener(new BtnListener());
        btnLess.setOnClickListener(new BtnListener());
        //VARIABLES
        fractionOne = new Fraction();
        fractionTwo = new Fraction();


        go();
        startingTime = System.currentTimeMillis();
    }

    public void go(){
        setup();
    }
    public void setup(){
        setFractions();
        txtCompareSign.setText("_");
        txtInstruction.setText(AppConstants.I_COMPARE);
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
        txtProduct1.setVisibility(TextView.INVISIBLE);
        txtProduct2.setVisibility(TextView.INVISIBLE);
    }
    public void shakeAnimate(TextView textview){
        ObjectAnimator.ofFloat(textview, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                .setDuration(1000)
                .start();
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
    public void check(String compareSign){
        int product1 = Integer.valueOf(String.valueOf(txtDenom2.getText())) * Integer.valueOf(String.valueOf(txtNum1.getText()));
        int product2 = Integer.valueOf(String.valueOf(txtDenom1.getText())) * Integer.valueOf(String.valueOf(txtNum2.getText()));
        if (compareSign.matches(GREATER_THAN)){
            if (product1 > product2) {
                incrementCorrect();
            }
        }
        if (compareSign.matches(EQUAL_TO)){
            if (product1 == product2) {
                incrementCorrect();
            }
        }
        if (compareSign.matches(LESS_THAN)){
            if (product1 < product2) {
                incrementCorrect();
            }
        }
        stepsIdList.clear();
        removeTxtListeners();
        incrementItemNum();
        if (getCurrentItemNum()>getItems_size()){
            long endingTime = System.currentTimeMillis();
            enableBtnCompareSign(false);
            setTimeSpent(endingTime-startingTime);
            SeatWorkStatDialog seatWorkStatDialog = new SeatWorkStatDialog(mContext, ComparingDissimilarSeatWork.this);
            seatWorkStatDialog.show();
            seatWorkStatDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Intent intent = new Intent(ComparingDissimilarSeatWork.this,
                            SeatworkListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        } else {
            updateItemIndicator(txtItemIndicator);
            go();
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