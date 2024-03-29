package com.example.laher.learnfractions.util;

import android.content.Context;

import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.model.User;

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
    private static boolean empty;

    public static final String TEACHER_ID="TEACHER_ID=";
    public static final String STUDENT_ID="STUDENT_ID=";
    public static final String USER_ID="USER_ID=";
    public static final String TEACHER_CODE="TEACHER_CODE=";
    public static final String TEACHER_USERNAME="TEACHER_USERNAME=";
    public static final String STUDENT_USERNAME="STUDENT_USERNAME=";
    public static final String USER_USERNAME="USER_USERNAME=";
    public static final String USER_TYPE="USER_TYPE=";

    public static boolean isEmpty() {
        return empty;
    }

    public static void setEmpty(boolean empty) {
        Storage.empty = empty;
    }


    public static void save(Context context, Teacher teacher) {

        try {
            fos = context.getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            String user_type = USER_TYPE + AppConstants.TEACHER;
            write(user_type);
            String teacher_id = TEACHER_ID + teacher.getId();
            write(teacher_id);
            String teacher_code = TEACHER_CODE + teacher.getTeacher_code();
            write(teacher_code);
            String teacher_username = TEACHER_USERNAME + teacher.getUsername();
            write(teacher_username);
            fos.close();
            empty = false;
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
            String student_username = STUDENT_USERNAME + student.getUsername();
            write(student_username);
            fos.close();
            empty = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save(Context context, User user) {

        try {
            fos = context.getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            String user_type = USER_TYPE + AppConstants.USER;
            write(user_type);
            String user_id = USER_ID + user.getId();
            write(user_id);
            String student_username = USER_USERNAME + user.getUsername();
            write(student_username);
            fos.close();
            empty = false;
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

        switch (identifier) {
            case TEACHER_ID:
                out = getStringValue(tokens, TEACHER_ID);
                break;
            case STUDENT_ID:
                out = getStringValue(tokens, STUDENT_ID);
                break;
            case USER_ID:
                out = getStringValue(tokens, USER_ID);
                break;
            case STUDENT_USERNAME:
                out = getStringValue(tokens, STUDENT_USERNAME);
                break;
            case USER_USERNAME:
                out = getStringValue(tokens, USER_USERNAME);
                break;
            case TEACHER_CODE:
                out = getStringValue(tokens, TEACHER_CODE);
                break;
            case TEACHER_USERNAME:
                out = getStringValue(tokens, TEACHER_USERNAME);
                break;
            case USER_TYPE:
                out = getStringValue(tokens, USER_TYPE);
                break;
        }
        if (out!=null) {
            out = out.trim();
        }
        return out;
    }

    public static void logout(Context context) {

        try {
            FileOutputStream fos = context.getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write("".getBytes());
            fos.close();
            empty = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
