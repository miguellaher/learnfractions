package com.example.laher.learnfractions.chapter_exam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.laher.learnfractions.model.SeatWork;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.service.ExamStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.AppCache;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class ChapterExam extends AppCompatActivity {
    private Context mContext = this;
    private final String TAG = "C_Exam";
    private ArrayList<SeatWork> seatWorks;
    private ArrayList<SeatWork> seatWorkStats;
    private ArrayList<Intent> intents;
    private String examTitle;
    private int totalScore;
    private int totalItems;
    private long timeSpent;
    private boolean answered;

    public ArrayList<SeatWork> getSeatWorkStats() {
        return seatWorkStats;
    }

    public ChapterExam() {
        seatWorks = new ArrayList<>();
        intents = new ArrayList<>();
    }

    public ChapterExam(String examTitle) {
        seatWorks = new ArrayList<>();
        intents = new ArrayList<>();
        this.examTitle = examTitle;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public ArrayList<SeatWork> getSeatWorks() {
        return seatWorks;
    }

    public void setSeatWorks(ArrayList<SeatWork> seatWorks) {
        this.seatWorks = seatWorks;
    }

    public void addSeatWork(SeatWork seatWork){
        seatWorks.add(seatWork);
        Log.d(TAG, "seat work added; size = " + seatWorks.size());
        Intent intent = new Intent(mContext, seatWork.getClass());
        intent.putExtra("item_size", seatWork.getItems_size());
        intent.putExtra("type", AppConstants.CHAPTER_EXAM);
        intents.add(intent);
        Log.d(TAG, "intent added; size = " + intents.size());
    }

    public void execute(){
        Log.d(TAG, "on exec; intents.size = " + intents.size());
        if (intents.size()>0){
            Intent intent = intents.get(0);
            intents.remove(0);
            Log.d(TAG, "pre startActivity()");
            startActivity(intent);
        } else {
            for (SeatWork seatWork : getSeatWorkStats()){
                int examScore = getTotalScore();
                examScore = examScore + seatWork.getCorrect();
                setTotalScore(examScore);
                int itemSize = getTotalItems();
                itemSize = itemSize + seatWork.getItems_size();
                setTotalItems(itemSize);
                long timeSpent = getTimeSpent();
                timeSpent = timeSpent + seatWork.getTimeSpent();
                setTimeSpent(timeSpent);
            }
            Log.d(TAG, "exam finished.");
            Service service = new Service("Posting stats...", mContext, new ServiceResponse() {
                @Override
                public void postExecute(JSONObject response) {
                    Util.toast(mContext, response.optString("message"));
                    Log.d(TAG, "post execute");
                    finish();
                }
            });
            Student student = new Student();
            student.setId(Storage.load(mContext, Storage.STUDENT_ID));
            student.setTeacher_code(Storage.load(mContext, Storage.TEACHER_CODE));
            ExamStatService.postStats(service, student, this);
        }
        Log.d(TAG, "post exec");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        ChapterExam chapterExam = AppCache.getChapterExam();
        setExamTitle(chapterExam.getExamTitle());

        for (SeatWork seatWork : AppCache.getSeatWorks()){
            addSeatWork(seatWork);
        }
        execute();
        seatWorkStats = new ArrayList<>();
    }

    protected void onRestart() {
        super.onRestart();
        SeatWork seatWorkStat = AppCache.getSeatWorkStat();
        seatWorkStats.add(seatWorkStat);
        execute();
    }
}
