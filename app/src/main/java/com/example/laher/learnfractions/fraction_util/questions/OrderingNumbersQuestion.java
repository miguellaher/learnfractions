package com.example.laher.learnfractions.fraction_util.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OrderingNumbersQuestion {
    private int number1;
    private int number2;
    private int number3;
    private ArrayList<Integer> numbers;
    private ArrayList<Integer> sortedNumbers;

    public ArrayList<Integer> getSortedNumbers() {
        return sortedNumbers;
    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    public int getNumber3() {
        return number3;
    }

    public void setNumber3(int number3) {
        this.number3 = number3;
    }

    public OrderingNumbersQuestion() {
        generateNumbers();
    }

    public void generateNumbers(){
        int number1 = (int) (Math.random() * 9 + 1);
        int number2 = (int) (Math.random() * 9 + 1);
        int number3 = (int) (Math.random() * 9 + 1);
        while (number1==number2 || number1==number3 ||
                number2==number3){
            number1 = (int) (Math.random() * 9 + 1);
            number2 = (int) (Math.random() * 9 + 1);
            number3 = (int) (Math.random() * 9 + 1);
        }
        numbers.add(number1);
        numbers.add(number2);
        numbers.add(number3);
        sortedNumbers = numbers;
        Collections.sort(sortedNumbers, new SortNumbers());
    }
    private class SortNumbers implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            if (o1<o2){
                return -1;
            } else if (o1<o2){
                return 1;
            }
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OrderingNumbersQuestion){
            OrderingNumbersQuestion orderingNumbersQuestion = (OrderingNumbersQuestion) obj;
            ArrayList<Integer> numbers1 = this.numbers;
            ArrayList<Integer> numbers2 = orderingNumbersQuestion.numbers;
            Collections.sort(numbers1);
            Collections.sort(numbers2);
            return numbers1.equals(numbers2);
        }
        return super.equals(obj);
    }
}
