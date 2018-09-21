package com.example.laher.learnfractions.parent_activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.ChapterExamListActivity;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.SeatWorkListActivity;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.dialog_layout.ConfirmationDialog;
import com.example.laher.learnfractions.dialog_layout.SeatWorkStatDialog;
import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.util.ActivityUtil;
import com.example.laher.learnfractions.util.AppCache;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;
import java.util.Collections;

import pl.droidsonroids.gif.GifImageView;

public class SeatWork extends AppCompatActivity {
    private Context context;
    //TOOLBAR || GUI
    protected Button buttonBack;
    protected Button buttonNext;
    protected TextView txtTitle;
    protected GifImageView gifAvatar;
    //IMPORTANT DETAILS
    private String id;
    private int seatWorkNum; // REMEMBER TO REMOVE
    private int items_size;
    //STATISTIC
    private String topicName;
    private int correct;
    private long startingTime;
    private long timeSpent;
    private Student student;
    //GUIDELINES
    private int currentItemNum;
    private int maxItemsSize;
    private Probability probability;
    private Range range;
    //FOR CHECKING
    private boolean answered;
    //SETTINGS
    private boolean rangeEditable;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public boolean isRangeEditable() {
        return rangeEditable;
    }

    public void setRangeEditable(boolean rangeEditable) {
        this.rangeEditable = rangeEditable;
    }

    public Probability getProbability() {
        return probability;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public long getStartingTime() {
        return startingTime;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public void setStartingTime(long startingTime) {
        this.startingTime = startingTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProbability(Probability probability) {
        this.probability = probability;
        int outComes = probability.getOutComes();
        setMaxItemsSize(outComes);
    }

    public void setMaxItemsSize(int maxItemsSize) {
        if (maxItemsSize>0) {
            this.maxItemsSize = maxItemsSize;
            if (this.items_size>this.maxItemsSize) {
                setItems_size(maxItemsSize);
            }
        }
    }

    public SeatWork(String topicName, int seatWorkNum) {
        super();
        this.topicName = topicName;
        this.seatWorkNum = seatWorkNum;
        setItems_size(AppConstants.DEFAULT_ITEMS_NUM);
        Range range = new Range();
        setRange(range);
        setAnswered(false);
    }

    public SeatWork(String topicName) {
        this.topicName = topicName;
        setItems_size(AppConstants.DEFAULT_ITEMS_NUM);
        Range range = new Range();
        setRange(range);
        setAnswered(false);
    }

    public SeatWork() {
        setCurrentItemNum(AppConstants.STARTING_NUM);
        setItems_size(AppConstants.DEFAULT_ITEMS_NUM);
        setAnswered(false);
    }

    public SeatWork(int size){
        super();
        this.items_size = size;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public int getSeatWorkNum() {
        return seatWorkNum;
    }

    public void setSeatWorkNum(int seatWorkNum) {
        this.seatWorkNum = seatWorkNum;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getCurrentItemNum() {
        return currentItemNum;
    }

    public void setCurrentItemNum(int currentItemNum) {
        this.currentItemNum = currentItemNum;
    }

    public int getItems_size() {
        return items_size;
    }

    public void setItems_size(int items_size) {
        if (items_size>0) {
            if (items_size>maxItemsSize && maxItemsSize>0){
                items_size = maxItemsSize;
            }
            this.items_size = items_size;
        }
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public void incrementCorrect(){
        this.correct++;
    }

    public void incrementItemNum(){
        this.currentItemNum++;
    }

    public void updateItemIndicator(TextView txtItemIndicator){
        txtItemIndicator.setText(AppConstants.ITEM(getCurrentItemNum(), getItems_size()));
    }

    public void getValuesFrom(SeatWork seatWork){
        String topicName = seatWork.getTopicName();
        if (topicName!=null){
            this.setTopicName(topicName);
        }

        int itemSize = seatWork.getItems_size();
        if (itemSize!=0){
            this.setItems_size(itemSize);
        }

        Range range = seatWork.getRange();
        if (range!=null){
            this.setRange(range);
        }
    }

    public boolean hasEqualValuesWith(SeatWork seatWork){
        boolean hasEqualValues = true;

        int itemSize = seatWork.getItems_size();
        int thisItemSize = this.getItems_size();

        if (itemSize!=thisItemSize){
            hasEqualValues = false;
        }

        Range range = seatWork.getRange();
        Range thisRange = seatWork.getRange();
        if (!thisRange.equals(range)){
            hasEqualValues = false;
        }

        return hasEqualValues;
    }

    public boolean isUpdatedWith(SeatWork seatWork){
        if (this.equals(seatWork)){
            int itemsSize = getItems_size();
            Range range = getRange();

            int swItemsSize = seatWork.getItems_size();
            Range swRange = seatWork.getRange();

            return itemsSize==swItemsSize && range.equals(swRange);
        }
        return false;
    }

    public void getStatsFrom(SeatWork seatWork){
        int score = seatWork.getCorrect();
        long timeSpent = seatWork.getTimeSpent();
        Student student = seatWork.getStudent();

        if (student!=null){
            setStudent(student);
        }
        setCorrect(score);
        setTimeSpent(timeSpent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContext(this);
        super.onCreate(savedInstanceState);
        SeatWork seatWork = AppCache.getSeatWork();

        if (seatWork!=null) {
            String topicName = seatWork.getTopicName();
            int itemSize = seatWork.getItems_size();
            Range range = seatWork.getRange();

            setTopicName(topicName);
            setItems_size(itemSize);
            setRange(range);
        }

        setToolBarGui();
        setImgAvatar();
    }

    private void setImgAvatar() {
        gifAvatar = findViewById(R.id.gifAvatar);

        ArrayList<Integer> resourceIDs = getResourceIDs();
        Collections.shuffle(resourceIDs);
        int resourceID = resourceIDs.get(0);
        gifAvatar.setImageResource(resourceID);
    }

    private ArrayList<Integer> getResourceIDs(){
        // GIF
        int gentleFrits = R.drawable.gentle_frits;
        int kidFrits = R.drawable.kid_frits;
        int safariFrits = R.drawable.safari_frits;
        int summerFrits = R.drawable.summer_frits;

        ArrayList<Integer> resourceIDs = new ArrayList<>();
        resourceIDs.add(gentleFrits);
        resourceIDs.add(kidFrits);
        resourceIDs.add(safariFrits);
        resourceIDs.add(summerFrits);

        return resourceIDs;
    }

    private void setToolBarGui(){
        ConstraintLayout toolbar = findViewById(R.id.constraintLayoutToolbar);
        Styles.bgPaintMainYellow(toolbar);

        buttonBack = findViewById(R.id.btnBack);
        buttonBack.setOnClickListener(new ButtonBackListener());
        Styles.bgPaintMainBlue(buttonBack);

        buttonNext = findViewById(R.id.btnNext);
        buttonNext.setVisibility(View.INVISIBLE);
        txtTitle = findViewById(R.id.txtTitle);
        String title = getTopicName();
        if (AppCache.isInChapterExam()){
            ChapterExam chapterExam = AppCache.getChapterExam();
            String examTitle = chapterExam.getExamTitle();
            title = examTitle + ": " + title;
        }
        txtTitle.setText(title);
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
                        goBack();
                    }
                }
            });
            confirmationDialog.show();
        }
    }

    protected void go(){
        long startingTime = System.currentTimeMillis();
        setStartingTime(startingTime);
    }

    protected void seatworkFinished(){
        long endingTime = System.currentTimeMillis();
        long startingTime = getStartingTime();
        long totalTimeSpent = endingTime - startingTime;
        setTimeSpent(totalTimeSpent);

        SeatWorkStatDialog seatWorkStatDialog = new SeatWorkStatDialog(context, this);
        if (isNetworkAvailable()) {
            seatWorkStatDialog.show();

            if (AppCache.isInChapterExam()) {
                SeatWork seatWork = this;
                AppCache.setSeatWork(seatWork);
                seatWorkStatDialog.setOnDismissListener(new IfInChapterExamListener());
            } else {
                seatWorkStatDialog.setOnDismissListener(new DialogListener());
            }
        } else {
            finish();
        }
    }

    public void goBack(){
        boolean inChapterExam = AppCache.isInChapterExam();
        if (!inChapterExam) {
            Intent intent = new Intent(context, SeatWorkListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else { // currently in a chapter exam
            Intent intent = new Intent(context, ChapterExamListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SeatWork){
            SeatWork objSeatWork = (SeatWork) obj;
            String objId = objSeatWork.getId();
            String thisId = this.getId();
            if (thisId!=null && objId!=null) {
                return thisId.equals(objId);
            }
        }
        return super.equals(obj);
    }

    private class IfInChapterExamListener implements DialogInterface.OnDismissListener{
        @Override
        public void onDismiss(DialogInterface dialog) {
            finish();
        }
    }

    private class DialogListener implements DialogInterface.OnDismissListener{
        @Override
        public void onDismiss(DialogInterface dialog) {
            goBack();
        }
    }

    @Override
    public void onBackPressed() {
        buttonBack.performClick();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }



    @Override
    protected void onPause() {
        super.onPause();
        ActivityUtil.muteBgMusicMediaPlayer();
    }

    @Override
    protected void onResume() {
        ActivityUtil.unmuteBgMusicMediaPlayer();
        super.onResume();
    }

    @Override
    protected void onStart() {
        ActivityUtil.playBgMusicMediaPlayer(context);
        super.onStart();
    }

}
