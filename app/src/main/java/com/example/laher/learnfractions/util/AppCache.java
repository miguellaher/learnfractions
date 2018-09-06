package com.example.laher.learnfractions.util;

import android.annotation.SuppressLint;

import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.model.ExamStat;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.parent_activities.Lesson;

import java.util.ArrayList;

public class AppCache {
    private static ArrayList<SeatWork> seatWorks;
    private static SeatWork seatWork;
    @SuppressLint("StaticFieldLeak")
    private static ChapterExam chapterExam;
    @SuppressLint("StaticFieldLeak")
    private static ExamStat examStat;

    public static ExamStat getExamStat() {
        return examStat;
    }

    public static void setExamStat(ExamStat examStat) {
        AppCache.examStat = examStat;
    }


    public static void postSeatWorkStat(SeatWork seatWorkStat) {
        AppCache.seatWork = seatWorkStat;
    }

    //LESSON ACTIVITIES
    @SuppressLint("StaticFieldLeak")
    private static Lesson lesson;

    public static Lesson getLesson() {
        return lesson;
    }

    public static void setLesson(Lesson lesson) {
        AppCache.lesson = lesson;
    }

    private static boolean nextClicked;

    public static boolean isNextClicked() {
        return nextClicked;
    }

    public static void setNextClicked(boolean nextClicked) {
        AppCache.nextClicked = nextClicked;
    }

    private static boolean backClicked;

    public static boolean isBackClicked() {
        return backClicked;
    }

    public static void setBackClicked(boolean backClicked) {
        AppCache.backClicked = backClicked;
    }
    //LESSON ACTIVITIES




    // FOR CHAPTER EXAM
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

    public static SeatWork getSeatWork() {
        return seatWork;
    }

    public static void setSeatWork(SeatWork seatWork) {
        AppCache.seatWork = seatWork;
    }


    private static boolean inChapterExam;

    public static boolean isInChapterExam() {
        return inChapterExam;
    }

    public static void setInChapterExam(boolean inChapterExam) {
        AppCache.inChapterExam = inChapterExam;
    }
}
