package com.example.laher.learnfractions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.parent_activities.MainFrame;
import com.example.laher.learnfractions.rankings.ClassExamRanksActivity;
import com.example.laher.learnfractions.rankings.ClassExercisesRanksActivity;
import com.example.laher.learnfractions.rankings.ClassSeatWorkRanksActivity;
import com.example.laher.learnfractions.student_activities.StudentMainActivity;
import com.example.laher.learnfractions.teacher.TeacherMainActivity;
import com.example.laher.learnfractions.user_activities.UserMainActivity;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Styles;

public class ClassRanksMainActivity extends MainFrame {
    Context mContext = this;
    //TOOLBAR
    TextView txtTitle;
    Button btnBack;
    Button btnNext;
    //GUI
    Button btnExercises;
    Button btnSeatWorks;
    Button btnExams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_ranks);
        //TOOLBAR
        txtTitle = findViewById(R.id.txtTitle);

        String userType = Storage.load(mContext, Storage.USER_TYPE);

        if (userType.equals(AppConstants.USER)){
            txtTitle.setText(AppConstants.GLOBAL_RANKS);
        } else {
            txtTitle.setText(AppConstants.CLASS_RANKS);
        }
        btnBack = findViewById(R.id.btnBack);

        Styles.bgPaintMainBlue(btnBack);

        ConstraintLayout toolbar = findViewById(R.id.constraintLayoutToolbar);
        Styles.bgPaintMainYellow(toolbar);

        if (AppConstants.TEACHER.equals(userType)) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TeacherMainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        } else if (AppConstants.STUDENT.equals(userType)) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, StudentMainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        } else if (AppConstants.USER.equals(userType)) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserMainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }
        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);
        //GUI
        btnExercises = findViewById(R.id.class_ranks_btnExercises);
        btnExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClassExercisesRanksActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnSeatWorks = findViewById(R.id.class_ranks_btnSeatWorks);
        btnSeatWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClassSeatWorkRanksActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnExams = findViewById(R.id.class_ranks_btnExams);
        btnExams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClassExamRanksActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        Styles.bgPaintMainOrange(btnExercises);
        Styles.bgPaintMainBlueGreen(btnSeatWorks);
        Styles.bgPaintMainBlue(btnExams);
    }

    @Override
    public void onBackPressed() {
        btnBack.performClick();
    }
}
