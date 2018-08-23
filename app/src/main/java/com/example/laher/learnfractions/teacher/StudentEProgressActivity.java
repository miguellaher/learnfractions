package com.example.laher.learnfractions.teacher;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.model.E_StatAverage;
import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.model.ExerciseStat;
import com.example.laher.learnfractions.model.LessonClass;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.ExerciseStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.teacher.list_adapters.StudentEProgressAdapter;
import com.example.laher.learnfractions.teacher.list_adapters.StudentEProgressAdapter2;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class StudentEProgressActivity extends AppCompatActivity {
    private static final String TAG = "STUDENT_E_LIST";
    Context mContext = this;
    ListView mExerciseListView;
    ArrayList<Exercise> mExercises;
    ArrayList<ExerciseStat> mExerciseStats;
    ArrayList<E_StatAverage> mExerciseStatsAverage;
    StudentEProgressAdapter studentEProgressAdapter;
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
                Intent intent = new Intent(StudentEProgressActivity.this,
                        TeacherMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppConstants.E_PROGRESSES);
        //ACTIVITY
        mExerciseListView = findViewById(R.id.exercise_list);
        getLatestExercises();
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
    private void getLatestExercises(){
        final Teacher teacher = new Teacher();
        teacher.setId(Storage.load(mContext,Storage.TEACHER_ID));
        teacher.setTeacher_code(Storage.load(mContext,Storage.TEACHER_CODE));
        Service service = new Service("Getting updated exercises...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    mExercises = new ArrayList<>();
                    Log.d(TAG, "getLatestExercises()");
                    for (int i = 1; i <= item_count; i++) {
                        Exercise exercise = new Exercise();
                        exercise.setTopicName(String.valueOf(response.optString(i + "topic_name")));
                        exercise.setExerciseNum(Integer.valueOf(String.valueOf(response.optString(i + "exercise_num"))));
                        Log.d(TAG, "Topic name: " + exercise.getTopicName());
                        Log.d(TAG, "Exercise # " + exercise.getExerciseNum());
                        exercise.setRequiredCorrects(Integer.valueOf(String.valueOf(response.optString(i + "required_corrects"))));
                        Log.d(TAG, "Required corrects: " + exercise.getRequiredCorrects());
                        if (response.optString(i + "rc_consecutive").equals("1")) {
                            exercise.setRc_consecutive(true);
                        } else {
                            exercise.setRc_consecutive(false);
                        }
                        Log.d(TAG, "Rc consecutive: " + exercise.isRc_consecutive());
                        exercise.setMaxErrors(Integer.valueOf(String.valueOf(response.optString(i + "max_errors"))));
                        Log.d(TAG, "Max errors: " + exercise.getMaxErrors());
                        if (response.optString(i + "me_consecutive").equals("1")) {
                            exercise.setMe_consecutive(true);
                        } else {
                            exercise.setMe_consecutive(false);
                        }
                        Log.d(TAG, "Me consecutive: " + exercise.isMe_consecutive());
                        Log.d(TAG, "---------------------------------------");
                        mExercises.add(exercise);
                    }
                    ArrayList<LessonClass> lessons = LessonArchive.getAllLessons();
                    for(LessonClass lesson : lessons){
                        ArrayList<ExerciseStat> exerciseStats = lesson.getExercises();
                        try {
                            for (ExerciseStat lessonExercise : exerciseStats) {
                                boolean contains = false;
                                for (Exercise exercise : mExercises) {
                                    if (!contains) {
                                        if (exercise.getTopicName().equals(lessonExercise.getTopicName())) {
                                            if (exercise.getExerciseNum() == lessonExercise.getExerciseNum()) {
                                                contains = true;
                                                Log.d(TAG, "exercise contains lesson exercise");
                                            }
                                        }
                                    }
                                }
                                if (!contains) {
                                    mExercises.add(lessonExercise);
                                    Log.d(TAG, "exercise does not contain lesson exercise");
                                }
                            }
                        } catch (Exception e) { e.printStackTrace(); }
                    }
                    getAllStudentStats();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ExerciseService.getUpdates(teacher.getTeacher_code(), service);
    }
    private void setListViewAdapter(){
        studentEProgressAdapter = new StudentEProgressAdapter(mContext,R.layout.layout_seat_work_item, mExerciseStatsAverage);
        mExerciseListView.setAdapter(studentEProgressAdapter);
        setListViewOnClickListener();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentEProgressActivity.this,
                        TeacherMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
    private void setListViewOnClickListener(){
        mExerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String topic_name = mExerciseStatsAverage.get(position).getTopicName();
                int e_num = mExerciseStatsAverage.get(position).getExerciseNum();
                showStudents(topic_name,e_num);
                txtTitle.setText(Objects.requireNonNull(studentEProgressAdapter.getItem(position)).getTopicName());
            }
        });
    }
    public void showStudents(String topicName, int eNum){
        ArrayList<ExerciseStat> exerciseStats = new ArrayList<>();
        for (ExerciseStat exerciseStat : mExerciseStats){
            if (topicName.equals(exerciseStat.getTopicName())){
                if (eNum == exerciseStat.getExerciseNum()){
                    if (isUpdated(exerciseStat)) {
                        exerciseStats.add(exerciseStat);
                    }
                }
            }
        }
        StudentEProgressAdapter2 studentEProgressAdapter2 = new StudentEProgressAdapter2(mContext,R.layout.layout_user_item2,exerciseStats);
        mExerciseListView.setAdapter(studentEProgressAdapter2);
        mExerciseListView.setOnItemClickListener(null);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListViewAdapter();
                txtTitle.setText(AppConstants.E_PROGRESSES);
            }
        });
    }
}
