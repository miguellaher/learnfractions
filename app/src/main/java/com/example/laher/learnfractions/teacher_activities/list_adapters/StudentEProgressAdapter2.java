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
import com.example.laher.learnfractions.model.E_StatAverage;
import com.example.laher.learnfractions.model.ExerciseStat;

import java.util.ArrayList;
import java.util.Objects;

public class StudentEProgressAdapter2 extends ArrayAdapter<ExerciseStat> {
    private Context mContext;
    private static final String TAG = "SEP2_ADAPTER";
    private int mResource;

    static class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
    }

    public StudentEProgressAdapter2(@NonNull Context context, int resource, ArrayList<ExerciseStat> eStat) {
        super(context, resource, eStat);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String username = Objects.requireNonNull(getItem(position)).getStudent().getUsername();
        double errors = getItem(position).getErrors();
        long time_spent = getItem(position).getTime_spent();

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
        holder.textView2.setText("Errors: " + errors);
        holder.textView3.setText("Time spent: ");
        if (minutes>0){
            holder.textView3.setText(holder.textView3.getText().toString() + minutes + "m");
        }
        if (seconds>0) {
            holder.textView3.setText(holder.textView3.getText().toString() + seconds + "s");
        }
        if (minutes<1&&seconds<1){
            holder.textView3.setText(holder.textView3.getText().toString() + "__m__s");
        }


        return convertView;
    }
}
