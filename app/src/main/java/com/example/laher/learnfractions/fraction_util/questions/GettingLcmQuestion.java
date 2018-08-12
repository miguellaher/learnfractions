package com.example.laher.learnfractions.fraction_util.questions;

import android.util.Log;

import com.example.laher.learnfractions.util.FractionUtil;

import java.util.ArrayList;
import java.util.Collections;

public class GettingLcmQuestion {
    private int number1;
    private int number2;
    private int number3;
    private int lcm;
    private ArrayList<Integer> numbers;

    public int getLcm() {
        return lcm;
    }

    public int getNumber1() {
        return number1;
    }

    public int getNumber2() {
        return number2;
    }

    public int getNumber3() {
        return number3;
    }

    public static void main(String[] args){
        GettingLcmQuestion gettingLcmQuestion = new GettingLcmQuestion();
    }

    public GettingLcmQuestion() {
        generateNumbers();
    }

    public void generateNumbers(){
        int number1 = (int) (Math.random() * 9 + 1);
        int number2 = (int) (Math.random() * 9 + 1);
        int number3 = (int) (Math.random() * 9 + 1);
        while (number1==number2 ||
                number1==number3 ||
                number2==number3){
            number1 = (int) (Math.random() * 9 + 1);
            number2 = (int) (Math.random() * 9 + 1);
            number3 = (int) (Math.random() * 9 + 1);
        }
        numbers = new ArrayList<>();
        numbers.add(number1);
        numbers.add(number2);
        numbers.add(number3);
        this.lcm = FractionUtil.getLcm(numbers);
        System.out.print("numbers:");
        for (Integer number : numbers){
            System.out.print(number+", ");
        }
        System.out.println("lcm: " + this.lcm);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GettingLcmQuestion){
            GettingLcmQuestion gettingLcmQuestion = (GettingLcmQuestion) obj;
            ArrayList<Integer> numbers1 = this.numbers;
            ArrayList<Integer> numbers2 = gettingLcmQuestion.numbers;
            Collections.sort(numbers1);
            Collections.sort(numbers2);
            return numbers1.equals(numbers2);
        }
        return super.equals(obj);
    }
}
