package com.example.laher.learnfractions.util;

import com.example.laher.learnfractions.chapter_exam.ChapterExam;
import com.example.laher.learnfractions.model.SeatWork;

import java.util.ArrayList;

public class AppCache {
    private static ArrayList<SeatWork> seatWorks;
    private static SeatWork seatWorkStat;
    private static ChapterExam chapterExam;

    public static ChapterExam getChapterExam() {
        return chapterExam;
    }

    public static void setChapterExam(ChapterExam chapterExam) {
        AppCache.chapterExam = chapterExam;
    }

    public static ArrayList<SeatWork> getSeatWorks() {
        return seatWorks;
    }

    public static void setSeatWorks(ArrayList<SeatWork> seatWorks) {
        AppCache.seatWorks = seatWorks;
    }

    public static SeatWork getSeatWorkStat() {
        return seatWorkStat;
    }

    public static void postSeatWorkStat(SeatWork seatWorkStat) {
        AppCache.seatWorkStat = seatWorkStat;
    }
}
