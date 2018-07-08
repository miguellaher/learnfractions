package com.example.laher.learnfractions.model;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.laher.learnfractions.util.AppConstants;

public class SeatWork extends AppCompatActivity {
    private String topicName;
    private int seatWorkNum;
    private int items_size;
    private int correct;
    private int wrong;
    private long timeSpent;
    private int currentItemNum;

    public SeatWork(String topicName, int seatWorkNum) {
        super();
        this.topicName = topicName;
        this.seatWorkNum = seatWorkNum;
        setItems_size(AppConstants.DEFAULT_ITEMS_NUM);
    }

    public SeatWork() {
        setCurrentItemNum(AppConstants.STARTING_NUM);
        setItems_size(AppConstants.DEFAULT_ITEMS_NUM);
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
        this.items_size = items_size;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
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

    public void incrementWrong(){
        this.wrong++;
    }

    public void incrementItemNum(){
        this.currentItemNum++;
    }

    public void updateItemIndicator(TextView txtItemIndicator){
        txtItemIndicator.setText(AppConstants.ITEM(getCurrentItemNum(), getItems_size()));
    }
}
