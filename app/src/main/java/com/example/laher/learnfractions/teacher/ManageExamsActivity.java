package com.example.laher.learnfractions.teacher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.SeatWorkListActivity;
import com.example.laher.learnfractions.classes.ChapterExamList;
import com.example.laher.learnfractions.dialog_layout.MessageDialog;
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
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.teacher.dialogs.ExamUpdateDialog;
import com.example.laher.learnfractions.teacher.list_adapters.ManageExamsListAdapter;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class ManageExamsActivity extends AppCompatActivity {
    Context mContext = this;
    ListView examListView;
    ChapterExamList mChapterExams;
    ManageExamsListAdapter manageExamsListAdapter;
    LinearLayout linearLayoutBackground;
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

        ConstraintLayout toolbar = findViewById(R.id.constraintLayoutToolbar);
        Styles.bgPaintMainYellow(toolbar);

        Styles.bgPaintMainBlue(btnBack);

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

        mChapterExams = new ChapterExamList();
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

        updateExams();
        setLinearLayoutBackground();
    }

    private void setLinearLayoutBackground(){
        int exams = mChapterExams.size();

        while (exams>4){
            exams = exams - 4;
        }

        linearLayoutBackground = findViewById(R.id.linearLayoutBackground);

        if (exams == 1) {
            Styles.bgPaintMainBlueGreen(linearLayoutBackground);
        } else if (exams == 2) {
            Styles.bgPaintMainBlue(linearLayoutBackground);
        } else if (exams == 3) {
            Styles.bgPaintMainYellow(linearLayoutBackground);
        } else if (exams == 4) {
            Styles.bgPaintMainOrange(linearLayoutBackground);
        }
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
                    manageExamsListAdapter.notifyDataSetChanged();
                } catch (Exception e){
                    e.printStackTrace();
                    MessageDialog messageDialog = new MessageDialog(mContext, "\nService error.\n");
                    messageDialog.show();
                }
            }
        });
        ExamService.getExams(mContext, service);
    }

    @Override
    public void onBackPressed() {
        btnBack.performClick();
    }
}
