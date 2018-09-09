package com.example.laher.learnfractions.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.AppActivity;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

public class MainActivityListAdapter extends ArrayAdapter<AppActivity> {
    private Context mContext;
    private int mResource;
    private String mUserType;

    public MainActivityListAdapter(@NonNull Context context, int resource, ArrayList<AppActivity> appActivities) {
        super(context, resource, appActivities);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AppActivity activity = getItem(position);

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        ConstraintLayout constraintLayoutBackground = convertView.findViewById(R.id.constraintLayoutBackground);

        int activityPosition = position + 1;
        while (activityPosition>4){
            activityPosition = activityPosition - 4;
        }

        if (activityPosition==1){
            Styles.bgPaintMainBlue(constraintLayoutBackground);
        } else if (activityPosition==2){
            Styles.bgPaintMainYellow(constraintLayoutBackground);
        } else if (activityPosition==3){
            Styles.bgPaintMainOrange(constraintLayoutBackground);
        } else if (activityPosition==4){
            Styles.bgPaintMainBlueGreen(constraintLayoutBackground);
        }

        TextView txtButtonText = convertView.findViewById(R.id.teacher_activity_list_txtButtonText);

        assert activity != null;
        String title = activity.getTitle();

        txtButtonText.setText(title);
        Styles.paintBlack(txtButtonText);

        return convertView;
    }
}
