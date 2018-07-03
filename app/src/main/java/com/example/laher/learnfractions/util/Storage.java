package com.example.laher.learnfractions.util;

import android.content.Context;

import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.model.Teacher;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Laher on 1/20/2018.
 */

public class Storage {
    private static FileOutputStream fos;
    private static String FILENAME = "storage";

    public static final String TEACHER_ID="TEACHER_ID=";
    public static final String STUDENT_ID="STUDENT_ID=";
    public static final String TEACHER_CODE="TEACHER_CODE=";
    public static final String USER_TYPE="USER_TYPE=";
    public static void save(Context context, Teacher teacher) {

        try {
            fos = context.getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            String user_type = USER_TYPE + AppConstants.TEACHER;
            write(user_type);
            String teacher_id = TEACHER_ID + teacher.getId();
            write(teacher_id);
            String teacher_code = TEACHER_CODE + teacher.getTeacher_code();
            write(teacher_code);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save(Context context, Student student) {

        try {
            fos = context.getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            String user_type = USER_TYPE + AppConstants.STUDENT;
            write(user_type);
            String student_id = STUDENT_ID + student.getId();
            write(student_id);
            String teacher_code = TEACHER_CODE + student.getTeacher_code();
            write(teacher_code);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void write(String s) throws IOException {
        fos.write(s.getBytes());
        fos.write(";".getBytes());
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

    private static String getStringValue(String[] tokens, String identifier){
        for (String token : tokens){
            if (token.contains(identifier)){
                return token.replace(identifier,"");
            }
        }
        return null;
    }
    public static String load(Context context, String identifier) {
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
        String out = sb.toString();
        String[] tokens = out.split(";");

        if (identifier.equals(TEACHER_ID)){
            out = getStringValue(tokens, TEACHER_ID);
        }
        if (identifier.equals(TEACHER_CODE)){
            out = getStringValue(tokens, TEACHER_CODE);
        }
        if (identifier.equals(USER_TYPE)){
            out = getStringValue(tokens, USER_TYPE);
        }
        out = out.trim();
        return out;
    }
}
