package com.example.laher.learnfractions.lessons.solving_mixed2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.KeyEvent;
import android.view.TouchDelegate;
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
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;
import com.example.laher.learnfractions.fraction_util.MixedFraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.DividingMixedFractionsQuestion;
import com.example.laher.learnfractions.fraction_util.fraction_questions.MultiplyingMixedFractionsQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.DialogInterface.OnDismissListener;
import static android.content.DialogInterface.OnShowListener;

public class SolvingMixed2ExerciseActivity extends LessonExercise {
    //GUI
    TextView txtNum1;
    TextView txtNum2;
    TextView txtNum3;
    TextView txtNum4;
    TextView txtDenom1;
    TextView txtDenom2;
    TextView txtDenom3;
    TextView txtDenom4;
    TextView txtWholeNum1;
    TextView txtWholeNum2;
    TextView txtSign1;
    TextView txtSign2;
    TextView txtEquation1;
    TextView txtEquation2;
    TextView txtScore;
    TextView txtInstruction;
    EditText inputNum, inputDenom;
    Button btnCheck;
    ConstraintLayout clFraction1;
    ConstraintLayout clFraction2;
    ImageView imgLine1;
    ImageView imgLine2;
    ImageView imgLine3;
    ImageView imgLine4;
    ImageView imgLine5;
    ImageView imgAvatar;
    //MIXED CONVERT DIALOG
    Dialog dialogMixedConvert;
    View viewMixedConvert;
    TextView mcD_txtWholeNum;
    TextView mcD_txtNum1;
    TextView mcD_txtNum2;
    TextView mcD_txtDenom1;
    TextView mcD_txtDenom2;
    TextView mcD_txtEquation;
    Button mcD_btnConvert;
    //EQUATION DIALOG
    Dialog equationDialog;
    View edView;
    TextView eDTxtNum1, eDTxtNum2, eDTxtSign;
    EditText eDInputAnswer;
    Button eDBtnCheck;
    //VARIABLES
    ArrayList<FractionQuestionClass> mFractionQuestions;
    FractionQuestionClass mFractionQuestion;
    int mQuestionNum;
    MixedFraction mMixedFraction;
    int mConstraintLayoutID;
    ColorStateList defaultColor;
    int clicks;
    Context context;
    ArrayList<Integer> stepsListId;
    TextView txtContainer;

    public String title = "Multiplying and Dividing with Mixed Fractions ex.1";
    String id = AppIDs.SME2_ID;

    public SolvingMixed2ExerciseActivity() {
        super();
        Range range = getRange();
        Probability probability = new Probability(Probability.SOLVING_MIXED2, range);
        setProbability(probability);
        setRangeEditable(true);
        setId(id);
        setExerciseTitle(title);
    }

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fraction_equation_mixed);
        super.onCreate(savedInstanceState);
        Range range = getRange();
        Probability probability = new Probability(Probability.SOLVING_MIXED2, range);
        setProbability(probability);
        setRangeEditable(true);
        setId(id);
        setExerciseTitle(title);
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
        txtInstruction = findViewById(R.id.fem_txtInstruction);
        inputNum = findViewById(R.id.fem_inputNum);
        inputDenom = findViewById(R.id.fem_inputDenom);
        inputDenom.setOnEditorActionListener(new InputListener());
        btnCheck = findViewById(R.id.fem_btnCheck);
        btnCheck.setOnClickListener(new BtnCheckListener());
        clFraction1 = findViewById(R.id.fem_clFraction1);
        clFraction2 = findViewById(R.id.fem_clFraction2);
        imgLine1 = findViewById(R.id.adsm_imgLine1);
        imgLine2 = findViewById(R.id.adsm_imgLine2);
        imgLine3 = findViewById(R.id.adsm_imgLine3);
        imgLine4 = findViewById(R.id.adsm_imgLine4);
        imgLine5 = findViewById(R.id.adsm_imgLine5);
        imgAvatar = findViewById(R.id.fem_imgAvatar);
        imgLine1.setImageResource(R.drawable.line);
        imgLine2.setImageResource(R.drawable.line);
        imgLine3.setImageResource(R.drawable.line);
        imgLine4.setImageResource(R.drawable.line);
        imgLine5.setImageResource(R.drawable.line);
        imgAvatar.setImageResource(R.drawable.avatar);
        //MIXED CONVERT DIALOG
        viewMixedConvert = getLayoutInflater().inflate(R.layout.layout_mixed_convert, null);
        dialogMixedConvert = new Dialog(SolvingMixed2ExerciseActivity.this);
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
        equationDialog = new Dialog(SolvingMixed2ExerciseActivity.this);
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
        defaultColor = txtNum1.getTextColors();
        stepsListId = new ArrayList<>();

        setClickAreas();

        context = this;

        startExercise();
    }
    public void nextQuestion(){
        startUp();
        mQuestionNum++;
        setFractionGui();
    }
    public void answered(){
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        btnCheck.setEnabled(false);
    }
    @SuppressLint("SetTextI18n")
    public void startUp(){
        txtEquation1.setText("");
        txtEquation2.setText("");
        txtNum3.setText("");
        txtDenom3.setText("");
        txtNum4.setText("");
        txtDenom4.setText("");
        inputNum.setText("");
        inputDenom.setText("");
        inputNum.setHint("");
        inputDenom.setHint("");
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        btnCheck.setEnabled(false);
        txtInstruction.setText("Convert a mixed fraction to an improper fraction by clicking on it.");
    }
    public void setFractionQuestions(){
        mQuestionNum = 1;
        mFractionQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        Range range = getRange();
        for (int i = 0; i < requiredCorrects; i++){
            if (i < (requiredCorrects /2)){
                MultiplyingMixedFractionsQuestion fractionsQuestion = new MultiplyingMixedFractionsQuestion(range);
                while (mFractionQuestions.contains(fractionsQuestion)){
                    fractionsQuestion = new MultiplyingMixedFractionsQuestion(range);
                }
                mFractionQuestions.add(fractionsQuestion);
            } else {
                DividingMixedFractionsQuestion fractionsQuestion = new DividingMixedFractionsQuestion(range);
                while (mFractionQuestions.contains(fractionsQuestion)){
                    fractionsQuestion = new DividingMixedFractionsQuestion(range);
                }
                mFractionQuestions.add(fractionsQuestion);
            }
        }
        Collections.shuffle(mFractionQuestions);
    }
    public void setFractionGui(){
        mFractionQuestion = mFractionQuestions.get(mQuestionNum-1);
        if (mFractionQuestion instanceof MultiplyingMixedFractionsQuestion){
            MultiplyingMixedFractionsQuestion fractionsQuestion = (MultiplyingMixedFractionsQuestion) mFractionQuestion;
            String tag = fractionsQuestion.getTAG();
            txtSign1.setText("x");
            txtSign2.setText("x");
            if (tag.equals(MultiplyingMixedFractionsQuestion.ONE_MIXED)){
                MixedFraction mixedFraction = fractionsQuestion.getMixedFraction1();
                Fraction fraction = fractionsQuestion.getFraction();
                int wholeNumber = mixedFraction.getWholeNumber();
                int numeratorMixed = mixedFraction.getNumerator();
                int denominatorMixed = mixedFraction.getDenominator();
                int numerator = fraction.getNumerator();
                int denominator = fraction.getDenominator();
                String strWholeNumber = String.valueOf(wholeNumber);
                String strNumeratorMixed = String.valueOf(numeratorMixed);
                String strDenominatorMixed = String.valueOf(denominatorMixed);
                String strNumerator = String.valueOf(numerator);
                String strDenominator = String.valueOf(denominator);
                if (Util.randomBoolean()){
                    txtWholeNum1.setText(strWholeNumber);
                    txtNum1.setText(strNumeratorMixed);
                    txtDenom1.setText(strDenominatorMixed);
                    clFraction1.setOnClickListener(new ClFractionListener());
                    txtNum2.setText(strNumerator);
                    txtDenom2.setText(strDenominator);
                    txtWholeNum2.setText("");
                } else {
                    txtWholeNum2.setText(strWholeNumber);
                    txtNum2.setText(strNumeratorMixed);
                    txtDenom2.setText(strDenominatorMixed);
                    clFraction2.setOnClickListener(new ClFractionListener());
                    txtNum1.setText(strNumerator);
                    txtDenom1.setText(strDenominator);
                    txtWholeNum1.setText("");
                }
            } else if (tag.equals(MultiplyingMixedFractionsQuestion.TWO_MIXED)){
                MixedFraction mixedFraction1 = fractionsQuestion.getMixedFraction1();
                MixedFraction mixedFraction2 = fractionsQuestion.getMixedFraction2();
                int wholeNumber1 = mixedFraction1.getWholeNumber();
                int numeratorMixed1 = mixedFraction1.getNumerator();
                int denominatorMixed1 = mixedFraction1.getDenominator();
                int wholeNumber2 = mixedFraction2.getWholeNumber();
                int numeratorMixed2 = mixedFraction2.getNumerator();
                int denominatorMixed2 = mixedFraction2.getDenominator();
                String strWholeNumber1 = String.valueOf(wholeNumber1);
                String strNumeratorMixed1 = String.valueOf(numeratorMixed1);
                String strDenominatorMixed1 = String.valueOf(denominatorMixed1);
                String strWholeNumber2 = String.valueOf(wholeNumber2);
                String strNumeratorMixed2 = String.valueOf(numeratorMixed2);
                String strDenominatorMixed2 = String.valueOf(denominatorMixed2);
                txtWholeNum1.setText(strWholeNumber1);
                txtNum1.setText(strNumeratorMixed1);
                txtDenom1.setText(strDenominatorMixed1);
                clFraction1.setOnClickListener(new ClFractionListener());
                txtWholeNum2.setText(strWholeNumber2);
                txtNum2.setText(strNumeratorMixed2);
                txtDenom2.setText(strDenominatorMixed2);
                clFraction2.setOnClickListener(new ClFractionListener());
            }
        } else if (mFractionQuestion instanceof DividingMixedFractionsQuestion){
            DividingMixedFractionsQuestion fractionsQuestion = (DividingMixedFractionsQuestion) mFractionQuestion;
            String tag = fractionsQuestion.getTAG();
            txtSign1.setText("÷");
            txtSign2.setText("x");
            if (tag.equals(DividingMixedFractionsQuestion.ONE_MIXED)){
                MixedFraction mixedFraction = fractionsQuestion.getMixedFraction1();
                Fraction fraction = fractionsQuestion.getFraction();
                int wholeNumber = mixedFraction.getWholeNumber();
                int numeratorMixed = mixedFraction.getNumerator();
                int denominatorMixed = mixedFraction.getDenominator();
                int numerator = fraction.getNumerator();
                int denominator = fraction.getDenominator();
                String strWholeNumber = String.valueOf(wholeNumber);
                String strNumeratorMixed = String.valueOf(numeratorMixed);
                String strDenominatorMixed = String.valueOf(denominatorMixed);
                String strNumerator = String.valueOf(numerator);
                String strDenominator = String.valueOf(denominator);
                txtWholeNum1.setText(strWholeNumber);
                txtNum1.setText(strNumeratorMixed);
                txtDenom1.setText(strDenominatorMixed);
                clFraction1.setOnClickListener(new ClFractionListener());
                txtNum2.setText(strNumerator);
                txtDenom2.setText(strDenominator);
                txtWholeNum2.setText("");
            } else if (tag.equals(DividingMixedFractionsQuestion.TWO_MIXED)){
                MixedFraction mixedFraction1 = fractionsQuestion.getMixedFraction1();
                MixedFraction mixedFraction2 = fractionsQuestion.getMixedFraction2();
                int wholeNumber1 = mixedFraction1.getWholeNumber();
                int numeratorMixed1 = mixedFraction1.getNumerator();
                int denominatorMixed1 = mixedFraction1.getDenominator();
                int wholeNumber2 = mixedFraction2.getWholeNumber();
                int numeratorMixed2 = mixedFraction2.getNumerator();
                int denominatorMixed2 = mixedFraction2.getDenominator();
                String strWholeNumber1 = String.valueOf(wholeNumber1);
                String strNumeratorMixed1 = String.valueOf(numeratorMixed1);
                String strDenominatorMixed1 = String.valueOf(denominatorMixed1);
                String strWholeNumber2 = String.valueOf(wholeNumber2);
                String strNumeratorMixed2 = String.valueOf(numeratorMixed2);
                String strDenominatorMixed2 = String.valueOf(denominatorMixed2);
                txtWholeNum1.setText(strWholeNumber1);
                txtNum1.setText(strNumeratorMixed1);
                txtDenom1.setText(strDenominatorMixed1);
                clFraction1.setOnClickListener(new ClFractionListener());
                txtWholeNum2.setText(strWholeNumber2);
                txtNum2.setText(strNumeratorMixed2);
                txtDenom2.setText(strDenominatorMixed2);
                clFraction2.setOnClickListener(new ClFractionListener());
            }
        }
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
    private void randomizeContainer(){
        int random = (int) (Math.random() * 2 + 1);
        if (!txtNum4.getText().toString().trim().matches("")){
            random = 2;
        } else if (!txtDenom4.getText().toString().matches("")){
            random = 1;
        }
        if (random==1 && txtNum4.getText().toString().trim().matches("")){
            txtNum4.setText("    ");
            Styles.bgPaintBurlyWood(txtNum4);
            txtContainer = txtNum4;
        } else if (random==2 && txtDenom4.getText().toString().matches("")){
            txtDenom4.setText("    ");
            Styles.bgPaintBurlyWood(txtDenom4);
            txtContainer = txtDenom4;
        }
    }
    private void setTxtInvertListener(){
        txtNum2.setOnClickListener(new TxtInvertListener());
        txtDenom2.setOnClickListener(new TxtInvertListener());
        txtNum2.setClickable(true);
        txtDenom2.setClickable(true);
    }
    public class ClFractionListener implements ConstraintLayout.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == clFraction1.getId()){
                mConstraintLayoutID = clFraction1.getId();
                String strTxtWholeNumber1 = txtWholeNum1.getText().toString().trim();
                String strTxtNumerator1 = txtNum1.getText().toString().trim();
                String strTxtDenominator1 = txtDenom1.getText().toString().trim();
                mcD_txtWholeNum.setText(strTxtWholeNumber1);
                mcD_txtNum1.setText(strTxtNumerator1);
                mcD_txtDenom1.setText(strTxtDenominator1);
                mcD_txtDenom2.setText(strTxtDenominator1);
                int intTxtWholeNumber1 = Integer.valueOf(strTxtWholeNumber1);
                int intTxtNumerator1 = Integer.valueOf(strTxtNumerator1);
                int intTxtDenominator1 = Integer.valueOf(strTxtDenominator1);
                mMixedFraction = new MixedFraction(intTxtWholeNumber1,intTxtNumerator1,intTxtDenominator1);
            } else if (v.getId() == clFraction2.getId()){
                mConstraintLayoutID = clFraction2.getId();
                String strTxtWholeNumber2 = txtWholeNum2.getText().toString().trim();
                String strTxtNumerator2 = txtNum2.getText().toString().trim();
                String strTxtDenominator2 = txtDenom2.getText().toString().trim();
                mcD_txtWholeNum.setText(strTxtWholeNumber2);
                mcD_txtNum1.setText(strTxtNumerator2);
                mcD_txtDenom1.setText(strTxtDenominator2);
                mcD_txtDenom2.setText(strTxtDenominator2);
                int intTxtWholeNumber2 = Integer.valueOf(strTxtWholeNumber2);
                int intTxtNumerator2 = Integer.valueOf(strTxtNumerator2);
                int intTxtDenominator2 = Integer.valueOf(strTxtDenominator2);
                mMixedFraction = new MixedFraction(intTxtWholeNumber2,intTxtNumerator2,intTxtDenominator2);
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
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
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
            assert imm != null;
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }
    private class McDBtnConvertListener implements View.OnClickListener {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            mFractionQuestion = mFractionQuestions.get(mQuestionNum-1);
            if (mConstraintLayoutID==clFraction1.getId()){
                txtWholeNum1.setText("");
                txtNum1.setText(String.valueOf(mcD_txtNum2.getText()));
                txtDenom1.setText(String.valueOf(mcD_txtDenom2.getText()));
            }
            if (mConstraintLayoutID==clFraction2.getId()){
                txtWholeNum2.setText("");
                txtNum2.setText(String.valueOf(mcD_txtNum2.getText()));
                txtDenom2.setText(String.valueOf(mcD_txtDenom2.getText()));
            }
            dialogMixedConvert.dismiss();
            if (txtWholeNum1.getText().toString().trim().equals("")){
                clFraction1.setClickable(false);
            }
            if (txtWholeNum2.getText().toString().trim().equals("")){
                clFraction2.setClickable(false);
            }
            if (txtWholeNum1.getText().toString().matches("") &&
                txtWholeNum2.getText().toString().matches("")){
                if (mFractionQuestion instanceof MultiplyingMixedFractionsQuestion){
                    txtNum3.setText(String.valueOf(txtNum1.getText()));
                    txtDenom3.setText(String.valueOf(txtDenom1.getText()));
                    txtNum4.setText(String.valueOf(txtNum2.getText()));
                    txtDenom4.setText(String.valueOf(txtDenom2.getText()));
                    inputNum.setEnabled(true);
                    inputDenom.setEnabled(true);
                    btnCheck.setEnabled(true);
                    inputNum.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                } else if (mFractionQuestion instanceof DividingMixedFractionsQuestion){
                    txtNum3.setText(String.valueOf(txtNum1.getText()));
                    txtDenom3.setText(String.valueOf(txtDenom1.getText()));
                    randomizeContainer();
                    setTxtInvertListener();
                }
                txtInstruction.setText("Now the equation is familiar to you, solve it.");
            }
        }
    }
    private class TxtInvertListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            if (txtContainer == txtNum4) {
                if (t.getId() == txtDenom2.getId()) {
                    txtNum4.setText(String.valueOf(txtDenom2.getText()));
                    Styles.bgPaintWhite(txtNum4);
                    Styles.removeListener(t);
                    randomizeContainer();
                } else {
                    Styles.shakeAnimate(txtDenom2);
                }
            } else if (txtContainer == txtDenom4) {
                if (t.getId() == txtNum2.getId()) {
                    txtDenom4.setText(String.valueOf(txtNum2.getText()));
                    Styles.bgPaintWhite(txtDenom4);
                    Styles.removeListener(t);
                    randomizeContainer();
                } else {
                    Styles.shakeAnimate(txtNum2);
                }
            }
            if (!txtNum4.getText().toString().trim().matches("") && !txtDenom4.getText().toString().trim().matches("")){
                txtSign2.setText("x");
                inputNum.setEnabled(true);
                inputDenom.setEnabled(true);
                btnCheck.setEnabled(true);
                inputNum.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
        }
    }
    private class BtnCheckListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!inputNum.getText().toString().matches("")){
                if (!inputDenom.getText().toString().matches("")){
                    String strInputNumerator = inputNum.getText().toString().trim();
                    String strInputDenominator = inputDenom.getText().toString().trim();
                    if (Util.isNumeric(strInputNumerator) && Util.isNumeric(strInputDenominator)) {
                        int intInputNumerator = Integer.valueOf(strInputNumerator);
                        int intInputDenominator = Integer.valueOf(strInputDenominator);
                        mFractionQuestion = mFractionQuestions.get(mQuestionNum-1);
                        Fraction fractionAnswer = mFractionQuestion.getFractionAnswer();
                        int numeratorAnswer = fractionAnswer.getNumerator();
                        int denominatorAnswer = fractionAnswer.getDenominator();
                        if (intInputNumerator==numeratorAnswer && intInputDenominator==denominatorAnswer) {
                            correct();
                        } else {
                            wrong();
                        }
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
        setClickArea(txtDenom1,0,50,100,50);
        setClickArea(txtDenom2,0,50,100,50);
        setClickArea(txtNum1,100,50,0,100);

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
    }@Override
    public void showScore(){
        super.showScore();
        int correct = getCorrect();
        int requiredCorrects = getItemsSize();
        txtScore.setText(AppConstants.SCORE(correct,requiredCorrects));
    }

    @Override
    protected void startExercise() {
        super.startExercise();
        startUp();
        setFractionQuestions();
        setFractionGui();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        answered();
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
            startUp();
            setFractionQuestions();
            setFractionGui();
        } else {
            mFractionQuestion = mFractionQuestions.get(mQuestionNum-1);
            Range range = getRange();
            int multiplyingItems = 0;
            int dividingItems = 0;
            for (FractionQuestionClass questionClass : mFractionQuestions){
                if (questionClass instanceof MultiplyingMixedFractionsQuestion){
                    multiplyingItems++;
                } else if (questionClass instanceof DividingMixedFractionsQuestion){
                    dividingItems++;
                }
            }
            int maxItemSize = getMaxItemSize()/2;
            if (mFractionQuestion instanceof MultiplyingMixedFractionsQuestion){
                MultiplyingMixedFractionsQuestion fractionsQuestion = new MultiplyingMixedFractionsQuestion(range);
                while (mFractionQuestions.contains(fractionsQuestion) && multiplyingItems<maxItemSize){
                    fractionsQuestion = new MultiplyingMixedFractionsQuestion(range);
                }
                mFractionQuestions.add(fractionsQuestion);
            } else if (mFractionQuestion instanceof DividingMixedFractionsQuestion){
                DividingMixedFractionsQuestion fractionsQuestion = new DividingMixedFractionsQuestion(range);
                while (mFractionQuestions.contains(fractionsQuestion) && dividingItems<maxItemSize){
                    fractionsQuestion = new DividingMixedFractionsQuestion(range);
                }
                mFractionQuestions.add(fractionsQuestion);
            }
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