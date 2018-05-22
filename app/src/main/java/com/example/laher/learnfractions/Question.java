package com.example.laher.learnfractions;

import java.util.ArrayList;
import java.util.Collections;

public class Question {
    int num1, num2, num3;
    String answer;
    ArrayList<Integer> integerNums;
    String context;

    public final static String ORDERING_SIMILAR = "ORDERING_SIMILAR";

    public int getNum1() {
        return num1;
    }
    public int getNum2() {
        return num2;
    }
    public int getNum3() {
        return num3;
    }
    public String getAnswer() {
        return answer;
    }
    public int getIntegerNumAt(int index) {
        return integerNums.get(index);
    }

    public Question(String context){
        this.context = context;
        if (context == ORDERING_SIMILAR){
            num1 = (int) (Math.random() * 99);
            num2 = (int) (Math.random() * 99);
            num3 = (int) (Math.random() * 99);
            while (num1==num2 || num2==num3 || num1 == num3){
                num2 = (int) (Math.random() * 99);
                num3 = (int) (Math.random() * 99);
            }
            integerNums = new ArrayList<>();
            integerNums.add(num1);
            integerNums.add(num2);
            integerNums.add(num3);
            Collections.sort(integerNums);
        }
    }
}