package com.example.laher.learnfractions.parent_activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.SeatWorkListActivity;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.dialog_layout.ConfirmationDialog;
import com.example.laher.learnfractions.service.SeatWorkStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.AppCache;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

public class SeatWork extends AppCompatActivity {
    private Context context;
    //IMPORTANT DETAILS
    private String id;
    private int seatWorkNum; // REMEMBER TO REMOVE
    private int items_size;
    //STATISTIC
    private String topicName;
    private int correct;
    private long startingTime;
    private long endingTime;
    private long timeSpent;
    //GUIDELINES
    private int currentItemNum;
    private int maxItemsSize;
    private Probability probability;
    private Range range;
    //FOR CHECKING
    private boolean answered;

    public Probability getProbability() {
        return probability;
    }

    public long getEndingTime() {
        return endingTime;
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

    public void setEndingTime(long endingTime) {
        this.endingTime = endingTime;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContext(this);
        super.onCreate(savedInstanceState);
        setToolBarGui();
        SeatWork seatWork = AppCache.getSeatWork();

        String topicName = seatWork.getTopicName();
        int itemSize = seatWork.getItems_size();
        Range range = seatWork.getRange();

        setTopicName(topicName);
        setItems_size(itemSize);
        setRange(range);
    }

    private void setToolBarGui(){
        Button buttonBack = findViewById(R.id.btnBack);
        buttonBack.setOnClickListener(new ButtonBackListener());
        Button buttonNext = findViewById(R.id.btnNext);
        buttonNext.setVisibility(View.INVISIBLE);
        TextView txtTitle = findViewById(R.id.txtTitle);
        String title = getTopicName();
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
        setEndingTime(endingTime);
        long startingTime = getStartingTime();
        long totalTimeSpent = endingTime - startingTime;
        setTimeSpent(totalTimeSpent);

        Service service = new Service("Posting stats...", context, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                Util.toast(context, response.optString("message"));
                goBack();
            }
        });
        SeatWorkStatService.postStat(context, this, service);
    }

    public void goBack(){
        Intent intent = new Intent(context, SeatWorkListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SeatWork){
            SeatWork objSeatWork = (SeatWork) obj;
            String objId = objSeatWork.getId();
            String thisId = this.getId();
            return thisId.equals(objId);
        }
        return super.equals(obj);
    }
}
