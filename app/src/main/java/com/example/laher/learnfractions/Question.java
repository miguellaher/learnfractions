package com.example.laher.learnfractions;

import java.util.ArrayList;
import java.util.Collections;

public class Question {
    int num1, num2, num3;
    String answer;
    ArrayList<Integer> integerNums;
    String context;

    public final static String ORDERING_SIMILAR = "ORDERING_SIMILAR";
    public final static String GETTING_LCD = "GETTING_LCD";

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
            setThreeNums(99,0);
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
        if (context == GETTING_LCD){
            setThreeNums(9,2);
            while (num1==num2 || num2==num3 || num1 == num3){
                setThreeNums(9,2);
            }
            int[] numbers = {num1,num2,num3};
            answer = String.valueOf(getLCM(numbers));
        }
    }
    public void setThreeNums(int max, int min){
        num1 = (int) (Math.random() * max + min);
        num2 = (int) (Math.random() * max + min);
        num3 = (int) (Math.random() * max + min);
    }
    public static long getLCM(int[] element_array)
    {
        long lcm_of_array_elements = 1;
        int divisor = 2;
        while (true) {
            int counter = 0;
            boolean divisible = false;
            for (int i = 0; i < element_array.length; i++) {
                if (element_array[i] == 0) {
                    return 0;
                }
                else if (element_array[i] < 0) {
                    element_array[i] = element_array[i] * (-1);
                }
                if (element_array[i] == 1) {
                    counter++;
                }
                if (element_array[i] % divisor == 0) {
                    divisible = true;
                    element_array[i] = element_array[i] / divisor;
                }
            }
            if (divisible) {
                lcm_of_array_elements = lcm_of_array_elements * divisor;
            }
            else {
                divisor++;
            }
            if (counter == element_array.length) {
                return lcm_of_array_elements;
            }
        }
    }
}