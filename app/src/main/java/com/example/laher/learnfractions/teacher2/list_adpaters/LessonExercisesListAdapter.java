package com.example.laher.learnfractions.teacher2.list_adpaters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.parent_activities.LessonExercise;

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

            //result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            //result = convertView;
        }

        holder.textView1.setText(title);
        return convertView;
    }
}
