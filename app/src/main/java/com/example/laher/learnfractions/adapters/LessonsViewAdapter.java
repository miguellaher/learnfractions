package com.example.laher.learnfractions.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.parent_activities.Lesson;
import com.example.laher.learnfractions.util.AppCache;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Laher on 4/28/2018.
 */

public class LessonsViewAdapter extends RecyclerView.Adapter<LessonsViewAdapter.ViewHolder>{

    private static final String TAG = "LessonsViewAdapter";

    private ArrayList<Lesson> lessons;
    private Context mContext;

    public LessonsViewAdapter(Context context, ArrayList<Lesson> lessons){
        this.lessons = lessons;
        mContext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_topic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        ArrayList<Lesson> lessons = this.lessons;
        final Lesson lesson = lessons.get(position);
        final String lessonName = lesson.getLessonName();

        int lessonPosition = position + 1;
        
        ArrayList<Integer> resources = Styles.getFritsImageResources();
        int resourcesSize = resources.size();
        while (lessonPosition>resourcesSize){
            lessonPosition = lessonPosition - resourcesSize;
        }
        int resource = resources.get(lessonPosition-1);
        String imgUrl = getURLForResource(resource);
        final Class lessonClass = lesson.getClass();
        
        lessonPosition = position + 1;
        
        while (lessonPosition>4){
            lessonPosition = lessonPosition - 4;
        }

        if (lessonPosition==1){
            Styles.bgPaintMainOrange(holder.parentLayout);
        } else if (lessonPosition==2){
            Styles.bgPaintMainBlueGreen(holder.parentLayout);
        } else if (lessonPosition==3){
            Styles.bgPaintMainBlue(holder.parentLayout);
        } else if (lessonPosition==4){
            Styles.bgPaintMainYellow(holder.parentLayout);
        }

        Glide.with(mContext)
                .asBitmap()
                .load(imgUrl)
                .into(holder.imgTopic);

        holder.txtTopicName.setText(lessonName);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + lessonName);

                AppCache.setLesson(lesson);
                Intent intent = new Intent(mContext, lessonClass);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        ArrayList<Lesson> lessons = this.lessons;
        return lessons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgTopic;
        TextView txtTopicName;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            imgTopic = itemView.findViewById(R.id.imgTopic);
            txtTopicName = itemView.findViewById(R.id.txtTopic);
            parentLayout = itemView.findViewById(R.id.parent_layout_topic);
        }
    }

    private String getURLForResource(int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }
}
