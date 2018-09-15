package com.example.laher.learnfractions.seat_works;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.OrderingSimilarQuestion;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;

import java.util.ArrayList;

public class OrderingSimilarSeatWork extends SeatWork {
    Context mContext = this;
    //GUI
    TextView txtNum1;
    TextView txtNum2;
    TextView txtNum3;
    TextView txtDenom1;
    TextView txtDenom2;
    TextView txtDenom3;
    TextView txtItemIndicator;
    TextView txtInstruction;
    ConstraintLayout clFraction1;
    ConstraintLayout clFraction2;
    ConstraintLayout clFraction3;
    ImageView imageLine1;
    ImageView imageLine2;
    ImageView imageLine3;

    ArrayList<OrderingSimilarQuestion> questions;
    int questionNum;

    int clicks;
    boolean wrong;

    public OrderingSimilarSeatWork(String topicName) {
        super(topicName);
        String id = AppIDs.OSS;
        setId(id);
        Range range = getRange();
        Probability probability = new Probability(Probability.ORDERING_SIMILAR, range);
        setProbability(probability);
        setRangeEditable(true);
    }

    public OrderingSimilarSeatWork() {
        setTopicName(AppConstants.ORDERING_SIMILAR);
        setSeatWorkNum(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ordering_similar_exercise2);
        super.onCreate(savedInstanceState);
        String id = AppIDs.OSS;
        setId(id);
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

        imageLine1 = findViewById(R.id.ose2_imgLine1);
        imageLine2 = findViewById(R.id.ose2_imgLine2);
        imageLine3 = findViewById(R.id.ose2_imgLine3);

        imageLine1.setImageResource(R.drawable.line);
        imageLine2.setImageResource(R.drawable.line);
        imageLine3.setImageResource(R.drawable.line);

        go();
    }

    @Override
    protected void go(){
        super.go();
        setFractionQuestions();
        wrong = false;
    }
    public void setFractionQuestions(){
        questionNum = 0;
        questions = new ArrayList<>();
        Range range = getRange();
        for (int i = 0; i < getItems_size(); i++){
            OrderingSimilarQuestion question = new OrderingSimilarQuestion(range);
            while (questions.contains(question)){
                question = new OrderingSimilarQuestion(range);
            }
            questions.add(question);
        }
        setGuiFractions();
    }
    public void setGuiFractions(){
        OrderingSimilarQuestion question = questions.get(questionNum);

        Fraction fraction1 = question.getFraction1();
        Fraction fraction2 = question.getFraction2();
        Fraction fraction3 = question.getFraction3();
        int numerator1 = fraction1.getNumerator();
        int numerator2 = fraction2.getNumerator();
        int numerator3 = fraction3.getNumerator();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        int denominator3 = fraction3.getDenominator();
        String strNumerator1 = String.valueOf(numerator1);
        String strNumerator2 = String.valueOf(numerator2);
        String strNumerator3 = String.valueOf(numerator3);
        String strDenominator1 = String.valueOf(denominator1);
        String strDenominator2 = String.valueOf(denominator2);
        String strDenominator3 = String.valueOf(denominator3);

        clicks = 0;
        String instruction = "Click from least to greatest.";
        txtInstruction.setText(instruction);

        txtNum1.setText(strNumerator1);
        txtNum2.setText(strNumerator2);
        txtNum3.setText(strNumerator3);
        txtDenom1.setText(strDenominator1);
        txtDenom2.setText(strDenominator2);
        txtDenom3.setText(strDenominator3);
        setClFractionsListener();
        resetTxtNumColor();
    }
    public void resetTxtNumColor(){
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
            seatworkFinished();
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
            OrderingSimilarQuestion question = questions.get(questionNum);
            Fraction fraction1 = question.getFraction1();
            Fraction fraction2 = question.getFraction2();
            Fraction fraction3 = question.getFraction3();
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
            OrderingSimilarQuestion question = questions.get(questionNum);
            ArrayList<Fraction> sortedFractions = question.getSortedFractions();
            Fraction correctFraction = sortedFractions.get(clicks);
            if (!fraction.equals(correctFraction)){
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
