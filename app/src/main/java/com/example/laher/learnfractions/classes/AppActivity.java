package com.example.laher.learnfractions.classes;

import android.content.Context;

public class AppActivity {
    private Context context;
    private Class theClass;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Class getTheClass() {
        return theClass;
    }

    public void setTheClass(Class theClass) {
        this.theClass = theClass;
    }

    public AppActivity(Context context, Class theClass, String title) {
        this.context = context;
        this.theClass = theClass;
        this.title = title;
    }
}
