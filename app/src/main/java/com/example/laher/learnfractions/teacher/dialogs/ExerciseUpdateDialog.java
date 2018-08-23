package com.example.laher.learnfractions.teacher.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.Exercise;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

public class ExerciseUpdateDialog extends Dialog {
    private Context mContext;
    private static final String TAG = "E_Update_D";
    private TextView txtTopicName, txtLabel2;
    private EditText inputRequiredCorrects, inputMaxErrors;
    private CheckBox chk_RC_consecutive, chk_ME_consecutive;
    private Button btnUpdate;

    private Exercise mExercise;
    private Teacher mTeacher;

    public ExerciseUpdateDialog(@NonNull Context context, Exercise exercise, Teacher teacher) {
        super(context);
        mContext = context;
        mExercise = exercise;
        mTeacher = teacher;

        setGui();

        txtTopicName.setText(exercise.getTopicName());
    }

    private void setGui(){
        setContentView(R.layout.layout_exercise_update);
        txtLabel2 = findViewById(R.id.exercise_update_txtLabel2);
        inputRequiredCorrects = findViewById(R.id.exercise_update_inputRequiredCorrects);
        inputMaxErrors = findViewById(R.id.exercise_update_inputMaxErrors);
        chk_RC_consecutive = findViewById(R.id.exercise_update_chk_RC_consecutive);
        chk_ME_consecutive = findViewById(R.id.exercise_update_chk_ME_consecutive);
        btnUpdate = findViewById(R.id.exercise_update_btnUpdate);

        if (exerciseHasNoME()){
            hideViews();
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!inputRequiredCorrects.getText().toString().trim().equals("")){
                        Service service = new Service("Posting exercise...", mContext, new ServiceResponse() {
                            @Override
                            public void postExecute(JSONObject response) {
                                Util.toast(mContext, response.optString("message"));
                                dismiss();
                            }
                        });
                        Exercise exercise = new Exercise();
                        exercise.setTopicName(mExercise.getTopicName());
                        exercise.setExerciseNum(mExercise.getExerciseNum());
                        exercise.setRequiredCorrects(Integer.valueOf(String.valueOf(inputRequiredCorrects.getText())));
                        //ExerciseService.create(mTeacher, exercise, service);
                        Log.d(TAG, "required corrects: " + exercise.getRequiredCorrects() + ", consecutive: " + exercise.isRc_consecutive() +
                                "; max errors: " + exercise.getMaxErrors() + ", consecutive: " + exercise.isMe_consecutive());
                    } else {
                        Styles.shakeAnimate(inputRequiredCorrects);
                    }
                }
            });
        } else {
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkErrors()) {
                        Service service = new Service("Posting exercise...", mContext, new ServiceResponse() {
                            @Override
                            public void postExecute(JSONObject response) {
                                Util.toast(mContext, response.optString("message"));
                                dismiss();
                            }
                        });
                        Exercise exercise = new Exercise();
                        exercise.setTopicName(mExercise.getTopicName());
                        exercise.setExerciseNum(mExercise.getExerciseNum());
                        exercise.setRequiredCorrects(Integer.valueOf(String.valueOf(inputRequiredCorrects.getText())));
                        if (chk_RC_consecutive.isChecked()) {
                            exercise.setRc_consecutive(true);
                        } else {
                            exercise.setRc_consecutive(false);
                        }
                        exercise.setMaxErrors(Integer.valueOf(String.valueOf(inputMaxErrors.getText())));
                        if (chk_ME_consecutive.isChecked()) {
                            exercise.setMe_consecutive(true);
                        } else {
                            exercise.setMe_consecutive(false);
                        }
                        Log.d(TAG, "required corrects: " + exercise.getRequiredCorrects() + ", consecutive: " + exercise.isRc_consecutive() +
                        "; max errors: " + exercise.getMaxErrors() + ", consecutive: " + exercise.isMe_consecutive());
                        //ExerciseService.create(mTeacher, exercise, service);
                    }
                }
            });
        }
    }

    private boolean exerciseHasNoME(){
        return mExercise.getMaxErrors() < 1;
    }

    private void hideViews(){
        chk_RC_consecutive.setVisibility(View.INVISIBLE);
        txtLabel2.setVisibility(View.INVISIBLE);
        inputMaxErrors.setVisibility(View.INVISIBLE);
        chk_ME_consecutive.setVisibility(View.INVISIBLE);
    }

    private boolean checkErrors(){
        if (inputRequiredCorrects.getText().toString().trim().equals("")){
            Styles.shakeAnimate(inputRequiredCorrects);
            return false;
        }
        if (inputMaxErrors.getText().toString().trim().equals("")){
            Styles.shakeAnimate(inputMaxErrors);
            return false;
        }
        if (Integer.valueOf(String.valueOf(inputMaxErrors.getText())) < 1){
            Styles.shakeAnimate(inputMaxErrors);
            Util.toast(mContext, AppConstants.INVALID_INPUT);
            return false;
        } else if (Integer.valueOf(String.valueOf(
                inputRequiredCorrects.getText())) < 1){
            Styles.shakeAnimate(inputRequiredCorrects);
            Util.toast(mContext, AppConstants.INVALID_INPUT);
            return false;
        }
        return true;
    }

    public void focusInputRequiredCorrects(){
        inputRequiredCorrects.requestFocus();
    }

    @Override
    public void setOnShowListener(@Nullable OnShowListener listener) {
        focusInputRequiredCorrects();
        super.setOnShowListener(listener);
    }
}
