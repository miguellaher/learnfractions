package com.example.laher.learnfractions.teacher2;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.lessons.fraction_meaning.FractionMeaningExerciseActivity;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.parent_activities.MainFrame;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.teacher.TeacherMainActivity;
import com.example.laher.learnfractions.teacher2.dialog.LessonExerciseUpdateDialog;
import com.example.laher.learnfractions.teacher2.list_adpaters.LessonExercisesListAdapter;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class LessonExercisesListActivity extends MainFrame {
    Context context = this;
    final String TITLE = "Exercise List";
    ListView exerciseListView;
    ArrayList<LessonExercise> lessonExercises;
    LessonExercisesListAdapter listAdapter;

    public ArrayList<LessonExercise> getLessonExercises() {
        return lessonExercises;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_exercise_list);
        super.onCreate(savedInstanceState);
        setButtonBack(context,TeacherMainActivity.class);
        Button buttonNext = getButtonNext();
        buttonNext.setVisibility(View.INVISIBLE);
        setTitle(TITLE);
        //ACTIVITY
        exerciseListView = findViewById(R.id.exercise_list);


        FractionMeaningExerciseActivity fractionMeaningExerciseActivity = new FractionMeaningExerciseActivity();


        lessonExercises = new ArrayList<>();
        lessonExercises.add(fractionMeaningExerciseActivity);


        listAdapter = new LessonExercisesListAdapter(context,R.layout.exercise_list_item,lessonExercises);
        exerciseListView.setAdapter(listAdapter);

        exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LessonExercise lessonExercise = lessonExercises.get(position);
                LessonExerciseUpdateDialog updateDialog = new LessonExerciseUpdateDialog(context,lessonExercise);
                updateDialog.show();
            }
        });

        downloadPostedExercises();
    }

    private void downloadPostedExercises(){
        Service service = new Service("Updating exercise list...", context, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try{
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    ArrayList<LessonExercise> exercises = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        String exercise_id = response.optString(i + "exercise_id");
                        String title = response.optString(i + "title");
                        String strItemsSize = response.optString(i + "items_size");
                        String strRCC = response.optString(i + "rc_consecutive");
                        String strMaxWrong = response.optString(i + "max_wrong");
                        String strMEC = response.optString(i + "me_consecutive");

                        if (Util.isNumeric(strItemsSize) && Util.isNumeric(strMaxWrong)) {
                            int itemsSize = Integer.valueOf(strItemsSize);
                            boolean isCorrectsShouldBeConsecutive = false;
                            if (strRCC.equals("1")) {
                                isCorrectsShouldBeConsecutive = true;
                            }
                            int maxWrong = Integer.valueOf(strMaxWrong);
                            boolean isWrongsShouldBeConsecutive = true;
                            if (strMEC.equals("0")) {
                                isWrongsShouldBeConsecutive = false;
                            }

                            LessonExercise exercise = new LessonExercise();
                            exercise.setId(exercise_id);
                            exercise.setExerciseTitle(title);
                            exercise.setItemsSize(itemsSize);
                            exercise.setCorrectsShouldBeConsecutive(isCorrectsShouldBeConsecutive);
                            exercise.setMaxWrong(maxWrong);
                            exercise.setWrongsShouldBeConsecutive(isWrongsShouldBeConsecutive);
                            exercises.add(exercise);
                        }
                    }
                    updateList(exercises);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ExerciseService.getUpdates(context,service);
    }

    private void updateList(ArrayList<LessonExercise> downloadedExercises){
        int i = 0;
        for (LessonExercise exercise : getLessonExercises()){
            for (LessonExercise downloadedExercise : downloadedExercises){
                if (exercise.equals(downloadedExercise)){
                    getLessonExercises().set(i, downloadedExercise);
                }
            }
            i++;
        }
        listAdapter.notifyDataSetChanged();
    }
}
