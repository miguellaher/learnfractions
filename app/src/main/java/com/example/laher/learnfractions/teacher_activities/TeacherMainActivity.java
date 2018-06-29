package com.example.laher.learnfractions.teacher_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.laher.learnfractions.LoginActivity;
import com.example.laher.learnfractions.R;

public class TeacherMainActivity extends AppCompatActivity {
    Button btnManageExercises;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        btnManageExercises = findViewById(R.id.teacher_main_btnManageExercises);
        btnManageExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainActivity.this,
                        ExercisesListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
