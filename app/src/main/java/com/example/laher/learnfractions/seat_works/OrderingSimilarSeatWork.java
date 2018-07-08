package com.example.laher.learnfractions.seat_works;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.SeatworkListActivity;
import com.example.laher.learnfractions.dialog_layout.SeatWorkStatDialog;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestion;
import com.example.laher.learnfractions.model.SeatWork;
import com.example.laher.learnfractions.util.AppConstants;


import java.util.ArrayList;
import java.util.Objects;

public class OrderingSimilarSeatWork extends SeatWork {
    Context mContext = this;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Ordering Fractions";
    //GUI
    TextView txtNum1, txtNum2, txtNum3, txtDenom1, txtDenom2, txtDenom3, txtItemIndicator, txtInstruction;
    ConstraintLayout clFraction1, clFraction2, clFraction3;
    //VARIABLES
    Fraction fraction1, fraction2, fraction3;
    FractionQuestion fractionQuestion;
    ArrayList<FractionQuestion> fractionQuestions;
    int questionNum;

    int clicks;
    boolean wrong;
    long startingTime;

    public OrderingSimilarSeatWork(String topicName, int seatworkNum) {
        super(topicName, seatworkNum);
    }

    public OrderingSimilarSeatWork() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_similar_exercise2);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderingSimilarSeatWork.this,
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
        btnNext.setText(AppConstants.DONE);
        //GUI
        txtNum1 = findViewById(R.id.os2_txtNum1);
        txtNum2 = findViewById(R.id.os2_txtNum2);
        txtNum3 = findViewById(R.id.os2_txtNum3);
        txtDenom1 = findViewById(R.id.os2_txtDenom1);
        txtDenom2 = findViewById(R.id.os2_txtDenom2);
        txtDenom3 = findViewById(R.id.os2_txtDenom3);
        txtItemIndicator = findViewById(R.id.os2_txtScore);
        updateItemIndicator(txtItemIndicator);
        txtInstruction = findViewById(R.id.os2_txtInstruction);
        clFraction1 = findViewById(R.id.os2_clFraction1);
        clFraction2 = findViewById(R.id.os2_clFraction2);
        clFraction3 = findViewById(R.id.os2_clFraction3);

        int item_size = Objects.requireNonNull(getIntent().getExtras()).getInt("item_size");
        if (item_size != 0){
            setItems_size(item_size);
            updateItemIndicator(txtItemIndicator);
        }
        startingTime = System.currentTimeMillis();
        go();
    }

    public void go(){
        setFractionQuestions();
        wrong = false;
    }
    public void setFractionQuestions(){
        fractionQuestions = new ArrayList<>();
        questionNum = 0;
        for (int i = 0; i < getItems_size(); i++){
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
    public void setClFractionsListener(){
        clFraction1.setOnClickListener(new ClFractionListener());
        clFraction2.setOnClickListener(new ClFractionListener());
        clFraction3.setOnClickListener(new ClFractionListener());
    }
    public void answered(){
        incrementItemNum();
        if (getCurrentItemNum()>getItems_size()){
            long endingTime = System.currentTimeMillis();
            setTimeSpent(endingTime-startingTime);
            SeatWorkStatDialog seatWorkStatDialog = new SeatWorkStatDialog(mContext, OrderingSimilarSeatWork.this);
            seatWorkStatDialog.show();
            seatWorkStatDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Intent intent = new Intent(OrderingSimilarSeatWork.this,
                            SeatworkListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        } else {
            wrong = false;
            updateItemIndicator(txtItemIndicator);
            questionNum++;
            setGuiFractions();
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
                if(!wrong) {
                    incrementCorrect();
                }
                answered();
            }
        }
        public void check(Fraction fraction){
            if (!fraction.equals(fractionQuestions.get(questionNum).getFractions().get(clicks))){
                wrong = true;
            }
            setTextColor(0,255,0);
            clicks++;
        }
        public void setTextColor(int r, int g, int b){
            num.setTextColor(Color.rgb(r, g, b));
            denom.setTextColor(Color.rgb(r, g, b));
        }
    }
}
