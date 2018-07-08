package com.example.laher.learnfractions.seat_works;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.SeatworkListActivity;
import com.example.laher.learnfractions.dialog_layout.SeatWorkStatDialog;
import com.example.laher.learnfractions.fraction_util.FractionQuestion;
import com.example.laher.learnfractions.model.SeatWork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MultiplyingFractionsSeatWork extends SeatWork {
    Context mContext = this;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Multiplying Fractions";
    //FRACTION EQUATION GUI
    TextView txtNum1, txtNum2, txtDenom1, txtDenom2, txtSign, txtIndicator, txtInstruction;
    EditText inputNum, inputDenom;
    Button btnCheck;
    Button btnChoice1, btnChoice2, btnChoice3, btnChoice4;
    //VARIABLES
    FractionQuestion fractionQuestion;
    ArrayList<FractionQuestion> fractionQuestions;
    int questionNum;
    String correctAns;
    long startingTime;

    public MultiplyingFractionsSeatWork(String topicName, int seatworkNum) {
        super(topicName, seatworkNum);
    }

    public MultiplyingFractionsSeatWork() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_equation);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MultiplyingFractionsSeatWork.this,
                        SeatworkListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        //FRACTION EQUATION GUI
        txtNum1 = findViewById(R.id.fe_txtNum1);
        txtNum2 = findViewById(R.id.fe_txtNum2);
        txtDenom1 = findViewById(R.id.fe_txtDenom1);
        txtDenom2 = findViewById(R.id.fe_txtDenom2);
        txtSign = findViewById(R.id.fe_txtSign);
        txtSign.setText("x");
        txtIndicator = findViewById(R.id.fe_txtScore);
        txtInstruction = findViewById(R.id.fe_txtInstruction);
        inputNum = findViewById(R.id.fe_inputNum);
        inputDenom = findViewById(R.id.fe_inputDenom);
        btnCheck = findViewById(R.id.fe_btnCheck);
        btnCheck.setVisibility(View.INVISIBLE);
        btnChoice1 = findViewById(R.id.fe_btnChoice1);
        btnChoice2 = findViewById(R.id.fe_btnChoice2);
        btnChoice3 = findViewById(R.id.fe_btnChoice3);
        btnChoice4 = findViewById(R.id.fe_btnChoice4);
        btnChoice1.setOnClickListener(new BtnChoiceListener());
        btnChoice2.setOnClickListener(new BtnChoiceListener());
        btnChoice3.setOnClickListener(new BtnChoiceListener());
        btnChoice4.setOnClickListener(new BtnChoiceListener());

        int item_size = Objects.requireNonNull(getIntent().getExtras()).getInt("item_size");
        if (item_size != 0){
            setItems_size(item_size);
            updateItemIndicator(txtIndicator);
        }
        disableInputFraction();
        startingTime = System.currentTimeMillis();
        go();
    }
    public void go(){
        setQuestions();
        setGuiFractions();
        setBtnChoices();
        startUp();
    }
    public void nextQuestion(){
        questionNum++;
        setGuiFractions();
        setBtnChoices();
        updateItemIndicator(txtIndicator);
        clearInputFraction();
    }
    public void startUp(){
        updateItemIndicator(txtIndicator);
        clearInputFraction();
    }
    public void clearInputFraction(){
        inputNum.setText("");
        inputDenom.setText("");
        inputNum.requestFocus();
    }
    public void setQuestions(){
        questionNum = 0;
        fractionQuestions = new ArrayList<>();
        for(int i = 0; i < getItems_size(); i++){
            fractionQuestion = new FractionQuestion(FractionQuestion.MULTIPLYING_FRACTIONS);
            fractionQuestions.add(fractionQuestion);
        }
    }
    public void setBtnChoices(){
        ArrayList<String> choices = new ArrayList<>();
        int correctNum = fractionQuestions.get(questionNum).getFractionAnswer().getNumerator();
        int correctDenom = fractionQuestions.get(questionNum).getFractionAnswer().getDenominator();
        correctAns = correctNum + " / " + correctDenom;
        choices.add(correctAns);
        for (int i = 0; i < 3; i++){
            int num;
            int denom;
            if (i == 0){
                num = logicalRandomNum(correctNum);
                denom = correctDenom;
            } else if (i == 1){
                num = correctNum;
                denom = logicalRandomNum(correctDenom);
            } else {
                num = logicalRandomNum(correctNum);
                denom = logicalRandomNum(correctDenom);
            }
            String choice = num + " / " + denom;
            choices.add(choice);
        }
        Collections.shuffle(choices);
        btnChoice1.setText(choices.get(0));
        btnChoice2.setText(choices.get(1));
        btnChoice3.setText(choices.get(2));
        btnChoice4.setText(choices.get(3));
    }
    public void setGuiFractions(){
        txtInstruction.setText("Solve the equation.");
        txtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getNumerator()));
        txtNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getNumerator()));
        txtDenom1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
        txtDenom2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
    }
    public void disableInputFraction(){
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
    }
    public int logicalRandomNum(int baseNumber){
        int number;
        int distanceNum = 2;
        int randomNum = (int) (Math.random() * distanceNum + 1);
        int randomNum2 = (int) (Math.random() * 2 + 1);
        if (baseNumber>distanceNum && randomNum2 == 1){
            number = baseNumber - randomNum;
            return number;
        }
        number = baseNumber + randomNum;
        return number;

    }
    public void enableBtnChoices(boolean b){
        btnChoice1.setEnabled(b);
        btnChoice2.setEnabled(b);
        btnChoice3.setEnabled(b);
        btnChoice4.setEnabled(b);
    }
    public class BtnChoiceListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            String ans = b.getText().toString().trim();
            if (ans.equals(correctAns)){
                incrementCorrect();
            }
            incrementItemNum();
            if (getCurrentItemNum()>getItems_size()){
                long endingTime = System.currentTimeMillis();
                enableBtnChoices(false);
                setTimeSpent(endingTime - startingTime);
                SeatWorkStatDialog seatWorkStatDialog = new SeatWorkStatDialog(mContext, MultiplyingFractionsSeatWork.this);
                seatWorkStatDialog.show();
                seatWorkStatDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Intent intent = new Intent(MultiplyingFractionsSeatWork.this,
                                SeatworkListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
            } else {
                nextQuestion();
            }
        }
    }
}
