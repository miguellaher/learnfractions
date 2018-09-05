package com.example.laher.learnfractions.teacher.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.service.SeatWorkService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.ActivityUtil;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

public class SeatWorkUpdateDialog extends Dialog {
    private Context mContext;
    private TextView txtLabel1;
    private EditText inputTopicName;
    private EditText inputItemsSize;
    private EditText inputMinimum;
    private EditText inputMaximum;
    private EditText inputMaxWrong;
    private CheckBox chk_RC_consecutive;
    private CheckBox chk_ME_consecutive;

    private LinearLayout linearMaxErrors;
    private LinearLayout linearRange;

    private SeatWork mSeatWork;

    public SeatWorkUpdateDialog(@NonNull Context context, SeatWork seatWork) {
        super(context);
        mContext = context;
        mSeatWork = seatWork;

        setGui();

        String topicName = seatWork.getTopicName();
        inputTopicName.setText(topicName);
        String label1 = "Number of Items:";
        txtLabel1.setText(label1);

        boolean rangeEditable = seatWork.isRangeEditable();

        if (!rangeEditable) {
            ConstraintLayout.LayoutParams linearRangeLayoutParams = (ConstraintLayout.LayoutParams) linearRange.getLayoutParams();
            linearRangeLayoutParams.height = 0;
            linearRangeLayoutParams.topMargin = 0;
            linearRange.setLayoutParams(linearRangeLayoutParams);
        }

        ConstraintLayout.LayoutParams linearMaxErrorsLayoutParams = (ConstraintLayout.LayoutParams) linearMaxErrors.getLayoutParams();
        linearMaxErrorsLayoutParams.height = 0;
        linearMaxErrorsLayoutParams.topMargin = 0;
        linearMaxErrors.setLayoutParams(linearMaxErrorsLayoutParams);

        ConstraintLayout.LayoutParams chkRcConsecutiveLayoutParams = (ConstraintLayout.LayoutParams) chk_RC_consecutive.getLayoutParams();
        chkRcConsecutiveLayoutParams.height = 0;
        chkRcConsecutiveLayoutParams.topMargin = 0;
        chk_RC_consecutive.setLayoutParams(chkRcConsecutiveLayoutParams);
        chk_RC_consecutive.setVisibility(View.INVISIBLE);

        ConstraintLayout.LayoutParams chkMeConsecutiveLayoutParams = (ConstraintLayout.LayoutParams) chk_ME_consecutive.getLayoutParams();
        chkMeConsecutiveLayoutParams.height = 0;
        chkMeConsecutiveLayoutParams.topMargin = 0;
        chk_ME_consecutive.setLayoutParams(chkMeConsecutiveLayoutParams);
        chk_ME_consecutive.setVisibility(View.INVISIBLE);

        inputItemsSize.requestFocus();
    }

    private void setGui(){
        setContentView(R.layout.layout_exercise_update);
        inputMaxWrong = findViewById(R.id.exercise_update_inputMaxErrors);
        inputMaxWrong.setEnabled(false);

        inputTopicName = findViewById(R.id.exercise_update_inputTitle);
        txtLabel1 = findViewById(R.id.exercise_update_txtLabel1);

        inputItemsSize = findViewById(R.id.exercise_update_inputRequiredCorrects);
        SeatWork seatWork = mSeatWork;
        int itemSize = seatWork.getItems_size();
        String strItemSize = String.valueOf(itemSize);
        inputItemsSize.setHint(strItemSize);

        inputMinimum = findViewById(R.id.exercise_update_inputMinimum);
        inputMaximum = findViewById(R.id.exercise_update_inputMaximum);
        Range range = seatWork.getRange();
        int minimum = range.getMinimum();
        int maximum = range.getMaximum();
        String strMinimum = String.valueOf(minimum);
        String strMaximum = String.valueOf(maximum);
        inputMinimum.setHint(strMinimum);
        inputMaximum.setHint(strMaximum);

        linearMaxErrors = findViewById(R.id.exercise_update_linearMaxErrors);
        linearRange = findViewById(R.id.exercise_update_linearRange);
        chk_RC_consecutive = findViewById(R.id.exercise_update_chk_RC_consecutive);
        chk_ME_consecutive = findViewById(R.id.exercise_update_chk_ME_consecutive);
        Button btnUpdate = findViewById(R.id.exercise_update_btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.hideKeyboardFrom(mContext,v);
            if (noErrors()){
                String topicName = inputTopicName.getText().toString();
                String strItemSize = inputItemsSize.getText().toString();
                final int itemSize = Integer.valueOf(strItemSize);

                final SeatWork seatWork = mSeatWork;
                seatWork.setTopicName(topicName);
                if (seatWork.isRangeEditable()){
                    String strMinimum = inputMinimum.getText().toString().trim();
                    String strMaximum = inputMaximum.getText().toString().trim();
                    if (Util.isNumeric(strMinimum) && Util.isNumeric(strMaximum)){
                        int minimum = Integer.valueOf(strMinimum);
                        int maximum = Integer.valueOf(strMaximum);
                        Range range = new Range(minimum,maximum);
                        seatWork.setRange(range);

                        Probability probability = seatWork.getProbability();
                        String equation = probability.getEquation();

                        Probability newProbability = new Probability(equation,range);
                        seatWork.setProbability(newProbability);
                    }
                }
                seatWork.setItems_size(itemSize);

                Service service = new Service("Posting seat work...", mContext, new ServiceResponse() {
                    @Override
                    public void postExecute(JSONObject response) {
                        Util.toast(mContext, response.optString("message"));

                        String title = seatWork.getTopicName();
                        int itemSize = seatWork.getItems_size();
                        String strItemSize = String.valueOf(itemSize);
                        inputTopicName.setText(title);
                        inputTopicName.setHint(title);
                        inputItemsSize.setText(strItemSize);
                        inputItemsSize.setHint(strItemSize);
                        inputItemsSize.requestFocus();
                        inputItemsSize.setSelection(strItemSize.length());

                        if (seatWork.isRangeEditable()) {
                            Range range = seatWork.getRange();
                            int minimum = range.getMinimum();
                            int maximum = range.getMaximum();
                            String strMinimum = String.valueOf(minimum);
                            String strMaximum = String.valueOf(maximum);
                            inputMinimum.setHint(strMinimum);
                            inputMaximum.setHint(strMaximum);
                        }
                    }
                });
                SeatWorkService.insert(mContext, seatWork, service);
            }
            }
        });
    }

    private boolean noErrors(){
        boolean noErrors = true;
        String strInputTopicName = inputTopicName.getText().toString().trim();
        if (strInputTopicName.equals("")){
            Styles.shakeAnimate(inputTopicName);
            noErrors = false;
        }

        String strItemSize = inputItemsSize.getText().toString().trim();
        if (strItemSize.equals("")) {
            Styles.shakeAnimate(inputItemsSize);
            noErrors = false;
        } else {
            if (Util.isNumeric(strItemSize)) {
                if (Integer.valueOf(strItemSize) < 1) {
                    Styles.shakeAnimate(inputItemsSize);
                    Util.toast(mContext, AppConstants.INVALID_INPUT);
                    noErrors = false;
                }
            }
        }

        SeatWork seatWork = mSeatWork;
        boolean rangeEditable = seatWork.isRangeEditable();
        if (rangeEditable){
            String strMinimum = inputMinimum.getText().toString().trim();
            String strMaximum = inputMaximum.getText().toString().trim();

            if (strMinimum.equals("")) {
                Styles.shakeAnimate(inputMinimum);
                noErrors = false;
            }
            if (strMaximum.equals("")){
                Styles.shakeAnimate(inputMaximum);
                noErrors = false;
            }
            if (Util.isNumeric(strMinimum) && Util.isNumeric(strMaximum)) {
                int intInputMinimum = Integer.valueOf(strMinimum);
                int intInputMaximum = Integer.valueOf(strMaximum);
                if (intInputMinimum >= intInputMaximum) {
                    Styles.shakeAnimate(inputMinimum);
                    Styles.shakeAnimate(inputMaximum);
                    String message = "Input a larger number on the maximum field.";
                    Util.toast(mContext, message);
                    noErrors = false;
                }
            }
        }
        return noErrors;
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
