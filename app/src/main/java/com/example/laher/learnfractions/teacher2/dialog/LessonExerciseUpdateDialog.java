package com.example.laher.learnfractions.teacher2.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.ActivityUtil;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

public class LessonExerciseUpdateDialog extends Dialog {
    private Context context;
    private LessonExercise lessonExercise;
    private static final String TAG = "LEUD";

    private TextView txtLabel1, txtLabel2;
    private EditText inputTitle, inputItemsSize, inputMaxWrong, inputMinimum, inputMaximum;
    private LinearLayout linearRange;
    private CheckBox chk_RC_consecutive, chk_ME_consecutive;
    private Button btnUpdate;
    private boolean updated;

    public boolean isUpdated() {
        return updated;
    }

    private LessonExercise getLessonExercise() {
        return lessonExercise;
    }

    public LessonExerciseUpdateDialog(@NonNull Context context, LessonExercise lessonExercise) {
        super(context);
        this.context = context;
        this.lessonExercise = lessonExercise;

        setGui();

        updated = false;
        String title = lessonExercise.getExerciseTitle();
        String label1 = "Items Size:";
        String label2 = "Max Wrongs:";
        int itemsSize = lessonExercise.getItemsSize();
        String strItemsSize = String.valueOf(itemsSize);
        int maxWrong = lessonExercise.getMaxWrong();
        String strMaxWrong = String.valueOf(maxWrong);
        boolean isCorrectsShouldBeConsecutive = lessonExercise.isCorrectsShouldBeConsecutive();
        boolean isWrongsShouldBeConsecutive = lessonExercise.isWrongsShouldBeConsecutive();
        Range range = lessonExercise.getRange();
        int minimum = range.getMinimum();
        int maximum = range.getMaximum();
        String strMinimum = String.valueOf(minimum);
        String strMaximum = String.valueOf(maximum);

        inputTitle.setHint(title);
        inputTitle.setText(title);
        txtLabel1.setText(label1);
        txtLabel2.setText(label2);
        inputItemsSize.setHint(strItemsSize);
        inputMaxWrong.setHint(strMaxWrong);
        if (getLessonExercise().isRangeEditable()){
            inputMinimum.setHint(strMinimum);
            inputMaximum.setHint(strMaximum);
        } else {
            inputMinimum.setEnabled(false);
            inputMaximum.setEnabled(false);
            inputMaxWrong.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }
        chk_RC_consecutive.setChecked(isCorrectsShouldBeConsecutive);
        chk_ME_consecutive.setChecked(isWrongsShouldBeConsecutive);

        if (!lessonExercise.isRangeEditable()){
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) linearRange.getLayoutParams();
            params.height = 0;
            linearRange.setLayoutParams(params);
        }

        inputItemsSize.requestFocus();
        btnUpdate.setOnClickListener(new ButtonUpdateListener());
    }

    private void setGui(){
        setContentView(R.layout.layout_exercise_update);
        inputTitle = findViewById(R.id.exercise_update_inputTitle);
        txtLabel1 = findViewById(R.id.exercise_update_txtLabel1);
        txtLabel2 = findViewById(R.id.exercise_update_txtLabel2);
        inputItemsSize = findViewById(R.id.exercise_update_inputRequiredCorrects);
        inputMaxWrong = findViewById(R.id.exercise_update_inputMaxErrors);
        linearRange = findViewById(R.id.exercise_update_linearRange);
        inputMinimum = findViewById(R.id.exercise_update_inputMinimum);
        inputMaximum = findViewById(R.id.exercise_update_inputMaximum);
        chk_RC_consecutive = findViewById(R.id.exercise_update_chk_RC_consecutive);
        chk_ME_consecutive = findViewById(R.id.exercise_update_chk_ME_consecutive);
        btnUpdate = findViewById(R.id.exercise_update_btnUpdate);
    }

    private void hideViews(){ // to be used later
        chk_RC_consecutive.setVisibility(View.INVISIBLE);
        txtLabel2.setVisibility(View.INVISIBLE);
        inputMaxWrong.setVisibility(View.INVISIBLE);
        chk_ME_consecutive.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setOnShowListener(@Nullable OnShowListener listener) {
        inputItemsSize.requestFocus();
        super.setOnShowListener(listener);
    }

    private class ButtonUpdateListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            ActivityUtil.hideKeyboardFrom(context,v);
            if (checkFields()) {
                LessonExercise thisLessonExercise = getLessonExercise();
                String id = thisLessonExercise.getId();
                String title = inputTitle.getText().toString().trim();

                // <---------------------------------------------------> //
                //        setting the maximum number of item size
                Probability lessonExerciseProbability = thisLessonExercise.getProbability();
                String equation;
                int maxItemSize = 0;
                Range range = new Range();
                if (lessonExerciseProbability!=null) {
                    equation = lessonExerciseProbability.getEquation();

                    if (thisLessonExercise.isRangeEditable()) {
                        String strInputMinimum = inputMinimum.getText().toString().trim();
                        String strInputMaximum = inputMaximum.getText().toString().trim();
                        int minimum = Integer.valueOf(strInputMinimum);
                        int maximum = Integer.valueOf(strInputMaximum);
                        range = new Range(minimum, maximum);
                    }

                    Probability probability = new Probability(equation, range);
                    maxItemSize = probability.getOutComes(); // number of outcomes
                }
                // <---------------------------------------------------> //

                
                String strInputItemsSize = inputItemsSize.getText().toString().trim();
                int intInputItemsSize = Integer.valueOf(strInputItemsSize);
                String strInputMaxWrong = inputMaxWrong.getText().toString().trim();
                int intInputMaxWrong = Integer.valueOf(strInputMaxWrong);
                boolean isRcChecked = chk_RC_consecutive.isChecked();
                boolean isMeChecked = chk_ME_consecutive.isChecked();
                final LessonExercise lessonExercise = new LessonExercise();
                lessonExercise.setId(id);
                lessonExercise.setExerciseTitle(title);
                lessonExercise.setMaxItemSize(maxItemSize);
                lessonExercise.setItemsSize(intInputItemsSize);
                lessonExercise.setMaxWrong(intInputMaxWrong);
                lessonExercise.setCorrectsShouldBeConsecutive(isRcChecked);
                lessonExercise.setWrongsShouldBeConsecutive(isMeChecked);
                lessonExercise.setRangeEditable(getLessonExercise().isRangeEditable());
                lessonExercise.setRange(range);
                Service service = new Service("Posting lesson exercise...", context, new ServiceResponse() {
                    @Override
                    public void postExecute(JSONObject response) {
                        try {
                            String message = response.optString("message");
                            Log.d(TAG,message);
                            Util.toast(context, message);
                            Log.d(TAG,"finished");
                            updated = true;
                            int itemsSize = lessonExercise.getItemsSize();
                            int maxWrong = lessonExercise.getMaxWrong();
                            String strItemsSize = String.valueOf(itemsSize);
                            String strMaxWrong = String.valueOf(maxWrong);
                            inputItemsSize.setText(strItemsSize);
                            inputItemsSize.setHint(strItemsSize);
                            inputMaxWrong.setHint(strMaxWrong);
                            Range range = lessonExercise.getRange();
                            int minimum = range.getMinimum();
                            int maximum = range.getMaximum();
                            String strMinimum = String.valueOf(minimum);
                            String strMaximum = String.valueOf(maximum);
                            if (lessonExercise.isRangeEditable()){
                                inputMinimum.setHint(strMinimum);
                                inputMaximum.setHint(strMaximum);
                            }
                            String title = lessonExercise.getExerciseTitle();
                            inputTitle.setHint(title);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(TAG,e.toString());
                        }
                    }
                });
                ExerciseService.post(context, lessonExercise, service);
            }
        }
    }

    private boolean checkFields(){ // true means go
        boolean isAFieldEmpty = false;
        boolean errorDetected = false;
        if (Util.isEditTextEmpty(inputTitle)){
            Styles.shakeAnimate(inputTitle);
            isAFieldEmpty = true;
        }
        if (Util.isEditTextEmpty(inputItemsSize)){
            Styles.shakeAnimate(inputItemsSize);
            isAFieldEmpty = true;
        }
        if (Util.isEditTextEmpty(inputMaxWrong)){
            Styles.shakeAnimate(inputMaxWrong);
            isAFieldEmpty = true;
        }
        if (lessonExercise.isRangeEditable()){
            if (Util.isEditTextEmpty(inputMinimum)){
                Styles.shakeAnimate(inputMinimum);
                isAFieldEmpty = true;
            }
            if (Util.isEditTextEmpty(inputMaximum)){
                Styles.shakeAnimate(inputMaximum);
                isAFieldEmpty = true;
            }
            String strInputMinimum = inputMinimum.getText().toString().trim();
            String strInputMaximum = inputMaximum.getText().toString().trim();
            int intInputMinimum = Integer.valueOf(strInputMinimum);
            int intInputMaximum = Integer.valueOf(strInputMaximum);

            if (intInputMinimum>=intInputMaximum){
                Styles.shakeAnimate(inputMinimum);
                Styles.shakeAnimate(inputMaximum);
                String message = "Input a larger number on the maximum field.";
                Util.toast(context, message);
                errorDetected = true;
            }
        }
        return !isAFieldEmpty && !errorDetected;
    }
}
