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

    //TOOLBAR
    TextView txtTitle;
    Button btnBack, btnNext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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

        SeatWork seatWork1 = new ComparingSimilarSeatWork(3);
        SeatWork seatWork2 = new ComparingDissimilarSeatWork(3);

        ChapterExam chapterExam1 = new ChapterExam("Chapter Exam 1");
        chapterExam1.addSeatWork(seatWork1);
        chapterExam1.addSeatWork(seatWork2);
        ChapterExam chapterExam2 = new ChapterExam("Chapter Exam 2");
        chapterExam2.addSeatWork(new OrderingSimilarSeatWork(3));
        chapterExam2.addSeatWork(new OrderingDissimilarSeatWork(3));
        ChapterExam chapterExam3 = new ChapterExam("Chapter Exam 3");
        chapterExam3.addSeatWork(new AddingSimilarSeatWork(3));
        chapterExam3.addSeatWork(new SubtractingSimilarSeatWork(3));
        ChapterExam chapterExam4 = new ChapterExam("Chapter Exam 4");
        chapterExam4.addSeatWork(new AddingDissimilarSeatWork(3));
        chapterExam4.addSeatWork(new SubtractingDissimilarSeatWork(3));
        ChapterExam chapterExam5 = new ChapterExam("Chapter Exam 5");
        chapterExam5.addSeatWork(new MultiplyingFractionsSeatWork(3));
        chapterExam5.addSeatWork(new DividingFractionsSeatWork(3));
        ChapterExam chapterExam6 = new ChapterExam("Chapter Exam 6");
        chapterExam6.addSeatWork(new AddSubMixedFractionsSeatWork(3));
        chapterExam6.addSeatWork(new MultiplyDivideMixedFractionsSeatWork(3));

        mChapterExams = new ArrayList<>();
        mChapterExams.add(chapterExam1);
        mChapterExams.add(chapterExam2);
        mChapterExams.add(chapterExam3);
        mChapterExams.add(chapterExam4);
        mChapterExams.add(chapterExam5);
        mChapterExams.add(chapterExam6);

        setExamListView();

        getStudentStats();
    }
    private void getStudentStats(){
        Service service = new Service("Loading student stats...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    ArrayList<ChapterExam> onlineChapterExams = new ArrayList<>();
                    Log.d(TAG, "Student id: " + Storage.load(mContext, Storage.STUDENT_ID));
                    for (int i = 1; i <= item_count; i++) {
                        ChapterExam chapterExam = new ChapterExam();
                        chapterExam.setExamTitle(String.valueOf(response.optString(i + "exam_title")));
                        Log.d(TAG, "Exam title: " + chapterExam.getExamTitle());
                        chapterExam.setTotalScore(Integer.valueOf(String.valueOf(response.optString(i + "score"))));
                        chapterExam.setTotalItems(Integer.valueOf(String.valueOf(response.optString(i + "item_size"))));
                        chapterExam.setTimeSpent(Long.valueOf(String.valueOf(response.optString(i + "time_spent"))));
                        Log.d(TAG, "Score / Total Items, Time Spent: " + chapterExam.getTotalScore() + " / " + chapterExam.getTotalItems() +
                                ", " + chapterExam.getTimeSpent());
                        Log.d(TAG, "------------------------");
                        chapterExam.setAnswered(true);
                        onlineChapterExams.add(chapterExam);
                    }
                    rewriteChapterExams(onlineChapterExams);

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
                    mChapterExams.set(i, updatedChapterExam);
                }
                i++;
            }
        }
        setExamListView();
    }
    private void setExamListView(){
        ChapterExamListAdapter chapterExamListAdapter = new ChapterExamListAdapter(mContext, R.layout.layout_user_item, mChapterExams);
        examListView.setAdapter(chapterExamListAdapter);

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
        getStudentStats();
    }
}
