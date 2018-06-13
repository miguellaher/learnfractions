package com.example.laher.learnfractions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.laher.learnfractions.adding_dissimilar.AddingDissimilarExerciseActivity;
import com.example.laher.learnfractions.adding_dissimilar.AddingDissimilarVideoActivity;
import com.example.laher.learnfractions.adding_similar.AddingSimilarExerciseActivity;
import com.example.laher.learnfractions.adding_similar.AddingSimilarVideoActivity;
import com.example.laher.learnfractions.classifying_fractions.ClassifyingFractionsExerciseActivity;
import com.example.laher.learnfractions.classifying_fractions.ClassifyingFractionsVideoActivity;
import com.example.laher.learnfractions.comparing_dissimilar_fractions.ComparingDissimilarExercise2Activity;
import com.example.laher.learnfractions.comparing_dissimilar_fractions.ComparingDissimilarExerciseActivity;
import com.example.laher.learnfractions.comparing_dissimilar_fractions.ComparingDissimilarVideoActivity;
import com.example.laher.learnfractions.comparing_fractions.ComparingFractionsExercise2Activity;
import com.example.laher.learnfractions.comparing_fractions.ComparingFractionsVideoActivity;
import com.example.laher.learnfractions.comparing_similar_fractions.ComparingSimilarVideoActivity;
import com.example.laher.learnfractions.converting_fractions.ConvertingFractionsExercise2Activity;
import com.example.laher.learnfractions.converting_fractions.ConvertingFractionsExerciseActivity;
import com.example.laher.learnfractions.converting_fractions.ConvertingFractionsVideoActivity;
import com.example.laher.learnfractions.dividing_fractions.DividingFractionsExerciseActivity;
import com.example.laher.learnfractions.dividing_fractions.DividingFractionsVideoActivity;
import com.example.laher.learnfractions.fractionmeaning.FractionMeaningVideoActivity;
import com.example.laher.learnfractions.multiplying_fractions.MultiplyingFractionsExerciseActivity;
import com.example.laher.learnfractions.multiplying_fractions.MultiplyingFractionsVideoActivity;
import com.example.laher.learnfractions.non_visual_fraction.NonVisualVideoActivity;
import com.example.laher.learnfractions.ordering_dissimilar.OrderingDissimilarExercise2Activity;
import com.example.laher.learnfractions.ordering_dissimilar.OrderingDissimilarExerciseActivity;
import com.example.laher.learnfractions.ordering_dissimilar.OrderingDissimilarVideoActivity;
import com.example.laher.learnfractions.ordering_similar.OrderingSimilarVideoActivity;
import com.example.laher.learnfractions.subtracting_dissimilar.SubtractingDissimilarExerciseActivity;
import com.example.laher.learnfractions.subtracting_dissimilar.SubtractingDissimilarVideoActivity;
import com.example.laher.learnfractions.subtracting_similar.SubtractingSimilarExerciseActivity;
import com.example.laher.learnfractions.subtracting_similar.SubtractingSimilarVideoActivity;

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
        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("Fraction Meaning");
        mClasses.add(FractionMeaningVideoActivity.class);

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("Non-Visual Fraction");
        mClasses.add(NonVisualVideoActivity.class);

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("Comparing Similar Fractions");
        mClasses.add(ComparingSimilarVideoActivity.class);

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("Comparing Dissimilar Fractions");
        mClasses.add(ComparingDissimilarVideoActivity.class);

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("Comparing Fractions");
        mClasses.add(ComparingFractionsVideoActivity.class);

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("Ordering Similar Fractions");
        mClasses.add(OrderingSimilarVideoActivity.class);

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("Ordering Dissimilar Fractions");
        mClasses.add(OrderingDissimilarVideoActivity.class);

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("Classifying Fractions");
        mClasses.add(ClassifyingFractionsVideoActivity.class);

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("Converting Fractions");
        mClasses.add(ConvertingFractionsVideoActivity.class);

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("Adding Similar Fractions");
        mClasses.add(AddingSimilarVideoActivity.class);

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("Adding Dissimilar Fractions");
        mClasses.add(AddingDissimilarVideoActivity.class);

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("Subtracting Similar Fractions");
        mClasses.add(SubtractingSimilarVideoActivity.class);

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("Subtracting Dissimilar Fractions");
        mClasses.add(SubtractingDissimilarVideoActivity.class);

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("Multiplying Fractions");
        mClasses.add(MultiplyingFractionsVideoActivity.class);

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Salto_del_Angel-Canaima-Venezuela08.JPG/1200px-Salto_del_Angel-Canaima-Venezuela08.JPG");
        mNames.add("Dividing Fractions");
        mClasses.add(DividingFractionsVideoActivity.class);

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
