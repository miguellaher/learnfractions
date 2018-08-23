package com.example.laher.learnfractions.lessons.converting_fractions;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.MixedFraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.ConvertingFractionsQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;

import java.util.ArrayList;

public class ConvertingFractionsExerciseActivity extends LessonExercise {
    //private static final String TAG = "CF_E1";
    //GUI
    TextView txtNum1;
    TextView txtDenom1;
    TextView txtScore;
    TextView txtInstruction;
    TextView txtQuotient;
    TextView txtRemainder;
    TextView txtR;
    EditText inputNum;
    EditText inputDenom;
    EditText inputWholeNum;
    Button btnCheck;
    ImageView imgLine1;
    ImageView imgLine2;
    ImageView imgAvatar;
    //DIVISION DIALOG
    Dialog divisionDialog;
    View ddView;
    TextView diagDdTxtNum1;
    TextView diagDdTxtNum2;
    TextView diagDdTxtSign;
    EditText diagDdInputAnswer;
    EditText diagDdInputRemainder;
    Button diagDdBtnCheck;
    //VARIABLES
    ArrayList<ConvertingFractionsQuestion> mConvertingFractionsQuestions;
    ConvertingFractionsQuestion mConvertingFractionsQuestion;
    int mQuestionNum;
    ColorStateList defaultColor;
    int clicks;

    public String title = "Converting Fractions ex.1";
    String id = AppIDs.CONVERTING_EXERCISE_ID;

    public ConvertingFractionsExerciseActivity() {
        super();
        setId(id);
        setExerciseTitle(title);
    }

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_converting_fractions_exercise);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);
        //GUI
        txtNum1 = findViewById(R.id.cvt_txtNum1);
        txtDenom1 = findViewById(R.id.cvt_txtDenom1);
        txtScore = findViewById(R.id.cvt_txtScore);
        txtInstruction = findViewById(R.id.cvt_txtInstruction);
        txtQuotient = findViewById(R.id.cvt_txtQuotient);
        txtRemainder = findViewById(R.id.cvt_txtRemainder);
        txtR = findViewById(R.id.cvt_txtR);
        inputNum = findViewById(R.id.cvt_inputNum);
        inputDenom = findViewById(R.id.cvt_inputDenom);
        inputDenom.setOnEditorActionListener(new InputListener());
        inputWholeNum = findViewById(R.id.cvt_inputWholeNum);
        btnCheck = findViewById(R.id.cvt_btnCheck);
        btnCheck.setOnClickListener(new BtnCheckListener());
        imgLine1 = findViewById(R.id.cvt_imgLine1);
        imgLine2 = findViewById(R.id.cvt_imgLine2);
        imgAvatar = findViewById(R.id.cvt_imgAvatar);
        imgLine1.setImageResource(R.drawable.line);
        imgLine2.setImageResource(R.drawable.line);
        imgAvatar.setImageResource(R.drawable.avatar);
        //DIVISION DIALOG
        ddView = getLayoutInflater().inflate(R.layout.layout_dialog_division, null);
        divisionDialog = new Dialog(ConvertingFractionsExerciseActivity.this);
        divisionDialog.setOnDismissListener(new DivisionDialogListener());
        divisionDialog.setOnShowListener(new DivisionDialogListener());
        divisionDialog.setTitle("Division Equation");
        divisionDialog.setContentView(ddView);
        diagDdTxtNum1 = ddView.findViewById(R.id.dd_txtDividend);
        diagDdTxtNum2 = ddView.findViewById(R.id.dd_txtDivisor);
        diagDdTxtSign = ddView.findViewById(R.id.dd_txtSign);
        diagDdInputAnswer = ddView.findViewById(R.id.dd_inputProduct);
        diagDdInputRemainder = ddView.findViewById(R.id.dd_inputRemainder);
        diagDdInputAnswer.setOnKeyListener(new DiagInputListener());
        diagDdInputRemainder.setOnKeyListener(new DiagInputListener());
        diagDdInputRemainder.setOnEditorActionListener(new DiagInputListener());
        //diagEdInputAnswer.setOnKeyListener(new DiagTxtInputAnswerListener());
        diagDdBtnCheck = ddView.findViewById(R.id.dd_btnCheck);
        diagDdBtnCheck.setOnClickListener(new DiagDdBtnCheckListener());

        defaultColor = txtDenom1.getTextColors();

        startExercise();
    }
    private void setFractionQuestions(){
        mQuestionNum = 1;
        mConvertingFractionsQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        for (int i = 0; i < requiredCorrects; i++){
            ConvertingFractionsQuestion convertingFractionsQuestion = new ConvertingFractionsQuestion(ConvertingFractionsQuestion.IMPROPER_TO_MIXED);
            while (mConvertingFractionsQuestions.contains(convertingFractionsQuestion)){
                convertingFractionsQuestion = new ConvertingFractionsQuestion(ConvertingFractionsQuestion.IMPROPER_TO_MIXED);
            }
            mConvertingFractionsQuestions.add(convertingFractionsQuestion);
        }
        setGuiFraction();
    }
    public void setGuiFraction(){
        mConvertingFractionsQuestion = mConvertingFractionsQuestions.get(mQuestionNum-1);
        Fraction fraction = mConvertingFractionsQuestion.getFraction();
        int numerator = fraction.getNumerator();
        int denominator = fraction.getDenominator();
        String strNumerator = String.valueOf(numerator);
        String strDenominator = String.valueOf(denominator);
        txtNum1.setText(strNumerator);
        txtDenom1.setText(strDenominator);
    }
    @SuppressLint("SetTextI18n")
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
    @SuppressLint("SetTextI18n")
    public void popUpDivisionDialog(){
        mConvertingFractionsQuestion = mConvertingFractionsQuestions.get(mQuestionNum-1);
        Fraction fraction = mConvertingFractionsQuestion.getFraction();
        int numerator = fraction.getNumerator();
        int denominator = fraction.getDenominator();
        String strNumerator = String.valueOf(numerator);
        String strDenominator = String.valueOf(denominator);
        diagDdTxtNum1.setText(strNumerator);
        diagDdTxtNum2.setText(strDenominator);
        divisionDialog.show();
        diagDdInputAnswer.requestFocus();
        txtInstruction.setText("Get the quotient and remainder.");
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
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            mConvertingFractionsQuestion = mConvertingFractionsQuestions.get(mQuestionNum-1);
            MixedFraction mixedFraction = mConvertingFractionsQuestion.getMixedFraction();
            int wholeNumber = mixedFraction.getWholeNumber();
            int numerator = mixedFraction.getNumerator();
            if (!diagDdInputAnswer.getText().toString().matches("") &&
                    !diagDdInputRemainder.getText().toString().matches("")){
                int quotient = Integer.valueOf(String.valueOf(diagDdInputAnswer.getText()));
                try {
                    int remainder = Integer.valueOf(String.valueOf(diagDdInputRemainder.getText()));
                    if (quotient != wholeNumber) {
                        shakeAnimate(diagDdInputAnswer);
                    }
                    if (remainder != numerator) {
                        shakeAnimate(diagDdInputRemainder);
                    }
                    if (quotient == wholeNumber && remainder == numerator) {
                        txtQuotient.setText(String.valueOf(quotient));
                        txtRemainder.setText(String.valueOf(remainder));
                        setAnswerVisibility(true);
                        setInputEnabled(true);
                        inputWholeNum.requestFocus();
                        btnCheck.setEnabled(true);
                        setFractionTxtListener(false);
                        divisionDialog.dismiss();
                        txtInstruction.setText("The quotient will be the whole number. The remainder will be the numerator. " +
                                "The denominator remains the same.");
                    }
                } catch (Exception e) {e.printStackTrace();}
            } else {
                shakeAnimate(diagDdInputAnswer);
                shakeAnimate(diagDdInputRemainder);
            }
        }
    }
    public class BtnCheckListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            mConvertingFractionsQuestion = mConvertingFractionsQuestions.get(mQuestionNum-1);
            MixedFraction mixedFraction = mConvertingFractionsQuestion.getMixedFraction();
            int wholeNumber = mixedFraction.getWholeNumber();
            int numerator = mixedFraction.getNumerator();
            int denominator = mixedFraction.getDenominator();
            if (!inputNum.getText().toString().matches("") && !inputDenom.getText().toString().matches("")
                    && !inputWholeNum.getText().toString().matches("")){
                int wholeNum = Integer.valueOf(String.valueOf(inputWholeNum.getText()));
                int num = Integer.valueOf(String.valueOf(inputNum.getText()));
                int denom = Integer.valueOf(String.valueOf(inputDenom.getText()));
                if (wholeNum != wholeNumber){
                    shakeAnimate(inputWholeNum);
                }
                if (num != numerator){
                    shakeAnimate(inputNum);
                }
                if (denom != denominator){
                    shakeAnimate(inputDenom);
                }
                if (wholeNum == wholeNumber &&
                        num == numerator &&
                        denom == denominator) {
                    correct();
                }
            } else {
                shakeAnimate(inputWholeNum);
                shakeAnimate(inputNum);
                shakeAnimate(inputDenom);
            }
        }
    }
    public class DivisionDialogListener implements Dialog.OnDismissListener, DialogInterface.OnShowListener{
        @Override
        public void onDismiss(DialogInterface dialog) {
            txtNum1.setTextColor(defaultColor);
            txtDenom1.setTextColor(defaultColor);
            if ((txtQuotient.getVisibility()==View.VISIBLE && txtRemainder.getVisibility()==View.VISIBLE)&&mQuestionNum>1) {
                inputWholeNum.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
        }

        @Override
        public void onShow(DialogInterface dialog) {
            diagDdInputAnswer.setText("");
            diagDdInputRemainder.setText("");
            diagDdInputAnswer.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
        }
    }
    public class DiagInputListener implements EditText.OnKeyListener, TextView.OnEditorActionListener{
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            TextView t = (TextView) v;
            t.setTextColor(Color.rgb(0,0,0));
            return false;
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){
                diagDdBtnCheck.performClick();
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
        setFractionQuestions();
        setUp();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        setInputEnabled(false);
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
    }

    @Override
    protected void preFinished() {
        super.preFinished();
        txtInstruction.setText(AppConstants.FINISHED_EXERCISE);
    }
}
