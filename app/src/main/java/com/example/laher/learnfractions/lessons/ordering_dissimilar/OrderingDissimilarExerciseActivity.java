package com.example.laher.learnfractions.lessons.ordering_dissimilar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.questions.GettingLcmQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

public class OrderingDissimilarExerciseActivity extends LessonExercise {
    //private static final String TAG = "OD_E1";
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
    TextView txtScore;
    TextView txtInstruction;
    //VARIABLES
    ArrayList<GettingLcmQuestion> mGettingLcmQuestions;
    GettingLcmQuestion mGettingLcmQuestion;
    int mQuestionNum;

    int clicks;

    public String title = "Ordering Dissimilar Fractions ex.1";
    String id = AppIDs.ODE_ID;

    public OrderingDissimilarExerciseActivity() {
        super();
        Range range = getRange();
        Probability probability = new Probability(Probability.GETTING_LCM, range);
        setProbability(probability);
        setId(id);
        setExerciseTitle(title);
    }

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ordering_dissimilar_exercise);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);
        Range range = getRange();
        Probability probability = new Probability(Probability.GETTING_LCM, range);
        setProbability(probability);
        //LCM DIALOG
        lcmView = getLayoutInflater().inflate(R.layout.layout_dialog_lcm, null);
        lcmDialog = new Dialog(OrderingDissimilarExerciseActivity.this);
        lcmDialog.setOnShowListener(new LcmDialogListener());
        lcmDialog.setOnDismissListener(new LcmDialogListener());
        lcmDialog.setOnCancelListener(new LcmDialogListener());
        lcmDialog.setTitle("Getting the LCM");
        lcmDialog.setContentView(lcmView);
        diagLcmTxtNum1 = lcmView.findViewById(R.id.lcm_txtNum1);
        diagLcmTxtNum2 = lcmView.findViewById(R.id.lcm_txtNum2);
        diagLcmTxtNum3 = lcmView.findViewById(R.id.lcm_txtNum3);
        diagLcmInputLcm = lcmView.findViewById(R.id.lcm_inputLcm);
        diagLcmInputLcm.setOnKeyListener(new DiagLcmTxtInputLcmListener());
        diagLcmInputLcm.setOnEditorActionListener(new DiagLcmTxtInputLcmListener());
        diagLcmBtnCheck = lcmView.findViewById(R.id.lcm_btnCheck);
        diagLcmBtnCheck.setOnClickListener(new DiagLcmBtnCheckListener());
        //GUI
        txtNum1 = findViewById(R.id.od1_txtNum1);
        txtNum2 = findViewById(R.id.od1_txtNum2);
        txtNum3 = findViewById(R.id.od1_txtNum3);
        txtScore = findViewById(R.id.od1_txtScore);
        txtInstruction = findViewById(R.id.od1_txtInstruction);

        startExercise();
    }
    public void setup(){
        clicks = 0;
        resetTxtColors();
        diagLcmInputLcm.setEnabled(true);
        diagLcmBtnCheck.setEnabled(true);
        setTxtListeners();
    }
    public void setQuestions(){
        mQuestionNum = 1;
        mGettingLcmQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        for (int i = 0; i < requiredCorrects; i++){
            GettingLcmQuestion gettingLcmQuestion = new GettingLcmQuestion();
            while (mGettingLcmQuestions.contains(gettingLcmQuestion)){
                gettingLcmQuestion = new GettingLcmQuestion();
            }
            mGettingLcmQuestions.add(gettingLcmQuestion);
        }
        setGuiNumbers();
    }
    public void setGuiNumbers(){
        mGettingLcmQuestion = mGettingLcmQuestions.get(mQuestionNum-1);
        int number1 = mGettingLcmQuestion.getNumber1();
        int number2 = mGettingLcmQuestion.getNumber2();
        int number3 = mGettingLcmQuestion.getNumber3();
        txtNum1.setText(String.valueOf(number1));
        txtNum2.setText(String.valueOf(number2));
        txtNum3.setText(String.valueOf(number3));
        String instruction = "Click all numbers.";
        txtInstruction.setText(instruction);
    }
    public void setTxtListeners(){
        txtNum1.setOnClickListener(new TxtNumListener());
        txtNum2.setOnClickListener(new TxtNumListener());
        txtNum3.setOnClickListener(new TxtNumListener());
    }
    public void resetTxtColors(){
        txtNum1.setTextColor(Color.rgb(128,128,128));
        txtNum2.setTextColor(Color.rgb(128,128,128));
        txtNum3.setTextColor(Color.rgb(128,128,128));
    }
    public void nextQuestion(){
        lcmDialog.dismiss();
        mQuestionNum++;
        setGuiNumbers();
        setup();
    }
    public void diagInputLcm(){
        mGettingLcmQuestion = mGettingLcmQuestions.get(mQuestionNum-1);
        int number1 = mGettingLcmQuestion.getNumber1();
        int number2 = mGettingLcmQuestion.getNumber2();
        int number3 = mGettingLcmQuestion.getNumber3();
        diagLcmTxtNum1.setText(String.valueOf(number1));
        diagLcmTxtNum2.setText(String.valueOf(number2));
        diagLcmTxtNum3.setText(String.valueOf(number3));
        lcmDialog.show();
        String instruction = "Get the lcm.";
        txtInstruction.setText(instruction);
    }
    public void removeTxtNumListener(){
        txtNum1.setOnClickListener(null);
        txtNum2.setOnClickListener(null);
        txtNum3.setOnClickListener(null);
    }
    public class TxtNumListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            if (t.getCurrentTextColor() != Color.rgb(0,255,0)){
                t.setTextColor(Color.rgb(0,255,0));
                clicks++;
            }
            if (clicks>=3){
                diagInputLcm();
            }
        }
    }
    public class DiagLcmBtnCheckListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            mGettingLcmQuestion = mGettingLcmQuestions.get(mQuestionNum-1);
            int lcm = mGettingLcmQuestion.getLcm();
            String strLcm = String.valueOf(lcm);
            if (String.valueOf(diagLcmInputLcm.getText()).equals(strLcm)){
                lcmDialog.dismiss();
                correct();
            } else {
                diagLcmInputLcm.setTextColor(Color.rgb(255,0,0));
                Styles.shakeAnimate(diagLcmInputLcm);
                diagLcmInputLcm.setEnabled(false);
                diagLcmBtnCheck.setEnabled(false);
                wrong();
            }
        }
    }
    public class DiagLcmTxtInputLcmListener implements EditText.OnKeyListener, TextView.OnEditorActionListener{
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){
                diagLcmBtnCheck.performClick();
            }
            return false;
        }
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            diagLcmInputLcm.setTextColor(Color.rgb(0,0,0));
            return false;
        }
    }
    public class LcmDialogListener implements Dialog.OnDismissListener, DialogInterface.OnCancelListener, DialogInterface.OnShowListener{
        @Override
        public void onDismiss(DialogInterface dialog) {
            diagLcmInputLcm.setText("");
            diagLcmInputLcm.setTextColor(Color.rgb(0,0,0));
            clicks = 0;
            resetTxtColors();
        }
        @Override
        public void onCancel(DialogInterface dialog) {
            String instruction = "Click all numbers.";
            txtInstruction.setText(instruction);
        }
        @Override
        public void onShow(DialogInterface dialog) {
            diagLcmInputLcm.requestFocus();
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
        setQuestions();
        setup();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        removeTxtNumListener();
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
        if (correctsShouldBeConsecutive) {
            setQuestions();
            setup();
        } else {
            GettingLcmQuestion gettingLcmQuestion = new GettingLcmQuestion();
            int questionsSize = mGettingLcmQuestions.size();
            int maxItemSize = getMaxItemSize();
            while (mGettingLcmQuestions.contains(gettingLcmQuestion) && questionsSize<maxItemSize){
                gettingLcmQuestion = new GettingLcmQuestion();
            }
            mGettingLcmQuestions.add(gettingLcmQuestion);
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
