package com.example.laher.learnfractions.lessons.dividing_fractions;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.TouchDelegate;
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
import com.example.laher.learnfractions.fraction_util.fraction_questions.DividingFractionsQuestion;
import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class DividingFractionsExerciseActivity extends AppCompatActivity {
    Context mContext = this;

    Exercise exercise;
    final int EXERCISE_NUM = 1;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Dividing Fraction";
    //GUI
    TextView txtNum1, txtNum2, txtNum3, txtNum4, txtDenom1, txtDenom2, txtDenom3, txtDenom4, txtEquation1, txtEquation2
            , txtScore, txtInstruction, txtSign1, txtSign2;
    EditText inputNum, inputDenom;
    Button btnCheck;
    //VARIABLES
    ArrayList<DividingFractionsQuestion> mFractionsQuestions;
    DividingFractionsQuestion mFractionsQuestion;
    int mQuestionNum;

    int correct, error;
    int requiredCorrects;
    int maxErrors;
    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;
    final Handler handler = new Handler();
    ColorStateList defaultColor;
    TextView txtContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_dissimilar_equation);
        exercise = LessonArchive.getLesson(AppConstants.DIVIDING_FRACTIONS).getExercises().get(EXERCISE_NUM-1);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DividingFractionsExerciseActivity.this,
                        DividingFractionsVideoActivity.class);
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
                Intent intent = new Intent(DividingFractionsExerciseActivity.this,
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
        setTxtScore();
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
        setFractionGui();
        setUp();
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
        setAnswerEnabled(false);
        setTxtFractionListener(false);
        if (correct >= requiredCorrects){
            btnNext.setEnabled(true);
            txtInstruction.setText(AppConstants.FINISHED_LESSON);
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
        mQuestionNum++;
        setFractionGui();
        setUp();
    }
    public void wrong(){
        error++;
        if (correctsShouldBeConsecutive) {
            correct = 0;
        }
        setTxtScore();
        setAnswerEnabled(false);
        setTxtFractionListener(false);
        if (error >= maxErrors){
            if (errorsShouldBeConsecutive) {
                txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(error));
            } else {
                txtInstruction.setText(AppConstants.FAILED(error));
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(DividingFractionsExerciseActivity.this,
                            DividingFractionsVideoActivity.class);
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
                        DividingFractionsQuestion fractionsQuestion = new DividingFractionsQuestion();
                        while (mFractionsQuestions.contains(fractionsQuestion)){
                            fractionsQuestion = new DividingFractionsQuestion();
                        }
                        mFractionsQuestions.add(fractionsQuestion);
                        nextQuestion();
                    }
                }
            }, 2000);
        }
    }
    public void setFractionQuestions(){
        mQuestionNum = 1;
        mFractionsQuestions = new ArrayList<>();
        for (int i = 0; i < requiredCorrects; i++){
            DividingFractionsQuestion fractionsQuestion = new DividingFractionsQuestion();
            while (mFractionsQuestions.contains(fractionsQuestion)){
                fractionsQuestion = new DividingFractionsQuestion();
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
        txtDenom3.setText(strNumerator2);
    }
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
                        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                    }
                }
            }
        }catch (NumberFormatException e){}
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
}
