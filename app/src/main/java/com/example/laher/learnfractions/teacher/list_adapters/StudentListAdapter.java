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
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

public class StudentListAdapter extends ArrayAdapter<Student> {
    private Context mContext;
    private int mResource;

    static class ViewHolder {
        TextView txtUsername;
        TextView txtAge;
        TextView txtGender;
        LinearLayout linearLayout;
    }

    public StudentListAdapter(@NonNull Context context, int resource, ArrayList<Student> students) {
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
        String strAge = String.valueOf(age);
        String gender = student.getGender();

        final View result;

        ViewHolder holder;
        if (convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.txtUsername = convertView.findViewById(R.id.item_student_txtUsername);
            holder.txtAge = convertView.findViewById(R.id.item_student_txtAge);
            holder.txtGender = convertView.findViewById(R.id.item_student_txtGender);
            holder.linearLayout = convertView.findViewById(R.id.linearLayout);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.txtUsername.setText(userName);
        holder.txtAge.setText(strAge);
        holder.txtGender.setText(gender);

        int activityPosition = position + 1;
        while (activityPosition>4){
            activityPosition = activityPosition - 4;
        }

        if (activityPosition==1){
            Styles.bgPaintMainBlueGreen(holder.linearLayout);
        } else if (activityPosition==2){
            Styles.bgPaintMainBlue(holder.linearLayout);
        } else if (activityPosition==3){
            Styles.bgPaintMainYellow(holder.linearLayout);
        } else if (activityPosition==4){
            Styles.bgPaintMainOrange(holder.linearLayout);
        }

        return convertView;
    }
}
