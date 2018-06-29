package com.example.laher.learnfractions.admin_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.laher.learnfractions.R;


public class AdminMainActivity extends AppCompatActivity {
    Button btnAccountManagement, btnUserProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        btnAccountManagement = findViewById(R.id.admin_main_btnAccountManagement);
        btnUserProgress = findViewById(R.id.admin_main_btnUserProgress);
        btnAccountManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this,
                        AccountManagementActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
