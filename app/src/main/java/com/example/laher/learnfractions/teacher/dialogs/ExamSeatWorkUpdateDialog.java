package com.example.laher.learnfractions.teacher.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.util.Probability;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

public class ExamSeatWorkUpdateDialog extends Dialog {
    private Context mContext;
    private SeatWork mSeatWork;

    private EditText inputItemSize;
    private EditText inputMinimum;
    private EditText inputMaximum;
    private Button btnSave;

    ExamSeatWorkUpdateDialog(@NonNull Context context, SeatWork seatWork) {
        super(context);
        mContext = context;
        mSeatWork = seatWork;

        setGui();

        inputItemSize.requestFocus();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (areFieldsFilled()){
                    String strInputItemsSize = inputItemSize.getText().toString().trim();
                    int itemSize = Integer.valueOf(strInputItemsSize);

                    if (mSeatWork.isRangeEditable()){
                        String strInputMinimum = inputMinimum.getText().toString().trim();
                        String strInputMaximum = inputMaximum.getText().toString().trim();
                        int minimum = Integer.valueOf(strInputMinimum);
                        int maximum = Integer.valueOf(strInputMaximum);

                        Range range = new Range(minimum,maximum);
                        mSeatWork.setRange(range);
                        Probability probability = mSeatWork.getProbability();
                        String equation = probability.getEquation();

                        Probability newProbability = new Probability(equation,range);
                        mSeatWork.setProbability(newProbability);
                    }
                    mSeatWork.setItems_size(itemSize);
                    dismiss();
                } else {
                    Styles.shakeAnimate(inputItemSize);
                }
            }
        });
    }

    private boolean areFieldsFilled(){
        boolean fieldsFilled = true;
        String strInputItemsSize = inputItemSize.getText().toString().trim();
        if (strInputItemsSize.equals("")){
            Styles.shakeAnimate(inputItemSize);
            fieldsFilled = false;
        }
        if (mSeatWork.isRangeEditable()){
            String strInputMinimum = inputMinimum.getText().toString().trim();
            String strInputMaximum = inputMaximum.getText().toString().trim();
            if (strInputMinimum.equals("")){
                Styles.shakeAnimate(inputMinimum);
                fieldsFilled = false;
            }
            if (strInputMaximum.equals("")){
                Styles.shakeAnimate(inputMaximum);
                fieldsFilled = false;
            }
            if (Util.isNumeric(strInputMinimum) && Util.isNumeric(strInputMaximum)){
                int intInputMinimum = Integer.valueOf(strInputMinimum);
                int intInputMaximum = Integer.valueOf(strInputMaximum);

                if (intInputMinimum>=intInputMaximum){
                    Styles.shakeAnimate(inputMinimum);
                    Styles.shakeAnimate(inputMaximum);
                    String message = "Input a larger number on the maximum field.";
                    Util.toast(mContext, message);
                    fieldsFilled = false;
                }
            }
        }
        return fieldsFilled;
    }

    private void setGui(){
        setContentView(R.layout.layout_dialog_exam_seatwork_update);

        TextView txtTitle = findViewById(R.id.dialog_exam_seatwork_update_txtTitle);
        txtTitle.setText(mSeatWork.getTopicName());

        ConstraintLayout constraintLayoutRange = findViewById(R.id.dialog_exam_seatwork_update_constraintLayoutRange);
        SeatWork seatWork = mSeatWork;

        TextView txtView1 = findViewById(R.id.dialog_exam_seatwork_update_txtItemsSize);
        String strTxtView1 = "Item Size:";
        txtView1.setText(strTxtView1);

        TextView txtView2 = findViewById(R.id.dialog_exam_seatwork_update_txtRange);
        String strTxtView2 = "Range:";
        txtView2.setText(strTxtView2);

        inputItemSize = findViewById(R.id.dialog_exam_seatwork_update_inputItemSize);
        int itemSize = seatWork.getItems_size();
        String strItemSize = String.valueOf(itemSize);
        inputItemSize.setHint(strItemSize);

        inputMinimum = findViewById(R.id.dialog_exam_seatwork_update_inputMinimum);
        inputMaximum = findViewById(R.id.dialog_exam_seatwork_update_inputMaximum);

        if (!mSeatWork.isRangeEditable()){
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) constraintLayoutRange.getLayoutParams();
            params.height = 0;
            constraintLayoutRange.setLayoutParams(params);
            constraintLayoutRange.setVisibility(View.INVISIBLE);
            inputMinimum.setEnabled(false);
            inputMaximum.setEnabled(false);
        } else {
            Range range = seatWork.getRange();
            int minimum = range.getMinimum();
            int maximum = range.getMaximum();
            String strMinimum = String.valueOf(minimum);
            String strMaximum = String.valueOf(maximum);
            inputMinimum.setHint(strMinimum);
            inputMaximum.setHint(strMaximum);
        }

        btnSave = findViewById(R.id.dialog_exam_seatwork_update_btnSave);
    }

    public SeatWork getSeatWork() {
        return mSeatWork;
    }
}
