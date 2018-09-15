package com.example.laher.learnfractions.classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laher.learnfractions.R;

import java.util.List;

public class SecurityQuestionAdapter extends ArrayAdapter<String> {
    private List<String> mStrItems;
    private LayoutInflater mInflater;

    public SecurityQuestionAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater.from(context));
        mStrItems = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position,convertView,parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.security_question_dropdown_item,parent, false);
        }

        TextView txtView1 = convertView.findViewById(R.id.security_question_dropdown_item_txtView1);
        if (mStrItems.get(position)!=null){
            String strItem = mStrItems.get(position);
            txtView1.setText(strItem);
        }

        return convertView;
    }
}
