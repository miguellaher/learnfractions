package com.example.laher.learnfractions.rankings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.ExerciseRank;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.dialog_layout.MessageDialog;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.rankings.list_adapters.ExercisesRankListAdapter;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.ExerciseStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.ClassRanksMainActivity;
import com.example.laher.learnfractions.teacher2.LessonExercisesListActivity;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Encryptor;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ClassExercisesRanksActivity extends AppCompatActivity {
    Context mContext = this;
    //TOOLBAR
    TextView txtTitle;
    Button btnBack;
    Button btnNext;
    //ACTIVITY
    ArrayList<LessonExercise> mLessonExercises;
    ArrayList<ExerciseRank> mExerciseRanks;
    ListView mExercisesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_template);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);

        ConstraintLayout toolbar = findViewById(R.id.constraintLayoutToolbar);
        Styles.bgPaintMainYellow(toolbar);

        Styles.bgPaintMainBlue(btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassExercisesRanksActivity.this,
                        ClassRanksMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);
        txtTitle = findViewById(R.id.txtTitle);

        String userType = Storage.load(mContext, Storage.USER_TYPE);

        String title;
        if (userType.equals(AppConstants.USER)) {
            title = AppConstants.EXERCISE_RANKING;
        } else {
            title = AppConstants.EXERCISE_RANKING;
            String teacherCode = Storage.load(mContext, Storage.TEACHER_CODE);
            teacherCode = Encryptor.decrypt(teacherCode);
            title = title + "\n of Class " + teacherCode;
        }

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(title);
        //ACTIVITY
        mExercisesListView = findViewById(R.id.rank_template_listRanks);

        mLessonExercises = LessonExercisesListActivity.getExercises();

        if (userType.equals(AppConstants.USER)){
            getStudentsStats();
        } else { // if user type is either student or teacher
            updateExams();
        }
    }

    private void updateExams(){
        Service service = new Service("Updating exercise list...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try{
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    ArrayList<LessonExercise> exercises = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        String exercise_id = response.optString(i + "exercise_id");
                        String title = response.optString(i + "title");
                        String strItemsSize = response.optString(i + "items_size");
                        String strMaxItemsSize = response.optString(i + "max_items");
                        String strRCC = response.optString(i + "rc_consecutive");
                        String strMaxWrong = response.optString(i + "max_wrong");
                        String strMEC = response.optString(i + "me_consecutive");
                        String strRangeEditable = response.optString(i + "r_editable");
                        String strMinimum = response.optString(i + "r_minimum");
                        String strMaximum = response.optString(i + "r_maximum");

                        if (Util.isNumeric(strItemsSize) && Util.isNumeric(strMaxWrong) && Util.isNumeric(strMaxItemsSize)
                                && Util.isNumeric(strMinimum) && Util.isNumeric(strMaximum)) {
                            int itemsSize = Integer.valueOf(strItemsSize);
                            int maxItemsSize = Integer.valueOf(strMaxItemsSize);
                            boolean isCorrectsShouldBeConsecutive = false;
                            if (strRCC.equals("1")) {
                                isCorrectsShouldBeConsecutive = true;
                            }
                            int maxWrong = Integer.valueOf(strMaxWrong);
                            boolean isWrongsShouldBeConsecutive = true;
                            if (strMEC.equals("0")) {
                                isWrongsShouldBeConsecutive = false;
                            }
                            boolean isRangeEditable = false;
                            if (strRangeEditable.equals("1")){
                                isRangeEditable = true;
                            }
                            int minimum = Integer.valueOf(strMinimum);
                            int maximum = Integer.valueOf(strMaximum);
                            Range range = new Range(minimum,maximum);

                            LessonExercise exercise = new LessonExercise();
                            exercise.setId(exercise_id);
                            exercise.setExerciseTitle(title);
                            exercise.setItemsSize(itemsSize);
                            exercise.setMaxItemSize(maxItemsSize);
                            exercise.setCorrectsShouldBeConsecutive(isCorrectsShouldBeConsecutive);
                            exercise.setMaxWrong(maxWrong);
                            exercise.setWrongsShouldBeConsecutive(isWrongsShouldBeConsecutive);
                            exercise.setRangeEditable(isRangeEditable);
                            exercise.setRange(range);
                            exercises.add(exercise);
                        }
                    }
                    updateList(exercises);
                    getStudentsStats();
                } catch (Exception e) {
                    e.printStackTrace();
                    getStudentsStats();
                }
            }
        });
        ExerciseService.getUpdates(mContext,service);
    }

    private void getStudentsStats(){
        Service service = new Service("Getting exercises stats...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    ArrayList<LessonExercise> downloadedLessonExercises = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        String studentID = response.optString(i + "stud_id");

                        String userType = Storage.load(mContext, Storage.USER_TYPE);

                        String studentUserName;
                        if (userType.equals(AppConstants.USER)){
                            studentUserName = response.optString(i + "user_username");
                        } else {
                            studentUserName = response.optString(i + "student_username");
                        }

                        String exerciseID = response.optString(i + "exercise_id");
                        String strTimeSpent = response.optString(i + "time_spent");
                        String strErrors = response.optString(i + "errors");
                        String strRequiredCorrects = response.optString(i + "required_corrects");
                        String strRCConsecutive = response.optString(i + "rc_consecutive");
                        String strMaxErrors = response.optString(i + "max_errors");
                        String strMEConsecutive = response.optString(i + "me_consecutive");
                        String strMinimum = response.optString(i + "r_minimum");
                        String strMaximum = response.optString(i + "r_maximum");

                        Student student = new Student();
                        student.setId(studentID);
                        student.setUsername(studentUserName);

                        if (Util.isNumeric(strTimeSpent) && Util.isNumeric(strErrors) && Util.isNumeric(strRequiredCorrects) &&
                                Util.isNumeric(strRCConsecutive) && Util.isNumeric(strMaxErrors) &&
                                Util.isNumeric(strMEConsecutive) && Util.isNumeric(strMinimum) &&
                                Util.isNumeric(strMaximum)) {
                            long timeSpent = Long.valueOf(strTimeSpent);
                            int errors = Integer.valueOf(strErrors);

                            int requiredCorrects = Integer.valueOf(strRequiredCorrects);
                            int intRCConsecutive = Integer.valueOf(strRCConsecutive);
                            boolean rcShouldBeConsecutive = false;
                            if (intRCConsecutive==1){
                                rcShouldBeConsecutive = true;
                            }

                            int maxErrors = Integer.valueOf(strMaxErrors);

                            int intMEConsecutive = Integer.valueOf(strMEConsecutive);
                            boolean eShouldBeConsecutive = true;
                            if (intMEConsecutive==0){
                                eShouldBeConsecutive = false;
                            }

                            int minimum = Integer.valueOf(strMinimum);
                            int maximum = Integer.valueOf(strMaximum);
                            Range range = new Range(minimum,maximum);

                            LessonExercise lessonExercise = new LessonExercise();

                            lessonExercise.setStudent(student);
                            lessonExercise.setId(exerciseID);
                            lessonExercise.setTimeSpent(timeSpent);
                            lessonExercise.setTotalWrongs(errors);
                            lessonExercise.setItemsSize(requiredCorrects);
                            lessonExercise.setCorrectsShouldBeConsecutive(rcShouldBeConsecutive);
                            lessonExercise.setMaxWrong(maxErrors);
                            lessonExercise.setWrongsShouldBeConsecutive(eShouldBeConsecutive);
                            lessonExercise.setRange(range);

                            downloadedLessonExercises.add(lessonExercise);
                        }
                    }

                    ArrayList<LessonExercise> updatedExerciseStats = new ArrayList<>();
                    for (LessonExercise downloadedLessonExercise : downloadedLessonExercises){
                        for (LessonExercise mLessonExercise : mLessonExercises){
                            if (mLessonExercise.equals(downloadedLessonExercise)){
                                if (mLessonExercise.hasTheSameAttributesWith(downloadedLessonExercise)){
                                    updatedExerciseStats.add(downloadedLessonExercise);
                                }
                            }
                        }
                    }

                    mExerciseRanks = new ArrayList<>();
                    for (LessonExercise updatedExerciseStat : updatedExerciseStats){
                        Student student = updatedExerciseStat.getStudent();
                        ExerciseRank exerciseRank = new ExerciseRank(student);
                        if (mExerciseRanks.contains(exerciseRank)){
                            for (ExerciseRank mExerciseRank : mExerciseRanks){
                                if (mExerciseRank.equals(exerciseRank)){
                                    mExerciseRank.addExercise(updatedExerciseStat);
                                }
                            }
                        } else {
                            exerciseRank.addExercise(updatedExerciseStat);
                            mExerciseRanks.add(exerciseRank);
                        }
                    }
                    Collections.sort(mExerciseRanks);
                    setListAdapter(mExerciseRanks);
                } catch (Exception e){
                    e.printStackTrace();
                    MessageDialog messageDialog = new MessageDialog(mContext, "\nService error.\n");
                    messageDialog.show();
                    messageDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            btnBack.performClick();
                        }
                    });
                }
            }
        });
        ExerciseStatService.getAllStats(mContext, service);
    }

    private void updateList(ArrayList<LessonExercise> downloadedExercises){
        int i = 0;
        for (LessonExercise mExercise : mLessonExercises){
            for (LessonExercise downloadedExercise : downloadedExercises){
                if (mExercise.equals(downloadedExercise)){
                    mLessonExercises.set(i, downloadedExercise);
                }
            }
            i++;
        }
    }

    private void setListAdapter(ArrayList<ExerciseRank> exerciseRanks){
        ExercisesRankListAdapter adapter = new ExercisesRankListAdapter(mContext, R.layout.rank_adapter_template, exerciseRanks);
        mExercisesListView.setAdapter(adapter);

        int exerciseRanksSize = exerciseRanks.size();

        while (exerciseRanksSize>4){
            exerciseRanksSize = exerciseRanksSize - 4;
        }

        LinearLayout linearLayoutBackground = findViewById(R.id.linearLayoutBackground);

        if (exerciseRanksSize==1){
            Styles.bgPaintMainBlueGreen(linearLayoutBackground);
        } else if (exerciseRanksSize==2){
            Styles.bgPaintMainBlue(linearLayoutBackground);
        } else if (exerciseRanksSize==3){
            Styles.bgPaintMainYellow(linearLayoutBackground);
        } else if (exerciseRanksSize==4){
            Styles.bgPaintMainOrange(linearLayoutBackground);
        }
    }

    @Override
    public void onBackPressed() {
        btnBack.performClick();
    }
}
