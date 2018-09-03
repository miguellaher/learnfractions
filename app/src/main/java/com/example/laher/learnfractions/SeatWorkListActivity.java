package com.example.laher.learnfractions;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.adapters.SeatWorkListAdapter;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.model.Teacher;
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
    Button btnBack, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_template);
        seatWorkListView = findViewById(R.id.seatwork_list);
        //TOOLBAR
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(AppConstants.SEAT_WORKS);
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);

        //ACTIVITY
        FractionMeaningSeatWork fractionMeaningSeatWork = new FractionMeaningSeatWork(AppConstants.FRACTION_MEANING);
        ComparingSimilarSeatWork comparingSimilarSeatWork = new ComparingSimilarSeatWork(AppConstants.COMPARING_SIMILAR_FRACTIONS, 1);
        ComparingDissimilarSeatWork comparingDissimilarSeatWork = new ComparingDissimilarSeatWork(AppConstants.COMPARING_DISSIMILAR_FRACTIONS, 1);
        OrderingSimilarSeatWork orderingSimilarSeatWork = new OrderingSimilarSeatWork(AppConstants.ORDERING_SIMILAR, 1);
        OrderingDissimilarSeatWork orderingDissimilarSeatWork = new OrderingDissimilarSeatWork(AppConstants.ORDERING_DISSIMILAR, 1);
        AddingSimilarSeatWork addingSimilarSeatWork = new AddingSimilarSeatWork(AppConstants.ADDING_SIMILAR, 1);
        SubtractingSimilarSeatWork subtractingSimilarSeatWork = new SubtractingSimilarSeatWork(AppConstants.SUBTRACTING_SIMILAR, 1);
        AddingDissimilarSeatWork addingDissimilarSeatWork = new AddingDissimilarSeatWork(AppConstants.ADDING_DISSIMILAR, 1);
        SubtractingDissimilarSeatWork subtractingDissimilarSeatWork = new SubtractingDissimilarSeatWork(AppConstants.SUBTRACTING_DISSIMILAR, 1);
        MultiplyingFractionsSeatWork multiplyingFractionsSeatWork = new MultiplyingFractionsSeatWork(AppConstants.MULTIPLYING_FRACTIONS, 1);
        DividingFractionsSeatWork dividingFractionsSeatWork = new DividingFractionsSeatWork(AppConstants.DIVIDING_FRACTIONS,1);
        AddSubMixedFractionsSeatWork addSubMixedFractionsSeatWork = new AddSubMixedFractionsSeatWork(AppConstants.ADDING_SUBTRACTING_MIXED, 1);
        MultiplyDivideMixedFractionsSeatWork multiplyDivideMixedFractionsSeatWork = new MultiplyDivideMixedFractionsSeatWork(AppConstants.MULTIPLYING_DIVIDING_MIXED,1);


        seatWorks = new ArrayList<>();
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

        if (Storage.load(mContext,Storage.USER_TYPE).equals(AppConstants.TEACHER)){
            teacherMode();
        } else if (Storage.load(mContext,Storage.USER_TYPE).equals(AppConstants.STUDENT)){
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
        Service service = new Service("Loading seat works...", mContext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    ArrayList<SeatWork> uploadedSeatWorks = new ArrayList<>();
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

                        uploadedSeatWorks.add(seatWork);
                    }
                    for (SeatWork uploadedSeatWork : uploadedSeatWorks){
                        int i = 0;
                        for (SeatWork seatWork : seatWorks){
                            if (uploadedSeatWork.equals(seatWork)){
                                seatWork.getValuesFrom(uploadedSeatWork);
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
                    ArrayList<SeatWork> seatWorks2 = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        SeatWork seatWork2 = new SeatWork();
                        seatWork2.setTopicName(String.valueOf(response.optString(i + "topic_name")));
                        Log.d(TAG, seatWork2.getTopicName()+": stat received.");
                        seatWork2.setSeatWorkNum(Integer.valueOf(String.valueOf(response.optString(i + "sw_num"))));
                        seatWork2.setCorrect(Integer.valueOf(String.valueOf(response.optString(i + "score"))));
                        Long timeSpent = Long.valueOf(String.valueOf(response.optString(i + "time_spent"))) * 1000;
                        seatWork2.setTimeSpent(timeSpent);
                        seatWork2.setItems_size(Integer.valueOf(String.valueOf(response.optString(i + "items_size"))));
                        seatWorks2.add(seatWork2);
                    }
                    for(SeatWork seatWork2 : seatWorks2){
                        int i = 0;
                        for (SeatWork seatWork1 : seatWorks){
                            if (seatWork2.getTopicName().equals(seatWork1.getTopicName())){
                                if(seatWork2.getSeatWorkNum()==seatWork1.getSeatWorkNum()){
                                    if(seatWork2.getItems_size()==seatWork1.getItems_size()){
                                        seatWorks.get(i).setAnswered(true);
                                        seatWorks.get(i).setCorrect(seatWork2.getCorrect());
                                        seatWorks.get(i).setTimeSpent(seatWork2.getTimeSpent());
                                        Log.d(TAG, seatWorks.get(i).getTopicName()+": stat updated");
                                    }
                                }
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
        //SeatWorkStatService.getStats(student,getStatsService);

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
                    ArrayList<SeatWork> uploadedSeatWorks = new ArrayList<>();
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

                        uploadedSeatWorks.add(seatWork);
                    }
                    for (SeatWork uploadedSeatWork : uploadedSeatWorks){
                        int i = 0;
                        for (SeatWork seatWork : seatWorks){
                            if (uploadedSeatWork.equals(seatWork)){
                                seatWork.getValuesFrom(uploadedSeatWork);
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
