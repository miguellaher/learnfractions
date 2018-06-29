package com.example.laher.learnfractions;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.laher.learnfractions.archive.LessonArchive;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Laher on 4/28/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mTitle = new ArrayList<>();
    private ArrayList<String> mImageUrl = new ArrayList<>();
    private ArrayList<Class> mClasses = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter (Context context ,ArrayList<String> imageNames, ArrayList<String> images, ArrayList<Class> classes){
        mTitle = imageNames;
        mImageUrl = images;
        mContext = context;
        mClasses = classes;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_topic, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrl.get(position))
                .into(holder.imgTopic);

        holder.txtTopicName.setText(mTitle.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mTitle.get(position));

                Toast.makeText(mContext, mTitle.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, mClasses.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTitle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgTopic;
        TextView txtTopicName;
        RelativeLayout parentLayout;

        TextView txtLessonTitle, txtEnabled;

        public ViewHolder(View itemView) {
            super(itemView);
            imgTopic = itemView.findViewById(R.id.imgTopic);
            txtTopicName = itemView.findViewById(R.id.txtTopic);
            parentLayout = itemView.findViewById(R.id.parent_layout_topic);
        }
    }
}
