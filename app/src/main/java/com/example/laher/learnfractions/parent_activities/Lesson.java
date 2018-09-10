package com.example.laher.learnfractions.parent_activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.laher.learnfractions.LessonsMenuActivity;
import com.example.laher.learnfractions.util.AppCache;

import java.util.ArrayList;

public class Lesson extends AppCompatActivity {
    private Context context = this;
    private String lessonName;
    private ArrayList<Activity> activities;
    private int activityNumber;

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public Lesson(String lessonName)
    {
        this.lessonName = lessonName;
        activities = new ArrayList<>();
    }

    public Lesson() { // app needs an empty constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Lesson lesson = AppCache.getLesson();

        String lessonName = lesson.getLessonName();
        ArrayList<Activity> activities = lesson.getActivities();
        setLessonName(lessonName);
        setActivities(activities);

        activityNumber = 1;
        start();
    }

    public void addActivity(Activity activity){
        activities.add(activity);
    }

    public void start(){
        if (activityNumber<=activities.size()){
            Activity activity = activities.get(activityNumber-1);
            Class activityClass = activity.getClass();
            Intent intent = new Intent(context,activityClass);
            startActivity(intent);
        } else {
            finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (AppCache.isBackClicked()) {
            AppCache.setBackClicked(false);
            activityNumber--;
            if (activityNumber<1){
                finish();
            } else {
                start();
            }
        } else if (AppCache.isNextClicked()) {
            AppCache.setNextClicked(false);
            activityNumber++;
            start();
        }
    }

    @Override
    public void finish() {
        super.finish();
        Intent intent = new Intent(context, LessonsMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        activityNumber--;
        if (activityNumber<1){
            finish();
        } else {
            start();
        }
    }
}
