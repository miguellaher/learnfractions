package com.example.laher.learnfractions.parent_activities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.dialog_layout.ConfirmationDialog;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.model.User;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.ExerciseStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.AppCache;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

public class LessonExercise extends AppCompatActivity {
    Context context;
    //TOOLBAR
    protected Button buttonBack;
    private Button buttonNext;
    private TextView txtTitle;
    //IMPORTANT DETAILS
    private String id;
    private String exerciseTitle;
    //STATISTIC
    private Student student;
    private User user;
    private int correct;
    private int wrong;
    private int totalWrongs;
    private long startingTime;
    private long endingTime;
    private long timeSpent;
    //GUIDELINES
    private boolean correctsShouldBeConsecutive;
    private boolean wrongsShouldBeConsecutive;
    private int itemsSize;
    private int maxWrong;
    private Range range;
    private Probability probability;
    private int maxItemSize;
    protected final Handler handler = new Handler();
    //SETTINGS
    private boolean rangeEditable;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Probability getProbability() {
        return probability;
    }

    public void setProbability(Probability probability) {
        this.probability = probability;
        int outComes = probability.getOutComes();
        setMaxItemSize(outComes);
    }

    public int getMaxItemSize() {
        return maxItemSize;
    }

    public void setMaxItemSize(int maxItemSize) {
        this.maxItemSize = maxItemSize;
        int itemSize = getItemsSize();
        setItemsSize(itemSize);
    }

    public boolean isRangeEditable() {
        return rangeEditable;
    }

    public void setRangeEditable(boolean rangeEditable) {
        this.rangeEditable = rangeEditable;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public void setTotalWrongs(int totalWrongs) {
        this.totalWrongs = totalWrongs;
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
        Log.d(tag, "items size: " + itemsSize);
        if (itemsSize>maxItemSize && maxItemSize!=0){
            itemsSize = maxItemSize;
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
        buttonBack = findViewById(R.id.btnBack);
        buttonBack.setOnClickListener(new ButtonBackListener());

        if (Util.randomBoolean()){
            Styles.bgPaintMainBlue(buttonBack);
        } else {
            Styles.bgPaintMainYellow(buttonBack);
        }

        buttonNext = findViewById(R.id.btnNext);
        buttonNext.setEnabled(false);
        buttonNext.setOnClickListener(new ButtonNextListener());

        if (Util.randomBoolean()){
            Styles.bgPaintMainOrange(buttonNext);
        } else {
            Styles.bgPaintMainBlueGreen(buttonNext);
        }

        txtTitle = findViewById(R.id.txtTitle);
        defaultAttributes();

        String userType = Storage.load(context,Storage.USER_TYPE);

        if (!Storage.isEmpty()&&isNetworkAvailable()
                && userType.equals(AppConstants.STUDENT)) {
            updateExercise();
        }
    }

    protected void setToolBarBackground(int resourceID){
        ConstraintLayout toolbar = findViewById(R.id.constraintLayoutToolbar);
        toolbar.setBackgroundResource(resourceID);
    }

    protected void defaultAttributes(){
        wrong = 0;
        correct = 0;
        totalWrongs = 0;
        LessonExercise lessonExercise = new LessonExercise();
        String exerciseTitle = getExerciseTitle();
        lessonExercise.setExerciseTitle(exerciseTitle);
        setAttributes(lessonExercise);
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
        int maxItemSize = lessonExercise.getMaxItemSize();
        int requiredCorrects = lessonExercise.getItemsSize();
        int maxErrors = lessonExercise.getMaxWrong();
        boolean correctsShouldBeConsecutive = lessonExercise.isCorrectsShouldBeConsecutive();
        boolean errorsShouldBeConsecutive = lessonExercise.isWrongsShouldBeConsecutive();
        Range range = lessonExercise.getRange();
        try {
            setTxtTitle(title); // if there is a toolbar
        } catch (Exception e){
            setExerciseTitle(title);
        }
        setMaxItemSize(maxItemSize);
        setItemsSize(requiredCorrects);
        setMaxWrong(maxErrors);
        setCorrectsShouldBeConsecutive(correctsShouldBeConsecutive);
        setWrongsShouldBeConsecutive(errorsShouldBeConsecutive);
        setRange(range);
        String tag = "Lesson Exercise Class";
        Log.d(tag, "setAttributes()");
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
                    String strMaxItemsSize = response.optString("max_items");
                    String strMaxWrong = response.optString("max_wrong");
                    String strMEC = response.optString("me_consecutive");
                    String strMinimum = response.optString("r_minimum");
                    String strMaximum = response.optString("r_maximum");

                    String tag = "lesson_exercise";
                    Log.d(tag, title);
                    Log.d(tag, strItemsSize);
                    Log.d(tag, strMaxItemsSize);
                    Log.d(tag, strRCC);
                    Log.d(tag, strMaxWrong);
                    Log.d(tag, strMEC);
                    Log.d(tag, strMinimum);
                    Log.d(tag, strMaximum);

                    if (Util.isNumeric(strItemsSize) && Util.isNumeric(strMaxWrong)
                            && Util.isNumeric(strMinimum) && Util.isNumeric(strMaximum)
                            && Util.isNumeric(strMaxItemsSize)) {
                        int itemsSize = Integer.valueOf(strItemsSize);
                        int maxItemSize = Integer.valueOf(strMaxItemsSize);
                        int maxWrong = Integer.valueOf(strMaxWrong);
                        boolean isCorrectsShouldBeConsecutive = false;
                        if (strRCC.equals("1")){
                            isCorrectsShouldBeConsecutive = true;
                        }
                        boolean isWrongsShouldBeConsecutive = true;
                        if (strMEC.equals("0")){
                            isWrongsShouldBeConsecutive = false;
                        }
                        int minimum = Integer.valueOf(strMinimum);
                        int maximum = Integer.valueOf(strMaximum);
                        Range range = new Range(minimum,maximum);

                        LessonExercise exercise = new LessonExercise();
                        exercise.setExerciseTitle(title);
                        exercise.setMaxItemSize(maxItemSize);
                        exercise.setItemsSize(itemsSize);
                        exercise.setMaxWrong(maxWrong);
                        exercise.setCorrectsShouldBeConsecutive(isCorrectsShouldBeConsecutive);
                        exercise.setWrongsShouldBeConsecutive(isWrongsShouldBeConsecutive);
                        exercise.setRange(range);
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

        String userType = Storage.load(context, Storage.USER_TYPE);

        if (userType.equals(AppConstants.STUDENT)) {
            Student student = new Student();
            String student_id = Storage.load(context, Storage.STUDENT_ID);
            String teacher_code = Storage.load(context, Storage.TEACHER_CODE);
            student.setId(student_id);
            student.setTeacher_code(teacher_code);
            setStudent(student);
        } else if (userType.equals(AppConstants.USER)){
            User user = new User();
            String user_id = Storage.load(context, Storage.USER_ID);
            String user_code = AppConstants.USER_CODE;

            user.setId(user_id);
            user.setCode(user_code);

            setUser(user);
        }

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
        Range range = new Range(1,9);
        setRange(range);
        setItemsSize(5);
        setMaxWrong(3);
        setCorrectsShouldBeConsecutive(false);
        setWrongsShouldBeConsecutive(true);
        setExerciseTitle("EXERCISE"); //Default
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LessonExercise){
            LessonExercise exercise = (LessonExercise) obj;
            String thisId = this.getId().trim();
            String objId = exercise.getId().trim();
            return thisId.equals(objId);
        }
        return super.equals(obj);
    }

    public boolean hasTheSameAttributesWith(LessonExercise lessonExercise){
        boolean hasTheSameAttributes = true;

        int thisItemsSize = this.getItemsSize();
        int itemsSize = lessonExercise.getItemsSize();
        if (thisItemsSize!=itemsSize){
            hasTheSameAttributes = false;
        }

        boolean thisCorrectsShouldBeConsecutive = this.isCorrectsShouldBeConsecutive();
        boolean correctsShouldBeConsecutive = lessonExercise.isCorrectsShouldBeConsecutive();
        if (thisCorrectsShouldBeConsecutive!=correctsShouldBeConsecutive){
            hasTheSameAttributes = false;
        }

        int thisMaxWrong = this.getMaxWrong();
        int maxWrong = lessonExercise.getMaxWrong();
        if (thisMaxWrong!=maxWrong){
            hasTheSameAttributes = false;
        }

        boolean thisWrongsShouldBeConsecutive = this.isWrongsShouldBeConsecutive();
        boolean wrongsShouldBeConsecutive = lessonExercise.isWrongsShouldBeConsecutive();
        if (thisWrongsShouldBeConsecutive!=wrongsShouldBeConsecutive){
            hasTheSameAttributes = false;
        }

        Range thisRange = this.getRange();
        Range range = lessonExercise.getRange();
        if(!thisRange.equals(range)){
            hasTheSameAttributes = false;
        }

        return hasTheSameAttributes;
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
