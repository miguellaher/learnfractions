package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;
import com.example.laher.learnfractions.util.FractionUtil;

import java.util.ArrayList;
import java.util.Collections;

public class OrderingDissimilarQuestion extends FractionQuestionClass {
    private Fraction fraction1;
    private Fraction fraction2;
    private Fraction fraction3;
    private ArrayList<Fraction> fractions;
    private ArrayList<Fraction> sortedFractions;
    private int quotientLimit;
    private int lcd;

    public ArrayList<Fraction> getSortedFractions() {
        return sortedFractions;
    }

    public int getLcd() {
        return lcd;
    }

    public void setQuotientLimit(int quotientLimit) {
        this.quotientLimit = quotientLimit;
        generateFractions();
    }

    public Fraction getFraction1() {
        return fraction1;
    }

    public Fraction getFraction2() {
        return fraction2;
    }

    public Fraction getFraction3() {
        return fraction3;
    }

    public OrderingDissimilarQuestion() {
        this.quotientLimit = 10;
        generateFractions();
    }

    public void generateFractions(){
        fraction1 = new Fraction();
        fraction2 = new Fraction();
        fraction3 = new Fraction();
        fractions = new ArrayList<>();
        fractions.add(fraction1);
        fractions.add(fraction2);
        fractions.add(fraction3);
        sortedFractions = fractions;
        while ((fraction1.equals(fraction2) ||
                fraction1.equals(fraction3) ||
                fraction2.equals(fraction3)) ||
                limitLcd(this.quotientLimit, fractions)){
            fraction1 = new Fraction();
            fraction2 = new Fraction();
            fraction3 = new Fraction();
            fractions = new ArrayList<>();
            fractions.add(fraction1);
            fractions.add(fraction2);
            fractions.add(fraction3);
            sortedFractions = fractions;
        }
        int lcd = FractionUtil.getLcd(fractions);
        this.lcd = lcd;
        Collections.sort(sortedFractions);
    }

    private boolean limitLcd(int quotientLimit, ArrayList<Fraction> fractions){
        int lcd = FractionUtil.getLcd(fractions);
        boolean pastLimit = false;
        for (Fraction fraction : fractions){
            int denominator = fraction.getDenominator();
            int quotient = lcd / denominator;
            if (quotient > quotientLimit){
                pastLimit = true;
            }
        }
        return pastLimit;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OrderingDissimilarQuestion){
            OrderingDissimilarQuestion orderingDissimilarQuestion = (OrderingDissimilarQuestion) obj;
            return this.sortedFractions.equals(orderingDissimilarQuestion.sortedFractions);
        }
        return super.equals(obj);
    }
}
