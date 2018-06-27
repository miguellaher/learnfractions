package com.example.laher.learnfractions.lessons.ordering_dissimilar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.fraction_util.Question;
import com.example.laher.learnfractions.R;

import java.util.ArrayList;

public class OrderingDissimilarExerciseActivity extends AppCompatActivity {
    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Ordering Fractions";
    //LCM DIALOG
    Dialog lcmDialog;
    View lcmView;
    TextView diagLcmtxtNum1, diagLcmtxtNum2, diagLcmtxtNum3;
    EditText diagLcmInputLcm;
    Button diagLcmBtnCheck;
    //GUI
    TextView txtNum1,txtNum2, txtNum3, txtScore, txtInstruction;
    //VARIABLES
    Question question;
    ArrayList<Question> questions;
    int questionNum;
    int consecutiveRights, consecutiveWrongs;
    int requiredConsecutiveCorrects = 10;
    int maxConsecutiveWrongs = 3;
    int clicks;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_dissimilar_exercise);
        //TOOLBAR
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderingDissimilarExerciseActivity.this,
                        OrderingDissimilarVideoActivity.class);
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
                Intent intent = new Intent(OrderingDissimilarExerciseActivity.this,
                        OrderingDissimilarExercise2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        //LCM DIALOG
        lcmView = getLayoutInflater().inflate(R.layout.layout_dialog_lcm, null);
        lcmDialog = new Dialog(OrderingDissimilarExerciseActivity.this);
        lcmDialog.setOnDismissListener(new LcmDialogListener());
        lcmDialog.setTitle("Getting the LCM");
        lcmDialog.setContentView(lcmView);
        diagLcmtxtNum1 = (TextView) lcmView.findViewById(R.id.lcm_txtNum1);
        diagLcmtxtNum2 = (TextView) lcmView.findViewById(R.id.lcm_txtNum2);
        diagLcmtxtNum3 = (TextView) lcmView.findViewById(R.id.lcm_txtNum3);
        diagLcmInputLcm = (EditText) lcmView.findViewById(R.id.lcm_inputLcm);
        diagLcmInputLcm.setOnKeyListener(new DiagLcmTxtInputLcmListener());
        diagLcmBtnCheck = (Button) lcmView.findViewById(R.id.lcm_btnCheck);
        diagLcmBtnCheck.setOnClickListener(new DiagLcmBtnCheckListener());
        //GUI
        txtNum1 = (TextView) findViewById(R.id.od1_txtNum1);
        txtNum2 = (TextView) findViewById(R.id.od1_txtNum2);
        txtNum3 = (TextView) findViewById(R.id.od1_txtNum3);
        txtScore = (TextView) findViewById(R.id.od1_txtScore);
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        txtInstruction = (TextView) findViewById(R.id.od1_txtInstruction);

        go();
    }
    public void go(){
        setup();
        setQuestions();
        setGuiNums();
    }
    public void setup(){
        clicks = 0;
        resetTxtColors();
        setTxtListeners();
    }
    public void setQuestions(){
        questionNum = 0;
        questions = new ArrayList<>();
        for(int i = 0; i < requiredConsecutiveCorrects; i++){
            question = new Question(Question.GETTING_LCD);
            while (Integer.valueOf(question.getAnswer())>100){
                question = new Question(Question.GETTING_LCD);
            }
            questions.add(question);
        }
    }
    public void setGuiNums(){
        txtNum1.setText(String.valueOf(questions.get(questionNum).getNum1()));
        txtNum2.setText(String.valueOf(questions.get(questionNum).getNum2()));
        txtNum3.setText(String.valueOf(questions.get(questionNum).getNum3()));
        txtInstruction.setText("click all numbers");
    }
    public void setTxtListeners(){
        txtNum1.setOnClickListener(new TxtNumListener());
        txtNum2.setOnClickListener(new TxtNumListener());
        txtNum3.setOnClickListener(new TxtNumListener());
    }
    public void resetTxtColors(){
        txtNum1.setTextColor(Color.rgb(128,128,128));
        txtNum2.setTextColor(Color.rgb(128,128,128));
        txtNum3.setTextColor(Color.rgb(128,128,128));
    }
    public void correct(){
        consecutiveRights++;
        consecutiveWrongs = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        removeTxtNumListener();
        if (consecutiveRights>=requiredConsecutiveCorrects){
            txtInstruction.setText("finish");
            btnNext.setEnabled(true);
        } else {
            txtInstruction.setText("correct");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setup();
                    questionNum++;
                    setGuiNums();
                }
            }, 2000);
        }
    }
    public void wrong(){
        consecutiveWrongs++;
        consecutiveRights = 0;
        txtScore.setText(consecutiveRights + " / " + requiredConsecutiveCorrects);
        removeTxtNumListener();
        if (consecutiveWrongs>=maxConsecutiveWrongs){
            txtInstruction.setText("you had " + consecutiveWrongs + " consecutive wrongs. Preparing to go back to video.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(OrderingDissimilarExerciseActivity.this,
                            OrderingDissimilarVideoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 2000);
        } else {
            txtInstruction.setText("wrong");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    go();
                }
            }, 2000);
        }
    }
    public void diagInputLcm(){
        diagLcmtxtNum1.setText(String.valueOf(questions.get(questionNum).getNum1()));
        diagLcmtxtNum2.setText(String.valueOf(questions.get(questionNum).getNum2()));
        diagLcmtxtNum3.setText(String.valueOf(questions.get(questionNum).getNum3()));
        lcmDialog.show();
        txtInstruction.setText("Get the lcm");
    }
    public void removeTxtNumListener(){
        txtNum1.setOnClickListener(null);
        txtNum2.setOnClickListener(null);
        txtNum3.setOnClickListener(null);
    }
    public class TxtNumListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            if (t.getCurrentTextColor() == Color.rgb(0,255,0)){

            } else {
                t.setTextColor(Color.rgb(0,255,0));
                clicks++;
            }
            if (clicks>=3){
                diagInputLcm();
            }
        }
    }
    public class DiagLcmBtnCheckListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (String.valueOf(diagLcmInputLcm.getText()).matches(questions.get(questionNum).getAnswer())){
                correct();
                lcmDialog.dismiss();
            } else {
                diagLcmInputLcm.setTextColor(Color.rgb(255,0,0));
            }
        }
    }
    public class DiagLcmTxtInputLcmListener implements EditText.OnKeyListener{
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            diagLcmInputLcm.setTextColor(Color.rgb(0,0,0));
            return false;
        }
    }
    public class LcmDialogListener implements Dialog.OnDismissListener{
        @Override
        public void onDismiss(DialogInterface dialog) {
            diagLcmInputLcm.setText("");
            diagLcmInputLcm.setTextColor(Color.rgb(0,0,0));
            clicks = 0;
            resetTxtColors();
            txtInstruction.setText("click all numbers");
        }
    }
}
