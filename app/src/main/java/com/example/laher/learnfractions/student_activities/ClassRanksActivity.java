package com.example.laher.learnfractions.student_activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.rankings.ClassExerciseRanks;
import com.example.laher.learnfractions.teacher_activities.StudentEProgressActivity;
import com.example.laher.learnfractions.util.AppConstants;

public class ClassRanksActivity extends AppCompatActivity {
    Context mContext = this;
    //TOOLBAR
    TextView txtTitle;
    Button btnBack, btnNext;
    //GUI
    Button btnExercises, btnSeatWorks, btnExams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_ranks);
        //TOOLBAR
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppConstants.CLASS_RANKS);
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
        //GUI
        btnExercises = findViewById(R.id.class_ranks_btnExercises);
        btnExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClassExerciseRanks.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnSeatWorks = findViewById(R.id.class_ranks_btnSeatWorks);
        btnExams = findViewById(R.id.class_ranks_btnExams);
    }
}
