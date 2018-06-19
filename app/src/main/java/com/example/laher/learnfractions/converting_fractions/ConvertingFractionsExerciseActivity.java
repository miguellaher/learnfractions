package com.example.laher.learnfractions.converting_fractions;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.Fraction;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;

import java.util.ArrayList;

public class ConvertingFractionsExerciseActivity extends AppCompatActivity {
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Converting Fractions";
    //GUI
    TextView txtNum1, txtDenom1, txtScore, txtInstruction, txtQuotient, txtRemainder, txtR;
    EditText inputNum, inputDenom, inputWholeNum;
    Button btnCheck;
    //DIVISION DIALOG
    Dialog divisionDialog;
    View ddView;
    TextView diagDdTxtNum1, diagDdTxtNum2, diagDdTxtSign;
    EditText diagDdInputAnswer, diagDdInputRemainder;
    Button diagDdBtnCheck;
    //VARIABLES
    Fraction fraction;
    ArrayList<Fraction> fractions;
    int questionNum;
    int consecutiveRights;
    int requiredConsecutiveCorrects = 10;
    int clicks;
    final Handler handler = new Handler();
    ColorStateList defaultColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converting_fractions_exercise);
        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConvertingFractionsExerciseActivity.this,
                        ConvertingFractionsVideoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CHANGE INTENT PARAMS
                Intent intent = new Intent(ConvertingFractionsExerciseActivity.this,
                        ConvertingFractionsExercise2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        //GUI
        txtNum1 = (TextView) findViewById(R.id.cvt_txtNum1);
        txtDenom1 = (TextView) findViewById(R.id.cvt_txtDenom1);
        txtScore = (TextView) findViewById(R.id.cvt_txtScore);
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        txtInstruction = (TextView) findViewById(R.id.cvt_txtInstruction);
        txtQuotient = (TextView) findViewById(R.id.cvt_txtQuotient);
        txtRemainder = (TextView) findViewById(R.id.cvt_txtRemainder);
        txtR = (TextView) findViewById(R.id.cvt_txtR);
        inputNum = (EditText) findViewById(R.id.cvt_inputNum);
        inputDenom = (EditText) findViewById(R.id.cvt_inputDenom);
        inputWholeNum = (EditText) findViewById(R.id.cvt_inputWholeNum);
        btnCheck = (Button) findViewById(R.id.cvt_btnCheck);
        btnCheck.setOnClickListener(new BtnCheckListener());
        //DIVISION DIALOG
        ddView = getLayoutInflater().inflate(R.layout.layout_dialog_division, null);
        divisionDialog = new Dialog(ConvertingFractionsExerciseActivity.this);
        divisionDialog.setOnDismissListener(new DivisionDialogListener());
        divisionDialog.setTitle("Division Equation");
        divisionDialog.setContentView(ddView);
        diagDdTxtNum1 = (TextView) ddView.findViewById(R.id.dd_txtDividend);
        diagDdTxtNum2 = (TextView) ddView.findViewById(R.id.dd_txtDivisor);
        diagDdTxtSign = (TextView) ddView.findViewById(R.id.dd_txtSign);
        diagDdInputAnswer = (EditText) ddView.findViewById(R.id.dd_inputProduct);
        diagDdInputRemainder = (EditText) ddView.findViewById(R.id.dd_inputRemainder);
        diagDdInputAnswer.setOnKeyListener(new DiagInputListener());
        diagDdInputRemainder.setOnKeyListener(new DiagInputListener());
        //diagEdInputAnswer.setOnKeyListener(new DiagTxtInputAnswerListener());
        diagDdBtnCheck = (Button) ddView.findViewById(R.id.dd_btnCheck);
        diagDdBtnCheck.setOnClickListener(new DiagDdBtnCheckListener());

        defaultColor = txtDenom1.getTextColors();

        go();
    }
    public void go(){
        setQuestions();
        setGuiFraction();
        setUp();
    }
    public void correct(){
        consecutiveRights++;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        setInputEnabled(false);
        btnCheck.setEnabled(false);
        if (consecutiveRights >= requiredConsecutiveCorrects){
            btnNext.setEnabled(true);
            txtInstruction.setText("Finished");
        } else {
            txtInstruction.setText("Correct");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    questionNum++;
                    setGuiFraction();
                    setUp();
                }
            },2000);
        }
    }
    public void setQuestions(){
        questionNum = 0;
        fractions = new ArrayList<>();
        for (int i = 0; i < requiredConsecutiveCorrects; i++){
            fraction = new Fraction(Fraction.IMPROPER);
            while (fraction.getNumerator()%fraction.getDenominator()==0){
                fraction = new Fraction(Fraction.IMPROPER);
            }
            fractions.add(fraction);
        }
    }
    public void setGuiFraction(){
        txtNum1.setText(String.valueOf(fractions.get(questionNum).getNumerator()));
        txtDenom1.setText(String.valueOf(fractions.get(questionNum).getDenominator()));
    }
    public void setUp(){
        txtInstruction.setText("To divide the numerator by the denominator, click the numerator first and the " +
                "denominator second.");
        setFractionTxtListener(true);
        setAnswerVisibility(false);
        setInputEnabled(false);
        btnCheck.setEnabled(false);
        clearInputAreas();
    }
    public void setAnswerVisibility(boolean bool){
        if (bool){
            txtQuotient.setVisibility(TextView.VISIBLE);
            txtRemainder.setVisibility(TextView.VISIBLE);
            txtR.setVisibility(TextView.VISIBLE);
        } else {
            txtQuotient.setVisibility(TextView.INVISIBLE);
            txtRemainder.setVisibility(TextView.INVISIBLE);
            txtR.setVisibility(TextView.INVISIBLE);
        }
    }
    public void setInputEnabled(boolean bool){
        inputWholeNum.setEnabled(bool);
        inputNum.setEnabled(bool);
        inputDenom.setEnabled(bool);
    }
    public void popUpDivisionDialog(){
        diagDdTxtNum1.setText(String.valueOf(fractions.get(questionNum).getNumerator()));
        diagDdTxtNum2.setText(String.valueOf(fractions.get(questionNum).getDenominator()));
        fractions.get(questionNum).toMixed();
        divisionDialog.show();
        diagDdInputAnswer.requestFocus();
        txtInstruction.setText("Get the quotient and remainder");
    }
    public void clearInputAreas(){
        inputWholeNum.setText("");
        inputNum.setText("");
        inputDenom.setText("");
        diagDdInputAnswer.setText("");
        diagDdInputRemainder.setText("");
    }
    public void shakeAnimate(TextView textview){
        ObjectAnimator.ofFloat(textview, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                .setDuration(1000)
                .start();
    }
    public void setFractionTxtListener(boolean bool){
        if (bool) {
            txtNum1.setOnClickListener(new TxtFractionListener());
            txtDenom1.setOnClickListener(new TxtFractionListener());
        } else {
            txtNum1.setOnClickListener(null);
            txtDenom1.setOnClickListener(null);
        }
        txtNum1.setClickable(bool);
        txtDenom1.setClickable(bool);
    }
    public class TxtFractionListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            if (clicks==0){
                if (t.getId()==txtNum1.getId()){
                    t.setTextColor(Color.rgb(0,255,0));
                    clicks++;
                }
            }
            if (clicks==1){
                if (t.getId()==txtDenom1.getId()){
                    t.setTextColor(Color.rgb(0,255,0));
                    popUpDivisionDialog();
                    clicks=0;
                }
            }
        }
    }
    public class DiagDdBtnCheckListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (!diagDdInputAnswer.getText().toString().matches("") &&
                    !diagDdInputRemainder.getText().toString().matches("")){
                int quotient = Integer.valueOf(String.valueOf(diagDdInputAnswer.getText()));
                int remainder = Integer.valueOf(String.valueOf(diagDdInputRemainder.getText()));
                if (quotient != fractions.get(questionNum).getWholeNum()) {
                    shakeAnimate(diagDdInputAnswer);
                }
                if (remainder != fractions.get(questionNum).getNumerator()) {
                    shakeAnimate(diagDdInputRemainder);
                }
                if (quotient == fractions.get(questionNum).getWholeNum() && remainder ==
                        fractions.get(questionNum).getNumerator()) {
                    txtQuotient.setText("" + quotient);
                    txtRemainder.setText("" + remainder);
                    setAnswerVisibility(true);
                    setInputEnabled(true);
                    inputWholeNum.requestFocus();
                    btnCheck.setEnabled(true);
                    setFractionTxtListener(false);
                    divisionDialog.dismiss();
                    txtInstruction.setText("The quotient will be the whole number. The remainder will be the numerator. " +
                            "The denominator remains the same.");
                }
            } else {
                shakeAnimate(diagDdInputAnswer);
                shakeAnimate(diagDdInputRemainder);
            }
        }
    }
    public class BtnCheckListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (!inputNum.getText().toString().matches("") && !inputDenom.getText().toString().matches("")
                    && !inputWholeNum.getText().toString().matches("")){
                int wholeNum = Integer.valueOf(String.valueOf(inputWholeNum.getText()));
                int num = Integer.valueOf(String.valueOf(inputNum.getText()));
                int denom = Integer.valueOf(String.valueOf(inputDenom.getText()));
                if (wholeNum != fractions.get(questionNum).getWholeNum()){
                    shakeAnimate(inputWholeNum);
                }
                if (num != fractions.get(questionNum).getNumerator()){
                    shakeAnimate(inputNum);
                }
                if (denom != fractions.get(questionNum).getDenominator()){
                    shakeAnimate(inputDenom);
                }
                if (wholeNum == fractions.get(questionNum).getWholeNum() &&
                        num == fractions.get(questionNum).getNumerator() &&
                        denom == fractions.get(questionNum).getDenominator()) {
                    correct();
                }
            } else {
                shakeAnimate(inputWholeNum);
                shakeAnimate(inputNum);
                shakeAnimate(inputDenom);
            }
        }
    }
    public class DivisionDialogListener implements Dialog.OnDismissListener{
        @Override
        public void onDismiss(DialogInterface dialog) {
            txtNum1.setTextColor(defaultColor);
            txtDenom1.setTextColor(defaultColor);
        }
    }
    public class DiagInputListener implements EditText.OnKeyListener{
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            TextView t = (TextView) v;
            t.setTextColor(Color.rgb(0,0,0));
            return false;
        }
    }
}
