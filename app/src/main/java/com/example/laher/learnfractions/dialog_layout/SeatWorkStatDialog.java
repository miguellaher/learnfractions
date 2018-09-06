package com.example.laher.learnfractions.dialog_layout;

import android.annotation.SuppressLint;
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
import com.example.laher.learnfractions.util.AppCache;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

public class SeatWorkStatDialog extends Dialog {
    private Context mContext;
    private static final String TAG = "SW_STAT_D";

    private SeatWork mSeatWork;

    private TextView txtScore, txtTimeSpent;

    @SuppressLint("SetTextI18n")
    public SeatWorkStatDialog(@NonNull Context context, SeatWork seatWork) {
        super(context);
        mContext = context;
        this.mSeatWork = seatWork;
        postStat();
        setGui();

        long timeSpent = seatWork.getTimeSpent();
        timeSpent = Math.round(timeSpent/1000d);
        long seconds = timeSpent;
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
        Button btnOk = findViewById(R.id.seatwork_stats_btnOk);
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
        if (!AppCache.isInChapterExam()) {
            SeatWorkStatService.postStat(mContext, mSeatWork, service);
        }
        Log.d(TAG, "post SeatWorkStatService.postStat()");
    }

}
