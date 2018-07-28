package com.example.laher.learnfractions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.adapters.ChapterExamListAdapter;
import com.example.laher.learnfractions.chapter_exam.ChapterExam;
import com.example.laher.learnfractions.model.SeatWork;
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
import com.example.laher.learnfractions.student_activities.StudentMainActivity;
import com.example.laher.learnfractions.util.AppCache;
import com.example.laher.learnfractions.util.AppConstants;

import java.util.ArrayList;

public class ChapterExamListActivity extends AppCompatActivity {
    private static final String TAG = "CHAPTER_EXAM_ACTIVITY";
    Context mContext = this;
    ListView examListView;
    ArrayList<ChapterExam> chapterExams;

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

        chapterExams = new ArrayList<>();
        chapterExams.add(chapterExam1);
        chapterExams.add(chapterExam2);
        chapterExams.add(chapterExam3);
        chapterExams.add(chapterExam4);
        chapterExams.add(chapterExam5);
        chapterExams.add(chapterExam6);

        ChapterExamListAdapter chapterExamListAdapter = new ChapterExamListAdapter(mContext, R.layout.layout_user_item, chapterExams);
        examListView.setAdapter(chapterExamListAdapter);

        examListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class ce_class = chapterExams.get(position).getClass();
                ArrayList<SeatWork> seatWorks = chapterExams.get(position).getSeatWorks();
                AppCache.setSeatWorks(seatWorks);
                AppCache.setChapterExam(chapterExams.get(position));
                Intent intent = new Intent(mContext, ce_class);
                startActivity(intent);
            }
        });

        

    }
}
