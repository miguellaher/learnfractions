package com.example.laher.learnfractions.lessons.converting_fractions;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionClass;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.fraction_util.MixedFraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.ConvertingFractionsQuestion;
import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.model.ExerciseStat;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.ExerciseStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;

import org.json.JSONObject;

import java.util.ArrayList;

public class ConvertingFractionsExerciseActivity extends AppCompatActivity {
    Context mContext = this;
    private static final String TAG = "CF_E1";

    Exercise exercise;
    ExerciseStat mExerciseStat;
    final int EXERCISE_NUM = 1;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Converting Fraction";
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
    ArrayList<ConvertingFractionsQuestion> mConvertingFractionsQuestions;
    ConvertingFractionsQuestion mConvertingFractionsQuestion;
    int mQuestionNum;


    int correct;
    int requiredCorrects;
    int clicks;

    long startingTime, endingTime;

    final Handler handler = new Handler();
    ColorStateList defaultColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converting_fractions_exercise);
        exercise = LessonArchive.getLesson(AppConstants.CONVERTING_FRACTIONS).getExercises().get(EXERCISE_NUM-1);
        requiredCorrects = exercise.getRequiredCorrects();

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConvertingFractionsExerciseActivity.this,
                        ConvertingFractionsVideoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = findViewById(R.id.btnNext);
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
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        //GUI
        txtNum1 = findViewById(R.id.cvt_txtNum1);
        txtDenom1 = findViewById(R.id.cvt_txtDenom1);
        txtScore = findViewById(R.id.cvt_txtScore);
        setTxtScore();
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

        setAttributes((ExerciseStat) exercise);
        if (!Storage.isEmpty()) {
            checkUpdate();
        }
        startingTime = System.currentTimeMillis();

        go();
    }

    public void setAttributes(ExerciseStat exerciseAtt){
        Log.d(TAG, "set attributes");
        requiredCorrects = exerciseAtt.getRequiredCorrects();
        mExerciseStat = exerciseAtt;
        mExerciseStat.setTopicName(exercise.getTopicName());
        mExerciseStat.setExerciseNum(exercise.getExerciseNum());
        setTxtScore();
    }
    public void checkUpdate(){
        if (Storage.load(mContext, Storage.USER_TYPE).equals(AppConstants.STUDENT)){
            Service service = new Service("Checking for updates...", mContext, new ServiceResponse() {
                @Override
                public void postExecute(JSONObject response) {
                    try {
                        if (response.optString("message") != null && response.optString("message").equals("Exercise not found.")){
                        } else {
                            Exercise updatedExercise = new ExerciseStat();
                            updatedExercise.setRequiredCorrects(Integer.valueOf(response.optString("required_corrects")));
                            setAttributes((ExerciseStat) updatedExercise);
                            startingTime = System.currentTimeMillis();
                        }
                    } catch (Exception e){e.printStackTrace();}
                }
            });
            Student student = new Student();
            student.setTeacher_code(Storage.load(mContext, Storage.TEACHER_CODE));
            ExerciseService.getUpdate(exercise, student, service);
        }
    }
    public void go(){
        setFractionQuestions();
        setUp();
    }
    public void setTxtScore(){
        txtScore.setText(AppConstants.SCORE(correct,requiredCorrects));
    }
    public void correct(){
        correct++;
        setTxtScore();
        setInputEnabled(false);
        btnCheck.setEnabled(false);
        if (correct >= requiredCorrects){
            endingTime = System.currentTimeMillis();
            if (!Storage.isEmpty()) {
                setFinalAttributes();
            }
            btnNext.setEnabled(true);
            txtInstruction.setText(AppConstants.FINISHED_EXERCISE);
        } else {
            txtInstruction.setText(AppConstants.CORRECT);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mQuestionNum++;
                    setGuiFraction();
                    setUp();
                }
            },2000);
        }
    }
    private void setFinalAttributes(){
        Service service = new Service("Posting exercise stats...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try{
                    Log.d(TAG, "post execute");
                    Log.d(TAG, "message: " + response.optString("message"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mExerciseStat.setDone(true);
        mExerciseStat.setTime_spent(endingTime-startingTime);
        Student student = new Student();
        student.setId(Storage.load(mContext, Storage.STUDENT_ID));
        student.setTeacher_code(Storage.load(mContext, Storage.TEACHER_CODE));
        Log.d(TAG, "ATTRIBUTES: teacher_code: " + student.getTeacher_code() + "; student_id: " + student.getId() + "topic_name: " +
                mExerciseStat.getTopicName() + "; exercise_num: " + mExerciseStat.getExerciseNum() + "; done: " + mExerciseStat.isDone() +
                "; time_spent: " + mExerciseStat.getTime_spent() + "; errors: " + mExerciseStat.getErrors() + "; required_corrects: " +
                mExerciseStat.getRequiredCorrects() + "; rc_consecutive: " + mExerciseStat.isRc_consecutive() + "; max_errors: " +
                mExerciseStat.getMaxErrors() + "; me_consecutive: " + mExerciseStat.isMe_consecutive());
        ExerciseStatService.postStats(student,mExerciseStat,service);
    }
    private void setFractionQuestions(){
        mQuestionNum = 1;
        mConvertingFractionsQuestions = new ArrayList<>();
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
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
        }

        @Override
        public void onShow(DialogInterface dialog) {
            diagDdInputAnswer.setText("");
            diagDdInputRemainder.setText("");
            diagDdInputAnswer.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
}
