package com.example.laher.learnfractions.seat_works;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.SeatWorkListActivity;
import com.example.laher.learnfractions.dialog_layout.SeatWorkStatDialog;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestion;
import com.example.laher.learnfractions.model.SeatWork;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;

import java.util.ArrayList;
import java.util.Objects;

public class ComparingSimilarSeatWork extends SeatWork {
    public static final String TAG = "CS_SW1";
    Context mContext = this;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Comparing Similar";
    //GUI
    TextView txtNum1, txtNum2, txtDenom1, txtDenom2, txtCompareSign, txtItemIndicator, txtInstruction;
    Button btnGreater, btnEquals, btnLess;
    //VARIABLES
    int questionNum;
    Fraction fractionOne, fractionTwo;
    FractionQuestion fractionQuestion;
    ArrayList<FractionQuestion> fractionQuestions;
    String strAnswer;
    long startingTime;

    public ComparingSimilarSeatWork(String topicName, int seatWorkNum) {
        super(topicName, seatWorkNum);
    }

    public ComparingSimilarSeatWork() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparing_similar_exercise2);
        setTopicName(AppConstants.COMPARING_SIMILAR_FRACTIONS);
        setSeatWorkNum(1);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComparingSimilarSeatWork.this,
                        SeatWorkListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        //GUI
        txtNum1 = findViewById(R.id.c2_num1);
        txtNum2 = findViewById(R.id.c2_num2);
        txtDenom1 = findViewById(R.id.c2_denom1);
        txtDenom2 = findViewById(R.id.c2_denom2);
        txtCompareSign = findViewById(R.id.c2_compareSign);
        txtItemIndicator = findViewById(R.id.c2_txtScore);
        updateItemIndicator(txtItemIndicator);
        txtInstruction = findViewById(R.id.c2_txtInstruction);

        btnGreater = findViewById(R.id.c2_btnGreater);
        btnEquals = findViewById(R.id.c2_btnEqual);
        btnLess = findViewById(R.id.c2_btnLess);
        btnGreater.setText(FractionQuestion.ANSWER_GREATER);
        btnEquals.setText(FractionQuestion.ANSWER_EQUAL);
        btnLess.setText(FractionQuestion.ANSWER_LESS);
        btnGreater.setOnClickListener(new BtnListener());
        btnEquals.setOnClickListener(new BtnListener());
        btnLess.setOnClickListener(new BtnListener());
        //VARIABLES
        fractionOne = new Fraction();
        fractionTwo = new Fraction();
        fractionQuestion = new FractionQuestion();
        fractionQuestions = new ArrayList<>();

        int item_size = Objects.requireNonNull(getIntent().getExtras()).getInt("item_size");
        if (item_size != 0){
            setItems_size(item_size);
            updateItemIndicator(txtItemIndicator);
        }
        go();
        startingTime = System.currentTimeMillis();
    }
    public void go(){
        setFractionQuestions();
    }
    public void setFractionQuestions(){
        fractionQuestions = new ArrayList<>();
        questionNum = 0;
        for(int i = 0; i < getItems_size(); i++){
            fractionQuestion = new FractionQuestion(FractionQuestion.COMPARING_SIMILAR);
            fractionQuestions.add(fractionQuestion);
        }
        setTxtFractions();
    }
    public void setTxtFractions(){
        txtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getNumerator()));
        txtNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getNumerator()));
        txtDenom1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
        txtDenom2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
        strAnswer = fractionQuestions.get(questionNum).getAnswer();
        txtInstruction.setText(AppConstants.I_COMPARE);
        txtCompareSign.setText("_");
    }
    public void enableButtons(boolean bool){
        btnGreater.setEnabled(bool);
        btnEquals.setEnabled(bool);
        btnLess.setEnabled(bool);
    }
    public class BtnListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            String s = b.getText().toString();
            txtCompareSign.setText(s);
            if (s.matches(fractionQuestions.get(questionNum).getAnswer())){
                incrementCorrect();
            }
            if (getCurrentItemNum()>= getItems_size()){
                enableButtons(false);
                long endingTime = System.currentTimeMillis();
                setTimeSpent(endingTime-startingTime);
                Student student = new Student();
                student.setId(Storage.load(mContext,Storage.STUDENT_ID));
                student.setTeacher_code(Storage.load(mContext,Storage.TEACHER_CODE));
                SeatWorkStatDialog seatWorkStatDialog = new SeatWorkStatDialog(mContext, ComparingSimilarSeatWork.this, student);
                seatWorkStatDialog.show();
                seatWorkStatDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Intent intent = new Intent(ComparingSimilarSeatWork.this,
                                SeatWorkListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
            } else {
                questionNum++;
                setTxtFractions();
                incrementItemNum();
                updateItemIndicator(txtItemIndicator);
            }
        }
    }
}
