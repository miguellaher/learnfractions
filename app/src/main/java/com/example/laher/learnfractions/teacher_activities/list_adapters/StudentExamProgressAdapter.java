package com.example.laher.learnfractions.teacher_activities.list_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.ExamStatAverage;

import java.util.ArrayList;

public class StudentExamProgressAdapter extends ArrayAdapter<ExamStatAverage> {
    private Context mContext;
    private static final String TAG = "SExamP_ADAPTER";
    private int mResource;

    static class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
    }

    public StudentExamProgressAdapter(@NonNull Context context, int resource, ArrayList<ExamStatAverage> statAverages) {
        super(context, resource, statAverages);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int students_answered = getItem(position).getStudents_answered();
        double average_score = getItem(position).getAvgScore();
        long average_time_spent = getItem(position).getAvgTimeSpent();

        final View result;

        ViewHolder holder;
        if (convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.textView1 = convertView.findViewById(R.id.seat_work_item_txtView1);
            holder.textView2 = convertView.findViewById(R.id.seat_work_item_txtView2);
            holder.textView3 = convertView.findViewById(R.id.seat_work_item_txtView3);
            holder.textView4 = convertView.findViewById(R.id.seat_work_item_txtView4);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        long seconds = average_time_spent;
        long minutes = seconds/60;
        seconds = (minutes*60) - seconds;
        seconds = Math.abs(seconds);

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        result.startAnimation(animation);

        holder.textView1.setText(getItem(position).getExamTitle());
        holder.textView2.setText(String.valueOf(students_answered));
        if (students_answered<2){
            holder.textView2.setText(holder.textView2.getText().toString() + " Student");
        } else {
            holder.textView2.setText(holder.textView2.getText().toString() + " Students");
        }
        holder.textView3.setText("Avg Score: " + average_score + " / " + getItem(position).getTotalItems());
        holder.textView4.setText("Avg Time: ");
        if (minutes>0){
            holder.textView4.setText(holder.textView4.getText().toString() + minutes + "m");
        }
        if (seconds>0) {
            holder.textView4.setText(holder.textView4.getText().toString() + seconds + "s");
        }
        if (minutes<1&&seconds<1){
            holder.textView4.setText(holder.textView4.getText().toString() + "__m__s");
        }


        return convertView;
    }
}
