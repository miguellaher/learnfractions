package com.example.laher.learnfractions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class TopicsMenuActivity extends AppCompatActivity {
    private static final String TAG = "TopicsMenuActivity";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_menu);
        Log.d(TAG, "onCreate: started.");

        initImageBitmaps();
    }

    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("waterfall");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/b/bc/Metolius_River_near_Wizard_Falls.jpg");
        mNames.add("river");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("waterfall");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/b/bc/Metolius_River_near_Wizard_Falls.jpg");
        mNames.add("river");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("waterfall");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/b/bc/Metolius_River_near_Wizard_Falls.jpg");
        mNames.add("river");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/b/bc/Metolius_River_near_Wizard_Falls.jpg");
        mNames.add("river");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("waterfall");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/b/bc/Metolius_River_near_Wizard_Falls.jpg");
        mNames.add("river");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("waterfall");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/b/bc/Metolius_River_near_Wizard_Falls.jpg");
        mNames.add("river");

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_topics);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
