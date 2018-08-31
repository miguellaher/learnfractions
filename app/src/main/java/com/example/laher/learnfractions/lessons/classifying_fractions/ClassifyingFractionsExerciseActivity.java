package com.example.laher.learnfractions.lessons.classifying_fractions;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;
import com.example.laher.learnfractions.fraction_util.MixedFraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.ClassifyingFractionQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;

import java.util.ArrayList;
import java.util.Collections;

public class ClassifyingFractionsExerciseActivity extends LessonExercise {
    //private static final String TAG = "CF_E1";
    //GUI
    TextView txtNum;
    TextView txtDenom;
    TextView txtWholeNum;
    TextView txtScore;
    TextView txtInstruction;
    Button btnProper;
    Button btnImproper;
    Button btnMixed;
    ImageView imgLine;
    //VARIABLES
    ArrayList<ClassifyingFractionQuestion> mClassifyingFractionQuestions;
    ClassifyingFractionQuestion mClassifyingFractionQuestion;
    int mQuestionNum;

    public String title = "Classifying Fractions ex.1";
    String id = AppIDs.CLASSIFYING_EXERCISE_ID;

    public ClassifyingFractionsExerciseActivity() {
        super();
        Range range = getRange();
        Probability probability = new Probability(Probability.CLASSIFYING_FRACTIONS, range);
        setProbability(probability);
        setId(id);
        setExerciseTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_classifying_fractions_exercise);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);
        Range range = getRange();
        Probability probability = new Probability(Probability.CLASSIFYING_FRACTIONS, range);
        setProbability(probability);
        //GUI
        txtNum = findViewById(R.id.clF_txtNum);
        txtDenom = findViewById(R.id.clF_txtDenom);
        txtWholeNum = findViewById(R.id.clF_txtWholeNum);
        txtScore = findViewById(R.id.clF_txtScore);
        txtInstruction = findViewById(R.id.clF_txtInstruction);
        btnProper = findViewById(R.id.clF_btnProper);
        btnImproper = findViewById(R.id.clF_btnImproper);
        btnMixed = findViewById(R.id.clF_btnMixed);
        btnProper.setOnClickListener(new BtnAnswerListener());
        btnImproper.setOnClickListener(new BtnAnswerListener());
        btnMixed.setOnClickListener(new BtnAnswerListener());
        imgLine = findViewById(R.id.cfe_imgLine);
        imgLine.setImageResource(R.drawable.line);

        startExercise();
    }
    public void nextQuestion(){
        enableButtons(true);
        mQuestionNum++;
        setGuiFraction();
    }
    public void enableButtons(Boolean bool){
        btnProper.setEnabled(bool);
        btnImproper.setEnabled(bool);
        btnMixed.setEnabled(bool);
    }
    public void setFractionQuestions(){
        mQuestionNum = 1;
        mClassifyingFractionQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        for (int i = 0; i < requiredCorrects; i++){
            ClassifyingFractionQuestion classifyingFractionQuestion;
            if (i >= (requiredCorrects*2)/3){
                classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.PROPER);
            } else if (i >= (requiredCorrects/3)){
                classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.IMPROPER);
            } else {
                classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.MIXED);
            }
            while (mClassifyingFractionQuestions.contains(classifyingFractionQuestion)){
                if (i >= (requiredCorrects*2)/3){
                    classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.PROPER);
                } else if (i >= (requiredCorrects/3)){
                    classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.IMPROPER);
                } else {
                    classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.MIXED);
                }
            }
            mClassifyingFractionQuestions.add(classifyingFractionQuestion);
            Collections.shuffle(mClassifyingFractionQuestions);
        }
        setGuiFraction();
    }
    public void setGuiFraction(){
        String instruction = "Classify the fraction.";
        txtInstruction.setText(instruction);
        mClassifyingFractionQuestion = mClassifyingFractionQuestions.get(mQuestionNum-1);
        Fraction fraction = mClassifyingFractionQuestion.getFraction();
        int numerator = fraction.getNumerator();
        int denominator = fraction.getDenominator();
        String modifier = fraction.getModifier();
        if (modifier.equals(Fraction.MIXED)){
            MixedFraction mixedFraction = (MixedFraction) fraction;
            int wholeNumber = mixedFraction.getWholeNumber();
            String strWholeNumber = String.valueOf(wholeNumber);
            txtWholeNum.setText(strWholeNumber);
        } else {
            txtWholeNum.setText("");
        }
        txtNum.setText(String.valueOf(numerator));
        txtDenom.setText(String.valueOf(denominator));
    }
    public void check(String modifier){
        mClassifyingFractionQuestion = mClassifyingFractionQuestions.get(mQuestionNum-1);
        Fraction fraction = mClassifyingFractionQuestion.getFraction();
        String correctModifier = fraction.getModifier();
        if (modifier.equals(correctModifier)){
            correct();
        } else {
            wrong();
        }
    }
    public class BtnAnswerListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId()==btnProper.getId()){
                check(Fraction.PROPER);
            } else if (v.getId()==btnImproper.getId()){
                check(Fraction.IMPROPER);
            } else if (v.getId()==btnMixed.getId()){
                check(Fraction.MIXED);
            }
        }
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
        setFractionQuestions();
        setGuiFraction();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        enableButtons(false);
    }

    @Override
    protected void postAnswered() {
        super.postAnswered();
    }

    @Override
    protected void preCorrect() {
        super.preCorrect();
        txtInstruction.setText(AppConstants.CORRECT);
    }

    @Override
    protected void postCorrect() {
        super.postCorrect();
        nextQuestion();
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
        if (correctsShouldBeConsecutive) {
            setFractionQuestions();
            setGuiFraction();
        } else {
            mClassifyingFractionQuestion = mClassifyingFractionQuestions.get(mQuestionNum-1);
            ClassifyingFractionQuestion classifyingFractionQuestion = new ClassifyingFractionQuestion();
            Fraction fraction = mClassifyingFractionQuestion.getFraction();
            String modifier = fraction.getModifier();
            int improperFractionsItems = 0;
            int properFractionsItems = 0;
            int mixedFractionsItems = 0;
            for (ClassifyingFractionQuestion fractionQuestion : mClassifyingFractionQuestions){
                Fraction fraction1 = fractionQuestion.getFraction();
                String modifier1 = fraction1.getModifier();
                if (modifier1.equals(Fraction.IMPROPER)){
                    improperFractionsItems++;
                } else if (modifier1.equals(Fraction.PROPER)){
                    properFractionsItems++;
                } else if (modifier1.equals(Fraction.MIXED)){
                    mixedFractionsItems++;
                }
            }
            int maxItemSize = getMaxItemSize()/3;
            if (modifier.equals(Fraction.MIXED)){
                classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.MIXED);
                while (mClassifyingFractionQuestions.contains(classifyingFractionQuestion) && mixedFractionsItems<maxItemSize){
                    classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.MIXED);
                }
            } else {
                if (modifier.equals(Fraction.PROPER)){
                    classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.PROPER);
                    while (mClassifyingFractionQuestions.contains(classifyingFractionQuestion) && properFractionsItems<maxItemSize){
                        classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.PROPER);
                    }
                } else if (modifier.equals(Fraction.IMPROPER)){
                    classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.IMPROPER);
                    while (mClassifyingFractionQuestions.contains(classifyingFractionQuestion) && improperFractionsItems<maxItemSize){
                        classifyingFractionQuestion = new ClassifyingFractionQuestion(Fraction.IMPROPER);
                    }
                }
            }
            mClassifyingFractionQuestions.add(classifyingFractionQuestion);
            nextQuestion();
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
