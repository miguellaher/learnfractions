package com.example.laher.learnfractions;

public class LearnFractionsApp {
    public static boolean isAppVisible() {
        return appVisible;
    }

    public static void appResumed() {
        appVisible = true;
    }

    public static void appPaused() {
        appVisible = false;
    }

    private static boolean appVisible;
}

