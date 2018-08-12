package com.example.laher.learnfractions.lessons.ordering_dissimilar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.fraction_util.questions.GettingLcmQuestion;
import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.model.ExerciseStat;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.ExerciseStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Styles;

import org.json.JSONObject;

import java.util.ArrayList;

public class OrderingDissimilarExerciseActivity extends AppCompatActivity {
    Context mContext = this;
    private static final String TAG = "OD_E1";

    Exercise exercise;
    ExerciseStat mExerciseStat;
    final int EXERCISE_NUM = 1;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Ordering Fraction";
    //LCM DIALOG
    Dialog lcmDialog;
    View lcmView;
    TextView diagLcmtxtNum1, diagLcmtxtNum2, diagLcmtxtNum3;
    EditText diagLcmInputLcm;
    Button diagLcmBtnCheck;
    //GUI
    TextView txtNum1,txtNum2, txtNum3, txtScore, txtInstruction;
    //VARIABLES
    ArrayList<GettingLcmQuestion> mGettingLcmQuestions;
    GettingLcmQuestion mGettingLcmQuestion;
    int mQuestionNum;


    int correct, error;
    int requiredCorrects;
    int maxErrors;
    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;

    long startingTime, endingTime;

    int clicks;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_dissimilar_exercise);
        exercise = LessonArchive.getLesson(AppConstants.ORDERING_DISSIMILAR).getExercises().get(EXERCISE_NUM-1);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderingDissimilarExerciseActivity.this,
                        OrderingDissimilarVideoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CHANGE INTENT PARAMS
                Intent intent = new Intent(OrderingDissimilarExerciseActivity.this,
                        OrderingDissimilarExercise2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        //LCM DIALOG
        lcmView = getLayoutInflater().inflate(R.layout.layout_dialog_lcm, null);
        lcmDialog = new Dialog(OrderingDissimilarExerciseActivity.this);
        lcmDialog.setOnShowListener(new LcmDialogListener());
        lcmDialog.setOnDismissListener(new LcmDialogListener());
        lcmDialog.setOnCancelListener(new LcmDialogListener());
        lcmDialog.setTitle("Getting the LCM");
        lcmDialog.setContentView(lcmView);
        diagLcmtxtNum1 = lcmView.findViewById(R.id.lcm_txtNum1);
        diagLcmtxtNum2 = lcmView.findViewById(R.id.lcm_txtNum2);
        diagLcmtxtNum3 = lcmView.findViewById(R.id.lcm_txtNum3);
        diagLcmInputLcm = lcmView.findViewById(R.id.lcm_inputLcm);
        diagLcmInputLcm.setOnKeyListener(new DiagLcmTxtInputLcmListener());
        diagLcmInputLcm.setOnEditorActionListener(new DiagLcmTxtInputLcmListener());
        diagLcmBtnCheck = lcmView.findViewById(R.id.lcm_btnCheck);
        diagLcmBtnCheck.setOnClickListener(new DiagLcmBtnCheckListener());
        //GUI
        txtNum1 = findViewById(R.id.od1_txtNum1);
        txtNum2 = findViewById(R.id.od1_txtNum2);
        txtNum3 = findViewById(R.id.od1_txtNum3);
        txtScore = findViewById(R.id.od1_txtScore);
        setTxtScore();
        txtInstruction = findViewById(R.id.od1_txtInstruction);

        setAttributes((ExerciseStat) exercise);
        if (!Storage.isEmpty()) {
            checkUpdate();
        }
        startingTime = System.currentTimeMillis();

        go();
    }

    public void setAttributes(ExerciseStat exerciseAtt){
        Log.d(TAG, "set attributes");
        requiredCorrects = exerciseAtt.getRequiredCorrects();
        maxErrors = exerciseAtt.getMaxErrors();
        correctsShouldBeConsecutive = exerciseAtt.isRc_consecutive();
        errorsShouldBeConsecutive = exerciseAtt.isMe_consecutive();
        mExerciseStat = exerciseAtt;
        mExerciseStat.setTopicName(exercise.getTopicName());
        mExerciseStat.setExerciseNum(exercise.getExerciseNum());
        setTxtScore();
    }
    public void checkUpdate(){
        if (Storage.load(mContext, Storage.USER_TYPE).equals(AppConstants.STUDENT)){
            Service service = new Service("Checking for updates...", mContext, new ServiceResponse() {
                @Override
                public void postExecute(JSONObject response) {
                    try {
                        if (response.optString("message") != null && response.optString("message").equals("Exercise not found.")){
                        } else {
                            Exercise updatedExercise = new ExerciseStat();
                            updatedExercise.setRequiredCorrects(Integer.valueOf(response.optString("required_corrects")));
                            if (response.optString("rc_consecutive").equals("1")) {
                                updatedExercise.setRc_consecutive(true);
                            } else {
                                updatedExercise.setRc_consecutive(false);
                            }
                            updatedExercise.setMaxErrors(Integer.valueOf(response.optString("max_errors")));
                            if (response.optString("me_consecutive").equals("1")) {
                                updatedExercise.setMe_consecutive(true);
                            } else {
                                updatedExercise.setMe_consecutive(false);
                            }
                            setAttributes((ExerciseStat) updatedExercise);
                            startingTime = System.currentTimeMillis();
                        }
                    } catch (Exception e){e.printStackTrace();}
                }
            });
            Student student = new Student();
            student.setTeacher_code(Storage.load(mContext, Storage.TEACHER_CODE));
            ExerciseService.getUpdate(exercise, student, service);
        }
    }
    public void go(){
        setQuestions();
        setup();
    }
    public void setup(){
        clicks = 0;
        resetTxtColors();
        setTxtListeners();
    }
    public void setQuestions(){
        mQuestionNum = 1;
        mGettingLcmQuestions = new ArrayList<>();
        for (int i = 0; i < requiredCorrects; i++){
            GettingLcmQuestion gettingLcmQuestion = new GettingLcmQuestion();
            while (mGettingLcmQuestions.contains(gettingLcmQuestion)){
                gettingLcmQuestion = new GettingLcmQuestion();
            }
            mGettingLcmQuestions.add(gettingLcmQuestion);
        }
        setGuiNumbers();
    }
    public void setGuiNumbers(){
        mGettingLcmQuestion = mGettingLcmQuestions.get(mQuestionNum-1);
        int number1 = mGettingLcmQuestion.getNumber1();
        int number2 = mGettingLcmQuestion.getNumber2();
        int number3 = mGettingLcmQuestion.getNumber3();
        txtNum1.setText(String.valueOf(number1));
        txtNum2.setText(String.valueOf(number2));
        txtNum3.setText(String.valueOf(number3));
        txtInstruction.setText("Click all numbers.");
    }
    public void setTxtListeners(){
        txtNum1.setOnClickListener(new TxtNumListener());
        txtNum2.setOnClickListener(new TxtNumListener());
        txtNum3.setOnClickListener(new TxtNumListener());
    }
    public void resetTxtColors(){
        txtNum1.setTextColor(Color.rgb(128,128,128));
        txtNum2.setTextColor(Color.rgb(128,128,128));
        txtNum3.setTextColor(Color.rgb(128,128,128));
    }
    public void setTxtScore(){
        txtScore.setText(AppConstants.SCORE(correct,requiredCorrects));
    }
    public void correct(){
        correct++;
        if (errorsShouldBeConsecutive) {
            error = 0;
        }
        setTxtScore();
        removeTxtNumListener();
        if (correct >= requiredCorrects){
            endingTime = System.currentTimeMillis();
            if (!Storage.isEmpty()) {
                setFinalAttributes();
            }
            txtInstruction.setText(AppConstants.FINISHED_EXERCISE);
            btnNext.setEnabled(true);
        } else {
            txtInstruction.setText(AppConstants.CORRECT);
            Log.d(TAG, "txtInstruction text: " + txtInstruction.getText().toString());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextQuestion();
                }
            }, 2000);
        }
    }
    public void nextQuestion(){
        mQuestionNum++;
        setGuiNumbers();
        setup();
    }
    public void wrong(){
        error++;
        mExerciseStat.incrementError();
        if (correctsShouldBeConsecutive) {
            correct = 0;
        }
        setTxtScore();
        removeTxtNumListener();
        if (error >= maxErrors){
            if (errorsShouldBeConsecutive) {
                txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(error));
            } else {
                txtInstruction.setText(AppConstants.FAILED(error));
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(OrderingDissimilarExerciseActivity.this,
                            OrderingDissimilarVideoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 2000);
        } else {
            txtInstruction.setText(AppConstants.ERROR);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    lcmDialog.dismiss();
                    diagLcmInputLcm.setEnabled(true);
                    if (correctsShouldBeConsecutive) {
                        go();
                    } else {
                        GettingLcmQuestion gettingLcmQuestion = new GettingLcmQuestion();
                        while (mGettingLcmQuestions.contains(gettingLcmQuestion)){
                            gettingLcmQuestion = new GettingLcmQuestion();
                        }
                        mGettingLcmQuestions.add(gettingLcmQuestion);
                        nextQuestion();
                    }
                }
            }, 2000);
        }
    }
    private void setFinalAttributes(){
        Service service = new Service("Posting exercise stats...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try{
                    Log.d(TAG, "post execute");
                    Log.d(TAG, "message: " + response.optString("message"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mExerciseStat.setDone(true);
        mExerciseStat.setTime_spent(endingTime-startingTime);
        Student student = new Student();
        student.setId(Storage.load(mContext, Storage.STUDENT_ID));
        student.setTeacher_code(Storage.load(mContext, Storage.TEACHER_CODE));
        Log.d(TAG, "ATTRIBUTES: teacher_code: " + student.getTeacher_code() + "; student_id: " + student.getId() + "topic_name: " +
                mExerciseStat.getTopicName() + "; exercise_num: " + mExerciseStat.getExerciseNum() + "; done: " + mExerciseStat.isDone() +
                "; time_spent: " + mExerciseStat.getTime_spent() + "; errors: " + mExerciseStat.getErrors() + "; required_corrects: " +
                mExerciseStat.getRequiredCorrects() + "; rc_consecutive: " + mExerciseStat.isRc_consecutive() + "; max_errors: " +
                mExerciseStat.getMaxErrors() + "; me_consecutive: " + mExerciseStat.isMe_consecutive());
        ExerciseStatService.postStats(student,mExerciseStat,service);
    }
    public void diagInputLcm(){
        mGettingLcmQuestion = mGettingLcmQuestions.get(mQuestionNum-1);
        int number1 = mGettingLcmQuestion.getNumber1();
        int number2 = mGettingLcmQuestion.getNumber2();
        int number3 = mGettingLcmQuestion.getNumber3();
        diagLcmtxtNum1.setText(String.valueOf(number1));
        diagLcmtxtNum2.setText(String.valueOf(number2));
        diagLcmtxtNum3.setText(String.valueOf(number3));
        lcmDialog.show();
        txtInstruction.setText("Get the lcm.");
    }
    public void removeTxtNumListener(){
        txtNum1.setOnClickListener(null);
        txtNum2.setOnClickListener(null);
        txtNum3.setOnClickListener(null);
    }
    public class TxtNumListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            if (t.getCurrentTextColor() != Color.rgb(0,255,0)){
                t.setTextColor(Color.rgb(0,255,0));
                clicks++;
            }
            if (clicks>=3){
                diagInputLcm();
            }
        }
    }
    public class DiagLcmBtnCheckListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            mGettingLcmQuestion = mGettingLcmQuestions.get(mQuestionNum-1);
            int lcm = mGettingLcmQuestion.getLcm();
            String strLcm = String.valueOf(lcm);
            if (String.valueOf(diagLcmInputLcm.getText()).equals(strLcm)){
                lcmDialog.dismiss();
                correct();
            } else {
                diagLcmInputLcm.setTextColor(Color.rgb(255,0,0));
                Styles.shakeAnimate(diagLcmInputLcm);
                diagLcmInputLcm.setEnabled(false);
                wrong();
            }
        }
    }
    public class DiagLcmTxtInputLcmListener implements EditText.OnKeyListener, TextView.OnEditorActionListener{
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){
                diagLcmBtnCheck.performClick();
            }
            return false;
        }
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            diagLcmInputLcm.setTextColor(Color.rgb(0,0,0));
            return false;
        }
    }
    public class LcmDialogListener implements Dialog.OnDismissListener, DialogInterface.OnCancelListener, DialogInterface.OnShowListener{
        @Override
        public void onDismiss(DialogInterface dialog) {
            diagLcmInputLcm.setText("");
            diagLcmInputLcm.setTextColor(Color.rgb(0,0,0));
            clicks = 0;
            resetTxtColors();
        }
        @Override
        public void onCancel(DialogInterface dialog) {
            txtInstruction.setText("Click all numbers.");
        }
        @Override
        public void onShow(DialogInterface dialog) {
            diagLcmInputLcm.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
        }
    }
}
