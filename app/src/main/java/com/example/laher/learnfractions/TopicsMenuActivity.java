package com.example.laher.learnfractions;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.adapters.RecyclerViewAdapter;
import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.dialog_layout.MessageDialog;
import com.example.laher.learnfractions.model.Lesson;
import com.example.laher.learnfractions.student_activities.StudentMainActivity;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;

import java.util.ArrayList;

public class TopicsMenuActivity extends AppCompatActivity {
    Context mContext = this;
    private static final String TAG = "TopicsMenuActivity";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<Class> mClasses = new ArrayList<>();

    //TOOLBAR
    TextView txtTitle;
    Button btnBack, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_menu);
        Log.d(TAG, "onCreate: started.");
        if (!isNetworkAvailable()){
            MessageDialog messageDialog = new MessageDialog(mContext, "Go online to login/register.");
            messageDialog.show();
        }

        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        btnBack.setOnClickListener(new BtnBackListener());
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppConstants.LESSONS);

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
        RecyclerView recyclerView = findViewById(R.id.recycler_topics);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls, mClasses);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public class BtnBackListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if(Storage.load(mContext, Storage.USER_TYPE).equals(AppConstants.STUDENT)){
                Intent intent = new Intent(TopicsMenuActivity.this,
                        StudentMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                finish();
            }
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
