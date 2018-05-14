package com.example.laher.learnfractions.comparing_dissimilar_fractions;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;

import java.util.ArrayList;

public class ComparingDissimilarExerciseActivity extends AppCompatActivity {
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Comparing Dissimilar";
    //MULTIPLICATION DIALOG
    Dialog multiplicationDialog;
    TextView diagTxtMultiplicand, diagTxtMultiplier;
    EditText diagInputProduct;
    Button dialogBtnCheck;
    //GUI
    TextView txtScore, txtProduct1, txtProduct2, txtNum1, txtNum2, txtDenom1, txtDenom2, txtInstruction;
    //VARIABLES
    ArrayList<Integer> stepsIdList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparing_dissimilar_exercise);
        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CHANGE INTENT PARAMS
                Intent intent = new Intent(ComparingDissimilarExerciseActivity.this, TopicsMenuActivity.class);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        //MULTIPLICATION DIALOG
        multiplicationDialog = new Dialog(ComparingDissimilarExerciseActivity.this);


        multiplicationDialog.setTitle("Multiplication Equation");
        multiplicationDialog.setContentView(R.layout.layout_dialog_multiplication);
        diagTxtMultiplicand = (TextView) findViewById(R.id.md_txtMultiplicand);
        diagTxtMultiplier = (TextView) findViewById(R.id.md_txtMultiplier);
        diagInputProduct = (EditText) findViewById(R.id.md_inputProduct);
        dialogBtnCheck = (Button) findViewById(R.id.md_btnCheck);
        //GUI
        txtScore = (TextView) findViewById(R.id.d1_txtScore);
        txtProduct1 = (TextView) findViewById(R.id.d1_txtProduct1);
        txtProduct2 = (TextView) findViewById(R.id.d1_txtProduct2);
        txtNum1 = (TextView) findViewById(R.id.d1_txtNum1);
        txtNum2 = (TextView) findViewById(R.id.d1_txtNum2);
        txtDenom1 = (TextView) findViewById(R.id.d1_txtDenom1);
        txtDenom2 = (TextView) findViewById(R.id.d1_txtDenom2);
        txtNum1.setOnClickListener(new TxtFractionListener());
        txtNum2.setOnClickListener(new TxtFractionListener());
        txtDenom1.setOnClickListener(new TxtFractionListener());
        txtDenom2.setOnClickListener(new TxtFractionListener());
        txtInstruction = (TextView) findViewById(R.id.d1_txtInstruction);
        stepsIdList = new ArrayList<>();


    }
    public class TxtFractionListener implements TextView.OnClickListener {
        @Override
        public void onClick(View v) {
            stepsIdList.add(v.getId());
            if (stepsIdList.size() == 1){
                if (stepsIdList.get(0) == txtDenom1.getId()){
                    txtDenom1.setTextColor(Color.rgb(0,255,0));
                } else if (stepsIdList.get(0) == txtDenom2.getId()){
                    txtDenom2.setTextColor(Color.rgb(0,255,0));
                } else {
                    //wrong
                    shakeAnimate(txtDenom1);
                    shakeAnimate(txtDenom2);
                    stepsIdList.clear();
                }
            }
            if (stepsIdList.size() == 2){
                if (stepsIdList.get(1) == txtNum1.getId()){
                    if (stepsIdList.get(0) == txtDenom2.getId()) {
                        txtNum1.setTextColor(Color.rgb(0, 255, 0));
                        getProduct();
                    } else {
                        shakeAnimate(txtNum2);
                        stepsIdList.remove(1);
                    }
                } else if (stepsIdList.get(1) == txtNum2.getId()){
                    if (stepsIdList.get(0) == txtDenom1.getId()) {
                        txtNum2.setTextColor(Color.rgb(0, 255, 0));
                        getProduct();
                    } else {
                        shakeAnimate(txtNum1);
                        stepsIdList.remove(1);
                    }
                } else {
                    shakeAnimate(txtNum1);
                    shakeAnimate(txtNum2);
                    stepsIdList.remove(1);
                }
            }
        }
    }
    public void shakeAnimate(TextView textview){
        ObjectAnimator.ofFloat(textview, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                .setDuration(1000)
                .start();
    }
    public void getProduct(){
        int width = (int) (getResources().getDisplayMetrics().widthPixels);
        int height = (int) (getResources().getDisplayMetrics().heightPixels*0.3);

        multiplicationDialog.getWindow().setLayout(width,height);
        multiplicationDialog.show();
    }
}
