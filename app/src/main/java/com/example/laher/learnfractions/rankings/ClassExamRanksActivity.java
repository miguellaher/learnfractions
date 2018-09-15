package com.example.laher.learnfractions.rankings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.ChapterExamListActivity;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.SeatWorkListActivity;
import com.example.laher.learnfractions.classes.ChapterExamList;
import com.example.laher.learnfractions.classes.ChapterExamRank;
import com.example.laher.learnfractions.dialog_layout.MessageDialog;
import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.rankings.list_adapters.ChapterExamRankListAdapter;
import com.example.laher.learnfractions.service.ExamService;
import com.example.laher.learnfractions.service.ExamStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.ClassRanksMainActivity;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Encryptor;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ClassExamRanksActivity extends AppCompatActivity {
    Context mContext = this;
    ListView mExamRanksListView;
    ChapterExamList mChapterExams;
    ArrayList<ChapterExamRank> mChapterExamRanks;
    //TOOLBAR
    TextView txtTitle;
    Button btnBack, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_template);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);

        ConstraintLayout toolbar = findViewById(R.id.constraintLayoutToolbar);
        Styles.bgPaintMainYellow(toolbar);

        Styles.bgPaintMainBlue(btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassExamRanksActivity.this,
                        ClassRanksMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);
        txtTitle = findViewById(R.id.txtTitle);

        String userType = Storage.load(mContext, Storage.USER_TYPE);

        String title;
        if (userType.equals(AppConstants.USER)) {
            title = AppConstants.EXAM_RANKING;
        } else {
            title = AppConstants.EXAM_RANKING;
            String teacherCode = Storage.load(mContext, Storage.TEACHER_CODE);
            teacherCode = Encryptor.decrypt(teacherCode);
            title = title + "\n of Class " + teacherCode;
        }

        txtTitle = findViewById(R.id.txtTitle);

        txtTitle.setText(title);

        //ACTIVITY
        mExamRanksListView = findViewById(R.id.rank_template_listRanks);

        mChapterExams = ChapterExamListActivity.getChapterExams();

        if (userType.equals(AppConstants.USER)){
            getStudentsStats();
        } else { // if user type is either student or teacher
            updateExams();
        }
    }
    private void getStudentsStats(){
        Service service = new Service("Getting exams stats...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try{
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    ArrayList<ChapterExam> downloadedChapterExamsStats = new ArrayList<>();

                    for (int i = 1; i <= item_count; i++) {
                        ChapterExam chapterExamStats = new ChapterExam();

                        String studentId = response.optString(i + "stud_id");

                        String userType = Storage.load(mContext, Storage.USER_TYPE);

                        String username;
                        if (userType.equals(AppConstants.USER)){
                            username = response.optString(i + "user_username");
                        } else {
                            username = response.optString(i + "student_username");
                        }

                        Student student = new Student();
                        student.setId(studentId);
                        student.setUsername(username);
                        String strExamNumber = response.optString(i + "exam_number");
                        if (Util.isNumeric(strExamNumber)) {
                            int examNumber = Integer.valueOf(strExamNumber);
                            String compiledSeatWorksStats = response.optString(i + "seat_works_stats");
                            String compiledSeatWorks = response.optString(i + "seatworks");

                            chapterExamStats.setStudent(student);
                            chapterExamStats.setExamNumber(examNumber);
                            chapterExamStats.setCompiledSeatWorksStats(compiledSeatWorksStats);
                            chapterExamStats.setCompiledSeatWorks(compiledSeatWorks);
                        }

                        downloadedChapterExamsStats.add(chapterExamStats);
                    }

                    ArrayList<ChapterExam> updatedChapterExamsStats = new ArrayList<>();
                    for (ChapterExam downloadedChapterExamStats : downloadedChapterExamsStats){ // add only the updated
                        for (ChapterExam mChapterExam : mChapterExams){                         // exams stats
                            if (mChapterExam.equals(downloadedChapterExamStats)){
                                if (mChapterExam.hasSameSeatworksWith(downloadedChapterExamStats)){
                                    updatedChapterExamsStats.add(downloadedChapterExamStats);
                                }
                            }
                        }
                    }

                    mChapterExamRanks = new ArrayList<>();
                    for (ChapterExam updatedChapterExamStats : updatedChapterExamsStats){
                        Student student = updatedChapterExamStats.getStudent();
                        ChapterExamRank chapterExamRank = new ChapterExamRank(student);
                        if (mChapterExamRanks.contains(chapterExamRank)){
                            for (ChapterExamRank mChapterExamRank : mChapterExamRanks){
                                if (mChapterExamRank.equals(chapterExamRank)){
                                    mChapterExamRank.addChapterExam(updatedChapterExamStats);
                                }
                            }
                        } else {
                            chapterExamRank.addChapterExam(updatedChapterExamStats);
                            mChapterExamRanks.add(chapterExamRank);
                        }
                    }
                    Collections.sort(mChapterExamRanks);
                    setListAdapter(mChapterExamRanks);
                } catch (Exception e){
                    e.printStackTrace();
                    MessageDialog messageDialog = new MessageDialog(mContext, "\nService error.\n");
                    messageDialog.show();
                    messageDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            btnBack.performClick();
                        }
                    });
                }
            }
        });
        ExamStatService.getAllStats(mContext, service);
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
                            //String compiledSeatWorksStats = response.optString(i + "seat_works_stats");

                            ArrayList<SeatWork> downloadedSeatWorks = ChapterExam.decompileSeatWorks(compiledSeatWorks);
                            ArrayList<SeatWork> examSeatWorks = new ArrayList<>();
                            for (SeatWork downloadedSeatWork : downloadedSeatWorks) {
                                for (SeatWork builtInSeatWork : builtInSeatWorks) { // replace or copy the built-in
                                    if (builtInSeatWork.equals(downloadedSeatWork)) { // seatworks' values with the
                                        builtInSeatWork.getValuesFrom(downloadedSeatWork); // downloaded seatworks' values
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
                    getStudentsStats();
                } catch (Exception e){
                    e.printStackTrace();
                    getStudentsStats();
                }
            }
        });
        ExamService.getExams(mContext, service);
    }

    private void setListAdapter(ArrayList<ChapterExamRank> chapterExamRanks){
        ChapterExamRankListAdapter adapter = new ChapterExamRankListAdapter(mContext, R.layout.rank_adapter_template, chapterExamRanks);
        mExamRanksListView.setAdapter(adapter);

        int chapterExamRanksSize = chapterExamRanks.size();

        while (chapterExamRanksSize>4){
            chapterExamRanksSize = chapterExamRanksSize - 4;
        }

        LinearLayout linearLayoutBackground = findViewById(R.id.linearLayoutBackground);

        if (chapterExamRanksSize==1){
            Styles.bgPaintMainBlueGreen(linearLayoutBackground);
        } else if (chapterExamRanksSize==2){
            Styles.bgPaintMainBlue(linearLayoutBackground);
        } else if (chapterExamRanksSize==3){
            Styles.bgPaintMainYellow(linearLayoutBackground);
        } else if (chapterExamRanksSize==4){
            Styles.bgPaintMainOrange(linearLayoutBackground);
        }
    }

    @Override
    public void onBackPressed() {
        btnBack.performClick();
    }
}
