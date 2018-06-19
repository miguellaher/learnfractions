package com.example.laher.learnfractions.subtracting_dissimilar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.Fraction;
import com.example.laher.learnfractions.FractionQuestion;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

public class SubtractingDissimilarExerciseActivity extends AppCompatActivity {
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Subtracting Fractions";
    //GUI
    TextView txtNum1, txtNum2, txtNum3, txtNum4, txtDenom1, txtDenom2, txtDenom3, txtDenom4, txtEquation1, txtEquation2
            , txtScore, txtInstruction, txtSign1, txtSign2;
    EditText inputNum, inputDenom;
    Button btnCheck;
    //LCM DIALOG
    Dialog lcmDialog;
    View lcmView;
    TextView diagLcmtxtNum1, diagLcmtxtNum2, diagLcmtxtNum3;
    EditText diagLcmInputLcm;
    Button diagLcmBtnCheck;
    //EQUATION DIALOG
    Dialog equationDialog;
    View edView;
    TextView diagEdTxtNum1, diagEdTxtNum2, diagEdTxtSign;
    EditText diagEdInputAnswer;
    Button diagEdBtnCheck;
    //VARIABLES
    Fraction fraction;
    FractionQuestion fractionQuestion;
    ArrayList<FractionQuestion> fractionQuestions;
    int questionNum;
    int consecutiveRights, consecutiveWrongs;
    int requiredConsecutiveCorrects = 10;
    int maxConsecutiveWrongs = 3;
    ArrayList<Integer> viewIds;
    final Handler handler = new Handler();
    ColorStateList defaultColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_dissimilar_equation);
        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubtractingDissimilarExerciseActivity.this,
                        SubtractingDissimilarVideoActivity.class);
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
                Intent intent = new Intent(SubtractingDissimilarExerciseActivity.this,
                        TopicsMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
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
        txtScore = findViewById(R.id.adsm_txtScore);
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        txtInstruction = findViewById(R.id.adsm_txtInstruction);
        inputNum = findViewById(R.id.adsm_inputNum);
        inputDenom = findViewById(R.id.adsm_inputDenom);
        btnCheck = findViewById(R.id.adsm_btnCheck);
        btnCheck.setOnClickListener(new BtnCheckListener());
        txtSign1 = findViewById(R.id.adsm_txtSign1);
        txtSign2 = findViewById(R.id.adsm_txtSign2);
        txtSign1.setText(" - ");
        txtSign2.setText(" - ");
        //LCM DIALOG
        lcmView = getLayoutInflater().inflate(R.layout.layout_dialog_lcm, null);
        lcmDialog = new Dialog(SubtractingDissimilarExerciseActivity.this);
        lcmDialog.setOnDismissListener(new DialogListener());
        lcmDialog.setTitle("Getting the LCD");
        lcmDialog.setContentView(lcmView);
        diagLcmtxtNum1 = (TextView) lcmView.findViewById(R.id.lcm_txtNum1);
        diagLcmtxtNum2 = (TextView) lcmView.findViewById(R.id.lcm_txtNum2);
        diagLcmtxtNum3 = (TextView) lcmView.findViewById(R.id.lcm_txtNum3);
        diagLcmInputLcm = (EditText) lcmView.findViewById(R.id.lcm_inputLcm);
        diagLcmBtnCheck = (Button) lcmView.findViewById(R.id.lcm_btnCheck);
        diagLcmBtnCheck.setOnClickListener(new DiagLcmBtnCheckListener());
        //EQUATION DIALOG
        edView = getLayoutInflater().inflate(R.layout.layout_dialog_equation, null);
        equationDialog = new Dialog(SubtractingDissimilarExerciseActivity.this);
        equationDialog.setOnDismissListener(new DialogListener());
        equationDialog.setTitle("Division Equation");
        equationDialog.setContentView(edView);
        diagEdTxtNum1 = (TextView) edView.findViewById(R.id.md_txtMultiplicand);
        diagEdTxtNum2 = (TextView) edView.findViewById(R.id.md_txtMultiplier);
        diagEdTxtSign = (TextView) edView.findViewById(R.id.md_txtSign);
        diagEdInputAnswer = (EditText) edView.findViewById(R.id.md_inputProduct);
        diagEdBtnCheck = (Button) edView.findViewById(R.id.md_btnCheck);
        diagEdBtnCheck.setOnClickListener(new DiagEdBtnCheckListener());

        defaultColor = txtNum1.getTextColors();
        viewIds = new ArrayList<>();

        go();
    }
    public void go(){
        setQuestions();
        setFractionGui();
        startUp();
    }
    public void correct(){
        consecutiveRights++;
        consecutiveWrongs = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        btnCheck.setEnabled(false);
        if (consecutiveRights>=requiredConsecutiveCorrects){
            txtInstruction.setText("Finished.");
            btnNext.setEnabled(true);
        } else {
            txtInstruction.setText("Correct.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    questionNum++;
                    setFractionGui();
                    startUp();
                }
            }, 2000);
        }
    }
    public void wrong(){
        consecutiveWrongs++;
        consecutiveRights = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        btnCheck.setEnabled(false);
        if (consecutiveWrongs>=maxConsecutiveWrongs){
            txtInstruction.setText("You had " + consecutiveWrongs + " consecutive wrongs. Preparing to" +
                    " watch video again.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SubtractingDissimilarExerciseActivity.this,
                            SubtractingDissimilarVideoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 4000);
        } else {
            txtInstruction.setText("Wrong.");
            inputNum.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionAnswer().getNumerator()));
            inputDenom.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionAnswer().getDenominator()));
            Styles.paintRed(inputNum);
            Styles.paintRed(inputDenom);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    go();
                    Styles.paintBlack(inputNum);
                    Styles.paintBlack(inputDenom);
                }
            }, 3000);
        }
    }
    public void setQuestions() {
        fractionQuestions = new ArrayList<>();
        questionNum = 0;
        for (int i = 0; i < requiredConsecutiveCorrects; i++){
            fractionQuestion = new FractionQuestion(FractionQuestion.SUBTRACTING_DISSIMILAR);
            fractionQuestions.add(fractionQuestion);
        }
    }
    public void setFractionGui(){
        txtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getNumerator()));
        txtNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getNumerator()));
        txtDenom1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
        txtDenom2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
    }
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
        diagLcmtxtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
        diagLcmtxtNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
        diagLcmtxtNum3.setText("");
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
                        popUpEquationDialog(String.valueOf(fractionQuestions.get(questionNum).getFractionAnswer().getDenominator()),
                                String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()),
                                "÷");
                    }
                    if (viewIds.get(1)==txtDenom2.getId()){
                        popUpEquationDialog(String.valueOf(fractionQuestions.get(questionNum).getFractionAnswer().getDenominator()),
                                String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()),
                                "÷");
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
                        popUpEquationDialog(String.valueOf(txtEquation1.getText()),
                                String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getNumerator()),
                                "x");
                    }
                    if (viewIds.get(1)==txtNum2.getId()){
                        popUpEquationDialog(String.valueOf(txtEquation2.getText()),
                                String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getNumerator()),
                                "x");
                    }
                } else {
                    viewIds.remove(viewIds.size() - 1);
                }
            }
        }
    }
    private class DiagLcmBtnCheckListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (Integer.valueOf(String.valueOf(diagLcmInputLcm.getText())).equals(fractionQuestions.get(questionNum)
                    .getFractionAnswer().getDenominator())){
                txtDenom3.setText(""+fractionQuestions.get(questionNum).getFractionAnswer().getDenominator());
                txtDenom4.setText(""+fractionQuestions.get(questionNum).getFractionAnswer().getDenominator());
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
    private class DialogListener implements DialogInterface.OnDismissListener {
        @Override
        public void onDismiss(DialogInterface dialog) {
            resetColors();
            viewIds.clear();
            diagEdInputAnswer.setText("");
            diagLcmInputLcm.setText("");
        }
    }
    private class DiagEdBtnCheckListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!diagEdInputAnswer.getText().toString().matches("")){
                if (String.valueOf(diagEdTxtSign.getText())=="÷"){
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
                } else if (String.valueOf(diagEdTxtSign.getText())=="x"){
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
            if (!inputNum.getText().toString().matches("")) {
                if (!inputDenom.getText().toString().matches("")) {
                    if (String.valueOf(inputNum.getText()).matches(String.valueOf(fractionQuestions.get(questionNum)
                            .getFractionAnswer().getNumerator())) &&
                            String.valueOf(inputDenom.getText()).matches(String.valueOf(fractionQuestions.get(questionNum)
                                    .getFractionAnswer().getDenominator()))) {
                        correct();
                    } else {
                        wrong();
                    }
                } else {
                    Styles.shakeAnimate(inputDenom);
                }
            } else {
                Styles.shakeAnimate(inputNum);
            }
        }
    }
}