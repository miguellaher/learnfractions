package com.example.laher.learnfractions.teacher_activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.StatAverage;
import com.example.laher.learnfractions.model.Student_SW_Progress;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.service.SeatWorkStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.teacher_activities.list_adapters.StudentSWProgressAdapter;
import com.example.laher.learnfractions.teacher_activities.list_adapters.StudentSWProgressAdapter2;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class StudentSWProgressActivity extends AppCompatActivity {
    Context mContext = this;
    private static final String TAG = "Student_SW_P";
    StudentSWProgressAdapter studentSWProgressAdapter;

    //ACTIVITY
    ListView studentSWProgressListView;
    ArrayList<StatAverage> mStatAverages;
    ArrayList<Student_SW_Progress> mStudent_sw_progresses;
    Animation animation;

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
                Intent intent = new Intent(StudentSWProgressActivity.this,
                        TeacherMainActivity.class);
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
        animation = AnimationUtils.loadAnimation(mContext,R.anim.fade_in);

        Log.d(TAG, ":pre service.");
        getAllStudentStats();
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
                        for (StatAverage statAverage : mStatAverages){
                            if (student_sw_progress.getTopicName().equals(statAverage.getTopicName())){
                                if (student_sw_progress.getSeatWorkNum()==statAverage.getSeatWorkNum()){
                                    mStatAverages.get(i).addStats(student_sw_progress);
                                    contains = true;
                                }
                            }
                            i++;
                        }
                        if (!contains){
                            StatAverage statAverage = new StatAverage(student_sw_progress.getTopicName(), student_sw_progress.getSeatWorkNum());
                            statAverage.addStats(student_sw_progress);
                            mStatAverages.add(statAverage);
                        }
                    }
                    for(StatAverage statAverage : mStatAverages){
                        Log.d(TAG, "topic name: " + statAverage.getTopicName() + "; average_score: " + statAverage.getScore_average() +
                        "; average_time_spent: " + statAverage.getTime_spent_average() + "; students_answered: " + statAverage.getStudents_answered()
                        +       "; items_size: " + statAverage.getItems_size() + "; total_score: " + statAverage.getCorrect());
                        double num = statAverage.getItems_size() * statAverage.getStudents_answered();
                        Log.d(TAG, "num = " + num);
                        double avg = (statAverage.getCorrect() / num) * 100;
                        avg = Util.round(avg, 2);
                        Log.d(TAG, "avg = " + avg);

                    }
                    setListViewAdapter();
                    Log.d(TAG, "setAdapter()");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SeatWorkStatService.getAllStats(Storage.load(mContext,Storage.TEACHER_CODE),service);
    }
    public void showStudents(String topicName, int swNum){
        ArrayList<Student_SW_Progress> student_sw_progresses = new ArrayList<>();
        for (Student_SW_Progress student_sw_progress : mStudent_sw_progresses){
            if (topicName.equals(student_sw_progress.getTopicName())){
                if (swNum == student_sw_progress.getSeatWorkNum()){
                    student_sw_progresses.add(student_sw_progress);
                }
            }
        }
        StudentSWProgressAdapter2 studentSWProgressAdapter2 = new StudentSWProgressAdapter2(mContext,R.layout.layout_user_item2,student_sw_progresses);
        studentSWProgressListView.setAdapter(studentSWProgressAdapter2);
        studentSWProgressListView.setOnItemClickListener(null);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListViewAdapter();
                txtTitle.setText(AppConstants.SW_PROGRESSES);
                txtTitle.setAnimation(animation);
                txtTitle.animate();
            }
        });
    }

    private void setListViewAdapter(){
        studentSWProgressAdapter = new StudentSWProgressAdapter(mContext,R.layout.layout_seat_work_item, mStatAverages);
        studentSWProgressListView.setAdapter(studentSWProgressAdapter);
        setListViewOnClickListener();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentSWProgressActivity.this,
                        TeacherMainActivity.class);
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
                txtTitle.setText(studentSWProgressAdapter.getItem(position).getTopicName());
                txtTitle.setAnimation(animation);
                txtTitle.animate();
            }
        });
    }

    @Override
    public void onBackPressed() {
        btnBack.performClick();
    }
}
