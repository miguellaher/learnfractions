package com.example.laher.learnfractions.teacher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.LoginActivity;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.SeatWorkListActivity;
import com.example.laher.learnfractions.dialog_layout.ConfirmationDialog;
import com.example.laher.learnfractions.teacher2.LessonExercisesListActivity;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;

public class TeacherMainActivity extends AppCompatActivity {
    Context mContext = this;
    Button btnManageExercises, btnManageSeatWorks, btnManageExams, btnCheckSWProgress, btnCheckEProgress, btnCheckExamProgress;
    Button btnBack, btnNext;
    TextView txtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConfirmationDialog confirmationDialog = new ConfirmationDialog(mContext, AppConstants.LOGOUT_CONFIRMATION);
                confirmationDialog.show();
                confirmationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (confirmationDialog.isConfirmed()){
                            Storage.logout(mContext);
                            Intent intent = new Intent(TeacherMainActivity.this,
                                    LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
        btnBack.setText(AppConstants.LOG_OUT);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppConstants.TEACHER_MAIN);
        //ACTIVITY
        btnManageExercises = findViewById(R.id.teacher_main_btnManageExercises);
        btnManageExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainActivity.this,
                        LessonExercisesListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnManageSeatWorks = findViewById(R.id.teacher_main_btnManageSeatWorks);
        btnManageSeatWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainActivity.this,
                        SeatWorkListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnManageExams = findViewById(R.id.teacher_main_btnManageExams);
        btnManageExams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainActivity.this,
                        ManageExamsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnCheckSWProgress = findViewById(R.id.teacher_main_btnCheckSWProgress);
        btnCheckSWProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainActivity.this,
                        StudentSWProgressActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnCheckEProgress = findViewById(R.id.teacher_main_btnCheckEProgress);
        btnCheckEProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainActivity.this,
                        StudentEProgressActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        btnCheckExamProgress = findViewById(R.id.teacher_main_btnCheckExamProgress);
        btnCheckExamProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainActivity.this,
                        StudentExamProgressActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        /*TEMPORARY
        btnManageSeatWorks.setEnabled(false);
        btnManageExams.setEnabled(false);
        btnCheckSWProgress.setEnabled(false);
        btnCheckEProgress.setEnabled(false);
        btnCheckExamProgress.setEnabled(false);*/
    }
}
