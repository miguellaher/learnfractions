package com.example.laher.learnfractions.rankings;

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
import com.example.laher.learnfractions.model.SW_StatAverage;
import com.example.laher.learnfractions.model.SeatWork;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.model.Student_SW_Progress;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.rankings.list_adapters.SeatWorkRankListAdapter;
import com.example.laher.learnfractions.rankings.list_adapters.SeatWorkTop10ListAdapter;
import com.example.laher.learnfractions.service.SeatWorkService;
import com.example.laher.learnfractions.service.SeatWorkStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.student_activities.ClassRanksActivity;
import com.example.laher.learnfractions.student_activities.StudentMainActivity;
import com.example.laher.learnfractions.teacher_activities.TeacherMainActivity;
import com.example.laher.learnfractions.teacher_activities.list_adapters.StudentSWProgressAdapter;
import com.example.laher.learnfractions.teacher_activities.list_adapters.StudentSWProgressAdapter2;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class ClassSeatWorkRanksActivity extends AppCompatActivity {
    Context mContext = this;
    private static final String TAG = "Class_SW_Ranks";
    SeatWorkRankListAdapter mSeatWorkRankListAdapter;

    //ACTIVITY
    ListView studentSWProgressListView;
    ArrayList<SW_StatAverage> mStatAverages;
    ArrayList<Student_SW_Progress> mStudent_sw_progresses;
    ArrayList<SeatWork> mSeatWorks;

    //TOOLBAR
    TextView txtTitle;
    Button btnBack, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_swprogress);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassSeatWorkRanksActivity.this,
                        ClassRanksActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppConstants.SW_PROGRESSES);

        //ACTIVITY
        studentSWProgressListView = findViewById(R.id.student_swprogress_listSeatWork);
        mStatAverages = new ArrayList<>();

        Log.d(TAG, ":pre service.");
        getLatestSeatWorks();
    }

    private void getAllStudentStats(){
        Service service = new Service("Loading student stats...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    mStudent_sw_progresses = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        Student_SW_Progress student_sw_progress = new Student_SW_Progress();
                        student_sw_progress.setStudent(new Student());
                        student_sw_progress.getStudent().setUsername(String.valueOf(response.optString(i + "student_username")));
                        Log.d(TAG, student_sw_progress.getStudent().getUsername()+":received user.");
                        student_sw_progress.setTopicName(String.valueOf(response.optString(i + "topic_name")));
                        Log.d(TAG, student_sw_progress.getTopicName()+":received.");
                        student_sw_progress.setSeatWorkNum(Integer.valueOf(String.valueOf(response.optString(i + "sw_num"))));
                        Log.d(TAG, student_sw_progress.getTopicName()+" sw_num:" + student_sw_progress.getSeatWorkNum());
                        student_sw_progress.setCorrect(Integer.valueOf(String.valueOf(response.optString(i + "score"))));
                        Log.d(TAG, student_sw_progress.getTopicName()+" score:" + student_sw_progress.getCorrect());
                        student_sw_progress.setTimeSpent(Integer.valueOf(String.valueOf(response.optString(i + "time_spent"))));
                        Log.d(TAG, student_sw_progress.getTopicName()+" time spent:" + student_sw_progress.getTimeSpent());
                        student_sw_progress.setItems_size(Integer.valueOf(String.valueOf(response.optString(i + "items_size"))));
                        Log.d(TAG, student_sw_progress.getTopicName()+" item size:" + student_sw_progress.getItems_size());
                        mStudent_sw_progresses.add(student_sw_progress);
                    }
                    for (Student_SW_Progress student_sw_progress : mStudent_sw_progresses){
                        boolean contains = false;
                        int i = 0;
                        for (SW_StatAverage statAverage : mStatAverages){
                            if (student_sw_progress.getTopicName().equals(statAverage.getTopicName())){
                                if (student_sw_progress.getSeatWorkNum()==statAverage.getSeatWorkNum()){
                                    if (checkSWProgIfLatest(student_sw_progress)) {
                                        mStatAverages.get(i).addStats(student_sw_progress);
                                        contains = true;
                                    }
                                }
                            }
                            i++;
                        }
                        if (!contains){
                            if (checkSWProgIfLatest(student_sw_progress)) {
                                SW_StatAverage statAverage = new SW_StatAverage(student_sw_progress.getTopicName(), student_sw_progress.getSeatWorkNum());
                                statAverage.addStats(student_sw_progress);
                                mStatAverages.add(statAverage);
                            }
                        }
                    }
                    for(SW_StatAverage statAverage : mStatAverages){
                        Log.d(TAG, "topic name: " + statAverage.getTopicName() + "; average_score: " + statAverage.getScore_average() +
                        "; average_time_spent: " + statAverage.getTime_spent_average() + "; students_answered: " + statAverage.getStudents_answered()
                        +       "; items_size: " + statAverage.getItems_size() + "; total_score: " + statAverage.getCorrect());
                        double num = statAverage.getItems_size() * statAverage.getStudents_answered();
                        Log.d(TAG, "num = " + num);
                        double avg = (statAverage.getCorrect() / num) * 100;
                        avg = Util.round(avg, 2);
                        Log.d(TAG, "avg = " + avg);

                    }
                    Collections.sort(mStatAverages);
                    setListViewAdapter();
                    Log.d(TAG, "setAdapter()");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SeatWorkStatService.getAllStats(Storage.load(mContext,Storage.TEACHER_CODE),service);
    }
    private boolean checkSWProgIfLatest(Student_SW_Progress student_sw_progress){
        for (SeatWork seatWork : mSeatWorks){
            if (student_sw_progress.getTopicName().equals(seatWork.getTopicName())){
                if(student_sw_progress.getSeatWorkNum()==seatWork.getSeatWorkNum()){
                    if (student_sw_progress.getItems_size()==seatWork.getItems_size()){
                        Log.d(TAG, "student_sw_progress item size: " + student_sw_progress.getItems_size() +
                                "; seatWork item size: " + seatWork.getItems_size());
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void showStudents(String topicName, int swNum){
        ArrayList<Student_SW_Progress> student_sw_progresses = new ArrayList<>();
        for (Student_SW_Progress student_sw_progress : mStudent_sw_progresses){
            if (topicName.equals(student_sw_progress.getTopicName())){
                if (swNum == student_sw_progress.getSeatWorkNum()){
                    if (checkSWProgIfLatest(student_sw_progress)) {
                        student_sw_progresses.add(student_sw_progress);
                    }
                }
            }
        }
        Collections.sort(student_sw_progresses);
        SeatWorkTop10ListAdapter seatWorkTop10ListAdapter = new SeatWorkTop10ListAdapter(mContext, R.layout.exercise_rank_top10, student_sw_progresses);
        studentSWProgressListView.setAdapter(seatWorkTop10ListAdapter);
        studentSWProgressListView.setOnItemClickListener(null);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListViewAdapter();
                txtTitle.setText(AppConstants.SW_PROGRESSES);
            }
        });
    }

    private void setListViewAdapter(){
        mSeatWorkRankListAdapter = new SeatWorkRankListAdapter(mContext,R.layout.exercise_rank_item, mStatAverages);
        studentSWProgressListView.setAdapter(mSeatWorkRankListAdapter);
        setListViewOnClickListener();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassSeatWorkRanksActivity.this,
                        ClassRanksActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void setListViewOnClickListener(){
        studentSWProgressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String topic_name = mStatAverages.get(position).getTopicName();
                int sw_num = mStatAverages.get(position).getSeatWorkNum();
                showStudents(topic_name,sw_num);
                txtTitle.setText(Objects.requireNonNull(mSeatWorkRankListAdapter.getItem(position)).getTopicName());
            }
        });
    }

    @Override
    public void onBackPressed() {
        btnBack.performClick();
    }

    private void getLatestSeatWorks(){
        final Teacher teacher = new Teacher();
        teacher.setId(Storage.load(mContext,Storage.TEACHER_ID));
        teacher.setTeacher_code(Storage.load(mContext,Storage.TEACHER_CODE));
        Service service = new Service("Getting updated seat works...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    mSeatWorks = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        SeatWork seatWork = new SeatWork();
                        seatWork.setTopicName(String.valueOf(response.optString(i + "topic_name")));
                        seatWork.setSeatWorkNum(Integer.valueOf(String.valueOf(response.optString(i + "sw_num"))));
                        seatWork.setItems_size(Integer.valueOf(String.valueOf(response.optString(i + "item_size"))));
                        Log.d(TAG, seatWork.getTopicName()+" w/ item size " + seatWork.getItems_size() + ": received");
                        mSeatWorks.add(seatWork);
                    }
                    getAllStudentStats();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SeatWorkService.getUpdates(teacher.getTeacher_code(), service);
    }
}
