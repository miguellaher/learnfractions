package com.example.laher.learnfractions.teacher_activities.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.ExamStat;
import com.example.laher.learnfractions.model.SeatWork;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.service.SeatWorkService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.teacher_activities.list_adapters.StudentExamSWStatAdapter;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class StudentExamStatDialog extends Dialog {
    private Context mContext;

    private TextView txtUsername, txtExamTitle;
    private ListView statsList;
    private Button btnSave;

    private ExamStat mExamStat;

    public StudentExamStatDialog(@NonNull Context context, ExamStat examStat) {
        super(context);
        mContext = context;
        mExamStat = examStat;

        setGui();

        txtUsername.setText(examStat.getStudent().getUsername());
        txtExamTitle.setText(examStat.getExamTitle());
        ArrayList<SeatWork> seatWorks = examStat.getSeatWorks();
        StudentExamSWStatAdapter studentExamSWStatAdapter = new StudentExamSWStatAdapter(mContext,R.layout.layout_user_item2,seatWorks);
        statsList.setAdapter(studentExamSWStatAdapter);
    }

    private void setGui(){
        setContentView(R.layout.layout_dialog_student_exam_stat);
        txtUsername = findViewById(R.id.dialog_student_exam_stat_txtUsername);
        txtExamTitle = findViewById(R.id.dialog_student_exam_stat_txtExamTitle);
        statsList = findViewById(R.id.dialog_student_exam_stat_list);
        btnSave = findViewById(R.id.dialog_student_exam_stat_btnSave);
        btnSave.setVisibility(View.INVISIBLE);
    }
}
