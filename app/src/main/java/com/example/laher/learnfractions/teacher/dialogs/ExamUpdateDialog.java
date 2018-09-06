package com.example.laher.learnfractions.teacher.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.service.ExamService;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.teacher.list_adapters.ExamUpdateAdapter;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class ExamUpdateDialog extends Dialog {
    //GUI
    private TextView txtMainTitle, txtSubTitle;
    private ListView listSeatWorks;
    private Button btnSave;
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
        txtSubTitle.setVisibility(View.INVISIBLE);
        btnSave.setText(AppConstants.SAVE);

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
                        SeatWork updatedSeatWork1 = examSeatWorkUpdateDialog.getSeatWork();

                        int updatedItemSize = updatedSeatWork1.getItems_size();
                        Range updatedRange = updatedSeatWork1.getRange();

                        if (updatedItemSize>0) {
                            seatWork.setItems_size(updatedItemSize);
                        }

                        if (updatedRange!=null){
                            seatWork.setRange(updatedRange);
                        }

                        mSeatWorks.set(position, seatWork);
                        mChapterExam.setSeatWorks(mSeatWorks);

                        examUpdateAdapter.notifyDataSetChanged();
                    }
                });

                examSeatWorkUpdateDialog.show();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
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
        btnSave = findViewById(R.id.dialog_student_exam_stat_btnSave);
        listSeatWorks = findViewById(R.id.dialog_student_exam_stat_list);
    }

    private void postExam(){
        com.example.laher.learnfractions.service.Service service = new com.example.laher.learnfractions.service.Service("Posting exam...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    String message = response.optString("message");
                    Util.toast(mContext, message);
                    dismiss();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        ExamService.postExam(mContext, mChapterExam, service);
    }

    public ChapterExam getChapterExam() {
        return mChapterExam;
    }
}
