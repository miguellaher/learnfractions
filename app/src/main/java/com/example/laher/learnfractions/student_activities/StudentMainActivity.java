package com.example.laher.learnfractions.student_activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.ChapterExamListActivity;
import com.example.laher.learnfractions.ClassRanksMainActivity;
import com.example.laher.learnfractions.HelpActivity;
import com.example.laher.learnfractions.LessonsMenuActivity;
import com.example.laher.learnfractions.LoginActivity;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.SeatWorkListActivity;
import com.example.laher.learnfractions.adapters.MainActivityListAdapter;
import com.example.laher.learnfractions.classes.AppActivity;
import com.example.laher.learnfractions.dialog_layout.ConfirmationDialog;
import com.example.laher.learnfractions.parent_activities.MainFrame;
import com.example.laher.learnfractions.util.ActivityUtil;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Encryptor;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

public class StudentMainActivity extends MainFrame {
    Context mContext = this;

    //TOOLBAR
    TextView txtTitle;
    Button btnBack;
    Button btnNext;
    //ACTIVITY
    ListView listViewButtons;
    TextView txtWelcome;
    ImageView imgAvatar;
    ConstraintLayout constraintLayoutBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        //TOOLBAR
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppConstants.STUDENT_MAIN);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConfirmationDialog confirmationDialog = new ConfirmationDialog(mContext, AppConstants.LOGOUT_CONFIRMATION);
                confirmationDialog.show();
                confirmationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (confirmationDialog.isConfirmed()){
                            Storage.logout(mContext);
                            Intent intent = new Intent(StudentMainActivity.this,
                                    LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
        btnBack.setText(AppConstants.LOG_OUT);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setText(AppConstants.HELP);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.stopBgMusicMediaPlayer();
                Intent intent = new Intent(StudentMainActivity.this,
                        HelpActivity.class);
                startActivity(intent);
            }
        });

        if (!isNetworkAvailable()){
            Storage.logout(mContext);
            Intent intent = new Intent(mContext,
                    LessonsMenuActivity.class);
            startActivity(intent);
        }

        //ACTIVITY
        ConstraintLayout toolbar = findViewById(R.id.constraintLayoutToolbar);
        Styles.bgPaintMainYellow(toolbar);

        Styles.bgPaintMainBlue(btnBack);

        Styles.bgPaintMainOrange(btnNext);

        constraintLayoutBackground = findViewById(R.id.constraintLayoutBackground);
        Styles.bgPaintMainBlueGreen(constraintLayoutBackground);

        imgAvatar = findViewById(R.id.student_main_imgAvatar);
        int resource = Styles.getRandomFritsImageResource();
        imgAvatar.setImageResource(resource);

        String studentUsername = Storage.load(mContext, Storage.STUDENT_USERNAME);
        String teacherCode = Storage.load(mContext, Storage.TEACHER_CODE);
        teacherCode = Encryptor.decrypt(teacherCode);
        String strWelcome = "Welcome! " + studentUsername + ",\n from Class " + teacherCode;
        txtWelcome = findViewById(R.id.student_main_txtWelcome);
        txtWelcome.setText(strWelcome);
        Styles.paintBlack(txtWelcome);

        String LessonsTitle = "Lessons";
        AppActivity manageExercises = new AppActivity(mContext, LessonsMenuActivity.class, LessonsTitle);
        String seatworksTitle = "Seatworks";
        AppActivity manageSeatworks = new AppActivity(mContext, SeatWorkListActivity.class, seatworksTitle);
        String chapterExamsTitle = "Chapter Exams";
        AppActivity manageExams = new AppActivity(mContext, ChapterExamListActivity.class, chapterExamsTitle);
        String classRanksTitle = "Class Ranks";
        AppActivity classRanks = new AppActivity(mContext, ClassRanksMainActivity.class, classRanksTitle);

        final ArrayList<AppActivity> activities = new ArrayList<>();
        activities.add(manageExercises);
        activities.add(manageSeatworks);
        activities.add(manageExams);
        activities.add(classRanks);

        MainActivityListAdapter adapter = new MainActivityListAdapter(mContext, R.layout.teacher_main_activity_list_item, activities);

        listViewButtons = findViewById(R.id.student_main_listButtons);
        listViewButtons.setAdapter(adapter);

        listViewButtons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppActivity appActivity = activities.get(position);
                Context context = appActivity.getContext();
                Class theClass = appActivity.getTheClass();

                Intent intent = new Intent(context, theClass);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        ActivityUtil.playBgMusicMediaPlayer(mContext);

        /*TEMPORARY
        btnSeatWorks.setEnabled(false);
        btnChapterExam.setEnabled(false);
        btnClassRanks.setEnabled(false);*/

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
