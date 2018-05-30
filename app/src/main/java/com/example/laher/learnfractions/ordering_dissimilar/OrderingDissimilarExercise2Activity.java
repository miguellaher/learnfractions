package com.example.laher.learnfractions.ordering_dissimilar;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.FractionQuestion;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;

import java.util.ArrayList;

public class OrderingDissimilarExercise2Activity extends AppCompatActivity {
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Ordering Fractions";
    //EQUATION DIALOG
    Dialog equationDialog;
    View edView;
    TextView diagEdTxtNum1, diagEdTxtNum2, diagEdTxtSign;
    EditText diagEdInputAnswer;
    Button diagEdBtnCheck;
    //LCM DIALOG
    Dialog lcmDialog;
    View lcmView;
    TextView diagLcmtxtNum1, diagLcmtxtNum2, diagLcmtxtNum3;
    EditText diagLcmInputLcm;
    Button diagLcmBtnCheck;
    //GUI
    TextView txtNum1, txtNum2, txtNum3, txtNum4, txtNum5, txtNum6, txtDenom1, txtDenom2, txtDenom3, txtDenom4, txtDenom5, txtDenom6;
    TextView txtScore, txtInstruction, txtEquation1, txtEquation2, txtEquation3;
    ConstraintLayout clFraction1, clFraction2, clFraction3, clFraction4, clFraction5, clFraction6;
    ColorStateList defaultColor;
    //VARIABLES
    FractionQuestion fractionQuestion;
    ArrayList<FractionQuestion> fractionQuestions;
    int questionNum;
    int consecutiveRights, consecutiveWrongs;
    int requiredConsecutiveCorrects = 10;
    int maxConsecutiveWrongs = 3;
    int clicks;
    TextView standByTxt, standByTxtEquation, standByTxtNum;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_dissimilar_exercise2);
        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderingDissimilarExercise2Activity.this,
                        OrderingDissimilarExerciseActivity.class);
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
                Intent intent = new Intent(OrderingDissimilarExercise2Activity.this,
                        TopicsMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        //EQUATION DIALOG
        edView = getLayoutInflater().inflate(R.layout.layout_dialog_equation, null);
        equationDialog = new Dialog(OrderingDissimilarExercise2Activity.this);
        equationDialog.setTitle("Division Equation");
        equationDialog.setContentView(edView);
        diagEdTxtNum1 = (TextView) edView.findViewById(R.id.md_txtMultiplicand);
        diagEdTxtNum2 = (TextView) edView.findViewById(R.id.md_txtMultiplier);
        diagEdTxtSign = (TextView) edView.findViewById(R.id.md_txtSign);
        diagEdInputAnswer = (EditText) edView.findViewById(R.id.md_inputProduct);
        diagEdInputAnswer.setOnKeyListener(new DiagTxtInputAnswerListener());
        diagEdBtnCheck = (Button) edView.findViewById(R.id.md_btnCheck);
        diagEdBtnCheck.setOnClickListener(new DiagEdBtnCheckListener());
        //LCM DIALOG
        lcmView = getLayoutInflater().inflate(R.layout.layout_dialog_lcm, null);
        lcmDialog = new Dialog(OrderingDissimilarExercise2Activity.this);
        lcmDialog.setTitle("Getting the LCD");
        lcmDialog.setContentView(lcmView);
        diagLcmtxtNum1 = (TextView) lcmView.findViewById(R.id.lcm_txtNum1);
        diagLcmtxtNum2 = (TextView) lcmView.findViewById(R.id.lcm_txtNum2);
        diagLcmtxtNum3 = (TextView) lcmView.findViewById(R.id.lcm_txtNum3);
        diagLcmInputLcm = (EditText) lcmView.findViewById(R.id.lcm_inputLcm);
        diagLcmInputLcm.setOnKeyListener(new DiagTxtInputAnswerListener());
        diagLcmBtnCheck = (Button) lcmView.findViewById(R.id.lcm_btnCheck);
        diagLcmBtnCheck.setOnClickListener(new DiagLcmBtnCheckListener());
        //GUI
        txtNum1 = (TextView) findViewById(R.id.od_txtNum1);
        txtNum2 = (TextView) findViewById(R.id.od_txtNum2);
        txtNum3 = (TextView) findViewById(R.id.od_txtNum3);
        txtNum4 = (TextView) findViewById(R.id.od_txtNum4);
        txtNum5 = (TextView) findViewById(R.id.od_txtNum5);
        txtNum6 = (TextView) findViewById(R.id.od_txtNum6);
        txtDenom1 = (TextView) findViewById(R.id.od_txtDenom1);
        txtDenom2 = (TextView) findViewById(R.id.od_txtDenom2);
        txtDenom3 = (TextView) findViewById(R.id.od_txtDenom3);
        txtDenom4 = (TextView) findViewById(R.id.od_txtDenom4);
        txtDenom5 = (TextView) findViewById(R.id.od_txtDenom5);
        txtDenom6 = (TextView) findViewById(R.id.od_txtDenom6);
        txtEquation1 = (TextView) findViewById(R.id.od_txtEquation1);
        txtEquation2 = (TextView) findViewById(R.id.od_txtEquation2);
        txtEquation3 = (TextView) findViewById(R.id.od_txtEquation3);
        txtScore = (TextView) findViewById(R.id.od_txtScore);
        txtInstruction = (TextView) findViewById(R.id.od_txtInstruction);
        clFraction1 = (ConstraintLayout) findViewById(R.id.od_fraction1);
        clFraction2 = (ConstraintLayout) findViewById(R.id.od_fraction2);
        clFraction3 = (ConstraintLayout) findViewById(R.id.od_fraction3);
        clFraction4 = (ConstraintLayout) findViewById(R.id.od_fraction4);
        clFraction5 = (ConstraintLayout) findViewById(R.id.od_fraction5);
        clFraction6 = (ConstraintLayout) findViewById(R.id.od_fraction6);
        defaultColor = txtNum1.getTextColors();

        go();
    }
    public void go(){
        setQuestions();
        setGuiFractionSet1();
        startup();
    }
    public void correct(){
        consecutiveRights++;
        consecutiveWrongs = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        txtInstruction.setText("correct");
        if (consecutiveRights>=requiredConsecutiveCorrects){
            txtInstruction.setText("finish");
            btnNext.setEnabled(true);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    resetTextColors();
                    resetTextColors2();
                    hideGuiFractionSet2();
                    hideEquations();
                    questionNum++;
                    setGuiFractionSet1();
                    setFractionSet1TxtDenomsListner();
                    resetVariables();
                }
            }, 2000);
        }
    }
    public void wrong(){
        consecutiveWrongs++;
        consecutiveRights = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        txtInstruction.setText("wrong");
        if (consecutiveWrongs>=maxConsecutiveWrongs){
            txtInstruction.setText("you had " + consecutiveWrongs + " consecutive wrongs. Preparing to go back to" +
                    " previous exercise.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(OrderingDissimilarExercise2Activity.this,
                            OrderingDissimilarExerciseActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }, 2000);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    go();
                }
            }, 2000);
        }
    }
    public void startup(){
        consecutiveRights = 0;
        consecutiveWrongs = 0;
        hideGuiFractionSet2();
        hideEquations();
        resetVariables();
        setFractionSet1TxtDenomsListner();
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
    }
    public void resetVariables(){
        txtInstruction.setText("Click the denominators and get the lcd (least common denominator).");
        diagEdTxtSign.setText("÷");
        clicks = 0;
    }
    public void setQuestions(){
        fractionQuestions = new ArrayList<>();
        questionNum = 0;
        for (int i = 0; i < requiredConsecutiveCorrects; i++){
            fractionQuestion = new FractionQuestion(FractionQuestion.ORDERING_DISSIMILAR);
            while(Integer.valueOf(fractionQuestion.getAnswer())>100){
                fractionQuestion = new FractionQuestion(FractionQuestion.ORDERING_DISSIMILAR);
            }
            fractionQuestions.add(fractionQuestion);
        }
    }
    public void setGuiFractionSet1(){
        txtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getNumerator()));
        txtNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getNumerator()));
        txtNum3.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionThree().getNumerator()));
        txtDenom1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
        txtDenom2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
        txtDenom3.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionThree().getDenominator()));
    }
    public void hideGuiFractionSet2(){
        clFraction4.setVisibility(ConstraintLayout.INVISIBLE);
        clFraction5.setVisibility(ConstraintLayout.INVISIBLE);
        clFraction6.setVisibility(ConstraintLayout.INVISIBLE);
    }
    public void showGuiFractionSet2(){
        clFraction4.setVisibility(ConstraintLayout.VISIBLE);
        clFraction5.setVisibility(ConstraintLayout.VISIBLE);
        clFraction6.setVisibility(ConstraintLayout.VISIBLE);
    }
    public void hideGuiFractionSet2Numerators(){
        txtNum4.setVisibility(TextView.INVISIBLE);
        txtNum5.setVisibility(TextView.INVISIBLE);
        txtNum6.setVisibility(TextView.INVISIBLE);
    }
    public void setGuiFractionSet2Denominators(){
        txtDenom4.setText(String.valueOf(fractionQuestions.get(questionNum).getAnswer()));
        txtDenom5.setText(String.valueOf(fractionQuestions.get(questionNum).getAnswer()));
        txtDenom6.setText(String.valueOf(fractionQuestions.get(questionNum).getAnswer()));
    }
    public void hideEquations(){
        txtEquation1.setVisibility(TextView.INVISIBLE);
        txtEquation2.setVisibility(TextView.INVISIBLE);
        txtEquation3.setVisibility(TextView.INVISIBLE);
    }
    public void setFractionSet1TxtDenomsListner(){
        txtDenom1.setOnClickListener(new TxtDenomListener());
        txtDenom2.setOnClickListener(new TxtDenomListener());
        txtDenom3.setOnClickListener(new TxtDenomListener());
        txtDenom1.setClickable(true);
        txtDenom2.setClickable(true);
        txtDenom3.setClickable(true);
    }
    public void removeFractionSet1TxtDenomsListner(){
        txtDenom1.setOnClickListener(null);
        txtDenom2.setOnClickListener(null);
        txtDenom3.setOnClickListener(null);
    }
    public void popUpLcmDialog(){
        diagLcmInputLcm.setText("");
        diagLcmtxtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
        diagLcmtxtNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
        diagLcmtxtNum3.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionThree().getDenominator()));
        lcmDialog.setCancelable(false);
        lcmDialog.setCanceledOnTouchOutside(false);
        lcmDialog.show();
        txtInstruction.setText("Get the lcd.");
    }
    public void popUpDivisionDialog(){
        diagEdInputAnswer.setText("");
        diagEdTxtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getAnswer()));
        diagEdTxtNum2.setText(String.valueOf(standByTxt.getText()));
        equationDialog.setCancelable(false);
        equationDialog.setCanceledOnTouchOutside(false);
        equationDialog.show();
        txtInstruction.setText("Get the quotient.");
    }
    public void popUpMultiplicationDialog(){
        diagEdInputAnswer.setText("");
        diagEdTxtSign.setText("x");
        diagEdTxtNum1.setText(String.valueOf(standByTxtEquation.getText()));
        diagEdTxtNum2.setText(String.valueOf(standByTxt.getText()));
        equationDialog.setCancelable(false);
        equationDialog.setCanceledOnTouchOutside(false);
        equationDialog.show();
        txtInstruction.setText("Get the product.");
    }
    public void resetTextColors(){
        txtDenom1.setTextColor(defaultColor);
        txtDenom2.setTextColor(defaultColor);
        txtDenom3.setTextColor(defaultColor);
        txtDenom4.setTextColor(defaultColor);
        txtDenom5.setTextColor(defaultColor);
        txtDenom6.setTextColor(defaultColor);
    }
    public void resetTextColors2(){
        txtNum1.setTextColor(defaultColor);
        txtNum2.setTextColor(defaultColor);
        txtNum3.setTextColor(defaultColor);
        txtEquation1.setTextColor(defaultColor);
        txtEquation2.setTextColor(defaultColor);
        txtEquation3.setTextColor(defaultColor);
    }
    public void setTxtQuotientListener(){
        txtDenom1.setOnClickListener(new QuotientTxtListener());
        txtDenom2.setOnClickListener(new QuotientTxtListener());
        txtDenom3.setOnClickListener(new QuotientTxtListener());
        txtDenom4.setOnClickListener(new QuotientTxtListener());
        txtDenom5.setOnClickListener(new QuotientTxtListener());
        txtDenom6.setOnClickListener(new QuotientTxtListener());
        txtDenom1.setClickable(true);
        txtDenom2.setClickable(true);
        txtDenom3.setClickable(true);
        txtDenom4.setClickable(true);
        txtDenom5.setClickable(true);
        txtDenom6.setClickable(true);
    }
    public void removeOnClickListner(View v){
        v.setOnClickListener(null);
        v.setClickable(false);
    }
    public void showTxtView(TextView t){
        t.setVisibility(TextView.VISIBLE);
    }
    public void setTxtProductListener(){
        txtEquation1.setOnClickListener(new ProductTxtListener());
        txtEquation2.setOnClickListener(new ProductTxtListener());
        txtEquation3.setOnClickListener(new ProductTxtListener());
        txtNum1.setOnClickListener(new ProductTxtListener());
        txtNum2.setOnClickListener(new ProductTxtListener());
        txtNum3.setOnClickListener(new ProductTxtListener());
    }
    public void setTxtColor(int r, int g, int b, TextView t, TextView t2){
        t.setTextColor(Color.rgb(r,g,b));
        t2.setTextColor(Color.rgb(r,g,b));
    }
    public void setFractionListener(){
        clFraction1.setOnClickListener(new ClFractionListener());
        clFraction2.setOnClickListener(new ClFractionListener());
        clFraction3.setOnClickListener(new ClFractionListener());
        clFraction1.setClickable(true);
        clFraction2.setClickable(true);
        clFraction3.setClickable(true);
    }
    public class TxtDenomListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            if (t.getCurrentTextColor()!=Color.rgb(0,255,0)){
                t.setTextColor(Color.rgb(0,255,0));
                clicks++;
            }
            if (clicks>=3){
                popUpLcmDialog();
                clicks = 0;
            }
        }
    }
    public class DiagLcmBtnCheckListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!diagLcmInputLcm.getText().toString().matches("")) {
                if (String.valueOf(diagLcmInputLcm.getText()).matches(fractionQuestions.get(questionNum).getAnswer())) {
                    setGuiFractionSet2Denominators();
                    showGuiFractionSet2();
                    hideGuiFractionSet2Numerators();
                    resetTextColors();
                    removeFractionSet1TxtDenomsListner();
                    setTxtQuotientListener();
                    txtInstruction.setText("Divide the lcd to its' corresponding denominator by clicking the two.");

                    lcmDialog.dismiss();
                } else {
                    diagLcmInputLcm.setTextColor(Color.rgb(255, 0, 0));
                }
            }
        }
    }
    public class DiagTxtInputAnswerListener implements View.OnKeyListener {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            EditText e = (EditText) v;
            e.setTextColor(Color.rgb(0,0,0));
            return false;
        }
    }
    public class QuotientTxtListener implements TextView.OnClickListener {
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            if (clicks==0) {
                clicks++;
                t.setTextColor(Color.rgb(0,255,0));
                if (t.getId() == txtDenom4.getId()) {
                    standByTxt = txtDenom1;
                    standByTxtEquation = txtEquation1;
                    removeOnClickListner(t);
                } else if (t.getId() == txtDenom5.getId()) {
                    standByTxt = txtDenom2;
                    standByTxtEquation = txtEquation2;
                    removeOnClickListner(t);
                } else if (t.getId() == txtDenom6.getId()) {
                    standByTxt = txtDenom3;
                    standByTxtEquation = txtEquation3;
                    removeOnClickListner(t);
                } else {
                    clicks = 0;
                    t.setTextColor(defaultColor);
                }
            } else if (clicks==1){
                t.setTextColor(Color.rgb(0,255,0));
                if (t.getId() == standByTxt.getId()){
                    clicks = 0;
                    popUpDivisionDialog();
                    removeOnClickListner(t);
                } else {
                    t.setTextColor(defaultColor);
                }
            }
        }
    }
    public class ProductTxtListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            if (clicks==0) {
                clicks++;
                t.setTextColor(Color.rgb(0,255,0));
                if (t.getId() == txtEquation1.getId()) {
                    standByTxt = txtNum1;
                    standByTxtEquation = txtEquation1;
                    standByTxtNum = txtNum4;
                    removeOnClickListner(t);
                } else if (t.getId() == txtEquation2.getId()) {
                    standByTxt = txtNum2;
                    standByTxtEquation = txtEquation2;
                    standByTxtNum = txtNum5;
                    removeOnClickListner(t);
                } else if (t.getId() == txtEquation3.getId()) {
                    standByTxt = txtNum3;
                    standByTxtEquation = txtEquation3;
                    standByTxtNum = txtNum6;
                    removeOnClickListner(t);
                } else {
                    clicks = 0;
                    t.setTextColor(defaultColor);
                }
            } else if (clicks==1){
                t.setTextColor(Color.rgb(0,255,0));
                if (t.getId() == standByTxt.getId()){
                    clicks = 0;
                    removeOnClickListner(t);
                    popUpMultiplicationDialog();
                } else {
                    t.setTextColor(defaultColor);
                }
            }
        }
    }
    public class DiagEdBtnCheckListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!diagEdInputAnswer.getText().toString().matches("")) {
                if (diagEdTxtSign.getText() == "÷") {
                    if (Integer.valueOf(String.valueOf(diagEdTxtNum1.getText())) / Integer.valueOf(String.valueOf(diagEdTxtNum2.getText()))
                            == Integer.valueOf(String.valueOf(diagEdInputAnswer.getText()))) {
                        standByTxtEquation.setText(String.valueOf(diagEdInputAnswer.getText()));
                        showTxtView(standByTxtEquation);
                        resetTextColors();
                        txtInstruction.setText("Click another two.");
                        equationDialog.dismiss();
                    } else {
                        diagEdInputAnswer.setTextColor(Color.rgb(255, 0, 0));
                    }
                    if (txtEquation1.getVisibility() == TextView.VISIBLE &&
                            txtEquation2.getVisibility() == TextView.VISIBLE &&
                            txtEquation3.getVisibility() == TextView.VISIBLE) {
                        txtInstruction.setText("Multiply the quotient and the corresponding numerator above by clicking the two.");
                        setTxtProductListener();
                    }
                } else if (diagEdTxtSign.getText().toString().matches("x")) {
                    if (Integer.valueOf(String.valueOf(diagEdTxtNum1.getText())) * Integer.valueOf(String.valueOf(diagEdTxtNum2.getText()))
                            == Integer.valueOf(String.valueOf(diagEdInputAnswer.getText()))) {
                        standByTxtEquation.setText(String.valueOf(diagEdTxtNum1.getText())
                                + "x" + String.valueOf(diagEdTxtNum2.getText()) + "=");
                        standByTxtNum.setText(String.valueOf(diagEdInputAnswer.getText()));
                        showTxtView(standByTxtNum);
                        resetTextColors2();
                        txtInstruction.setText("Click another two.");
                        equationDialog.dismiss();
                    } else {
                        diagEdInputAnswer.setTextColor(Color.rgb(255, 0, 0));
                    }
                    if (txtNum4.getVisibility() == TextView.VISIBLE &&
                            txtNum5.getVisibility() == TextView.VISIBLE &&
                            txtNum6.getVisibility() == TextView.VISIBLE) {
                        txtInstruction.setText("Click the fractions above from least to greatest according to their corresponding fractions");
                        setFractionListener();
                    }
                }
            }
        }
    }
    public class ClFractionListener implements ConstraintLayout.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == clFraction1.getId()){
                if (fractionQuestions.get(questionNum).getFractionOne().getValue().equals(fractionQuestions.get(questionNum).getFractions().get(clicks).getValue())){
                    clicks++;
                    setTxtColor(0,255,0,txtNum1,txtDenom1);
                    removeOnClickListner(v);
                } else {
                    wrong();
                }
            }
            if (v.getId() == clFraction2.getId()){
                if (fractionQuestions.get(questionNum).getFractionTwo().getValue().equals(fractionQuestions.get(questionNum).getFractions().get(clicks).getValue())){
                    clicks++;
                    setTxtColor(0,255,0,txtNum2,txtDenom2);
                    removeOnClickListner(v);
                } else {
                    wrong();
                }
            }
            if (v.getId() == clFraction3.getId()){
                if (fractionQuestions.get(questionNum).getFractionThree().getValue().equals(fractionQuestions.get(questionNum).getFractions().get(clicks).getValue())){
                    clicks++;
                    setTxtColor(0,255,0,txtNum3,txtDenom3);
                    removeOnClickListner(v);
                } else {
                    wrong();
                }
            }
            if (clicks>=3){
                correct();
            }
        }
    }
}