package com.example.laher.learnfractions.lessons.solving_mixed;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.fraction_util.FractionClass;
import com.example.laher.learnfractions.fraction_util.FractionQuestion;
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
import com.example.laher.learnfractions.dialog_layout.EquationDialog;
import com.example.laher.learnfractions.dialog_layout.LcmDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.DialogInterface.*;

public class SolvingMixedExerciseActivity extends AppCompatActivity {
    public static final String TAG = "SM1_E1";
    Context mContext = this;

    Exercise exercise;
    ExerciseStat mExerciseStat;
    final int EXERCISE_NUM = 1;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Solving Equations";
    //GUI
    TextView txtNum1, txtNum2, txtNum3, txtNum4, txtDenom1, txtDenom2, txtDenom3, txtDenom4, txtWholeNum1,
            txtWholeNum2, txtSign1, txtSign2, txtEquation1, txtEquation2, txtScore, txtInstruction;
    EditText inputNum, inputDenom;
    Button btnCheck;
    ConstraintLayout clFraction1, clFraction2;
    //MIXED CONVERT DIALOG
    Dialog dialogMixedConvert;
    View viewMixedConvert;
    TextView mcD_txtWholeNum, mcD_txtNum1, mcD_txtNum2, mcD_txtDenom1, mcD_txtDenom2, mcD_txtEquation;
    Button mcD_btnConvert;
    //EQUATION DIALOG
    EquationDialog dialogEquation;
    Dialog equationDialog;
    View edView;
    TextView eDTxtNum1, eDTxtNum2, eDTxtSign;
    EditText eDInputAnswer;
    Button eDBtnCheck;
    FractionClass eDMixedFraction;
    //LCM DIALOG
    LcmDialog dialogLcm;
    //VARIABLES
    ArrayList<FractionQuestion> fractionQuestions;
    FractionQuestion fractionQuestion;
    int questionNum;
    int correct, error;
    int requiredCorrects;
    int maxErrors;
    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;

    long startingTime, endingTime;

    final Handler handler = new Handler();
    ColorStateList defaultColor;
    int clicks;
    Context context;
    ArrayList<Integer> stepsListId;
    TextView clickedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_equation_mixed);
        exercise = LessonArchive.getLesson(AppConstants.ADDING_SUBTRACTING_MIXED).getExercises().get(EXERCISE_NUM-1);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SolvingMixedExerciseActivity.this,
                        SolvingMixedVideoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CHANGE INTENT PARAMS
                Intent intent = new Intent(SolvingMixedExerciseActivity.this,
                        TopicsMenuActivity.class); //CHANGE
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        btnNext.setText(AppConstants.DONE);
        //GUI
        txtNum1 = findViewById(R.id.fem_txtNum1);
        txtNum2 = findViewById(R.id.fem_txtNum2);
        txtNum3 = findViewById(R.id.fem_txtNum3);
        txtNum4 = findViewById(R.id.fem_txtNum4);
        txtDenom1 = findViewById(R.id.fem_txtDenom1);
        txtDenom2 = findViewById(R.id.fem_txtDenom2);
        txtDenom3 = findViewById(R.id.fem_txtDenom3);
        txtDenom4 = findViewById(R.id.fem_txtDenom4);
        txtWholeNum1 = findViewById(R.id.fem_txtWholeNum1);
        txtWholeNum2 = findViewById(R.id.fem_txtWholeNum2);
        txtSign1 = findViewById(R.id.fem_txtSign1);
        txtSign2 = findViewById(R.id.fem_txtSign2);
        txtEquation1 = findViewById(R.id.fem_txtEquation1);
        txtEquation2 = findViewById(R.id.fem_txtEquation2);
        txtScore = findViewById(R.id.fem_txtScore);
        setTxtScore();
        txtInstruction = findViewById(R.id.fem_txtInstruction);
        inputNum = findViewById(R.id.fem_inputNum);
        inputDenom = findViewById(R.id.fem_inputDenom);
        inputDenom.setOnEditorActionListener(new InputListener());
        btnCheck = findViewById(R.id.fem_btnCheck);
        btnCheck.setOnClickListener(new BtnCheckListener());
        clFraction1 = findViewById(R.id.fem_clFraction1);
        clFraction2 = findViewById(R.id.fem_clFraction2);
        //MIXED CONVERT DIALOG
        viewMixedConvert = getLayoutInflater().inflate(R.layout.layout_mixed_convert, null);
        dialogMixedConvert = new Dialog(SolvingMixedExerciseActivity.this);
        dialogMixedConvert.setTitle("Converter");
        dialogMixedConvert.setContentView(viewMixedConvert);
        mcD_txtWholeNum = viewMixedConvert.findViewById(R.id.mcn_txtWholeNum1);
        mcD_txtNum1 = viewMixedConvert.findViewById(R.id.mcn_txtNum1);
        mcD_txtNum2 = viewMixedConvert.findViewById(R.id.mcn_txtNum2);
        mcD_txtDenom1 = viewMixedConvert.findViewById(R.id.mcn_txtDenom1);
        mcD_txtDenom2 = viewMixedConvert.findViewById(R.id.mcn_txtDenom2);
        mcD_txtEquation = viewMixedConvert.findViewById(R.id.mcn_txtEquation);
        mcD_btnConvert = viewMixedConvert.findViewById(R.id.mcn_btnConvert);
        mcD_btnConvert.setOnClickListener(new McDBtnConvertListener());
        //EQUATION DIALOG
        edView = getLayoutInflater().inflate(R.layout.layout_dialog_equation, null);
        equationDialog = new Dialog(SolvingMixedExerciseActivity.this);
        equationDialog.setOnDismissListener(new DialogEquationListener());
        equationDialog.setOnShowListener(new DialogEquationListener());
        equationDialog.setTitle("Division Equation");
        equationDialog.setContentView(edView);
        eDTxtNum1 = edView.findViewById(R.id.md_txtMultiplicand);
        eDTxtNum2 = edView.findViewById(R.id.md_txtMultiplier);
        eDTxtSign = edView.findViewById(R.id.md_txtSign);
        eDInputAnswer = edView.findViewById(R.id.md_inputProduct);
        eDInputAnswer.setOnEditorActionListener(new InputListener());
        eDBtnCheck = edView.findViewById(R.id.md_btnCheck);
        eDBtnCheck.setOnClickListener(new EDBtnCheckListener());

        setClickAreas();

        defaultColor = txtNum1.getTextColors();
        stepsListId = new ArrayList<>();

        context = this;

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
        maxErrors = exerciseAtt.getMaxErrors();
        correctsShouldBeConsecutive = exerciseAtt.isRc_consecutive();
        errorsShouldBeConsecutive = exerciseAtt.isMe_consecutive();
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
                        Log.d(TAG, "post execute");
                        Log.d(TAG, response.optString("message"));
                        Exercise updatedExercise = new ExerciseStat();
                        updatedExercise.setRequiredCorrects(Integer.valueOf(response.optString("required_corrects")));
                        Log.d(TAG, "finished setRequiredCorrects()");
                        if (response.optString("rc_consecutive").equals("1")) {
                            updatedExercise.setRc_consecutive(true);
                        } else {
                            updatedExercise.setRc_consecutive(false);
                        }
                        Log.d(TAG, "finished setRc_consecutive()");
                        updatedExercise.setMaxErrors(Integer.valueOf(response.optString("max_errors")));
                        Log.d(TAG, "finished setMaxErrors()");
                        if (response.optString("me_consecutive").equals("1")) {
                            updatedExercise.setMe_consecutive(true);
                        } else {
                            updatedExercise.setMe_consecutive(false);
                        }
                        Log.d(TAG, "finished setMe_consecutive()");
                        setAttributes((ExerciseStat) updatedExercise);
                        startingTime = System.currentTimeMillis();
                        Log.d(TAG, "finished setAttributes()");
                    } catch (Exception e) {
                        Log.d(TAG, "exception handled");
                        e.printStackTrace();
                    }
                }
            });
            Student student = new Student();
            student.setTeacher_code(Storage.load(mContext, Storage.TEACHER_CODE));
            ExerciseService.getUpdate(exercise, student, service);
        }
    }
    public void go(){
        startUp();
        setFractionQuestions();
        setFractionGui();
    }
    public void setTxtScore(){
        txtScore.setText(AppConstants.SCORE(correct,requiredCorrects));
    }
    public void correct(){
        correct++;
        if (errorsShouldBeConsecutive) {
            error = 0;
        }
        answered();
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
                    nextQuestion();
                }
            }, 2000);
        }
    }
    public void nextQuestion(){
        startUp();
        questionNum++;
        setFractionGui();
    }
    public void wrong(){
        error++;
        mExerciseStat.incrementError();
        if (correctsShouldBeConsecutive) {
            correct = 0;
        }
        answered();
        if (error >= maxErrors){
            if (errorsShouldBeConsecutive) {
                txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(error));
            } else {
                txtInstruction.setText(AppConstants.FAILED(error));
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SolvingMixedExerciseActivity.this,
                            SolvingMixedVideoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 4000);
        } else {
            txtInstruction.setText(AppConstants.ERROR);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (correctsShouldBeConsecutive) {
                        go();
                    } else {
                        if (fractionQuestions.get(questionNum).getContext().equals(FractionQuestion.ADDING_WITH_MIXED)){
                            fractionQuestion = new FractionQuestion(FractionQuestion.ADDING_WITH_MIXED);
                        } else if (fractionQuestions.get(questionNum).getContext().equals(FractionQuestion.SUBTRACTING_WITH_MIXED)){
                            fractionQuestion = new FractionQuestion(FractionQuestion.SUBTRACTING_WITH_MIXED);
                        }
                        fractionQuestions.add(fractionQuestion);
                        nextQuestion();
                    }
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
    public void answered(){
        setTxtScore();
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        btnCheck.setEnabled(false);
    }
    public void startUp(){
        txtEquation1.setText("");
        txtEquation2.setText("");
        txtNum3.setText("");
        txtDenom3.setText("");
        txtNum4.setText("");
        txtDenom4.setText("");
        inputNum.setText("");
        inputDenom.setText("");
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        btnCheck.setEnabled(false);
        txtInstruction.setText("Convert a mixed fraction to an improper fraction by clicking on it.");
    }
    public void setFractionQuestions(){
        fractionQuestions = new ArrayList<>();
        questionNum = 0;
        for (int i = 0; i < requiredCorrects; i++){
            if (i < (requiredCorrects /2)){
                fractionQuestion = new FractionQuestion(FractionQuestion.ADDING_WITH_MIXED);
            } else {
                fractionQuestion = new FractionQuestion(FractionQuestion.SUBTRACTING_WITH_MIXED);
            }
            fractionQuestions.add(fractionQuestion);
        }
        Collections.shuffle(fractionQuestions);
    }
    public void setFractionGui(){
        if (fractionQuestions.get(questionNum).getFractionOne().getContext().equals(FractionClass.MIXED)){
            txtWholeNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getWholeNum()));
            clFraction1.setOnClickListener(new ClFractionListener());
        } else {
            txtWholeNum1.setText("");
        }
        if (fractionQuestions.get(questionNum).getFractionTwo().getContext().equals(FractionClass.MIXED)){
            txtWholeNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getWholeNum()));
            clFraction2.setOnClickListener(new ClFractionListener());
        } else {
            txtWholeNum2.setText("");
        }
        if (fractionQuestions.get(questionNum).getContext().equals(FractionQuestion.ADDING_WITH_MIXED)){
            txtSign1.setText("+");
            txtSign2.setText("+");
        } else if (fractionQuestions.get(questionNum).getContext().equals(FractionQuestion.SUBTRACTING_WITH_MIXED)){
            txtSign1.setText("-");
            txtSign2.setText("-");
        }
        txtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getNumerator()));
        txtDenom1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
        txtNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getNumerator()));
        txtDenom2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
    }
    public void setMcnMultiplyListener(boolean b){
        if (b){
            mcD_txtDenom1.setOnClickListener(new McnTxtMultiplyListener());
            mcD_txtWholeNum.setOnClickListener(new McnTxtMultiplyListener());
        } else {
            mcD_txtDenom1.setOnClickListener(null);
            mcD_txtWholeNum.setOnClickListener(null);
        }
        mcD_txtDenom1.setClickable(b);
        mcD_txtWholeNum.setClickable(b);
    }
    public void setMcnAddListener(boolean b){
        if (b){
            mcD_txtEquation.setOnClickListener(new McnTxtAddListener());
            mcD_txtNum1.setOnClickListener(new McnTxtAddListener());
        } else {
            mcD_txtEquation.setOnClickListener(null);
            mcD_txtNum1.setOnClickListener(null);
        }
        mcD_txtEquation.setClickable(b);
        mcD_txtNum1.setClickable(b);
    }
    public void setTxtLcmListener(boolean b){
        if (b){
            txtDenom1.setOnClickListener(new TxtLcmListener());
            txtDenom2.setOnClickListener(new TxtLcmListener());
        } else {
            txtDenom1.setOnClickListener(null);
            txtDenom2.setOnClickListener(null);
        }
        txtDenom1.setClickable(b);
        txtDenom2.setClickable(b);
    }
    public void setTxtDivideListener(boolean b){
        if (b){
            txtDenom1.setOnClickListener(new TxtDivideListener());
            txtDenom2.setOnClickListener(new TxtDivideListener());
            txtDenom3.setOnClickListener(new TxtDivideListener());
            txtDenom4.setOnClickListener(new TxtDivideListener());
        } else {
            txtDenom1.setOnClickListener(null);
            txtDenom2.setOnClickListener(null);
            txtDenom3.setOnClickListener(null);
            txtDenom4.setOnClickListener(null);
        }
        txtDenom1.setClickable(b);
        txtDenom2.setClickable(b);
        txtDenom3.setClickable(b);
        txtDenom4.setClickable(b);
    }
    public void setTxtMultiplyListener(boolean b){
        if (b){
            txtEquation1.setOnClickListener(new TxtMultiplyListener());
            txtEquation2.setOnClickListener(new TxtMultiplyListener());
            txtNum1.setOnClickListener(new TxtMultiplyListener());
            txtNum2.setOnClickListener(new TxtMultiplyListener());
        } else {
            txtEquation1.setOnClickListener(null);
            txtEquation2.setOnClickListener(null);
            txtNum1.setOnClickListener(null);
            txtNum2.setOnClickListener(null);
        }
        txtEquation1.setClickable(b);
        txtEquation2.setClickable(b);
        txtNum1.setClickable(b);
        txtNum2.setClickable(b);
    }
    public class ClFractionListener implements ConstraintLayout.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == clFraction1.getId()){
                mcD_txtWholeNum.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getWholeNum()));
                mcD_txtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getNumerator()));
                mcD_txtDenom1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
                mcD_txtDenom2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
                eDMixedFraction = fractionQuestions.get(questionNum).getFractionOne();
            } else if (v.getId() == clFraction2.getId()){
                mcD_txtWholeNum.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getWholeNum()));
                mcD_txtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getNumerator()));
                mcD_txtDenom1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
                mcD_txtDenom2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
                eDMixedFraction = fractionQuestions.get(questionNum).getFractionTwo();
            }
            mcD_txtNum2.setText("");
            mcD_txtEquation.setText("");
            mcD_btnConvert.setEnabled(false);
            dialogMixedConvert.setCanceledOnTouchOutside(false);
            dialogMixedConvert.show();
            setMcnMultiplyListener(true);
        }
    }
    public class McnTxtMultiplyListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            if (clicks == 0){
                if (t.getId() == mcD_txtDenom1.getId()){
                    Styles.paintGreen(t);
                    clicks++;
                } else {
                    Styles.shakeAnimate(mcD_txtDenom1);
                }
            } else if (clicks == 1){
                if (t.getId() == mcD_txtWholeNum.getId()){
                    Styles.paintGreen(t);
                    clicks = 0;
                    eDTxtNum1.setText(String.valueOf(mcD_txtDenom1.getText()));
                    eDTxtNum2.setText(String.valueOf(mcD_txtWholeNum.getText()));
                    eDTxtSign.setText("x");
                    equationDialog.setCanceledOnTouchOutside(false);
                    equationDialog.show();
                } else {
                    Styles.shakeAnimate(mcD_txtWholeNum);
                }
            }
        }
    }
    public class McnTxtAddListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            if (clicks == 0){
                if (t.getId() == mcD_txtEquation.getId()){
                    Styles.paintGreen(t);
                    clicks++;
                } else {
                    Styles.shakeAnimate(mcD_txtEquation);
                }
            } else if (clicks == 1){
                if (t.getId() == mcD_txtNum1.getId()){
                    Styles.paintGreen(t);
                    clicks = 0;
                    eDTxtNum1.setText(String.valueOf(mcD_txtEquation.getText()));
                    eDTxtNum2.setText(String.valueOf(mcD_txtNum1.getText()));
                    eDTxtSign.setText("+");
                    equationDialog.setCanceledOnTouchOutside(false);
                    equationDialog.show();
                } else {
                    Styles.shakeAnimate(mcD_txtNum1);
                }
            }
        }
    }
    private class EDBtnCheckListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                if (!eDInputAnswer.toString().matches("")) {
                    if (String.valueOf(eDTxtSign.getText()).equals("x")) {
                        if (Integer.valueOf(String.valueOf(eDTxtNum1.getText())) *
                                Integer.valueOf(String.valueOf(eDTxtNum2.getText())) ==
                                Integer.valueOf(String.valueOf(eDInputAnswer.getText()))) {
                            mcD_txtEquation.setText(String.valueOf(eDInputAnswer.getText()));
                            equationDialog.dismiss();
                            setMcnMultiplyListener(false);
                            setMcnAddListener(true);
                        } else {
                            Styles.shakeAnimate(eDInputAnswer);
                        }
                    } else if (String.valueOf(eDTxtSign.getText()).equals("+")) {
                        if (Integer.valueOf(String.valueOf(eDTxtNum1.getText())) +
                                Integer.valueOf(String.valueOf(eDTxtNum2.getText())) ==
                                Integer.valueOf(String.valueOf(eDInputAnswer.getText()))) {
                            mcD_txtEquation.setText(String.valueOf(mcD_txtEquation.getText()) + " + "
                                    + String.valueOf(eDTxtNum2.getText()) + " =");
                            mcD_txtNum2.setText(String.valueOf(eDInputAnswer.getText()));
                            equationDialog.dismiss();
                            setMcnAddListener(false);
                            mcD_btnConvert.setEnabled(true);
                        } else {
                            Styles.shakeAnimate(eDInputAnswer);
                        }
                    }
                }
            } catch (NumberFormatException e){
                Styles.shakeAnimate(eDInputAnswer);
            }
        }
    }
    private class DialogEquationListener implements OnDismissListener, OnShowListener {
        @Override
        public void onDismiss(DialogInterface dialog) {
            mcD_txtWholeNum.setTextColor(defaultColor);
            mcD_txtNum1.setTextColor(defaultColor);
            mcD_txtDenom1.setTextColor(defaultColor);
            mcD_txtEquation.setTextColor(defaultColor);
            eDInputAnswer.setText("");
            eDInputAnswer.setHint("");
        }

        @Override
        public void onShow(DialogInterface dialog) {
            if (Integer.valueOf(String.valueOf(eDTxtNum1.getText()))>10 ||
                    Integer.valueOf(String.valueOf(eDTxtNum2.getText()))>10){
                if (String.valueOf(eDTxtSign.getText()).equals("x")){
                    eDInputAnswer.setHint(String.valueOf(Integer.valueOf(String.valueOf(eDTxtNum1.getText()))*
                            Integer.valueOf(String.valueOf(eDTxtNum2.getText()))));
                }
            }
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }
    private class McDBtnConvertListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (eDMixedFraction == fractionQuestions.get(questionNum).getFractionOne()){
                txtWholeNum1.setText("");
                txtNum1.setText(String.valueOf(mcD_txtNum2.getText()));
                txtDenom1.setText(String.valueOf(mcD_txtDenom2.getText()));
            }
            if (eDMixedFraction == fractionQuestions.get(questionNum).getFractionTwo()){
                txtWholeNum2.setText("");
                txtNum2.setText(String.valueOf(mcD_txtNum2.getText()));
                txtDenom2.setText(String.valueOf(mcD_txtDenom2.getText()));
            }
            dialogMixedConvert.dismiss();
            if (txtWholeNum1.getText().toString().matches("")){
                clFraction1.setClickable(false);
            }
            if (txtWholeNum2.getText().toString().matches("")){
                clFraction2.setClickable(false);
            }
            if (txtWholeNum1.getText().toString().matches("") &&
                    txtWholeNum2.getText().toString().matches("")){
                if (String.valueOf(txtDenom1.getText()).equals(String.valueOf(txtDenom2.getText()))){
                    txtNum3.setText(String.valueOf(txtNum1.getText()));
                    txtNum4.setText(String.valueOf(txtNum2.getText()));
                    txtDenom3.setText(String.valueOf(txtDenom1.getText()));
                    txtDenom4.setText(String.valueOf(txtDenom1.getText()));
                    txtInstruction.setText("Looks like we have an equation of two similar fractions." +
                            " Remember the steps from the previous lesson.");
                    inputNum.setEnabled(true);
                    inputDenom.setEnabled(true);
                    btnCheck.setEnabled(true);
                    inputNum.requestFocus();
                } else {
                    setTxtLcmListener(true);
                    if (fractionQuestions.get(questionNum).getContext()==FractionQuestion.ADDING_WITH_MIXED){
                        txtInstruction.setText("Now, we have an equation of two dissimilar fractions. Add the two" +
                                " dissimilar fractions like we did last time.");
                    } else if (fractionQuestions.get(questionNum).getContext()==FractionQuestion.SUBTRACTING_WITH_MIXED){
                        txtInstruction.setText("Now, we have an equation of two dissimilar fractions. Subtract the two" +
                                " dissimilar fractions like we did last time.");
                    }
                }
            }
        }
    }
    private class TxtLcmListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            Styles.paintGreen(t);
            clicks++;
            t.setClickable(false);
            if (clicks==2){
                int num1 = Integer.valueOf(String.valueOf(txtDenom1.getText()));
                int num2 = Integer.valueOf(String.valueOf(txtDenom2.getText()));
                clicks = 0;
                dialogLcm = new LcmDialog(context, num1, num2);
                dialogLcm.setOnDismissListener(new DialogLcmListener());
                dialogLcm.setOnShowListener(new DialogLcmListener());
                dialogLcm.show();
            }
        }
    }
    private class TxtDivideListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            stepsListId.add(t.getId());
            if (stepsListId.size()==1) {
                if (t.getId() == txtDenom3.getId() || t.getId() == txtDenom4.getId()) {
                    Styles.paintGreen(t);
                } else {
                    Styles.shakeAnimate(txtDenom3);
                    Styles.shakeAnimate(txtDenom4);
                    stepsListId.clear();
                }
            }
            if (stepsListId.size()==2){
                if (stepsListId.get(0) == txtDenom3.getId()){
                    if (t.getId() == txtDenom1.getId()) {
                        Styles.paintGreen(t);
                        dialogEquation = new EquationDialog(context,
                                Integer.valueOf(String.valueOf(txtDenom3.getText())),
                                Integer.valueOf(String.valueOf(txtDenom1.getText())),
                                EquationDialog.DIVISION);
                        dialogEquationShow();
                        stepsListId.clear();
                        clickedTextView = txtDenom1;
                    } else {
                        stepsListId.remove(stepsListId.size() - 1);
                        Styles.shakeAnimate(txtDenom1);
                    }
                } else if (stepsListId.get(0) == txtDenom4.getId()){
                    if (t.getId() == txtDenom2.getId()) {
                        Styles.paintGreen(t);
                        dialogEquation = new EquationDialog(context,
                                Integer.valueOf(String.valueOf(txtDenom4.getText())),
                                Integer.valueOf(String.valueOf(txtDenom2.getText())),
                                EquationDialog.DIVISION);
                        dialogEquationShow();
                        stepsListId.clear();
                        clickedTextView = txtDenom2;
                    } else {
                        stepsListId.remove(stepsListId.size() - 1);
                        Styles.shakeAnimate(txtDenom2);
                    }
                }
            }
        }
    }

    private class TxtMultiplyListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            stepsListId.add(t.getId());
            if (stepsListId.size() == 1) {
                if (stepsListId.get(0) == txtEquation1.getId() ||
                        stepsListId.get(0) == txtEquation2.getId()) {
                    Styles.paintGreen(t);
                } else {
                    stepsListId.clear();
                }
            }
            if (stepsListId.size() == 2) {
                if (stepsListId.get(0) == txtEquation1.getId()) {
                    if (stepsListId.get(1) == txtNum1.getId()){
                        Styles.paintGreen(t);
                        dialogEquation = new EquationDialog(context,
                                Integer.valueOf(String.valueOf(txtEquation1.getText())),
                                Integer.valueOf(String.valueOf(txtNum1.getText())),
                                EquationDialog.MULTIPLICATION);
                        dialogEquationShow();
                        dialogEquation.getInputAns().setHint(dialogEquation.getHint());
                        stepsListId.clear();
                        clickedTextView = txtNum1;
                    } else {
                        stepsListId.remove(stepsListId.size() - 1);
                        Styles.shakeAnimate(txtNum1);
                    }
                } else if (stepsListId.get(0) == txtEquation2.getId()){
                    if (t.getId() == txtNum2.getId()) {
                        Styles.paintGreen(t);
                        dialogEquation = new EquationDialog(context,
                                Integer.valueOf(String.valueOf(txtEquation2.getText())),
                                Integer.valueOf(String.valueOf(txtNum2.getText())),
                                EquationDialog.MULTIPLICATION);
                        dialogEquationShow();
                        dialogEquation.getInputAns().setHint(dialogEquation.getHint());
                        stepsListId.clear();
                        clickedTextView = txtNum2;
                    } else {
                        stepsListId.remove(stepsListId.size() - 1);
                        Styles.shakeAnimate(txtNum2);
                    }
                }
            }
        }
    }

    public void dialogEquationShow(){
        dialogEquation.setOnShowListener(new DialogEquationListener());
        dialogEquation.show();
        dialogEquation.setOnDismissListener(new DialogEquationListener2());

    }

    public void getListenerRemove(View v){
        v.setOnClickListener(null);
        v.setClickable(false);
    }

    private class DialogLcmListener implements OnDismissListener, OnShowListener{
        @Override
        public void onDismiss(DialogInterface dialog) {
            if (dialogLcm.getLcm()!=0){
                txtDenom3.setText(String.valueOf(dialogLcm.getLcm()));
                txtDenom4.setText(String.valueOf(dialogLcm.getLcm()));
                setTxtDivideListener(true);
            } else {
                setTxtLcmListener(true);
            }
            txtDenom1.setTextColor(defaultColor);
            txtDenom2.setTextColor(defaultColor);
        }

        @Override
        public void onShow(DialogInterface dialog) {
            if (questionNum>0) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
        }
    }
    private class DialogEquationListener2 implements OnDismissListener{
        @Override
        public void onDismiss(DialogInterface dialog) {
            if (dialogEquation.isCorrect()){
                if (clickedTextView == txtDenom1){
                    txtEquation1.setText(String.valueOf(dialogEquation.getAnswer()));
                    getListenerRemove(txtDenom1);
                    getListenerRemove(txtDenom3);
                } else if (clickedTextView == txtDenom2){
                    txtEquation2.setText(String.valueOf(dialogEquation.getAnswer()));
                    getListenerRemove(txtDenom2);
                    getListenerRemove(txtDenom4);
                } else if (clickedTextView == txtNum1){
                    txtEquation1.setText(String.valueOf(txtEquation1.getText()) + " * " +
                            String.valueOf(txtNum1.getText()) + " =");
                    txtNum3.setText(String.valueOf(dialogEquation.getAnswer()));
                    Styles.removeListener(txtEquation1);
                    Styles.removeListener(txtNum1);
                } else if (clickedTextView == txtNum2){
                    txtEquation2.setText(String.valueOf(txtEquation2.getText()) + " * " +
                            String.valueOf(txtNum2.getText()) + " =");
                    txtNum4.setText(String.valueOf(dialogEquation.getAnswer()));
                    getListenerRemove(txtEquation2);
                    getListenerRemove(txtNum2);
                }
            }
            txtDenom1.setTextColor(defaultColor);
            txtDenom2.setTextColor(defaultColor);
            txtDenom3.setTextColor(defaultColor);
            txtDenom4.setTextColor(defaultColor);
            txtNum1.setTextColor(defaultColor);
            txtNum2.setTextColor(defaultColor);
            txtEquation1.setTextColor(defaultColor);
            txtEquation2.setTextColor(defaultColor);
            if (!txtNum3.getText().toString().matches("") &&
                    !txtNum4.getText().toString().matches("")){
                inputNum.setEnabled(true);
                inputDenom.setEnabled(true);
                btnCheck.setEnabled(true);
                inputNum.requestFocus();
            } else if (txtNum3.getText().toString().matches("") &&
                    txtNum4.getText().toString().matches("") &&
                    !txtEquation1.getText().toString().matches("") &&
                    !txtEquation2.getText().toString().matches("")) {
                setTxtLcmListener(false);
                setTxtMultiplyListener(true);
            }
        }
    }

    private class BtnCheckListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!inputNum.getText().toString().matches("")){
                if (!inputDenom.getText().toString().matches("")){
                    if (Integer.valueOf(String.valueOf(inputNum.getText())) == fractionQuestions.get(questionNum)
                            .getFractionAnswer().getNumerator() &&
                            Integer.valueOf(String.valueOf(inputDenom.getText())) == fractionQuestions.get(questionNum)
                                    .getFractionAnswer().getDenominator()){
                        correct();
                    } else {
                        wrong();
                    }
                }
            } else {
                Styles.shakeAnimate(inputNum);
                if (inputDenom.getText().toString().matches("")){
                    Styles.shakeAnimate(inputDenom);
                }
            }
        }
    }
    private void setClickAreas(){
        setClickArea(mcD_txtDenom1, 0,0,100,50);
        setClickArea(mcD_txtWholeNum, 0,100,0,0);
        setClickArea(mcD_txtEquation, 100,100,100,100);
        setClickArea(txtDenom1,0,50,50,100);
        setClickArea(txtDenom2,0,50,50,100);
        setClickArea(txtEquation1,100,100,100,100);
        setClickArea(txtEquation2,100,100,100,100);

    }
    private void setClickArea(final TextView textView, final int t, final int l, final int b, final int r){
        final View parent = (View) textView.getParent();  // button: the view you want to enlarge hit area
        parent.post( new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                textView.getHitRect(rect);
                rect.top -= t;    // increase top hit area
                rect.left -= l;   // increase left hit area
                rect.bottom += b; // increase bottom hit area
                rect.right += r;  // increase right hit area
                parent.setTouchDelegate( new TouchDelegate( rect , textView));
            }
        });
    }
    private class InputListener implements TextView.OnEditorActionListener{
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){
                if (v.getId()==eDInputAnswer.getId()){
                    eDBtnCheck.performClick();
                } else if (v.getId()==inputDenom.getId()) {
                    btnCheck.performClick();
                }
            }
            return false;
        }
    }
}