package com.example.laher.learnfractions.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

public class SeatWorkListAdapter extends ArrayAdapter<SeatWork> {
    private Context mContext;
    private int mResource;
    private String mUserType;

    public SeatWorkListAdapter(@NonNull Context context, int resource, ArrayList<SeatWork> seatWorks) {
        super(context, resource, seatWorks);
        mContext = context;
        mResource = resource;
    }

    public SeatWorkListAdapter(@NonNull Context context, int resource, ArrayList<SeatWork> seatWorks, String userType) {
        super(context, resource, seatWorks);
        mContext = context;
        mResource = resource;
        mUserType = userType;
    }

    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SeatWork seatWork = getItem(position);
        assert seatWork != null;
        String topicName = seatWork.getTopicName();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        long timeSpent = seatWork.getTimeSpent();
        timeSpent = Math.round(timeSpent/1000d);
        long seconds = timeSpent;
        long minutes = seconds/60;
        seconds = (minutes*60) - seconds;
        seconds = Math.abs(seconds);

        TextView textView1 = convertView.findViewById(R.id.user_item_txtView1);
        TextView textView2 = convertView.findViewById(R.id.user_item_txtView2);
        TextView textView3 = convertView.findViewById(R.id.user_item_txtView3);

        Styles.paintBlack(textView1);
        Styles.paintBlack(textView2);
        Styles.paintBlack(textView3);

        textView1.setText(topicName);

        LinearLayout linearLayoutBackground1 = convertView.findViewById(R.id.linearLayoutBackground);

        int activityPosition = position + 1;
        while (activityPosition>4){
            activityPosition = activityPosition - 4;
        }

        if (activityPosition==1){
            Styles.bgPaintMainOrange(linearLayoutBackground1);
        } else if (activityPosition==2){
            Styles.bgPaintMainBlueGreen(linearLayoutBackground1);
        } else if (activityPosition==3){
            Styles.bgPaintMainBlue(linearLayoutBackground1);
        } else if (activityPosition==4){
            Styles.bgPaintMainYellow(linearLayoutBackground1);
        }

        if (mUserType!=null) {
            if (mUserType.equals(AppConstants.TEACHER)) {
                textView2.setText("No. of items");
                textView3.setText(String.valueOf(seatWork.getItems_size()));
            }
        } else {
            textView2.setText("Score: ");
            if(seatWork.isAnswered()) {
                String strTxtView2 = textView2.getText().toString();
                int correct = seatWork.getCorrect();
                String strCorrect = String.valueOf(correct);
                int itemSize = seatWork.getItems_size();
                String strItemSize = String.valueOf(itemSize);

                textView2.setText(strTxtView2 + strCorrect + " / " + strItemSize);
            } else {
                textView2.setText(textView2.getText().toString() + "__ / " + seatWork.getItems_size());
                linearLayoutBackground1.setBackgroundColor(AppConstants.BG_DEFAULT_NOT_FINISHED);
            }
            textView3.setText("Time spent:\n");
            if (minutes>0){
                textView3.setText(textView3.getText().toString() + minutes + "m");
            }
            if (seconds>0) {
                textView3.setText(textView3.getText().toString() + seconds + "s");
            }
            if (minutes<1&&seconds<1){
                textView3.setText(textView3.getText().toString() + "__m__s");
            }
        }

        return convertView;
    }
}
