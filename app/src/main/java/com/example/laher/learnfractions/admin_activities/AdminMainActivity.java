package com.example.laher.learnfractions.admin_activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.RecyclerViewAdapter;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {
    private static final String TAG = "AdminMainActivity";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<Class> mClasses = new ArrayList<>();

    Button btnBack, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_menu);
        Log.d(TAG, "onCreate: started.");
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
        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("Manage Lessons");
        mClasses.add(ManageLessonsActivity.class);

        initRecyclerView();
    }
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recycler_topics);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls, mClasses);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public class BtnBackListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            finish();
        }
    }
}
