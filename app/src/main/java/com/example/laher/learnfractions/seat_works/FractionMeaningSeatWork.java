package com.example.laher.learnfractions.seat_works;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.SeatWorkListActivity;
import com.example.laher.learnfractions.dialog_layout.SeatWorkStatDialog;
import com.example.laher.learnfractions.model.SeatWork;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class FractionMeaningSeatWork extends SeatWork {
    Context mContext = this;
    private static final String TAG = "FM_E2";

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Fraction Meaning";
    //GUI
    ImageView imgBox1, imgBox2, imgBox3, imgBox4, imgBox5, imgBox6, imgBox7, imgBox8, imgBox9;
    EditText inputNum, inputDenom;
    TextView txtItemIndicator, txtInstruction;
    Button btnOK;
    ConstraintLayout clChoices;
    Button btnChoice1, btnChoice2, btnChoice3, btnChoice4;
    //VARIABLES
    int num, denom;
    final static String INPUT_NUM = "INPUT_NUM";
    final static String INPUT_DENOM = "INPUT_DENOM";
    String instruction;

    long startingTime;

    Handler handler = new Handler();

    public FractionMeaningSeatWork() {
        super();
    }

    public FractionMeaningSeatWork(String topicName, int seatWorkNum) {
        super(topicName, seatWorkNum);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_meaning_exercise2);
        setTopicName(AppConstants.FRACTION_MEANING);
        setSeatWorkNum(1);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);
        btnBack.setOnClickListener(new BtnBackListener());
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        //GUI
        clChoices = findViewById(R.id.a1_clChoices);
        imgBox1 = findViewById(R.id.a1_imgBox1);
        imgBox2 = findViewById(R.id.a1_imgBox2);
        imgBox3 = findViewById(R.id.a1_imgBox3);
        imgBox4 = findViewById(R.id.a1_imgBox4);
        imgBox5 = findViewById(R.id.a1_imgBox5);
        imgBox6 = findViewById(R.id.a1_imgBox6);
        imgBox7 = findViewById(R.id.a1_imgBox7);
        imgBox8 = findViewById(R.id.a1_imgBox8);
        imgBox9 = findViewById(R.id.a1_imgBox9);
        inputNum = findViewById(R.id.a1_numerator);
        inputDenom = findViewById(R.id.a1_denominator);
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        inputNum.setHintTextColor(Color.rgb(0,0,0));
        inputDenom.setHintTextColor(Color.rgb(0,0,0));
        btnOK = findViewById(R.id.a1_btnOk);
        btnOK.setVisibility(View.INVISIBLE);
        txtItemIndicator = findViewById(R.id.a1_txtScore);
        txtInstruction = findViewById(R.id.a1_txtInstruction);
        btnChoice1 = findViewById(R.id.fe_btnChoice1);
        btnChoice2 = findViewById(R.id.fe_btnChoice2);
        btnChoice3 = findViewById(R.id.fe_btnChoice3);
        btnChoice4 = findViewById(R.id.a1_btnChoice4);
        btnChoice1.setOnClickListener(new BtnChoiceListener());
        btnChoice2.setOnClickListener(new BtnChoiceListener());
        btnChoice3.setOnClickListener(new BtnChoiceListener());
        btnChoice4.setOnClickListener(new BtnChoiceListener());

        int item_size = Objects.requireNonNull(getIntent().getExtras()).getInt("item_size");
        if (item_size != 0){
            setItems_size(item_size);
            updateItemIndicator(txtItemIndicator);
        }
        go();
        instruction = INPUT_NUM;
        startingTime = System.currentTimeMillis();
    }
    public void go(){

        generateFraction();
        setBoxes(num, denom);
        inputNum.setText("");
        inputDenom.setText("");
        updateItemIndicator(txtItemIndicator);
        setClChoices();
    }
    public void setClChoices(){
        ArrayList<Integer> choices = new ArrayList<>();
        choices.add(num);
        if (num!=denom) {
            choices.add(denom);
        }
        while(choices.size()!=4){
            int random = (int) (Math.random() * 9 + 1);
            while (random==num || random==denom || choices.contains(random)) {
                random = (int) (Math.random() * 9 + 1);
            }
            choices.add(random);
        }
        Collections.shuffle(choices);
        btnChoice1.setText(String.valueOf(choices.get(0)));
        btnChoice2.setText(String.valueOf(choices.get(1)));
        btnChoice3.setText(String.valueOf(choices.get(2)));
        btnChoice4.setText(String.valueOf(choices.get(3)));
    }
    public void generateFraction(){
        num = (int) (Math.random() * 9 + 1);
        denom = (int) (Math.random() * 9 + 1);
        while (denom<num) {
            denom = (int) (Math.random() * 9 + 1);
        }
    }
    public void setBoxes(int num, int denom){
        denom = denom - num;
        ArrayList<Integer> imageList = new ArrayList<>();
        for (int i = 1; i <= num; i++){
            imageList.add(R.drawable.chocolate);
        }
        for (int i = 1; i <= denom; i++){
            imageList.add(R.drawable.chocosmudge);
        }
        for (int i = imageList.size(); i <= 9; i++){
            imageList.add(0);
        }
        imgBox1.setImageResource(imageList.get(0));
        imgBox2.setImageResource(imageList.get(1));
        imgBox3.setImageResource(imageList.get(2));
        imgBox4.setImageResource(imageList.get(3));
        imgBox5.setImageResource(imageList.get(4));
        imgBox6.setImageResource(imageList.get(5));
        imgBox7.setImageResource(imageList.get(6));
        imgBox8.setImageResource(imageList.get(7));
        imgBox9.setImageResource(imageList.get(8));
    }

    public class BtnBackListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            goBack();
        }
    }
    public void goBack(){
        Intent intent = new Intent(FractionMeaningSeatWork.this,
                SeatWorkListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public class BtnChoiceListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
                if (instruction.matches(INPUT_NUM)) {
                    int ans = Integer.valueOf(String.valueOf(b.getText()));
                    inputNum.setText(String.valueOf(ans));
                    instruction = INPUT_DENOM;
                    setClChoices();
                } else if (instruction.matches(INPUT_DENOM)) {
                    int ans = Integer.valueOf(String.valueOf(b.getText()));
                    inputDenom.setText(String.valueOf(ans));
                    if (inputNum.getText().toString().trim().matches(String.valueOf(num)) &&
                            inputDenom.getText().toString().trim().matches(String.valueOf(denom))) {
                        incrementCorrect();
                    }
                    incrementItemNum();
                    instruction = INPUT_NUM;
                    if (getCurrentItemNum() <= getItems_size()) {
                        go();
                    } else {
                        long endingTime = System.currentTimeMillis();
                        btnChoice1.setEnabled(false);
                        btnChoice2.setEnabled(false);
                        btnChoice3.setEnabled(false);
                        btnChoice4.setEnabled(false);
                        setTimeSpent(endingTime - startingTime);
                        Student student = new Student();
                        student.setId(Storage.load(mContext,Storage.STUDENT_ID));
                        student.setTeacher_code(Storage.load(mContext,Storage.TEACHER_CODE));
                        SeatWorkStatDialog seatWorkStatDialog = new SeatWorkStatDialog(mContext, FractionMeaningSeatWork.this, student);
                        seatWorkStatDialog.show();
                        seatWorkStatDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                goBack();
                            }
                        });
                    }
                }
        }
    }
}
