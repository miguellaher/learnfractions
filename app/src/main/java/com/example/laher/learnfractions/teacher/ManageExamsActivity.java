package com.example.laher.learnfractions.teacher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.model.Exercise;
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
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.teacher.dialogs.ExamUpdateDialog;
import com.example.laher.learnfractions.teacher.list_adapters.ManageExamsListAdapter;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;

import org.json.JSONObject;

import java.util.ArrayList;

public class ManageExamsActivity extends AppCompatActivity {
    private static final String TAG = "MANAGE_EXAM";
    Context mContext = this;
    ListView examListView;
    ArrayList<Exercise> exercises;
    ArrayList<ChapterExam> mChapterExams;
    ManageExamsListAdapter manageExamsListAdapter;
    //TOOLBAR
    TextView txtTitle;
    Button btnBack, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                Intent intent = new Intent(mContext, TeacherMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);
        //ACTIVITY
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

        mChapterExams = new ArrayList<>();
        mChapterExams.add(chapterExam1);
        mChapterExams.add(chapterExam2);
        mChapterExams.add(chapterExam3);
        mChapterExams.add(chapterExam4);
        mChapterExams.add(chapterExam5);
        mChapterExams.add(chapterExam6);

        manageExamsListAdapter = new ManageExamsListAdapter(mContext,R.layout.layout_user_item,mChapterExams);
        examListView.setAdapter(manageExamsListAdapter);
        examListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ChapterExam chapterExam = mChapterExams.get(position);
                final ExamUpdateDialog examUpdateDialog = new ExamUpdateDialog(mContext, chapterExam);
                examUpdateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        ChapterExam dialogChapterExam = examUpdateDialog.getChapterExam();
                        mChapterExams.set(position, dialogChapterExam);
                        manageExamsListAdapter.notifyDataSetChanged();
                    }
                });
                examUpdateDialog.show();
            }
        });

    }
    private void updateExams(){
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
                            seatWork.setSeatWorkNum(swNum);
                            seatWork.setItems_size(item_size);
                            seatWorks.add(seatWork);
                        }
                        chapterExam.setSeatWorks(seatWorks);
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
                    manageExamsListAdapter.notifyDataSetChanged();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        ExamService.getExams(mContext, service);
    }
}
