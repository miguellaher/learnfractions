package com.example.laher.learnfractions.teacher.list_adapters;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

public class ExerciseStatAdapter extends ArrayAdapter<LessonExercise> {
    private Context mContext;
    private int mResource;

    public ExerciseStatAdapter(@NonNull Context context, int resource, @NonNull ArrayList<LessonExercise> objects) {
        super(context, resource, objects);
        mResource = resource;
        mContext = context;
    }

    static class ViewHolder {
        TextView txtExerciseTitle;
        TextView txtErrors;
        TextView txtTimeSpent;
        LinearLayout linearLayout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LessonExercise exercise = getItem(position);
        assert exercise != null;
        String exerciseTitle = exercise.getExerciseTitle();
        int errors = exercise.getTotalWrongs();
        long time_spent = exercise.getTimeSpent();
        time_spent = time_spent / 1000;

        final View result;

        final ViewHolder holder;
        if (convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.txtExerciseTitle = convertView.findViewById(R.id.user_item2_txtView1);
            holder.txtErrors = convertView.findViewById(R.id.user_item2_txtView2);
            holder.txtTimeSpent = convertView.findViewById(R.id.user_item2_txtView3);
            holder.linearLayout = convertView.findViewById(R.id.linearLayout);

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

        holder.txtExerciseTitle.setText(exerciseTitle);
        String strErrors = "Errors: " + errors;
        holder.txtErrors.setText(strErrors);
        holder.txtTimeSpent.setText("Time Spent:\n");
        String strTimeSpent;
        if (minutes>0){
            strTimeSpent = holder.txtTimeSpent.getText().toString() + minutes + "m";
            holder.txtTimeSpent.setText(strTimeSpent);
        }
        strTimeSpent = holder.txtTimeSpent.getText().toString() + seconds + "s";
        holder.txtTimeSpent.setText(strTimeSpent);

        int activityPosition = position + 1;
        while (activityPosition>4){
            activityPosition = activityPosition - 4;
        }

        if (activityPosition ==1){
            Styles.bgPaintMainBlueGreen(holder.linearLayout);
        } else if (activityPosition ==2){
            Styles.bgPaintMainBlue(holder.linearLayout);
        } else if (activityPosition ==3){
            Styles.bgPaintMainYellow(holder.linearLayout);
        } else if (activityPosition ==4){
            Styles.bgPaintMainOrange(holder.linearLayout);
        }


        return convertView;
    }
}
