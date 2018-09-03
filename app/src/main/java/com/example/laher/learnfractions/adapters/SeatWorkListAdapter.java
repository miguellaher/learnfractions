package com.example.laher.learnfractions.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.util.AppConstants;

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

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String topicName = getItem(position).getTopicName();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        long seconds = getItem(position).getTimeSpent()/1000;
        long minutes = seconds/60;
        seconds = (minutes*60) - seconds;
        seconds = Math.abs(seconds);

        TextView textView1 = convertView.findViewById(R.id.user_item_txtView1);
        TextView textView2 = convertView.findViewById(R.id.user_item_txtView2);
        TextView textView3 = convertView.findViewById(R.id.user_item_txtView3);

        textView1.setText(topicName);
        if (mUserType!=null) {
            if (mUserType.equals(AppConstants.TEACHER)) {
                textView2.setText("No. of items");
                textView3.setText(String.valueOf(getItem(position).getItems_size()));
            }
        } else {
            textView2.setText("Score: ");
            if(getItem(position).isAnswered()) {
                textView2.setText(textView2.getText().toString() + getItem(position).getCorrect() + " / " + getItem(position).getItems_size());
            } else {
                textView2.setText(textView2.getText().toString() + "__ / " + getItem(position).getItems_size());
                convertView.setBackgroundColor(AppConstants.BG_DEFAULT_NOT_FINISHED);
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
