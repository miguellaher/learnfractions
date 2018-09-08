package com.example.laher.learnfractions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.adapters.ChapterExamListAdapter;
import com.example.laher.learnfractions.classes.ChapterExamList;
import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.parent_activities.SeatWork;
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
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class ChapterExamListActivity extends AppCompatActivity {
    private static final String TAG = "CHAPTER_EXAM_ACTIVITY";
    Context mContext = this;
    ListView examListView;
    ChapterExamList mChapterExams;
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

        ConstraintLayout toolbar = findViewById(R.id.constraintLayoutToolbar);
        Styles.bgPaintMainYellow(toolbar);

        Styles.bgPaintMainBlue(btnBack);


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

        mChapterExams = getChapterExams();

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
                    for (int i = 1; i <= item_count; i++) {
                        ChapterExam chapterExam = new ChapterExam();

                        String strExamNumber = response.optString(i + "exam_number");
                        if (Util.isNumeric(strExamNumber)) {
                            int examNumber = Integer.valueOf(strExamNumber);
                            String compiledSeatWorksStats = response.optString(i + "seat_works_stats");
                            String compiledSeatWorks = response.optString(i + "seatworks");

                            chapterExam.setExamNumber(examNumber);
                            chapterExam.setCompiledSeatWorksStats(compiledSeatWorksStats);
                            chapterExam.setCompiledSeatWorks(compiledSeatWorks);
                        }
                        onlineChapterExamStats.add(chapterExam);
                    }
                    for (ChapterExam onlineChapterExamStat : onlineChapterExamStats){
                        int i = 0;
                        for (ChapterExam mChapterExam : mChapterExams){
                            if (mChapterExam.equals(onlineChapterExamStat)){
                                String mCompiledSeatWorks = mChapterExam.getCompiledSeatWorks();
                                String onlineCompiledSeatWorks = onlineChapterExamStat.getCompiledSeatWorks();
                                if (mCompiledSeatWorks.equals(onlineCompiledSeatWorks)){
                                    String onlineCompiledSeatWorksStats = onlineChapterExamStat.getCompiledSeatWorksStats();
                                    ArrayList<SeatWork> onlineSeatWorksStats = ChapterExam.decompileSeatWorksStats(onlineCompiledSeatWorksStats);

                                    mChapterExam.setSeatWorksStats(onlineSeatWorksStats);
                                    mChapterExam.setAnswered(true);
                                    mChapterExams.set(i, mChapterExam);
                                }
                            }
                            i++;
                        }
                    }
                    mChapterExamListAdapter.notifyDataSetChanged();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        ExamStatService.getStudentStats(mContext, service);
    }
    private void setExamListView(){
        mChapterExamListAdapter = new ChapterExamListAdapter(mContext, R.layout.layout_user_item, mChapterExams);
        examListView.setAdapter(mChapterExamListAdapter);

        examListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChapterExam chapterExam = mChapterExams.get(position);
                Class ce_class = chapterExam.getClass();
                ArrayList<SeatWork> seatWorks = chapterExam.getSeatWorks();
                AppCache.setSeatWorks(seatWorks);
                AppCache.setChapterExam(chapterExam);
                Intent intent = new Intent(mContext, ce_class);
                startActivity(intent);
            }
        });
    }

    private void updateExams(){
        Service service = new Service("Getting exams...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try{
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    ArrayList<SeatWork> builtInSeatWorks = SeatWorkListActivity.getSeatWorks(); // "ARCHIVE OF SEATWORKS"

                    for (int i = 1; i <= item_count; i++) {
                        ChapterExam onlineChapterExam = new ChapterExam();
                        String strExamNumber = response.optString(i + "exam_number");
                        if (Util.isNumeric(strExamNumber)) {
                            int examNumber = Integer.valueOf(strExamNumber);
                            String examTitle = response.optString(i + "exam_title");
                            String compiledSeatWorks = response.optString(i + "seat_works");

                            ArrayList<SeatWork> downloadedSeatWorks = ChapterExam.decompileSeatWorks(compiledSeatWorks);
                            ArrayList<SeatWork> examSeatWorks = new ArrayList<>();
                            for (SeatWork downloadedSeatWork : downloadedSeatWorks) {
                                for (SeatWork builtInSeatWork : builtInSeatWorks) {
                                    if (builtInSeatWork.equals(downloadedSeatWork)) {
                                        builtInSeatWork.getValuesFrom(downloadedSeatWork);
                                        examSeatWorks.add(builtInSeatWork);
                                    }
                                }
                            }

                            onlineChapterExam.setExamNumber(examNumber);
                            onlineChapterExam.setExamTitle(examTitle);
                            onlineChapterExam.setSeatWorks(examSeatWorks);
                            int i1 = 0;
                            for (ChapterExam mChapterExam : mChapterExams) {
                                if (mChapterExam.equals(onlineChapterExam)) {
                                    mChapterExam = onlineChapterExam;

                                    mChapterExams.set(i1, mChapterExam);
                                }
                                i1++;
                            }
                        }
                    }
                    mChapterExamListAdapter.notifyDataSetChanged();
                    getStudentStats();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        ExamService.getExams(mContext, service);
    }

    public static ChapterExamList getChapterExams(){
        ChapterExamList chapterExams = new ChapterExamList();

        ComparingSimilarSeatWork comparingSimilarSeatWork = new ComparingSimilarSeatWork(AppConstants.COMPARING_SIMILAR_FRACTIONS);
        ComparingDissimilarSeatWork comparingDissimilarSeatWork = new ComparingDissimilarSeatWork(AppConstants.COMPARING_DISSIMILAR_FRACTIONS);
        OrderingSimilarSeatWork orderingSimilarSeatWork = new OrderingSimilarSeatWork(AppConstants.ORDERING_SIMILAR);
        OrderingDissimilarSeatWork orderingDissimilarSeatWork = new OrderingDissimilarSeatWork(AppConstants.ORDERING_DISSIMILAR);
        AddingSimilarSeatWork addingSimilarSeatWork = new AddingSimilarSeatWork(AppConstants.ADDING_SIMILAR);
        SubtractingSimilarSeatWork subtractingSimilarSeatWork = new SubtractingSimilarSeatWork(AppConstants.SUBTRACTING_SIMILAR);
        AddingDissimilarSeatWork addingDissimilarSeatWork = new AddingDissimilarSeatWork(AppConstants.ADDING_DISSIMILAR);
        SubtractingDissimilarSeatWork subtractingDissimilarSeatWork = new SubtractingDissimilarSeatWork(AppConstants.SUBTRACTING_DISSIMILAR);
        MultiplyingFractionsSeatWork multiplyingFractionsSeatWork = new MultiplyingFractionsSeatWork(AppConstants.MULTIPLYING_FRACTIONS);
        DividingFractionsSeatWork dividingFractionsSeatWork = new DividingFractionsSeatWork(AppConstants.DIVIDING_FRACTIONS);
        AddSubMixedFractionsSeatWork addSubMixedFractionsSeatWork = new AddSubMixedFractionsSeatWork(AppConstants.ADDING_SUBTRACTING_MIXED);
        MultiplyDivideMixedFractionsSeatWork multiplyDivideMixedFractionsSeatWork = new MultiplyDivideMixedFractionsSeatWork(AppConstants.MULTIPLYING_DIVIDING_MIXED);

        ChapterExam chapterExam1 = new ChapterExam(AppConstants.C_EXAM1);
        chapterExam1.addSeatWork(comparingSimilarSeatWork);
        chapterExam1.addSeatWork(comparingDissimilarSeatWork);

        ChapterExam chapterExam2 = new ChapterExam(AppConstants.C_EXAM2);
        chapterExam2.addSeatWork(orderingSimilarSeatWork);
        chapterExam2.addSeatWork(orderingDissimilarSeatWork);

        ChapterExam chapterExam3 = new ChapterExam(AppConstants.C_EXAM3);
        chapterExam3.addSeatWork(addingSimilarSeatWork);
        chapterExam3.addSeatWork(subtractingSimilarSeatWork);

        ChapterExam chapterExam4 = new ChapterExam(AppConstants.C_EXAM4);
        chapterExam4.addSeatWork(addingDissimilarSeatWork);
        chapterExam4.addSeatWork(subtractingDissimilarSeatWork);

        ChapterExam chapterExam5 = new ChapterExam(AppConstants.C_EXAM5);
        chapterExam5.addSeatWork(multiplyingFractionsSeatWork);
        chapterExam5.addSeatWork(dividingFractionsSeatWork);

        ChapterExam chapterExam6 = new ChapterExam(AppConstants.C_EXAM6);
        chapterExam6.addSeatWork(addSubMixedFractionsSeatWork);
        chapterExam6.addSeatWork(multiplyDivideMixedFractionsSeatWork);

        chapterExams.add(chapterExam1);
        chapterExams.add(chapterExam2);
        chapterExams.add(chapterExam3);
        chapterExams.add(chapterExam4);
        chapterExams.add(chapterExam5);
        chapterExams.add(chapterExam6);

        return chapterExams;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateExams();
    }
}
