package com.example.laher.learnfractions.teacher.list_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

public class ExamStatAdapter extends ArrayAdapter<ChapterExam> {
    private Context mContext;
    private int mResource;

    public ExamStatAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ChapterExam> objects) {
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
        ChapterExam chapterExam = getItem(position);
        assert chapterExam != null;
        String examTitle = chapterExam.getExamTitle();
        int correct = chapterExam.getTotalScore();
        int totalItems = chapterExam.getTotalItems();
        long time_spent = chapterExam.getTimeSpent();
        time_spent = time_spent / 1000;

        final View result;

        ViewHolder holder;
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

        holder.txtExerciseTitle.setText(examTitle);
        String strErrors = "Score: " + correct + "/" + totalItems;
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
