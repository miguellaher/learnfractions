package com.example.laher.learnfractions.lessons.comparing_similar_fractions;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.ComparingSimilarQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class ComparingSimilarExercise2Activity extends LessonExercise {
    private static final String TAG = "CS_E2";
    //GUI
    TextView txtNum1;
    TextView txtNum2;
    TextView txtDenominator1;
    TextView txtDenominator2;
    TextView txtCompareSign;
    TextView txtScore;
    TextView txtInstruction;
    Button btnGreater;
    Button btnEquals;
    Button btnLess;
    ImageView imgLine1;
    ImageView imgLine2;
    GifImageView gifAvatar;
    ConstraintLayout constraintLayoutBackground;
    ConstraintLayout constraintLayoutBottom;
    //VARIABLES
    ComparingSimilarQuestion mComparingSimilarQuestion;
    ArrayList<ComparingSimilarQuestion> mComparingSimilarQuestions;
    int mQuestionNum;

    public String title = "Comparing Similar ex.2";
    String id = AppIDs.CSE2_ID;

    public ComparingSimilarExercise2Activity() {
        super();
        Range range = getRange();
        Probability probability = new Probability(Probability.P_RAISED_TO_3, range);
        setProbability(probability);
        setRangeEditable(true);
        setId(id);
        setExerciseTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comparing_similar_exercise2);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);
        Range range = getRange();
        Probability probability = new Probability(Probability.P_RAISED_TO_3, range);
        setProbability(probability);
        setRangeEditable(true);
        //TOOLBAR
        Styles.bgPaintMainYellow(buttonBack); // SPECIAL CASE - colors   are similar
        //GUI
        txtNum1 = findViewById(R.id.c2_num1);
        txtNum2 = findViewById(R.id.c2_num2);
        txtDenominator1 = findViewById(R.id.c2_denom1);
        txtDenominator2 = findViewById(R.id.c2_denom2);
        txtCompareSign = findViewById(R.id.c2_compareSign);
        txtScore = findViewById(R.id.c2_txtScore);
        txtInstruction = findViewById(R.id.c2_txtInstruction);
        imgLine1 = findViewById(R.id.cse2_imgLine1);
        imgLine2 = findViewById(R.id.cse2_imgLine2);
        imgLine1.setImageResource(R.drawable.line);
        imgLine2.setImageResource(R.drawable.line);
        gifAvatar = findViewById(R.id.gifAvatar);
        int gifID = R.drawable.summer_frits;
        gifAvatar.setImageResource(gifID);

        constraintLayoutBackground = findViewById(R.id.constraintLayoutBackground);
        constraintLayoutBackground.setBackgroundResource(R.drawable.beach_background);

        constraintLayoutBottom = findViewById(R.id.constraintLayoutBottom);
        constraintLayoutBottom.setBackgroundResource(R.drawable.beach_bottom);

        int resourceID = R.drawable.beach_toolbar;
        setToolBarBackground(resourceID);

        btnGreater = findViewById(R.id.c2_btnGreater);
        btnEquals = findViewById(R.id.c2_btnEqual);
        btnLess = findViewById(R.id.c2_btnLess);
        btnGreater.setText(AppConstants.GREATER_THAN);
        btnEquals.setText(AppConstants.EQUAL_TO);
        btnLess.setText(AppConstants.LESS_THAN);
        btnGreater.setOnClickListener(new BtnListener());
        btnEquals.setOnClickListener(new BtnListener());
        btnLess.setOnClickListener(new BtnListener());
        Styles.bgPaintMainBlue(btnGreater);
        Styles.bgPaintMainYellow(btnEquals);
        Styles.bgPaintMainOrange(btnLess);

        startExercise();
    }
    private void generateFractionQuestion(){
        mQuestionNum = 1;
        mComparingSimilarQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        Range range = getRange();
        for (int i = 0; i < requiredCorrects; i++){
            ComparingSimilarQuestion comparingSimilarQuestion = new ComparingSimilarQuestion(range);
            while (mComparingSimilarQuestions.contains(comparingSimilarQuestion)){
                comparingSimilarQuestion = new ComparingSimilarQuestion(range);
            }
            mComparingSimilarQuestions.add(comparingSimilarQuestion);
        }
        setTxtFractions();
    }
    public void setTxtFractions(){
        mComparingSimilarQuestion = mComparingSimilarQuestions.get(mQuestionNum-1);
        Fraction fraction1 = mComparingSimilarQuestion.getFraction1();
        Fraction fraction2 = mComparingSimilarQuestion.getFraction2();
        int numerator1 = fraction1.getNumerator();
        int numerator2 = fraction2.getNumerator();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        txtNum1.setText(String.valueOf(numerator1));
        txtNum2.setText(String.valueOf(numerator2));
        txtDenominator1.setText(String.valueOf(denominator1));
        txtDenominator2.setText(String.valueOf(denominator2));
        String instruction = "Compare the two fractions.";
        txtInstruction.setText(instruction);
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
            if (isCorrect(s)){
                correct();
            } else {
                wrong();
            }
        }
    }

    private boolean isCorrect(String answer){
        mComparingSimilarQuestion = mComparingSimilarQuestions.get(mQuestionNum-1);
        Fraction fraction1 = mComparingSimilarQuestion.getFraction1();
        Fraction fraction2 = mComparingSimilarQuestion.getFraction2();
        Fraction fractionAnswer = mComparingSimilarQuestion.getFractionAnswer();
        Log.d(TAG,"---------------");
        Log.d(TAG,"answer: " + answer);
        Log.d(TAG,"fraction 1: " + fraction1.toString());
        Log.d(TAG,"fraction 2: " + fraction2.toString());
        Log.d(TAG,"fraction answer: " + fractionAnswer.toString());
        Log.d(TAG,"compare(): " + fraction1.compare(fraction2));
        switch (answer) {
            case AppConstants.GREATER_THAN:
                if (fractionAnswer.equals(fraction1)) {
                    return true;
                }
                break;
            case AppConstants.LESS_THAN:
                if (fractionAnswer.equals(fraction2)) {
                    return true;
                }
                break;
            case AppConstants.EQUAL_TO:
                if (fractionAnswer.equals(AppConstants.EQUAL_FRACTIONS)) {
                    return true;
                }
                break;
        }
        return false;
    }
    @Override
    public void showScore(){
        super.showScore();
        int correct = getCorrect();
        int requiredCorrects = getItemsSize();
        txtScore.setText(AppConstants.SCORE(correct,requiredCorrects));
    }

    @Override
    protected void startExercise() {
        super.startExercise();
        generateFractionQuestion();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        enableButtons(false);
    }

    @Override
    protected void postAnswered() {
        super.postAnswered();
        enableButtons(true);
    }

    @Override
    protected void preCorrect() {
        super.preCorrect();
        txtInstruction.setText(AppConstants.CORRECT);
    }

    @Override
    protected void postCorrect() {
        super.postCorrect();
        mQuestionNum++;
        setTxtFractions();
    }

    @Override
    protected void preFinished() {
        super.preFinished();
        txtInstruction.setText(AppConstants.FINISHED_EXERCISE);
    }

    @Override
    protected void preWrong() {
        super.preWrong();
        txtInstruction.setText(AppConstants.ERROR);
    }

    @Override
    protected void postWrong() {
        super.postWrong();
        boolean correctsShouldBeConsecutive = isCorrectsShouldBeConsecutive();
        Range range = getRange();
        if (correctsShouldBeConsecutive) {
            generateFractionQuestion();
        } else {
            ComparingSimilarQuestion comparingSimilarQuestion = new ComparingSimilarQuestion(range);
            int questionsSize = mComparingSimilarQuestions.size();
            int maxItemSize = getMaxItemSize();
            while (mComparingSimilarQuestions.contains(comparingSimilarQuestion) && questionsSize<maxItemSize){
                comparingSimilarQuestion = new ComparingSimilarQuestion(range);
            }
            mComparingSimilarQuestions.add(comparingSimilarQuestion);
            mQuestionNum++;
            setTxtFractions();
        }
    }

    @Override
    protected void preFailWrongsAreConsecutive() {
        super.preFailWrongsAreConsecutive();
        txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(getWrong()));
    }

    @Override
    protected void preFailWrongsAreNotConsecutive() {
        super.preFailWrongsAreNotConsecutive();
        txtInstruction.setText(AppConstants.FAILED(getWrong()));
    }
}
