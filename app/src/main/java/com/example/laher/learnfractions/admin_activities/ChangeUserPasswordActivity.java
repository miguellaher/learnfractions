package com.example.laher.learnfractions.admin_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.util.AppConstants;

import java.util.ArrayList;

public class ChangeUserPasswordActivity extends AppCompatActivity {
    Button btnUser, btnStudent, btnTeacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_password);
        btnUser = findViewById(R.id.change_user_password_btnUser);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeUserPasswordActivity.this,
                        UserListActivity.class);
                intent.putExtra("user_type", AppConstants.USER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnStudent = findViewById(R.id.change_user_password_btnStudent);
        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeUserPasswordActivity.this,
                        UserListActivity.class);
                intent.putExtra("user_type", AppConstants.STUDENT);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnTeacher = findViewById(R.id.change_user_password_btnTeacher);
        btnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeUserPasswordActivity.this,
                        UserListActivity.class);
                intent.putExtra("user_type", AppConstants.TEACHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
