package com.example.laher.learnfractions.teacher2.list_adpaters;

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
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

public class LessonExercisesListAdapter extends ArrayAdapter<LessonExercise> {
    private Context mContext;
    private int mResource;

    public LessonExercisesListAdapter(@NonNull Context context, int resource, ArrayList<LessonExercise> lessonExercises) {
        super(context, resource, lessonExercises);
        mContext = context;
        mResource = resource;

    }

    static class ViewHolder {
        TextView textView1;
        ConstraintLayout constraintLayoutBackground;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // layout exercise_list_item
        LessonExercise lessonExercise = getItem(position);
        assert lessonExercise != null;
        String title = lessonExercise.getExerciseTitle();

        //final View result;

        ViewHolder holder;
        if (convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.textView1 = convertView.findViewById(R.id.exercise_list_item_txtView1);
            holder.constraintLayoutBackground = convertView.findViewById(R.id.constraintLayoutBackground);

            //result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            //result = convertView;
        }

        int activityPosition = position + 1;
        while (activityPosition>4){
            activityPosition = activityPosition - 4;
        }

        if (activityPosition==1){
            Styles.bgPaintMainOrange(holder.constraintLayoutBackground);
        } else if (activityPosition==2){
            Styles.bgPaintMainBlueGreen(holder.constraintLayoutBackground);
        } else if (activityPosition==3){
            Styles.bgPaintMainBlue(holder.constraintLayoutBackground);
        } else if (activityPosition==4){
            Styles.bgPaintMainYellow(holder.constraintLayoutBackground);
        }

        holder.textView1.setText(title);
        Styles.paintBlack(holder.textView1);

        return convertView;
    }
}
