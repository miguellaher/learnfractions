package com.example.laher.learnfractions.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.util.AppConstants;

import java.util.ArrayList;

public class ChapterExamListAdapter extends ArrayAdapter<ChapterExam> {
    //private final String TAG = "ChapterExamListAdapter";
    private Context mContext;
    private int mResource;

    public ChapterExamListAdapter(@NonNull Context context, int resource, ArrayList<ChapterExam> chapterExams) {
        super(context, resource, chapterExams);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String title = getItem(position).getExamTitle();
        int score = getItem(position).getTotalScore();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        long seconds = getItem(position).getTimeSpent()/1000;
        long minutes = seconds/60;
        seconds = (minutes*60) - seconds;
        seconds = Math.abs(seconds);

        TextView textView1 = convertView.findViewById(R.id.user_item_txtView1);
        TextView textView2 = convertView.findViewById(R.id.user_item_txtView2);
        TextView textView3 = convertView.findViewById(R.id.user_item_txtView3);

        textView1.setText(title);
        textView2.setText("Score: ");
        if(getItem(position).isAnswered()) {
            textView2.setText(textView2.getText().toString() + score + " / " + getItem(position).getTotalItems());
        } else {
            textView2.setText(textView2.getText().toString() + "__ / " + getItem(position).getTotalItems());
            convertView.setBackgroundColor(AppConstants.BG_DEFAULT_NOT_FINISHED);
        }
        textView3.setText("Time spent:\n");
        if (minutes>0){
            textView3.setText(textView3.getText().toString() + minutes + "m");
        }
        if (seconds>0) {
            textView3.setText(textView3.getText().toString() + seconds + "s");
        }
        if (minutes<1&&seconds<1){
            textView3.setText(textView3.getText().toString() + "__m__s");
        }
        return convertView;
    }
}
