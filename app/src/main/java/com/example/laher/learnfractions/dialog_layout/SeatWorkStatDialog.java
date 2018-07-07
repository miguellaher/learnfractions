package com.example.laher.learnfractions.dialog_layout;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.SeatWork;

public class SeatWorkStatDialog extends Dialog {
    TextView txtScore, txtTimeSpent;
    Button btnOk;

    public SeatWorkStatDialog(@NonNull Context context, SeatWork seatwork) {
        super(context);
        setGui();

        long seconds = seatwork.getTimeSpent()/1000;
        long minutes = seconds/60;
        seconds = (minutes*60) - seconds;
        seconds = Math.abs(seconds);

        txtScore.setText("Score: "+seatwork.getCorrect()+" / " + seatwork.getItems_size());
        txtTimeSpent.setText("Time Spent: " + minutes + "m" + seconds + "s");
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
    private void setGui(){
        setContentView(R.layout.layout_dialog_seatwork_stats);
        txtScore = findViewById(R.id.seatwork_stats_txtScore);
        txtTimeSpent = findViewById(R.id.seatwork_stats_txtTimeSpent);
        btnOk = findViewById(R.id.seatwork_stats_btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
