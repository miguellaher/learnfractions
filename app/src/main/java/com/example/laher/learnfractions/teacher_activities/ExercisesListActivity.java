package com.example.laher.learnfractions.teacher_activities;

import android.content.Context;
import android.content.DialogInterface;
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
import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.teacher_activities.dialogs.ExerciseUpdateDialog;
import com.example.laher.learnfractions.teacher_activities.list_adapters.ExerciseListAdapter;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;

import java.util.ArrayList;

public class ExercisesListActivity extends AppCompatActivity {
    private static final String TAG = "E_LIST";
    Context mContext = this;
    ListView exerciseListView;
    ArrayList<Exercise> exercises;
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
                Intent intent = new Intent(ExercisesListActivity.this,
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
        exerciseListView = findViewById(R.id.exercise_list);
        exercises = new ArrayList<>();
        exercises.add(LessonArchive.getLesson(AppConstants.FRACTION_MEANING).getExercises().get(0));
        exercises.add(LessonArchive.getLesson(AppConstants.FRACTION_MEANING).getExercises().get(1));
        exercises.add(LessonArchive.getLesson(AppConstants.NON_VISUAL_FRACTION).getExercises().get(0));
        exercises.add(LessonArchive.getLesson(AppConstants.NON_VISUAL_FRACTION).getExercises().get(1));
        exercises.add(LessonArchive.getLesson(AppConstants.COMPARING_SIMILAR_FRACTIONS).getExercises().get(0));
        exercises.add(LessonArchive.getLesson(AppConstants.COMPARING_SIMILAR_FRACTIONS).getExercises().get(1));
        exercises.add(LessonArchive.getLesson(AppConstants.COMPARING_DISSIMILAR_FRACTIONS).getExercises().get(0));
        exercises.add(LessonArchive.getLesson(AppConstants.COMPARING_DISSIMILAR_FRACTIONS).getExercises().get(1));
        exercises.add(LessonArchive.getLesson(AppConstants.COMPARING_FRACTIONS).getExercises().get(0));
        exercises.add(LessonArchive.getLesson(AppConstants.COMPARING_FRACTIONS).getExercises().get(1));
        exercises.add(LessonArchive.getLesson(AppConstants.ORDERING_SIMILAR).getExercises().get(0));
        exercises.add(LessonArchive.getLesson(AppConstants.ORDERING_SIMILAR).getExercises().get(1));
        exercises.add(LessonArchive.getLesson(AppConstants.ORDERING_DISSIMILAR).getExercises().get(0));
        exercises.add(LessonArchive.getLesson(AppConstants.ORDERING_DISSIMILAR).getExercises().get(1));
        exercises.add(LessonArchive.getLesson(AppConstants.CLASSIFYING_FRACTIONS).getExercises().get(0));
        exercises.add(LessonArchive.getLesson(AppConstants.CONVERTING_FRACTIONS).getExercises().get(0));
        exercises.add(LessonArchive.getLesson(AppConstants.CONVERTING_FRACTIONS).getExercises().get(1));
        exercises.add(LessonArchive.getLesson(AppConstants.ADDING_SIMILAR).getExercises().get(0));
        exercises.add(LessonArchive.getLesson(AppConstants.ADDING_DISSIMILAR).getExercises().get(0));
        exercises.add(LessonArchive.getLesson(AppConstants.SUBTRACTING_SIMILAR).getExercises().get(0));
        exercises.add(LessonArchive.getLesson(AppConstants.SUBTRACTING_DISSIMILAR).getExercises().get(0));
        exercises.add(LessonArchive.getLesson(AppConstants.MULTIPLYING_FRACTIONS).getExercises().get(0));
        exercises.add(LessonArchive.getLesson(AppConstants.DIVIDING_FRACTIONS).getExercises().get(0));
        exercises.add(LessonArchive.getLesson(AppConstants.ADDING_SUBTRACTING_MIXED).getExercises().get(0));
        exercises.add(LessonArchive.getLesson(AppConstants.MULTIPLYING_DIVIDING_MIXED).getExercises().get(0));


        ExerciseListAdapter exerciseListAdapter = new ExerciseListAdapter(mContext, R.layout.layout_user_item, exercises);
        exerciseListView.setAdapter(exerciseListAdapter);
        exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Teacher teacher = new Teacher();
                teacher.setId(Storage.load(mContext, Storage.TEACHER_ID));
                teacher.setTeacher_code(Storage.load(mContext, Storage.TEACHER_CODE));
                Log.d(TAG, Storage.load(mContext, Storage.TEACHER_CODE));
                final ExerciseUpdateDialog exerciseUpdateDialog = new ExerciseUpdateDialog(mContext, exercises.get(position), teacher);
                exerciseUpdateDialog.show();
                exerciseUpdateDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        exerciseUpdateDialog.focusInputRequiredCorrects();
                    }
                });
            }
        });
    }
}
