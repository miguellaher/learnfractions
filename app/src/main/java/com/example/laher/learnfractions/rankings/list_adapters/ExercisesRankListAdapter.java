package com.example.laher.learnfractions.rankings.list_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.ExerciseRank;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

public class ExercisesRankListAdapter extends ArrayAdapter<ExerciseRank> {
    private Context mContext;
    private int mResource;

    static class ViewHolder {
        TextView txtUsername;
        TextView txtRank;
        TextView txtExercisesDone;
        TextView labelExercisesDone;
        TextView txtTotalErrors;
        TextView labelTotalErrors;
        TextView txtTotalTime;
    }

    public ExercisesRankListAdapter(@NonNull Context context, int resource, ArrayList<ExerciseRank> exerciseRanks) {
        super(context, resource, exerciseRanks);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View result;
        ExerciseRank exerciseRank = getItem(position);

        assert convertView != null;

        ViewHolder holder;
        if (convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.txtUsername = convertView.findViewById(R.id.rank_adapter_txtUserName);
            holder.txtRank = convertView.findViewById(R.id.rank_adapter_txtRank);
            holder.txtExercisesDone = convertView.findViewById(R.id.rank_adapter_txtSWDone);
            holder.labelExercisesDone = convertView.findViewById(R.id.rank_adapter_strSWDone);
            holder.txtTotalErrors = convertView.findViewById(R.id.rank_adapter_txtTotalScore);
            holder.labelTotalErrors = convertView.findViewById(R.id.rank_adapter_strTotalScore);
            holder.txtTotalTime = convertView.findViewById(R.id.rank_adapter_txtTotalTime);

            ConstraintLayout constraintLayout = convertView.findViewById(R.id.rank_adapter_constraintLayout);
            ConstraintLayout constraintLayout1 = convertView.findViewById(R.id.rank_adapter_constraintLayout1);

            int activityPosition = position + 1;
            while (activityPosition>4){
                activityPosition = activityPosition - 4;
            }

            if (activityPosition==1){
                Styles.bgPaintMainOrange(constraintLayout);
                Styles.bgPaintMainOrange(constraintLayout1);
            } else if (activityPosition==2){
                Styles.bgPaintMainBlueGreen(constraintLayout);
                Styles.bgPaintMainBlueGreen(constraintLayout1);
            } else if (activityPosition==3){
                Styles.bgPaintMainBlue(constraintLayout);
                Styles.bgPaintMainBlue(constraintLayout1);
            } else if (activityPosition==4){
                Styles.bgPaintMainYellow(constraintLayout);
                Styles.bgPaintMainYellow(constraintLayout1);
            }

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        assert exerciseRank != null;
        Student student = exerciseRank.getStudent();
        String username = student.getUsername();
        int rank = position+1;
        String strRank = String.valueOf(rank);
        int swDone = exerciseRank.getExercisesDone();
        String strSWDone = String.valueOf(swDone);
        int totalErrors = exerciseRank.getTotalErrors();
        String strErrors = String.valueOf(totalErrors);

        long totalTimeSpent = exerciseRank.getTotalTimeSpent();
        totalTimeSpent = Math.round(totalTimeSpent/1000d);
        long seconds = totalTimeSpent;
        long minutes = seconds/60;
        seconds = (minutes*60) - seconds;
        seconds = Math.abs(seconds);



        holder.txtUsername.setText(username);
        String strTop = "TOP";
        holder.txtRank.setText(strTop + " " + strRank);
        holder.txtExercisesDone.setText(strSWDone);

        String strExamsDone = "Exercises\nDone";
        holder.labelExercisesDone.setText(strExamsDone);

        holder.txtTotalErrors.setText(strErrors);
        String strLabelTotalErrors = "Total Errors";
        holder.labelTotalErrors.setText(strLabelTotalErrors);

        holder.txtTotalTime.setText("");
        if (minutes>0){
            holder.txtTotalTime.setText(minutes + "m");
        }
        String strTxtTotalTime = holder.txtTotalTime.getText().toString().trim();
        holder.txtTotalTime.setText(strTxtTotalTime + seconds + "s");


        return convertView;
    }
}
