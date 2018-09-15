package com.example.laher.learnfractions.teacher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.LessonsMenuActivity;
import com.example.laher.learnfractions.LoginActivity;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.SeatWorkListActivity;
import com.example.laher.learnfractions.adapters.MainActivityListAdapter;
import com.example.laher.learnfractions.classes.AppActivity;
import com.example.laher.learnfractions.dialog_layout.ConfirmationDialog;
import com.example.laher.learnfractions.ClassRanksMainActivity;
import com.example.laher.learnfractions.teacher2.LessonExercisesListActivity;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Encryptor;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Styles;

import java.util.ArrayList;

public class TeacherMainActivity extends AppCompatActivity {
    Context mContext = this;
    //TOOLBAR
    Button btnBack;
    Button btnNext;
    TextView txtTitle;
    //ACTIVITY
    ListView listViewButtons;
    TextView txtWelcome;
    ImageView imgAvatar;
    ConstraintLayout constraintLayoutBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        //TOOLBAR
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
                            Intent intent = new Intent(TeacherMainActivity.this,
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
        btnNext.setVisibility(View.INVISIBLE);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppConstants.TEACHER_MAIN);

        if (!isNetworkAvailable()){
            Storage.logout(mContext);
            Intent intent = new Intent(mContext,
                    LessonsMenuActivity.class);
            startActivity(intent);
        }

        //ACTIVITY
        ConstraintLayout toolbar = findViewById(R.id.constraintLayoutToolbar);
        Styles.bgPaintMainYellow(toolbar);

        constraintLayoutBackground = findViewById(R.id.constraintLayoutBackground);
        Styles.bgPaintRandomMainSet2(constraintLayoutBackground);

        imgAvatar = findViewById(R.id.teacher_main_imgAvatar);
        int resource = Styles.getRandomFritsImageResource();
        imgAvatar.setImageResource(resource);

        txtWelcome = findViewById(R.id.teacher_main_txtWelcome);
        String teacherUsername = Storage.load(mContext, Storage.TEACHER_USERNAME);
        String teacherCode = Storage.load(mContext, Storage.TEACHER_CODE);
        teacherCode = Encryptor.decrypt(teacherCode);
        String strWelcome = "Welcome! " + teacherUsername + ",\nteacher of Class " + teacherCode;
        txtWelcome.setText(strWelcome);
        Styles.paintBlack(txtWelcome);

        String manageExercisesTitle = "Manage Exercises";
        AppActivity manageExercises = new AppActivity(mContext, LessonExercisesListActivity.class, manageExercisesTitle);
        String manageSeatworksTitle = "Manage Seatworks";
        AppActivity manageSeatworks = new AppActivity(mContext, SeatWorkListActivity.class, manageSeatworksTitle);
        String manageExamsTitle = "Manage Exams";
        AppActivity manageExams = new AppActivity(mContext, ManageExamsActivity.class, manageExamsTitle);
        String manageClassRanksTitle = "Class Ranks";
        AppActivity classRanks = new AppActivity(mContext, ClassRanksMainActivity.class, manageClassRanksTitle);

        final ArrayList<AppActivity> activities = new ArrayList<>();
        activities.add(manageExercises);
        activities.add(manageSeatworks);
        activities.add(manageExams);
        activities.add(classRanks);

        MainActivityListAdapter adapter = new MainActivityListAdapter(mContext, R.layout.teacher_main_activity_list_item, activities);

        listViewButtons = findViewById(R.id.teacher_main_listButtons);
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

        Styles.bgPaintMainBlue(btnBack);
        /*TEMPORARY
        btnManageSeatWorks.setEnabled(false);
        btnManageExams.setEnabled(false);
        btnCheckSWProgress.setEnabled(false);
        btnCheckEProgress.setEnabled(false);
        btnCheckExamProgress.setEnabled(false);*/
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
