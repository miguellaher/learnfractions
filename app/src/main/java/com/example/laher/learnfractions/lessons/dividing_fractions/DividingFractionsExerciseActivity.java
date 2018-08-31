package com.example.laher.learnfractions.lessons.dividing_fractions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.TouchDelegate;
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
import com.example.laher.learnfractions.fraction_util.fraction_questions.DividingFractionsQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import java.util.ArrayList;

public class DividingFractionsExerciseActivity extends LessonExercise {
    //GUI
    TextView txtNum1;
    TextView txtNum2;
    TextView txtNum3;
    TextView txtNum4;
    TextView txtDenom1;
    TextView txtDenom2;
    TextView txtDenom3;
    TextView txtDenom4;
    TextView txtEquation1;
    TextView txtEquation2;
    TextView txtScore;
    TextView txtInstruction;
    TextView txtSign1;
    TextView txtSign2;
    EditText inputNum;
    EditText inputDenom;
    Button btnCheck;
    ImageView imgLine1;
    ImageView imgLine2;
    ImageView imgLine3;
    ImageView imgLine4;
    ImageView imgLine5;
    ImageView imgAvatar;
    //VARIABLES
    ArrayList<DividingFractionsQuestion> mFractionsQuestions;
    DividingFractionsQuestion mFractionsQuestion;
    int mQuestionNum;
    ColorStateList defaultColor;
    TextView txtContainer;

    public String title = "Dividing Fractions ex.1";
    String id = AppIDs.DFE_ID;

    public DividingFractionsExerciseActivity() {
        super();
        Range range = getRange();
        Probability probability = new Probability(Probability.P_RAISED_TO_4, range);
        setProbability(probability);
        setRangeEditable(true);
        setId(id);
        setExerciseTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fraction_dissimilar_equation);
        super.onCreate(savedInstanceState);
        Range range = getRange();
        Probability probability = new Probability(Probability.P_RAISED_TO_4, range);
        setProbability(probability);
        setRangeEditable(true);
        setId(id);
        setExerciseTitle(title);
        //GUI
        txtNum1 = findViewById(R.id.adsm_txtNum1);
        txtNum2 = findViewById(R.id.adsm_txtNum2);
        txtNum3 = findViewById(R.id.adsm_txtNum3);
        txtNum4 = findViewById(R.id.adsm_txtNum4);
        txtDenom1 = findViewById(R.id.adsm_txtDenom1);
        txtDenom2 = findViewById(R.id.adsm_txtDenom2);
        txtDenom3 = findViewById(R.id.adsm_txtDenom3);
        txtDenom4 = findViewById(R.id.adsm_txtDenom4);
        txtEquation1 = findViewById(R.id.adsm_txtEquation1);
        txtEquation2 = findViewById(R.id.adsm_txtEquation2);
        txtEquation1.setVisibility(TextView.INVISIBLE);
        txtEquation2.setVisibility(TextView.INVISIBLE);
        setClickAreas();
        txtScore = findViewById(R.id.adsm_txtScore);
        txtInstruction = findViewById(R.id.adsm_txtInstruction);
        inputNum = findViewById(R.id.adsm_inputNum);
        inputDenom = findViewById(R.id.adsm_inputDenom);
        inputDenom.setOnEditorActionListener(new InputListener());
        btnCheck = findViewById(R.id.adsm_btnCheck);
        txtSign1 = findViewById(R.id.adsm_txtSign1);
        txtSign2 = findViewById(R.id.adsm_txtSign2);
        txtSign1.setText("÷");
        txtSign2.setText("x");
        defaultColor = txtScore.getTextColors();
        btnCheck.setOnClickListener(new BtnCheckListener());
        imgLine1 = findViewById(R.id.adsm_imgLine1);
        imgLine2 = findViewById(R.id.adsm_imgLine2);
        imgLine3 = findViewById(R.id.adsm_imgLine3);
        imgLine4 = findViewById(R.id.adsm_imgLine4);
        imgLine5 = findViewById(R.id.adsm_imgLine5);
        imgAvatar = findViewById(R.id.adsm_imgAvatar);
        imgLine1.setImageResource(R.drawable.line);
        imgLine2.setImageResource(R.drawable.line);
        imgLine3.setImageResource(R.drawable.line);
        imgLine4.setImageResource(R.drawable.line);
        imgLine5.setImageResource(R.drawable.line);
        imgAvatar.setImageResource(R.drawable.avatar);

        startExercise();
    }
    public void nextQuestion(){
        mQuestionNum++;
        setFractionGui();
        setUp();
    }
    public void setFractionQuestions(){
        mQuestionNum = 1;
        mFractionsQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        Range range = getRange();
        for (int i = 0; i < requiredCorrects; i++){
            DividingFractionsQuestion fractionsQuestion = new DividingFractionsQuestion(range);
            while (mFractionsQuestions.contains(fractionsQuestion)){
                fractionsQuestion = new DividingFractionsQuestion(range);
            }
            mFractionsQuestions.add(fractionsQuestion);
        }
    }
    public void setFractionGui(){
        mFractionsQuestion = mFractionsQuestions.get(mQuestionNum-1);
        Fraction fraction1 = mFractionsQuestion.getFraction1();
        Fraction fraction2 = mFractionsQuestion.getFraction2();
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
        txtNum3.setText(strNumerator1);
        txtDenom3.setText(strDenominator1);
    }
    @SuppressLint("SetTextI18n")
    public void setUp(){
        inputNum.setText("");
        inputDenom.setText("");
        txtNum4.setText("    ");
        txtDenom4.setText("    ");
        txtNum4.setBackgroundColor(Color.rgb(255,255,255));
        txtDenom4.setBackgroundColor(Color.rgb(255,255,255));
        setAnswerEnabled(false);
        paintContainer();
        setTxtFractionListener(true);
        txtInstruction.setText("Click the right number to put in the colored space.");
    }
    public void setAnswerEnabled(boolean b){
        inputNum.setEnabled(b);
        inputDenom.setEnabled(b);
        btnCheck.setEnabled(b);
    }
    public void paintContainer(){
        int random = (int) (Math.random() * 2 + 1);
        if (random==1){
            if (txtNum4.getText().toString().trim().matches("")) {
                Styles.bgPaintBurlyWood(txtNum4);
                txtContainer = txtNum4;
            } else if (txtDenom4.getText().toString().trim().matches("")) {
                Styles.bgPaintBurlyWood(txtDenom4);
                txtContainer = txtDenom4;
            }
        } else if (random==2){
            if (txtDenom4.getText().toString().trim().matches("")) {
                Styles.bgPaintBurlyWood(txtDenom4);
                txtContainer = txtDenom4;
            } else if (txtNum4.getText().toString().trim().matches("")) {
                Styles.bgPaintBurlyWood(txtNum4);
                txtContainer = txtNum4;
            }
        }
    }
    public void setTxtFractionListener(boolean b){
        if (b){
            txtNum2.setOnClickListener(new TxtFractionListener());
            txtDenom2.setOnClickListener(new TxtFractionListener());
        } else {
            txtNum2.setOnClickListener(null);
            txtDenom2.setOnClickListener(null);
        }
        txtNum2.setClickable(b);
        txtDenom2.setClickable(b);
    }
    @SuppressLint("SetTextI18n")
    public void readyCheck(){
        try {
            mFractionsQuestion = mFractionsQuestions.get(mQuestionNum-1);
            Fraction fraction2 = mFractionsQuestion.getFraction2();
            int numerator2 = fraction2.getNumerator();
            int denominator2 = fraction2.getDenominator();
            String strTxtNum4 = txtNum4.getText().toString().trim();
            String strTxtDenominator4 = txtDenom4.getText().toString().trim();
            if (Util.isNumeric(strTxtDenominator4) && Util.isNumeric(strTxtNum4)) {
                int intTxtNum4 = Integer.valueOf(strTxtNum4);
                int intTxtDenominator4 = Integer.valueOf(strTxtDenominator4);
                if (intTxtDenominator4==numerator2 && intTxtNum4==denominator2) {
                    setTxtFractionListener(false);
                    inputNum.setEnabled(true);
                    inputDenom.setEnabled(true);
                    btnCheck.setEnabled(true);
                    inputNum.requestFocus();
                    txtInstruction.setText("Solve the equation.");
                    if (mQuestionNum>1) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                    }
                }
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }
    public class TxtFractionListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            mFractionsQuestion = mFractionsQuestions.get(mQuestionNum-1);
            Fraction fraction2 = mFractionsQuestion.getFraction2();
            int numerator2 = fraction2.getNumerator();
            int denominator2 = fraction2.getDenominator();
            String strNumerator2 = String.valueOf(numerator2);
            String strDenominator2 = String.valueOf(denominator2);
            TextView t = (TextView) v;
            if (txtContainer == txtNum4 && t.getId() == txtDenom2.getId()){
                txtNum4.setText(strDenominator2);
                txtNum4.setBackgroundColor(Color.rgb(255,255,255));
                paintContainer();
                readyCheck();
            } else if (txtContainer == txtDenom4 && t.getId() == txtNum2.getId()){
                txtDenom4.setText(strNumerator2);
                txtDenom4.setBackgroundColor(Color.rgb(255,255,255));
                paintContainer();
                readyCheck();
            } else {
                wrong();
            }
        }
    }
    private class BtnCheckListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mFractionsQuestion = mFractionsQuestions.get(mQuestionNum-1);
            Fraction fractionAnswer = mFractionsQuestion.getFractionAnswer();
            int numeratorAnswer = fractionAnswer.getNumerator();
            int denominatorAnswer = fractionAnswer.getDenominator();
            if (!inputNum.getText().toString().matches("")){
                if (!inputDenom.getText().toString().matches("")) {
                    String strInputNumerator = inputNum.getText().toString().trim();
                    String strInputDenominator = inputDenom.getText().toString().trim();
                    if (Util.isNumeric(strInputNumerator) && Util.isNumeric(strInputDenominator)) {
                        int intInputNumerator = Integer.valueOf(strInputNumerator);
                        int intInputDenominator = Integer.valueOf(strInputDenominator);
                        if (intInputNumerator==numeratorAnswer && intInputDenominator==denominatorAnswer) {
                            correct();
                        } else {
                            wrong();
                        }
                    }
                } else {
                    Styles.shakeAnimate(inputDenom);
                }
            } else {
                Styles.shakeAnimate(inputNum);
            }
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
    private void setClickAreas(){
        setLargerClickArea(txtNum2,100,0);
        setLargerClickArea(txtDenom2,0,100);
    }
    private void setLargerClickArea(final TextView textView, final int top, final int bottom){
        final View parent = (View) textView.getParent();  // button: the view you want to enlarge hit area
        parent.post( new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                textView.getHitRect(rect);
                rect.top -= top;    // increase top hit area
                rect.left -= 50;   // increase left hit area
                rect.bottom += bottom; // increase bottom hit area
                rect.right += 50;  // increase right hit area
                parent.setTouchDelegate( new TouchDelegate( rect , textView));
            }
        });
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
        setFractionGui();
        setUp();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        setAnswerEnabled(false);
        setTxtFractionListener(false);
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
        Range range = getRange();
        if (correctsShouldBeConsecutive) {
            setFractionQuestions();
            setFractionGui();
            setUp();
        } else {
            DividingFractionsQuestion fractionsQuestion = new DividingFractionsQuestion(range);
            int questionsSize = mFractionsQuestions.size();
            int maxItemSize = getMaxItemSize();
            while (mFractionsQuestions.contains(fractionsQuestion) && questionsSize<maxItemSize){
                fractionsQuestion = new DividingFractionsQuestion(range);
            }
            mFractionsQuestions.add(fractionsQuestion);
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
