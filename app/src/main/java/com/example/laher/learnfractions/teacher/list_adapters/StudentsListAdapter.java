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
import com.example.laher.learnfractions.model.Student;

import java.util.ArrayList;

public class StudentsListAdapter extends ArrayAdapter<Student> {
    private Context mContext;
    private int mResource;

    static class ViewHolder {
        TextView txtUsername;
        TextView txtAge;
        TextView txtGender;
    }

    public StudentsListAdapter(@NonNull Context context, int resource, ArrayList<Student> students) {
        super(context, resource, students);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Student student = getItem(position);

        assert student != null;
        String userName = student.getUsername();
        int age = student.getAge();
        String gender = student.getGender();

        String strAge = String.valueOf(age);

        final View result;

        ViewHolder holder;
        if (convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.txtUsername = convertView.findViewById(R.id.item_student_txtUsername);
            holder.txtAge = convertView.findViewById(R.id.item_student_txtAge);
            holder.txtGender = convertView.findViewById(R.id.item_student_txtGender);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.txtUsername.setText(userName);
        holder.txtAge.setText(strAge);
        holder.txtGender.setText(gender);

        return convertView;
    }
}
