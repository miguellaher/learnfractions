package com.example.laher.learnfractions.lessons.non_visual_fraction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.fraction_util.fraction_questions.NonVisualQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;

import java.util.ArrayList;
import java.util.Collections;

public class NonVisualExerciseActivity extends LessonExercise {
    //GUI
    TextView txtNumerator;
    TextView txtDenominator;
    TextView txtInstruction;
    TextView txtScore;
    ImageView imgLine;

    public String title = "Non-Visual Fractions\nex.1";
    String id = AppIDs.NVE_ID;

    //ACTIVITY VARIABLES
    ArrayList<String> instructions;
    public final String INSTRUCTION_NUMERATOR = "Click the numerator.";
    public final String INSTRUCTION_DENOMINATOR = "Click the denominator.";

    ArrayList<NonVisualQuestion> mNonVisualQuestions;
    int mQuestionNum;

    public NonVisualExerciseActivity() {
        super();
        setId(id);
        setExerciseTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_non_visual_exercise);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);

        txtNumerator = findViewById(R.id.b1_txtNumerator);
        txtDenominator = findViewById(R.id.b1_txtDenominator);
        txtInstruction = findViewById(R.id.b1_txtInstruction);
        txtNumerator.setOnClickListener(new TxtFractionListener());
        txtDenominator.setOnClickListener(new TxtFractionListener());
        txtScore = findViewById(R.id.b1_txtScore);
        imgLine = findViewById(R.id.nve_imgLine);
        imgLine.setImageResource(R.drawable.line);

        instructions = new ArrayList<>();
        instructions.add(INSTRUCTION_NUMERATOR);
        instructions.add(INSTRUCTION_DENOMINATOR);

        startExercise();
    }

    private void generateFractionQuestions(){
        mQuestionNum = 1;
        mNonVisualQuestions = new ArrayList<>();
        int requiredCorrects = getItemsSize();
        for (int i = 0; i < requiredCorrects; i++){
            NonVisualQuestion nonVisualQuestion = new NonVisualQuestion();
            while (mNonVisualQuestions.contains(nonVisualQuestion)) {
                nonVisualQuestion = new NonVisualQuestion();
            }
            mNonVisualQuestions.add(nonVisualQuestion);
        }
        setTxtFraction();
    }
    public void setTxtFraction(){
        int numerator = mNonVisualQuestions.get(mQuestionNum-1).getNumerator();
        int denominator = mNonVisualQuestions.get(mQuestionNum-1).getDenominator();
        txtNumerator.setText(String.valueOf(numerator));
        txtDenominator.setText(String.valueOf(denominator));
        generateInstruction();
    }
    public void generateInstruction(){
        Collections.shuffle(instructions);
        txtInstruction.setText(instructions.get(0));
    }

    public void resetColor() {
        txtNumerator.setTextColor(Color.rgb(0,0,0));
        txtDenominator.setTextColor(Color.rgb(0,0,0));
    }
    public class TxtFractionListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.b1_txtNumerator){
                if (txtInstruction.getText()== INSTRUCTION_NUMERATOR){
                    txtNumerator.setTextColor(Color.rgb(0,255,0));
                    correct();
                } else {
                    txtNumerator.setTextColor(Color.rgb(255,0,0));
                    wrong();
                }
            } else if (v.getId() == R.id.b1_txtDenominator){
                if (txtInstruction.getText()== INSTRUCTION_DENOMINATOR){
                    txtDenominator.setTextColor(Color.rgb(0,255,0));
                    correct();
                } else {
                    txtDenominator.setTextColor(Color.rgb(255,0,0));
                    wrong();
                }
            }
        }
    }

    @Override
    public void showScore(){
        super.showScore();
        int correct = getCorrect();
        int itemsSize = getItemsSize();
        txtScore.setText(AppConstants.SCORE(correct,itemsSize));
    }

    @Override
    protected void startExercise() {
        super.startExercise();
        resetColor();
        generateFractionQuestions();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        txtNumerator.setOnClickListener(null);
        txtDenominator.setOnClickListener(null);
    }

    @Override
    protected void postAnswered() {
        super.postAnswered();
        resetColor();
        txtNumerator.setOnClickListener(new TxtFractionListener());
        txtDenominator.setOnClickListener(new TxtFractionListener());
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
            generateFractionQuestions();
        } else {
            resetColor();
            NonVisualQuestion nonVisualQuestion = new NonVisualQuestion();
            int questionsSize = mNonVisualQuestions.size();
            int maxItemsSize = getMaxItemsSize();
            while (mNonVisualQuestions.contains(nonVisualQuestion) && questionsSize<=maxItemsSize) {
                nonVisualQuestion = new NonVisualQuestion();
            }
            mNonVisualQuestions.add(nonVisualQuestion);
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
