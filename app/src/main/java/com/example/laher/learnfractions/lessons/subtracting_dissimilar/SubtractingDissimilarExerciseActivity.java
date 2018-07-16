package com.example.laher.learnfractions.lessons.subtracting_dissimilar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.fraction_util.FractionQuestion;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.TopicsMenuActivity;
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

public class SubtractingDissimilarExerciseActivity extends AppCompatActivity {
    Context mContext = this;
    private static final String TAG = "SD_E1";

    Exercise exercise;
    ExerciseStat mExerciseStat;
    final int EXERCISE_NUM = 1;

    //TOOLBAR
    Button btnBack, btnNext;
    TextView txtTitle;
    public final String TITLE = "Subtracting Fractions";
    //GUI
    TextView txtNum1, txtNum2, txtNum3, txtNum4, txtDenom1, txtDenom2, txtDenom3, txtDenom4, txtEquation1, txtEquation2
            , txtScore, txtInstruction, txtSign1, txtSign2;
    EditText inputNum, inputDenom;
    Button btnCheck;
    //LCM DIALOG
    Dialog lcmDialog;
    View lcmView;
    TextView diagLcmtxtNum1, diagLcmtxtNum2, diagLcmtxtNum3;
    EditText diagLcmInputLcm;
    Button diagLcmBtnCheck;
    //EQUATION DIALOG
    Dialog equationDialog;
    View edView;
    TextView diagEdTxtNum1, diagEdTxtNum2, diagEdTxtSign;
    EditText diagEdInputAnswer;
    Button diagEdBtnCheck;
    //VARIABLES
    FractionQuestion fractionQuestion;
    ArrayList<FractionQuestion> fractionQuestions;
    int questionNum;
    int correct, error;
    int requiredCorrects;
    int maxErrors;
    boolean correctsShouldBeConsecutive;
    boolean errorsShouldBeConsecutive;

    long startingTime, endingTime;

    ArrayList<Integer> viewIds;
    final Handler handler = new Handler();
    ColorStateList defaultColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_dissimilar_equation);
        exercise = LessonArchive.getLesson(AppConstants.SUBTRACTING_DISSIMILAR).getExercises().get(EXERCISE_NUM-1);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubtractingDissimilarExerciseActivity.this,
                        SubtractingDissimilarVideoActivity.class);
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
                Intent intent = new Intent(SubtractingDissimilarExerciseActivity.this,
                        TopicsMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(TITLE);
        txtTitle.setTextSize(14);
        btnNext.setText(AppConstants.DONE);
        //GUI
        txtNum1 = findViewById(R.id.adsm_txtNum1);
        txtNum2 = findViewById(R.id.adsm_txtNum2);
        txtNum3 = findViewById(R.id.adsm_txtNum3);
        txtNum4 = findViewById(R.id.adsm_txtNum4);
        txtDenom1 = findViewById(R.id.adsm_txtDenom1);
        txtDenom2 = findViewById(R.id.adsm_txtDenom2);
        txtDenom3 = findViewById(R.id.adsm_txtDenom3);
        txtDenom4 = findViewById(R.id.adsm_txtDenom4);
        txtEquation1 = findViewById(R.id.adsm_txtEquation1);
        txtEquation2 = findViewById(R.id.adsm_txtEquation2);
        setClickAreas();
        txtScore = findViewById(R.id.adsm_txtScore);
        setTxtScore();
        txtInstruction = findViewById(R.id.adsm_txtInstruction);
        inputNum = findViewById(R.id.adsm_inputNum);
        inputDenom = findViewById(R.id.adsm_inputDenom);
        inputDenom.setOnEditorActionListener(new InputListener());
        btnCheck = findViewById(R.id.adsm_btnCheck);
        btnCheck.setOnClickListener(new BtnCheckListener());
        txtSign1 = findViewById(R.id.adsm_txtSign1);
        txtSign2 = findViewById(R.id.adsm_txtSign2);
        txtSign1.setText(" - ");
        txtSign2.setText(" - ");
        //LCM DIALOG
        lcmView = getLayoutInflater().inflate(R.layout.layout_dialog_lcm, null);
        lcmDialog = new Dialog(SubtractingDissimilarExerciseActivity.this);
        lcmDialog.setOnDismissListener(new DialogListener());
        lcmDialog.setOnShowListener(new DialogListener());
        lcmDialog.setTitle("Getting the LCD");
        lcmDialog.setContentView(lcmView);
        diagLcmtxtNum1 = lcmView.findViewById(R.id.lcm_txtNum1);
        diagLcmtxtNum2 = lcmView.findViewById(R.id.lcm_txtNum2);
        diagLcmtxtNum3 = lcmView.findViewById(R.id.lcm_txtNum3);
        diagLcmInputLcm = lcmView.findViewById(R.id.lcm_inputLcm);
        diagLcmInputLcm.setOnEditorActionListener(new InputListener());
        diagLcmBtnCheck = lcmView.findViewById(R.id.lcm_btnCheck);
        diagLcmBtnCheck.setOnClickListener(new DiagLcmBtnCheckListener());
        //EQUATION DIALOG
        edView = getLayoutInflater().inflate(R.layout.layout_dialog_equation, null);
        equationDialog = new Dialog(SubtractingDissimilarExerciseActivity.this);
        equationDialog.setOnDismissListener(new DialogListener());
        equationDialog.setOnShowListener(new DialogListener());
        equationDialog.setTitle("Division Equation");
        equationDialog.setContentView(edView);
        diagEdTxtNum1 = edView.findViewById(R.id.md_txtMultiplicand);
        diagEdTxtNum2 = edView.findViewById(R.id.md_txtMultiplier);
        diagEdTxtSign = edView.findViewById(R.id.md_txtSign);
        diagEdInputAnswer = edView.findViewById(R.id.md_inputProduct);
        diagEdInputAnswer.setOnEditorActionListener(new InputListener());
        diagEdBtnCheck = edView.findViewById(R.id.md_btnCheck);
        diagEdBtnCheck.setOnClickListener(new DiagEdBtnCheckListener());

        defaultColor = txtNum1.getTextColors();
        viewIds = new ArrayList<>();

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
        setFractionGui();
        startUp();
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
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        btnCheck.setEnabled(false);
        if (correct >= requiredCorrects){
            endingTime = System.currentTimeMillis();
            if (!Storage.isEmpty()) {
                setFinalAttributes();
            }
            txtInstruction.setText(AppConstants.FINISHED_LESSON);
            btnNext.setEnabled(true);
        } else {
            txtInstruction.setText(AppConstants.CORRECT);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextQuestion();
                }
            }, 2000);
        }
    }
    public void nextQuestion(){
        questionNum++;
        setFractionGui();
        startUp();
    }
    public void wrong(){
        error++;
        mExerciseStat.incrementError();
        if (correctsShouldBeConsecutive) {
            correct = 0;
        }
        setTxtScore();
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        btnCheck.setEnabled(false);
        if (error >= maxErrors){
            if (errorsShouldBeConsecutive) {
                txtInstruction.setText(AppConstants.FAILED_CONSECUTIVE(error));
            } else {
                txtInstruction.setText(AppConstants.FAILED(error));
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SubtractingDissimilarExerciseActivity.this,
                            SubtractingDissimilarVideoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 4000);
        } else {
            txtInstruction.setText(AppConstants.ERROR);
            inputNum.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionAnswer().getNumerator()));
            inputDenom.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionAnswer().getDenominator()));
            Styles.paintRed(inputNum);
            Styles.paintRed(inputDenom);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (correctsShouldBeConsecutive) {
                        go();
                        Styles.paintBlack(inputNum);
                        Styles.paintBlack(inputDenom);
                    } else {
                        addQuestion();
                        nextQuestion();
                    }
                }
            }, 3000);
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
    public void setQuestions() {
        fractionQuestions = new ArrayList<>();
        questionNum = 0;
        for (int i = 0; i < requiredCorrects; i++){
            addQuestion();
        }
    }
    public void addQuestion(){
        fractionQuestion = new FractionQuestion(FractionQuestion.SUBTRACTING_DISSIMILAR);
        fractionQuestions.add(fractionQuestion);
    }
    public void setFractionGui(){
        txtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getNumerator()));
        txtNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getNumerator()));
        txtDenom1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
        txtDenom2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
    }
    public void startUp(){
        txtNum3.setVisibility(TextView.INVISIBLE);
        txtNum4.setVisibility(TextView.INVISIBLE);
        setFractionSet2DenomVisibility(false);
        txtEquation1.setVisibility(TextView.INVISIBLE);
        txtEquation2.setVisibility(TextView.INVISIBLE);
        inputNum.setText("");
        inputDenom.setText("");
        inputNum.setEnabled(false);
        inputDenom.setEnabled(false);
        btnCheck.setEnabled(false);
        setLcmListeners(true);
        txtInstruction.setText("Get the lcd of the two denominators by clicking them.");
    }
    public void resetColors(){
        txtDenom1.setTextColor(defaultColor);
        txtDenom2.setTextColor(defaultColor);
        txtDenom3.setTextColor(defaultColor);
        txtDenom4.setTextColor(defaultColor);
        txtNum1.setTextColor(defaultColor);
        txtNum2.setTextColor(defaultColor);
        txtEquation1.setTextColor(defaultColor);
        txtEquation2.setTextColor(defaultColor);
    }
    public void setFractionSet2DenomVisibility(boolean b){
        if (b){
            txtDenom3.setVisibility(TextView.VISIBLE);
            txtDenom4.setVisibility(TextView.VISIBLE);
        } else {
            txtDenom3.setVisibility(TextView.INVISIBLE);
            txtDenom4.setVisibility(TextView.INVISIBLE);
        }
    }
    public void setLcmListeners(boolean bool){
        if (bool){
            txtDenom1.setOnClickListener(new TxtLcmListener());
            txtDenom2.setOnClickListener(new TxtLcmListener());
        } else {
            txtDenom1.setOnClickListener(null);
            txtDenom2.setOnClickListener(null);
        }
        txtDenom1.setClickable(bool);
        txtDenom2.setClickable(bool);
    }
    public void setDivisionListeners(boolean b){
        if (b){
            txtDenom1.setOnClickListener(new TxtDivisionListener());
            txtDenom2.setOnClickListener(new TxtDivisionListener());
            txtDenom3.setOnClickListener(new TxtDivisionListener());
            txtDenom4.setOnClickListener(new TxtDivisionListener());
        } else {
            txtDenom1.setOnClickListener(null);
            txtDenom2.setOnClickListener(null);
            txtDenom3.setOnClickListener(null);
            txtDenom4.setOnClickListener(null);
        }
        txtDenom1.setClickable(b);
        txtDenom2.setClickable(b);
        txtDenom3.setClickable(b);
        txtDenom4.setClickable(b);
    }
    public void setMultiplicationListeners(boolean b){
        if (b){
            txtEquation1.setOnClickListener(new TxtMultiplicationListener());
            txtEquation2.setOnClickListener(new TxtMultiplicationListener());
            txtNum1.setOnClickListener(new TxtMultiplicationListener());
            txtNum2.setOnClickListener(new TxtMultiplicationListener());
        } else {
            txtEquation1.setOnClickListener(null);
            txtEquation2.setOnClickListener(null);
            txtNum1.setOnClickListener(null);
            txtNum2.setOnClickListener(null);
        }
        txtEquation1.setClickable(b);
        txtEquation2.setClickable(b);
        txtNum1.setClickable(b);
        txtNum2.setClickable(b);
    }
    public void removeListeners(View v1, View v2){
        v1.setOnClickListener(null);
        v2.setOnClickListener(null);
        v1.setClickable(false);
        v2.setClickable(false);
    }
    public void popUpLcmDialog(){
        diagLcmtxtNum1.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()));
        diagLcmtxtNum2.setText(String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()));
        diagLcmtxtNum3.setText("");
        lcmDialog.show();
    }
    private void popUpEquationDialog(String t1, String t2, String sign){
        diagEdTxtNum1.setText(t1);
        diagEdTxtNum2.setText(t2);
        diagEdTxtSign.setText(sign);
        equationDialog.show();
    }
    public class TxtLcmListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            viewIds.add(t.getId());
            if (viewIds.size()==1){
                if(viewIds.get(0)==txtDenom1.getId() || viewIds.get(0)==txtDenom2.getId()) {
                    Styles.paintGreen(t);
                }
            }
            if (viewIds.size()==2){
                if(viewIds.get(0)==txtDenom1.getId() && viewIds.get(1)==txtDenom2.getId()
                        || viewIds.get(0)==txtDenom2.getId() && viewIds.get(1)==txtDenom1.getId()) {
                    Styles.paintGreen(t);
                    popUpLcmDialog();
                } else {
                    viewIds.remove(viewIds.size() - 1);
                }
            }
        }
    }
    private class TxtDivisionListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            viewIds.add(t.getId());
            if (viewIds.size()==1){
                if(viewIds.get(0)==txtDenom3.getId() || viewIds.get(0)==txtDenom4.getId()) {
                    Styles.paintGreen(t);
                } else {
                    viewIds.clear();
                }
            }
            if (viewIds.size()==2){
                if(viewIds.get(0)==txtDenom3.getId() && viewIds.get(1)==txtDenom1.getId()
                        || viewIds.get(0)==txtDenom4.getId() && viewIds.get(1)==txtDenom2.getId()) {
                    Styles.paintGreen(t);
                    if (viewIds.get(1)==txtDenom1.getId()){
                        popUpEquationDialog(String.valueOf(fractionQuestions.get(questionNum).getFractionAnswer().getDenominator()),
                                String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getDenominator()),
                                "÷");
                    }
                    if (viewIds.get(1)==txtDenom2.getId()){
                        popUpEquationDialog(String.valueOf(fractionQuestions.get(questionNum).getFractionAnswer().getDenominator()),
                                String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getDenominator()),
                                "÷");
                    }
                } else {
                    viewIds.remove(viewIds.size() - 1);
                }
            }
        }
    }
    private class TxtMultiplicationListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            viewIds.add(t.getId());
            if (viewIds.size()==1){
                if(viewIds.get(0)==txtEquation1.getId() || viewIds.get(0)==txtEquation2.getId()) {
                    Styles.paintGreen(t);
                } else {
                    viewIds.clear();
                }
            }
            if (viewIds.size()==2){
                if(viewIds.get(0)==txtEquation1.getId() && viewIds.get(1)==txtNum1.getId()
                        || viewIds.get(0)==txtEquation2.getId() && viewIds.get(1)==txtNum2.getId()) {
                    Styles.paintGreen(t);
                    if (viewIds.get(1)==txtNum1.getId()){
                        popUpEquationDialog(String.valueOf(txtEquation1.getText()),
                                String.valueOf(fractionQuestions.get(questionNum).getFractionOne().getNumerator()),
                                "x");
                    }
                    if (viewIds.get(1)==txtNum2.getId()){
                        popUpEquationDialog(String.valueOf(txtEquation2.getText()),
                                String.valueOf(fractionQuestions.get(questionNum).getFractionTwo().getNumerator()),
                                "x");
                    }
                } else {
                    viewIds.remove(viewIds.size() - 1);
                }
            }
        }
    }
    private class DiagLcmBtnCheckListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (Integer.valueOf(String.valueOf(diagLcmInputLcm.getText())).equals(fractionQuestions.get(questionNum)
                    .getFractionAnswer().getDenominator())){
                txtDenom3.setText(""+fractionQuestions.get(questionNum).getFractionAnswer().getDenominator());
                txtDenom4.setText(""+fractionQuestions.get(questionNum).getFractionAnswer().getDenominator());
                setLcmListeners(false);
                setFractionSet2DenomVisibility(true);
                setDivisionListeners(true);
                lcmDialog.dismiss();
                txtInstruction.setText("Divide a new denominator to its' corresponding denominator by clicking the" +
                        " new denominator first.");
            } else {
                Styles.shakeAnimate(diagLcmInputLcm);
            }
        }
    }
    private class DialogListener implements DialogInterface.OnDismissListener, DialogInterface.OnShowListener {
        @Override
        public void onDismiss(DialogInterface dialog) {
            resetColors();
            viewIds.clear();
            diagEdInputAnswer.setText("");
            diagLcmInputLcm.setText("");
            if (dialog.equals(equationDialog)){
                if(diagEdTxtSign.getText().toString().matches("x")){
                    if (txtNum3.getVisibility()==TextView.VISIBLE &&
                            txtNum4.getVisibility()==TextView.VISIBLE) {
                        if (questionNum>0) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                        }
                    }
                }
            }
        }

        @Override
        public void onShow(DialogInterface dialog) {
            if (dialog.equals(lcmDialog)) {
                diagLcmInputLcm.requestFocus();
            } else if (dialog.equals(equationDialog)){
                diagEdInputAnswer.requestFocus();
            }
            if (questionNum>0) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
        }
    }
    private class DiagEdBtnCheckListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!diagEdInputAnswer.getText().toString().matches("")){
                if (String.valueOf(diagEdTxtSign.getText())=="÷"){
                    if (Integer.valueOf(String.valueOf(diagEdTxtNum1.getText().toString())) /
                            Integer.valueOf(String.valueOf(diagEdTxtNum2.getText())) ==
                            Integer.valueOf(String.valueOf(diagEdInputAnswer.getText()))){
                        if (viewIds.get(0)==txtDenom3.getId()){
                            removeListeners(txtDenom1,txtDenom3);
                            txtEquation1.setText(String.valueOf(diagEdInputAnswer.getText()));
                            txtEquation1.setVisibility(TextView.VISIBLE);
                            equationDialog.dismiss();
                        } else if (viewIds.get(0)==txtDenom4.getId()){
                            removeListeners(txtDenom2,txtDenom4);
                            txtEquation2.setText(String.valueOf(diagEdInputAnswer.getText()));
                            txtEquation2.setVisibility(TextView.VISIBLE);
                            equationDialog.dismiss();
                        }
                        txtInstruction.setText("Divide the other two also.");
                        if (txtEquation1.getVisibility()==TextView.VISIBLE &&
                                txtEquation2.getVisibility()==TextView.VISIBLE){
                            setMultiplicationListeners(true);
                            txtInstruction.setText("Multiply a quotient to its' corresponding numerator by" +
                                    " clicking the quotient first.");
                        }
                    } else {
                        Styles.shakeAnimate(diagEdInputAnswer);
                    }
                } else if (String.valueOf(diagEdTxtSign.getText())=="x"){
                    if (Integer.valueOf(String.valueOf(diagEdTxtNum1.getText())) *
                            Integer.valueOf(String.valueOf(diagEdTxtNum2.getText())) ==
                            Integer.valueOf(String.valueOf(diagEdInputAnswer.getText()))){
                        if (viewIds.get(0)==txtEquation1.getId()){
                            removeListeners(txtEquation1,txtNum3);
                            txtEquation1.setText(String.valueOf(txtEquation1.getText())+"x"+
                                    String.valueOf(diagEdTxtNum2.getText())+"=");
                            txtNum3.setText(String.valueOf(diagEdInputAnswer.getText()));
                            txtNum3.setVisibility(TextView.VISIBLE);
                            equationDialog.dismiss();
                        } else if (viewIds.get(0)==txtEquation2.getId()){
                            removeListeners(txtEquation2,txtNum4);
                            txtEquation2.setText(String.valueOf(txtEquation2.getText())+"x"+
                                    String.valueOf(diagEdTxtNum2.getText())+"=");
                            txtNum4.setText(String.valueOf(diagEdInputAnswer.getText()));
                            txtNum4.setVisibility(TextView.VISIBLE);
                            equationDialog.dismiss();
                        }
                        txtInstruction.setText("Multiply the other two also.");
                        if (txtNum3.getVisibility()==TextView.VISIBLE &&
                                txtNum4.getVisibility()==TextView.VISIBLE){
                            inputNum.setEnabled(true);
                            inputDenom.setEnabled(true);
                            inputNum.requestFocus();
                            btnCheck.setEnabled(true);
                            txtInstruction.setText("Subtract the numerators and copy the denominators.");
                        }
                    } else {
                        Styles.shakeAnimate(diagEdInputAnswer);
                    }
                }
            } else {
                Styles.shakeAnimate(diagEdInputAnswer);
            }
        }
    }
    private class BtnCheckListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!inputNum.getText().toString().matches("")) {
                if (!inputDenom.getText().toString().matches("")) {
                    if (String.valueOf(inputNum.getText()).matches(String.valueOf(fractionQuestions.get(questionNum)
                            .getFractionAnswer().getNumerator())) &&
                            String.valueOf(inputDenom.getText()).matches(String.valueOf(fractionQuestions.get(questionNum)
                                    .getFractionAnswer().getDenominator()))) {
                        correct();
                    } else {
                        wrong();
                    }
                } else {
                    Styles.shakeAnimate(inputDenom);
                }
            } else {
                Styles.shakeAnimate(inputNum);
            }
        }
    }
    private class InputListener implements TextView.OnEditorActionListener{
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){
                if (v.getId()==diagLcmInputLcm.getId()){
                    diagLcmBtnCheck.performClick();
                } else if (v.getId()==diagEdInputAnswer.getId()){
                    diagEdBtnCheck.performClick();
                } else if (v.getId()==inputDenom.getId()){
                    btnCheck.performClick();
                }
            }
            return false;
        }
    }
    private void setClickAreas(){
        setLargerClickArea(txtNum1);
        setLargerClickArea(txtNum2);
        setLargerClickArea(txtNum3);
        setLargerClickArea(txtNum4);
        setLargerClickArea(txtDenom1);
        setLargerClickArea(txtDenom2);
        setLargerClickArea(txtDenom3);
        setLargerClickArea(txtDenom4);
        setLargerClickArea(txtEquation1);
        setLargerClickArea(txtEquation2);
    }

    private void setLargerClickArea(final TextView textView){
        final View parent = (View) textView.getParent();  // button: the view you want to enlarge hit area
        parent.post( new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                textView.getHitRect(rect);
                rect.top -= 100;    // increase top hit area
                rect.left -= 100;   // increase left hit area
                rect.bottom += 100; // increase bottom hit area
                rect.right += 100;  // increase right hit area
                parent.setTouchDelegate( new TouchDelegate( rect , textView));
            }
        });
    }
}
