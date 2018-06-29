package com.example.laher.learnfractions.util;

import android.content.Context;

import com.example.laher.learnfractions.model.Teacher;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by Laher on 1/20/2018.
 */

public class Storage {
    private static String FILENAME = "storage";
    public static void save(Context context, Teacher teacher) {

        try {
            FileOutputStream fos = context.getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(teacher.getId().getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String load(Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = context.getApplicationContext().openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
