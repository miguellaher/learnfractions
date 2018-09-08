package com.example.laher.learnfractions.rankings.list_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.ChapterExamRank;
import com.example.laher.learnfractions.classes.SeatWorkRank;
import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.model.Student;

import java.util.ArrayList;

public class ChapterExamRankListAdapter extends ArrayAdapter<ChapterExamRank> {
    private Context mContext;
    private int mResource;

    static class ViewHolder {
        TextView txtUsername;
        TextView txtRank;
        TextView txtSWDone;
        TextView txtTotalScore;
        TextView txtTotalTime;
    }

    public ChapterExamRankListAdapter(@NonNull Context context, int resource, ArrayList<ChapterExamRank> chapterExamRanks) {
        super(context, resource, chapterExamRanks);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View result;
        ChapterExamRank chapterExamRank = getItem(position);

        ViewHolder holder;
        if (convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.txtUsername = convertView.findViewById(R.id.rank_adapter_txtUserName);
            holder.txtRank = convertView.findViewById(R.id.rank_adapter_txtRank);
            holder.txtSWDone = convertView.findViewById(R.id.rank_adapter_txtSWDone);
            holder.txtTotalScore = convertView.findViewById(R.id.rank_adapter_txtTotalScore);
            holder.txtTotalTime = convertView.findViewById(R.id.rank_adapter_txtTotalTime);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        assert chapterExamRank != null;
        Student student = chapterExamRank.getStudent();
        String username = student.getUsername();
        int rank = position+1;
        String strRank = String.valueOf(rank);
        int swDone = chapterExamRank.getExamDone();
        String strSWDone = String.valueOf(swDone);
        int totalScore = chapterExamRank.getTotalScore();
        String strScore = String.valueOf(totalScore);
        int totalItemsSize = chapterExamRank.getTotalItemsSize();
        String strTotalItemsSize = String.valueOf(totalItemsSize);

        long totalTimeSpent = chapterExamRank.getTotalTimeSpent();
        totalTimeSpent = Math.round(totalTimeSpent/1000d);
        long seconds = totalTimeSpent;
        long minutes = seconds/60;
        seconds = (minutes*60) - seconds;
        seconds = Math.abs(seconds);



        holder.txtUsername.setText(username);
        String strTop = "TOP";
        holder.txtRank.setText(strTop + " " + strRank);
        holder.txtSWDone.setText(strSWDone);
        holder.txtTotalScore.setText(strScore + " / " + strTotalItemsSize);
        if (minutes<=0){
            holder.txtTotalTime.setText(minutes + "m");
        }
        String strTxtTotalTime = holder.txtTotalTime.getText().toString().trim();
        holder.txtTotalTime.setText(strTxtTotalTime + seconds + "s");


        return convertView;
    }
}
