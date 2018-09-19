package com.example.laher.learnfractions.rankings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.SeatWorkListActivity;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.classes.SeatWorkRank;
import com.example.laher.learnfractions.dialog_layout.MessageDialog;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.rankings.list_adapters.SeatWorkRankListAdapter;
import com.example.laher.learnfractions.service.SeatWorkService;
import com.example.laher.learnfractions.service.SeatWorkStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.ClassRanksMainActivity;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Encryptor;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Styles;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ClassSeatWorkRanksActivity extends AppCompatActivity {
    Context mContext = this;

    //ACTIVITY
    ListView studentRanksListView;
    ArrayList<SeatWork> mSeatWorks;
    ArrayList<SeatWorkRank> mSeatWorkRanks;

    //TOOLBAR
    TextView txtTitle;
    Button btnBack;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_template);

        //TOOLBAR
        btnBack = findViewById(R.id.btnBack);

        Styles.bgPaintMainBlue(btnBack);

        ConstraintLayout toolbar = findViewById(R.id.constraintLayoutToolbar);
        Styles.bgPaintMainYellow(toolbar);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassSeatWorkRanksActivity.this,
                        ClassRanksMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);
        txtTitle = findViewById(R.id.txtTitle);

        String userType = Storage.load(mContext, Storage.USER_TYPE);

        String title;
        if (userType.equals(AppConstants.USER)) {
            title = AppConstants.SW_RANKING;
        } else {
            title = AppConstants.SW_RANKING;
            String teacherCode = Storage.load(mContext, Storage.TEACHER_CODE);
            teacherCode = Encryptor.decrypt(teacherCode);
            title = title + "\n of Class " + teacherCode;
        }

        txtTitle.setText(title);
        Styles.paintBlack(txtTitle);

        //ACTIVITY
        studentRanksListView = findViewById(R.id.rank_template_listRanks);

        mSeatWorks = SeatWorkListActivity.getSeatWorks();

        if (isNetworkAvailable()) {
            if (userType.equals(AppConstants.USER)) {
                getStudentsStats();
            } else { // if user type is either student or teacher
                updateSeatworks();
            }
        } else {
            btnBack.performClick();
        }
    }

    private void updateSeatworks(){
        Service service = new Service("Getting updated seat works...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    ArrayList<SeatWork> downloadedSeatWorks = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        SeatWork seatWork = new SeatWork();

                        String id = response.optString(i + "sw_id");
                        String topic_name = response.optString(i + "topic_name");
                        String strItemSize = response.optString(i + "item_size");
                        String strMinimum = response.optString(i + "r_minimum");
                        String strMaximum = response.optString(i + "r_maximum");

                        int itemSize = Integer.valueOf(strItemSize);

                        int minimum = Integer.valueOf(strMinimum);
                        int maximum = Integer.valueOf(strMaximum);
                        Range range = new Range(minimum,maximum);

                        seatWork.setId(id);
                        seatWork.setTopicName(topic_name);
                        seatWork.setItems_size(itemSize);
                        seatWork.setRange(range);

                        downloadedSeatWorks.add(seatWork);
                    }
                    for (SeatWork downloadedSeatWork : downloadedSeatWorks){
                        int i = 0;
                        for (SeatWork seatWork : mSeatWorks){
                            if (downloadedSeatWork.equals(seatWork)){
                                seatWork.getValuesFrom(downloadedSeatWork);
                                mSeatWorks.set(i, seatWork);
                            }
                            i++;
                        }
                    }
                    getStudentsStats();
                } catch (Exception e) {
                    e.printStackTrace();
                    getStudentsStats();
                }
            }
        });
        SeatWorkService.getUpdates(mContext, service);
    }

    private void getStudentsStats(){
        Service service = new Service("Getting seatworks stats...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    ArrayList<SeatWork> downloadedSeatWorksStats = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        SeatWork downloadedSeatWorkStats = new SeatWork();

                        String userType = Storage.load(mContext, Storage.USER_TYPE);

                        String studentUserName;
                        if (userType.equals(AppConstants.USER)){
                            studentUserName = response.optString(i + "user_username");
                        } else {
                            studentUserName = response.optString(i + "student_username");
                        }

                        String studentID = response.optString(i + "stud_id");
                        String seatwork_id = response.optString(i + "seatwork_id");
                        String strItemSize = response.optString(i + "items_size");
                        String strMinimum = response.optString(i + "r_minimum");
                        String strMaximum = response.optString(i + "r_maximum");
                        String topic_name = response.optString(i + "topic_name");
                        String strScore = response.optString(i + "score");
                        String strTimeSpent = response.optString(i + "time_spent");

                        Student student = new Student();
                        student.setId(studentID);
                        student.setUsername(studentUserName);

                        int itemSize = Integer.valueOf(strItemSize);

                        int minimum = Integer.valueOf(strMinimum);
                        int maximum = Integer.valueOf(strMaximum);
                        Range range = new Range(minimum,maximum);

                        int score = Integer.valueOf(strScore);
                        long timeSpent = Long.valueOf(strTimeSpent);

                        downloadedSeatWorkStats.setStudent(student);
                        downloadedSeatWorkStats.setId(seatwork_id);
                        downloadedSeatWorkStats.setItems_size(itemSize);
                        downloadedSeatWorkStats.setRange(range);
                        downloadedSeatWorkStats.setTopicName(topic_name);
                        downloadedSeatWorkStats.setCorrect(score);
                        downloadedSeatWorkStats.setTimeSpent(timeSpent);

                        downloadedSeatWorksStats.add(downloadedSeatWorkStats);
                    }

                    ArrayList<SeatWork> updatedSeatworks = new ArrayList<>();
                    for (SeatWork downloadedSeatWork : downloadedSeatWorksStats){ // getting only the updated seatworks
                        for (SeatWork seatWork : mSeatWorks){
                            if (downloadedSeatWork.equals(seatWork)){
                                if (seatWork.hasEqualValuesWith(downloadedSeatWork)) {
                                    updatedSeatworks.add(downloadedSeatWork);
                                }
                            }
                        }
                    }

                    mSeatWorkRanks = new ArrayList<>();
                    for (SeatWork updatedSeatWork : updatedSeatworks){ // arranging the ranks
                        Student student = updatedSeatWork.getStudent();
                        SeatWorkRank seatWorkRank = new SeatWorkRank(student);
                        if (mSeatWorkRanks.contains(seatWorkRank)){
                            for (SeatWorkRank mSeatWorkRank : mSeatWorkRanks){
                                if (mSeatWorkRank.equals(seatWorkRank)) {
                                    mSeatWorkRank.addSeatWork(updatedSeatWork);
                                }
                            }
                        } else {
                            seatWorkRank.addSeatWork(updatedSeatWork);
                            mSeatWorkRanks.add(seatWorkRank);
                        }
                    }
                    Collections.sort(mSeatWorkRanks);
                    setListAdapter(mSeatWorkRanks);
                } catch (Exception e) {
                    e.printStackTrace();
                    MessageDialog messageDialog = new MessageDialog(mContext, "\nService error.\n");
                    messageDialog.show();
                    messageDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            btnBack.performClick();
                        }
                    });
                }
            }
        });
        SeatWorkStatService.getAllStats(mContext, service);
    }

    private void setListAdapter(ArrayList<SeatWorkRank> seatWorkRanks){
        SeatWorkRankListAdapter adapter = new SeatWorkRankListAdapter(mContext, R.layout.rank_adapter_template, seatWorkRanks);
        studentRanksListView.setAdapter(adapter);

        int seatworkRanksSize = seatWorkRanks.size();

        while (seatworkRanksSize>4){
            seatworkRanksSize = seatworkRanksSize - 4;
        }

        LinearLayout linearLayoutBackground = findViewById(R.id.linearLayoutBackground);

        if (seatworkRanksSize==1){
            Styles.bgPaintMainBlueGreen(linearLayoutBackground);
        } else if (seatworkRanksSize==2){
            Styles.bgPaintMainBlue(linearLayoutBackground);
        } else if (seatworkRanksSize==3){
            Styles.bgPaintMainYellow(linearLayoutBackground);
        } else if (seatworkRanksSize==4){
            Styles.bgPaintMainOrange(linearLayoutBackground);
        }
    }

    @Override
    public void onBackPressed() {
        btnBack.performClick();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
