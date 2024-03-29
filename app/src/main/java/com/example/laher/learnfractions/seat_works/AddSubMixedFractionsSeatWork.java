package com.example.laher.learnfractions.seat_works;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;
import com.example.laher.learnfractions.fraction_util.MixedFraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.AddingMixedFractionsQuestion;
import com.example.laher.learnfractions.fraction_util.fraction_questions.SubtractingMixedFractionsQuestion;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import java.util.ArrayList;
import java.util.Collections;

public class AddSubMixedFractionsSeatWork extends SeatWork {
    Context mContext = this;
    //FRACTION EQUATION GUI
    TextView txtNum1;
    TextView txtNum2;
    TextView txtDenom1;
    TextView txtDenom2;
    TextView txtSign;
    TextView txtIndicator;
    TextView txtInstruction;
    TextView txtWholeNum1;
    TextView txtWholeNum2;
    EditText inputNum;
    EditText inputDenom;
    Button btnChoice1;
    Button btnChoice2;
    Button btnChoice3;
    Button btnChoice4;
    ImageView imgLine1;
    ImageView imgLine2;
    ImageView imgLine3;

    ArrayList<FractionQuestionClass> questions;
    int questionNum;
    String correctAns;
    long startingTime;

    public AddSubMixedFractionsSeatWork(String topicName) {
        super(topicName);
        String id = AppIDs.MF1S;
        setId(id);
        Range range = getRange();
        Probability probability = new Probability(Probability.SOLVING_MIXED1, range);
        setProbability(probability);
        setRangeEditable(true);
    }

    public AddSubMixedFractionsSeatWork() {
        setTopicName(AppConstants.ADDING_SUBTRACTING_MIXED);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mixed_fraction_equation);
        super.onCreate(savedInstanceState);
        String id = AppIDs.MF1S;
        setId(id);
        //FRACTION EQUATION GUI
        txtNum1 = findViewById(R.id.mfe_txtNum1);
        txtNum2 = findViewById(R.id.mfe_txtNum2);
        txtDenom1 = findViewById(R.id.mfe_txtDenom1);
        txtDenom2 = findViewById(R.id.mfe_txtDenom2);
        txtWholeNum1 = findViewById(R.id.mfe_txtWholeNum1);
        txtWholeNum2 = findViewById(R.id.mfe_txtWholeNum2);
        txtSign = findViewById(R.id.mfe_txtSign1);
        txtIndicator = findViewById(R.id.fem_txtScore);
        txtInstruction = findViewById(R.id.fem_txtInstruction);
        inputNum = findViewById(R.id.mfe_inputAns1);
        inputDenom = findViewById(R.id.mfe_inputAns2);
        btnChoice1 = findViewById(R.id.fe_btnChoice1);
        btnChoice2 = findViewById(R.id.fe_btnChoice2);
        btnChoice3 = findViewById(R.id.fe_btnChoice3);
        btnChoice4 = findViewById(R.id.fe_btnChoice4);

        Styles.bgPaintMainBlue(btnChoice1);
        Styles.bgPaintMainYellow(btnChoice2);
        Styles.bgPaintMainOrange(btnChoice3);
        Styles.bgPaintMainBlueGreen(btnChoice4);

        btnChoice1.setOnClickListener(new BtnChoiceListener());
        btnChoice2.setOnClickListener(new BtnChoiceListener());
        btnChoice3.setOnClickListener(new BtnChoiceListener());
        btnChoice4.setOnClickListener(new BtnChoiceListener());

        imgLine1 = findViewById(R.id.adsm_imgLine1);
        imgLine2 = findViewById(R.id.adsm_imgLine2);
        imgLine3 = findViewById(R.id.adsm_imgLine3);

        imgLine1.setImageResource(R.drawable.line);
        imgLine2.setImageResource(R.drawable.line);
        imgLine3.setImageResource(R.drawable.line);

        disableInputFraction();
        startingTime = System.currentTimeMillis();
        go();
    }
    @Override
    public void go(){
        super.go();
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
        questions = new ArrayList<>();
        Range range = getRange();
        int requiredCorrects = getItems_size();
        for (int i = 0; i < requiredCorrects; i++){
            if (i>=(requiredCorrects/2)) {
                AddingMixedFractionsQuestion question = new AddingMixedFractionsQuestion(range);
                while (questions.contains(question)) {
                    question = new AddingMixedFractionsQuestion(range);
                }
                questions.add(question);
            } else {
                SubtractingMixedFractionsQuestion question = new SubtractingMixedFractionsQuestion(range);
                while (questions.contains(question)) {
                    question = new SubtractingMixedFractionsQuestion(range);
                }
                questions.add(question);
            }
        }
        Collections.shuffle(questions);
    }
    public void setBtnChoices(){
        ArrayList<String> choices = new ArrayList<>();
        FractionQuestionClass question = questions.get(questionNum);
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
        FractionQuestionClass question = questions.get(questionNum);
        if (question instanceof AddingMixedFractionsQuestion){
            AddingMixedFractionsQuestion fractionsQuestion = (AddingMixedFractionsQuestion) question;
            String tag = fractionsQuestion.getTAG();
            txtSign.setText("+");
            if (tag.equals(AddingMixedFractionsQuestion.ONE_MIXED)){
                MixedFraction mixedFraction = fractionsQuestion.getMixedFraction1();
                Fraction fraction = fractionsQuestion.getFraction();
                int wholeNumber = mixedFraction.getWholeNumber();
                int numeratorMixed = mixedFraction.getNumerator();
                int denominatorMixed = mixedFraction.getDenominator();
                int numerator = fraction.getNumerator();
                int denominator = fraction.getDenominator();
                String strWholeNumber = String.valueOf(wholeNumber);
                String strNumeratorMixed = String.valueOf(numeratorMixed);
                String strDenominatorMixed = String.valueOf(denominatorMixed);
                String strNumerator = String.valueOf(numerator);
                String strDenominator = String.valueOf(denominator);
                if (Util.randomBoolean()){
                    txtWholeNum1.setText(strWholeNumber);
                    txtNum1.setText(strNumeratorMixed);
                    txtDenom1.setText(strDenominatorMixed);
                    txtNum2.setText(strNumerator);
                    txtDenom2.setText(strDenominator);
                    txtWholeNum2.setText("");
                } else {
                    txtWholeNum2.setText(strWholeNumber);
                    txtNum2.setText(strNumeratorMixed);
                    txtDenom2.setText(strDenominatorMixed);
                    txtNum1.setText(strNumerator);
                    txtDenom1.setText(strDenominator);
                    txtWholeNum1.setText("");
                }
            } else if (tag.equals(AddingMixedFractionsQuestion.TWO_MIXED)){
                MixedFraction mixedFraction1 = fractionsQuestion.getMixedFraction1();
                MixedFraction mixedFraction2 = fractionsQuestion.getMixedFraction2();
                int wholeNumber1 = mixedFraction1.getWholeNumber();
                int numeratorMixed1 = mixedFraction1.getNumerator();
                int denominatorMixed1 = mixedFraction1.getDenominator();
                int wholeNumber2 = mixedFraction2.getWholeNumber();
                int numeratorMixed2 = mixedFraction2.getNumerator();
                int denominatorMixed2 = mixedFraction2.getDenominator();
                String strWholeNumber1 = String.valueOf(wholeNumber1);
                String strNumeratorMixed1 = String.valueOf(numeratorMixed1);
                String strDenominatorMixed1 = String.valueOf(denominatorMixed1);
                String strWholeNumber2 = String.valueOf(wholeNumber2);
                String strNumeratorMixed2 = String.valueOf(numeratorMixed2);
                String strDenominatorMixed2 = String.valueOf(denominatorMixed2);
                txtWholeNum1.setText(strWholeNumber1);
                txtNum1.setText(strNumeratorMixed1);
                txtDenom1.setText(strDenominatorMixed1);
                txtWholeNum2.setText(strWholeNumber2);
                txtNum2.setText(strNumeratorMixed2);
                txtDenom2.setText(strDenominatorMixed2);
            }
        } else if (question instanceof SubtractingMixedFractionsQuestion){
            SubtractingMixedFractionsQuestion fractionsQuestion = (SubtractingMixedFractionsQuestion) question;
            String tag = fractionsQuestion.getTAG();
            txtSign.setText("-");
            if (tag.equals(SubtractingMixedFractionsQuestion.ONE_MIXED)){
                MixedFraction mixedFraction = fractionsQuestion.getMixedFraction1();
                Fraction fraction = fractionsQuestion.getFraction();
                int wholeNumber = mixedFraction.getWholeNumber();
                int numeratorMixed = mixedFraction.getNumerator();
                int denominatorMixed = mixedFraction.getDenominator();
                int numerator = fraction.getNumerator();
                int denominator = fraction.getDenominator();
                String strWholeNumber = String.valueOf(wholeNumber);
                String strNumeratorMixed = String.valueOf(numeratorMixed);
                String strDenominatorMixed = String.valueOf(denominatorMixed);
                String strNumerator = String.valueOf(numerator);
                String strDenominator = String.valueOf(denominator);
                txtWholeNum1.setText(strWholeNumber);
                txtNum1.setText(strNumeratorMixed);
                txtDenom1.setText(strDenominatorMixed);
                txtNum2.setText(strNumerator);
                txtDenom2.setText(strDenominator);
                txtWholeNum2.setText("");
            } else if (tag.equals(SubtractingMixedFractionsQuestion.TWO_MIXED)){
                MixedFraction mixedFraction1 = fractionsQuestion.getMixedFraction1();
                MixedFraction mixedFraction2 = fractionsQuestion.getMixedFraction2();
                int wholeNumber1 = mixedFraction1.getWholeNumber();
                int numeratorMixed1 = mixedFraction1.getNumerator();
                int denominatorMixed1 = mixedFraction1.getDenominator();
                int wholeNumber2 = mixedFraction2.getWholeNumber();
                int numeratorMixed2 = mixedFraction2.getNumerator();
                int denominatorMixed2 = mixedFraction2.getDenominator();
                String strWholeNumber1 = String.valueOf(wholeNumber1);
                String strNumeratorMixed1 = String.valueOf(numeratorMixed1);
                String strDenominatorMixed1 = String.valueOf(denominatorMixed1);
                String strWholeNumber2 = String.valueOf(wholeNumber2);
                String strNumeratorMixed2 = String.valueOf(numeratorMixed2);
                String strDenominatorMixed2 = String.valueOf(denominatorMixed2);
                txtWholeNum1.setText(strWholeNumber1);
                txtNum1.setText(strNumeratorMixed1);
                txtDenom1.setText(strDenominatorMixed1);
                txtWholeNum2.setText(strWholeNumber2);
                txtNum2.setText(strNumeratorMixed2);
                txtDenom2.setText(strDenominatorMixed2);
            }
        }
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
                seatworkFinished();
            } else {
                nextQuestion();
            }
        }
    }
}
