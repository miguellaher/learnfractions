package com.example.laher.learnfractions.seat_works;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.ChapterExamListActivity;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.dialog_layout.ConfirmationDialog;
import com.example.laher.learnfractions.dialog_layout.SeatWorkStatDialog;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.SubtractingDissimilarFractionsQuestion;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.util.AppCache;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class SubtractingDissimilarSeatWork extends SeatWork {
    Context mContext = this;
    //FRACTION EQUATION GUI
    TextView txtNum1;
    TextView txtNum2;
    TextView txtDenom1;
    TextView txtDenom2;
    TextView txtSign;
    TextView txtIndicator;
    TextView txtInstruction;
    EditText inputNum;
    EditText inputDenom;
    Button btnCheck;
    Button btnChoice1;
    Button btnChoice2;
    Button btnChoice3;
    Button btnChoice4;
    //VARIABLES
    private String TYPE;
    boolean shouldAllowBack;

    ArrayList<SubtractingDissimilarFractionsQuestion> questions;
    int questionNum;
    String correctAns;

    public SubtractingDissimilarSeatWork(String topicName) {
        super(topicName);
        String id = AppIDs.SDS;
        setId(id);
        Range range = getRange();
        Probability probability = new Probability(Probability.SUBTRACTING_2DISSIMILAR_F, range);
        setProbability(probability);
        setRangeEditable(true);
    }

    public SubtractingDissimilarSeatWork(int size) {
        super(size);
        setTopicName(AppConstants.SUBTRACTING_DISSIMILAR);
        setSeatWorkNum(1);
    }

    public SubtractingDissimilarSeatWork() {
        setTopicName(AppConstants.SUBTRACTING_DISSIMILAR);
        setSeatWorkNum(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fraction_equation);
        super.onCreate(savedInstanceState);
        String id = AppIDs.SDS;
        setId(id);
        //FRACTION EQUATION GUI
        txtNum1 = findViewById(R.id.fe_txtNum1);
        txtNum2 = findViewById(R.id.fe_txtNum2);
        txtDenom1 = findViewById(R.id.fe_txtDenom1);
        txtDenom2 = findViewById(R.id.fe_txtDenom2);
        txtSign = findViewById(R.id.fe_txtSign);
        txtSign.setText("-");
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

        shouldAllowBack = true;

        try {
            int item_size = Objects.requireNonNull(getIntent().getExtras()).getInt("item_size");
            if (item_size != 0) {
                setItems_size(item_size);
                updateItemIndicator(txtIndicator);
            }
            TYPE = Objects.requireNonNull(getIntent().getExtras()).getString("type");
            assert TYPE != null;
            if (TYPE.equals(AppConstants.CHAPTER_EXAM)){
                String title = AppCache.getChapterExam().getExamTitle();
                txtTitle.setText(title);
                shouldAllowBack = false;
                buttonBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ConfirmationDialog confirmationDialog = new ConfirmationDialog(mContext,"Are you sure you want to exit exam?");
                        confirmationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                if (confirmationDialog.isConfirmed()){
                                    Intent intent = new Intent(SubtractingDissimilarSeatWork.this,
                                            ChapterExamListActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }
                        });
                        confirmationDialog.show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disableInputFraction();
        go();
    }
    @Override
    protected void go(){
        super.go();
        setQuestions();
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
        questions = new ArrayList<>();
        Range range = getRange();
        for(int i = 0; i < getItems_size(); i++){
            SubtractingDissimilarFractionsQuestion question = new SubtractingDissimilarFractionsQuestion(range);
            while (questions.contains(question)){
                question = new SubtractingDissimilarFractionsQuestion(range);
            }
            questions.add(question);
        }
        setGuiFractions();
    }
    public void setBtnChoices(){
        ArrayList<String> choices = new ArrayList<>();
        SubtractingDissimilarFractionsQuestion question = questions.get(questionNum);
        Fraction fractionAnswer = question.getFractionAnswer();
        int numeratorAnswer = fractionAnswer.getNumerator();
        int denominatorAnswer = fractionAnswer.getDenominator();
        correctAns = numeratorAnswer + " / " + denominatorAnswer;
        choices.add(correctAns);
        for (int i = 0; i < 3; i++){
            int num;
            int denom;
            if (i == 0){
                num = logicalRandomNum(numeratorAnswer);
                denom = denominatorAnswer;
            } else if (i == 1){
                num = numeratorAnswer;
                denom = logicalRandomNum(denominatorAnswer);
            } else {
                num = logicalRandomNum(numeratorAnswer);
                denom = logicalRandomNum(denominatorAnswer);
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
        SubtractingDissimilarFractionsQuestion question = questions.get(questionNum);
        Fraction fraction1 = question.getFraction1();
        Fraction fraction2 = question.getFraction2();
        int numerator1 = fraction1.getNumerator();
        int numerator2 = fraction2.getNumerator();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        String strNumerator1 = String.valueOf(numerator1);
        String strNumerator2 = String.valueOf(numerator2);
        String strDenominator1 = String.valueOf(denominator1);
        String strDenominator2 = String.valueOf(denominator2);

        String instruction = "Solve the equation.";
        txtInstruction.setText(instruction);
        txtNum1.setText(strNumerator1);
        txtNum2.setText(strNumerator2);
        txtDenom1.setText(strDenominator1);
        txtDenom2.setText(strDenominator2);
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
                enableBtnChoices(false);
                if (TYPE!=null) {
                    if (TYPE.equals(AppConstants.CHAPTER_EXAM)) {
                        AppCache.postSeatWorkStat(SubtractingDissimilarSeatWork.this);
                        SeatWorkStatDialog seatWorkStatDialog = new SeatWorkStatDialog(mContext, SubtractingDissimilarSeatWork.this);
                        seatWorkStatDialog.show();
                        seatWorkStatDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        });
                    }
                } else {
                    seatworkFinished();
                }
            } else {
                nextQuestion();
            }
        }
    }
    @Override
    public void onBackPressed() {
        if (shouldAllowBack){
            super.onBackPressed();
        }
    }
}
