package com.example.laher.learnfractions.lessons.comparing_fractions;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.ComparingFractionsQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;

import java.util.ArrayList;
import java.util.Collections;

public class ComparingFractionsExerciseActivity extends LessonExercise {
    //private static final String TAG = "CF_E1";
    //GUI
    TextView txtNum1;
    TextView txtNum2;
    TextView txtDenominator1;
    TextView txtDenominator2;
    TextView txtScore;
    TextView txtInstruction;
    Button btnSimilar;
    Button btnDissimilar;
    ImageView imgLine1;
    ImageView imgLine2;
    ImageView imgAvatar;
    //VARIABLES
    ArrayList<ComparingFractionsQuestion> mComparingFractionsQuestions;
    ComparingFractionsQuestion mComparingFractionsQuestion;
    int mQuestionNum;

    public String title = "Comparing Fractions ex.1";
    String id = AppIDs.CFE_ID;

    public ComparingFractionsExerciseActivity() {
        super();
        setId(id);
        setExerciseTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comparing_fractions_exercise);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);
        //GUI
        txtNum1 = findViewById(R.id.e1_txtNum1);
        txtNum2 = findViewById(R.id.e1_txtNum2);
        txtDenominator1 = findViewById(R.id.e1_txtDenom1);
        txtDenominator2 = findViewById(R.id.e1_txtDenom2);
        txtScore = findViewById(R.id.e1_txtScore);
        txtInstruction = findViewById(R.id.e1_txtInstruction);
        btnSimilar = findViewById(R.id.e1_btnSimilar);
        btnDissimilar = findViewById(R.id.e1_btnDissimilar);
        btnSimilar.setOnClickListener(new BtnChoiceListener());
        btnDissimilar.setOnClickListener(new BtnChoiceListener());
        imgLine1 = findViewById(R.id.imgLine1);
        imgLine2 = findViewById(R.id.imgLine2);
        imgAvatar = findViewById(R.id.e1_imgAvatar);
        imgLine1.setImageResource(R.drawable.line);
        imgLine2.setImageResource(R.drawable.line);
        imgAvatar.setImageResource(R.drawable.avatar);

        startExercise();
    }
    private void setFractionQuestions(){
        mQuestionNum = 1;
        mComparingFractionsQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        for (int i = 0; i < requiredCorrects; i++){
            ComparingFractionsQuestion comparingFractionsQuestion;
            if (i<(requiredCorrects/2)){
                comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.SIMILAR);
            } else {
                comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.DISSIMILAR);
            }
            while (mComparingFractionsQuestions.contains(comparingFractionsQuestion)){
                if (i<(requiredCorrects/2)){
                    comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.SIMILAR);
                } else {
                    comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.DISSIMILAR);
                }
            }
            mComparingFractionsQuestions.add(comparingFractionsQuestion);
        }
        Collections.shuffle(mComparingFractionsQuestions);
        setTxtFraction();
    }
    public void setTxtFraction() {
        mComparingFractionsQuestion = mComparingFractionsQuestions.get(mQuestionNum-1);
        Fraction fraction1 = mComparingFractionsQuestion.getFraction1();
        Fraction fraction2 = mComparingFractionsQuestion.getFraction2();
        int numerator1 = fraction1.getNumerator();
        int numerator2 = fraction2.getNumerator();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        txtNum1.setText(String.valueOf(numerator1));
        txtDenominator1.setText(String.valueOf(denominator1));
        txtNum2.setText(String.valueOf(numerator2));
        txtDenominator2.setText(String.valueOf(denominator2));
        String instruction = "Determine whether the pair is dissimilar or similar.";
        txtInstruction.setText(instruction);
        btnSimilar.setEnabled(true);
        btnDissimilar.setEnabled(true);
    }
    public class BtnChoiceListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.e1_btnSimilar){
                if (mComparingFractionsQuestion.getModifier().equals(ComparingFractionsQuestion.SIMILAR)){
                    correct();
                } else {
                    wrong();
                }
            }
            if (v.getId() == R.id.e1_btnDissimilar){
                if (mComparingFractionsQuestion.getModifier().equals(ComparingFractionsQuestion.DISSIMILAR)){
                    correct();
                } else {
                    wrong();
                }
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
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        btnSimilar.setEnabled(false);
        btnDissimilar.setEnabled(false);
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
        mQuestionNum++;
        setTxtFraction();
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
        } else {
            mComparingFractionsQuestion = mComparingFractionsQuestions.get(mQuestionNum-1);
            ComparingFractionsQuestion comparingFractionsQuestion = null;
            if (mComparingFractionsQuestion.getModifier().equals(ComparingFractionsQuestion.SIMILAR)){
                comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.SIMILAR);
            } else if (mComparingFractionsQuestion.getModifier().equals(ComparingFractionsQuestion.DISSIMILAR)){
                comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.SIMILAR);
            }
            while (mComparingFractionsQuestions.contains(comparingFractionsQuestion)){
                if (mComparingFractionsQuestion.getModifier().equals(ComparingFractionsQuestion.SIMILAR)){
                    comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.SIMILAR);
                } else if (mComparingFractionsQuestion.getModifier().equals(ComparingFractionsQuestion.DISSIMILAR)){
                    comparingFractionsQuestion = new ComparingFractionsQuestion(ComparingFractionsQuestion.SIMILAR);
                }
            }
            mComparingFractionsQuestions.add(comparingFractionsQuestion);
            mQuestionNum++;
            setTxtFraction();
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
