package com.example.laher.learnfractions.lessons.comparing_fractions;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ComparingFractionsExercise2Activity extends AppCompatActivity {
    Context mContext = this;
    private static final String TAG = "CF_E2";

    Exercise exercise;
    ExerciseStat mExerciseStat;
    final int EXERCISE_NUM = 2;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Comparing Fractions";
    //GUI
    TextView txtNum1, txtNum2, txtDenom1, txtDenom2, txtProduct1, txtProduct2, txtCompareSign, txtScore, txtInstruction;
    Button btnGreater, btnEqual, btnLess;
    //MULTIPLICATION DIALOG
    Dialog multiplicationDialog;
    View mdView;
    TextView diagTxtMultiplicand, diagTxtMultiplier;
    EditText diagInputProduct;
    Button dialogBtnCheck;
    //VARIABLES
    Fraction fractionOne, fractionTwo;
    FractionQuestion fractionQuestion;
    ArrayList<FractionQuestion> fractionQuestions;
    ArrayList<Integer> crossMultiplicationStepList;
    String strAnswer;
    final Handler handler = new Handler();


    int questionNum;
    int correct, error;
    int requiredCorrects;
    int maxErrors;

    long startingTime, endingTime;

    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparing_fractions_exercise2);
        exercise = LessonArchive.getLesson(AppConstants.COMPARING_FRACTIONS).getExercises().get(EXERCISE_NUM-1);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComparingFractionsExercise2Activity.this,
                        ComparingFractionsExerciseActivity.class);
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
                Intent intent = new Intent(ComparingFractionsExercise2Activity.this,
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
        txtNum1 = findViewById(R.id.e2_txtNum1);
        txtNum2 = findViewById(R.id.e2_txtNum2);
        txtDenom1 = findViewById(R.id.e2_txtDenom1);
        txtDenom2 = findViewById(R.id.e2_txtDenom2);
        txtNum1.setOnClickListener(new TxtFractionListener());
        txtNum2.setOnClickListener(new TxtFractionListener());
        txtDenom1.setOnClickListener(new TxtFractionListener());
        txtDenom2.setOnClickListener(new TxtFractionListener());
        txtProduct1 = findViewById(R.id.e2_txtProduct1);
        txtProduct2 = findViewById(R.id.e2_txtProduct2);
        txtCompareSign = findViewById(R.id.e2_txtCompareSign);
        txtScore = findViewById(R.id.e2_txtScore);
        setTxtScore();
        txtInstruction = findViewById(R.id.e2_txtInstruction);
        btnGreater = findViewById(R.id.e2_btnGreater);
        btnEqual = findViewById(R.id.e2_btnEquals);
        btnLess = findViewById(R.id.e2_btnLess);
        btnGreater.setText(FractionQuestion.ANSWER_GREATER);
        btnEqual.setText(FractionQuestion.ANSWER_EQUAL);
        btnLess.setText(FractionQuestion.ANSWER_LESS);
        btnGreater.setOnClickListener(new BtnAnswerListener());
        btnEqual.setOnClickListener(new BtnAnswerListener());
        btnLess.setOnClickListener(new BtnAnswerListener());
        //MULTIPLICATION DIALOG
        mdView = getLayoutInflater().inflate(R.layout.layout_dialog_equation, null);
        multiplicationDialog = new Dialog(ComparingFractionsExercise2Activity.this);
        multiplicationDialog.setOnDismissListener(new DiagMultiplicationListener());
        multiplicationDialog.setOnShowListener(new DiagMultiplicationListener());
        multiplicationDialog.setTitle("Multiplication Equation");
        multiplicationDialog.setContentView(mdView);
        diagTxtMultiplicand = mdView.findViewById(R.id.md_txtMultiplicand);
        diagTxtMultiplier = mdView.findViewById(R.id.md_txtMultiplier);
        diagInputProduct = mdView.findViewById(R.id.md_inputProduct);
        diagInputProduct.setOnEditorActionListener(new InputListener());
        dialogBtnCheck = mdView.findViewById(R.id.md_btnCheck);
        dialogBtnCheck.setOnClickListener(new DiagBtnCheckListener());
        //VARIABLES
        fractionOne = new Fraction();
        fractionTwo = new Fraction();
        fractionQuestion = new FractionQuestion(fractionOne, fractionTwo, FractionQuestion.COMPARING_FRACTION);
        crossMultiplicationStepList = new ArrayList<>();

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
                        Exercise updatedExercise = new ExerciseStat();
                        updatedExercise.setRequiredCorrects(Integer.valueOf(response.optString("required_corrects")));
                        if (response.optString("rc_consecutive").equals("1")) {
                            updatedExercise.setRc_consecutive(true);
                        } else {
                            updatedExercise.setRc_consecutive(false);
                        }
                        updatedExercise.setMaxErrors(Integer.valueOf(response.optString("max_errors")));
                        if (response.optString("me_consecutive").equals("1")) {
                            updatedExercise.setMe_consecutive(true);
                        } else {
                            updatedExercise.setMe_consecutive(false);
                        }
                        setAttributes((ExerciseStat) updatedExercise);
                        startingTime = System.currentTimeMillis();
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
        setup();
    }
    public void setup(){
        txtProduct1.setVisibility(TextView.INVISIBLE);
        txtProduct2.setVisibility(TextView.INVISIBLE);
        txtCompareSign.setText("_");
        txtInstruction.setText("Compare the two fractions.");
        crossMultiplicationStepList.clear();
    }
    public void setFractionQuestions(){
        fractionQuestions = new ArrayList<>();
        questionNum = 0;
        for(int i = 0; i < requiredCorrects; i++){
            if (i < (requiredCorrects /2)){
                fractionQuestion = new FractionQuestion(FractionQuestion.COMPARING_SIMILAR);
                fractionQuestions.add(fractionQuestion);
            } else {
                fractionQuestion = new FractionQuestion(FractionQuestion.COMPARING_DISSIMILAR);
                fractionQuestions.add(fractionQuestion);
            }
        }
        Collections.shuffle(fractionQuestions);
        setTxtFractions();
    }
    public void resetTxtFractionsColor(){
        txtNum1.setTextColor(Color.rgb(128,128,128));
        txtNum2.setTextColor(Color.rgb(128,128,128));
        txtDenom1.setTextColor(Color.rgb(128,128,128));
        txtDenom2.setTextColor(Color.rgb(128,128,128));
    }
    public void setTxtFractions(){
        txtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getNumerator()));
        txtNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getNumerator()));
        txtDenom1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
        txtDenom2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
        strAnswer = fractionQuestions.get(questionNum).getAnswer();
    }
    public void setTxtScore(){
        txtScore.setText(AppConstants.SCORE(correct,requiredCorrects));
    }
    public void correct(){
        correct++;
        if (errorsShouldBeConsecutive) {
            error = 0;
        }
        setTxtScore();
        enableBtnAnswers(false);
        if (correct >= requiredCorrects){
            endingTime = System.currentTimeMillis();
            setFinalAttributes();
            txtInstruction.setText(AppConstants.FINISHED_LESSON);
            btnNext.setEnabled(true);
        } else {
            txtInstruction.setText(AppConstants.CORRECT);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    questionNum++;
                    setTxtFractions();
                    setup();
                    enableBtnAnswers(true);
                }
            }, 2000);
        }
    }
    public void wrong(){
        error++;
        mExerciseStat.incrementError();
        if (correctsShouldBeConsecutive) {
            correct = 0;
        }
        setTxtScore();
        enableBtnAnswers(false);
        if (error >= maxErrors){
            if (errorsShouldBeConsecutive) {
                txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(error));
            } else {
                txtInstruction.setText(AppConstants.FAILED(error));
            }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(ComparingFractionsExercise2Activity.this,
                                    ComparingFractionsExerciseActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }, 3000);
        } else {
            txtInstruction.setText(AppConstants.ERROR);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (correctsShouldBeConsecutive) {
                        go();
                    } else {
                        if (fractionQuestions.get(questionNum).getContext().equals(FractionQuestion.COMPARING_SIMILAR)){
                            fractionQuestion = new FractionQuestion(FractionQuestion.COMPARING_SIMILAR);
                            fractionQuestions.add(fractionQuestion);
                        } else if (fractionQuestions.get(questionNum).getContext().equals(FractionQuestion.COMPARING_DISSIMILAR)){
                            fractionQuestion = new FractionQuestion(FractionQuestion.COMPARING_DISSIMILAR);
                            fractionQuestions.add(fractionQuestion);
                        }
                        questionNum++;
                        setTxtFractions();
                        setup();
                    }
                    enableBtnAnswers(true);
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
    public void diagInputProduct(int num, int denom){

        txtInstruction.setText("Get the product of the clicked numbers.");

        diagTxtMultiplicand.setText(String.valueOf(denom));
        diagTxtMultiplier.setText(String.valueOf(num));
        multiplicationDialog.show();
    }
    public void enableBtnAnswers(boolean bool){
        btnGreater.setEnabled(bool);
        btnEqual.setEnabled(bool);
        btnLess.setEnabled(bool);
    }
    public class BtnAnswerListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            String s = b.getText().toString();
            txtCompareSign.setText(s);
            if (s.equals(fractionQuestions.get(questionNum).getAnswer())){
                correct();
            } else {
                wrong();
            }
        }
    }
    public class TxtFractionListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            crossMultiplicationStepList.add(v.getId());
            if (fractionQuestions.get(questionNum).getContext().equals(FractionQuestion.COMPARING_SIMILAR)){
                crossMultiplicationStepList.clear();
                txtInstruction.setText("Do not use the cross multiplication technique to similar fractions.");
                enableBtnAnswers(false);
                Styles.shakeAnimate(txtNum1);
                Styles.shakeAnimate(txtNum2);
                Styles.shakeAnimate(txtDenom1);
                Styles.shakeAnimate(txtDenom2);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wrong();
                    }
                }, 4000);
            }
            if (crossMultiplicationStepList.size() == 1){
                if (crossMultiplicationStepList.get(0) == txtDenom1.getId()){
                    txtDenom1.setTextColor(Color.rgb(0,255,0));
                } else if (crossMultiplicationStepList.get(0) == txtDenom2.getId()){
                    txtDenom2.setTextColor(Color.rgb(0,255,0));
                } else {
                    crossMultiplicationStepList.remove(0);
                }
            }
            if (crossMultiplicationStepList.size() == 2){
                if (crossMultiplicationStepList.get(0) == txtDenom1.getId()){
                    if (crossMultiplicationStepList.get(1) == txtNum2.getId()){
                        txtNum2.setTextColor(Color.rgb(0,255,0));
                        diagInputProduct(fractionQuestions.get(questionNum).getFractionTwo().getNumerator(),
                                fractionQuestions.get(questionNum).getFractionOne().getDenominator());
                    } else {
                        crossMultiplicationStepList.remove(1);
                    }
                } else if (crossMultiplicationStepList.get(0) == txtDenom2.getId()){
                    if (crossMultiplicationStepList.get(1) == txtNum1.getId()){
                        txtNum1.setTextColor(Color.rgb(0,255,0));
                        diagInputProduct(fractionQuestions.get(questionNum).getFractionOne().getNumerator(),
                                fractionQuestions.get(questionNum).getFractionTwo().getDenominator());
                    } else {
                        crossMultiplicationStepList.remove(1);
                    }
                }
            }
            if (crossMultiplicationStepList.size() == 3){
                if (crossMultiplicationStepList.get(0) == txtDenom1.getId()){
                    if (crossMultiplicationStepList.get(2) == txtDenom2.getId()){
                        txtDenom2.setTextColor(Color.rgb(0,255,0));
                    } else {
                        crossMultiplicationStepList.remove(2);
                    }
                } else if (crossMultiplicationStepList.get(0) == txtDenom2.getId()){
                    if (crossMultiplicationStepList.get(2) == txtDenom1.getId()){
                        txtDenom1.setTextColor(Color.rgb(0,255,0));
                    } else {
                        crossMultiplicationStepList.remove(2);
                    }
                }
            }
            if (crossMultiplicationStepList.size() == 4){
                if (crossMultiplicationStepList.get(0) == txtDenom1.getId()){
                    if (crossMultiplicationStepList.get(3) == txtNum1.getId()){
                        txtNum1.setTextColor(Color.rgb(0,255,0));
                        diagInputProduct(fractionQuestions.get(questionNum).getFractionOne().getNumerator(),
                                fractionQuestions.get(questionNum).getFractionTwo().getDenominator());
                    } else {
                        crossMultiplicationStepList.remove(2);
                    }
                } else if (crossMultiplicationStepList.get(0) == txtDenom2.getId()){
                    if (crossMultiplicationStepList.get(3) == txtNum2.getId()){
                        txtNum2.setTextColor(Color.rgb(0,255,0));
                        diagInputProduct(fractionQuestions.get(questionNum).getFractionTwo().getNumerator(),
                                fractionQuestions.get(questionNum).getFractionOne().getDenominator());
                    } else {
                        crossMultiplicationStepList.remove(2);
                    }
                }
            }
        }
    }

    public class DiagBtnCheckListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int product = Integer.valueOf((String) diagTxtMultiplicand.getText())
                    * Integer.valueOf((String) diagTxtMultiplier.getText());
            if (!diagInputProduct.getText().toString().matches("")) {
                if (Integer.valueOf(String.valueOf(diagInputProduct.getText())) == product) {
                    if (crossMultiplicationStepList.get(crossMultiplicationStepList.size() - 1) == txtNum2.getId()) {
                        if (crossMultiplicationStepList.get(crossMultiplicationStepList.size() - 2) == txtDenom1.getId()) {
                            txtProduct2.setText(diagInputProduct.getText());
                            txtProduct2.setVisibility(TextView.VISIBLE);
                        }
                    } else if (crossMultiplicationStepList.get(crossMultiplicationStepList.size() - 1) == txtNum1.getId()) {
                        if (crossMultiplicationStepList.get(crossMultiplicationStepList.size() - 2) == txtDenom2.getId()) {
                            txtProduct1.setText(diagInputProduct.getText());
                            txtProduct1.setVisibility(TextView.VISIBLE);
                        }
                    }
                    multiplicationDialog.dismiss();
                }
            }
        }
    }
    public class DiagMultiplicationListener implements Dialog.OnDismissListener, DialogInterface.OnShowListener{
        @Override
        public void onDismiss(DialogInterface dialog) {
            resetTxtFractionsColor();
            diagInputProduct.setText("");
            if (crossMultiplicationStepList.size()==2){
                if (txtProduct1.getVisibility()==TextView.INVISIBLE && txtProduct2.getVisibility()==TextView.INVISIBLE){
                    crossMultiplicationStepList.clear();
                }
            }
            if (crossMultiplicationStepList.size()==4){
                if (txtProduct1.getVisibility()==TextView.INVISIBLE || txtProduct2.getVisibility()==TextView.INVISIBLE){
                    crossMultiplicationStepList.remove(crossMultiplicationStepList.size()-1);
                    crossMultiplicationStepList.remove(crossMultiplicationStepList.size()-1);
                }
            }
        }

        @Override
        public void onShow(DialogInterface dialog) {
            diagInputProduct.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
        }
    }

    public class InputListener implements TextView.OnEditorActionListener{
        int product = Integer.valueOf((String) diagTxtMultiplicand.getText())
                * Integer.valueOf((String) diagTxtMultiplier.getText());
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){
                dialogBtnCheck.performClick();
                return Integer.valueOf(String.valueOf(diagInputProduct.getText())) != product;
            }
            return false;
        }
    }
}