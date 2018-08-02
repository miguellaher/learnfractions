package com.example.laher.learnfractions.teacher_activities;

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
import com.example.laher.learnfractions.model.E_StatAverage;
import com.example.laher.learnfractions.model.ExamStat;
import com.example.laher.learnfractions.model.ExamStatAverage;
import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.model.ExerciseStat;
import com.example.laher.learnfractions.model.SeatWork;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.service.ExamStatService;
import com.example.laher.learnfractions.service.ExerciseStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.teacher_activities.dialogs.StudentExamStatDialog;
import com.example.laher.learnfractions.teacher_activities.list_adapters.StudentEProgressAdapter;
import com.example.laher.learnfractions.teacher_activities.list_adapters.StudentExamProgressAdapter;
import com.example.laher.learnfractions.teacher_activities.list_adapters.StudentExamProgressAdapter2;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class StudentExamProgressActivity extends AppCompatActivity {
    private static final String TAG = "STUDENT_EXAM_LIST";
    Context mContext = this;
    ListView mExamListView;
    ArrayList<ExamStat> mExamStats;
    ArrayList<ExamStatAverage> mExamStatAverages;



    ArrayList<Exercise> mExercises;
    ArrayList<ExerciseStat> mExerciseStats;
    ArrayList<E_StatAverage> mExerciseStatsAverage;
    StudentEProgressAdapter studentEProgressAdapter;

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
        getExamStats();
    }
    private void getAllStudentStats(){
        Service service = new Service("Loading student stats...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    mExerciseStats = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        ExerciseStat exerciseStat = new ExerciseStat();
                        exerciseStat.setStudent(new Student());
                        exerciseStat.getStudent().setUsername(String.valueOf(response.optString(i + "student_username")));
                        Log.d(TAG, exerciseStat.getStudent().getUsername()+":received user.");
                        Log.d(TAG, "");
                        exerciseStat.setTopicName(String.valueOf(response.optString(i + "topic_name")));
                        Log.d(TAG, exerciseStat.getTopicName()+":received.");
                        exerciseStat.setExerciseNum(Integer.valueOf(String.valueOf(response.optString(i + "exercise_num"))));
                        Log.d(TAG, exerciseStat.getTopicName()+" exercise_num:" + exerciseStat.getExerciseNum());
                        exerciseStat.setTime_spent(Integer.valueOf(String.valueOf(response.optString(i + "time_spent"))));
                        Log.d(TAG, exerciseStat.getTopicName()+" time_spent:" + exerciseStat.getTime_spent());
                        exerciseStat.setErrors(Integer.valueOf(String.valueOf(response.optString(i + "errors"))));
                        Log.d(TAG, exerciseStat.getTopicName()+" errors:" + exerciseStat.getErrors());
                        exerciseStat.setRequiredCorrects(Integer.valueOf(String.valueOf(response.optString(i + "required_corrects"))));
                        Log.d(TAG, exerciseStat.getTopicName()+" item size:" + exerciseStat.getRequiredCorrects());
                        if (response.optString(i + "rc_consecutive").equals("1")){
                            exerciseStat.setRc_consecutive(true);
                        } else {
                            exerciseStat.setRc_consecutive(false);
                        }
                        Log.d(TAG, exerciseStat.getTopicName()+" item size:" + exerciseStat.isRc_consecutive());
                        exerciseStat.setMaxErrors(Integer.valueOf(String.valueOf(response.optString(i + "max_errors"))));
                        Log.d(TAG, exerciseStat.getTopicName()+" max_errors:" + exerciseStat.getMaxErrors());
                        if (response.optString(i + "me_consecutive").equals("1")){
                            exerciseStat.setMe_consecutive(true);
                        } else {
                            exerciseStat.setMe_consecutive(false);
                        }
                        Log.d(TAG, exerciseStat.getTopicName()+" me_consecutive:" + exerciseStat.isMe_consecutive());
                        Log.d(TAG, "---------------------------------------");
                        mExerciseStats.add(exerciseStat);
                    }
                    mExerciseStatsAverage = new ArrayList<>();
                    for (ExerciseStat exerciseStat : mExerciseStats){
                        boolean contains = false;
                        int i = 0;
                        for (E_StatAverage statAverage : mExerciseStatsAverage){
                            if (exerciseStat.getTopicName().equals(statAverage.getTopicName())){
                                if (exerciseStat.getExerciseNum()==statAverage.getExerciseNum()){
                                    if (isUpdated(exerciseStat)) {
                                        mExerciseStatsAverage.get(i).addStats(exerciseStat);
                                        contains = true;
                                    }
                                }
                            }
                            i++;
                        }
                        if (!contains){
                            if (isUpdated(exerciseStat)) {
                                E_StatAverage statAverage = new E_StatAverage(exerciseStat.getTopicName(), exerciseStat.getExerciseNum());
                                statAverage.addStats(exerciseStat);
                                mExerciseStatsAverage.add(statAverage);
                            }
                        }
                    }
                    Collections.sort(mExerciseStatsAverage);
                    setListViewAdapter();
                    Log.d(TAG, "setAdapter()");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ExerciseStatService.getAllStats(Storage.load(mContext,Storage.TEACHER_CODE),service);
    }
    private boolean isUpdated(ExerciseStat exerciseStat) {
        for (Exercise exercise : mExercises) {
            if (exerciseStat.getRequiredCorrects() == exercise.getRequiredCorrects()) {
                if (exerciseStat.isRc_consecutive() == exercise.isRc_consecutive()) {
                    if (exerciseStat.getMaxErrors() == exercise.getMaxErrors()) {
                        if (exerciseStat.isMe_consecutive() == exercise.isMe_consecutive()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
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
                }
            }
        });
        ExamStatService.getAllStats(teacher.getTeacher_code(), service);
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
}
