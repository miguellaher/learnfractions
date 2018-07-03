package com.example.laher.learnfractions.lessons.comparing_fractions;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestion;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;
import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ComparingFractionsExercise2Activity extends AppCompatActivity {
    Context mContext = this;

    Exercise exercise;
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
    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparing_fractions_exercise2);
        exercise = LessonArchive.getLesson(AppConstants.COMPARING_FRACTIONS).getExercises().get(EXERCISE_NUM-1);

        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComparingFractionsExercise2Activity.this,
                        ComparingFractionsExerciseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = (Button) findViewById(R.id.btnNext);
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
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        btnNext.setText(AppConstants.DONE);
        //GUI
        txtNum1 = (TextView) findViewById(R.id.e2_txtNum1);
        txtNum2 = (TextView) findViewById(R.id.e2_txtNum2);
        txtDenom1 = (TextView) findViewById(R.id.e2_txtDenom1);
        txtDenom2 = (TextView) findViewById(R.id.e2_txtDenom2);
        txtNum1.setOnClickListener(new TxtFractionListener());
        txtNum2.setOnClickListener(new TxtFractionListener());
        txtDenom1.setOnClickListener(new TxtFractionListener());
        txtDenom2.setOnClickListener(new TxtFractionListener());
        txtProduct1 = (TextView) findViewById(R.id.e2_txtProduct1);
        txtProduct2 = (TextView) findViewById(R.id.e2_txtProduct2);
        txtCompareSign = (TextView) findViewById(R.id.e2_txtCompareSign);
        txtScore = (TextView) findViewById(R.id.e2_txtScore);
        setTxtScore();
        txtInstruction = (TextView) findViewById(R.id.e2_txtInstruction);
        btnGreater = (Button) findViewById(R.id.e2_btnGreater);
        btnEqual = (Button) findViewById(R.id.e2_btnEquals);
        btnLess = (Button) findViewById(R.id.e2_btnLess);
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
        multiplicationDialog.setTitle("Multiplication Equation");
        multiplicationDialog.setContentView(mdView);
        diagTxtMultiplicand = (TextView) mdView.findViewById(R.id.md_txtMultiplicand);
        diagTxtMultiplier = (TextView) mdView.findViewById(R.id.md_txtMultiplier);
        diagInputProduct = (EditText) mdView.findViewById(R.id.md_inputProduct);
        dialogBtnCheck = (Button) mdView.findViewById(R.id.md_btnCheck);
        dialogBtnCheck.setOnClickListener(new DiagBtnCheckListener());
        //VARIABLES
        fractionOne = new Fraction();
        fractionTwo = new Fraction();
        fractionQuestion = new FractionQuestion(fractionOne, fractionTwo, FractionQuestion.COMPARING_FRACTION);
        crossMultiplicationStepList = new ArrayList<>();

        setAttributes(exercise);
        checkUpdate();

        go();
    }

    public void setAttributes(Exercise exerciseAtt){
        requiredCorrects = exerciseAtt.getRequiredCorrects();
        maxErrors = exerciseAtt.getMaxErrors();
        correctsShouldBeConsecutive = exerciseAtt.isRc_consecutive();
        errorsShouldBeConsecutive = exerciseAtt.isMe_consecutive();
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
                            Util.toast(mContext,"Exercise updated.");
                            Exercise updatedExercise = new Exercise();
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
                            setAttributes(updatedExercise);
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
                        if (fractionQuestions.get(questionNum).getContext()==FractionQuestion.COMPARING_SIMILAR){
                            fractionQuestion = new FractionQuestion(FractionQuestion.COMPARING_SIMILAR);
                            fractionQuestions.add(fractionQuestion);
                        } else if (fractionQuestions.get(questionNum).getContext()==FractionQuestion.COMPARING_DISSIMILAR){
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
            if (s == fractionQuestions.get(questionNum).getAnswer()){
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
            if (fractionQuestions.get(questionNum).getContext()==FractionQuestion.COMPARING_SIMILAR){
                crossMultiplicationStepList.clear();
                txtInstruction.setText("Do not use the cross multiplication technique to similar fractions.");
                enableBtnAnswers(false);
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
    public class DiagMultiplicationListener implements Dialog.OnDismissListener{
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
    }
}