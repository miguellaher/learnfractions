package com.example.laher.learnfractions.seat_works;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.fraction_questions.FractionMeaningQuestion;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class FractionMeaningSeatWork extends SeatWork {
    Context mContext = this;
    //private static final String TAG = "FM_S";
    //GUI
    ImageView imgBox1;
    ImageView imgBox2;
    ImageView imgBox3;
    ImageView imgBox4;
    ImageView imgBox5;
    ImageView imgBox6;
    ImageView imgBox7;
    ImageView imgBox8;
    ImageView imgBox9;
    ImageView imgLine;
    EditText inputNum;
    EditText inputDenom;
    TextView txtItemIndicator;
    TextView txtInstruction;
    Button btnOK;
    ConstraintLayout clChoices;
    Button btnChoice1;
    Button btnChoice2;
    Button btnChoice3;
    Button btnChoice4;
    //VARIABLES
    final static String INPUT_NUM = "INPUT_NUM";
    final static String INPUT_DENOM = "INPUT_DENOM";
    String instruction;

    ArrayList<FractionMeaningQuestion> questions;
    int questionNum;

    public FractionMeaningSeatWork() {
        super();
        setTopicName(AppConstants.FRACTION_MEANING);
    }

    public FractionMeaningSeatWork(String topicName) {
        super(topicName);
        String id = AppIDs.FMS;
        setId(id);
        Range range = getRange();
        Probability probability = new Probability(Probability.SUMMATION_NOTATION_1, range);
        setProbability(probability);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fraction_meaning_exercise2);
        super.onCreate(savedInstanceState);
        String id = AppIDs.FMS;
        setId(id);
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
        inputNum = findViewById(R.id.fme2_inputNumerator);
        inputDenom = findViewById(R.id.fme2_inputDenominator);
        imgLine = findViewById(R.id.fme2_imgLine);
        imgLine.setImageResource(R.drawable.line);
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        inputNum.setHintTextColor(Color.rgb(0,0,0));
        inputDenom.setHintTextColor(Color.rgb(0,0,0));
        inputNum.setText("");
        inputDenom.setText("");
        btnOK = findViewById(R.id.a1_btnOk);
        btnOK.setVisibility(View.INVISIBLE);
        txtItemIndicator = findViewById(R.id.a1_txtScore);
        txtInstruction = findViewById(R.id.a1_txtInstruction);
        btnChoice1 = findViewById(R.id.fe_btnChoice1);
        btnChoice2 = findViewById(R.id.fe_btnChoice2);
        btnChoice3 = findViewById(R.id.fe_btnChoice3);
        btnChoice4 = findViewById(R.id.a1_btnChoice4);

        Styles.bgPaintMainBlue(btnChoice1);
        Styles.bgPaintMainYellow(btnChoice2);
        Styles.bgPaintMainOrange(btnChoice3);
        Styles.bgPaintMainBlueGreen(btnChoice4);

        btnChoice1.setOnClickListener(new BtnChoiceListener());
        btnChoice2.setOnClickListener(new BtnChoiceListener());
        btnChoice3.setOnClickListener(new BtnChoiceListener());
        btnChoice4.setOnClickListener(new BtnChoiceListener());


        go();
        instruction = INPUT_NUM;
    }

    @Override
    protected void go(){
        super.go();
        generateQuestions();
        setClChoices();
    }
    public void setClChoices(){
        updateItemIndicator(txtItemIndicator);
        ArrayList<Integer> choices = new ArrayList<>();
        FractionMeaningQuestion question = questions.get(questionNum-1);
        int numerator = question.getNumerator();
        int denominator = question.getDenominator();
        choices.add(numerator);
        if (numerator!=denominator) {
            choices.add(denominator);
        }
        while(choices.size()!=4 || choices.size()<4){
            Random random = new Random();
            int maximum = 9;
            int minimum = 1;
            int randomNumber = random.nextInt(maximum + 1 - minimum) + minimum;
            while (randomNumber==numerator || randomNumber==denominator || choices.contains(randomNumber)) {
                randomNumber = random.nextInt(maximum + 1 - minimum) + minimum;
            }
            choices.add(randomNumber);
        }
        Collections.shuffle(choices);
        btnChoice1.setText(String.valueOf(choices.get(0)));
        btnChoice2.setText(String.valueOf(choices.get(1)));
        btnChoice3.setText(String.valueOf(choices.get(2)));
        btnChoice4.setText(String.valueOf(choices.get(3)));
        if (inputNum.getText().toString().trim().equals("")){
            Styles.bgPaintBurlyWood(inputNum);
        } else {
            Styles.bgPaintBurlyWood(inputDenom);
        }
        setBoxes();
    }
    public void generateQuestions(){
        questions = new ArrayList<>();
        questionNum = 1;
        int itemSize = getItems_size();
        for (int i = 0; i < itemSize; i++){
            FractionMeaningQuestion question = new FractionMeaningQuestion();
            while (questions.contains(question)){
                question = new FractionMeaningQuestion();
            }
            questions.add(question);
        }
    }
    public void setBoxes(){
        FractionMeaningQuestion question = questions.get(questionNum-1);
        int numerator = question.getNumerator();
        int denominator = question.getDenominator();
        denominator = denominator - numerator;
        ArrayList<Integer> imageList = new ArrayList<>();
        for (int i = 1; i <= numerator; i++){
            imageList.add(R.drawable.chocolate);
        }
        for (int i = 1; i <= denominator; i++){
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

    public class BtnChoiceListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
                if (instruction.matches(INPUT_NUM)) {
                    int ans = Integer.valueOf(String.valueOf(b.getText()));
                    inputNum.setText(String.valueOf(ans));
                    instruction = INPUT_DENOM;
                    Styles.bgPaintWhite(inputNum);
                    setClChoices();
                } else if (instruction.matches(INPUT_DENOM)) {
                    int ans = Integer.valueOf(String.valueOf(b.getText()));
                    inputDenom.setText(String.valueOf(ans));
                    Styles.bgPaintWhite(inputDenom);
                    FractionMeaningQuestion question = questions.get(questionNum-1);
                    int numerator = question.getNumerator();
                    int denominator = question.getDenominator();
                    if (inputNum.getText().toString().trim().matches(String.valueOf(numerator)) &&
                            inputDenom.getText().toString().trim().matches(String.valueOf(denominator))) {
                        incrementCorrect();
                    }
                    incrementItemNum();
                    inputNum.setText("");
                    inputDenom.setText("");
                    instruction = INPUT_NUM;
                    if (getCurrentItemNum() <= getItems_size()) {
                        questionNum++;
                        setClChoices();
                    } else {
                        btnChoice1.setEnabled(false);
                        btnChoice2.setEnabled(false);
                        btnChoice3.setEnabled(false);
                        btnChoice4.setEnabled(false);
                        seatworkFinished();
                    }
                }
        }
    }
}
