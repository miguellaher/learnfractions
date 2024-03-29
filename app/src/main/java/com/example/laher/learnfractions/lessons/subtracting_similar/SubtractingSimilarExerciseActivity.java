package com.example.laher.learnfractions.lessons.subtracting_similar;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.laher.learnfractions.fraction_util.fraction_questions.SubtractingSimilarFractionsQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.ActivityUtil;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class SubtractingSimilarExerciseActivity extends LessonExercise {
    //private static final String TAG = "SS_E1";
    //FRACTION EQUATION GUI
    TextView txtNum1;
    TextView txtNum2;
    TextView txtDenom1;
    TextView txtDenom2;
    TextView txtSign;
    TextView txtScore;
    TextView txtInstruction;
    EditText inputNum;
    EditText inputDenom;
    Button btnCheck;
    ConstraintLayout clChoices;
    ImageView imgLine1;
    ImageView imgLine2;
    ImageView imgLine3;
    GifImageView gifAvatar;
    ConstraintLayout constraintLayoutBackground;
    ConstraintLayout constraintLayoutBottom;
    //VARIABLES
    ArrayList<SubtractingSimilarFractionsQuestion> mFractionQuestions;
    SubtractingSimilarFractionsQuestion mFractionQuestion;
    int mQuestionNum;

    public String title = "Subtracting Similar Fractions ex.1";
    String id = AppIDs.SSE_ID;

    public SubtractingSimilarExerciseActivity() {
        super();
        Range range = getRange();
        Probability probability = new Probability(Probability.SUBTRACTING_2SIMILAR_F, range);
        setProbability(probability);
        setRangeEditable(true);
        setId(id);
        setExerciseTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fraction_equation);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);
        Range range = getRange();
        Probability probability = new Probability(Probability.SUBTRACTING_2SIMILAR_F, range);
        setProbability(probability);
        setRangeEditable(true);
        //FRACTION EQUATION GUI
        txtNum1 = findViewById(R.id.fe_txtNum1);
        txtNum2 = findViewById(R.id.fe_txtNum2);
        txtDenom1 = findViewById(R.id.fe_txtDenom1);
        txtDenom2 = findViewById(R.id.fe_txtDenom2);
        txtSign = findViewById(R.id.fe_txtSign);
        txtSign.setText(" - ");
        txtScore = findViewById(R.id.fe_txtScore);
        txtInstruction = findViewById(R.id.fe_txtInstruction);
        Styles.paintWhite(txtInstruction); // SPECIAL CASE - contrast is the purpose
        inputNum = findViewById(R.id.fe_inputNum);
        inputDenom = findViewById(R.id.fe_inputDenom);
        inputDenom.setOnEditorActionListener(new InputListener());
        btnCheck = findViewById(R.id.fe_btnCheck);
        btnCheck.setOnClickListener(new BtnCheckListener());
        Styles.bgPaintRandomMain(btnCheck);
        clChoices = findViewById(R.id.fe_clChoices);
        clChoices.setVisibility(View.INVISIBLE);
        imgLine1 = findViewById(R.id.fe_imgLine1);
        imgLine2 = findViewById(R.id.fe_imgLine2);
        imgLine3 = findViewById(R.id.fe_imgLine3);
        imgLine1.setImageResource(R.drawable.line);
        imgLine2.setImageResource(R.drawable.line);
        imgLine3.setImageResource(R.drawable.line);

        gifAvatar = findViewById(R.id.gifAvatar);
        int gifID = R.drawable.safari_frits;
        gifAvatar.setImageResource(gifID);

        constraintLayoutBackground = findViewById(R.id.constraintLayoutBackground);
        constraintLayoutBackground.setBackgroundResource(R.drawable.forrest_background);

        constraintLayoutBottom = findViewById(R.id.constraintLayoutBottom);
        constraintLayoutBottom.setBackgroundResource(R.drawable.forrest_bottom);

        int resourceID = R.drawable.forrest_toolbar;
        setToolBarBackground(resourceID);

        startExercise();
    }
    public void go(){
        setQuestions();
        startUp();
    }
    public void nextQuestion(){
        mQuestionNum++;
        setGuiFractions();
        clearInputFraction();
        enableInputFraction();
        btnCheck.setEnabled(true);
        if (mQuestionNum>1) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
        }
    }
    @SuppressLint("SetTextI18n")
    public void startUp(){
        txtInstruction.setText("Subtract the two numerators and the new denominator is the same " +
                "with the similar denominators.");
        ActivityUtil.playAvatarMediaPlayer(getContext(), R.raw.ss_subtract);
        clearInputFraction();
    }
    public void clearInputFraction(){
        inputNum.setText("");
        inputDenom.setText("");
        inputNum.requestFocus();
    }
    public void setQuestions(){
        mQuestionNum = 1;
        mFractionQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        Range range = getRange();
        for(int i = 0; i < requiredCorrects; i++){
            SubtractingSimilarFractionsQuestion fractionsQuestion = new SubtractingSimilarFractionsQuestion(range);
            while (mFractionQuestions.contains(fractionsQuestion)){
                fractionsQuestion = new SubtractingSimilarFractionsQuestion(range);
            }
            mFractionQuestions.add(fractionsQuestion);
        }
        setGuiFractions();
    }
    @SuppressLint("SetTextI18n")
    public void setGuiFractions(){
        mFractionQuestion = mFractionQuestions.get(mQuestionNum-1);
        Fraction fraction1 = mFractionQuestion.getFraction1();
        Fraction fraction2 = mFractionQuestion.getFraction2();
        int numerator1 = fraction1.getNumerator();
        int numerator2 = fraction2.getNumerator();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        String strNumerator1 = String.valueOf(numerator1);
        String strNumerator2 = String.valueOf(numerator2);
        String strDenominator1 = String.valueOf(denominator1);
        String strDenominator2 = String.valueOf(denominator2);
        txtInstruction.setText("Solve the equation.");
        ActivityUtil.playAvatarMediaPlayer(getContext(), R.raw.solve_the_equation);
        txtNum1.setText(strNumerator1);
        txtNum2.setText(strNumerator2);
        txtDenom1.setText(strDenominator1);
        txtDenom2.setText(strDenominator2);
    }
    public void shakeAnimate(View view){
        ObjectAnimator.ofFloat(view, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                .setDuration(1000)
                .start();
    }
    public void shakeInputFraction(){
        shakeAnimate(inputNum);
        shakeAnimate(inputDenom);
    }
    public void disableInputFraction(){
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
    }
    public void enableInputFraction(){
        inputNum.setEnabled(true);
        inputDenom.setEnabled(true);
    }
    public class BtnCheckListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            mFractionQuestion = mFractionQuestions.get(mQuestionNum-1);
            Fraction fractionAnswer = mFractionQuestion.getFractionAnswer();
            int numeratorAnswer = fractionAnswer.getNumerator();
            int denominatorAnswer = fractionAnswer.getDenominator();
            if (inputNum.getText().toString().trim().length() != 0 &&
                    inputDenom.getText().toString().trim().length() != 0 ) {
                String strInputNum = inputNum.getText().toString().trim();
                String strInputDenominator = inputDenom.getText().toString().trim();
                int intInputNumerator = Integer.valueOf(strInputNum);
                int intInputDenominator = Integer.valueOf(strInputDenominator);
                if (intInputNumerator==numeratorAnswer && intInputDenominator==denominatorAnswer) {
                    correct();
                } else {
                    shakeInputFraction();
                    wrong();
                }
            } else {
                shakeInputFraction();
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
        startUp();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        disableInputFraction();
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
        nextQuestion();
    }

    @Override
    protected void preFinished() {
        super.preFinished();
        txtInstruction.setText(AppConstants.FINISHED_LESSON);
        ActivityUtil.playAvatarMediaPlayer(getContext(), R.raw.finished_lesson);
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
            enableInputFraction();
            btnCheck.setEnabled(true);
            setQuestions();
            startUp();
        } else {
            SubtractingSimilarFractionsQuestion fractionsQuestion = new SubtractingSimilarFractionsQuestion(range);
            int questionsSize = mFractionQuestions.size();
            int maxItemSize = getMaxItemSize();
            while (mFractionQuestions.contains(fractionsQuestion) && questionsSize<maxItemSize){
                fractionsQuestion = new SubtractingSimilarFractionsQuestion(range);
            }
            mFractionQuestions.add(fractionsQuestion);
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
