package com.example.laher.learnfractions.admin_activities.list_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.Teacher;

import java.util.ArrayList;

public class TeacherListAdapter extends ArrayAdapter<Teacher> {

    private Context mContext;
    private int mResource;

    public TeacherListAdapter(Context context, int resource, ArrayList<Teacher> teachers){
        super(context,resource,teachers);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String username = getItem(position).getUsername();

        Teacher teacher = new Teacher();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView textView1 = convertView.findViewById(R.id.user_item_txtView1);
        TextView textView2 = convertView.findViewById(R.id.user_item_txtView2);
        TextView textView3 = convertView.findViewById(R.id.user_item_txtView3);

        textView1.setText(username);
        textView2.setText("");
        textView3.setText("");

        return convertView;
    }


}
