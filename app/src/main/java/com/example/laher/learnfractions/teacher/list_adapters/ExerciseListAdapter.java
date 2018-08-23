package com.example.laher.learnfractions.teacher.list_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.Exercise;

import java.util.ArrayList;

public class ExerciseListAdapter extends ArrayAdapter<Exercise> {
    private Context mContext;
    private int mResource;

    public ExerciseListAdapter(@NonNull Context context, int resource, ArrayList<Exercise> exercises) {
        super(context, resource, exercises);
        mContext = context;
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String topicName = getItem(position).getTopicName();
        String exerciseNum = String.valueOf(getItem(position).getExerciseNum());

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView textView1 = convertView.findViewById(R.id.user_item_txtView1);
        TextView textView2 = convertView.findViewById(R.id.user_item_txtView2);
        TextView textView3 = convertView.findViewById(R.id.user_item_txtView3);

        textView1.setText(topicName);
        textView2.setText("Exercise no.");
        textView3.setText(exerciseNum);

        return convertView;
    }
}
