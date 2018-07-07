package com.example.laher.learnfractions;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.laher.learnfractions.adapters.SeatWorkListAdapter;
import com.example.laher.learnfractions.model.SeatWork;
import com.example.laher.learnfractions.seatworks.AddSubMixedFractionsSeatWork;
import com.example.laher.learnfractions.seatworks.AddingDissimilarSeatWork;
import com.example.laher.learnfractions.seatworks.AddingSimilarSeatWork;
import com.example.laher.learnfractions.seatworks.ComparingDissimilarSeatWork;
import com.example.laher.learnfractions.seatworks.ComparingSimilarSeatWork;
import com.example.laher.learnfractions.seatworks.DividingFractionsSeatWork;
import com.example.laher.learnfractions.seatworks.FractionMeaningSeatWork;
import com.example.laher.learnfractions.seatworks.MultiplyingFractionsSeatWork;
import com.example.laher.learnfractions.seatworks.OrderingDissimilarSeatWork;
import com.example.laher.learnfractions.seatworks.OrderingSimilarSeatWork;
import com.example.laher.learnfractions.seatworks.SubtractingDissimilarSeatWork;
import com.example.laher.learnfractions.seatworks.SubtractingSimilarSeatWork;
import com.example.laher.learnfractions.util.AppConstants;

import java.util.ArrayList;

public class SeatworkListActivity extends AppCompatActivity {
    //private static final String TAG = "SW_LIST";
    Context mContext = this;
    ListView seatWorkListView;
    ArrayList<SeatWork> seatWorks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seatwork_list);
        seatWorkListView = findViewById(R.id.seatwork_list);

        FractionMeaningSeatWork fractionMeaningSeatWork = new FractionMeaningSeatWork(AppConstants.FRACTION_MEANING, 1);
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


        SeatWorkListAdapter seatworkListAdapter = new SeatWorkListAdapter(mContext, R.layout.layout_user_item, seatWorks);
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
