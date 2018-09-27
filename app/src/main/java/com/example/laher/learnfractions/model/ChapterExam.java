package com.example.laher.learnfractions.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.parent_activities.MainFrame;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.service.ExamStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.AppCache;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class ChapterExam extends MainFrame {
    private Context mContext = this;
    private final String TAG = "C_Exam";
    private ArrayList<SeatWork> seatWorks;
    private ArrayList<SeatWork> seatWorksStats;
    private ArrayList<Intent> intents;
    private String examTitle;
    private int totalScore;
    private int totalItems;
    private long timeSpent;
    private boolean answered;

    private Student student;

    //FOR DATABASE
    private String compiledSeatWorks;
    private String compiledSeatWorksStats;
    private int examNumber;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getCompiledSeatWorksStats() {
        return compiledSeatWorksStats;
    }

    public void setCompiledSeatWorksStats(String compiledSeatWorksStats) {
        this.compiledSeatWorksStats = compiledSeatWorksStats;
        decompileSeatWorksStats();
    }

    public String getCompiledSeatWorks() {
        return compiledSeatWorks;
    }

    public void setCompiledSeatWorks(String compiledSeatWorks) {
        this.compiledSeatWorks = compiledSeatWorks;
    }

    public ArrayList<SeatWork> getSeatWorksStats() {
        return seatWorksStats;
    }

    public void setSeatWorksStats(ArrayList<SeatWork> seatWorksStats) {
        this.seatWorksStats = seatWorksStats;
        int totalExamScore = 0;
        int totalExamItemsSize = 0;
        long totalExamTime = 0;
        for (SeatWork seatWorkStats : seatWorksStats){
            int score = seatWorkStats.getCorrect();
            int itemsSize = seatWorkStats.getItems_size();
            long timeSpent = seatWorkStats.getTimeSpent();

            totalExamScore = totalExamScore + score;
            totalExamItemsSize = totalExamItemsSize + itemsSize;
            totalExamTime = totalExamTime + timeSpent;
        }
        setTotalScore(totalExamScore);
        setTotalItems(totalExamItemsSize);
        setTimeSpent(totalExamTime);
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

    public int getExamNumber() {
        return examNumber;
    }

    public void setExamNumber(int examNumber) {
        this.examNumber = examNumber;
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
        setTotalItems(0);
        for (SeatWork seatWork : seatWorks){
            int items_size = getTotalItems();
            items_size = items_size + seatWork.getItems_size();
            setTotalItems(items_size);

            Intent intent = new Intent(mContext, seatWork.getClass());
            intents.add(intent);
        }
        compileSeatWorks();
    }

    public void addSeatWork(SeatWork seatWork){
        seatWorks.add(seatWork);
        int items_size = getTotalItems();
        items_size = items_size + seatWork.getItems_size();
        setTotalItems(items_size);

        Intent intent = new Intent(mContext, seatWork.getClass());
        intents.add(intent);
        compileSeatWorks();
    }

    private void compileSeatWorks(){
        ArrayList<SeatWork> seatWorks = getSeatWorks();
        StringBuilder compiledSeatWorks = new StringBuilder();
        for (SeatWork seatWork : seatWorks){
            String seatWorkID = seatWork.getId();

            int swItemsSize = seatWork.getItems_size();
            String strSWItemsSize = String.valueOf(swItemsSize);

            Range range = seatWork.getRange();
            int minimum = range.getMinimum();
            int maximum = range.getMaximum();
            String strMinimum = String.valueOf(minimum);
            String strMaximum = String.valueOf(maximum);

            compiledSeatWorks.append(seatWorkID);
            compiledSeatWorks.append(":");
            compiledSeatWorks.append(strSWItemsSize);
            compiledSeatWorks.append(":");
            compiledSeatWorks.append(strMinimum);
            compiledSeatWorks.append(":");
            compiledSeatWorks.append(strMaximum);
            compiledSeatWorks.append(";");
        }
        String strCompiledSeatWorks = String.valueOf(compiledSeatWorks);
        setCompiledSeatWorks(strCompiledSeatWorks);
    }

    public static ArrayList<SeatWork> decompileSeatWorks(String compiledSeatWorks){
        String[] decompiledSeatWorks = compiledSeatWorks.split(";");
        ArrayList<SeatWork> downloadedSeatWorks = new ArrayList<>();
        for (String decompiledSeatWork : decompiledSeatWorks) {
            String[] seatWorkVariables = decompiledSeatWork.split(":");
            if (seatWorkVariables.length > 3) {
                String seatWorkID = seatWorkVariables[0];
                String strItemsSize = seatWorkVariables[1];
                String strMinimum = seatWorkVariables[2];
                String strMaximum = seatWorkVariables[3];

                if (Util.isNumeric(strItemsSize) && Util.isNumeric(strMinimum) && Util.isNumeric(strMaximum)) {
                    int itemSize = Integer.valueOf(strItemsSize);

                    int minimum = Integer.valueOf(strMinimum);
                    int maximum = Integer.valueOf(strMaximum);
                    Range range = new Range(minimum, maximum);

                    SeatWork seatWork = new SeatWork();
                    seatWork.setId(seatWorkID);
                    seatWork.setItems_size(itemSize);
                    seatWork.setRange(range);
                    downloadedSeatWorks.add(seatWork);
                }
            }
        }
        return downloadedSeatWorks;
    }

    private void compileSeatWorksStats(){
        ArrayList<SeatWork> seatWorks = getSeatWorksStats();
        StringBuilder compiledSeatWorks = new StringBuilder();
        for (SeatWork seatWork : seatWorks){
            String seatWorkID = seatWork.getId();

            int swItemsSize = seatWork.getItems_size();
            String strSWItemsSize = String.valueOf(swItemsSize);

            Range range = seatWork.getRange();
            int minimum = range.getMinimum();
            int maximum = range.getMaximum();
            String strMinimum = String.valueOf(minimum);
            String strMaximum = String.valueOf(maximum);

            int score = seatWork.getCorrect();
            String strScore = String.valueOf(score);

            long timeSpent = seatWork.getTimeSpent();
            String strTimeSpent = String.valueOf(timeSpent);

            compiledSeatWorks.append(seatWorkID);
            compiledSeatWorks.append(":");
            compiledSeatWorks.append(strSWItemsSize);
            compiledSeatWorks.append(":");
            compiledSeatWorks.append(strMinimum);
            compiledSeatWorks.append(":");
            compiledSeatWorks.append(strMaximum);
            compiledSeatWorks.append(":");
            compiledSeatWorks.append(strScore);
            compiledSeatWorks.append(":");
            compiledSeatWorks.append(strTimeSpent);
            compiledSeatWorks.append(";");
        }
        String strCompiledSeatWorks = String.valueOf(compiledSeatWorks);
        setCompiledSeatWorksStats(strCompiledSeatWorks);
    }

    public static ArrayList<SeatWork> decompileSeatWorksStats(String compiledSeatWorksStats){
        String[] decompiledSeatWorks = compiledSeatWorksStats.split(";");
        ArrayList<SeatWork> decompiledSeatworksStats = new ArrayList<>();
        for (String decompiledSeatWork : decompiledSeatWorks) {
            String[] seatWorkVariables = decompiledSeatWork.split(":");
            if (seatWorkVariables.length > 5) {
                String seatWorkID = seatWorkVariables[0];
                String strItemsSize = seatWorkVariables[1];
                String strMinimum = seatWorkVariables[2];
                String strMaximum = seatWorkVariables[3];
                String strScore = seatWorkVariables[4];
                String strTimeSpent = seatWorkVariables[5];

                if (Util.isNumeric(strItemsSize) && Util.isNumeric(strMinimum) &&
                        Util.isNumeric(strMaximum) && Util.isNumeric(strScore) &&
                        Util.isNumeric(strTimeSpent)) {
                    int itemSize = Integer.valueOf(strItemsSize);

                    int minimum = Integer.valueOf(strMinimum);
                    int maximum = Integer.valueOf(strMaximum);
                    Range range = new Range(minimum, maximum);

                    int score = Integer.valueOf(strScore);
                    int timeSpent = Integer.valueOf(strTimeSpent);

                    SeatWork seatWork = new SeatWork();
                    seatWork.setId(seatWorkID);
                    seatWork.setItems_size(itemSize);
                    seatWork.setRange(range);
                    seatWork.setCorrect(score);
                    seatWork.setTimeSpent(timeSpent);
                    decompiledSeatworksStats.add(seatWork);
                }
            }
        }
        return decompiledSeatworksStats;
    }

    private void decompileSeatWorksStats(){
        String compiledSeatWorksStats = getCompiledSeatWorksStats();
        ArrayList<SeatWork> decompiledSeatWorksStats = decompileSeatWorksStats(compiledSeatWorksStats);
        setSeatWorksStats(decompiledSeatWorksStats);
    }

    public void execute(){
        if (intents.size()>0){
            Intent intent = intents.get(0);
            intents.remove(0);

            SeatWork seatWork = seatWorks.get(0);
            AppCache.setSeatWork(seatWork);
            seatWorks.remove(0);

            startActivity(intent);
        } else {
            summarizeAndFinishExam();
        }
        Log.d(TAG, "post exec");
    }

    private void summarizeAndFinishExam(){
        int totalExamScore = 0;
        long totalExamTime = 0;
        for (SeatWork seatWork : getSeatWorksStats()){
            int totalScorePer = seatWork.getCorrect();
            long totalTimeSpentPer = seatWork.getTimeSpent();

            totalExamScore = totalExamScore + totalScorePer;
            totalExamTime = totalExamTime + totalTimeSpentPer;
        }
        setTotalScore(totalExamScore);
        setTimeSpent(totalExamTime);
        compileSeatWorksStats();

        Service service = new Service("Posting stats...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    Util.toast(mContext, response.optString("message"));
                    finish();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        ExamStatService.postStats(mContext, this, service);
    }

    public boolean hasSameSeatworksWith(ChapterExam chapterExam){
        String thisSeatworks = this.getCompiledSeatWorks();
        String seatworks = chapterExam.getCompiledSeatWorks();

        return thisSeatworks.equals(seatworks);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        ConstraintLayout toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.INVISIBLE);

        AppCache.setInChapterExam(true); // REMEMBER TO TURN OFF

        ChapterExam chapterExam = AppCache.getChapterExam();

        String examTitle = chapterExam.getExamTitle();
        int examNumber = chapterExam.getExamNumber();

        setExamTitle(examTitle);
        setExamNumber(examNumber);

        for (SeatWork seatWork : AppCache.getSeatWorks()){
            addSeatWork(seatWork);
        }
        execute();
        seatWorksStats = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        AppCache.setInChapterExam(false); // TURNED OFF

        super.onDestroy();
    }

    protected void onRestart() {
        super.onRestart();
        SeatWork seatWorkStat = AppCache.getSeatWork();
        if (seatWorkStat!=null) {
            seatWorksStats.add(seatWorkStat);
        }
        execute();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChapterExam){
            ChapterExam objChapterExam = (ChapterExam) obj;
            int objExamNumber = objChapterExam.getExamNumber();
            int thisExamNumber = this.getExamNumber();
            return objExamNumber==thisExamNumber;
        }
        return super.equals(obj);
    }
}
