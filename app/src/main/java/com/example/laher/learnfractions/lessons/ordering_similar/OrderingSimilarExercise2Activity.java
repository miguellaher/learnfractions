package com.example.laher.learnfractions.lessons.ordering_similar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.fraction_questions.OrderingSimilarQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.ActivityUtil;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class OrderingSimilarExercise2Activity extends LessonExercise {
    //private static final String TAG = "OS_E2";
    //GUI
    TextView txtNum1;
    TextView txtNum2;
    TextView txtNum3;
    TextView txtDenom1;
    TextView txtDenom2;
    TextView txtDenom3;
    TextView txtScore;
    TextView txtInstruction;
    ConstraintLayout clFraction1;
    ConstraintLayout clFraction2;
    ConstraintLayout clFraction3;
    ImageView imageLine1;
    ImageView imageLine2;
    ImageView imageLine3;
    GifImageView gifAvatar;
    ConstraintLayout constraintLayoutBackground;
    ConstraintLayout constraintLayoutBottom;
    //VARIABLES
    ArrayList<OrderingSimilarQuestion> mOrderingSimilarQuestions;
    OrderingSimilarQuestion mOrderingSimilarQuestion;
    int mQuestionNum;
    int clicks;

    public String title = "Ordering Similar Fractions ex.2";
    String id = AppIDs.OSE2_ID;

    public OrderingSimilarExercise2Activity() {
        super();
        Range range = getRange();
        Probability probability = new Probability(Probability.ORDERING_SIMILAR, range);
        setProbability(probability);
        setRangeEditable(true);
        setId(id);
        setExerciseTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ordering_similar_exercise2);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);
        Range range = getRange();
        Probability probability = new Probability(Probability.ORDERING_SIMILAR, range);
        setProbability(probability);
        setRangeEditable(true);
        //GUI
        txtNum1 = findViewById(R.id.os2_txtNum1);
        txtNum2 = findViewById(R.id.os2_txtNum2);
        txtNum3 = findViewById(R.id.os2_txtNum3);
        txtDenom1 = findViewById(R.id.os2_txtDenom1);
        txtDenom2 = findViewById(R.id.os2_txtDenom2);
        txtDenom3 = findViewById(R.id.os2_txtDenom3);
        txtScore = findViewById(R.id.os2_txtScore);
        txtInstruction = findViewById(R.id.os2_txtInstruction);
        clFraction1 = findViewById(R.id.os2_clFraction1);
        clFraction2 = findViewById(R.id.os2_clFraction2);
        clFraction3 = findViewById(R.id.os2_clFraction3);
        imageLine1 = findViewById(R.id.ose2_imgLine1);
        imageLine2 = findViewById(R.id.ose2_imgLine2);
        imageLine3 = findViewById(R.id.ose2_imgLine3);
        imageLine1.setImageResource(R.drawable.line);
        imageLine2.setImageResource(R.drawable.line);
        imageLine3.setImageResource(R.drawable.line);

        gifAvatar = findViewById(R.id.gifAvatar);
        int gifID = R.drawable.kid_frits;
        gifAvatar.setImageResource(gifID);

        constraintLayoutBackground = findViewById(R.id.constraintLayoutBackground);
        constraintLayoutBackground.setBackgroundResource(R.drawable.playground_background);

        constraintLayoutBottom = findViewById(R.id.constraintLayoutBottom);
        constraintLayoutBottom.setBackgroundResource(R.drawable.playground_bottom);

        int resourceID = R.drawable.playground_toolbar;
        setToolBarBackground(resourceID);

        startExercise();
    }
    public void setFractionQuestions(){
        mQuestionNum = 1;
        mOrderingSimilarQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        Range range = getRange();
        for (int i = 0; i < requiredCorrects; i++){
            OrderingSimilarQuestion orderingFractionsQuestion = new OrderingSimilarQuestion(range);
            while(mOrderingSimilarQuestions.contains(orderingFractionsQuestion)){
                orderingFractionsQuestion = new OrderingSimilarQuestion(range);
            }
            mOrderingSimilarQuestions.add(orderingFractionsQuestion);
        }
        setGuiFractions();
    }
    public void setGuiFractions(){
        clicks = 0;
        mOrderingSimilarQuestion = mOrderingSimilarQuestions.get(mQuestionNum-1);
        Fraction fraction1 = mOrderingSimilarQuestion.getFraction1();
        Fraction fraction2 = mOrderingSimilarQuestion.getFraction2();
        Fraction fraction3 = mOrderingSimilarQuestion.getFraction3();
        String instruction = "Click from least to greatest.";
        txtInstruction.setText(instruction);
        ActivityUtil.playAvatarMediaPlayer(getContext(), R.raw.ose_least_greatest);
        txtNum1.setText(String.valueOf(fraction1.getNumerator()));
        txtNum2.setText(String.valueOf(fraction2.getNumerator()));
        txtNum3.setText(String.valueOf(fraction3.getNumerator()));
        txtDenom1.setText(String.valueOf(fraction1.getDenominator()));
        txtDenom2.setText(String.valueOf(fraction2.getDenominator()));
        txtDenom3.setText(String.valueOf(fraction3.getDenominator()));
        setClFractionsListener();
        resetTxtNumColor();
    }
    public void resetTxtNumColor(){
        txtNum1.setTextColor(Color.rgb(128,128,128));
        txtNum2.setTextColor(Color.rgb(128,128,128));
        txtNum3.setTextColor(Color.rgb(128,128,128));
        txtDenom1.setTextColor(Color.rgb(128,128,128));
        txtDenom2.setTextColor(Color.rgb(128,128,128));
        txtDenom3.setTextColor(Color.rgb(128,128,128));
    }
    public void removeClFractionsListener(){
        clFraction1.setOnClickListener(null);
        clFraction2.setOnClickListener(null);
        clFraction3.setOnClickListener(null);
    }
    public void setClFractionsListener(){
        clFraction1.setOnClickListener(new ClFractionListener());
        clFraction2.setOnClickListener(new ClFractionListener());
        clFraction3.setOnClickListener(new ClFractionListener());
    }
    public class ClFractionListener implements ConstraintLayout.OnClickListener{
        TextView num, denom;
        @Override
        public void onClick(View v) {
            mOrderingSimilarQuestion = mOrderingSimilarQuestions.get(mQuestionNum-1);
            Fraction fraction1 = mOrderingSimilarQuestion.getFraction1();
            Fraction fraction2 = mOrderingSimilarQuestion.getFraction2();
            Fraction fraction3 = mOrderingSimilarQuestion.getFraction3();
            if (v.getId()==clFraction1.getId()){
                num = txtNum1;
                denom = txtDenom1;
                check(fraction1);
            }
            if (v.getId()==clFraction2.getId()){
                num = txtNum2;
                denom = txtDenom2;
                check(fraction2);
            }
            if (v.getId()==clFraction3.getId()){
                num = txtNum3;
                denom = txtDenom3;
                check(fraction3);
            }
            if (clicks>=3){
                correct();
            }
        }
        public void check(Fraction fraction){
            ArrayList<Fraction> sortedFraction = mOrderingSimilarQuestion.getSortedFractions();
            Fraction correctFraction = sortedFraction.get(clicks);
            if (fraction.equals(correctFraction)){
                setTextColor(0,255,0);
                clicks++;
            } else {
                setTextColor(255,0,0);
                wrong();
            }
        }
        public void setTextColor(int r, int g, int b){
            num.setTextColor(Color.rgb(r, g, b));
            denom.setTextColor(Color.rgb(r, g, b));
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
        removeClFractionsListener();
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
        setGuiFractions();
    }

    @Override
    protected void preFinished() {
        super.preFinished();
        txtInstruction.setText(AppConstants.FINISHED_LESSON);
        ActivityUtil.playAvatarMediaPlayer(getContext(), R.raw.finished_lesson);
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
            setFractionQuestions();
        } else {
            OrderingSimilarQuestion orderingFractionsQuestion = new OrderingSimilarQuestion(range);
            int questionsSize = mOrderingSimilarQuestions.size();
            int maxItemSize = getMaxItemSize();
            while (mOrderingSimilarQuestions.contains(orderingFractionsQuestion) && questionsSize<maxItemSize){
                orderingFractionsQuestion = new OrderingSimilarQuestion(range);
            }
            mOrderingSimilarQuestions.add(orderingFractionsQuestion);
            mQuestionNum++;
            setGuiFractions();
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
