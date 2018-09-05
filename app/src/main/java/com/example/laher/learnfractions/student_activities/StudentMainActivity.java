package com.example.laher.learnfractions.student_activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.ChapterExamListActivity;
import com.example.laher.learnfractions.LoginActivity;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.SeatWorkListActivity;
import com.example.laher.learnfractions.LessonsMenuActivity;
import com.example.laher.learnfractions.dialog_layout.ConfirmationDialog;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;

public class StudentMainActivity extends AppCompatActivity {
    Context mContext = this;
    Button btnLessons, btnSeatWorks, btnChapterExam, btnClassRanks;

    //TOOLBAR
    TextView txtTitle;
    Button btnBack, btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        //TOOLBAR
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppConstants.STUDENT_MAIN);
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
                            Intent intent = new Intent(StudentMainActivity.this,
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
        //GUI
        btnLessons = findViewById(R.id.student_main_btnLessons);
        btnLessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentMainActivity.this,
                        LessonsMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnSeatWorks = findViewById(R.id.student_main_btnSeatworks);
        btnSeatWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentMainActivity.this,
                        SeatWorkListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnChapterExam = findViewById(R.id.student_main_btnChapterExam);
        btnChapterExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentMainActivity.this,
                        ChapterExamListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnClassRanks = findViewById(R.id.student_main_btnClassRanks);
        btnClassRanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentMainActivity.this,
                        ClassRanksActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        /*TEMPORARY
        btnSeatWorks.setEnabled(false);
        btnChapterExam.setEnabled(false);
        btnClassRanks.setEnabled(false);*/

    }
}
