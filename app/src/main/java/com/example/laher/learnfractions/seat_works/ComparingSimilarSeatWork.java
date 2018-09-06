package com.example.laher.learnfractions.seat_works;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.ComparingSimilarQuestion;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;

import java.util.ArrayList;

public class ComparingSimilarSeatWork extends SeatWork {
    //private static final String TAG = "CS_SW1";
    Context mContext = this;
    //GUI
    TextView txtNum1;
    TextView txtNum2;
    TextView txtDenom1;
    TextView txtDenom2;
    TextView txtCompareSign;
    TextView txtItemIndicator;
    TextView txtInstruction;
    Button btnGreater;
    Button btnEquals;
    Button btnLess;

    int questionNum;
    ComparingSimilarQuestion fractionQuestion;
    ArrayList<ComparingSimilarQuestion> fractionQuestions;

    public ComparingSimilarSeatWork(String topicName) {
        super(topicName);
        String id = AppIDs.CSS;
        setId(id);
        Range range = getRange();
        Probability probability = new Probability(Probability.P_RAISED_TO_3, range);
        setProbability(probability);
        setRangeEditable(true);
    }

    public ComparingSimilarSeatWork() {
        setTopicName(AppConstants.COMPARING_SIMILAR_FRACTIONS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comparing_similar_exercise2);
        super.onCreate(savedInstanceState);
        String id = AppIDs.CSS;
        setId(id);
        //GUI
        txtNum1 = findViewById(R.id.c2_num1);
        txtNum2 = findViewById(R.id.c2_num2);
        txtDenom1 = findViewById(R.id.c2_denom1);
        txtDenom2 = findViewById(R.id.c2_denom2);
        txtCompareSign = findViewById(R.id.c2_compareSign);
        txtItemIndicator = findViewById(R.id.c2_txtScore);
        updateItemIndicator(txtItemIndicator);
        txtInstruction = findViewById(R.id.c2_txtInstruction);

        btnGreater = findViewById(R.id.c2_btnGreater);
        btnEquals = findViewById(R.id.c2_btnEqual);
        btnLess = findViewById(R.id.c2_btnLess);
        btnGreater.setText(AppConstants.GREATER_THAN);
        btnEquals.setText(AppConstants.EQUAL_TO);
        btnLess.setText(AppConstants.LESS_THAN);
        btnGreater.setOnClickListener(new BtnListener());
        btnEquals.setOnClickListener(new BtnListener());
        btnLess.setOnClickListener(new BtnListener());

        go();
    }
    @Override
    protected void go(){
        super.go();
        setFractionQuestions();
    }
    public void setFractionQuestions(){
        fractionQuestions = new ArrayList<>();
        questionNum = 0;
        Range range = getRange();
        for(int i = 0; i < getItems_size(); i++){
            fractionQuestion = new ComparingSimilarQuestion(range);
            while (fractionQuestions.contains(fractionQuestion)){
                fractionQuestion = new ComparingSimilarQuestion(range);
            }
            fractionQuestions.add(fractionQuestion);
        }
        setTxtFractions();
    }
    public void setTxtFractions(){
        ComparingSimilarQuestion question = fractionQuestions.get(questionNum);
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
        txtInstruction.setText(AppConstants.I_COMPARE);
        txtCompareSign.setText("_");
    }
    public void enableButtons(boolean bool){
        btnGreater.setEnabled(bool);
        btnEquals.setEnabled(bool);
        btnLess.setEnabled(bool);
    }
    public class BtnListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            String s = b.getText().toString();
            txtCompareSign.setText(s);
            if (isCorrect(s)){
                incrementCorrect();
            }
            if (getCurrentItemNum()>= getItems_size()){
                enableButtons(false);
                seatworkFinished();
            } else {
                questionNum++;
                setTxtFractions();
                incrementItemNum();
                updateItemIndicator(txtItemIndicator);
            }
        }
    }
    private boolean isCorrect(String answer){
        ComparingSimilarQuestion question = fractionQuestions.get(questionNum);
        Fraction fraction1 = question.getFraction1();
        Fraction fraction2 = question.getFraction2();
        Fraction fractionAnswer = question.getFractionAnswer();
        switch (answer) {
            case AppConstants.GREATER_THAN:
                if (fractionAnswer.equals(fraction1)) {
                    return true;
                }
                break;
            case AppConstants.LESS_THAN:
                if (fractionAnswer.equals(fraction2)) {
                    return true;
                }
                break;
            case AppConstants.EQUAL_TO:
                if (fractionAnswer.equals(AppConstants.EQUAL_FRACTIONS)) {
                    return true;
                }
                break;
        }
        return false;
    }
}
