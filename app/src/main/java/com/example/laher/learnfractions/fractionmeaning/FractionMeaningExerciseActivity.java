package com.example.laher.learnfractions.fractionmeaning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;

import java.util.ArrayList;
import java.util.Collections;

public class FractionMeaningExerciseActivity extends AppCompatActivity {
    ImageView imgBox1, imgBox2, imgBox3, imgBox4, imgBox5, imgBox6, imgBox7, imgBox8, imgBox9;
    Button btnChoice1, btnChoice2, btnChoice3, btnChoice4;
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Fraction Meaning";
    TextView txtScore, txtInstruction;
    ArrayList<String> instructions;
    Button btnTest;
    String strCorrectAns;
    int num, denom, consecutiveRights, consecutiveWrongs;
    public final String INSTRUCTION_DENOM = "click how many parts the whole is divided into";
    public final String INSTRUCTION_NUM = "click how many parts we have";
    int requiredConsecutiveCorrects = 6;
    int maxConsecutiveWrongs = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_meaning_exercise);
        imgBox1 = (ImageView) findViewById(R.id.a_imgBox1);
        imgBox2 = (ImageView) findViewById(R.id.a_imgBox2);
        imgBox3 = (ImageView) findViewById(R.id.a_imgBox3);
        imgBox4 = (ImageView) findViewById(R.id.a_imgBox4);
        imgBox5 = (ImageView) findViewById(R.id.a_imgBox5);
        imgBox6 = (ImageView) findViewById(R.id.a_imgBox6);
        imgBox7 = (ImageView) findViewById(R.id.a_imgBox7);
        imgBox8 = (ImageView) findViewById(R.id.a_imgBox8);
        imgBox9 = (ImageView) findViewById(R.id.a_imgBox9);
        btnChoice1 = (Button) findViewById(R.id.btnChoice1);
        btnChoice2 = (Button) findViewById(R.id.btnChoice2);
        btnChoice3 = (Button) findViewById(R.id.btnChoice3);
        btnChoice4 = (Button) findViewById(R.id.btnChoice4);
        btnChoice1.setOnClickListener(new BtnChoiceListener());
        btnChoice2.setOnClickListener(new BtnChoiceListener());
        btnChoice3.setOnClickListener(new BtnChoiceListener());
        btnChoice4.setOnClickListener(new BtnChoiceListener());
        txtInstruction = (TextView) findViewById(R.id.txtInstruction);
        txtScore = (TextView) findViewById(R.id.txtScore);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnBack.setOnClickListener(new BtnBackListener());
        btnNext.setOnClickListener(new BtnNextListener());
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);

        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        instructions = new ArrayList<String>();
        instructions.add(INSTRUCTION_DENOM);
        instructions.add(INSTRUCTION_NUM);
        consecutiveRights = 0;
        consecutiveWrongs = 0;

        go();
    }
    public void go(){
        btnNext.setEnabled(false);

        generateFraction();
        setBoxes(num, denom);
        setTxtInstruction();
        setButtonChoices(Integer.valueOf(strCorrectAns));
    }
    public void setBoxes(int num, int denom){
        denom = denom - num;
        ArrayList<Integer> imageList = new ArrayList<Integer>();
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
    public void setButtonChoices(int correctAnswer){
        strCorrectAns = String.valueOf(correctAnswer);
        ArrayList<Integer> choiceNums = new ArrayList<>();
        //FOUR CHOICES
        choiceNums.add(correctAnswer);
        int randomNum;
        boolean numAvailable = false;
        for(int i = 0; i < 3; i++){
            randomNum = (int) (Math.random() * 9 + 1);
            while (choiceNums.contains(randomNum)){
                randomNum = (int) (Math.random() * 9 + 1);
            }
            choiceNums.add(randomNum);
        }
        Collections.shuffle(choiceNums);
        btnChoice1.setText(String.valueOf(choiceNums.get(0)));
        btnChoice2.setText(String.valueOf(choiceNums.get(1)));
        btnChoice3.setText(String.valueOf(choiceNums.get(2)));
        btnChoice4.setText(String.valueOf(choiceNums.get(3)));
    }
    public void setTxtInstruction(){
        Collections.shuffle(instructions);
        txtInstruction.setText(instructions.get(0));
        if(instructions.get(0) == INSTRUCTION_DENOM){
            strCorrectAns = String.valueOf(denom);
        } else if (instructions.get(0) == INSTRUCTION_NUM){
            strCorrectAns = String.valueOf(num);
        }
    }
    public void generateFraction(){
        num = (int) (Math.random() * 9 + 1);
        denom = (int) (Math.random() * 9 + 1);
        while (denom<num) {
            denom = (int) (Math.random() * 9 + 1);
        }
    }
    public void resetValues(){
        consecutiveRights = 0;
        consecutiveWrongs = 0;
        instructions.clear();
        instructions.add(INSTRUCTION_DENOM);
        instructions.add(INSTRUCTION_NUM);
    }
    public void finishExercise(){
        btnChoice1.setEnabled(false);
        btnChoice2.setEnabled(false);
        btnChoice3.setEnabled(false);
        btnChoice4.setEnabled(false);
        btnNext.setEnabled(true);
    }
    public class BtnChoiceListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Button choice = (Button) view;
            if (choice.getText() == strCorrectAns) {
                //FOR TESTS
                //txtInstruction.setText("correct " + choice.getText() + " / " + strCorrectAns + " " + consecutiveRights);
                consecutiveWrongs = 0;
                consecutiveRights++;
                txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
                if (consecutiveRights >= requiredConsecutiveCorrects) {
                    txtInstruction.setText("finish " + consecutiveRights);
                    finishExercise();
                } else {
                    instructions.remove(0);
                    if (instructions.size() == 0) {
                        instructions.add(INSTRUCTION_DENOM);
                        instructions.add(INSTRUCTION_NUM);
                        go();
                    } else {
                        setTxtInstruction();
                        setButtonChoices(Integer.parseInt(strCorrectAns));
                    }
                }
            } else {
                consecutiveRights = 0;
                consecutiveWrongs++;
                txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
                if (consecutiveWrongs >= maxConsecutiveWrongs) {
                    txtInstruction.setText("Go watch the video again.");
                    btnChoice1.setEnabled(false);
                    btnChoice2.setEnabled(false);
                    btnChoice3.setEnabled(false);
                    btnChoice4.setEnabled(false);
                } else {
                    instructions.clear();
                    instructions.add(INSTRUCTION_DENOM);
                    instructions.add(INSTRUCTION_NUM);
                    go();
                }
                //FOR TESTS
                //txtInstruction.setText("wrong " + choice.getText() + " / " + strCorrectAns);
            }
        }
    }

    public class BtnBackListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    public class BtnNextListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(FractionMeaningExerciseActivity.this, FractionMeaningExercise2Activity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onRestart() {
        resetValues();
        btnChoice1.setEnabled(true);
        btnChoice2.setEnabled(true);
        btnChoice3.setEnabled(true);
        btnChoice4.setEnabled(true);
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        go();
        super.onRestart();
    }
}
