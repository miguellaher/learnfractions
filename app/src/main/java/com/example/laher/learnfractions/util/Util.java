package com.example.laher.learnfractions.util;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParsePosition;
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
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static boolean isNumeric(String str)
    {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }
    public static boolean randomBoolean(){
        int randomNumber = (int) (Math.random() * 2 + 1);
        if (randomNumber==1){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEditTextEmpty(EditText editText) {
        if (editText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

}
