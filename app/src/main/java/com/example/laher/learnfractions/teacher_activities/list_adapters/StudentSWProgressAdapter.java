package com.example.laher.learnfractions.teacher_activities.list_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.StatAverage;

import java.util.ArrayList;

public class StudentSWProgressAdapter extends ArrayAdapter<StatAverage> {
    private Context mContext;
    private static final String TAG = "SSWP_ADAPTER";
    private int mResource;

    public StudentSWProgressAdapter(@NonNull Context context, int resource, ArrayList<StatAverage> statAverages) {
        super(context, resource, statAverages);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int students_answered = getItem(position).getStudents_answered();
        double average_score = getItem(position).getScore_average();
        long average_time_spent = getItem(position).getTime_spent_average();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView textView1 = convertView.findViewById(R.id.seat_work_item_txtView1);
        TextView textView2 = convertView.findViewById(R.id.seat_work_item_txtView2);
        TextView textView3 = convertView.findViewById(R.id.seat_work_item_txtView3);
        TextView textView4 = convertView.findViewById(R.id.seat_work_item_txtView4);

        long seconds = average_time_spent;
        long minutes = seconds/60;
        seconds = (minutes*60) - seconds;
        seconds = Math.abs(seconds);

        textView1.setText(getItem(position).getTopicName());
        textView2.setText(String.valueOf(students_answered));
        if (students_answered<2){
            textView2.setText(textView2.getText().toString() + " Student");
        } else {
            textView2.setText(textView2.getText().toString() + " Students");
        }
        textView3.setText("Avg Score: " + average_score + "%");
        textView4.setText("Avg Time: ");
        if (minutes>0){
            textView4.setText(textView4.getText().toString() + minutes + "m");
        }
        if (seconds>0) {
            textView4.setText(textView4.getText().toString() + seconds + "s");
        }
        if (minutes<1&&seconds<1){
            textView4.setText(textView4.getText().toString() + "__m__s");
        }


        return convertView;
    }
}
