package com.example.laher.learnfractions.dialog_layout;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.service.SeatWorkStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

public class SeatWorkStatDialog extends Dialog {
    Context mContext;
    private static final String TAG = "SW_STAT_D";

    SeatWork mSeatWork;
    Student mStudent;

    TextView txtScore, txtTimeSpent;
    Button btnOk;

    public SeatWorkStatDialog(@NonNull Context context, SeatWork seatwork, Student student) {
        super(context);
        mContext = context;
        mSeatWork = seatwork;
        mStudent = student;
        postStat();
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

    public SeatWorkStatDialog(@NonNull Context context, SeatWork seatWork) {
        super(context);
        mContext = context;
        this.mSeatWork = seatWork;

        setGui();

        long seconds = seatWork.getTimeSpent()/1000;
        long minutes = seconds/60;
        seconds = (minutes*60) - seconds;
        seconds = Math.abs(seconds);

        txtScore.setText("Score: "+seatWork.getCorrect()+" / " + seatWork.getItems_size());
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
    private void postStat(){
        Service service = new Service("Posting stats...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                Util.toast(mContext, response.optString("message"));
                Log.d(TAG, "post execute");
            }
        });
        SeatWorkStatService.postStat(mContext, mSeatWork, service);
        Log.d(TAG, "post SeatWorkStatService.postStat()");
    }

}
