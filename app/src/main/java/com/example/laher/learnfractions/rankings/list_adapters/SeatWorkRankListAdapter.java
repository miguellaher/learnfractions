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
import com.example.laher.learnfractions.model.SW_StatAverage;

import java.util.ArrayList;

public class SeatWorkRankListAdapter extends ArrayAdapter<SW_StatAverage> {
    private Context mContext;
    private int mResource;

    static class ViewHolder {
        TextView textView1;
    }

    public SeatWorkRankListAdapter(@NonNull Context context, int resource, ArrayList<SW_StatAverage> sw_statAverages) {
        super(context, resource, sw_statAverages);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View result;

        ViewHolder holder;
        if (convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.textView1 = convertView.findViewById(R.id.exercise_list_item_txtView1);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.textView1.setText(getItem(position).getTopicName() + " " + getItem(position).getSeatWorkNum());


        return convertView;
    }
}
