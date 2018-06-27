package com.example.laher.learnfractions.lessons.solving_mixed2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestion;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.dialog_layout.EquationDialog;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.DialogInterface.OnDismissListener;
import static android.content.DialogInterface.OnShowListener;

public class SolvingMixed2ExerciseActivity extends AppCompatActivity {
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
    Fraction eDMixedFraction;
    //VARIABLES
    ArrayList<FractionQuestion> fractionQuestions;
    int questionNum;
    int consecutiveRights, consecutiveWrongs;
    int requiredConsecutiveCorrects = 16;
    int maxConsecutiveWrongs = 5;
    final Handler handler = new Handler();
    ColorStateList defaultColor;
    int clicks;
    Context context;
    ArrayList<Integer> stepsListId;
    TextView txtContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_equation_mixed);
        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SolvingMixed2ExerciseActivity.this,
                        SolvingMixed2VideoActivity.class);
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
                Intent intent = new Intent(SolvingMixed2ExerciseActivity.this,
                        TopicsMenuActivity.class); //CHANGE
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
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
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        txtInstruction = findViewById(R.id.fem_txtInstruction);
        inputNum = findViewById(R.id.fem_inputNum);
        inputDenom = findViewById(R.id.fem_inputDenom);
        btnCheck = findViewById(R.id.fem_btnCheck);
        btnCheck.setOnClickListener(new BtnCheckListener());
        clFraction1 = findViewById(R.id.fem_clFraction1);
        clFraction2 = findViewById(R.id.fem_clFraction2);
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
        eDTxtNum1 = (TextView) edView.findViewById(R.id.md_txtMultiplicand);
        eDTxtNum2 = (TextView) edView.findViewById(R.id.md_txtMultiplier);
        eDTxtSign = (TextView) edView.findViewById(R.id.md_txtSign);
        eDInputAnswer = (EditText) edView.findViewById(R.id.md_inputProduct);
        eDBtnCheck = (Button) edView.findViewById(R.id.md_btnCheck);
        eDBtnCheck.setOnClickListener(new EDBtnCheckListener());
        defaultColor = txtNum1.getTextColors();
        stepsListId = new ArrayList<>();

        context = this;

        go();
    }
    public void go(){
        startUp();
        setFractionQuestions();
        setFractionGui();
    }
    public void correct(){
        consecutiveRights++;
        consecutiveWrongs = 0;
        answered();
        if (consecutiveRights >= requiredConsecutiveCorrects){
            txtInstruction.setText("Finished.");
            btnNext.setEnabled(true);
        } else {
            txtInstruction.setText("Correct.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startUp();
                    questionNum++;
                    setFractionGui();
                }
            }, 2000);
        }
    }
    public void wrong(){
        consecutiveWrongs++;
        consecutiveRights = 0;
        answered();
        if (consecutiveWrongs >= maxConsecutiveWrongs){
            txtInstruction.setText("You had " + consecutiveWrongs + " consecutive wrongs. Preparing to watch" +
                    " video again.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SolvingMixed2ExerciseActivity.this,
                            SolvingMixed2VideoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 4000);
        } else {
            txtInstruction.setText("Wrong.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    go();
                }
            }, 2000);
        }
    }
    public void answered(){
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
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
        inputNum.setHint("");
        inputDenom.setHint("");
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        btnCheck.setEnabled(false);
        txtInstruction.setText("Convert a mixed fraction to an improper fraction by clicking on it.");
    }
    public void setFractionQuestions(){
        fractionQuestions = new ArrayList<>();
        questionNum = 0;
        for (int i = 0; i < requiredConsecutiveCorrects; i++){
            FractionQuestion fractionQuestion;
            if (i < (requiredConsecutiveCorrects/2)){
                fractionQuestion = new FractionQuestion(FractionQuestion.MULTIPLYING_WITH_MIXED);
            } else {
                fractionQuestion = new FractionQuestion(FractionQuestion.DIVIDING_WITH_MIXED);
            }
            fractionQuestions.add(fractionQuestion);
        }
        Collections.shuffle(fractionQuestions);
    }
    public void setFractionGui(){
        if (fractionQuestions.get(questionNum).getFractionOne().getContext()== Fraction.MIXED){
            txtWholeNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getWholeNum()));
            clFraction1.setOnClickListener(new ClFractionListener());
        } else {
            txtWholeNum1.setText("");
        }
        if (fractionQuestions.get(questionNum).getFractionTwo().getContext()== Fraction.MIXED){
            txtWholeNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getWholeNum()));
            clFraction2.setOnClickListener(new ClFractionListener());
        } else {
            txtWholeNum2.setText("");
        }
        if (fractionQuestions.get(questionNum).getContext()==FractionQuestion.MULTIPLYING_WITH_MIXED){
            txtSign1.setText("x");
            txtSign2.setText("x");
        } else if (fractionQuestions.get(questionNum).getContext()==FractionQuestion.DIVIDING_WITH_MIXED){
            txtSign1.setText("÷");
            txtSign2.setText("");
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
    private void setHint(){
        if((Integer.valueOf(String.valueOf(txtNum3.getText())) > 10 &&
                Integer.valueOf(String.valueOf(txtNum4.getText())) > 1)||
                (Integer.valueOf(String.valueOf(txtNum4.getText())) > 10 &&
                        Integer.valueOf(String.valueOf(txtNum3.getText())) > 1)){
            inputNum.setHint(String.valueOf(fractionQuestions.get(questionNum).getFractionAnswer().getNumerator()));
        }
        if((Integer.valueOf(String.valueOf(txtDenom3.getText())) > 10 &&
                Integer.valueOf(String.valueOf(txtDenom4.getText())) > 1)||
                (Integer.valueOf(String.valueOf(txtDenom4.getText())) > 10 &&
                        Integer.valueOf(String.valueOf(txtDenom3.getText())) > 1)){
            inputDenom.setHint(String.valueOf(fractionQuestions.get(questionNum).getFractionAnswer().getDenominator()));
        }
    }
    private void setTxtInvertListener(boolean b){
        if (b){
            txtNum2.setOnClickListener(new TxtInvertListener());
            txtDenom2.setOnClickListener(new TxtInvertListener());
        } else {
            txtNum2.setOnClickListener(null);
            txtDenom2.setOnClickListener(null);
        }
        txtNum2.setClickable(b);
        txtDenom2.setClickable(b);
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
            if (!eDInputAnswer.toString().matches("")) {
                if (String.valueOf(eDTxtSign.getText()) == "x") {
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
                } else if (String.valueOf(eDTxtSign.getText()) == "+") {
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
                if (String.valueOf(eDTxtSign.getText())=="x"){
                    eDInputAnswer.setHint(String.valueOf(Integer.valueOf(String.valueOf(eDTxtNum1.getText()))*
                            Integer.valueOf(String.valueOf(eDTxtNum2.getText()))));
                }
            }
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
                if (fractionQuestions.get(questionNum).getContext()==FractionQuestion.MULTIPLYING_WITH_MIXED){
                    txtNum3.setText(String.valueOf(txtNum1.getText()));
                    txtDenom3.setText(String.valueOf(txtDenom1.getText()));
                    txtNum4.setText(String.valueOf(txtNum2.getText()));
                    txtDenom4.setText(String.valueOf(txtDenom2.getText()));
                    inputNum.setEnabled(true);
                    inputDenom.setEnabled(true);
                    btnCheck.setEnabled(true);
                    inputNum.requestFocus();
                    setHint();
                } else if (fractionQuestions.get(questionNum).getContext()==FractionQuestion.DIVIDING_WITH_MIXED){
                    txtNum3.setText(String.valueOf(txtNum1.getText()));
                    txtDenom3.setText(String.valueOf(txtDenom1.getText()));
                    randomizeContainer();
                    setTxtInvertListener(true);
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
                setHint();
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
}