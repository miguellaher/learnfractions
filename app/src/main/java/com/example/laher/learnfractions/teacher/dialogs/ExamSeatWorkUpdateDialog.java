package com.example.laher.learnfractions.teacher.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.SeatWork;
import com.example.laher.learnfractions.util.Styles;

public class ExamSeatWorkUpdateDialog extends Dialog {
    private Context mContext;
    private SeatWork mSeatWork;
    private int itemSize;
    //GUI
    private TextView txtTitle, txtView1;
    private EditText inputItemSize;
    private Button btnSave;

    public ExamSeatWorkUpdateDialog(@NonNull Context context, SeatWork seatWork) {
        super(context);
        mContext = context;
        mSeatWork = seatWork;
        itemSize = seatWork.getItems_size();

        setGui();

        txtTitle.setText(seatWork.getTopicName() + " no." + seatWork.getSeatWorkNum());
        txtView1.setText("Item Size:");
        inputItemSize.requestFocus();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputItemSize.getText().toString().trim().equals("")){
                    itemSize = Integer.valueOf(String.valueOf(inputItemSize.getText()));
                    dismiss();
                } else {
                    Styles.shakeAnimate(inputItemSize);
                }
            }
        });
    }

    private void setGui(){
        setContentView(R.layout.layout_dialog_exam_seatwork_update);
        txtTitle = findViewById(R.id.dialog_exam_seatwork_update_txtTitle);
        txtView1 = findViewById(R.id.dialog_exam_seatwork_update_txtView1);
        inputItemSize = findViewById(R.id.dialog_exam_seatwork_update_inputItemSize);
        btnSave = findViewById(R.id.dialog_exam_seatwork_update_btnSave);
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }
}
