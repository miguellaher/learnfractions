package com.example.laher.learnfractions.lessons.non_visual_fraction;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
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
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.NonVisualQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;
import java.util.Collections;

public class NonVisualExercise2Activity extends LessonExercise {
    private static final String TAG = "NVE2";
    Button btnCheck;
    TextView txtNumerator;
    TextView txtDenominator;
    TextView txtInstruction;
    TextView txtInstruction2;
    TextView txtScore;
    EditText inputAnswer;
    ImageView imgLine;

    public String title = "Non-Visual Fractions\nex.1";
    String id = AppIDs.NVE2_ID;

    ArrayList<String> instructions;
    public final String INSTRUCTION_NUMERATOR = "Type the numerator.";
    public final String INSTRUCTION_DENOMINATOR = "Type the denominator.";
    public final String TITLE = "NON-VISUAL";

    ArrayList<NonVisualQuestion> mNonVisualQuestions;
    int mQuestionNum;

    public NonVisualExercise2Activity() {
        super();
        setId(id);
        setExerciseTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_non_visual_exercise2);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);

        txtNumerator = findViewById(R.id.b2_txtNumerator);
        txtDenominator = findViewById(R.id.b2_txtDenominator);
        txtInstruction = findViewById(R.id.b2_txtInstruction);
        txtInstruction2 = findViewById(R.id.b2_txtInstruction2);
        txtScore = findViewById(R.id.b2_txtScore);
        inputAnswer = findViewById(R.id.b2_inputAnswer);
        inputAnswer.getText().clear();
        inputAnswer.setOnFocusChangeListener(new InputAnswerListener());
        inputAnswer.setOnEditorActionListener(new InputAnswerListener());
        inputAnswer.addTextChangedListener(new InputAnswerListener());
        btnCheck = findViewById(R.id.b2_btnCheck);
        btnCheck.setOnClickListener(new BtnChkListener());
        btnCheck.setEnabled(false);
        imgLine = findViewById(R.id.nve2_imgLine);
        imgLine.setImageResource(R.drawable.line);

        instructions = new ArrayList<>();
        instructions.add(INSTRUCTION_NUMERATOR);
        instructions.add(INSTRUCTION_DENOMINATOR);

        startExercise();
    }
    private void generateFractionQuestions(){
        mQuestionNum = 1;
        mNonVisualQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        for (int i = 0; i < requiredCorrects; i++){
            NonVisualQuestion nonVisualQuestion = new NonVisualQuestion();
            while (mNonVisualQuestions.contains(nonVisualQuestion)) {
                nonVisualQuestion = new NonVisualQuestion();
            }
            mNonVisualQuestions.add(nonVisualQuestion);
        }
        setTxtFraction();
    }
    public void setTxtFraction(){
        int numerator = mNonVisualQuestions.get(mQuestionNum-1).getNumerator();
        int denominator = mNonVisualQuestions.get(mQuestionNum-1).getDenominator();
        txtNumerator.setText(String.valueOf(numerator));
        txtDenominator.setText(String.valueOf(denominator));
        generateInstruction();
    }
    public void generateInstruction(){
        Collections.shuffle(instructions);
        txtInstruction.setText(instructions.get(0));
        if (txtInstruction.getText().toString().equals(INSTRUCTION_NUMERATOR)){
            txtInstruction2.setText(AppConstants.NUMERATOR);
        } else {
            txtInstruction2.setText(AppConstants.DENOMINATOR);
        }
    }
    public void reset(){
        inputAnswer.getText().clear();
        Styles.paintBlack(inputAnswer);
        btnCheck.setEnabled(false);
        if (mQuestionNum>1) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }

    public class BtnChkListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            NonVisualQuestion nonVisualQuestion = mNonVisualQuestions.get(mQuestionNum-1);
            Fraction fraction = nonVisualQuestion.getFractionAnswer();
            int numeratorAnswer = fraction.getNumerator();
            int denominatorAnswer = fraction.getDenominator();
            if (inputAnswer.getText().toString().trim().length() != 0) {
                if (instructions.get(0).equals(INSTRUCTION_NUMERATOR)) {
                    //if(inputAnswer.getText() == txtNumerator.getText()){
                    if (Integer.parseInt(String.valueOf(inputAnswer.getText())) == numeratorAnswer) {
                        correct();
                    } else {
                        wrong();
                        Log.d(TAG, "correct numerator: " + numeratorAnswer);
                    }
                } else if (instructions.get(0).equals(INSTRUCTION_DENOMINATOR)) {
                    if (Integer.parseInt(String.valueOf(inputAnswer.getText())) == denominatorAnswer) {
                        correct();
                    } else {
                        wrong();
                        Log.d(TAG, "correct denominator: " + denominatorAnswer);
                    }
                }
            }
        }
    }
    public class InputAnswerListener implements View.OnFocusChangeListener, TextView.OnEditorActionListener, TextWatcher{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus){
                if (mQuestionNum>1) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){
                btnCheck.performClick();
                return false;
            }
            return false;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (inputAnswer.getText().toString().trim().length() == 0){
                btnCheck.setEnabled(false);
            } else {
                btnCheck.setEnabled(true);
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
        int requiredCorrects = getItemsSize();
        txtScore.setText(AppConstants.SCORE(correct,requiredCorrects));
    }

    @Override
    protected void startExercise() {
        super.startExercise();
        reset();
        generateFractionQuestions();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        inputAnswer.setEnabled(false);
        btnCheck.setEnabled(false);
    }

    @Override
    protected void postAnswered() {
        super.postAnswered();
        inputAnswer.setEnabled(true);
        btnCheck.setEnabled(true);
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
        setTxtFraction();
    }

    @Override
    protected void preFinished() {
        super.preFinished();
        txtInstruction.setText(AppConstants.FINISHED_LESSON);
        inputAnswer.getText().clear();
    }

    @Override
    protected void preWrong() {
        super.preWrong();
        txtInstruction.setText(AppConstants.ERROR);
        Styles.shakeAnimate(inputAnswer);
        Styles.paintRed(inputAnswer);
    }

    @Override
    protected void postWrong() {
        super.postWrong();
        NonVisualQuestion nonVisualQuestion = new NonVisualQuestion();
        int questionsSize = mNonVisualQuestions.size();
        int maxItemsSize = getMaxItemsSize();
        while (mNonVisualQuestions.contains(nonVisualQuestion) && questionsSize<=maxItemsSize) {
            nonVisualQuestion = new NonVisualQuestion();
        }
        mNonVisualQuestions.add(nonVisualQuestion);
        mQuestionNum++;
        reset();
        setTxtFraction();
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
