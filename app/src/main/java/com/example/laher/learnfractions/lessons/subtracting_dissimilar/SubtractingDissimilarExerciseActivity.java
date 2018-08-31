package com.example.laher.learnfractions.lessons.subtracting_dissimilar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.os.Bundle;
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
import com.example.laher.learnfractions.fraction_util.fraction_questions.SubtractingDissimilarFractionsQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import java.util.ArrayList;

public class SubtractingDissimilarExerciseActivity extends LessonExercise {
    //private static final String TAG = "SD_E1";
    //GUI
    TextView txtNum1;
    TextView txtNum2;
    TextView txtNum3;
    TextView txtNum4;
    TextView txtDenom1;
    TextView txtDenom2;
    TextView txtDenom3;
    TextView txtDenom4;
    TextView txtEquation1;
    TextView txtEquation2;
    TextView txtScore;
    TextView txtInstruction;
    TextView txtSign1;
    TextView txtSign2;
    EditText inputNum, inputDenom;
    Button btnCheck;
    ImageView imgLine1;
    ImageView imgLine2;
    ImageView imgLine3;
    ImageView imgLine4;
    ImageView imgLine5;
    ImageView imgAvatar;
    //LCM DIALOG
    Dialog lcmDialog;
    View lcmView;
    TextView diagLcmTxtNum1;
    TextView diagLcmTxtNum2;
    TextView diagLcmTxtNum3;
    EditText diagLcmInputLcm;
    Button diagLcmBtnCheck;
    //EQUATION DIALOG
    Dialog equationDialog;
    View edView;
    TextView diagEdTxtNum1;
    TextView diagEdTxtNum2;
    TextView diagEdTxtSign;
    EditText diagEdInputAnswer;
    Button diagEdBtnCheck;
    //VARIABLES
    ArrayList<SubtractingDissimilarFractionsQuestion> mFractionsQuestions;
    SubtractingDissimilarFractionsQuestion mFractionsQuestion;
    int mQuestionNum;

    ArrayList<Integer> viewIds;
    ColorStateList defaultColor;

    public String title = "Subtracting Dissimilar Fractions ex.1";
    String id = AppIDs.SDE_ID;

    public SubtractingDissimilarExerciseActivity() {
        super();
        Range range = getRange();
        Probability probability = new Probability(Probability.SUBTRACTING_2DISSIMILAR_F, range);
        setProbability(probability);
        setRangeEditable(true);
        setId(id);
        setExerciseTitle(title);
    }

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fraction_dissimilar_equation);
        super.onCreate(savedInstanceState);
        Range range = getRange();
        Probability probability = new Probability(Probability.SUBTRACTING_2DISSIMILAR_F, range);
        setProbability(probability);
        setRangeEditable(true);
        setId(id);
        setExerciseTitle(title);
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
        setClickAreas();
        txtScore = findViewById(R.id.adsm_txtScore);
        txtInstruction = findViewById(R.id.adsm_txtInstruction);
        inputNum = findViewById(R.id.adsm_inputNum);
        inputDenom = findViewById(R.id.adsm_inputDenom);
        inputDenom.setOnEditorActionListener(new InputListener());
        btnCheck = findViewById(R.id.adsm_btnCheck);
        btnCheck.setOnClickListener(new BtnCheckListener());
        txtSign1 = findViewById(R.id.adsm_txtSign1);
        txtSign2 = findViewById(R.id.adsm_txtSign2);
        txtSign1.setText(" - ");
        txtSign2.setText(" - ");
        imgLine1 = findViewById(R.id.adsm_imgLine1);
        imgLine2 = findViewById(R.id.adsm_imgLine2);
        imgLine3 = findViewById(R.id.adsm_imgLine3);
        imgLine4 = findViewById(R.id.adsm_imgLine4);
        imgLine5 = findViewById(R.id.adsm_imgLine5);
        imgAvatar = findViewById(R.id.adsm_imgAvatar);
        imgLine1.setImageResource(R.drawable.line);
        imgLine2.setImageResource(R.drawable.line);
        imgLine3.setImageResource(R.drawable.line);
        imgLine4.setImageResource(R.drawable.line);
        imgLine5.setImageResource(R.drawable.line);
        imgAvatar.setImageResource(R.drawable.avatar);
        //LCM DIALOG
        lcmView = getLayoutInflater().inflate(R.layout.layout_dialog_lcm, null);
        lcmDialog = new Dialog(SubtractingDissimilarExerciseActivity.this);
        lcmDialog.setOnDismissListener(new DialogListener());
        lcmDialog.setOnShowListener(new DialogListener());
        lcmDialog.setTitle("Getting the LCD");
        lcmDialog.setContentView(lcmView);
        diagLcmTxtNum1 = lcmView.findViewById(R.id.lcm_txtNum1);
        diagLcmTxtNum2 = lcmView.findViewById(R.id.lcm_txtNum2);
        diagLcmTxtNum3 = lcmView.findViewById(R.id.lcm_txtNum3);
        diagLcmInputLcm = lcmView.findViewById(R.id.lcm_inputLcm);
        diagLcmInputLcm.setOnEditorActionListener(new InputListener());
        diagLcmBtnCheck = lcmView.findViewById(R.id.lcm_btnCheck);
        diagLcmBtnCheck.setOnClickListener(new DiagLcmBtnCheckListener());
        //EQUATION DIALOG
        edView = getLayoutInflater().inflate(R.layout.layout_dialog_equation, null);
        equationDialog = new Dialog(SubtractingDissimilarExerciseActivity.this);
        equationDialog.setOnDismissListener(new DialogListener());
        equationDialog.setOnShowListener(new DialogListener());
        equationDialog.setTitle("Division Equation");
        equationDialog.setContentView(edView);
        diagEdTxtNum1 = edView.findViewById(R.id.md_txtMultiplicand);
        diagEdTxtNum2 = edView.findViewById(R.id.md_txtMultiplier);
        diagEdTxtSign = edView.findViewById(R.id.md_txtSign);
        diagEdInputAnswer = edView.findViewById(R.id.md_inputProduct);
        diagEdInputAnswer.setOnEditorActionListener(new InputListener());
        diagEdBtnCheck = edView.findViewById(R.id.md_btnCheck);
        diagEdBtnCheck.setOnClickListener(new DiagEdBtnCheckListener());

        defaultColor = txtNum1.getTextColors();
        viewIds = new ArrayList<>();

        startExercise();
    }
    public void nextQuestion(){
        mQuestionNum++;
        setFractionGui();
        startUp();
    }
    public void setQuestions() {
        mQuestionNum = 1;
        mFractionsQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        Range range = getRange();
        for (int i = 0; i < requiredCorrects; i++){
            SubtractingDissimilarFractionsQuestion fractionsQuestion = new SubtractingDissimilarFractionsQuestion(range);
            while (mFractionsQuestions.contains(fractionsQuestion)){
                fractionsQuestion = new SubtractingDissimilarFractionsQuestion(range);
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
    }
    @SuppressLint("SetTextI18n")
    public void startUp(){
        txtNum3.setVisibility(TextView.INVISIBLE);
        txtNum4.setVisibility(TextView.INVISIBLE);
        setFractionSet2DenomVisibility(false);
        txtEquation1.setVisibility(TextView.INVISIBLE);
        txtEquation2.setVisibility(TextView.INVISIBLE);
        inputNum.setText("");
        inputDenom.setText("");
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        btnCheck.setEnabled(false);
        setLcmListeners(true);
        txtInstruction.setText("Get the lcd of the two denominators by clicking them.");
    }
    public void resetColors(){
        txtDenom1.setTextColor(defaultColor);
        txtDenom2.setTextColor(defaultColor);
        txtDenom3.setTextColor(defaultColor);
        txtDenom4.setTextColor(defaultColor);
        txtNum1.setTextColor(defaultColor);
        txtNum2.setTextColor(defaultColor);
        txtEquation1.setTextColor(defaultColor);
        txtEquation2.setTextColor(defaultColor);
    }
    public void setFractionSet2DenomVisibility(boolean b){
        if (b){
            txtDenom3.setVisibility(TextView.VISIBLE);
            txtDenom4.setVisibility(TextView.VISIBLE);
        } else {
            txtDenom3.setVisibility(TextView.INVISIBLE);
            txtDenom4.setVisibility(TextView.INVISIBLE);
        }
    }
    public void setLcmListeners(boolean bool){
        if (bool){
            txtDenom1.setOnClickListener(new TxtLcmListener());
            txtDenom2.setOnClickListener(new TxtLcmListener());
        } else {
            txtDenom1.setOnClickListener(null);
            txtDenom2.setOnClickListener(null);
        }
        txtDenom1.setClickable(bool);
        txtDenom2.setClickable(bool);
    }
    public void setDivisionListeners(boolean b){
        if (b){
            txtDenom1.setOnClickListener(new TxtDivisionListener());
            txtDenom2.setOnClickListener(new TxtDivisionListener());
            txtDenom3.setOnClickListener(new TxtDivisionListener());
            txtDenom4.setOnClickListener(new TxtDivisionListener());
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
    public void setMultiplicationListeners(boolean b){
        if (b){
            txtEquation1.setOnClickListener(new TxtMultiplicationListener());
            txtEquation2.setOnClickListener(new TxtMultiplicationListener());
            txtNum1.setOnClickListener(new TxtMultiplicationListener());
            txtNum2.setOnClickListener(new TxtMultiplicationListener());
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
    public void removeListeners(View v1, View v2){
        v1.setOnClickListener(null);
        v2.setOnClickListener(null);
        v1.setClickable(false);
        v2.setClickable(false);
    }
    public void popUpLcmDialog(){
        mFractionsQuestion = mFractionsQuestions.get(mQuestionNum-1);
        Fraction fraction1 = mFractionsQuestion.getFraction1();
        Fraction fraction2 = mFractionsQuestion.getFraction2();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        String strDenominator1 = String.valueOf(denominator1);
        String strDenominator2 = String.valueOf(denominator2);
        diagLcmTxtNum1.setText(strDenominator1);
        diagLcmTxtNum2.setText(strDenominator2);
        diagLcmTxtNum3.setText("");
        lcmDialog.show();
    }
    private void popUpEquationDialog(String t1, String t2, String sign){
        diagEdTxtNum1.setText(t1);
        diagEdTxtNum2.setText(t2);
        diagEdTxtSign.setText(sign);
        equationDialog.show();
    }
    public class TxtLcmListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            viewIds.add(t.getId());
            if (viewIds.size()==1){
                if(viewIds.get(0)==txtDenom1.getId() || viewIds.get(0)==txtDenom2.getId()) {
                    Styles.paintGreen(t);
                }
            }
            if (viewIds.size()==2){
                if(viewIds.get(0)==txtDenom1.getId() && viewIds.get(1)==txtDenom2.getId()
                        || viewIds.get(0)==txtDenom2.getId() && viewIds.get(1)==txtDenom1.getId()) {
                    Styles.paintGreen(t);
                    popUpLcmDialog();
                } else {
                    viewIds.remove(viewIds.size() - 1);
                }
            }
        }
    }
    private class TxtDivisionListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            mFractionsQuestion = mFractionsQuestions.get(mQuestionNum-1);
            Fraction fraction1 = mFractionsQuestion.getFraction1();
            Fraction fraction2 = mFractionsQuestion.getFraction2();
            int lcd = mFractionsQuestion.getEquationLcd();
            String strLcd = String.valueOf(lcd);
            int denominator1 = fraction1.getDenominator();
            int denominator2 = fraction2.getDenominator();
            String strDenominator1 = String.valueOf(denominator1);
            String strDenominator2 = String.valueOf(denominator2);
            TextView t = (TextView) v;
            viewIds.add(t.getId());
            if (viewIds.size()==1){
                if(viewIds.get(0)==txtDenom3.getId() || viewIds.get(0)==txtDenom4.getId()) {
                    Styles.paintGreen(t);
                } else {
                    viewIds.clear();
                }
            }
            if (viewIds.size()==2){
                if(viewIds.get(0)==txtDenom3.getId() && viewIds.get(1)==txtDenom1.getId()
                        || viewIds.get(0)==txtDenom4.getId() && viewIds.get(1)==txtDenom2.getId()) {
                    Styles.paintGreen(t);
                    if (viewIds.get(1)==txtDenom1.getId()){
                        popUpEquationDialog(strLcd, strDenominator1,"÷");
                    }
                    if (viewIds.get(1)==txtDenom2.getId()){
                        popUpEquationDialog(strLcd, strDenominator2,"÷");
                    }
                } else {
                    viewIds.remove(viewIds.size() - 1);
                }
            }
        }
    }
    private class TxtMultiplicationListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            mFractionsQuestion = mFractionsQuestions.get(mQuestionNum-1);
            Fraction fraction1 = mFractionsQuestion.getFraction1();
            Fraction fraction2 = mFractionsQuestion.getFraction2();
            int numerator1 = fraction1.getNumerator();
            int numerator2 = fraction2.getNumerator();
            String strNumerator1 = String.valueOf(numerator1);
            String strNumerator2 = String.valueOf(numerator2);
            TextView t = (TextView) v;
            viewIds.add(t.getId());
            if (viewIds.size()==1){
                if(viewIds.get(0)==txtEquation1.getId() || viewIds.get(0)==txtEquation2.getId()) {
                    Styles.paintGreen(t);
                } else {
                    viewIds.clear();
                }
            }
            if (viewIds.size()==2){
                if(viewIds.get(0)==txtEquation1.getId() && viewIds.get(1)==txtNum1.getId()
                        || viewIds.get(0)==txtEquation2.getId() && viewIds.get(1)==txtNum2.getId()) {
                    Styles.paintGreen(t);
                    if (viewIds.get(1)==txtNum1.getId()){
                        popUpEquationDialog(String.valueOf(txtEquation1.getText()), strNumerator1,"x");
                    }
                    if (viewIds.get(1)==txtNum2.getId()){
                        popUpEquationDialog(String.valueOf(txtEquation2.getText()), strNumerator2,"x");
                    }
                } else {
                    viewIds.remove(viewIds.size() - 1);
                }
            }
        }
    }
    private class DiagLcmBtnCheckListener implements View.OnClickListener {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            mFractionsQuestion = mFractionsQuestions.get(mQuestionNum-1);
            int lcd = mFractionsQuestion.getEquationLcd();
            String strLcmInputLcm = diagLcmInputLcm.getText().toString().trim();
            if (Util.isNumeric(strLcmInputLcm)) {
                int intLcmInputLcm = Integer.valueOf(strLcmInputLcm);
                if (intLcmInputLcm == lcd) {
                    txtDenom3.setText(strLcmInputLcm);
                    txtDenom4.setText(strLcmInputLcm);
                    setLcmListeners(false);
                    setFractionSet2DenomVisibility(true);
                    setDivisionListeners(true);
                    lcmDialog.dismiss();
                    txtInstruction.setText("Divide a new denominator to its' corresponding denominator by clicking the" +
                            " new denominator first.");
                } else {
                    Styles.shakeAnimate(diagLcmInputLcm);
                }
            }
        }
    }
    private class DialogListener implements DialogInterface.OnDismissListener, DialogInterface.OnShowListener {
        @Override
        public void onDismiss(DialogInterface dialog) {
            resetColors();
            viewIds.clear();
            diagEdInputAnswer.setText("");
            diagLcmInputLcm.setText("");
            if (dialog.equals(equationDialog)){
                if(diagEdTxtSign.getText().toString().matches("x")){
                    if (txtNum3.getVisibility()==TextView.VISIBLE &&
                            txtNum4.getVisibility()==TextView.VISIBLE) {
                        if (mQuestionNum>1) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            assert imm != null;
                            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                        }
                    }
                }
            }
        }

        @Override
        public void onShow(DialogInterface dialog) {
            if (dialog.equals(lcmDialog)) {
                diagLcmInputLcm.requestFocus();
            } else if (dialog.equals(equationDialog)){
                diagEdInputAnswer.requestFocus();
            }
            if (mQuestionNum>1) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
        }
    }
    private class DiagEdBtnCheckListener implements View.OnClickListener {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            if (!diagEdInputAnswer.getText().toString().matches("")){
                if (String.valueOf(diagEdTxtSign.getText()).equals("÷")){
                    if (Integer.valueOf(String.valueOf(diagEdTxtNum1.getText().toString())) /
                            Integer.valueOf(String.valueOf(diagEdTxtNum2.getText())) ==
                            Integer.valueOf(String.valueOf(diagEdInputAnswer.getText()))){
                        if (viewIds.get(0)==txtDenom3.getId()){
                            removeListeners(txtDenom1,txtDenom3);
                            txtEquation1.setText(String.valueOf(diagEdInputAnswer.getText()));
                            txtEquation1.setVisibility(TextView.VISIBLE);
                            equationDialog.dismiss();
                        } else if (viewIds.get(0)==txtDenom4.getId()){
                            removeListeners(txtDenom2,txtDenom4);
                            txtEquation2.setText(String.valueOf(diagEdInputAnswer.getText()));
                            txtEquation2.setVisibility(TextView.VISIBLE);
                            equationDialog.dismiss();
                        }
                        txtInstruction.setText("Divide the other two also.");
                        if (txtEquation1.getVisibility()==TextView.VISIBLE &&
                                txtEquation2.getVisibility()==TextView.VISIBLE){
                            setMultiplicationListeners(true);
                            txtInstruction.setText("Multiply a quotient to its' corresponding numerator by" +
                                    " clicking the quotient first.");
                        }
                    } else {
                        Styles.shakeAnimate(diagEdInputAnswer);
                    }
                } else if (String.valueOf(diagEdTxtSign.getText()).equals("x")){
                    if (Integer.valueOf(String.valueOf(diagEdTxtNum1.getText())) *
                            Integer.valueOf(String.valueOf(diagEdTxtNum2.getText())) ==
                            Integer.valueOf(String.valueOf(diagEdInputAnswer.getText()))){
                        if (viewIds.get(0)==txtEquation1.getId()){
                            removeListeners(txtEquation1,txtNum3);
                            txtEquation1.setText(String.valueOf(txtEquation1.getText())+"x"+
                                    String.valueOf(diagEdTxtNum2.getText())+"=");
                            txtNum3.setText(String.valueOf(diagEdInputAnswer.getText()));
                            txtNum3.setVisibility(TextView.VISIBLE);
                            equationDialog.dismiss();
                        } else if (viewIds.get(0)==txtEquation2.getId()){
                            removeListeners(txtEquation2,txtNum4);
                            txtEquation2.setText(String.valueOf(txtEquation2.getText())+"x"+
                                    String.valueOf(diagEdTxtNum2.getText())+"=");
                            txtNum4.setText(String.valueOf(diagEdInputAnswer.getText()));
                            txtNum4.setVisibility(TextView.VISIBLE);
                            equationDialog.dismiss();
                        }
                        txtInstruction.setText("Multiply the other two also.");
                        if (txtNum3.getVisibility()==TextView.VISIBLE &&
                                txtNum4.getVisibility()==TextView.VISIBLE){
                            inputNum.setEnabled(true);
                            inputDenom.setEnabled(true);
                            inputNum.requestFocus();
                            btnCheck.setEnabled(true);
                            txtInstruction.setText("Subtract the numerators and copy the denominators.");
                        }
                    } else {
                        Styles.shakeAnimate(diagEdInputAnswer);
                    }
                }
            } else {
                Styles.shakeAnimate(diagEdInputAnswer);
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
            if (!inputNum.getText().toString().matches("")) {
                if (!inputDenom.getText().toString().matches("")) {
                    String strInputNum = inputNum.getText().toString().trim();
                    String strInputDenominator = inputDenom.getText().toString().trim();
                    if (Util.isNumeric(strInputNum) && Util.isNumeric(strInputDenominator)) {
                        int intInputNum = Integer.valueOf(strInputNum);
                        int intInputDenominator = Integer.valueOf(strInputDenominator);
                        if (intInputNum==numeratorAnswer&&intInputDenominator==denominatorAnswer) {
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
                if (v.getId()==diagLcmInputLcm.getId()){
                    diagLcmBtnCheck.performClick();
                } else if (v.getId()==diagEdInputAnswer.getId()){
                    diagEdBtnCheck.performClick();
                } else if (v.getId()==inputDenom.getId()){
                    btnCheck.performClick();
                }
            }
            return false;
        }
    }
    private void setClickAreas(){
        setLargerClickArea(txtNum1);
        setLargerClickArea(txtNum2);
        setLargerClickArea(txtNum3);
        setLargerClickArea(txtNum4);
        setLargerClickArea(txtDenom1);
        setLargerClickArea(txtDenom2);
        setLargerClickArea(txtDenom3);
        setLargerClickArea(txtDenom4);
        setLargerClickArea(txtEquation1);
        setLargerClickArea(txtEquation2);
    }

    private void setLargerClickArea(final TextView textView){
        final View parent = (View) textView.getParent();  // button: the view you want to enlarge hit area
        parent.post( new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                textView.getHitRect(rect);
                rect.top -= 100;    // increase top hit area
                rect.left -= 100;   // increase left hit area
                rect.bottom += 100; // increase bottom hit area
                rect.right += 100;  // increase right hit area
                parent.setTouchDelegate( new TouchDelegate( rect , textView));
            }
        });
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
        setQuestions();
        setFractionGui();
        startUp();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        btnCheck.setEnabled(false);
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
        mFractionsQuestion = mFractionsQuestions.get(mQuestionNum-1);
        Fraction fractionAnswer = mFractionsQuestion.getFractionAnswer();
        int numeratorAnswer = fractionAnswer.getNumerator();
        int denominatorAnswer = fractionAnswer.getDenominator();
        String strNumeratorAnswer = String.valueOf(numeratorAnswer);
        String strDenominatorAnswer = String.valueOf(denominatorAnswer);
        inputNum.setText(strNumeratorAnswer);
        inputDenom.setText(strDenominatorAnswer);
        Styles.paintRed(inputNum);
        Styles.paintRed(inputDenom);
    }

    @Override
    protected void postWrong() {
        super.postWrong();
        boolean correctsShouldBeConsecutive = isCorrectsShouldBeConsecutive();
        Range range = getRange();
        if (correctsShouldBeConsecutive) {
            setQuestions();
            setFractionGui();
            startUp();
        } else {
            SubtractingDissimilarFractionsQuestion fractionsQuestion = new SubtractingDissimilarFractionsQuestion(range);
            int questionsSize = mFractionsQuestions.size();
            int maxItemSize = getMaxItemSize();
            while (mFractionsQuestions.contains(fractionsQuestion) && questionsSize<maxItemSize){
                fractionsQuestion = new SubtractingDissimilarFractionsQuestion(range);
            }
            mFractionsQuestions.add(fractionsQuestion);
            nextQuestion();
        }
        Styles.paintBlack(inputNum);
        Styles.paintBlack(inputDenom);
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
