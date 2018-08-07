package com.example.laher.learnfractions.teacher_activities.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.model.SeatWork;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.service.ExamService;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.teacher_activities.list_adapters.ExamUpdateAdapter;
import com.example.laher.learnfractions.teacher_activities.list_adapters.StudentExamSWStatAdapter;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;

import org.json.JSONObject;

import java.util.ArrayList;

public class ExamUpdateDialog extends Dialog {
    //GUI
    private TextView txtMainTitle, txtSubTitle;
    private ListView listSeatWorks;
    //VARIABLES
    private Context mContext;
    private ChapterExam mChapterExam;
    private static final String TAG = "EXAM_UPDATE_DIALOG";

    public ExamUpdateDialog(@NonNull Context context, ChapterExam chapterExam) {
        super(context);
        mContext = context;
        mChapterExam = chapterExam;
        final ArrayList<SeatWork> mSeatWorks;

        setGui();

        txtMainTitle.setText(chapterExam.getExamTitle());
        txtSubTitle.setText(AppConstants.SAVE);

        mSeatWorks = chapterExam.getSeatWorks();
        final ExamUpdateAdapter examUpdateAdapter = new ExamUpdateAdapter(mContext,R.layout.layout_user_item,mSeatWorks);
        listSeatWorks.setAdapter(examUpdateAdapter);
        listSeatWorks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final SeatWork seatWork = mSeatWorks.get(position);
                final ExamSeatWorkUpdateDialog examSeatWorkUpdateDialog = new ExamSeatWorkUpdateDialog(mContext, seatWork);
                examSeatWorkUpdateDialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Log.d(TAG, "onDismiss:");
                        seatWork.setItems_size(examSeatWorkUpdateDialog.getItemSize());
                        Log.d(TAG, "seat work: " + seatWork.getTopicName() + " no." + seatWork.getSeatWorkNum());
                        Log.d(TAG, "item size: " + seatWork.getItems_size());
                        mSeatWorks.set(position, seatWork);
                        mChapterExam.setSeatWorks(mSeatWorks);
                        examUpdateAdapter.notifyDataSetChanged();
                    }
                });
                examSeatWorkUpdateDialog.show();
            }
        });
        txtSubTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postExam();
            }
        });

    }

    private void setGui(){
        setContentView(R.layout.layout_dialog_student_exam_stat);
        txtMainTitle = findViewById(R.id.dialog_student_exam_stat_txtUsername);
        txtSubTitle = findViewById(R.id.dialog_student_exam_stat_txtExamTitle);
        listSeatWorks = findViewById(R.id.dialog_student_exam_stat_list);
    }

    private void postExam(){
        com.example.laher.learnfractions.service.Service service = new com.example.laher.learnfractions.service.Service("Posting exam...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    Log.d(TAG, response.optString("message"));
                    dismiss();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        Teacher teacher = new Teacher();
        teacher.setTeacher_code(Storage.load(mContext,Storage.TEACHER_CODE));
        ExamService.postExam(service, mChapterExam, teacher);
    }

    public ChapterExam getmChapterExam() {
        return mChapterExam;
    }

    public void setmChapterExam(ChapterExam mChapterExam) {
        this.mChapterExam = mChapterExam;
    }
}