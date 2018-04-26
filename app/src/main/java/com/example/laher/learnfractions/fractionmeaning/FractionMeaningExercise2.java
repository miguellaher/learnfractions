package com.example.laher.learnfractions.fractionmeaning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;

import java.util.ArrayList;
import java.util.Collections;

public class FractionMeaningExercise2 extends AppCompatActivity {
    ImageView imgBox1, imgBox2, imgBox3, imgBox4, imgBox5, imgBox6, imgBox7, imgBox8, imgBox9;
    EditText inputNum, inputDenom;
    TextView txtScore, txtInstruction;
    ArrayList<String> instructions;
    Button btnTest, btnOK;
    int num, denom, consecutiveRights, consecutiveWrongs;
    int maxInputLength = 3;
    int requiredConsecutiveCorrects = 5;
    int maxConsecutiveWrongs = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_meaning_exercise2);
        imgBox1 = (ImageView) findViewById(R.id.a1_imgBox1);
        imgBox2 = (ImageView) findViewById(R.id.a1_imgBox2);
        imgBox3 = (ImageView) findViewById(R.id.a1_imgBox3);
        imgBox4 = (ImageView) findViewById(R.id.a1_imgBox4);
        imgBox5 = (ImageView) findViewById(R.id.a1_imgBox5);
        imgBox6 = (ImageView) findViewById(R.id.a1_imgBox6);
        imgBox7 = (ImageView) findViewById(R.id.a1_imgBox7);
        imgBox8 = (ImageView) findViewById(R.id.a1_imgBox8);
        imgBox9 = (ImageView) findViewById(R.id.a1_imgBox9);

        inputNum = (EditText) findViewById(R.id.a1_numerator);
        inputDenom = (EditText) findViewById(R.id.a1_denominator);
        inputNum.setOnKeyListener(new InputListener());
        inputDenom.setOnKeyListener(new InputListener());
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        inputNum.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(maxInputLength)
        });
        inputDenom.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(maxInputLength)
        });

        btnOK = (Button) findViewById(R.id.a1_btnOk);
        btnOK.setOnClickListener(new BtnOkListener());
        btnOK.setEnabled(false);

        txtScore = (TextView) findViewById(R.id.a1_txtScore);
        txtInstruction = (TextView) findViewById(R.id.a1_txtInstrucion);

        btnTest = (Button) findViewById(R.id.a1_btnTest);
        btnTest.setOnClickListener(new BtnTestListener());
        go();
    }
    public void go(){
        generateFraction();
        setBoxes(num, denom);
        inputNum.setEnabled(true);
        inputDenom.setEnabled(true);
        inputNum.setText("");
        inputDenom.setText("");
        setTxtScore();
        btnOK.setEnabled(false);
        inputNum.requestFocus();
    }
    public void reset(){
        generateFraction();
        setBoxes(num, denom);
        inputNum.getText().clear();
        inputDenom.getText().clear();
        inputNum.requestFocus();
        btnOK.setEnabled(false);
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
    public void setTxtScore(){
        txtScore.setText(consecutiveRights + "/" + requiredConsecutiveCorrects);
    }
    public class BtnOkListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if ((Integer.parseInt(String.valueOf(inputNum.getText())) == num) &&
                    (Integer.parseInt(String.valueOf(inputDenom.getText())) == denom)){
                consecutiveWrongs = 0;
                consecutiveRights++;
                setTxtScore();
                txtInstruction.setText("correct");
                reset();
                if (consecutiveRights >= 5){
                    txtInstruction.setText("lesson ended");
                }
            } else {
                consecutiveRights = 0;
                consecutiveWrongs++;
                setTxtScore();
                reset();
                if (consecutiveWrongs >= maxConsecutiveWrongs){
                    txtInstruction.setText("go back to exercise 1");
                }
            }
        }
    }
    public class InputListener implements View.OnKeyListener{

        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if ((inputNum.getText().toString().trim().length() == 0)
                    || (inputDenom.getText().toString().trim().length() == 0)){
                btnOK.setEnabled(false);
            } else {
                txtInstruction.setText(inputNum.getText().toString().trim().length() + "/"
                        + inputDenom.getText().toString().trim().length());
                btnOK.setEnabled(true);
            }
            return false;
        }
    }
    public class BtnTestListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            reset();
        }
    }
}
