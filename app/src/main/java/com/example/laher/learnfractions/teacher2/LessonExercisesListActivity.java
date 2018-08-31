package com.example.laher.learnfractions.teacher2;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.lessons.adding_dissimilar.AddingDissimilarExerciseActivity;
import com.example.laher.learnfractions.lessons.adding_similar.AddingSimilarExerciseActivity;
import com.example.laher.learnfractions.lessons.classifying_fractions.ClassifyingFractionsExerciseActivity;
import com.example.laher.learnfractions.lessons.comparing_dissimilar_fractions.ComparingDissimilarExercise2Activity;
import com.example.laher.learnfractions.lessons.comparing_dissimilar_fractions.ComparingDissimilarExerciseActivity;
import com.example.laher.learnfractions.lessons.comparing_fractions.ComparingFractionsExercise2Activity;
import com.example.laher.learnfractions.lessons.comparing_fractions.ComparingFractionsExerciseActivity;
import com.example.laher.learnfractions.lessons.comparing_similar_fractions.ComparingSimilarExercise2Activity;
import com.example.laher.learnfractions.lessons.comparing_similar_fractions.ComparingSimilarExerciseActivity;
import com.example.laher.learnfractions.lessons.converting_fractions.ConvertingFractionsExercise2Activity;
import com.example.laher.learnfractions.lessons.converting_fractions.ConvertingFractionsExerciseActivity;
import com.example.laher.learnfractions.lessons.dividing_fractions.DividingFractionsExerciseActivity;
import com.example.laher.learnfractions.lessons.fraction_meaning.FractionMeaningExercise2Activity;
import com.example.laher.learnfractions.lessons.fraction_meaning.FractionMeaningExerciseActivity;
import com.example.laher.learnfractions.lessons.multiplying_fractions.MultiplyingFractionsExerciseActivity;
import com.example.laher.learnfractions.lessons.non_visual_fraction.NonVisualExercise2Activity;
import com.example.laher.learnfractions.lessons.non_visual_fraction.NonVisualExerciseActivity;
import com.example.laher.learnfractions.lessons.ordering_dissimilar.OrderingDissimilarExercise2Activity;
import com.example.laher.learnfractions.lessons.ordering_dissimilar.OrderingDissimilarExerciseActivity;
import com.example.laher.learnfractions.lessons.ordering_similar.OrderingSimilarExercise2Activity;
import com.example.laher.learnfractions.lessons.ordering_similar.OrderingSimilarExerciseActivity;
import com.example.laher.learnfractions.lessons.solving_mixed.SolvingMixedExerciseActivity;
import com.example.laher.learnfractions.lessons.solving_mixed2.SolvingMixed2ExerciseActivity;
import com.example.laher.learnfractions.lessons.subtracting_dissimilar.SubtractingDissimilarExerciseActivity;
import com.example.laher.learnfractions.lessons.subtracting_similar.SubtractingSimilarExerciseActivity;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.parent_activities.MainFrame;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.teacher.TeacherMainActivity;
import com.example.laher.learnfractions.teacher2.dialog.LessonExerciseUpdateDialog;
import com.example.laher.learnfractions.teacher2.list_adpaters.LessonExercisesListAdapter;
import com.example.laher.learnfractions.util.Probability;
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
        FractionMeaningExercise2Activity fractionMeaningExercise2Activity = new FractionMeaningExercise2Activity();
        NonVisualExerciseActivity nonVisualExerciseActivity = new NonVisualExerciseActivity();
        NonVisualExercise2Activity nonVisualExercise2Activity = new NonVisualExercise2Activity();
        ComparingSimilarExerciseActivity comparingSimilarExerciseActivity = new ComparingSimilarExerciseActivity();
        ComparingSimilarExercise2Activity comparingSimilarExercise2Activity = new ComparingSimilarExercise2Activity();
        ComparingDissimilarExerciseActivity comparingDissimilarExerciseActivity = new ComparingDissimilarExerciseActivity();
        ComparingDissimilarExercise2Activity comparingDissimilarExercise2Activity = new ComparingDissimilarExercise2Activity();
        ComparingFractionsExerciseActivity comparingFractionsExerciseActivity = new ComparingFractionsExerciseActivity();
        ComparingFractionsExercise2Activity comparingFractionsExercise2Activity = new ComparingFractionsExercise2Activity();
        OrderingSimilarExerciseActivity orderingSimilarExerciseActivity = new OrderingSimilarExerciseActivity();
        OrderingSimilarExercise2Activity orderingSimilarExercise2Activity = new OrderingSimilarExercise2Activity();
        OrderingDissimilarExerciseActivity orderingDissimilarExerciseActivity = new OrderingDissimilarExerciseActivity();
        OrderingDissimilarExercise2Activity orderingDissimilarExercise2Activity = new OrderingDissimilarExercise2Activity();
        ClassifyingFractionsExerciseActivity classifyingFractionsExerciseActivity = new ClassifyingFractionsExerciseActivity();
        ConvertingFractionsExerciseActivity convertingFractionsExerciseActivity = new ConvertingFractionsExerciseActivity();
        ConvertingFractionsExercise2Activity convertingFractionsExercise2Activity = new ConvertingFractionsExercise2Activity();
        AddingSimilarExerciseActivity addingSimilarExerciseActivity = new AddingSimilarExerciseActivity();
        AddingDissimilarExerciseActivity addingDissimilarExerciseActivity = new AddingDissimilarExerciseActivity();
        SubtractingSimilarExerciseActivity subtractingSimilarExerciseActivity = new SubtractingSimilarExerciseActivity();
        SubtractingDissimilarExerciseActivity subtractingDissimilarExerciseActivity = new SubtractingDissimilarExerciseActivity();
        MultiplyingFractionsExerciseActivity multiplyingFractionsExerciseActivity = new MultiplyingFractionsExerciseActivity();
        DividingFractionsExerciseActivity dividingFractionsExerciseActivity = new DividingFractionsExerciseActivity();
        SolvingMixedExerciseActivity solvingMixedExerciseActivity = new SolvingMixedExerciseActivity();
        SolvingMixed2ExerciseActivity solvingMixed2ExerciseActivity = new SolvingMixed2ExerciseActivity();

        lessonExercises = new ArrayList<>();
        lessonExercises.add(fractionMeaningExerciseActivity);
        lessonExercises.add(fractionMeaningExercise2Activity);
        lessonExercises.add(nonVisualExerciseActivity);
        lessonExercises.add(nonVisualExercise2Activity);
        lessonExercises.add(comparingSimilarExerciseActivity);
        lessonExercises.add(comparingSimilarExercise2Activity);
        lessonExercises.add(comparingDissimilarExerciseActivity);
        lessonExercises.add(comparingDissimilarExercise2Activity);
        lessonExercises.add(comparingFractionsExerciseActivity);
        lessonExercises.add(comparingFractionsExercise2Activity);
        lessonExercises.add(orderingSimilarExerciseActivity);
        lessonExercises.add(orderingSimilarExercise2Activity);
        lessonExercises.add(orderingDissimilarExerciseActivity);
        lessonExercises.add(orderingDissimilarExercise2Activity);
        lessonExercises.add(classifyingFractionsExerciseActivity);
        lessonExercises.add(convertingFractionsExerciseActivity);
        lessonExercises.add(convertingFractionsExercise2Activity);
        lessonExercises.add(addingSimilarExerciseActivity);
        lessonExercises.add(addingDissimilarExerciseActivity);
        lessonExercises.add(subtractingSimilarExerciseActivity);
        lessonExercises.add(subtractingDissimilarExerciseActivity);
        lessonExercises.add(multiplyingFractionsExerciseActivity);
        lessonExercises.add(dividingFractionsExerciseActivity);
        lessonExercises.add(solvingMixedExerciseActivity);
        lessonExercises.add(solvingMixed2ExerciseActivity);

        listAdapter = new LessonExercisesListAdapter(context,R.layout.exercise_list_item,lessonExercises);
        exerciseListView.setAdapter(listAdapter);

        exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LessonExercise lessonExercise = lessonExercises.get(position);
                final LessonExerciseUpdateDialog updateDialog = new LessonExerciseUpdateDialog(context,lessonExercise);
                updateDialog.show();
                updateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (updateDialog.isUpdated()) {
                            downloadPostedExercises();
                        }
                    }
                });
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
                    Probability probability = exercise.getProbability();
                    if (probability!=null){
                        downloadedExercise.setProbability(probability);
                    }
                    getLessonExercises().set(i, downloadedExercise);
                }
            }
            i++;
        }
        listAdapter.notifyDataSetChanged();
    }
}
