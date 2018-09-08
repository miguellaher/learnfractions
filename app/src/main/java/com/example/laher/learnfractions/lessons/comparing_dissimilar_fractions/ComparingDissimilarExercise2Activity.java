package com.example.laher.learnfractions.lessons.comparing_dissimilar_fractions;

import android.animation.ObjectAnimator;
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
import com.example.laher.learnfractions.fraction_util.fraction_questions.ComparingDissimilarQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class ComparingDissimilarExercise2Activity extends LessonExercise {
    //private static final String TAG = "CD_E2";
    //MULTIPLICATION DIALOG
    Dialog multiplicationDialog;
    View mdView;
    TextView dialogTxtMultiplicand;
    TextView dialogTxtMultiplier;
    EditText dialogInputProduct;
    Button dialogBtnCheck;
    //GUI
    TextView txtScore;
    TextView txtProduct1;
    TextView txtProduct2;
    TextView txtNum1;
    TextView txtNum2;
    TextView txtDenominator1;
    TextView txtDenominator2;
    TextView txtCompareSign;
    TextView txtInstruction;
    Button btnGreater;
    Button btnEquals;
    Button btnLess;
    ImageView imgLine1;
    ImageView imgLine2;
    GifImageView gifAvatar;
    ConstraintLayout constraintLayoutBackground;
    ConstraintLayout constraintLayoutBottom;
    //VARIABLES
    ArrayList<Integer> stepsIdList;

    ArrayList<ComparingDissimilarQuestion> mComparingDissimilarQuestions;
    ComparingDissimilarQuestion mComparingDissimilarQuestion;
    int mQuestionNum;

    public String title = "Comparing Dissimilar ex.2";
    String id = AppIDs.CDE2_ID;

    public ComparingDissimilarExercise2Activity() {
        super();
        Range range = getRange();
        Probability probability = new Probability(Probability.TWO_DISSIMILAR_FRACTIONS, range);
        setProbability(probability);
        setRangeEditable(true);
        setId(id);
        setExerciseTitle(title);
    }

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comparing_dissimilar_exercise2);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);
        Range range = getRange();
        Probability probability = new Probability(Probability.TWO_DISSIMILAR_FRACTIONS, range);
        setProbability(probability);
        setRangeEditable(true);
        //TOOLBAR
        Styles.bgPaintMainYellow(buttonBack); // SPECIAL CASE - colors   are similar
        //MULTIPLICATION DIALOG
        mdView = getLayoutInflater().inflate(R.layout.layout_dialog_equation, null);
        multiplicationDialog = new Dialog(ComparingDissimilarExercise2Activity.this);
        multiplicationDialog.setOnDismissListener(new DialogMultiplicationListener());
        multiplicationDialog.setOnShowListener(new DialogMultiplicationListener());
        multiplicationDialog.setTitle("Multiplication Equation");
        multiplicationDialog.setContentView(mdView);
        dialogTxtMultiplicand = mdView.findViewById(R.id.md_txtMultiplicand);
        dialogTxtMultiplier = mdView.findViewById(R.id.md_txtMultiplier);
        dialogInputProduct = mdView.findViewById(R.id.md_inputProduct);
        dialogInputProduct.setOnEditorActionListener(new InputListener());
        dialogBtnCheck = mdView.findViewById(R.id.md_btnCheck);
        dialogBtnCheck.setOnClickListener(new DiagBtnCheckListener());
        //GUI
        txtScore = findViewById(R.id.d2_txtScore);
        txtProduct1 = findViewById(R.id.d2_txtProduct1);
        txtProduct2 = findViewById(R.id.d2_txtProduct2);
        txtNum1 = findViewById(R.id.d2_txtNum1);
        txtNum2 = findViewById(R.id.d2_txtNum2);
        txtDenominator1 = findViewById(R.id.d2_txtDenom1);
        txtDenominator2 = findViewById(R.id.d2_txtDenom2);
        txtCompareSign = findViewById(R.id.d2_txtCompareSign);
        resetTxtFractionsColor();
        txtInstruction = findViewById(R.id.d2_txtInstruction);
        stepsIdList = new ArrayList<>();
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

        btnGreater = findViewById(R.id.d2_btnGreater);
        btnEquals = findViewById(R.id.d2_btnEquals);
        btnLess = findViewById(R.id.d2_btnLess);
        btnGreater.setOnClickListener(new BtnListener());
        btnEquals.setOnClickListener(new BtnListener());
        btnLess.setOnClickListener(new BtnListener());
        Styles.bgPaintMainBlue(btnGreater);
        Styles.bgPaintMainYellow(btnEquals);
        Styles.bgPaintMainOrange(btnLess);

        startExercise();
    }
    public void setup(){
        enableBtnCompareSign(false);
        txtCompareSign.setText("_");
        String instruction = "Click a denominator.";
        txtInstruction.setText(instruction);
    }
    private void generateFractionQuestions(){
        mQuestionNum = 1;
        mComparingDissimilarQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        Range range = getRange();
        for (int i = 0; i < requiredCorrects; i++){
            ComparingDissimilarQuestion comparingDissimilarQuestion = new ComparingDissimilarQuestion(range);
            while (mComparingDissimilarQuestions.contains(comparingDissimilarQuestion)){
                comparingDissimilarQuestion = new ComparingDissimilarQuestion(range);
            }
            mComparingDissimilarQuestions.add(comparingDissimilarQuestion);
        }
        setGuiFractions();
    }
    public void enableBtnCompareSign(boolean bool){
        btnGreater.setEnabled(bool);
        btnEquals.setEnabled(bool);
        btnLess.setEnabled(bool);
    }
    public void setGuiFractions(){
        mComparingDissimilarQuestion = mComparingDissimilarQuestions.get(mQuestionNum-1);
        Fraction fraction1 = mComparingDissimilarQuestion.getFraction1();
        Fraction fraction2 = mComparingDissimilarQuestion.getFraction2();
        int numerator1 = fraction1.getNumerator();
        int numerator2 = fraction2.getNumerator();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        txtNum1.setText(String.valueOf(numerator1));
        txtDenominator1.setText(String.valueOf(denominator1));
        txtNum2.setText(String.valueOf(numerator2));
        txtDenominator2.setText(String.valueOf(denominator2));
        txtNum1.setOnClickListener(new TxtFractionListener());
        txtNum2.setOnClickListener(new TxtFractionListener());
        txtDenominator1.setOnClickListener(new TxtFractionListener());
        txtDenominator2.setOnClickListener(new TxtFractionListener());
        txtProduct1.setVisibility(TextView.INVISIBLE);
        txtProduct2.setVisibility(TextView.INVISIBLE);
    }
    public void shakeAnimate(TextView textview){
        ObjectAnimator.ofFloat(textview, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                .setDuration(1000)
                .start();
    }
    public void dialogInputProduct(int num, int denominator){
        String instruction = "Get the product of the clicked numbers.";
        txtInstruction.setText(instruction);

        dialogTxtMultiplicand.setText(String.valueOf(denominator));
        dialogTxtMultiplier.setText(String.valueOf(num));
        multiplicationDialog.show();
    }
    public void resetTxtFractionsColor(){
        txtNum1.setTextColor(Color.rgb(128,128,128));
        txtNum2.setTextColor(Color.rgb(128,128,128));
        txtDenominator1.setTextColor(Color.rgb(128,128,128));
        txtDenominator2.setTextColor(Color.rgb(128,128,128));
    }
    public void removeTxtListeners(){
        txtNum1.setOnClickListener(null);
        txtNum2.setOnClickListener(null);
        txtDenominator1.setOnClickListener(null);
        txtDenominator2.setOnClickListener(null);
    }
    public void check(String compareSign){
        Fraction fraction1 = mComparingDissimilarQuestion.getFraction1();
        Fraction fraction2 = mComparingDissimilarQuestion.getFraction2();
        Fraction fractionAnswer = mComparingDissimilarQuestion.getFractionAnswer();
        if (compareSign.equals(AppConstants.GREATER_THAN)){
            if (fractionAnswer.equals(fraction1)) {
                correct();
            } else {
                wrong();
            }
        }
        if (compareSign.equals(AppConstants.EQUAL_TO)){
            if (fractionAnswer.equals(AppConstants.EQUAL_FRACTIONS)) {
                correct();
            } else {
                wrong();
            }
        }
        if (compareSign.equals(AppConstants.LESS_THAN)){
            if (fractionAnswer.equals(fraction2)) {
                correct();
            } else {
                wrong();
            }
        }
    }
    public class TxtFractionListener implements TextView.OnClickListener {
        @Override
        public void onClick(View v) {
            stepsIdList.add(v.getId());
            Fraction fraction1 = mComparingDissimilarQuestion.getFraction1();
            Fraction fraction2 = mComparingDissimilarQuestion.getFraction2();
            int numerator1 = fraction1.getNumerator();
            int numerator2 = fraction2.getNumerator();
            int denominator1 = fraction1.getDenominator();
            int denominator2 = fraction2.getDenominator();
            if (stepsIdList.size() == 1){
                if (stepsIdList.get(0) == txtDenominator1.getId()){
                    txtDenominator1.setTextColor(Color.rgb(0,255,0));
                    String instruction = "Click the numerator of the second fraction.";
                    txtInstruction.setText(instruction);
                } else if (stepsIdList.get(0) == txtDenominator2.getId()){
                    txtDenominator2.setTextColor(Color.rgb(0,255,0));
                    String instruction = "Click the numerator of the first fraction.";
                    txtInstruction.setText(instruction);
                } else {
                    //wrong
                    shakeAnimate(txtDenominator1);
                    shakeAnimate(txtDenominator2);
                    stepsIdList.remove(0);
                }
            }
            if (stepsIdList.size() == 2){
                if (stepsIdList.get(0) == txtDenominator2.getId()){
                    if (stepsIdList.get(1) == txtNum1.getId()) {
                        txtNum1.setTextColor(Color.rgb(0, 255, 0));
                        //dialogInputProduct(Integer.valueOf((String) txtNum1.getText()),
                        //        Integer.valueOf((String) txtDenominator2.getText()));
                        dialogInputProduct(numerator1, denominator2);
                    } else {
                        shakeAnimate(txtNum1);
                        stepsIdList.remove(1);
                    }
                } else if (stepsIdList.get(0) == txtDenominator1.getId()){
                    if (stepsIdList.get(1) == txtNum2.getId()) {
                        txtNum2.setTextColor(Color.rgb(0, 255, 0));
                        //dialogInputProduct(Integer.valueOf((String) txtNum2.getText()),
                        //        Integer.valueOf((String) txtDenominator1.getText()));
                        dialogInputProduct(numerator2, denominator1);
                    } else {
                        shakeAnimate(txtNum2);
                        stepsIdList.remove(1);
                    }
                }
            }
            if (stepsIdList.size()==3){
                if (stepsIdList.get(0) == txtDenominator1.getId()){
                    if (stepsIdList.get(1) == txtNum2.getId()){
                        if (stepsIdList.get(2) == txtDenominator2.getId()){
                            txtDenominator2.setTextColor(Color.rgb(0, 255, 0));
                            String instruction = "Click the numerator of the first fraction.";
                            txtInstruction.setText(instruction);
                        } else {
                            shakeAnimate(txtDenominator2);
                            stepsIdList.remove(2);
                        }
                    }
                } else if (stepsIdList.get(0) == txtDenominator2.getId()){
                    if (stepsIdList.get(1) == txtNum1.getId()){
                        if (stepsIdList.get(2) == txtDenominator1.getId()){
                            txtDenominator1.setTextColor(Color.rgb(0, 255, 0));
                            String instruction = "Click the numerator of the second fraction.";
                            txtInstruction.setText(instruction);
                        } else {
                            shakeAnimate(txtDenominator1);
                            stepsIdList.remove(2);
                        }
                    }

                }
            }
            if (stepsIdList.size()==4){
                if (stepsIdList.get(0) == txtDenominator1.getId()){
                    if (stepsIdList.get(1) == txtNum2.getId()){
                        if (stepsIdList.get(2) == txtDenominator2.getId()){
                            if (stepsIdList.get(3) == txtNum1.getId()){
                                txtNum1.setTextColor(Color.rgb(0, 255, 0));
                                dialogInputProduct(numerator1, denominator2);
                            } else {
                                shakeAnimate(txtNum1);
                                stepsIdList.remove(3);
                            }
                        }
                    }
                } else if (stepsIdList.get(0) == txtDenominator2.getId()){
                    if (stepsIdList.get(1) == txtNum1.getId()){
                        if (stepsIdList.get(2) == txtDenominator1.getId()){
                            if (stepsIdList.get(3) == txtNum2.getId()){
                                txtNum2.setTextColor(Color.rgb(0, 255, 0));
                                dialogInputProduct(numerator2, denominator1);
                            } else {
                                shakeAnimate(txtNum2);
                                stepsIdList.remove(3);
                            }
                        }
                    }
                }
            }
        }
    }
    public class DiagBtnCheckListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            int product = Integer.valueOf((String) dialogTxtMultiplicand.getText())
                    * Integer.valueOf((String) dialogTxtMultiplier.getText());
            if (!dialogInputProduct.getText().toString().matches("")) {
                if (Integer.valueOf(String.valueOf(dialogInputProduct.getText())) == product) {
                    if (stepsIdList.get(stepsIdList.size() - 1) == txtNum2.getId()) {
                        if (stepsIdList.get(stepsIdList.size() - 2) == txtDenominator1.getId()) {
                            txtProduct2.setText(dialogInputProduct.getText());
                            txtProduct2.setVisibility(TextView.VISIBLE);
                        }
                    } else if (stepsIdList.get(stepsIdList.size() - 1) == txtNum1.getId()) {
                        if (stepsIdList.get(stepsIdList.size() - 2) == txtDenominator2.getId()) {
                            txtProduct1.setText(dialogInputProduct.getText());
                            txtProduct1.setVisibility(TextView.VISIBLE);
                        }
                    }
                    multiplicationDialog.dismiss();
                } else {
                    shakeAnimate(dialogInputProduct);
                }
                if (txtProduct1.getVisibility() == TextView.VISIBLE
                        && txtProduct2.getVisibility() == TextView.VISIBLE) {
                    enableBtnCompareSign(true);
                    String instruction = "Compare the two products.";
                    txtInstruction.setText(instruction);
                } else {
                    String instruction = "Click the other denominator.";
                    txtInstruction.setText(instruction);
                }
            } else {
                shakeAnimate(dialogInputProduct);
            }
        }
    }
    public class BtnListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == btnGreater.getId()){
                txtCompareSign.setText(AppConstants.GREATER_THAN);
                check(AppConstants.GREATER_THAN);
            }
            if (v.getId() == btnEquals.getId()){
                txtCompareSign.setText(AppConstants.EQUAL_TO);
                check(AppConstants.EQUAL_TO);
            }
            if (v.getId() == btnLess.getId()){
                txtCompareSign.setText(AppConstants.LESS_THAN);
                check(AppConstants.LESS_THAN);
            }
        }
    }
    public class DialogMultiplicationListener implements Dialog.OnDismissListener, DialogInterface.OnShowListener{
        @Override
        public void onDismiss(DialogInterface dialog) {
            resetTxtFractionsColor();
            dialogInputProduct.setText("");
            if (stepsIdList.size()==2){
                if (txtProduct1.getVisibility()==TextView.INVISIBLE && txtProduct2.getVisibility()==TextView.INVISIBLE){
                    stepsIdList.clear();
                    String instruction = "Click a denominator.";
                    txtInstruction.setText(instruction);
                }
            }
            if (stepsIdList.size()==4){
                if (txtProduct1.getVisibility()==TextView.INVISIBLE || txtProduct2.getVisibility()==TextView.INVISIBLE){
                    stepsIdList.remove(stepsIdList.size()-1);
                    stepsIdList.remove(stepsIdList.size()-1);
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
        setup();
        generateFractionQuestions();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();stepsIdList.clear();
        removeTxtListeners();
        enableBtnCompareSign(false);

    }

    @Override
    protected void postAnswered() {
        super.postAnswered();
        setup();
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
        setGuiFractions();
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
            setup();
            generateFractionQuestions();
        } else {
            ComparingDissimilarQuestion comparingDissimilarQuestion = new ComparingDissimilarQuestion(range);
            int questionsSize = mComparingDissimilarQuestions.size();
            int maxItemSize = getMaxItemSize();
            while (mComparingDissimilarQuestions.contains(comparingDissimilarQuestion) && questionsSize<maxItemSize){
                comparingDissimilarQuestion = new ComparingDissimilarQuestion(range);
            }
            mComparingDissimilarQuestions.add(comparingDissimilarQuestion);
            setup();
            mQuestionNum++;
            setGuiFractions();
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
