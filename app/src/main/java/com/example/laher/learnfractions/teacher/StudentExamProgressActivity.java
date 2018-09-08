package com.example.laher.learnfractions.teacher;

import android.content.Context;
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
import com.example.laher.learnfractions.model.ExamStat;
import com.example.laher.learnfractions.model.ExamStatAverage;
import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.service.ExamService;
import com.example.laher.learnfractions.service.ExamStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.teacher.dialogs.StudentExamStatDialog;
import com.example.laher.learnfractions.teacher.list_adapters.StudentExamProgressAdapter;
import com.example.laher.learnfractions.teacher.list_adapters.StudentExamProgressAdapter2;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class StudentExamProgressActivity extends AppCompatActivity {
    private static final String TAG = "STUDENT_EXAM_LIST";
    Context mContext = this;
    ListView mExamListView;
    ArrayList<ChapterExam> mOnlineChapterExams;
    ArrayList<ExamStat> mExamStats;
    ArrayList<ExamStatAverage> mExamStatAverages;




    ArrayList<Exercise> mExercises;

    StudentExamProgressAdapter studentExamProgressAdapter;
    //TOOLBAR
    TextView txtTitle;
    Button btnBack, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentExamProgressActivity.this,
                        TeacherMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppConstants.EXAM_PROGRESSES);
        //ACTIVITY
        mExamListView = findViewById(R.id.exercise_list);
        updateExams();
    }
    private void getExamStats(){
        final Teacher teacher = new Teacher();
        teacher.setId(Storage.load(mContext,Storage.TEACHER_ID));
        teacher.setTeacher_code(Storage.load(mContext,Storage.TEACHER_CODE));
        Service service = new Service("Getting exam stats...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    mExamStats = new ArrayList<>();
                    Log.d(TAG, "getExamStats()");
                    for (int i = 1; i <= item_count; i++) {
                        ExamStat examStat = new ExamStat();
                        Student student = new Student();
                        student.setUsername(String.valueOf(response.optString(i + "student_username")));
                        examStat.setStudent(student);
                        examStat.setExamTitle(String.valueOf(response.optString(i + "exam_title")));
                        examStat.setTotalScore(Integer.valueOf(String.valueOf(response.optString(i + "score"))));
                        examStat.setTotalItems(Integer.valueOf(String.valueOf(response.optString(i + "item_size"))));
                        examStat.setTimeSpent(Long.valueOf(String.valueOf(response.optString(i + "time_spent"))));
                        Log.d(TAG, "username: " + student.getUsername());
                        Log.d(TAG, "exam_title: " + examStat.getExamTitle());
                        Log.d(TAG, "score: " + examStat.getTotalScore());
                        Log.d(TAG, "item_size: " + examStat.getTotalItems());
                        Log.d(TAG, "time_spent: " + examStat.getTimeSpent());
                        String s_seatWork = String.valueOf(response.optString(i + "seatworks"));
                        Log.d(TAG, "seat works: " + s_seatWork);
                        String s_seatWorkStat = String.valueOf(response.optString(i + "seatwork_stats"));
                        Log.d(TAG, "seat works stats: " + s_seatWorkStat);
                        Log.d(TAG, "<>");
                        Log.d(TAG, "<>");
                        String[] sw_tokens = s_seatWork.split(";");
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

                        String[] stat_tokens = s_seatWorkStat.split(";");
                        for (String token : stat_tokens){
                            String[] tokens2 = token.split(":");
                            String topicName = tokens2[0];
                            int swNum = Integer.valueOf(tokens2[1]);
                            int score = Integer.valueOf(tokens2[2]);
                            long time_spent = Long.valueOf(tokens2[3]);
                            int it = 0;
                            for (SeatWork seatWork : seatWorks){
                                if (seatWork.getTopicName().equals(topicName)){
                                    if (seatWork.getSeatWorkNum() == swNum){
                                        seatWorks.get(it).setCorrect(score);
                                        seatWorks.get(it).setTimeSpent(time_spent);
                                        Log.d(TAG, "topic name:" + seatWorks.get(it).getTopicName());
                                        Log.d(TAG, "sw num:" + seatWorks.get(it).getSeatWorkNum());
                                        Log.d(TAG, "correct:" + seatWorks.get(it).getCorrect());
                                        Log.d(TAG, "item size:" + seatWorks.get(it).getItems_size());
                                        Log.d(TAG, "time spent:" + seatWorks.get(it).getTimeSpent());
                                        Log.d(TAG, "<>");
                                    }
                                }
                                it++;
                            }
                        }
                        Log.d(TAG, "---------------------------------------");
                        examStat.setSeatWorks(seatWorks);
                        mExamStats.add(examStat);
                    }
                    ArrayList<ExamStat> toRemove = new ArrayList<>();
                    for (ChapterExam chapterExam : mOnlineChapterExams){ // removing exam stats that isn't updated
                        for (ExamStat examStat : mExamStats){
                            if (chapterExam.getExamTitle().equals(examStat.getExamTitle())) {
                                boolean hasDifferentSeatWorks = false;
                                if (chapterExam.getSeatWorks().size() == examStat.getSeatWorks().size()) {
                                    boolean hasDifference;
                                    for (SeatWork ce_seatWork : chapterExam.getSeatWorks()) {
                                        boolean hasSameSeatWorkTopicName = false;
                                        boolean hasSameSeatWorkNum = false;
                                        boolean hasSameSeatWork = false;
                                        for (SeatWork es_seatWork : examStat.getSeatWorks()) {
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
                                    toRemove.add(examStat);
                                }
                            }
                        }
                    }
                    mExamStats.removeAll(toRemove);

                    mExamStatAverages = new ArrayList<>();
                    for (ExamStat examStat : mExamStats){
                        boolean contains = false;
                        int i = 0;
                        for (ExamStatAverage examStatAverage : mExamStatAverages){
                            if (examStat.getExamTitle().equals(examStatAverage.getExamTitle())){
                                contains = true;
                                mExamStatAverages.get(i).addStats(examStat);
                            }
                            i++;
                        }
                        if (!contains){
                            ExamStatAverage examStatAverage = new ExamStatAverage();
                            examStatAverage.addStats(examStat);
                            Log.d(TAG, examStat.getExamTitle() + " added to mExamStatAverages.");
                            mExamStatAverages.add(examStatAverage);
                        }
                    }
                    Collections.sort(mExamStatAverages);
                    setListViewAdapter();
                    //getAllStudentStats();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, e.toString());
                }
            }
        });
        //ExamStatService.getAllStats(teacher.getTeacher_code(), service);
    }
    private void setListViewAdapter(){
        studentExamProgressAdapter = new StudentExamProgressAdapter(mContext, R.layout.layout_seat_work_item, mExamStatAverages);
        mExamListView.setAdapter(studentExamProgressAdapter);
        setListViewOnClickListener();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentExamProgressActivity.this,
                        TeacherMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
    private void setListViewOnClickListener(){
        mExamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String title = mExamStatAverages.get(position).getExamTitle();
                showStudents(title);
            }
        });
    }
    public void showStudents(String title){
        txtTitle.setText(title);
        ArrayList<ExamStat> examStats = new ArrayList<>();
        for (ExamStat examStat : mExamStats){
            if (examStat.getExamTitle().equals(title)){
                examStats.add(examStat);
            }
        }

        StudentExamProgressAdapter2 studentExamProgressAdapter2 = new StudentExamProgressAdapter2(mContext,R.layout.layout_user_item2,examStats);
        mExamListView.setAdapter(studentExamProgressAdapter2);
        mExamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExamStat examStat = (ExamStat) parent.getItemAtPosition(position);
                StudentExamStatDialog studentExamStatDialog = new StudentExamStatDialog(mContext, examStat);
                studentExamStatDialog.show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListViewAdapter();
                txtTitle.setText(AppConstants.EXAM_PROGRESSES);
            }
        });
    }
    private void updateExams(){
        Service service = new Service("Getting exams...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try{
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    mOnlineChapterExams = new ArrayList<>();
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
                        mOnlineChapterExams.add(chapterExam);
                    }
                    getExamStats();
                } catch (Exception e){
                    e.printStackTrace();
                    Log.d(TAG, e.toString());
                }
            }
        });
        String teacher_code = Storage.load(mContext,Storage.TEACHER_CODE);
        //ExamService.getExams(service, teacher_code);
    }
}
