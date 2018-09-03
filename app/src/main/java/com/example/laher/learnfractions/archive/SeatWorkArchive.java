package com.example.laher.learnfractions.archive;

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

import java.util.ArrayList;

public class SeatWorkArchive {
    private final AddingDissimilarSeatWork addingDissimilarSeatWork = new AddingDissimilarSeatWork();
    private final AddingSimilarSeatWork addingSimilarSeatWork = new AddingSimilarSeatWork();
    private final AddSubMixedFractionsSeatWork addSubMixedFractionsSeatWork = new AddSubMixedFractionsSeatWork();
    private final ComparingDissimilarSeatWork comparingDissimilarSeatWork = new ComparingDissimilarSeatWork();
    private final ComparingSimilarSeatWork comparingSimilarSeatWork = new ComparingSimilarSeatWork();
    private final DividingFractionsSeatWork dividingFractionsSeatWork = new DividingFractionsSeatWork();
    private final FractionMeaningSeatWork fractionMeaningSeatWork = new FractionMeaningSeatWork();
    private final MultiplyDivideMixedFractionsSeatWork multiplyDivideMixedFractionsSeatWork = new MultiplyDivideMixedFractionsSeatWork();
    private final MultiplyingFractionsSeatWork multiplyingFractionsSeatWork = new MultiplyingFractionsSeatWork();
    private final OrderingDissimilarSeatWork orderingDissimilarSeatWork = new OrderingDissimilarSeatWork();
    private final OrderingSimilarSeatWork orderingSimilarSeatWork = new OrderingSimilarSeatWork();
    private final SubtractingDissimilarSeatWork subtractingDissimilarSeatWork = new SubtractingDissimilarSeatWork();
    private final SubtractingSimilarSeatWork subtractingSimilarSeatWork = new SubtractingSimilarSeatWork();

    private ArrayList<SeatWork> seatWorkArchive;

    public SeatWorkArchive() {
        seatWorkArchive = new ArrayList<>();
        seatWorkArchive = getSeatWorks();
    }

    public ArrayList<SeatWork> getSeatWorks(){
        ArrayList<SeatWork> seatWorks = new ArrayList<>();
        seatWorks.add(addingDissimilarSeatWork);
        seatWorks.add(addingSimilarSeatWork);
        seatWorks.add(addSubMixedFractionsSeatWork);
        seatWorks.add(comparingDissimilarSeatWork);
        seatWorks.add(comparingSimilarSeatWork);
        seatWorks.add(dividingFractionsSeatWork);
        seatWorks.add(fractionMeaningSeatWork);
        seatWorks.add(multiplyDivideMixedFractionsSeatWork);
        seatWorks.add(multiplyingFractionsSeatWork);
        seatWorks.add(orderingDissimilarSeatWork);
        seatWorks.add(orderingSimilarSeatWork);
        seatWorks.add(subtractingDissimilarSeatWork);
        seatWorks.add(subtractingSimilarSeatWork);
        return seatWorks;
    }

    public SeatWork findSeatWork(SeatWork seatWork){
        int i = 0;
        for (SeatWork seatWork1 : seatWorkArchive){
            if (seatWork.getTopicName().equals(seatWork1.getTopicName())){
                if (seatWork.getSeatWorkNum() == seatWork1.getSeatWorkNum()){
                    int item_size = seatWork.getItems_size();
                    seatWork1.setItems_size(item_size);
                    seatWorkArchive.set(i, seatWork1);
                    return seatWorkArchive.get(i);
                }
            }
            i++;
        }
        return null;
    }
}
