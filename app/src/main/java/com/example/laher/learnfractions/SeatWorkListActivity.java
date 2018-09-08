package com.example.laher.learnfractions;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.adapters.SeatWorkListAdapter;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.seat_works.AddSubMixedFractionsSeatWork;
import com.example.laher.learnfractions.seat_works.AddingDissimilarSeatWork;
import com.example.laher.learnfractions.seat_works.AddingSimilarSeatWork;
import com.example.laher.learnfractions.seat_works.ComparingDissimilarSeatWork;
import com.example.laher.learnfractions.seat_works.ComparingSimilarSeatWork;
import com.example.laher.learnfractions.seat_works.DividingFractionsSeatWork;
import com.example.laher.learnfractions.seat_works.FractionMeaningSeatWork;
import com.example.laher.learnfractions.seat_works.MultiplyDivideMixedFractionsSeatWork;
import com.example.laher.learnfractions.seat_works.MultiplyingFractionsSeatWork;
import com.example.laher.learnfractions.seat_works.OrderingDissimilarSeatWork;
import com.example.laher.learnfractions.seat_works.OrderingSimilarSeatWork;
import com.example.laher.learnfractions.seat_works.SubtractingDissimilarSeatWork;
import com.example.laher.learnfractions.seat_works.SubtractingSimilarSeatWork;
import com.example.laher.learnfractions.service.SeatWorkService;
import com.example.laher.learnfractions.service.SeatWorkStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.student_activities.StudentMainActivity;
import com.example.laher.learnfractions.teacher.TeacherMainActivity;
import com.example.laher.learnfractions.teacher.dialogs.SeatWorkUpdateDialog;
import com.example.laher.learnfractions.util.AppCache;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Styles;

import org.json.JSONObject;

import java.util.ArrayList;

public class SeatWorkListActivity extends AppCompatActivity {
    private static final String TAG = "SW_LIST";
    Context mContext = this;
    ListView seatWorkListView;
    ArrayList<SeatWork> seatWorks;
    SeatWorkListAdapter seatworkListAdapter;

    //TOOLBAR
    TextView txtTitle;
    Button btnBack;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_template);
        seatWorkListView = findViewById(R.id.seatwork_list);
        //TOOLBAR
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppConstants.SEAT_WORKS);
        btnBack = findViewById(R.id.btnBack);

        ConstraintLayout toolbar = findViewById(R.id.constraintLayoutToolbar);
        Styles.bgPaintMainYellow(toolbar);

        Styles.bgPaintMainBlue(btnBack);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);

        seatWorks = getSeatWorks();

        String s = Storage.load(mContext, Storage.USER_TYPE);
        if (AppConstants.TEACHER.equals(s)) {
            teacherMode();

        } else if (AppConstants.STUDENT.equals(s)) {
            studentMode();

        } else {
            baseMode();

        }
    }

    private void studentMode(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeatWorkListActivity.this,
                        StudentMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        seatworkListAdapter = new SeatWorkListAdapter(mContext, R.layout.layout_user_item, seatWorks);
        seatWorkListView.setAdapter(seatworkListAdapter);

        seatWorkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SeatWork seatWork = seatWorks.get(position);
                AppCache.setSeatWork(seatWork);
                Intent intent = new Intent(mContext, seatWorks.get(position).getClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        Service service = new Service("Loading seat works...", mContext, new ServiceResponse() {
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
                        for (SeatWork seatWork : seatWorks){
                            if (downloadedSeatWork.equals(seatWork)){
                                seatWork.getValuesFrom(downloadedSeatWork);
                                seatWorks.set(i, seatWork);
                            }
                            i++;
                        }
                    }
                    seatworkListAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SeatWorkService.getUpdates(mContext, service);

        Service getStatsService = new Service("Getting stats...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    ArrayList<SeatWork> downloadedSeatworkStats = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        String seatworkID = response.optString(i + "seatwork_id");
                        String strItemsSize = response.optString(i + "items_size");
                        String strMinimum = response.optString(i + "r_minimum");
                        String strMaximum = response.optString(i + "r_maximum");
                        String topic_name = response.optString(i + "topic_name");
                        String strScore = response.optString(i + "score");
                        String strTimeSpent = response.optString(i + "time_spent");

                        int itemsSize = Integer.valueOf(strItemsSize);

                        int minimum = Integer.valueOf(strMinimum);
                        int maximum = Integer.valueOf(strMaximum);
                        Range range = new Range(minimum,maximum);

                        int score = Integer.valueOf(strScore);
                        long timeSpent = Long.valueOf(strTimeSpent);

                        SeatWork downloadedSeatworkStat = new SeatWork();
                        downloadedSeatworkStat.setId(seatworkID);
                        downloadedSeatworkStat.setItems_size(itemsSize);
                        downloadedSeatworkStat.setRange(range);
                        downloadedSeatworkStat.setTopicName(topic_name);
                        downloadedSeatworkStat.setCorrect(score);
                        downloadedSeatworkStat.setTimeSpent(timeSpent);

                        downloadedSeatworkStats.add(downloadedSeatworkStat);
                    }
                    for(SeatWork downloadedSeatworkStat : downloadedSeatworkStats){
                        int i = 0;
                        for (SeatWork seatWork : seatWorks){
                            if (seatWork.isUpdatedWith(downloadedSeatworkStat)){
                                seatWork.getStatsFrom(downloadedSeatworkStat);
                                seatWork.setAnswered(true);
                                seatWorks.set(i, seatWork);
                            }
                            i++;
                        }
                    }
                    seatworkListAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                    seatworkListAdapter.notifyDataSetChanged();
                }
            }
        });
        SeatWorkStatService.getStats(mContext, getStatsService);
    }

    private void teacherMode(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeatWorkListActivity.this,
                        TeacherMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        Service service = new Service("Loading seat works...", mContext, new ServiceResponse() {
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
                        for (SeatWork seatWork : seatWorks){
                            if (downloadedSeatWork.equals(seatWork)){
                                seatWork.getValuesFrom(downloadedSeatWork);
                                seatWorks.set(i, seatWork);
                            }
                            i++;
                        }
                    }
                    seatworkListAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SeatWorkService.getUpdates(mContext, service);

        seatworkListAdapter = new SeatWorkListAdapter(mContext, R.layout.layout_user_item, seatWorks, AppConstants.TEACHER);
        seatWorkListView.setAdapter(seatworkListAdapter);

        seatWorkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SeatWorkUpdateDialog seatWorkUpdateDialog = new SeatWorkUpdateDialog(mContext, seatWorks.get(position));
                seatWorkUpdateDialog.show();
                seatWorkUpdateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        seatworkListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public static ArrayList<SeatWork> getSeatWorks(){ // "ARCHIVE OF SEATWORKS"
        ArrayList<SeatWork> seatWorks = new ArrayList<>();

        FractionMeaningSeatWork fractionMeaningSeatWork = new FractionMeaningSeatWork(AppConstants.FRACTION_MEANING);
        ComparingSimilarSeatWork comparingSimilarSeatWork = new ComparingSimilarSeatWork(AppConstants.COMPARING_SIMILAR_FRACTIONS);
        ComparingDissimilarSeatWork comparingDissimilarSeatWork = new ComparingDissimilarSeatWork(AppConstants.COMPARING_DISSIMILAR_FRACTIONS);
        OrderingSimilarSeatWork orderingSimilarSeatWork = new OrderingSimilarSeatWork(AppConstants.ORDERING_SIMILAR);
        OrderingDissimilarSeatWork orderingDissimilarSeatWork = new OrderingDissimilarSeatWork(AppConstants.ORDERING_DISSIMILAR);
        AddingSimilarSeatWork addingSimilarSeatWork = new AddingSimilarSeatWork(AppConstants.ADDING_SIMILAR);
        SubtractingSimilarSeatWork subtractingSimilarSeatWork = new SubtractingSimilarSeatWork(AppConstants.SUBTRACTING_SIMILAR);
        AddingDissimilarSeatWork addingDissimilarSeatWork = new AddingDissimilarSeatWork(AppConstants.ADDING_DISSIMILAR);
        SubtractingDissimilarSeatWork subtractingDissimilarSeatWork = new SubtractingDissimilarSeatWork(AppConstants.SUBTRACTING_DISSIMILAR);
        MultiplyingFractionsSeatWork multiplyingFractionsSeatWork = new MultiplyingFractionsSeatWork(AppConstants.MULTIPLYING_FRACTIONS);
        DividingFractionsSeatWork dividingFractionsSeatWork = new DividingFractionsSeatWork(AppConstants.DIVIDING_FRACTIONS);
        AddSubMixedFractionsSeatWork addSubMixedFractionsSeatWork = new AddSubMixedFractionsSeatWork(AppConstants.ADDING_SUBTRACTING_MIXED);
        MultiplyDivideMixedFractionsSeatWork multiplyDivideMixedFractionsSeatWork = new MultiplyDivideMixedFractionsSeatWork(AppConstants.MULTIPLYING_DIVIDING_MIXED);

        seatWorks.add(fractionMeaningSeatWork);
        seatWorks.add(comparingSimilarSeatWork);
        seatWorks.add(comparingDissimilarSeatWork);
        seatWorks.add(orderingSimilarSeatWork);
        seatWorks.add(orderingDissimilarSeatWork);
        seatWorks.add(addingSimilarSeatWork);
        seatWorks.add(subtractingSimilarSeatWork);
        seatWorks.add(addingDissimilarSeatWork);
        seatWorks.add(subtractingDissimilarSeatWork);
        seatWorks.add(multiplyingFractionsSeatWork);
        seatWorks.add(dividingFractionsSeatWork);
        seatWorks.add(addSubMixedFractionsSeatWork);
        seatWorks.add(multiplyDivideMixedFractionsSeatWork);

        return seatWorks;
    }

    private void baseMode(){
        Log.d(TAG, "User type: " + Storage.load(mContext,Storage.USER_TYPE));

        seatworkListAdapter = new SeatWorkListAdapter(mContext, R.layout.layout_user_item, seatWorks);
        seatWorkListView.setAdapter(seatworkListAdapter);

        seatWorkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, seatWorks.get(position).getClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
