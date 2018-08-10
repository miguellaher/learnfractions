package com.example.laher.learnfractions.rankings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.example.laher.learnfractions.model.Lesson;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.rankings.list_adapters.ExerciseRankListAdapter;
import com.example.laher.learnfractions.rankings.list_adapters.ExerciseTop10ListAdapter;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.ExerciseStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.student_activities.ClassRanksActivity;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ClassExerciseRanks extends AppCompatActivity {
    Context mContext = this;
    //GUI
    ListView listView;
    //TOOLBAR
    TextView txtTitle;
    Button btnBack, btnNext;
    //ACTIVITY
    ArrayList<Exercise> mExercises;
    ArrayList<ExerciseStat> mExerciseStats;
    ArrayList<E_StatAverage> mExerciseStatsAverage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_template);
        listView = findViewById(R.id.seatwork_list);
        //TOOLBAR
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppConstants.EXERCISE_RANKS);
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);
        //ACTIVITY
        getLatestExercises();
    }
    private void getAllStudentStats(){
        Service service = new Service("Getting student stats...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try{
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    mExerciseStats = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        ExerciseStat exerciseStat = new ExerciseStat();
                        exerciseStat.setStudent(new Student());
                        exerciseStat.getStudent().setUsername(String.valueOf(response.optString(i + "student_username")));
                        exerciseStat.setTopicName(String.valueOf(response.optString(i + "topic_name")));
                        exerciseStat.setExerciseNum(Integer.valueOf(String.valueOf(response.optString(i + "exercise_num"))));
                        exerciseStat.setTime_spent(Integer.valueOf(String.valueOf(response.optString(i + "time_spent"))));
                        exerciseStat.setErrors(Integer.valueOf(String.valueOf(response.optString(i + "errors"))));
                        exerciseStat.setRequiredCorrects(Integer.valueOf(String.valueOf(response.optString(i + "required_corrects"))));
                        if (response.optString(i + "rc_consecutive").equals("1")){
                            exerciseStat.setRc_consecutive(true);
                        } else {
                            exerciseStat.setRc_consecutive(false);
                        }
                        exerciseStat.setMaxErrors(Integer.valueOf(String.valueOf(response.optString(i + "max_errors"))));
                        if (response.optString(i + "me_consecutive").equals("1")){
                            exerciseStat.setMe_consecutive(true);
                        } else {
                            exerciseStat.setMe_consecutive(false);
                        }
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
                    setListView();
                }catch (Exception e){
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
                    for (int i = 1; i <= item_count; i++) {
                        Exercise exercise = new Exercise();
                        exercise.setTopicName(String.valueOf(response.optString(i + "topic_name")));
                        exercise.setExerciseNum(Integer.valueOf(String.valueOf(response.optString(i + "exercise_num"))));
                        exercise.setRequiredCorrects(Integer.valueOf(String.valueOf(response.optString(i + "required_corrects"))));
                        if (response.optString(i + "rc_consecutive").equals("1")) {
                            exercise.setRc_consecutive(true);
                        } else {
                            exercise.setRc_consecutive(false);
                        }
                        exercise.setMaxErrors(Integer.valueOf(String.valueOf(response.optString(i + "max_errors"))));
                        if (response.optString(i + "me_consecutive").equals("1")) {
                            exercise.setMe_consecutive(true);
                        } else {
                            exercise.setMe_consecutive(false);
                        }
                        mExercises.add(exercise);
                    }
                    ArrayList<Lesson> lessons = LessonArchive.getAllLessons();
                    for(Lesson lesson : lessons){
                        ArrayList<ExerciseStat> exerciseStats = lesson.getExercises();
                        try {
                            for (ExerciseStat lessonExercise : exerciseStats) {
                                boolean contains = false;
                                for (Exercise exercise : mExercises) {
                                    if (!contains) {
                                        if (exercise.getTopicName().equals(lessonExercise.getTopicName())) {
                                            if (exercise.getExerciseNum() == lessonExercise.getExerciseNum()) {
                                                contains = true;
                                            }
                                        }
                                    }
                                }
                                if (!contains) {
                                    mExercises.add(lessonExercise);
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
    private void setListView(){
        ExerciseRankListAdapter exerciseListAdapter = new ExerciseRankListAdapter(mContext, R.layout.exercise_rank_item, mExerciseStatsAverage);
        listView.setAdapter(exerciseListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String topicName = mExerciseStatsAverage.get(position).getTopicName();
                int eNum = mExerciseStatsAverage.get(position).getExerciseNum();
                showStudents(topicName, eNum);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClassRanksActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
    public void showStudents(String topicName, int eNum){
        txtTitle.setText(topicName + " " + eNum);
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
        Collections.sort(exerciseStats);
        exerciseStats.subList(0,9);
        ExerciseTop10ListAdapter exerciseTop10ListAdapter = new ExerciseTop10ListAdapter(mContext, R.layout.exercise_rank_top10, exerciseStats);
        listView.setAdapter(exerciseTop10ListAdapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListView();
                txtTitle.setText(AppConstants.EXERCISE_RANKS);
            }
        });
    }
}
