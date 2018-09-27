package com.example.laher.learnfractions.lessons.fraction_meaning;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.fraction_questions.FractionMeaningQuestion;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.ActivityUtil;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.AppIDs;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;


import java.util.ArrayList;
import java.util.Collections;

import pl.droidsonroids.gif.GifImageView;

public class FractionMeaningExerciseActivity extends LessonExercise {
    private static final String TAG = "FM_E1";
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
    GifImageView gifAvatar;
    Button btnChoice1;
    Button btnChoice2;
    Button btnChoice3;
    Button btnChoice4;
    TextView txtScore;
    TextView txtInstruction;
    ConstraintLayout constraintLayoutBackGround;
    ConstraintLayout constraintLayoutBottom;

    public String title = "Fraction Meaning ex.1"; // IMPORTANT VAR
    String id = AppIDs.FME_ID; // IMPORTANT VAR

    //ACTIVITY VARIABLES
    ArrayList<String> instructions;
    String strCorrectAns;
    int num;
    int denominator;
    public final String INSTRUCTION_DENOMINATOR = "Click how many parts the whole is divided into.";
    public final String INSTRUCTION_NUM = "Click how many parts we have.";

    ArrayList<FractionMeaningQuestion> mFractionMeaningQuestions;
    int mQuestionNum;

    public FractionMeaningExerciseActivity() {
        super();
        Range range = getRange();
        Probability probability = new Probability(Probability.SUMMATION_NOTATION_1, range);
        setProbability(probability);
        setId(id);
        setExerciseTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fraction_meaning_exercise);
        setId(id);
        setExerciseTitle(title);
        super.onCreate(savedInstanceState);
        Range range = getRange();
        Probability probability = new Probability(Probability.SUMMATION_NOTATION_1, range);
        setProbability(probability);
        imgBox1 = findViewById(R.id.a_imgBox1);
        imgBox2 = findViewById(R.id.a_imgBox2);
        imgBox3 = findViewById(R.id.a_imgBox3);
        imgBox4 = findViewById(R.id.a_imgBox4);
        imgBox5 = findViewById(R.id.a_imgBox5);
        imgBox6 = findViewById(R.id.a_imgBox6);
        imgBox7 = findViewById(R.id.a_imgBox7);
        imgBox8 = findViewById(R.id.a_imgBox8);
        imgBox9 = findViewById(R.id.a_imgBox9);
        gifAvatar = findViewById(R.id.a_imgAvatar);
        int gifID = R.drawable.gentle_frits;
        gifAvatar.setImageResource(gifID);
        btnChoice1 = findViewById(R.id.btnChoice1);
        btnChoice2 = findViewById(R.id.btnChoice2);
        btnChoice3 = findViewById(R.id.btnChoice3);
        btnChoice4 = findViewById(R.id.btnChoice4);
        Styles.bgPaintMainBlue(btnChoice1);
        Styles.bgPaintMainYellow(btnChoice2);
        Styles.bgPaintMainOrange(btnChoice3);
        Styles.bgPaintMainBlueGreen(btnChoice4);
        btnChoice1.setOnClickListener(new BtnChoiceListener());
        btnChoice2.setOnClickListener(new BtnChoiceListener());
        btnChoice3.setOnClickListener(new BtnChoiceListener());
        btnChoice4.setOnClickListener(new BtnChoiceListener());
        txtInstruction = findViewById(R.id.txtInstruction);
        txtScore = findViewById(R.id.txtScore);

        constraintLayoutBackGround = findViewById(R.id.fme_constraintLayoutBackground);
        constraintLayoutBackGround.setBackgroundResource(R.drawable.factory_background);

        constraintLayoutBottom = findViewById(R.id.fme_constraintLayoutBottom);
        constraintLayoutBottom.setBackgroundResource(R.drawable.factory_bottom);

        int resourceID = R.drawable.factory_toolbar;
        setToolBarBackground(resourceID);

        instructions = new ArrayList<>();
        instructions.add(INSTRUCTION_DENOMINATOR);
        instructions.add(INSTRUCTION_NUM);

        startExercise();
    }

    public void generateFractionQuestions(){
        mFractionMeaningQuestions = new ArrayList<>();
        mQuestionNum = 1;
        int item_size;
        int requiredCorrects = getItemsSize();
        if (requiredCorrects%2!=0){
            item_size = requiredCorrects/2+1;
        } else {
            item_size = requiredCorrects/2;
        }
        for (int i = 0; i < item_size; i++){
            FractionMeaningQuestion fractionMeaningQuestion = new FractionMeaningQuestion();
            while (mFractionMeaningQuestions.contains(fractionMeaningQuestion)){
                fractionMeaningQuestion = new FractionMeaningQuestion();
            }
            mFractionMeaningQuestions.add(fractionMeaningQuestion);
        }
        for (FractionMeaningQuestion fractionMeaningQuestion : mFractionMeaningQuestions) {
            Log.d(TAG, "num: " + fractionMeaningQuestion.getNumeratorAnswer());
            Log.d(TAG, "denominator: " + fractionMeaningQuestion.getDenominatorAnswer());
            Log.d(TAG, "------------------------------------");
        }
        Log.d(TAG, "size: " + mFractionMeaningQuestions.size());
        setBoxes();
    }
    public void setBoxes(){
        num = mFractionMeaningQuestions.get(mQuestionNum-1).getNumerator();
        denominator = mFractionMeaningQuestions.get(mQuestionNum-1).getDenominator();
        denominator = denominator - num;
        ArrayList<Integer> imageList = new ArrayList<>();
        for (int i = 1; i <= num; i++){
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
        setTxtInstruction();
    }
    public void setButtonChoices(){
        int correctAnswer = Integer.valueOf(strCorrectAns);
        ArrayList<Integer> choiceNumbers = new ArrayList<>();
        //FOUR CHOICES
        choiceNumbers.add(correctAnswer);
        int randomNum;
        for(int i = 0; i < 3; i++){
            randomNum = (int) (Math.random() * 9 + 1);
            while (choiceNumbers.contains(randomNum)){
                randomNum = (int) (Math.random() * 9 + 1);
            }
            choiceNumbers.add(randomNum);
        }
        Collections.shuffle(choiceNumbers);
        btnChoice1.setText(String.valueOf(choiceNumbers.get(0)));
        btnChoice2.setText(String.valueOf(choiceNumbers.get(1)));
        btnChoice3.setText(String.valueOf(choiceNumbers.get(2)));
        btnChoice4.setText(String.valueOf(choiceNumbers.get(3)));
    }
    public void setTxtInstruction(){
        Collections.shuffle(instructions);
        txtInstruction.setText(instructions.get(0));
        if(instructions.get(0).equals(INSTRUCTION_DENOMINATOR)){
            strCorrectAns = String.valueOf(mFractionMeaningQuestions.get(mQuestionNum-1).getDenominator());
            ActivityUtil.playAvatarMediaPlayer(getContext(),R.raw.fme_how_many_parts);
        } else if (instructions.get(0).equals(INSTRUCTION_NUM)){
            strCorrectAns = String.valueOf(mFractionMeaningQuestions.get(mQuestionNum-1).getNumerator());
            ActivityUtil.playAvatarMediaPlayer(getContext(),R.raw.fme_we_have);
        }
        setButtonChoices();
    }

    public class BtnChoiceListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Button choice = (Button) view;
            if (choice.getText() == strCorrectAns) {
                correct();// IMPORTANT METHOD
            } else {
                wrong();// IMPORTANT METHOD
            }
        }
    }

    @Override
    public void showScore(){
        super.showScore();
        int correct = getCorrect();
        int itemsSize = getItemsSize();
        Log.d(TAG, "c:"+correct);
        Log.d(TAG, "rc:"+itemsSize);
        txtScore.setText(AppConstants.SCORE(correct,itemsSize));
    }

    @Override
    protected void startExercise() {
        super.startExercise();
        generateFractionQuestions();
    }

    @Override
    protected void preAnswered() {
        super.preAnswered();
        btnChoice1.setEnabled(false);
        btnChoice2.setEnabled(false);
        btnChoice3.setEnabled(false);
        btnChoice4.setEnabled(false);
    }

    @Override
    protected void postAnswered() {
        super.postAnswered();
        btnChoice1.setEnabled(true);
        btnChoice2.setEnabled(true);
        btnChoice3.setEnabled(true);
        btnChoice4.setEnabled(true);
    }

    @Override
    protected void preCorrect() {
        super.preCorrect();
        txtInstruction.setText(AppConstants.CORRECT);
    }

    @Override
    protected void postCorrect() {
        super.postCorrect();
        instructions.remove(0);
        if (instructions.size() == 0) {
            instructions.add(INSTRUCTION_DENOMINATOR);
            instructions.add(INSTRUCTION_NUM);
            mQuestionNum++;
            setBoxes();
        } else {
            setTxtInstruction();
        }
    }

    @Override
    protected void preFinished() {
        super.preFinished();
        txtInstruction.setText(AppConstants.FINISHED_EXERCISE);
        ActivityUtil.playAvatarMediaPlayer(getContext(),R.raw.finished_exercise);
    }

    @Override
    protected void preWrong() {
        super.preWrong();
        txtInstruction.setText(AppConstants.ERROR);
    }

    @Override
    protected void postWrong() {
        super.postWrong();
        final boolean correctsShouldBeConsecutive = isCorrectsShouldBeConsecutive();
        if (correctsShouldBeConsecutive) {
            generateFractionQuestions();
        } else {
            FractionMeaningQuestion fractionMeaningQuestion = new FractionMeaningQuestion();
            int questionsSize = mFractionMeaningQuestions.size();
            int maxItemSize = getMaxItemSize();
            while (mFractionMeaningQuestions.contains(fractionMeaningQuestion) && questionsSize<maxItemSize){
                fractionMeaningQuestion = new FractionMeaningQuestion();
            }
            mFractionMeaningQuestions.add(fractionMeaningQuestion);
            instructions.remove(0);
            if (instructions.size() == 0) {
                instructions.add(INSTRUCTION_DENOMINATOR);
                instructions.add(INSTRUCTION_NUM);
                mQuestionNum++;
                setBoxes();
            } else {
                setTxtInstruction();
            }
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
