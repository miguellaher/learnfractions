package com.example.laher.learnfractions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.adapters.ChapterExamListAdapter;
import com.example.laher.learnfractions.archive.SeatWorkArchive;
import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.model.SeatWork;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.seat_works.AddSubMixedFractionsSeatWork;
import com.example.laher.learnfractions.seat_works.AddingDissimilarSeatWork;
import com.example.laher.learnfractions.seat_works.AddingSimilarSeatWork;
import com.example.laher.learnfractions.seat_works.ComparingDissimilarSeatWork;
import com.example.laher.learnfractions.seat_works.ComparingSimilarSeatWork;
import com.example.laher.learnfractions.seat_works.DividingFractionsSeatWork;
import com.example.laher.learnfractions.seat_works.MultiplyDivideMixedFractionsSeatWork;
import com.example.laher.learnfractions.seat_works.MultiplyingFractionsSeatWork;
import com.example.laher.learnfractions.seat_works.OrderingDissimilarSeatWork;
import com.example.laher.learnfractions.seat_works.OrderingSimilarSeatWork;
import com.example.laher.learnfractions.seat_works.SubtractingDissimilarSeatWork;
import com.example.laher.learnfractions.seat_works.SubtractingSimilarSeatWork;
import com.example.laher.learnfractions.service.ExamService;
import com.example.laher.learnfractions.service.ExamStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.student_activities.StudentMainActivity;
import com.example.laher.learnfractions.util.AppCache;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;

import org.json.JSONObject;

import java.util.ArrayList;

public class ChapterExamListActivity extends AppCompatActivity {
    private static final String TAG = "CHAPTER_EXAM_ACTIVITY";
    Context mContext = this;
    ListView examListView;
    ArrayList<ChapterExam> mChapterExams;
    ChapterExamListAdapter mChapterExamListAdapter;

    //TOOLBAR
    TextView txtTitle;
    Button btnBack, btnNext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_template);
        examListView = findViewById(R.id.seatwork_list);

        //TOOLBAR
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppConstants.CHAPTER_EXAM);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StudentMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);

        SeatWork seatWork1 = new ComparingSimilarSeatWork(10);
        SeatWork seatWork2 = new ComparingDissimilarSeatWork(10);

        ChapterExam chapterExam1 = new ChapterExam(AppConstants.C_EXAM1);
        chapterExam1.addSeatWork(seatWork1);
        chapterExam1.addSeatWork(seatWork2);
        ChapterExam chapterExam2 = new ChapterExam(AppConstants.C_EXAM2);
        chapterExam2.addSeatWork(new OrderingSimilarSeatWork(10));
        chapterExam2.addSeatWork(new OrderingDissimilarSeatWork(10));
        ChapterExam chapterExam3 = new ChapterExam(AppConstants.C_EXAM3);
        chapterExam3.addSeatWork(new AddingSimilarSeatWork(10));
        chapterExam3.addSeatWork(new SubtractingSimilarSeatWork(10));
        ChapterExam chapterExam4 = new ChapterExam(AppConstants.C_EXAM4);
        chapterExam4.addSeatWork(new AddingDissimilarSeatWork(10));
        chapterExam4.addSeatWork(new SubtractingDissimilarSeatWork(10));
        ChapterExam chapterExam5 = new ChapterExam(AppConstants.C_EXAM5);
        chapterExam5.addSeatWork(new MultiplyingFractionsSeatWork(10));
        chapterExam5.addSeatWork(new DividingFractionsSeatWork(10));
        ChapterExam chapterExam6 = new ChapterExam(AppConstants.C_EXAM6);
        chapterExam6.addSeatWork(new AddSubMixedFractionsSeatWork(10));
        chapterExam6.addSeatWork(new MultiplyDivideMixedFractionsSeatWork(10));

        mChapterExams = new ArrayList<>();
        mChapterExams.add(chapterExam1);
        mChapterExams.add(chapterExam2);
        mChapterExams.add(chapterExam3);
        mChapterExams.add(chapterExam4);
        mChapterExams.add(chapterExam5);
        mChapterExams.add(chapterExam6);

        setExamListView();
    }
    private void getStudentStats(){
        Log.d(TAG, "getStudentStats()");
        Service service = new Service("Loading student stats...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    ArrayList<ChapterExam> onlineChapterExamStats = new ArrayList<>();
                    Log.d(TAG, "Student id: " + Storage.load(mContext, Storage.STUDENT_ID));
                    for (int i = 1; i <= item_count; i++) {
                        ChapterExam chapterExam = new ChapterExam();
                        chapterExam.setExamTitle(String.valueOf(response.optString(i + "exam_title")));
                        Log.d(TAG, "Exam title: " + chapterExam.getExamTitle());
                        chapterExam.setTotalScore(Integer.valueOf(String.valueOf(response.optString(i + "score"))));
                        chapterExam.setTotalItems(Integer.valueOf(String.valueOf(response.optString(i + "item_size"))));
                        chapterExam.setTimeSpent(Long.valueOf(String.valueOf(response.optString(i + "time_spent"))));
                        String seat_works = response.optString(i + "seat_works");
                        Log.d(TAG, "Seat works: " + seat_works);
                        String[] sw_tokens = seat_works.split(";");
                        ArrayList<SeatWork> seatWorks = new ArrayList<>();
                        for (String token : sw_tokens){
                            String[] tokens2 = token.split(":");
                            String topicName = tokens2[0];
                            int swNum = Integer.valueOf(tokens2[1]);
                            int item_size = Integer.valueOf(tokens2[2]);
                            SeatWork seatWork = new SeatWork();
                            seatWork.setTopicName(topicName);
                            seatWork.setSeatWorkNum(swNum);
                            seatWork.setItems_size(item_size);
                            seatWorks.add(seatWork);
                        }
                        chapterExam.setSeatWorks(seatWorks);

                        Log.d(TAG, "Score / Total Items, Time Spent: " + chapterExam.getTotalScore() + " / " + chapterExam.getTotalItems() +
                                ", " + chapterExam.getTimeSpent());
                        Log.d(TAG, "------------------------");
                        chapterExam.setAnswered(true);
                        onlineChapterExamStats.add(chapterExam);
                    }
                    ArrayList<ChapterExam> toRemove = new ArrayList<>();
                    for (ChapterExam mChapterExam : mChapterExams){ // removing exam stats that isn't updated
                        for (ChapterExam onlineChapterExamStat : onlineChapterExamStats){
                            if (mChapterExam.getExamTitle().equals(onlineChapterExamStat.getExamTitle())) {
                                boolean hasDifferentSeatWorks = false;
                                if (mChapterExam.getSeatWorks().size() == onlineChapterExamStat.getSeatWorks().size()) {
                                    boolean hasDifference;
                                    for (SeatWork ce_seatWork : mChapterExam.getSeatWorks()) {
                                        boolean hasSameSeatWorkTopicName = false;
                                        boolean hasSameSeatWorkNum = false;
                                        boolean hasSameSeatWork = false;
                                        for (SeatWork es_seatWork : onlineChapterExamStat.getSeatWorks()) {
                                            if (ce_seatWork.getTopicName().equals(es_seatWork.getTopicName())) {
                                                hasSameSeatWorkTopicName = true;
                                            }
                                            if (hasSameSeatWorkTopicName) {
                                                if (ce_seatWork.getSeatWorkNum() == es_seatWork.getSeatWorkNum()) {
                                                    hasSameSeatWorkNum = true;
                                                }
                                                if (hasSameSeatWorkNum) {
                                                    if (ce_seatWork.getItems_size() == es_seatWork.getItems_size()) {
                                                        hasSameSeatWork = true;
                                                    }
                                                }
                                            }
                                        }
                                        hasDifference = !hasSameSeatWork;
                                        if (hasDifference){
                                            hasDifferentSeatWorks = true;
                                        }
                                    }
                                } else {
                                    hasDifferentSeatWorks = true;
                                }
                                if (hasDifferentSeatWorks) {
                                    toRemove.add(onlineChapterExamStat);
                                }
                            }
                        }
                    }
                    onlineChapterExamStats.removeAll(toRemove);
                    rewriteChapterExams(onlineChapterExamStats);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        Student student = new Student();
        student.setTeacher_code(Storage.load(mContext,Storage.TEACHER_CODE));
        student.setId(Storage.load(mContext, Storage.STUDENT_ID));
        ExamStatService.getStudentStats(service, student);
    }
    private void rewriteChapterExams(ArrayList<ChapterExam> updatedChapterExams){
        for (ChapterExam updatedChapterExam : updatedChapterExams) {
            int i = 0;
            for (ChapterExam chapterExam : mChapterExams) {
                if (updatedChapterExam.getExamTitle().equals(chapterExam.getExamTitle())){
                    updatedChapterExam.setSeatWorks(chapterExam.getSeatWorks());
                    mChapterExams.set(i, updatedChapterExam);
                }
                i++;
            }
        }
        setExamListView();
    }
    private void setExamListView(){
        mChapterExamListAdapter = new ChapterExamListAdapter(mContext, R.layout.layout_user_item, mChapterExams);
        examListView.setAdapter(mChapterExamListAdapter);

        examListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class ce_class = mChapterExams.get(position).getClass();
                ArrayList<SeatWork> seatWorks = mChapterExams.get(position).getSeatWorks();
                AppCache.setSeatWorks(seatWorks);
                AppCache.setChapterExam(mChapterExams.get(position));
                Intent intent = new Intent(mContext, ce_class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        updateExams();
    }
    private void updateExams(){
        Log.d(TAG, "updateExams()");
        Service service = new Service("Getting exams...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try{
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    ArrayList<ChapterExam> onlineChapterExams = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        ChapterExam chapterExam = new ChapterExam();
                        String exam_title = response.optString(i + "exam_title");
                        Log.d(TAG, "Exam title: " + exam_title);
                        chapterExam.setExamTitle(exam_title);
                        String seat_works = response.optString(i + "seat_works");
                        Log.d(TAG, "Seat works: " + seat_works);
                        String[] sw_tokens = seat_works.split(";");
                        ArrayList<SeatWork> seatWorks = new ArrayList<>();
                        for (String token : sw_tokens){
                            String[] tokens2 = token.split(":");
                            String topicName = tokens2[0];
                            int swNum = Integer.valueOf(tokens2[1]);
                            int item_size = Integer.valueOf(tokens2[2]);
                            SeatWork seatWork = new SeatWork();
                            seatWork.setTopicName(topicName);
                            Log.d(TAG, "Seat work topic name: " + seatWork.getTopicName());
                            seatWork.setSeatWorkNum(swNum);
                            Log.d(TAG, "Seat work num: " + seatWork.getSeatWorkNum());
                            seatWork.setItems_size(item_size);
                            Log.d(TAG, "Seat work item size: " + seatWork.getItems_size());
                            seatWorks.add(seatWork);
                        }
                        for (SeatWork seatWork : seatWorks){
                            SeatWork toAdd;
                            SeatWorkArchive seatWorkArchive = new SeatWorkArchive();
                            toAdd = seatWorkArchive.findSeatWork(seatWork);
                            Log.d(TAG, "To Add Seat work topic name: " + toAdd.getTopicName());
                            Log.d(TAG, "To Add Seat work num: " + toAdd.getSeatWorkNum());
                            Log.d(TAG, "To Add Seat work item size: " + toAdd.getItems_size());
                            chapterExam.addSeatWork(toAdd);
                        }
                        onlineChapterExams.add(chapterExam);
                    }
                    for (ChapterExam onlineChapterExam : onlineChapterExams){
                        int i = 0;
                        for (ChapterExam chapterExam : mChapterExams){
                            if (onlineChapterExam.getExamTitle().equals(chapterExam.getExamTitle())){
                                mChapterExams.set(i, onlineChapterExam);
                            }
                            i++;
                        }
                    }
                    rewriteChapterExams(mChapterExams);
                    getStudentStats();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        String teacher_code = Storage.load(mContext,Storage.TEACHER_CODE);
        ExamService.getExams(service, teacher_code);
    }
}
