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
import com.example.laher.learnfractions.model.Student_SW_Progress;

import java.util.ArrayList;
import java.util.Objects;

public class StudentSWProgressAdapter2 extends ArrayAdapter<Student_SW_Progress> {
    private Context mContext;
    private static final String TAG = "SSWP_ADAPTER2";
    private int mResource;

    static class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
    }

    public StudentSWProgressAdapter2(@NonNull Context context, int resource, ArrayList<Student_SW_Progress> student_sw_progresses) {
        super(context, resource, student_sw_progresses);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String username = Objects.requireNonNull(getItem(position)).getStudent().getUsername();
        int score = Objects.requireNonNull(getItem(position)).getCorrect();
        long time_spent = Objects.requireNonNull(getItem(position)).getTimeSpent();

        final View result;

        ViewHolder holder;
        if (convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.textView1 = convertView.findViewById(R.id.user_item2_txtView1);
            holder.textView2 = convertView.findViewById(R.id.user_item2_txtView2);
            holder.textView3 = convertView.findViewById(R.id.user_item2_txtView3);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        long seconds = time_spent;
        long minutes = seconds/60;
        seconds = (minutes*60) - seconds;
        seconds = Math.abs(seconds);

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        result.startAnimation(animation);

        holder.textView1.setText(username);
        holder.textView2.setText("Score: " + score + " / " + getItem(position).getItems_size());
        holder.textView3.setText("Time Spent: ");

        if (minutes>0){
            holder.textView3.setText(holder.textView3.getText().toString() + minutes + "m");
        }
        holder.textView3.setText(holder.textView3.getText().toString() + seconds + "s");

        return convertView;
    }
}
