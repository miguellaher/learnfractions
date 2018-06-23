package com.example.laher.learnfractions.util;

import android.content.Context;
import android.widget.Toast;

import java.util.UUID;

public class Util {
    public static String generateId(){
        return UUID.randomUUID().toString();
    }
    public static void toast(Context context, String message){
        Context con = context.getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(con, text, duration);
        toast.show();
    }
}
