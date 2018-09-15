package com.example.laher.learnfractions;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.adapters.LessonsViewAdapter;
import com.example.laher.learnfractions.dialog_layout.ConfirmationDialog;
import com.example.laher.learnfractions.dialog_layout.MessageDialog;
import com.example.laher.learnfractions.lessons.adding_dissimilar.AddingDissimilarExerciseActivity;
import com.example.laher.learnfractions.lessons.adding_dissimilar.AddingDissimilarVideoActivity;
import com.example.laher.learnfractions.lessons.adding_similar.AddingSimilarExerciseActivity;
import com.example.laher.learnfractions.lessons.adding_similar.AddingSimilarVideoActivity;
import com.example.laher.learnfractions.lessons.classifying_fractions.ClassifyingFractionsExerciseActivity;
import com.example.laher.learnfractions.lessons.classifying_fractions.ClassifyingFractionsVideoActivity;
import com.example.laher.learnfractions.lessons.comparing_dissimilar_fractions.ComparingDissimilarExercise2Activity;
import com.example.laher.learnfractions.lessons.comparing_dissimilar_fractions.ComparingDissimilarExerciseActivity;
import com.example.laher.learnfractions.lessons.comparing_dissimilar_fractions.ComparingDissimilarVideoActivity;
import com.example.laher.learnfractions.lessons.comparing_fractions.ComparingFractionsExercise2Activity;
import com.example.laher.learnfractions.lessons.comparing_fractions.ComparingFractionsExerciseActivity;
import com.example.laher.learnfractions.lessons.comparing_fractions.ComparingFractionsVideoActivity;
import com.example.laher.learnfractions.lessons.comparing_similar_fractions.ComparingSimilarExercise2Activity;
import com.example.laher.learnfractions.lessons.comparing_similar_fractions.ComparingSimilarExerciseActivity;
import com.example.laher.learnfractions.lessons.comparing_similar_fractions.ComparingSimilarVideoActivity;
import com.example.laher.learnfractions.lessons.converting_fractions.ConvertingFractionsExercise2Activity;
import com.example.laher.learnfractions.lessons.converting_fractions.ConvertingFractionsExerciseActivity;
import com.example.laher.learnfractions.lessons.converting_fractions.ConvertingFractionsVideoActivity;
import com.example.laher.learnfractions.lessons.dividing_fractions.DividingFractionsExerciseActivity;
import com.example.laher.learnfractions.lessons.dividing_fractions.DividingFractionsVideoActivity;
import com.example.laher.learnfractions.lessons.fraction_meaning.FractionMeaningExercise2Activity;
import com.example.laher.learnfractions.lessons.fraction_meaning.FractionMeaningExerciseActivity;
import com.example.laher.learnfractions.lessons.fraction_meaning.FractionMeaningVideoActivity;
import com.example.laher.learnfractions.lessons.multiplying_fractions.MultiplyingFractionsExerciseActivity;
import com.example.laher.learnfractions.lessons.multiplying_fractions.MultiplyingFractionsVideoActivity;
import com.example.laher.learnfractions.lessons.non_visual_fraction.NonVisualExercise2Activity;
import com.example.laher.learnfractions.lessons.non_visual_fraction.NonVisualExerciseActivity;
import com.example.laher.learnfractions.lessons.non_visual_fraction.NonVisualVideoActivity;
import com.example.laher.learnfractions.lessons.ordering_dissimilar.OrderingDissimilarExercise2Activity;
import com.example.laher.learnfractions.lessons.ordering_dissimilar.OrderingDissimilarExerciseActivity;
import com.example.laher.learnfractions.lessons.ordering_dissimilar.OrderingDissimilarVideoActivity;
import com.example.laher.learnfractions.lessons.ordering_similar.OrderingSimilarExercise2Activity;
import com.example.laher.learnfractions.lessons.ordering_similar.OrderingSimilarExerciseActivity;
import com.example.laher.learnfractions.lessons.ordering_similar.OrderingSimilarVideoActivity;
import com.example.laher.learnfractions.lessons.solving_mixed.SolvingMixedExerciseActivity;
import com.example.laher.learnfractions.lessons.solving_mixed.SolvingMixedVideoActivity;
import com.example.laher.learnfractions.lessons.solving_mixed2.SolvingMixed2ExerciseActivity;
import com.example.laher.learnfractions.lessons.solving_mixed2.SolvingMixed2VideoActivity;
import com.example.laher.learnfractions.lessons.subtracting_dissimilar.SubtractingDissimilarExerciseActivity;
import com.example.laher.learnfractions.lessons.subtracting_dissimilar.SubtractingDissimilarVideoActivity;
import com.example.laher.learnfractions.lessons.subtracting_similar.SubtractingSimilarExerciseActivity;
import com.example.laher.learnfractions.lessons.subtracting_similar.SubtractingSimilarVideoActivity;
import com.example.laher.learnfractions.parent_activities.Lesson;
import com.example.laher.learnfractions.student_activities.StudentMainActivity;
import com.example.laher.learnfractions.user_activities.UserMainActivity;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

public class LessonsMenuActivity extends AppCompatActivity {
    Context mContext = this;
    private static final String TAG = "LessonsMenuActivity";
    private static boolean openedOnce = false;
    private ArrayList<Lesson> lessons;

    //TOOLBAR
    TextView txtTitle;
    Button btnBack;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_menu);

        btnBack = findViewById(R.id.btnBack);

        ConstraintLayout toolbar = findViewById(R.id.constraintLayoutToolbar);
        Styles.bgPaintMainYellow(toolbar);

        Styles.bgPaintMainBlue(btnBack);

        if (!isNetworkAvailable()){
            if(!LessonsMenuActivity.openedOnce) {
                LessonsMenuActivity.openedOnce = true;
                final MessageDialog messageDialog = new MessageDialog(mContext, "Go online to login/register.");
                messageDialog.show();
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isNetworkAvailable()) {
                            btnBack.setOnClickListener(new BtnBackListener());
                            btnBack.performClick();
                        } else {
                            messageDialog.show();
                        }
                        Storage.logout(mContext);
                    }
                });
            } else {
                final MessageDialog messageDialog = new MessageDialog(mContext, "Go online to login/register.");
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isNetworkAvailable()) {
                            btnBack.setOnClickListener(new BtnBackListener());
                            btnBack.performClick();
                        } else {
                            messageDialog.show();
                        }
                        Storage.logout(mContext);
                    }
                });
            }
            Storage.logout(mContext);
        } else {
            btnBack.setOnClickListener(new BtnBackListener());
        }
        btnNext = findViewById(R.id.btnNext);
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
        lessons = new ArrayList<>();

        //Lessons
        Lesson fractionMeaningLesson = new Lesson("Fraction Meaning");
        fractionMeaningLesson.addActivity(new FractionMeaningVideoActivity());
        fractionMeaningLesson.addActivity(new FractionMeaningExerciseActivity());
        fractionMeaningLesson.addActivity(new FractionMeaningExercise2Activity());

        Lesson nonVisualLesson = new Lesson("Non-Visual Fractions");
        nonVisualLesson.addActivity(new NonVisualVideoActivity());
        nonVisualLesson.addActivity(new NonVisualExerciseActivity());
        nonVisualLesson.addActivity(new NonVisualExercise2Activity());

        Lesson comparingSimilarLesson = new Lesson("Comparing Similar Fractions");
        comparingSimilarLesson.addActivity(new ComparingSimilarVideoActivity());
        comparingSimilarLesson.addActivity(new ComparingSimilarExerciseActivity());
        comparingSimilarLesson.addActivity(new ComparingSimilarExercise2Activity());

        Lesson comparingDissimilarLesson = new Lesson("Comparing Dissimilar Fractions");
        comparingDissimilarLesson.addActivity(new ComparingDissimilarVideoActivity());
        comparingDissimilarLesson.addActivity(new ComparingDissimilarExerciseActivity());
        comparingDissimilarLesson.addActivity(new ComparingDissimilarExercise2Activity());

        Lesson comparingFractionsLesson = new Lesson("Comparing Fractions");
        comparingFractionsLesson.addActivity(new ComparingFractionsVideoActivity());
        comparingFractionsLesson.addActivity(new ComparingFractionsExerciseActivity());
        comparingFractionsLesson.addActivity(new ComparingFractionsExercise2Activity());

        Lesson orderingSimilarLesson = new Lesson("Ordering Similar Fractions");
        orderingSimilarLesson.addActivity(new OrderingSimilarVideoActivity());
        orderingSimilarLesson.addActivity(new OrderingSimilarExerciseActivity());
        orderingSimilarLesson.addActivity(new OrderingSimilarExercise2Activity());

        Lesson orderingDissimilarLesson = new Lesson("Ordering Dissimilar Fractions");
        orderingDissimilarLesson.addActivity(new OrderingDissimilarVideoActivity());
        orderingDissimilarLesson.addActivity(new OrderingDissimilarExerciseActivity());
        orderingDissimilarLesson.addActivity(new OrderingDissimilarExercise2Activity());

        Lesson classifyingFractionsLesson = new Lesson("Classifying Fractions");
        classifyingFractionsLesson.addActivity(new ClassifyingFractionsVideoActivity());
        classifyingFractionsLesson.addActivity(new ClassifyingFractionsExerciseActivity());

        Lesson convertingFractionsLesson = new Lesson("Converting Fractions");
        convertingFractionsLesson.addActivity(new ConvertingFractionsVideoActivity());
        convertingFractionsLesson.addActivity(new ConvertingFractionsExerciseActivity());
        convertingFractionsLesson.addActivity(new ConvertingFractionsExercise2Activity());

        Lesson addingSimilarLesson = new Lesson("Adding Similar Fractions");
        addingSimilarLesson.addActivity(new AddingSimilarVideoActivity());
        addingSimilarLesson.addActivity(new AddingSimilarExerciseActivity());

        Lesson addingDissimilarLesson = new Lesson("Adding Dissimilar Fractions");
        addingDissimilarLesson.addActivity(new AddingDissimilarVideoActivity());
        addingDissimilarLesson.addActivity(new AddingDissimilarExerciseActivity());

        Lesson subtractingSimilarLesson = new Lesson("Subtracting Similar Fractions");
        subtractingSimilarLesson.addActivity(new SubtractingSimilarVideoActivity());
        subtractingSimilarLesson.addActivity(new SubtractingSimilarExerciseActivity());

        Lesson subtractingDissimilarLesson = new Lesson("Subtracting Dissimilar Fractions");
        subtractingDissimilarLesson.addActivity(new SubtractingDissimilarVideoActivity());
        subtractingDissimilarLesson.addActivity(new SubtractingDissimilarExerciseActivity());

        Lesson multiplyingLesson = new Lesson("Multiplying Fractions");
        multiplyingLesson.addActivity(new MultiplyingFractionsVideoActivity());
        multiplyingLesson.addActivity(new MultiplyingFractionsExerciseActivity());

        Lesson dividingLesson = new Lesson("Dividing Fractions");
        dividingLesson.addActivity(new DividingFractionsVideoActivity());
        dividingLesson.addActivity(new DividingFractionsExerciseActivity());

        Lesson solvingMixedLesson = new Lesson("Adding and Subtracting with Mixed Fraction");
        solvingMixedLesson.addActivity(new SolvingMixedVideoActivity());
        solvingMixedLesson.addActivity(new SolvingMixedExerciseActivity());

        Lesson solvingMixed2Lesson = new Lesson("Multiplying and Dividing with Mixed Fraction");
        solvingMixed2Lesson.addActivity(new SolvingMixed2VideoActivity());
        solvingMixed2Lesson.addActivity(new SolvingMixed2ExerciseActivity());

        //ArrayList for Adapter
        lessons.add(fractionMeaningLesson);
        lessons.add(nonVisualLesson);
        lessons.add(comparingSimilarLesson);
        lessons.add(comparingDissimilarLesson);
        lessons.add(comparingFractionsLesson);
        lessons.add(orderingSimilarLesson);
        lessons.add(orderingDissimilarLesson);
        lessons.add(classifyingFractionsLesson);
        lessons.add(convertingFractionsLesson);
        lessons.add(addingSimilarLesson);
        lessons.add(addingDissimilarLesson);
        lessons.add(subtractingSimilarLesson);
        lessons.add(subtractingDissimilarLesson);
        lessons.add(multiplyingLesson);
        lessons.add(dividingLesson);
        lessons.add(solvingMixedLesson);
        lessons.add(solvingMixed2Lesson);

        initRecyclerView();
    }
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recycler view.");
        RecyclerView recyclerView = findViewById(R.id.recycler_topics);
        ArrayList<Lesson> lessons = this.lessons;
        LessonsViewAdapter adapter = new LessonsViewAdapter(this, lessons);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public class BtnBackListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (!Storage.isEmpty()) {
                switch (Storage.load(mContext, Storage.USER_TYPE)) {
                    case AppConstants.STUDENT: {
                        Intent intent = new Intent(LessonsMenuActivity.this,
                                StudentMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                    }
                    case AppConstants.USER: {
                        Intent intent = new Intent(LessonsMenuActivity.this,
                                UserMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                    }
                    default:
                        final ConfirmationDialog confirmationDialog = new ConfirmationDialog(mContext, "Are you sure you want to exit app?");
                        confirmationDialog.show();
                        confirmationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                if (confirmationDialog.isConfirmed()) {
                                    finishAffinity();
                                }
                            }
                        });
                        break;
                }
            } else {
                Intent intent = new Intent(LessonsMenuActivity.this,
                        LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    public void onBackPressed() {
        btnBack.performClick();
    }
}
