package com.example.laher.learnfractions.lessons.converting_fractions;

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
import com.example.laher.learnfractions.fraction_util.FractionClass;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;
import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.model.ExerciseStat;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.ExerciseStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Styles;

import org.json.JSONObject;

import java.util.ArrayList;

public class ConvertingFractionsExercise2Activity extends AppCompatActivity {
    Context mContext = this;
    private static final String TAG = "CF_E2";

    Exercise exercise;
    ExerciseStat mExerciseStat;
    final int EXERCISE_NUM = 2;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Converting Fraction";
    //GUI
    TextView txtWholeNum, txtNum1, txtNum2, txtDenom1, txtEquation, txtScore, txtInstruction;
    EditText inputDenom;
    Button btnCheck;
    //EQUATION DIALOG
    Dialog equationDialog;
    View edView;
    TextView diagEdTxtNum1, diagEdTxtNum2, diagEdTxtSign;
    EditText diagEdInputAnswer;
    Button diagEdBtnCheck;
    //VARIABLES
    FractionClass fraction;
    ArrayList<FractionClass> fractions;
    int questionNum;
    int correct;
    int requiredCorrects;

    long startingTime, endingTime;

    ArrayList<Integer> viewId;
    final Handler handler = new Handler();
    ColorStateList defaultColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converting_fractions_exercise2);
        exercise = LessonArchive.getLesson(AppConstants.CONVERTING_FRACTIONS).getExercises().get(EXERCISE_NUM-1);
        requiredCorrects = exercise.getRequiredCorrects();

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConvertingFractionsExercise2Activity.this,
                        ConvertingFractionsExerciseActivity.class);
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
                Intent intent = new Intent(ConvertingFractionsExercise2Activity.this,
                        TopicsMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        btnNext.setText(AppConstants.DONE);
        //GUI
        txtWholeNum = findViewById(R.id.cvt2_txtWholeNum);
        txtNum1 = findViewById(R.id.cvt2_txtNum);
        txtNum2 = findViewById(R.id.cvt2_txtNum2);
        txtNum2.setTextColor(Color.rgb(0,0,0));
        txtDenom1 = findViewById(R.id.cvt2_txtDenom);
        txtEquation = findViewById(R.id.cvt2_txtEquation);
        txtScore = findViewById(R.id.cvt2_txtScore);
        txtScore.setText(correct + " / " + requiredCorrects);
        txtInstruction = findViewById(R.id.cvt2_txtInstruction);
        inputDenom = findViewById(R.id.cvt2_inputDenom);
        inputDenom.setOnEditorActionListener(new InputListener());
        btnCheck = findViewById(R.id.cvt2_btnCheck);
        btnCheck.setOnClickListener(new BtnCheckListener());
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
    public void setTxtScore(){
        txtScore.setText(AppConstants.SCORE(correct,requiredCorrects));
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
        setQuestions();
        setGuiFraction();
        setUp();
    }
    public void correct(){
        correct++;
        setTxtScore();
        inputDenom.setEnabled(false);
        btnCheck.setEnabled(false);
        if (correct >= requiredCorrects){
            endingTime = System.currentTimeMillis();
            if (!Storage.isEmpty()) {
                setFinalAttributes();
            }
            txtInstruction.setText(AppConstants.FINISHED_LESSON);
            btnNext.setEnabled(true);
        } else {
            txtInstruction.setText(AppConstants.CORRECT);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    questionNum++;
                    setGuiFraction();
                    setUp();
                    inputDenom.setText("");
                }
            }, 2000);
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
    public void setQuestions(){
        questionNum = 0;
        fractions = new ArrayList<>();
        for (int i = 0; i < requiredCorrects; i++){
            fraction = new FractionClass(FractionClass.MIXED);
            fractions.add(fraction);
        }
    }
    public void setGuiFraction(){
        txtWholeNum.setText(String.valueOf(fractions.get(questionNum).getWholeNum()));
        txtNum1.setText(String.valueOf(fractions.get(questionNum).getNumerator()));
        txtDenom1.setText(String.valueOf(fractions.get(questionNum).getDenominator()));
    }
    public void setUp(){
        txtNum2.setText("");
        setInputEnabled(false);
        btnCheck.setEnabled(false);
        txtEquation.setVisibility(TextView.INVISIBLE);
        setMultiplyTxtListeners(true);
        txtInstruction.setText("Multiply the denominator to the whole number by clicking the denominator first and " +
                "whole number, second.");
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
        diagEdTxtNum1.setText(String.valueOf(fractions.get(questionNum).getDenominator()));
        diagEdTxtNum2.setText(String.valueOf(fractions.get(questionNum).getWholeNum()));
        diagEdTxtSign.setText("x");
        equationDialog.show();
    }
    public void popUpAddDialog(){
        diagEdTxtNum1.setText(String.valueOf(txtEquation.getText()));
        diagEdTxtNum2.setText(String.valueOf(fractions.get(questionNum).getNumerator()));
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
        @Override
        public void onClick(View v) {
            if (!diagEdInputAnswer.getText().toString().trim().matches("")){
                if (String.valueOf(diagEdTxtSign.getText())=="+") {
                    if (Integer.valueOf(String.valueOf(diagEdTxtNum1.getText()))
                            + Integer.valueOf(String.valueOf(diagEdTxtNum2.getText()))
                            == Integer.valueOf(String.valueOf(diagEdInputAnswer.getText().toString().trim()))){
                        txtEquation.setText(txtEquation.getText()+" + "
                                + String.valueOf(fractions.get(questionNum).getNumerator())
                                + " =");
                        setAddTxtListeners(false);
                        setInputEnabled(true);
                        btnCheck.setEnabled(true);
                        txtNum2.setText(String.valueOf(diagEdInputAnswer.getText().toString().trim()));
                        fractions.get(questionNum).toImproper();
                        equationDialog.dismiss();
                        txtInstruction.setText("The new denominator remains the same.");
                        inputDenom.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
                    } else {
                        Styles.shakeAnimate(diagEdInputAnswer);
                    }
                } else if (String.valueOf(diagEdTxtSign.getText()).equals("x")){
                    if (fractions.get(questionNum).getDenominator() * fractions.get(questionNum).getWholeNum()
                            == Integer.valueOf(String.valueOf(diagEdInputAnswer.getText().toString().trim()))) {
                        txtEquation.setText(String.valueOf(diagEdInputAnswer.getText()));
                        txtEquation.setVisibility(TextView.VISIBLE);
                        setMultiplyTxtListeners(false);
                        setAddTxtListeners(true);
                        equationDialog.dismiss();
                        txtInstruction.setText("To get the new numerator, add the product and the numerator by" +
                                " clicking the product first and numerator, second.");
                    } else {
                        Styles.shakeAnimate(diagEdInputAnswer);
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
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
        }
    }
    public class BtnCheckListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (!inputDenom.getText().toString().matches("")){
                if (Integer.valueOf(String.valueOf(inputDenom.getText().toString().trim()))
                        ==fractions.get(questionNum).getDenominator()){
                    correct();
                } else {
                    Styles.shakeAnimate(inputDenom);
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
}
