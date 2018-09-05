package com.example.laher.learnfractions.seat_works;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.ChapterExamListActivity;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.dialog_layout.ConfirmationDialog;
import com.example.laher.learnfractions.dialog_layout.SeatWorkStatDialog;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.ComparingDissimilarQuestion;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.util.AppCache;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;

import java.util.ArrayList;
import java.util.Objects;

public class ComparingDissimilarSeatWork extends SeatWork {
    Context mContext = this;
    //GUI
    TextView txtItemIndicator;
    TextView txtProduct1;
    TextView txtProduct2;
    TextView txtNum1;
    TextView txtNum2;
    TextView txtDenom1;
    TextView txtDenom2;
    TextView txtCompareSign;
    TextView txtInstruction;
    Button btnGreater;
    Button btnEquals;
    Button btnLess;
    //VARIABLES
    private String TYPE;
    boolean shouldAllowBack;

    ArrayList<Integer> stepsIdList;

    ArrayList<ComparingDissimilarQuestion> questions;
    int questionNum;

    public final String GREATER_THAN = ">";
    public final String EQUAL_TO = "=";
    public final String LESS_THAN = "<";

    public ComparingDissimilarSeatWork(String topicName) {
        super(topicName);
        String id = AppIDs.CDS;
        setId(id);
        Range range = getRange();
        Probability probability = new Probability(Probability.TWO_DISSIMILAR_FRACTIONS, range);
        setProbability(probability);
        setRangeEditable(true);
    }

    public ComparingDissimilarSeatWork(int size) {
        super(size);
        setTopicName(AppConstants.COMPARING_DISSIMILAR_FRACTIONS);
    }

    public ComparingDissimilarSeatWork() {
        setTopicName(AppConstants.COMPARING_DISSIMILAR_FRACTIONS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comparing_dissimilar_exercise2);
        super.onCreate(savedInstanceState);
        String id = AppIDs.CDS;
        setId(id);
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
        shouldAllowBack = true;

        try {
            int item_size = Objects.requireNonNull(getIntent().getExtras()).getInt("item_size");
            if (item_size != 0) {
                setItems_size(item_size);
                updateItemIndicator(txtItemIndicator);
            }
            TYPE = Objects.requireNonNull(getIntent().getExtras()).getString("type");
            assert TYPE != null;
            if (TYPE.equals(AppConstants.CHAPTER_EXAM)){
                String title = AppCache.getChapterExam().getExamTitle();
                txtTitle.setText(title);
                shouldAllowBack = false;
                buttonBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ConfirmationDialog confirmationDialog = new ConfirmationDialog(mContext,"Are you sure you want to exit exam?");
                        confirmationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                if (confirmationDialog.isConfirmed()){
                                    Intent intent = new Intent(ComparingDissimilarSeatWork.this,
                                            ChapterExamListActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }
                        });
                        confirmationDialog.show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        go();
    }

    @Override
    protected void go(){
        super.go();
        setup();
    }
    public void setup(){
        setFractionQuestions();
        txtCompareSign.setText("_");
        txtInstruction.setText(AppConstants.I_COMPARE);
    }
    public void enableBtnCompareSign(boolean bool){
        btnGreater.setEnabled(bool);
        btnEquals.setEnabled(bool);
        btnLess.setEnabled(bool);
    }
    public void setFractionQuestions(){
        questionNum = 0;
        questions = new ArrayList<>();
        Range range = getRange();
        for(int i = 0; i < getItems_size(); i++) {
            ComparingDissimilarQuestion question = new ComparingDissimilarQuestion(range);
            while (questions.contains(question)) {
                question = new ComparingDissimilarQuestion(range);
            }
            questions.add(question);
        }
        setGuiFractions();
    }
    public void setGuiFractions(){
        ComparingDissimilarQuestion question = questions.get(questionNum);
        txtCompareSign.setText("");
        Fraction fraction1 = question.getFraction1();
        Fraction fraction2 = question.getFraction2();
        int numerator1 = fraction1.getNumerator();
        int numerator2 = fraction2.getNumerator();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        String strNumerator1 = String.valueOf(numerator1);
        String strNumerator2 = String.valueOf(numerator2);
        String strDenominator1 = String.valueOf(denominator1);
        String strDenominator2 = String.valueOf(denominator2);

        txtNum1.setText(strNumerator1);
        txtNum2.setText(strNumerator2);
        txtDenom1.setText(strDenominator1);
        txtDenom2.setText(strDenominator2);
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
            enableBtnCompareSign(false);
            if (TYPE!=null) {
                if (TYPE.equals(AppConstants.CHAPTER_EXAM)) {
                    AppCache.postSeatWorkStat(ComparingDissimilarSeatWork.this);
                    SeatWorkStatDialog seatWorkStatDialog = new SeatWorkStatDialog(mContext, ComparingDissimilarSeatWork.this);
                    seatWorkStatDialog.show();
                    seatWorkStatDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            finish();
                        }
                    });
                }
            } else {
                seatworkFinished();
            }
        } else {
            updateItemIndicator(txtItemIndicator);
            questionNum++;
            setGuiFractions();
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
    @Override
    public void onBackPressed() {
        if (shouldAllowBack){
            super.onBackPressed();
        }
    }
}
