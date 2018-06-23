package com.example.laher.learnfractions.admin_activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.RecyclerViewAdapter;
import com.example.laher.learnfractions.admin_activities.dialogs.LessonSettingsDialog;
import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.model.Admin;
import com.example.laher.learnfractions.model.Lesson;
import com.example.laher.learnfractions.service.LessonService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class ManageLessonsActivity extends AppCompatActivity {
    Context context = this;

    private static final String TAG = "AdminMainActivity";

    //vars
    private ArrayList<String> lessonTitles;
    private ArrayList<LessonSettingsDialog> lessonSettingsDialog;

    Button btnBack, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_menu);
        Log.d(TAG, "onCreate: started.");
        //postLessons();
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnBack.setOnClickListener(new BtnBackListener());

        go();
    }
    public void go(){
        btnNext.setVisibility(Button.INVISIBLE);
        initImageBitmaps();
    }

    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        lessonTitles = new ArrayList<>();
        lessonSettingsDialog = new ArrayList<>();
        ArrayList<Lesson> lessons = LessonArchive.getAllLessons();
        for(Lesson lesson : lessons) {
            lessonTitles.add(lesson.getTitle());
            lessonSettingsDialog.add(new LessonSettingsDialog(this, lesson.getTitle()));
        }

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recycler_topics);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, lessonTitles, lessonSettingsDialog);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void postLessons(){
        Service service = new Service("Posting lessons...", context, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    if (response.optString("message") != null && response.optString("message").equals("Error inserting lesson.")) {
                        Util.toast(context, "Error inserting lesson.");
                    }else{
                        Util.toast(context, "Lessons posted.");
                    }
                }catch (Exception e){e.printStackTrace();}
            }
        });
        LessonService.postLessons(service);
    }
    private class BtnBackListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ManageLessonsActivity.this, AdminMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}