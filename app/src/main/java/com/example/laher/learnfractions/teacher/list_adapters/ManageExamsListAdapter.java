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
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

public class ManageExamsListAdapter extends ArrayAdapter<ChapterExam> {
    private Context mContext;
    private int mResource;
    public ManageExamsListAdapter(@NonNull Context context, int resource, ArrayList<ChapterExam> chapterExams) {
        super(context, resource, chapterExams);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String topicName = getItem(position).getExamTitle();
        int items_size = getItem(position).getTotalItems();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView textView1 = convertView.findViewById(R.id.user_item_txtView1);
        TextView textView2 = convertView.findViewById(R.id.user_item_txtView2);
        TextView textView3 = convertView.findViewById(R.id.user_item_txtView3);

        textView1.setText(topicName);
        textView2.setText("No. of items");
        textView3.setText(String.valueOf(items_size));

        Styles.paintBlack(textView1);
        Styles.paintBlack(textView2);
        Styles.paintBlack(textView3);

        LinearLayout linearLayoutBackground1 = convertView.findViewById(R.id.linearLayoutBackground);

        int activityPosition = position + 1;
        while (activityPosition>4){
            activityPosition = activityPosition - 4;
        }

        if (activityPosition==1){
            Styles.bgPaintMainOrange(linearLayoutBackground1);
        } else if (activityPosition==2){
            Styles.bgPaintMainBlueGreen(linearLayoutBackground1);
        } else if (activityPosition==3){
            Styles.bgPaintMainBlue(linearLayoutBackground1);
        } else if (activityPosition==4){
            Styles.bgPaintMainYellow(linearLayoutBackground1);
        }

        return convertView;
    }
}
