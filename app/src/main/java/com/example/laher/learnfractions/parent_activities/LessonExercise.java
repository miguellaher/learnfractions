package com.example.laher.learnfractions.parent_activities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.dialog_layout.ConfirmationDialog;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.ExerciseStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.AppCache;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

public class LessonExercise extends AppCompatActivity {
    Context context;
    //TOOLBAR
    private Button buttonBack, buttonNext;
    private TextView txtTitle;
    //IMPORTANT DETAILS
    private String id;
    private String exerciseTitle;
    //STATISTIC
    private Student student;
    private int correct;
    private int wrong;
    private int totalWrongs;
    private long startingTime;
    private long endingTime;
    private long timeSpent;
    //GUIDELINES
    private boolean correctsShouldBeConsecutive;
    private boolean wrongsShouldBeConsecutive;
    private int rangeMinimum;
    private int rangeMaximum;
    private int range;
    private int itemsSize;
    private int maxItemsSize;
    private int maxWrong;
    protected final Handler handler = new Handler();

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setRange(int minimum, int maximum){
        if (maximum>minimum && maximum>=0 && minimum>=0){
            this.rangeMinimum = minimum;
            this.rangeMaximum = maximum;
            int difference = maximum - maximum;
            this.range = difference + 1;
        }
    }

    public int getMaxItemsSize() {
        return maxItemsSize;
    }

    /*public void setMaxItemsSize(int maxItemsSize) {
        this.maxItemsSize = maxItemsSize;
    }*/

    protected void setMaxItemsSize(){

    }

    public int getTotalWrongs() {
        return totalWrongs;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public String getExerciseTitle() {
        return exerciseTitle;
    }

    public void setExerciseTitle(String exerciseTitle) {
        this.exerciseTitle = exerciseTitle;
    }

    public Button getButtonNext() {
        return buttonNext;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public TextView getTxtTitle() {
        return txtTitle;
    }

    public int getItemsSize() {
        return itemsSize;
    }

    public void setItemsSize(int itemsSize) {
        String tag = "Lesson Exercise Class";
        Log.d(tag, "setItemsSize()");
        Log.d(tag, "max items size: " + getMaxItemsSize());
        Log.d(tag, "items size: " + itemsSize);
        int maxItemsSize = getMaxItemsSize();
        if (itemsSize>maxItemsSize && maxItemsSize!=0){
            itemsSize = maxItemsSize;
        }
        this.itemsSize = itemsSize;
    }

    public int getMaxWrong() {
        return maxWrong;
    }

    public void setMaxWrong(int maxWrong) {
        this.maxWrong = maxWrong;
    }

    public boolean isCorrectsShouldBeConsecutive() {
        return correctsShouldBeConsecutive;
    }

    public void setCorrectsShouldBeConsecutive(boolean correctsShouldBeConsecutive) {
        this.correctsShouldBeConsecutive = correctsShouldBeConsecutive;
    }

    public boolean isWrongsShouldBeConsecutive() {
        return wrongsShouldBeConsecutive;
    }

    public void setWrongsShouldBeConsecutive(boolean wrongsShouldBeConsecutive) {
        this.wrongsShouldBeConsecutive = wrongsShouldBeConsecutive;
    }

    public long getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(long startingTime) {
        this.startingTime = startingTime;
    }

    public long getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(long endingTime) {
        this.endingTime = endingTime;
    }

    public void setTxtTitle(String title){
        txtTitle.setText(title);
        setExerciseTitle(title);
    }

    protected void setToolBarGui(){
        String tag = "Lesson Exercise Class";
        Log.d(tag, "setToolBarGui()");
        Log.d(tag, "max items size: " + getMaxItemsSize());
        buttonBack = findViewById(R.id.btnBack);
        buttonBack.setOnClickListener(new ButtonBackListener());
        buttonNext = findViewById(R.id.btnNext);
        buttonNext.setEnabled(false);
        buttonNext.setOnClickListener(new ButtonNextListener());
        txtTitle = findViewById(R.id.txtTitle);
        defaultAttributes();
    }

    protected void defaultAttributes(){
        wrong = 0;
        correct = 0;
        totalWrongs = 0;
        LessonExercise lessonExercise = new LessonExercise();
        String exerciseTitle = getExerciseTitle();
        lessonExercise.setExerciseTitle(exerciseTitle);
        setAttributes(lessonExercise);
        if (!Storage.isEmpty()&&isNetworkAvailable()) {
            updateExercise();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContext(this);
        super.onCreate(savedInstanceState);
        setToolBarGui();
        String title = getExerciseTitle();
        setTxtTitle(title);
    }

    protected void startExercise(){
        setStartingTime(System.currentTimeMillis());
        showScore();
    }

    protected void finishExercise(){
        preFinished();
        buttonNext.setEnabled(true);
    }

    public void setAttributes(LessonExercise lessonExercise){
        String title = lessonExercise.getExerciseTitle();
        int range = lessonExercise.getRange();
        int requiredCorrects = lessonExercise.getItemsSize();
        int maxErrors = lessonExercise.getMaxWrong();
        boolean correctsShouldBeConsecutive = lessonExercise.isCorrectsShouldBeConsecutive();
        boolean errorsShouldBeConsecutive = lessonExercise.isWrongsShouldBeConsecutive();
        setRange(range);
        setTxtTitle(title);
        setItemsSize(requiredCorrects);
        setMaxWrong(maxErrors);
        setCorrectsShouldBeConsecutive(correctsShouldBeConsecutive);
        setWrongsShouldBeConsecutive(errorsShouldBeConsecutive);
        String tag = "Lesson Exercise Class";
        Log.d(tag, "setAttributes()");
        Log.d(tag, "max items size: " + getMaxItemsSize());
    }

    public void incrementCorrect(){
        this.correct++;
    }

    public void incrementError(){
        this.wrong++;
        this.totalWrongs++;
    }

    public void updateExercise(){
        Service service = new Service("Getting update...", context, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    String title = response.optString("title");
                    String strItemsSize = response.optString("items_size");
                    String strRCC = response.optString("rc_consecutive");
                    String strMaxWrong = response.optString("max_wrong");
                    String strMEC = response.optString("me_consecutive");

                    String tag = "lesson_exercise";
                    Log.d(tag, title);
                    Log.d(tag, strItemsSize);
                    Log.d(tag, strRCC);
                    Log.d(tag, strMaxWrong);
                    Log.d(tag, strMEC);

                    if (Util.isNumeric(strItemsSize) && Util.isNumeric(strMaxWrong)) {
                        int itemsSize = Integer.valueOf(strItemsSize);
                        int maxWrong = Integer.valueOf(strMaxWrong);
                        boolean isCorrectsShouldBeConsecutive = false;
                        if (strRCC.equals("1")){
                            isCorrectsShouldBeConsecutive = true;
                        }
                        boolean isWrongsShouldBeConsecutive = true;
                        if (strMEC.equals("0")){
                            isWrongsShouldBeConsecutive = false;
                        }

                        LessonExercise exercise = new LessonExercise();
                        exercise.setExerciseTitle(title);
                        exercise.setItemsSize(itemsSize);
                        exercise.setMaxWrong(maxWrong);
                        exercise.setCorrectsShouldBeConsecutive(isCorrectsShouldBeConsecutive);
                        exercise.setWrongsShouldBeConsecutive(isWrongsShouldBeConsecutive);
                        setAttributes(exercise);
                    }
                    startExercise();
                } catch (Exception e) {
                    e.printStackTrace();
                    startExercise();
                }
            }
        });

        ExerciseService.getUpdate(context, this, service);
    }

    public void postExerciseData(){
        long startingTime = getStartingTime();
        long endingTime = getEndingTime();
        long timeSpent = endingTime - startingTime;
        setTimeSpent(timeSpent);
        Student student = new Student();
        String student_id = Storage.load(context,Storage.STUDENT_ID);
        String teacher_code = Storage.load(context,Storage.TEACHER_CODE);
        student.setId(student_id);
        student.setTeacher_code(teacher_code);
        setStudent(student);

        Service service = new Service("Posting data...", context, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    String message = response.optString("message");
                    Util.toast(context, message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ExerciseStatService.post(this, service);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public LessonExercise() {
        //DEFAULT
        setRange(1,9);
        setItemsSize(3);
        setMaxWrong(3);
        setCorrectsShouldBeConsecutive(false);
        setWrongsShouldBeConsecutive(true);
        setExerciseTitle("EXERCISE"); //Default
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LessonExercise){
            LessonExercise exercise = (LessonExercise) obj;
            String thisId = this.getId();
            String objId = exercise.getId();
            return thisId.equals(objId);
        }
        return super.equals(obj);
    }

    private class ButtonBackListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            final ConfirmationDialog confirmationDialog = new ConfirmationDialog(context,"Are you sure you want to go back?");
            confirmationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (confirmationDialog.isConfirmed()){
                        AppCache.setBackClicked(true);
                        finish();
                    }
                }
            });
            confirmationDialog.show();
        }
    }

    private class ButtonNextListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            AppCache.setNextClicked(true);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        buttonBack.performClick();
    }


    protected void correct(){
        preAnswered();
        preCorrect();
        boolean errorsShouldBeConsecutive = isWrongsShouldBeConsecutive();
        incrementCorrect();
        int correct = getCorrect();
        int itemsSize = getItemsSize();
        showScore();
        if (errorsShouldBeConsecutive) {
            setWrong(0);
        }
        if (correct >= itemsSize) {
            setEndingTime(System.currentTimeMillis());
            if (!Storage.isEmpty()&&isNetworkAvailable()) {
                postExerciseData();
            }
            finishExercise(); //exercise done
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    postAnswered();
                    postCorrect();
                }
            }, 2000);
        }
    }

    protected void wrong(){
        preAnswered();
        preWrong();
        if (correctsShouldBeConsecutive) {
            setCorrect(0);
        }
        incrementError();
        int wrong = getWrong();
        int maxWrong = getMaxWrong();
        showScore();
        if (wrong >= maxWrong) {
            preFail();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AppCache.setBackClicked(true); //exercise failed
                    finish();
                }
            }, 3000);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    postAnswered();
                    postWrong();
                }
            }, 2000);
        }
    }

    protected void preCorrect(){    // before checking if you finished the exercise
                                    // or just got a correct answer and will continue
    }

    protected void postCorrect(){   // after processing your correct, what will happen before answering the next question?
                                    // put it here
    }

    protected void preFinished(){   // "celebration"
                                    // before finishing exercise
    }

    protected void showScore(){ // set gui to show score (ex. setTxtScore("..."))

    }

    protected void preWrong(){  // before checking if you exceed the max wrong and fail the exercise
                                // or just got a wrong answer and continue
    }

    protected void postWrong(){ // you failed a question. what will happen before answering a new one?
                                // put it here
    }

    protected void preFail(){   // "any last words"
                                // method before failing the exercise
        boolean errorsShouldBeConsecutive = isWrongsShouldBeConsecutive();
        if (errorsShouldBeConsecutive) {
            preFailWrongsAreConsecutive();
        } else {
            preFailWrongsAreNotConsecutive();
        }
    }

    protected void preFailWrongsAreConsecutive(){ // if wrongs are consecutive

    }

    protected void preFailWrongsAreNotConsecutive(){ // if wrongs are not consecutive

    }

    protected void preAnswered(){   // restrictions after answering
                                    // for bug avoidance
    }

    protected void postAnswered(){ // remove restriction

    }
}
