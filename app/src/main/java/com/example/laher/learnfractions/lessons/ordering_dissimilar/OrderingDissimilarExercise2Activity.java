package com.example.laher.learnfractions.lessons.ordering_dissimilar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.OrderingDissimilarQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

public class OrderingDissimilarExercise2Activity extends LessonExercise {
    //private static final String TAG = "OD_E2";
    //EQUATION DIALOG
    Dialog equationDialog;
    View edView;
    TextView diagEdTxtNum1;
    TextView diagEdTxtNum2;
    TextView diagEdTxtSign;
    EditText diagEdInputAnswer;
    Button diagEdBtnCheck;
    //LCM DIALOG
    Dialog lcmDialog;
    View lcmView;
    TextView diagLcmTxtNum1;
    TextView diagLcmTxtNum2;
    TextView diagLcmTxtNum3;
    EditText diagLcmInputLcm;
    Button diagLcmBtnCheck;
    //GUI
    TextView txtNum1;
    TextView txtNum2;
    TextView txtNum3;
    TextView txtNum4;
    TextView txtNum5;
    TextView txtNum6;
    TextView txtDenom1;
    TextView txtDenom2;
    TextView txtDenom3;
    TextView txtDenom4;
    TextView txtDenom5;
    TextView txtDenom6;
    TextView txtScore;
    TextView txtInstruction;
    TextView txtEquation1;
    TextView txtEquation2;
    TextView txtEquation3;
    ConstraintLayout clFraction1;
    ConstraintLayout clFraction2;
    ConstraintLayout clFraction3;
    ConstraintLayout clFraction4;
    ConstraintLayout clFraction5;
    ConstraintLayout clFraction6;
    ColorStateList defaultColor;
    ImageView imgLine1;
    ImageView imgLine2;
    ImageView imgLine3;
    ImageView imgLine4;
    ImageView imgLine5;
    ImageView imgLine6;
    ImageView imgAvatar;
    //VARIABLES
    ArrayList<OrderingDissimilarQuestion> mOrderingDissimilarQuestions;
    OrderingDissimilarQuestion mOrderingDissimilarQuestion;
    int mQuestionNum;
    int clicks;
    TextView standByTxt, standByTxtEquation, standByTxtNum;

    public String title = "Ordering Dissimilar Fractions ex.2";
    String id = AppIDs.ODE2_ID;

    public OrderingDissimilarExercise2Activity() {
        super();
        Range range = getRange();
        Probability probability = new Probability(Probability.ORDERING_DISSIMILAR, range);
        setProbability(probability);
        setId(id);
        setExerciseTitle(title);
    }

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ordering_dissimilar_exercise2);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);
        Range range = getRange();
        Probability probability = new Probability(Probability.ORDERING_DISSIMILAR, range);
        setProbability(probability);
        //EQUATION DIALOG
        edView = getLayoutInflater().inflate(R.layout.layout_dialog_equation, null);
        equationDialog = new Dialog(OrderingDissimilarExercise2Activity.this);
        equationDialog.setOnDismissListener(new EquationDialogListener());
        equationDialog.setOnShowListener(new EquationDialogListener());
        equationDialog.setTitle("Division Equation");
        equationDialog.setContentView(edView);
        diagEdTxtNum1 = edView.findViewById(R.id.md_txtMultiplicand);
        diagEdTxtNum2 = edView.findViewById(R.id.md_txtMultiplier);
        diagEdTxtSign = edView.findViewById(R.id.md_txtSign);
        diagEdInputAnswer = edView.findViewById(R.id.md_inputProduct);
        diagEdInputAnswer.setOnKeyListener(new DiagTxtInputAnswerListener());
        diagEdInputAnswer.setOnEditorActionListener(new DiagTxtInputAnswerListener());
        diagEdBtnCheck = edView.findViewById(R.id.md_btnCheck);
        diagEdBtnCheck.setOnClickListener(new DiagEdBtnCheckListener());
        //LCM DIALOG
        lcmView = getLayoutInflater().inflate(R.layout.layout_dialog_lcm, null);
        lcmDialog = new Dialog(OrderingDissimilarExercise2Activity.this);
        lcmDialog.setOnDismissListener(new LcmDialogListener());
        lcmDialog.setOnShowListener(new LcmDialogListener());
        lcmDialog.setTitle("Getting the LCD");
        lcmDialog.setContentView(lcmView);
        diagLcmTxtNum1 = lcmView.findViewById(R.id.lcm_txtNum1);
        diagLcmTxtNum2 = lcmView.findViewById(R.id.lcm_txtNum2);
        diagLcmTxtNum3 = lcmView.findViewById(R.id.lcm_txtNum3);
        diagLcmInputLcm = lcmView.findViewById(R.id.lcm_inputLcm);
        diagLcmInputLcm.setOnKeyListener(new DiagTxtInputAnswerListener());
        diagLcmInputLcm.setOnEditorActionListener(new DiagTxtInputAnswerListener());
        diagLcmBtnCheck = lcmView.findViewById(R.id.lcm_btnCheck);
        diagLcmBtnCheck.setOnClickListener(new DiagLcmBtnCheckListener());
        //GUI
        txtNum1 = findViewById(R.id.od_txtNum1);
        txtNum2 = findViewById(R.id.od_txtNum2);
        txtNum3 = findViewById(R.id.od_txtNum3);
        txtNum4 = findViewById(R.id.od_txtNum4);
        txtNum5 = findViewById(R.id.od_txtNum5);
        txtNum6 = findViewById(R.id.od_txtNum6);
        txtDenom1 = findViewById(R.id.od_txtDenom1);
        txtDenom2 = findViewById(R.id.od_txtDenom2);
        txtDenom3 = findViewById(R.id.od_txtDenom3);
        txtDenom4 = findViewById(R.id.od_txtDenom4);
        txtDenom5 = findViewById(R.id.od_txtDenom5);
        txtDenom6 = findViewById(R.id.od_txtDenom6);
        txtEquation1 = findViewById(R.id.od_txtEquation1);
        txtEquation2 = findViewById(R.id.od_txtEquation2);
        txtEquation3 = findViewById(R.id.od_txtEquation3);
        txtScore = findViewById(R.id.od_txtScore);
        txtInstruction = findViewById(R.id.od_txtInstruction);
        clFraction1 = findViewById(R.id.od_fraction1);
        clFraction2 = findViewById(R.id.od_fraction2);
        clFraction3 = findViewById(R.id.od_fraction3);
        clFraction4 = findViewById(R.id.od_fraction4);
        clFraction5 = findViewById(R.id.od_fraction5);
        clFraction6 = findViewById(R.id.od_fraction6);
        defaultColor = txtNum1.getTextColors();
        imgLine1 = findViewById(R.id.od_imgLine1);
        imgLine2 = findViewById(R.id.od_imgLine2);
        imgLine3 = findViewById(R.id.od_imgLine3);
        imgLine4 = findViewById(R.id.od_imgLine4);
        imgLine5 = findViewById(R.id.od_imgLine5);
        imgLine6 = findViewById(R.id.od_imgLine6);
        imgAvatar = findViewById(R.id.od_imgAvatar);
        imgLine1.setImageResource(R.drawable.line);
        imgLine2.setImageResource(R.drawable.line);
        imgLine3.setImageResource(R.drawable.line);
        imgLine4.setImageResource(R.drawable.line);
        imgLine5.setImageResource(R.drawable.line);
        imgLine6.setImageResource(R.drawable.line);
        imgAvatar.setImageResource(R.drawable.avatar);

        startExercise();
    }
    public void nextQuestion(){
        resetTextColors();
        resetTextColors2();
        hideGuiFractionSet2();
        hideEquations();
        setGuiFractionSet1();
        setFractionSet1TetDenomLister();
        resetVariables();
    }
    public void startup(){
        setCorrect(0);
        hideGuiFractionSet2();
        hideEquations();
        resetVariables();
        setFractionSet1TetDenomLister();
    }
    public void resetVariables(){
        String instruction = "Click the denominators and get the lcd (least common denominator).";
        txtInstruction.setText(instruction);
        diagEdTxtSign.setText("÷");
        clicks = 0;
    }
    public void setFractionQuestions(){
        mQuestionNum = 1;
        mOrderingDissimilarQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        for (int i = 0; i < requiredCorrects; i++){
            OrderingDissimilarQuestion orderingDissimilarQuestion = new OrderingDissimilarQuestion();
            while (mOrderingDissimilarQuestions.contains(orderingDissimilarQuestion)){
                orderingDissimilarQuestion = new OrderingDissimilarQuestion();
            }
            mOrderingDissimilarQuestions.add(orderingDissimilarQuestion);
        }
        nextQuestion();
    }
    public void setGuiFractionSet1(){
        mOrderingDissimilarQuestion = mOrderingDissimilarQuestions.get(mQuestionNum-1);
        Fraction fraction1 = mOrderingDissimilarQuestion.getFraction1();
        Fraction fraction2 = mOrderingDissimilarQuestion.getFraction2();
        Fraction fraction3 = mOrderingDissimilarQuestion.getFraction3();
        int numerator1 = fraction1.getNumerator();
        int numerator2 = fraction2.getNumerator();
        int numerator3 = fraction3.getNumerator();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        int denominator3 = fraction3.getDenominator();
        txtNum1.setText(String.valueOf(numerator1));
        txtNum2.setText(String.valueOf(numerator2));
        txtNum3.setText(String.valueOf(numerator3));
        txtDenom1.setText(String.valueOf(denominator1));
        txtDenom2.setText(String.valueOf(denominator2));
        txtDenom3.setText(String.valueOf(denominator3));
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
        mOrderingDissimilarQuestion = mOrderingDissimilarQuestions.get(mQuestionNum-1);
        int lcd = mOrderingDissimilarQuestion.getLcd();
        txtDenom4.setText(String.valueOf(lcd));
        txtDenom5.setText(String.valueOf(lcd));
        txtDenom6.setText(String.valueOf(lcd));
    }
    public void hideEquations(){
        txtEquation1.setVisibility(TextView.INVISIBLE);
        txtEquation2.setVisibility(TextView.INVISIBLE);
        txtEquation3.setVisibility(TextView.INVISIBLE);
    }
    public void setFractionSet1TetDenomLister(){
        txtDenom1.setOnClickListener(new TxtDenomListener());
        txtDenom2.setOnClickListener(new TxtDenomListener());
        txtDenom3.setOnClickListener(new TxtDenomListener());
        txtDenom1.setClickable(true);
        txtDenom2.setClickable(true);
        txtDenom3.setClickable(true);
    }
    public void removeFractionSet1TetDenomLister(){
        txtDenom1.setOnClickListener(null);
        txtDenom2.setOnClickListener(null);
        txtDenom3.setOnClickListener(null);
    }
    public void popUpLcmDialog(){
        diagLcmInputLcm.setText("");
        mOrderingDissimilarQuestion = mOrderingDissimilarQuestions.get(mQuestionNum-1);
        Fraction fraction1 = mOrderingDissimilarQuestion.getFraction1();
        Fraction fraction2 = mOrderingDissimilarQuestion.getFraction2();
        Fraction fraction3 = mOrderingDissimilarQuestion.getFraction3();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        int denominator3 = fraction3.getDenominator();
        diagLcmTxtNum1.setText(String.valueOf(denominator1));
        diagLcmTxtNum2.setText(String.valueOf(denominator2));
        diagLcmTxtNum3.setText(String.valueOf(denominator3));
        lcmDialog.show();
        String instruction = "Get the lcd.";
        txtInstruction.setText(instruction);
    }
    public void popUpDivisionDialog(){
        diagEdInputAnswer.setText("");
        mOrderingDissimilarQuestion = mOrderingDissimilarQuestions.get(mQuestionNum-1);
        int lcd = mOrderingDissimilarQuestion.getLcd();
        diagEdTxtNum1.setText(String.valueOf(lcd));
        diagEdTxtNum2.setText(String.valueOf(standByTxt.getText()));
        equationDialog.show();
        String instruction = "Get the quotient.";
        txtInstruction.setText(instruction);
    }
    public void popUpMultiplicationDialog(){
        diagEdInputAnswer.setText("");
        diagEdTxtSign.setText("x");
        diagEdTxtNum1.setText(String.valueOf(standByTxtEquation.getText()));
        diagEdTxtNum2.setText(String.valueOf(standByTxt.getText()));
        equationDialog.show();
        String instruction = "Get the product.";
        txtInstruction.setText(instruction);
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
    public void removeOnClickListner(View v1, View v2){
        v1.setOnClickListener(null);
        v1.setClickable(false);
        v2.setOnClickListener(null);
        v2.setClickable(false);
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
    public void removeFractionsListener(){
        clFraction1.setOnClickListener(null);
        clFraction2.setOnClickListener(null);
        clFraction3.setOnClickListener(null);
        clFraction1.setClickable(false);
        clFraction2.setClickable(false);
        clFraction3.setClickable(false);
    }
    public void removeCorrespondingListener(TextView t){
        if (t.getId()==txtEquation1.getId()){
            removeOnClickListner(txtDenom1,txtDenom4);
        } else if (t.getId()==txtEquation2.getId()) {
            removeOnClickListner(txtDenom2,txtDenom5);
        } else if (t.getId()==txtEquation3.getId()) {
            removeOnClickListner(txtDenom3,txtDenom6);
        }
    }
    public void removeCorrespondingListener2(TextView t){
        if (t.getId()==txtNum4.getId()){
            removeOnClickListner(txtNum1,txtEquation1);
        } else if (t.getId()==txtNum5.getId()) {
            removeOnClickListner(txtNum2,txtEquation2);
        } else if (t.getId()==txtNum6.getId()) {
            removeOnClickListner(txtNum3,txtEquation3);
        }
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
                mOrderingDissimilarQuestion = mOrderingDissimilarQuestions.get(mQuestionNum-1);
                int lcd = mOrderingDissimilarQuestion.getLcd();
                String strLcd = String.valueOf(lcd);
                if (String.valueOf(diagLcmInputLcm.getText()).equals(strLcd)) {
                    setGuiFractionSet2Denominators();
                    showGuiFractionSet2();
                    hideGuiFractionSet2Numerators();
                    removeFractionSet1TetDenomLister();
                    setTxtQuotientListener();
                    String instruction = "Divide the lcd to its' corresponding denominator by clicking the two.";
                    txtInstruction.setText(instruction);

                    lcmDialog.dismiss();
                } else {
                    Styles.shakeAnimate(diagLcmInputLcm);
                    diagLcmInputLcm.setTextColor(Color.rgb(255, 0, 0));
                }
            }
        }
    }
    public class DiagTxtInputAnswerListener implements View.OnKeyListener, TextView.OnEditorActionListener {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            EditText e = (EditText) v;
            e.setTextColor(Color.rgb(0,0,0));
            return false;
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE && v.getId()==diagLcmInputLcm.getId()){
                diagLcmBtnCheck.performClick();
            } else if (actionId== EditorInfo.IME_ACTION_DONE && v.getId()==diagEdInputAnswer.getId()){
                diagEdBtnCheck.performClick();
            }
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
                } else if (t.getId() == txtDenom5.getId()) {
                    standByTxt = txtDenom2;
                    standByTxtEquation = txtEquation2;
                } else if (t.getId() == txtDenom6.getId()) {
                    standByTxt = txtDenom3;
                    standByTxtEquation = txtEquation3;
                } else {
                    clicks = 0;
                    t.setTextColor(defaultColor);
                }
            } else if (clicks==1){
                t.setTextColor(Color.rgb(0,255,0));
                if (t.getId() == standByTxt.getId()){
                    clicks = 0;
                    popUpDivisionDialog();
                } else {
                    t.setTextColor(defaultColor);
                    clicks--;
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
                } else if (t.getId() == txtEquation2.getId()) {
                    standByTxt = txtNum2;
                    standByTxtEquation = txtEquation2;
                    standByTxtNum = txtNum5;
                } else if (t.getId() == txtEquation3.getId()) {
                    standByTxt = txtNum3;
                    standByTxtEquation = txtEquation3;
                    standByTxtNum = txtNum6;
                } else {
                    clicks = 0;
                    t.setTextColor(defaultColor);
                }
            } else if (clicks==1){
                t.setTextColor(Color.rgb(0,255,0));
                if (t.getId() == standByTxt.getId()){
                    clicks = 0;
                    popUpMultiplicationDialog();
                } else {
                    t.setTextColor(defaultColor);
                }
            }
        }
    }
    public class DiagEdBtnCheckListener implements View.OnClickListener {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            if (!diagEdInputAnswer.getText().toString().matches("")) {
                if (diagEdTxtSign.getText() == "÷") {
                    if (Integer.valueOf(String.valueOf(diagEdTxtNum1.getText())) / Integer.valueOf(String.valueOf(diagEdTxtNum2.getText()))
                            == Integer.valueOf(String.valueOf(diagEdInputAnswer.getText()))) {
                        standByTxtEquation.setText(String.valueOf(diagEdInputAnswer.getText()));
                        showTxtView(standByTxtEquation);
                        removeCorrespondingListener(standByTxtEquation);
                        String instruction = "Click another two.";
                        txtInstruction.setText(instruction);
                        equationDialog.dismiss();
                    } else {
                        Styles.shakeAnimate(diagEdInputAnswer);
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
                        removeCorrespondingListener2(standByTxtNum);
                        txtInstruction.setText("Click another two.");
                        equationDialog.dismiss();
                    } else {
                        Styles.shakeAnimate(diagEdInputAnswer);
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
            mOrderingDissimilarQuestion = mOrderingDissimilarQuestions.get(mQuestionNum-1);
            Fraction fraction1 = mOrderingDissimilarQuestion.getFraction1();
            Fraction fraction2 = mOrderingDissimilarQuestion.getFraction2();
            Fraction fraction3 = mOrderingDissimilarQuestion.getFraction3();
            ArrayList<Fraction> sortedFractions = mOrderingDissimilarQuestion.getSortedFractions();
            Fraction correctFraction = sortedFractions.get(clicks);
            if (v.getId() == clFraction1.getId()){
                if (fraction1.equals(correctFraction)){
                    clicks++;
                    setTxtColor(0,255,0,txtNum1,txtDenom1);
                    removeOnClickListner(v);
                } else {
                    setTxtColor(255,0,0,txtNum1,txtDenom1);
                    wrong();
                }
            }
            if (v.getId() == clFraction2.getId()){
                if (fraction2.equals(correctFraction)){
                    clicks++;
                    setTxtColor(0,255,0,txtNum2,txtDenom2);
                    removeOnClickListner(v);
                } else {
                    setTxtColor(255,0,0,txtNum2,txtDenom2);
                    wrong();
                }
            }
            if (v.getId() == clFraction3.getId()){
                if (fraction3.equals(correctFraction)){
                    clicks++;
                    setTxtColor(0,255,0,txtNum3,txtDenom3);
                    removeOnClickListner(v);
                } else {
                    setTxtColor(255,0,0,txtNum3,txtDenom3);
                    wrong();
                }
            }
            if (clicks>=3){
                correct();
            }
        }
    }
    public class LcmDialogListener implements Dialog.OnDismissListener, DialogInterface.OnShowListener{
        @Override
        public void onDismiss(DialogInterface dialog) {
            resetTextColors();
        }

        @Override
        public void onShow(DialogInterface dialog) {
            diagLcmInputLcm.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
        }
    }
    public class EquationDialogListener implements Dialog.OnDismissListener, DialogInterface.OnShowListener{
        @Override
        public void onDismiss(DialogInterface dialog) {
            resetTextColors();
            resetTextColors2();
        }

        @Override
        public void onShow(DialogInterface dialog) {
            diagEdInputAnswer.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
        }
    }
    @Override
    public void showScore(){
        super.showScore();
        int correct = getCorrect();
        int requiredCorrects = getItemsSize();
        txtScore.setText(AppConstants.SCORE(correct,requiredCorrects));
    }

    @Override
    protected void startExercise() {
        super.startExercise();
        setFractionQuestions();
        setGuiFractionSet1();
        startup();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        removeFractionsListener();
    }

    @Override
    protected void postAnswered() {
        super.postAnswered();
    }

    @Override
    protected void preCorrect() {
        super.preCorrect();
        txtInstruction.setText(AppConstants.CORRECT);
    }

    @Override
    protected void postCorrect() {
        super.postCorrect();
        mQuestionNum++;
        nextQuestion();
    }

    @Override
    protected void preFinished() {
        super.preFinished();
        txtInstruction.setText(AppConstants.FINISHED_EXERCISE);
    }

    @Override
    protected void preWrong() {
        super.preWrong();
        txtInstruction.setText(AppConstants.ERROR);
    }

    @Override
    protected void postWrong() {
        super.postWrong();
        boolean correctsShouldBeConsecutive = isCorrectsShouldBeConsecutive();
        if (correctsShouldBeConsecutive) {
            setFractionQuestions();
            setGuiFractionSet1();
            startup();
        } else {
            OrderingDissimilarQuestion orderingDissimilarQuestion = new OrderingDissimilarQuestion();
            int questionsSize = mOrderingDissimilarQuestions.size();
            int maxItemSize = getMaxItemSize();
            while (mOrderingDissimilarQuestions.contains(orderingDissimilarQuestion) && questionsSize<maxItemSize){
                orderingDissimilarQuestion = new OrderingDissimilarQuestion();
            }
            mOrderingDissimilarQuestions.add(orderingDissimilarQuestion);
            mQuestionNum++;
            nextQuestion();
        }
    }

    @Override
    protected void preFailWrongsAreConsecutive() {
        super.preFailWrongsAreConsecutive();
        txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(getWrong()));
    }

    @Override
    protected void preFailWrongsAreNotConsecutive() {
        super.preFailWrongsAreNotConsecutive();
        txtInstruction.setText(AppConstants.FAILED(getWrong()));
    }
}