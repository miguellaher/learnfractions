package com.example.laher.learnfractions.teacher_activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
    Context mContext = this;
    ListView exerciseListView;
    ArrayList<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        exerciseListView = findViewById(R.id.exercise_list);
        exercises = new ArrayList<>();
        exercises.add(LessonArchive.getLesson(AppConstants.FRACTION_MEANING).getExercises().get(0));

        ExerciseListAdapter exerciseListAdapter = new ExerciseListAdapter(mContext, R.layout.layout_user_item, exercises);
        exerciseListView.setAdapter(exerciseListAdapter);
        exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Teacher teacher = new Teacher();
                teacher.setId(Storage.load(mContext));
                ExerciseUpdateDialog exerciseUpdateDialog = new ExerciseUpdateDialog(mContext, exercises.get(position), teacher);
                exerciseUpdateDialog.show();
            }
        });
    }
}
