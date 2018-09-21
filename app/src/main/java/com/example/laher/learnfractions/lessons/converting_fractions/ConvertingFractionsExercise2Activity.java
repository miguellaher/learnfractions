package com.example.laher.learnfractions.lessons.converting_fractions;

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
import com.example.laher.learnfractions.fraction_util.MixedFraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.ConvertingFractionsQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.ActivityUtil;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class ConvertingFractionsExercise2Activity extends LessonExercise {
    //private static final String TAG = "CF_E2";
    //GUI
    TextView txtWholeNum;
    TextView txtNum1;
    TextView txtNum2;
    TextView txtDenom1;
    TextView txtEquation;
    TextView txtScore;
    TextView txtInstruction;
    EditText inputDenom;
    Button btnCheck;
    ImageView imgLine1;
    ImageView imgLine2;
    GifImageView gifAvatar;
    ConstraintLayout constraintLayoutBackground;
    ConstraintLayout constraintLayoutBottom;
    //EQUATION DIALOG
    Dialog equationDialog;
    View edView;
    TextView diagEdTxtNum1;
    TextView diagEdTxtNum2;
    TextView diagEdTxtSign;
    EditText diagEdInputAnswer;
    Button diagEdBtnCheck;
    //VARIABLES
    ArrayList<ConvertingFractionsQuestion> mConvertingFractionsQuestions;
    ConvertingFractionsQuestion mConvertingFractionsQuestion;
    int mQuestionNum;
    ArrayList<Integer> viewId;
    ColorStateList defaultColor;

    public String title = "Converting Fractions ex.2";
    String id = AppIDs.CONVERTING_EXERCISE2_ID;

    public ConvertingFractionsExercise2Activity() {
        super();
        Range range = getRange();
        Probability probability = new Probability(Probability.P_RAISED_TO_3, range);
        setProbability(probability);
        setId(id);
        setExerciseTitle(title);
    }

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_converting_fractions_exercise2);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);
        Range range = getRange();
        Probability probability = new Probability(Probability.P_RAISED_TO_3, range);
        setProbability(probability);
        //GUI
        txtWholeNum = findViewById(R.id.cvt2_txtWholeNum);
        txtNum1 = findViewById(R.id.cvt2_txtNum);
        txtNum2 = findViewById(R.id.cvt2_txtNum2);
        txtNum2.setTextColor(Color.rgb(0,0,0));
        txtDenom1 = findViewById(R.id.cvt2_txtDenom);
        txtEquation = findViewById(R.id.cvt2_txtEquation);
        txtScore = findViewById(R.id.cvt2_txtScore);
        txtInstruction = findViewById(R.id.cvt2_txtInstruction);
        inputDenom = findViewById(R.id.cvt2_inputDenom);
        inputDenom.setOnEditorActionListener(new InputListener());
        btnCheck = findViewById(R.id.cvt2_btnCheck);
        btnCheck.setOnClickListener(new BtnCheckListener());
        Styles.bgPaintRandomMain(btnCheck);
        imgLine1 = findViewById(R.id.cvt_imgLine1);
        imgLine2 = findViewById(R.id.cvt_imgLine2);
        imgLine1.setImageResource(R.drawable.line);
        imgLine2.setImageResource(R.drawable.line);

        gifAvatar = findViewById(R.id.gifAvatar);
        int gifID = R.drawable.kid_frits;
        gifAvatar.setImageResource(gifID);

        constraintLayoutBackground = findViewById(R.id.constraintLayoutBackground);
        constraintLayoutBackground.setBackgroundResource(R.drawable.playground_background);

        constraintLayoutBottom = findViewById(R.id.constraintLayoutBottom);
        constraintLayoutBottom.setBackgroundResource(R.drawable.playground_bottom);

        int resourceID = R.drawable.playground_toolbar;
        setToolBarBackground(resourceID);

        //EQUATION DIALOG
        edView = getLayoutInflater().inflate(R.layout.layout_dialog_equation, null);
        equationDialog = new Dialog(ConvertingFractionsExercise2Activity.this);
        equationDialog.setOnDismissListener(new EquationDialogListener());
        equationDialog.setOnShowListener(new EquationDialogListener());
        equationDialog.setTitle("Division Equation");
        equationDialog.setContentView(edView);
        diagEdTxtNum1 = edView.findViewById(R.id.md_txtMultiplicand);
        diagEdTxtNum2 = edView.findViewById(R.id.md_txtMultiplier);
        diagEdTxtSign = edView.findViewById(R.id.md_txtSign);
        diagEdInputAnswer = edView.findViewById(R.id.md_inputProduct);
        diagEdInputAnswer.setOnEditorActionListener(new DialogInputListener());
        diagEdBtnCheck = edView.findViewById(R.id.md_btnCheck);
        diagEdBtnCheck.setOnClickListener(new DiagEdBtnCheckListener());

        defaultColor = txtDenom1.getTextColors();
        viewId = new ArrayList<>();

        startExercise();
    }
    public void setQuestions(){
        mQuestionNum = 1;
        mConvertingFractionsQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        for (int i = 0; i < requiredCorrects; i++){
            ConvertingFractionsQuestion convertingFractionsQuestion = new ConvertingFractionsQuestion(ConvertingFractionsQuestion.MIXED_TO_IMPROPER);
            while (mConvertingFractionsQuestions.contains(convertingFractionsQuestion)){
                convertingFractionsQuestion = new ConvertingFractionsQuestion(ConvertingFractionsQuestion.MIXED_TO_IMPROPER);
            }
            mConvertingFractionsQuestions.add(convertingFractionsQuestion);
        }
        setGuiFraction();
    }
    public void setGuiFraction(){
        mConvertingFractionsQuestion = mConvertingFractionsQuestions.get(mQuestionNum -1);
        MixedFraction mixedFraction = mConvertingFractionsQuestion.getMixedFraction();
        int wholeNumber = mixedFraction.getWholeNumber();
        int numerator = mixedFraction.getNumerator();
        int denominator = mixedFraction.getDenominator();
        String strWholeNumber = String.valueOf(wholeNumber);
        String strNumerator = String.valueOf(numerator);
        String strDenominator = String.valueOf(denominator);
        txtWholeNum.setText(strWholeNumber);
        txtNum1.setText(strNumerator);
        txtDenom1.setText(strDenominator);
    }
    @SuppressLint("SetTextI18n")
    public void setUp(){
        txtNum2.setText("");
        setInputEnabled(false);
        btnCheck.setEnabled(false);
        txtEquation.setVisibility(TextView.INVISIBLE);
        setMultiplyTxtListeners(true);
        txtInstruction.setText("Multiply the denominator to the whole number by clicking the denominator first and " +
                "whole number, second.");
        ActivityUtil.playAvatarMediaPlayer(getContext(), R.raw.cf2_multiply);
    }
    public void setMultiplyTxtListeners(boolean bool){
        if (bool){
            txtDenom1.setOnClickListener(new TxtFractionListener());
            txtWholeNum.setOnClickListener(new TxtFractionListener());
        } else {
            txtDenom1.setOnClickListener(null);
            txtWholeNum.setOnClickListener(null);
        }
        txtDenom1.setClickable(bool);
        txtWholeNum.setClickable(bool);
    }
    public void setAddTxtListeners(boolean bool){
        if (bool){
            txtNum1.setOnClickListener(new TxtFractionListener());
            txtEquation.setOnClickListener(new TxtFractionListener());
        } else {
            txtNum1.setOnClickListener(null);
            txtEquation.setOnClickListener(null);
        }
        txtNum1.setClickable(bool);
        txtDenom1.setClickable(bool);
    }
    public void setInputEnabled(boolean bool){
        inputDenom.setEnabled(bool);
    }
    public void popUpMultiplicationDialog(){
        mConvertingFractionsQuestion = mConvertingFractionsQuestions.get(mQuestionNum -1);
        MixedFraction mixedFraction = mConvertingFractionsQuestion.getMixedFraction();
        int wholeNumber = mixedFraction.getWholeNumber();
        int denominator = mixedFraction.getDenominator();
        String strWholeNumber = String.valueOf(wholeNumber);
        String strDenominator = String.valueOf(denominator);
        diagEdTxtNum1.setText(strWholeNumber);
        diagEdTxtNum2.setText(strDenominator);
        diagEdTxtSign.setText("x");
        equationDialog.show();
    }
    public void popUpAddDialog(){
        mConvertingFractionsQuestion = mConvertingFractionsQuestions.get(mQuestionNum -1);
        MixedFraction mixedFraction = mConvertingFractionsQuestion.getMixedFraction();
        int numerator = mixedFraction.getNumerator();
        String strNumerator = String.valueOf(numerator);
        diagEdTxtNum1.setText(String.valueOf(txtEquation.getText()));
        diagEdTxtNum2.setText(strNumerator);
        diagEdTxtSign.setText("+");
        equationDialog.show();
    }
    public void resetTxtColors(){
        txtDenom1.setTextColor(defaultColor);
        txtWholeNum.setTextColor(defaultColor);
        txtNum1.setTextColor(defaultColor);
        txtEquation.setTextColor(defaultColor);
    }
    public class TxtFractionListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            viewId.add(t.getId());
            if (viewId.size()==1){
                if (viewId.get(0)==txtEquation.getId()
                        || viewId.get(0)== txtDenom1.getId()) {
                    Styles.paintGreen(t);
                } else {
                    viewId.clear();
                }
            }
            if (viewId.size()==2){
                if (viewId.get(1)==txtWholeNum.getId()){
                    popUpMultiplicationDialog();
                    Styles.paintGreen(t);
                } else if (viewId.get(1)== txtNum1.getId()){
                    popUpAddDialog();
                    Styles.paintGreen(t);
                } else {
                    viewId.remove(viewId.size()-1);
                }
            }
        }
    }
    public class DiagEdBtnCheckListener implements Button.OnClickListener{
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            mConvertingFractionsQuestion = mConvertingFractionsQuestions.get(mQuestionNum -1);
            MixedFraction mixedFraction = mConvertingFractionsQuestion.getMixedFraction();
            if (!diagEdInputAnswer.getText().toString().trim().matches("")){
                if (String.valueOf(diagEdTxtSign.getText()).equals("+")) {
                    if (Integer.valueOf(String.valueOf(diagEdTxtNum1.getText()))
                            + Integer.valueOf(String.valueOf(diagEdTxtNum2.getText()))
                            == Integer.valueOf(String.valueOf(diagEdInputAnswer.getText().toString().trim()))){
                        int numerator = mixedFraction.getNumerator();
                        String strNumerator = String.valueOf(numerator);
                        String strTxtEquation = String.valueOf(txtEquation.getText());
                        txtEquation.setText(strTxtEquation+" + "
                                + strNumerator
                                + " =");
                        setAddTxtListeners(false);
                        setInputEnabled(true);
                        btnCheck.setEnabled(true);
                        txtNum2.setText(String.valueOf(diagEdInputAnswer.getText().toString().trim()));
                        equationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                resetTxtColors();
                                inputDenom.requestFocus();
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                assert imm != null;
                                if (mQuestionNum>1) {
                                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                                }
                                equationDialog.setOnDismissListener(new EquationDialogListener());
                            }
                        });
                        equationDialog.dismiss();
                        txtInstruction.setText("The new denominator remains the same.");
                        ActivityUtil.playAvatarMediaPlayer(getContext(), R.raw.cf2_the_new);
                    } else {
                        Styles.shakeAnimate(diagEdInputAnswer);
                    }
                } else if (String.valueOf(diagEdTxtSign.getText()).equals("x")){
                    int denominator = mixedFraction.getDenominator();
                    int wholeNumber = mixedFraction.getWholeNumber();
                    String strInputAnswer = diagEdInputAnswer.getText().toString().trim();
                    int intInputAnswer;
                    if (Util.isNumeric(strInputAnswer)) {
                        intInputAnswer = Integer.valueOf(strInputAnswer);
                        if (denominator * wholeNumber == intInputAnswer) {
                            txtEquation.setText(strInputAnswer);
                            txtEquation.setVisibility(TextView.VISIBLE);
                            setMultiplyTxtListeners(false);
                            setAddTxtListeners(true);
                            equationDialog.dismiss();
                            txtInstruction.setText("To get the new numerator, add the product and the numerator by" +
                                    " clicking the product first and numerator, second.");
                            ActivityUtil.playAvatarMediaPlayer(getContext(), R.raw.cf2_to_get);
                        } else {
                            Styles.shakeAnimate(diagEdInputAnswer);
                        }
                    }
                }
            } else {
                Styles.shakeAnimate(diagEdInputAnswer);
            }
        }
    }
    public class EquationDialogListener implements Dialog.OnDismissListener, Dialog.OnShowListener{
        @Override
        public void onDismiss(DialogInterface dialog) {
            resetTxtColors();
        }
        @Override
        public void onShow(DialogInterface dialog) {
            diagEdInputAnswer.setText("");
            viewId.clear();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            if (mQuestionNum>1) {
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
        }
    }
    public class BtnCheckListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            mConvertingFractionsQuestion = mConvertingFractionsQuestions.get(mQuestionNum -1);
            MixedFraction mixedFraction = mConvertingFractionsQuestion.getMixedFraction();
            if (!inputDenom.getText().toString().matches("")){
                String strInputDenominator = inputDenom.getText().toString().trim();
                int intInputDenominator;
                if (Util.isNumeric(strInputDenominator)) {
                    intInputDenominator = Integer.valueOf(strInputDenominator);
                    int denominator = mixedFraction.getDenominator();
                    if (intInputDenominator==denominator) {
                        correct();
                    } else {
                        Styles.shakeAnimate(inputDenom);
                    }
                }
            } else {
                Styles.shakeAnimate(inputDenom);
            }
        }
    }
    private class DialogInputListener implements TextView.OnEditorActionListener{
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){
                diagEdBtnCheck.performClick();
            }
            return false;
        }
    }
    private class InputListener implements TextView.OnEditorActionListener{
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){
                btnCheck.performClick();
            }
            return false;
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
        setQuestions();
        setUp();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        inputDenom.setEnabled(false);
        btnCheck.setEnabled(false);
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
        setGuiFraction();
        setUp();
        inputDenom.setText("");
    }

    @Override
    protected void preFinished() {
        super.preFinished();
        txtInstruction.setText(AppConstants.FINISHED_LESSON);
        ActivityUtil.playAvatarMediaPlayer(getContext(), R.raw.finished_lesson);
    }
}
