package com.example.laher.learnfractions.teacher_activities.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.SeatWork;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.service.SeatWorkService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

public class SeatWorkUpdateDialog extends Dialog {
    private Context mContext;
    private TextView txtTopicName, txtSeatWorkNum;
    private TextView txtLabel1, txtLabel2;
    private EditText inputItemsSize, inputMaxErrors;
    private CheckBox chk_RC_consecutive, chk_ME_consecutive;

    private SeatWork mSeatWork;
    private Teacher mTeacher;

    public SeatWorkUpdateDialog(@NonNull Context context, SeatWork seatWork, Teacher teacher) {
        super(context);
        mContext = context;
        mSeatWork = seatWork;
        mTeacher = teacher;

        setGui();

        txtTopicName.setText(seatWork.getTopicName());
        txtSeatWorkNum.setText("SeatWork no. " + seatWork.getSeatWorkNum());
        txtLabel1.setText("Number of Items:");
        inputMaxErrors.setVisibility(View.INVISIBLE);
        txtLabel2.setVisibility(View.INVISIBLE);
        chk_RC_consecutive.setVisibility(View.INVISIBLE);
        chk_ME_consecutive.setVisibility(View.INVISIBLE);
    }

    private void setGui(){
        setContentView(R.layout.layout_exercise_update);
        txtTopicName = findViewById(R.id.exercise_update_txtTopicName);
        txtSeatWorkNum = findViewById(R.id.exercise_update_txtExerciseNum);
        txtLabel1 = findViewById(R.id.exercise_update_txtLabel1);
        txtLabel2 = findViewById(R.id.exercise_update_txtLabel2);
        inputItemsSize = findViewById(R.id.exercise_update_inputRequiredCorrects);
        inputMaxErrors = findViewById(R.id.exercise_update_inputMaxErrors);
        chk_RC_consecutive = findViewById(R.id.exercise_update_chk_RC_consecutive);
        chk_ME_consecutive = findViewById(R.id.exercise_update_chk_ME_consecutive);
        Button btnUpdate = findViewById(R.id.exercise_update_btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (checkErrors()){
                SeatWork seatWork = mSeatWork;
                int items_size = Integer.valueOf(String.valueOf(inputItemsSize.getText()));
                seatWork.setItems_size(items_size);
                Service service = new Service("Posting seat work...", mContext, new ServiceResponse() {
                    @Override
                    public void postExecute(JSONObject response) {
                        Util.toast(mContext, response.optString("message"));
                        dismiss();
                    }
                });
                SeatWorkService.updateSeatWork(mTeacher,seatWork,service);
            }
            }
        });
    }

    private boolean checkErrors(){
        if (inputItemsSize.getText().toString().trim().equals("")){
            Styles.shakeAnimate(inputItemsSize);
            return false;
        }
        if (Integer.valueOf(String.valueOf(inputItemsSize.getText()))<1){
            Styles.shakeAnimate(inputItemsSize);
            Util.toast(mContext, AppConstants.INVALID_INPUT);
            return false;
        }
        return true;
    }

    private void focusInputRequiredCorrects(){
        inputItemsSize.requestFocus();
    }

    @Override
    public void setOnShowListener(@Nullable OnShowListener listener) {
        focusInputRequiredCorrects();
        super.setOnShowListener(listener);
    }
}
