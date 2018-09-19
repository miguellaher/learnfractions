package com.example.laher.learnfractions.lessons.fraction_meaning;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.fraction_questions.FractionMeaningQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.ActivityUtil;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class FractionMeaningExercise2Activity extends LessonExercise {
    private static final String TAG = "FM_E2";

    ImageView imgBox1;
    ImageView imgBox2;
    ImageView imgBox3;
    ImageView imgBox4;
    ImageView imgBox5;
    ImageView imgBox6;
    ImageView imgBox7;
    ImageView imgBox8;
    ImageView imgBox9;
    ImageView imgLine;
    GifImageView gifAvatar;
    EditText inputNumerator;
    EditText inputDenominator;
    TextView txtScore;
    TextView txtInstruction;
    ConstraintLayout constraintLayoutBackGround;
    ConstraintLayout constraintLayoutBottom;

    public String title = "Fraction Meaning\nex.2";
    String id = AppIDs.FME2_ID;

    Button btnOK;
    ConstraintLayout clChoices;
    int num, denominator;

    ArrayList<FractionMeaningQuestion> mFractionMeaningQuestions;
    int mQuestionNum;

    public FractionMeaningExercise2Activity() {
        super();
        Range range = getRange();
        Probability probability = new Probability(Probability.SUMMATION_NOTATION_1, range);
        setProbability(probability);
        setId(id);
        setExerciseTitle(title);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() 1st statement");
        setContentView(R.layout.activity_fraction_meaning_exercise2);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);
        Range range = getRange();
        Probability probability = new Probability(Probability.SUMMATION_NOTATION_1, range);
        setProbability(probability);
        clChoices = findViewById(R.id.a1_clChoices);
        clChoices.setVisibility(View.INVISIBLE);
        imgBox1 = findViewById(R.id.a1_imgBox1);
        imgBox2 = findViewById(R.id.a1_imgBox2);
        imgBox3 = findViewById(R.id.a1_imgBox3);
        imgBox4 = findViewById(R.id.a1_imgBox4);
        imgBox5 = findViewById(R.id.a1_imgBox5);
        imgBox6 = findViewById(R.id.a1_imgBox6);
        imgBox7 = findViewById(R.id.a1_imgBox7);
        imgBox8 = findViewById(R.id.a1_imgBox8);
        imgBox9 = findViewById(R.id.a1_imgBox9);
        imgLine = findViewById(R.id.fme2_imgLine);
        gifAvatar = findViewById(R.id.gifAvatar);
        int gifID = R.drawable.gentle_frits;
        gifAvatar.setImageResource(gifID);
        imgLine.setImageResource(R.drawable.line);

        constraintLayoutBackGround = findViewById(R.id.constraintLayoutBackground);
        constraintLayoutBackGround.setBackgroundResource(R.drawable.factory_background);

        constraintLayoutBottom = findViewById(R.id.constraintLayoutBottom);
        constraintLayoutBottom.setBackgroundResource(R.drawable.factory_bottom);

        int resourceID = R.drawable.factory_toolbar;
        setToolBarBackground(resourceID);

        inputNumerator = findViewById(R.id.fme2_inputNumerator);
        inputDenominator = findViewById(R.id.fme2_inputDenominator);
        inputNumerator.setVisibility(View.VISIBLE);
        inputNumerator.setEnabled(true);
        inputDenominator.setEnabled(true);
        inputDenominator.setVisibility(View.VISIBLE);
        inputNumerator.setOnFocusChangeListener(new InputListener());
        inputNumerator.setOnEditorActionListener(new InputListener());
        inputNumerator.addTextChangedListener(new InputListener());
        inputDenominator.setOnFocusChangeListener(new InputListener());
        inputDenominator.setOnEditorActionListener(new InputListener());
        inputDenominator.addTextChangedListener(new InputListener());
        int maxInputLength = 3;
        inputNumerator.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(maxInputLength)
        });
        inputDenominator.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(maxInputLength)
        });

        btnOK = findViewById(R.id.a1_btnOk);
        btnOK.setOnClickListener(new BtnOkListener());
        btnOK.setEnabled(false);

        Styles.bgPaintRandomMain(btnOK);

        txtScore = findViewById(R.id.a1_txtScore);
        txtInstruction = findViewById(R.id.a1_txtInstruction);

        Log.d(TAG, "startExercise()");
        startExercise();
    }

    public void nextQuestion(){
        setBoxes();
        inputNumerator.setEnabled(true);
        inputDenominator.setEnabled(true);
        inputNumerator.setText("");
        inputDenominator.setText("");
        String instruction = "Fill in the blanks.";
        txtInstruction.setText(instruction);
        ActivityUtil.playMusic(getContext(),R.raw.fill_in_the_blanks);
        btnOK.setEnabled(false);
        inputNumerator.requestFocus();
        Log.d(TAG, "nextQuestion() last statement");
    }
    public void generateFractionQuestions(){
        Log.d(TAG, "generateFractionQuestions()");
        mFractionMeaningQuestions = new ArrayList<>();
        mQuestionNum = 1;
        int itemsSize = getItemsSize();
        for (int i = 0; i < itemsSize; i++){
            FractionMeaningQuestion fractionMeaningQuestion = new FractionMeaningQuestion();
            while (mFractionMeaningQuestions.contains(fractionMeaningQuestion)){
                fractionMeaningQuestion = new FractionMeaningQuestion();
            }
            mFractionMeaningQuestions.add(fractionMeaningQuestion);
        }
        for (FractionMeaningQuestion fractionMeaningQuestion : mFractionMeaningQuestions) {
            Log.d(TAG, "num: " + fractionMeaningQuestion.getNumeratorAnswer());
            Log.d(TAG, "denominator: " + fractionMeaningQuestion.getDenominatorAnswer());
            Log.d(TAG, "------------------------------------");
        }
        Log.d(TAG, "size: " + mFractionMeaningQuestions.size());
    }
    public void setBoxes(){
        num = mFractionMeaningQuestions.get(mQuestionNum-1).getNumerator();
        denominator = mFractionMeaningQuestions.get(mQuestionNum-1).getDenominator();
        int prod = denominator - num;
        ArrayList<Integer> imageList = new ArrayList<>();
        for (int i = 1; i <= num; i++){
            imageList.add(R.drawable.chocolate);
        }
        for (int i = 1; i <= prod; i++){
            imageList.add(R.drawable.chocosmudge);
        }
        for (int i = imageList.size(); i <= 9; i++){
            imageList.add(0);
        }
        imgBox1.setImageResource(imageList.get(0));
        imgBox2.setImageResource(imageList.get(1));
        imgBox3.setImageResource(imageList.get(2));
        imgBox4.setImageResource(imageList.get(3));
        imgBox5.setImageResource(imageList.get(4));
        imgBox6.setImageResource(imageList.get(5));
        imgBox7.setImageResource(imageList.get(6));
        imgBox8.setImageResource(imageList.get(7));
        imgBox9.setImageResource(imageList.get(8));
    }
    public void reset(){
        nextQuestion();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
    }
    public class BtnOkListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            try {
                if ((Integer.parseInt(String.valueOf(inputNumerator.getText())) == num) &&
                        (Integer.parseInt(String.valueOf(inputDenominator.getText())) == denominator)) {
                    correct();
                } else {
                    wrong();
                }
            } catch (Exception e) {
                Styles.shakeAnimate(inputNumerator);
                Styles.shakeAnimate(inputDenominator);
                e.printStackTrace();
            }
        }
    }


    public class InputListener implements View.OnFocusChangeListener, TextView.OnEditorActionListener, TextWatcher{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus){
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){
                btnOK.performClick();
            }
            return false;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if ((inputNumerator.getText().toString().trim().length() == 0)
                    || (inputDenominator.getText().toString().trim().length() == 0)){
                btnOK.setEnabled(false);
            } else {
                //txtInstruction.setText(inputNumerator.getText().toString().trim().length() + "/" + inputDenominator.getText().toString().trim().length());
                btnOK.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @Override
    public void showScore(){
        super.showScore();
        int correct = getCorrect();
        int itemsSize = getItemsSize();
        txtScore.setText(AppConstants.SCORE(correct,itemsSize));
    }

    @Override
    protected void startExercise() {
        super.startExercise();
        getButtonNext().setText(AppConstants.DONE);
        generateFractionQuestions();
        nextQuestion();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        inputNumerator.setEnabled(false);
        inputDenominator.setEnabled(false);
        btnOK.setEnabled(false);
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
        reset();
    }

    @Override
    protected void preFinished() {
        super.preFinished();
        txtInstruction.setText(AppConstants.FINISHED_LESSON);
        ActivityUtil.playMusic(getContext(),R.raw.finished_lesson);
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
            getButtonNext().setText(AppConstants.DONE);
            generateFractionQuestions();
            nextQuestion();
        } else {
            FractionMeaningQuestion fractionMeaningQuestion = new FractionMeaningQuestion();
            int questionsSize = mFractionMeaningQuestions.size();
            int maxItemSize = getMaxItemSize();
            while (mFractionMeaningQuestions.contains(fractionMeaningQuestion) && questionsSize<maxItemSize){
                fractionMeaningQuestion = new FractionMeaningQuestion();
            }
            mFractionMeaningQuestions.add(fractionMeaningQuestion);
            mQuestionNum++;
            reset();
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
