package com.example.laher.learnfractions.lessons.solving_mixed;

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
import com.example.laher.learnfractions.dialog_layout.EquationDialog;
import com.example.laher.learnfractions.dialog_layout.LcmDialog;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;
import com.example.laher.learnfractions.fraction_util.MixedFraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.AddingMixedFractionsQuestion;
import com.example.laher.learnfractions.fraction_util.fraction_questions.SubtractingMixedFractionsQuestion;
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

public class SolvingMixedExerciseActivity extends LessonExercise {
    public static final String TAG = "SM1_E1";
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
    EditText inputNum;
    EditText inputDenom;
    Button btnCheck;
    ConstraintLayout clFraction1;
    ConstraintLayout clFraction2;
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
    ImageView imgLine1;
    ImageView imgLine2;
    ImageView imgLine3;
    ImageView imgLine4;
    ImageView imgLine5;
    ImageView imgAvatar;
    //EQUATION DIALOG
    EquationDialog dialogEquation;
    Dialog equationDialog;
    View edView;
    TextView eDTxtNum1, eDTxtNum2, eDTxtSign;
    EditText eDInputAnswer;
    Button eDBtnCheck;
    //LCM DIALOG
    LcmDialog dialogLcm;
    //VARIABLES
    ArrayList<FractionQuestionClass> mFractionsQuestions;
    FractionQuestionClass mFractionsQuestion;
    int mQuestionNum;

    MixedFraction mMixedFraction;
    int mConstraintLayoutID;
    ColorStateList defaultColor;
    int clicks;
    ArrayList<Integer> stepsListId;
    TextView clickedTextView;

    public String title = "Adding and Subtracting with Mixed Fractions ex.1";
    String id = AppIDs.SME_ID;

    public SolvingMixedExerciseActivity() {
        super();
        Range range = getRange();
        Probability probability = new Probability(Probability.SOLVING_MIXED1, range);
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
        Probability probability = new Probability(Probability.SOLVING_MIXED1, range);
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
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        btnCheck.setEnabled(false);
        txtInstruction.setText("Convert a mixed fraction to an improper fraction by clicking on it.");
    }
    public void setFractionQuestions(){
        mQuestionNum = 1;
        mFractionsQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        Range range = getRange();
        for (int i = 0; i < requiredCorrects; i++){
            if (i>=(requiredCorrects/2)) {
                AddingMixedFractionsQuestion fractionsQuestion = new AddingMixedFractionsQuestion(range);
                while (mFractionsQuestions.contains(fractionsQuestion)) {
                    fractionsQuestion = new AddingMixedFractionsQuestion(range);
                }
                mFractionsQuestions.add(fractionsQuestion);
            } else {
                SubtractingMixedFractionsQuestion fractionsQuestion = new SubtractingMixedFractionsQuestion(range);
                while (mFractionsQuestions.contains(fractionsQuestion)) {
                    fractionsQuestion = new SubtractingMixedFractionsQuestion(range);
                }
                mFractionsQuestions.add(fractionsQuestion);
            }
        }
        Collections.shuffle(mFractionsQuestions);
    }
    public void setFractionGui(){
        mFractionsQuestion = mFractionsQuestions.get(mQuestionNum-1);
        if (mFractionsQuestion instanceof AddingMixedFractionsQuestion){
            AddingMixedFractionsQuestion fractionsQuestion = (AddingMixedFractionsQuestion) mFractionsQuestion;
            String tag = fractionsQuestion.getTAG();
            txtSign1.setText("+");
            txtSign2.setText("+");
            if (tag.equals(AddingMixedFractionsQuestion.ONE_MIXED)){
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
            } else if (tag.equals(AddingMixedFractionsQuestion.TWO_MIXED)){
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
        } else if (mFractionsQuestion instanceof SubtractingMixedFractionsQuestion){
            SubtractingMixedFractionsQuestion fractionsQuestion = (SubtractingMixedFractionsQuestion) mFractionsQuestion;
            String tag = fractionsQuestion.getTAG();
            txtSign1.setText("-");
            txtSign2.setText("-");
            if (tag.equals(SubtractingMixedFractionsQuestion.ONE_MIXED)){
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
            } else if (tag.equals(SubtractingMixedFractionsQuestion.TWO_MIXED)){
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
            assert imm != null;
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }
    private class McDBtnConvertListener implements View.OnClickListener {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            mFractionsQuestion = mFractionsQuestions.get(mQuestionNum-1);
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
                    if (mFractionsQuestion instanceof AddingMixedFractionsQuestion){
                        txtInstruction.setText("Now, we have an equation of two dissimilar fractions. Add the two" +
                                " dissimilar fractions like we did last time.");
                    } else if (mFractionsQuestion instanceof SubtractingMixedFractionsQuestion){
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
                dialogLcm = new LcmDialog(getContext(), num1, num2);
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
                        dialogEquation = new EquationDialog(getContext(),
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
                        dialogEquation = new EquationDialog(getContext(),
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
                        dialogEquation = new EquationDialog(getContext(),
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
                        dialogEquation = new EquationDialog(getContext(),
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
            if (mQuestionNum>1) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
        }
    }
    private class DialogEquationListener2 implements OnDismissListener{
        @SuppressLint("SetTextI18n")
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
                    String strInputNumerator = inputNum.getText().toString().trim();
                    String strInputDenominator = inputDenom.getText().toString().trim();
                    if (Util.isNumeric(strInputNumerator) && Util.isNumeric(strInputDenominator)) {
                        int intInputNumerator = Integer.valueOf(strInputNumerator);
                        int intInputDenominator = Integer.valueOf(strInputDenominator);
                        mFractionsQuestion = mFractionsQuestions.get(mQuestionNum-1);
                        Fraction fractionAnswer = mFractionsQuestion.getFractionAnswer();
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
            mFractionsQuestion = mFractionsQuestions.get(mQuestionNum-1);
            Range range = getRange();
            int addingItems = 0;
            int subtractingItems = 0;
            for (FractionQuestionClass questionClass : mFractionsQuestions){
                if (questionClass instanceof AddingMixedFractionsQuestion){
                    addingItems++;
                } else if (questionClass instanceof SubtractingMixedFractionsQuestion){
                    subtractingItems++;
                }
            }
            int maxItemSize = getMaxItemSize()/2;
            if (mFractionsQuestion instanceof AddingMixedFractionsQuestion){
                AddingMixedFractionsQuestion question = new AddingMixedFractionsQuestion(range);
                while (mFractionsQuestions.contains(question) && addingItems<maxItemSize){
                    question = new AddingMixedFractionsQuestion(range);
                }
                mFractionsQuestions.add(question);
            } else if (mFractionsQuestion instanceof SubtractingMixedFractionsQuestion){
                SubtractingMixedFractionsQuestion question = new SubtractingMixedFractionsQuestion(range);
                while (mFractionsQuestions.contains(question) && subtractingItems<maxItemSize){
                    question = new SubtractingMixedFractionsQuestion(range);
                }
                mFractionsQuestions.add(question);
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