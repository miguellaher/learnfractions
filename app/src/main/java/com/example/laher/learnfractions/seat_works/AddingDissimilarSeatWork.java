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
import com.example.laher.learnfractions.fraction_util.fraction_questions.AddingDissimilarFractionsQuestion;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;
import java.util.Collections;

public class AddingDissimilarSeatWork extends SeatWork {
    Context mContext = this;
    //private static final String TAG = "AD_SW1";

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
    ImageView imgLine1;
    ImageView imgLine2;
    ImageView imgLine3;

    ArrayList<AddingDissimilarFractionsQuestion> fractionQuestions;
    AddingDissimilarFractionsQuestion fractionQuestion;
    int questionNum;
    String correctAns;

    public AddingDissimilarSeatWork() {
        setTopicName(AppConstants.ADDING_DISSIMILAR);
        setSeatWorkNum(1);
    }

    public AddingDissimilarSeatWork(String topicName) {
        super(topicName);
        String id = AppIDs.ADS;
        setId(id);
        Range range = getRange();
        Probability probability = new Probability(Probability.TWO_DISSIMILAR_FRACTIONS, range);
        setProbability(probability);
        setRangeEditable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fraction_equation);
        super.onCreate(savedInstanceState);
        String id = AppIDs.ADS;
        setId(id);
        //FRACTION EQUATION GUI
        txtNum1 = findViewById(R.id.fe_txtNum1);
        txtNum2 = findViewById(R.id.fe_txtNum2);
        txtDenom1 = findViewById(R.id.fe_txtDenom1);
        txtDenom2 = findViewById(R.id.fe_txtDenom2);
        txtSign = findViewById(R.id.fe_txtSign);
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

        Styles.bgPaintMainBlue(btnChoice1);
        Styles.bgPaintMainYellow(btnChoice2);
        Styles.bgPaintMainOrange(btnChoice3);
        Styles.bgPaintMainBlueGreen(btnChoice4);

        imgLine1 = findViewById(R.id.fe_imgLine1);
        imgLine2 = findViewById(R.id.fe_imgLine2);
        imgLine3 = findViewById(R.id.fe_imgLine3);
        imgLine1.setImageResource(R.drawable.line);
        imgLine2.setImageResource(R.drawable.line);
        imgLine3.setImageResource(R.drawable.line);

        disableInputFraction();
        go();
    }
    @Override
    protected void go(){
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
        fractionQuestions = new ArrayList<>();
        Range range = getRange();
        for(int i = 0; i < getItems_size(); i++){
            fractionQuestion = new AddingDissimilarFractionsQuestion(range);
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
        String instruction = "Solve the equation.";
        AddingDissimilarFractionsQuestion fractionsQuestion = fractionQuestions.get(questionNum);
        Fraction fraction1 = fractionsQuestion.getFraction1();
        Fraction fraction2 = fractionsQuestion.getFraction2();
        int numerator1 = fraction1.getNumerator();
        int denominator1 = fraction1.getDenominator();
        int numerator2 = fraction2.getNumerator();
        int denominator2 = fraction2.getDenominator();
        String strNumerator1 = String.valueOf(numerator1);
        String strDenominator1 = String.valueOf(denominator1);
        String strNumerator2 = String.valueOf(numerator2);
        String strDenominator2 = String.valueOf(denominator2);
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
                seatworkFinished();
            } else {
                nextQuestion();
            }
        }
    }
}
