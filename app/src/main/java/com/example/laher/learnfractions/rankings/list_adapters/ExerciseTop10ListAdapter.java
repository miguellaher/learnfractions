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
import com.example.laher.learnfractions.model.E_StatAverage;
import com.example.laher.learnfractions.model.ExerciseStat;

import java.util.ArrayList;

public class ExerciseTop10ListAdapter extends ArrayAdapter<ExerciseStat> {
    private Context mContext;
    private int mResource;

    static class ViewHolder {
        TextView txtRankNum, txtUserName, txtErrors, txtTime;
    }

    public ExerciseTop10ListAdapter(@NonNull Context context, int resource, ArrayList<ExerciseStat> exerciseStats) {
        super(context, resource, exerciseStats);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View result;
        double errors = getItem(position).getErrors();
        long time_spent = getItem(position).getTime_spent();

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
        holder.txtErrors.setText("Errors:\n" + errors);
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
