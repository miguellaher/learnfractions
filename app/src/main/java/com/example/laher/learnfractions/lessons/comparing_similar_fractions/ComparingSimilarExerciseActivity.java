package com.example.laher.learnfractions.lessons.comparing_similar_fractions;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.fraction_util.questions.ComparingNumbersQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;

import java.util.ArrayList;

public class ComparingSimilarExerciseActivity extends LessonExercise {
    //private static final String TAG = "CS_E1";

    //GUI
    TextView txtNum1;
    TextView txtNum2;
    TextView txtScore;
    TextView txtCompareSign;
    TextView txtInstruction;
    Button btnGreater;
    Button btnEquals;
    Button btnLess;
    ImageView imgAvatar;
    //VARIABLES
    public final String GREATER_THAN = ">";
    public final String EQUAL_TO = "=";
    public final String LESS_THAN = "<";

    public String title = "Comparing Similar ex.1";
    String id = AppIDs.CSE_ID;

    ArrayList<ComparingNumbersQuestion> mComparingNumbersQuestions;
    int mQuestionNum;

    public ComparingSimilarExerciseActivity() {
        super();
        setId(id);
        setExerciseTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comparing_similar_exercise);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);

        //GUI
        txtNum1 = findViewById(R.id.c1_txtNum1);
        txtNum2 = findViewById(R.id.c1_txtNum2);
        txtCompareSign = findViewById(R.id.c1_txtCompareSign);
        txtScore = findViewById(R.id.c1_txtScore);
        txtInstruction = findViewById(R.id.c1_txtInstruction);
        imgAvatar = findViewById(R.id.cse_imgAvatar);


        btnGreater = findViewById(R.id.c1_btnGreater);
        btnEquals = findViewById(R.id.c1_btnEquals);
        btnLess = findViewById(R.id.c1_btnLess);
        btnGreater.setOnClickListener(new BtnListener());
        btnEquals.setOnClickListener(new BtnListener());
        btnLess.setOnClickListener(new BtnListener());



        startExercise();
    }
    private void generateNumbers(){
        mQuestionNum = 1;
        mComparingNumbersQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        for (int i = 0; i < requiredCorrects; i++){
            ComparingNumbersQuestion comparingNumbers = new ComparingNumbersQuestion();
            while (mComparingNumbersQuestions.contains(comparingNumbers)){
                comparingNumbers = new ComparingNumbersQuestion();
            }
            mComparingNumbersQuestions.add(comparingNumbers);
        }
        setNumbers();
    }
    public void setNumbers(){
        int number1 = mComparingNumbersQuestions.get(mQuestionNum-1).getNumber1();
        int number2 = mComparingNumbersQuestions.get(mQuestionNum-1).getNumber2();
        txtNum1.setText(String.valueOf(number1));
        txtNum2.setText(String.valueOf(number2));
        String instruction = "Compare the two numbers.";
        txtInstruction.setText(instruction);
    }
    public class BtnListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == btnGreater.getId()){
                txtCompareSign.setText(GREATER_THAN);
                check(GREATER_THAN);
            }
            if (v.getId() == btnEquals.getId()){
                txtCompareSign.setText(EQUAL_TO);
                check(EQUAL_TO);
            }
            if (v.getId() == btnLess.getId()){
                txtCompareSign.setText(LESS_THAN);
                check(LESS_THAN);
            }
        }
    }
    public void check(String compareSign){
        ComparingNumbersQuestion question = mComparingNumbersQuestions.get(mQuestionNum-1);
        int numberAnswer = question.getAnswer();
        int number1 = question.getNumber1();
        int number2 = question.getNumber2();

        if (compareSign.equals(GREATER_THAN)){
            if (numberAnswer == number1){
                correct();
            } else {
                wrong();
            }
        }
        if (compareSign.equals(EQUAL_TO)){
            if (numberAnswer == -1){
                correct();
            } else {
                wrong();
            }
        }
        if (compareSign.equals(LESS_THAN)){
            if (numberAnswer == number2){
                correct();
            } else {
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
        txtCompareSign.setText("_");
        generateNumbers();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        btnGreater.setEnabled(false);
        btnEquals.setEnabled(false);
        btnLess.setEnabled(false);
    }

    @Override
    protected void postAnswered() {
        super.postAnswered();
        txtCompareSign.setText("_");
        btnGreater.setEnabled(true);
        btnEquals.setEnabled(true);
        btnLess.setEnabled(true);
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
        setNumbers();
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
        ComparingNumbersQuestion comparingNumbers = new ComparingNumbersQuestion();
        int questionSize = mComparingNumbersQuestions.size();
        int maxItemsSize = getMaxItemsSize();
        while (mComparingNumbersQuestions.contains(comparingNumbers) && questionSize<=maxItemsSize){
            comparingNumbers = new ComparingNumbersQuestion();
        }
        mComparingNumbersQuestions.add(comparingNumbers);
        mQuestionNum++;
        setNumbers();
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