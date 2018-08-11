package com.example.laher.learnfractions.rankings.list_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.ExamStat;
import com.example.laher.learnfractions.model.ExerciseStat;

import java.util.ArrayList;

public class ExamTop10ListAdapter extends ArrayAdapter<ExamStat> {
    private Context mContext;
    private int mResource;

    static class ViewHolder {
        TextView txtRankNum, txtUserName, txtErrors, txtTime;
    }

    public ExamTop10ListAdapter(@NonNull Context context, int resource, ArrayList<ExamStat> examStats) {
        super(context, resource, examStats);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View result;
        int correct = getItem(position).getTotalScore();
        long time_spent = getItem(position).getTimeSpent();

        ViewHolder holder;
        if (convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.txtRankNum = convertView.findViewById(R.id.exercise_rank_top10_txtRankNum);
            holder.txtUserName = convertView.findViewById(R.id.exercise_rank_top10_txtUserName);
            holder.txtErrors = convertView.findViewById(R.id.exercise_rank_top10_txtErrors);
            holder.txtTime = convertView.findViewById(R.id.exercise_rank_top10_txtTime);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.txtRankNum.setText(String.valueOf(position+1));
        holder.txtUserName.setText(getItem(position).getStudent().getUsername());
        holder.txtErrors.setText("Score:\n" + correct + "/" + getItem(position).getTotalItems());
        holder.txtTime.setText("Time spent:\n");
        long seconds = time_spent;
        long minutes = seconds/60;
        seconds = (minutes*60) - seconds;
        seconds = Math.abs(seconds);
        if (minutes>0){
            holder.txtTime.setText(holder.txtTime.getText().toString() + minutes + "m");
        }
        if (seconds>0) {
            holder.txtTime.setText(holder.txtTime.getText().toString() + seconds + "s");
        }
        if (minutes<1&&seconds<1){
            holder.txtTime.setText(holder.txtTime.getText().toString() + "__m__s");
        }


        return convertView;
    }
}
