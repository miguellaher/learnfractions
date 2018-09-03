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
import com.example.laher.learnfractions.parent_activities.SeatWork;

import java.util.ArrayList;

public class ExamUpdateAdapter extends ArrayAdapter<SeatWork> {
    private Context mContext;
    private int mResource;
    public ExamUpdateAdapter(@NonNull Context context, int resource, ArrayList<SeatWork> seatWorks) {
        super(context, resource, seatWorks);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String topicName = getItem(position).getTopicName();
        int items_size = getItem(position).getItems_size();

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView textView1 = convertView.findViewById(R.id.user_item_txtView1);
        TextView textView2 = convertView.findViewById(R.id.user_item_txtView2);
        TextView textView3 = convertView.findViewById(R.id.user_item_txtView3);

        textView1.setText(topicName);
        textView2.setText("No. of items");
        textView3.setText(String.valueOf(items_size));

        return convertView;
    }
}
