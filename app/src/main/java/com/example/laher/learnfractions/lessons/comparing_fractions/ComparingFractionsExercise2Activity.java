package com.example.laher.learnfractions.lessons.comparing_fractions;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import com.example.laher.learnfractions.fraction_util.FractionQuestion;
import com.example.laher.learnfractions.fraction_util.fraction_questions.ComparingFractionsQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;
import java.util.Collections;

import pl.droidsonroids.gif.GifImageView;

public class ComparingFractionsExercise2Activity extends LessonExercise {
    //private static final String TAG = "CF_E2";
    //GUI
    TextView txtNum1;
    TextView txtNum2;
    TextView txtDenominator1;
    TextView txtDenominator2;
    TextView txtProduct1;
    TextView txtProduct2;
    TextView txtCompareSign;
    TextView txtScore;
    TextView txtInstruction;
    Button btnGreater;
    Button btnEqual;
    Button btnLess;
    ImageView imgLine1;
    ImageView imgLine2;
    GifImageView gifAvatar;
    ConstraintLayout constraintLayoutBackground;
    ConstraintLayout constraintLayoutBottom;
    //MULTIPLICATION DIALOG
    Dialog multiplicationDialog;
    View mdView;
    TextView dialogTxtMultiplicand;
    TextView dialogTxtMultiplier;
    EditText dialogInputProduct;
    Button dialogBtnCheck;
    //VARIABLES
    ArrayList<ComparingFractionsQuestion> mComparingFractionsQuestions;
    ComparingFractionsQuestion mComparingFractionsQuestion;
    int mQuestionNum;

    ArrayList<Integer> crossMultiplicationStepList;
    String strAnswer;

    public String title = "Comparing Fractions ex.2";
    String id = AppIDs.CFE2_ID;

    public ComparingFractionsExercise2Activity() {
        super();
        Range range = getRange();
        Probability probability = new Probability(Probability.COMPARING_FRACTIONS, range);
        setProbability(probability);
        setRangeEditable(true);
        setId(id);
        setExerciseTitle(title);
    }

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comparing_fractions_exercise2);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);
        Range range = getRange();
        Probability probability = new Probability(Probability.COMPARING_FRACTIONS, range);
        setProbability(probability);
        setRangeEditable(true);
        //TOOLBAR
        Styles.bgPaintMainYellow(buttonBack); // SPECIAL CASE - colors are similar
        //GUI
        txtNum1 = findViewById(R.id.e2_txtNum1);
        txtNum2 = findViewById(R.id.e2_txtNum2);
        txtDenominator1 = findViewById(R.id.e2_txtDenom1);
        txtDenominator2 = findViewById(R.id.e2_txtDenom2);
        txtNum1.setOnClickListener(new TxtFractionListener());
        txtNum2.setOnClickListener(new TxtFractionListener());
        txtDenominator1.setOnClickListener(new TxtFractionListener());
        txtDenominator2.setOnClickListener(new TxtFractionListener());
        txtProduct1 = findViewById(R.id.e2_txtProduct1);
        txtProduct2 = findViewById(R.id.e2_txtProduct2);
        txtCompareSign = findViewById(R.id.e2_txtCompareSign);
        txtScore = findViewById(R.id.e2_txtScore);
        txtInstruction = findViewById(R.id.e2_txtInstruction);

        btnGreater = findViewById(R.id.e2_btnGreater);
        btnEqual = findViewById(R.id.e2_btnEquals);
        btnLess = findViewById(R.id.e2_btnLess);
        Styles.bgPaintMainBlue(btnGreater);
        Styles.bgPaintMainYellow(btnEqual);
        Styles.bgPaintMainOrange(btnLess);

        btnGreater.setText(FractionQuestion.ANSWER_GREATER);
        btnEqual.setText(FractionQuestion.ANSWER_EQUAL);
        btnLess.setText(FractionQuestion.ANSWER_LESS);
        btnGreater.setOnClickListener(new BtnAnswerListener());
        btnEqual.setOnClickListener(new BtnAnswerListener());
        btnLess.setOnClickListener(new BtnAnswerListener());
        imgLine1 = findViewById(R.id.imgLine1);
        imgLine2 = findViewById(R.id.imgLine2);
        imgLine1.setImageResource(R.drawable.line);
        imgLine2.setImageResource(R.drawable.line);

        gifAvatar = findViewById(R.id.gifAvatar);
        int gifID = R.drawable.summer_frits;
        gifAvatar.setImageResource(gifID);

        constraintLayoutBackground = findViewById(R.id.constraintLayoutBackground);
        constraintLayoutBackground.setBackgroundResource(R.drawable.beach_background);

        constraintLayoutBottom = findViewById(R.id.constraintLayoutBottom);
        constraintLayoutBottom.setBackgroundResource(R.drawable.beach_bottom);

        int resourceID = R.drawable.beach_toolbar;
        setToolBarBackground(resourceID);

        //MULTIPLICATION DIALOG
        mdView = getLayoutInflater().inflate(R.layout.layout_dialog_equation, null);
        multiplicationDialog = new Dialog(ComparingFractionsExercise2Activity.this);
        multiplicationDialog.setOnDismissListener(new DiagMultiplicationListener());
        multiplicationDialog.setOnShowListener(new DiagMultiplicationListener());
        multiplicationDialog.setTitle("Multiplication Equation");
        multiplicationDialog.setContentView(mdView);
        dialogTxtMultiplicand = mdView.findViewById(R.id.md_txtMultiplicand);
        dialogTxtMultiplier = mdView.findViewById(R.id.md_txtMultiplier);
        dialogInputProduct = mdView.findViewById(R.id.md_inputProduct);
        dialogInputProduct.setOnEditorActionListener(new InputListener());
        dialogBtnCheck = mdView.findViewById(R.id.md_btnCheck);
        dialogBtnCheck.setOnClickListener(new DiagBtnCheckListener());
        //VARIABLES

        crossMultiplicationStepList = new ArrayList<>();

        startExercise();
    }
    public void setup(){
        txtProduct1.setVisibility(TextView.INVISIBLE);
        txtProduct2.setVisibility(TextView.INVISIBLE);
        txtCompareSign.setText("_");
        String instruction = "Compare the two fractions.";
        txtInstruction.setText(instruction);
        crossMultiplicationStepList.clear();
    }
    private void setFractionQuestions(){
        mQuestionNum = 1;
        mComparingFractionsQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        Range range = getRange();
        for (int i = 0; i < requiredCorrects; i++){
            ComparingFractionsQuestion comparingFractionsQuestion;
            if (i<requiredCorrects/2){
                comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.SIMILAR,range);
            } else {
                comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.DISSIMILAR,range);
            }
            while (mComparingFractionsQuestions.contains(comparingFractionsQuestion)){
                if (i<requiredCorrects/2){
                    comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.SIMILAR,range);
                } else {
                    comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.DISSIMILAR,range);
                }
            }
            mComparingFractionsQuestions.add(comparingFractionsQuestion);
        }
        Collections.shuffle(mComparingFractionsQuestions);
        setTxtFraction();
    }
    public void resetTxtFractionsColor(){
        txtNum1.setTextColor(Color.rgb(128,128,128));
        txtNum2.setTextColor(Color.rgb(128,128,128));
        txtDenominator1.setTextColor(Color.rgb(128,128,128));
        txtDenominator2.setTextColor(Color.rgb(128,128,128));
    }
    public void setTxtFraction() {
        mComparingFractionsQuestion = mComparingFractionsQuestions.get(mQuestionNum-1);
        Fraction fraction1 = mComparingFractionsQuestion.getFraction1();
        Fraction fraction2 = mComparingFractionsQuestion.getFraction2();
        int numerator1 = fraction1.getNumerator();
        int numerator2 = fraction2.getNumerator();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        txtNum1.setText(String.valueOf(numerator1));
        txtDenominator1.setText(String.valueOf(denominator1));
        txtNum2.setText(String.valueOf(numerator2));
        txtDenominator2.setText(String.valueOf(denominator2));
        if (fraction1.compare(fraction2)>0){
            strAnswer = AppConstants.GREATER_THAN;
        } else if (fraction1.compare(fraction2)<0){
            strAnswer = AppConstants.LESS_THAN;
        } else if (fraction1.compare(fraction2)==0){
            strAnswer = AppConstants.EQUAL_TO;
        }
    }
    public void diagInputProduct(int num, int denom){
        String instruction = "Get the product of the clicked numbers.";
        txtInstruction.setText(instruction);

        dialogTxtMultiplicand.setText(String.valueOf(denom));
        dialogTxtMultiplier.setText(String.valueOf(num));
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
            if (s.equals(strAnswer)){
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
            Fraction fraction1 = mComparingFractionsQuestion.getFraction1();
            Fraction fraction2 = mComparingFractionsQuestion.getFraction2();
            int numerator1 = fraction1.getNumerator();
            int numerator2 = fraction2.getNumerator();
            int denominator1 = fraction1.getDenominator();
            int denominator2 = fraction2.getDenominator();
            if (mComparingFractionsQuestion.getModifier().equals(ComparingFractionsQuestion.SIMILAR)){
                crossMultiplicationStepList.clear();
                String instruction = "It's not necessary to use the cross multiplication technique to similar fractions.";
                txtInstruction.setText(instruction);
                enableBtnAnswers(false);
                Styles.shakeAnimate(txtNum1);
                Styles.shakeAnimate(txtNum2);
                Styles.shakeAnimate(txtDenominator1);
                Styles.shakeAnimate(txtDenominator2);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wrong();
                    }
                }, 4000);
            }
            if (crossMultiplicationStepList.size() == 1){
                if (crossMultiplicationStepList.get(0) == txtDenominator1.getId()){
                    txtDenominator1.setTextColor(Color.rgb(0,255,0));
                } else if (crossMultiplicationStepList.get(0) == txtDenominator2.getId()){
                    txtDenominator2.setTextColor(Color.rgb(0,255,0));
                } else {
                    crossMultiplicationStepList.remove(0);
                }
            }
            if (crossMultiplicationStepList.size() == 2){
                if (crossMultiplicationStepList.get(0) == txtDenominator1.getId()){
                    if (crossMultiplicationStepList.get(1) == txtNum2.getId()){
                        txtNum2.setTextColor(Color.rgb(0,255,0));
                        diagInputProduct(numerator2, denominator1);
                    } else {
                        crossMultiplicationStepList.remove(1);
                    }
                } else if (crossMultiplicationStepList.get(0) == txtDenominator2.getId()){
                    if (crossMultiplicationStepList.get(1) == txtNum1.getId()){
                        txtNum1.setTextColor(Color.rgb(0,255,0));
                        diagInputProduct(numerator1, denominator2);
                    } else {
                        crossMultiplicationStepList.remove(1);
                    }
                }
            }
            if (crossMultiplicationStepList.size() == 3){
                if (crossMultiplicationStepList.get(0) == txtDenominator1.getId()){
                    if (crossMultiplicationStepList.get(2) == txtDenominator2.getId()){
                        txtDenominator2.setTextColor(Color.rgb(0,255,0));
                    } else {
                        crossMultiplicationStepList.remove(2);
                    }
                } else if (crossMultiplicationStepList.get(0) == txtDenominator2.getId()){
                    if (crossMultiplicationStepList.get(2) == txtDenominator1.getId()){
                        txtDenominator1.setTextColor(Color.rgb(0,255,0));
                    } else {
                        crossMultiplicationStepList.remove(2);
                    }
                }
            }
            if (crossMultiplicationStepList.size() == 4){
                if (crossMultiplicationStepList.get(0) == txtDenominator1.getId()){
                    if (crossMultiplicationStepList.get(3) == txtNum1.getId()){
                        txtNum1.setTextColor(Color.rgb(0,255,0));
                        diagInputProduct(numerator1, denominator2);
                    } else {
                        crossMultiplicationStepList.remove(2);
                    }
                } else if (crossMultiplicationStepList.get(0) == txtDenominator2.getId()){
                    if (crossMultiplicationStepList.get(3) == txtNum2.getId()){
                        txtNum2.setTextColor(Color.rgb(0,255,0));
                        diagInputProduct(numerator2, denominator1);
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
            int product = Integer.valueOf((String) dialogTxtMultiplicand.getText())
                    * Integer.valueOf((String) dialogTxtMultiplier.getText());
            if (!dialogInputProduct.getText().toString().matches("")) {
                if (Integer.valueOf(String.valueOf(dialogInputProduct.getText())) == product) {
                    if (crossMultiplicationStepList.get(crossMultiplicationStepList.size() - 1) == txtNum2.getId()) {
                        if (crossMultiplicationStepList.get(crossMultiplicationStepList.size() - 2) == txtDenominator1.getId()) {
                            txtProduct2.setText(dialogInputProduct.getText());
                            txtProduct2.setVisibility(TextView.VISIBLE);
                        }
                    } else if (crossMultiplicationStepList.get(crossMultiplicationStepList.size() - 1) == txtNum1.getId()) {
                        if (crossMultiplicationStepList.get(crossMultiplicationStepList.size() - 2) == txtDenominator2.getId()) {
                            txtProduct1.setText(dialogInputProduct.getText());
                            txtProduct1.setVisibility(TextView.VISIBLE);
                        }
                    }
                    multiplicationDialog.dismiss();
                } else {
                    Styles.shakeAnimate(dialogInputProduct);
                }
            }
        }
    }
    public class DiagMultiplicationListener implements Dialog.OnDismissListener, DialogInterface.OnShowListener{
        @Override
        public void onDismiss(DialogInterface dialog) {
            resetTxtFractionsColor();
            dialogInputProduct.setText("");
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
            dialogInputProduct.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
        }
    }

    public class InputListener implements TextView.OnEditorActionListener{
        int product = Integer.valueOf((String) dialogTxtMultiplicand.getText())
                * Integer.valueOf((String) dialogTxtMultiplier.getText());
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){
                dialogBtnCheck.performClick();
                return Integer.valueOf(String.valueOf(dialogInputProduct.getText())) != product;
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
        setFractionQuestions();
        setup();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        enableBtnAnswers(false);
    }

    @Override
    protected void postAnswered() {
        super.postAnswered();
        setup();
        enableBtnAnswers(true);
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
        setTxtFraction();
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
        Range range = getRange();
        if (correctsShouldBeConsecutive) {
            setFractionQuestions();
            setup();
        } else {
            mComparingFractionsQuestion = mComparingFractionsQuestions.get(mQuestionNum-1);
            ComparingFractionsQuestion comparingFractionsQuestion = null;
            if (mComparingFractionsQuestion.getModifier().equals(ComparingFractionsQuestion.SIMILAR)){
                comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.SIMILAR,range);
            } else if (mComparingFractionsQuestion.getModifier().equals(ComparingFractionsQuestion.DISSIMILAR)){
                comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.SIMILAR,range);
            }
            int similarFractionsSize = 0;
            int dissimilarFractionsSize = 0;
            for (ComparingFractionsQuestion fractionQuestion : mComparingFractionsQuestions){
                String modifier = fractionQuestion.getModifier();
                if (modifier.equals(ComparingFractionsQuestion.SIMILAR)){
                    similarFractionsSize++;
                } else if (modifier.equals(ComparingFractionsQuestion.DISSIMILAR)){
                    dissimilarFractionsSize++;
                }
            }
            int maxItemSize = getMaxItemSize()/2;
            if (mComparingFractionsQuestion.getModifier().equals(ComparingFractionsQuestion.SIMILAR)){
                comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.SIMILAR,range);
                while (mComparingFractionsQuestions.contains(comparingFractionsQuestion) && similarFractionsSize<maxItemSize) {
                    comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.SIMILAR,range);
                }
            } else if (mComparingFractionsQuestion.getModifier().equals(ComparingFractionsQuestion.DISSIMILAR)){
                comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.DISSIMILAR,range);
                while (mComparingFractionsQuestions.contains(comparingFractionsQuestion) && dissimilarFractionsSize<maxItemSize) {
                    comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.DISSIMILAR,range);
                }
            }
            mComparingFractionsQuestions.add(comparingFractionsQuestion);
            mQuestionNum++;
            setTxtFraction();
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