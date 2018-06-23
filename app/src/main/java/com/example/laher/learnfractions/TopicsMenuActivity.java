package com.example.laher.learnfractions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.model.Lesson;

import java.util.ArrayList;

public class TopicsMenuActivity extends AppCompatActivity {
    private static final String TAG = "TopicsMenuActivity";

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
        ArrayList<Lesson> lessons = LessonArchive.getAllLessons();
        for(Lesson lesson : lessons){
            mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
            mNames.add(lesson.getTitle());
            mClasses.add(lesson.getStartingActivity());
        }

        initRecyclerView();
    }
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_topics);
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
