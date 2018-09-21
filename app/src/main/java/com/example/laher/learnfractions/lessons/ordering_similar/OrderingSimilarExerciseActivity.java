package com.example.laher.learnfractions.lessons.ordering_similar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.questions.OrderingNumbersQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.ActivityUtil;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class OrderingSimilarExerciseActivity extends LessonExercise {
    //private static final String TAG = "OS_E1";
    //GUI
    TextView txtNum1;
    TextView txtNum2;
    TextView txtNum3;
    TextView txtScore;
    TextView txtInstruction;
    GifImageView gifAvatar;
    ConstraintLayout constraintLayoutBackground;
    ConstraintLayout constraintLayoutBottom;
    //VARIABLES
    ArrayList<OrderingNumbersQuestion> mOrderingNumbersQuestions;
    OrderingNumbersQuestion mOrderingNumbersQuestion;
    int mQuestionNum;
    int clicks;

    public String title = "Ordering Similar Fractions ex.1";
    String id = AppIDs.OSE_ID;

    public OrderingSimilarExerciseActivity() {
        super();
        Range range = getRange();
        Probability probability = new Probability(Probability.ORDERING_NUMBERS, range);
        setProbability(probability);
        setId(id);
        setExerciseTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ordering_similar_exercise);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);
        Range range = getRange();
        Probability probability = new Probability(Probability.ORDERING_NUMBERS, range);
        setProbability(probability);
        //GUI
        txtNum1 = findViewById(R.id.os_txtNum1);
        txtNum2 = findViewById(R.id.os_txtNum2);
        txtNum3 = findViewById(R.id.os_txtNum3);
        txtScore = findViewById(R.id.os_txtScore);
        txtInstruction = findViewById(R.id.os_txtInstruction);

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
    public void setQuestions(){
        mQuestionNum = 1;
        mOrderingNumbersQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        for (int i = 0; i < requiredCorrects; i++){
            OrderingNumbersQuestion orderingNumbersQuestion = new OrderingNumbersQuestion();
            while (mOrderingNumbersQuestions.contains(orderingNumbersQuestion)){
                orderingNumbersQuestion = new OrderingNumbersQuestion();
            }
            mOrderingNumbersQuestions.add(orderingNumbersQuestion);
        }
        setTxtNumbers();
    }
    public void setTxtNumbers(){
        mOrderingNumbersQuestion = mOrderingNumbersQuestions.get(mQuestionNum-1);
        clicks = 0;
        resetTxtNumColor();
        int number1 = mOrderingNumbersQuestion.getNumber1();
        int number2 = mOrderingNumbersQuestion.getNumber2();
        int number3 = mOrderingNumbersQuestion.getNumber3();
        txtNum1.setText(String.valueOf(number1));
        txtNum2.setText(String.valueOf(number2));
        txtNum3.setText(String.valueOf(number3));
        setTxtNumListener();
        String instruction = "Click from least to greatest.";
        txtInstruction.setText(instruction);
        ActivityUtil.playAvatarMediaPlayer(getContext(), R.raw.ose_least_greatest);
    }
    public void resetTxtNumColor(){
        txtNum1.setTextColor(Color.rgb(128,128,128));
        txtNum2.setTextColor(Color.rgb(128,128,128));
        txtNum3.setTextColor(Color.rgb(128,128,128));
    }
    public void setTxtNumListener(){
        txtNum1.setOnClickListener(new TxtNumbersListener());
        txtNum2.setOnClickListener(new TxtNumbersListener());
        txtNum3.setOnClickListener(new TxtNumbersListener());
    }
    public void removeTxtNumListener(){
        txtNum1.setOnClickListener(null);
        txtNum2.setOnClickListener(null);
        txtNum3.setOnClickListener(null);
    }
    public class TxtNumbersListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            t.setOnClickListener(null);
            t.setClickable(false);
            t.setTextColor(Color.rgb(0,255,0));
            String s = t.getText().toString();
            ArrayList<Integer> numbers = mOrderingNumbersQuestion.getSortedNumbers();
            String correctNumber = String.valueOf(numbers.get(clicks));
            if (s.equals(correctNumber)){
                clicks++;
                if (clicks>=3) {
                    correct();
                }
            } else {
                t.setTextColor(Color.rgb(255,0,0));
                wrong();
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
        setQuestions();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        removeTxtNumListener();
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
        setTxtNumbers();
    }

    @Override
    protected void preFinished() {
        super.preFinished();
        txtInstruction.setText(AppConstants.FINISHED_EXERCISE);
        ActivityUtil.playAvatarMediaPlayer(getContext(), R.raw.finished_exercise);
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
            setQuestions();
        } else {
            OrderingNumbersQuestion orderingNumbersQuestion = new OrderingNumbersQuestion();
            int questionsSize = mOrderingNumbersQuestions.size();
            int maxItemSize = getMaxItemSize();
            while (mOrderingNumbersQuestions.contains(orderingNumbersQuestion) && questionsSize<maxItemSize){
                orderingNumbersQuestion = new OrderingNumbersQuestion();
            }
            mOrderingNumbersQuestions.add(orderingNumbersQuestion);
            mQuestionNum++;
            setTxtNumbers();
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
