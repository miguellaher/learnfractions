package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;
import com.example.laher.learnfractions.util.FractionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    public int getQuotientLimit() {
        return quotientLimit;
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
        double doubleFraction1 = fraction1.toDouble();
        double doubleFraction2 = fraction2.toDouble();
        double doubleFraction3 = fraction3.toDouble();
        while ((fraction1.equals(fraction2) ||
                fraction1.equals(fraction3) ||
                fraction2.equals(fraction3)) ||
                limitLcd(this.quotientLimit, fractions) ||
                doubleFraction1 == doubleFraction2 ||
                doubleFraction1 == doubleFraction3 ||
                doubleFraction2 == doubleFraction3){
            fraction1 = new Fraction();
            fraction2 = new Fraction();
            fraction3 = new Fraction();
            fractions = new ArrayList<>();
            fractions.add(fraction1);
            fractions.add(fraction2);
            fractions.add(fraction3);
            sortedFractions = fractions;
            doubleFraction1 = fraction1.toDouble();
            doubleFraction2 = fraction2.toDouble();
            doubleFraction3 = fraction3.toDouble();
        }
        this.lcd = FractionUtil.getLcd(fractions);
        Collections.sort(sortedFractions, new SortFraction());
    }

    public static boolean limitLcd(int quotientLimit, ArrayList<Fraction> fractions){
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

    private class SortFraction implements Comparator<Fraction>{
        @Override
        public int compare(Fraction o1, Fraction o2) {
            if (o1.compare(o2)>0){
                return 1;
            } else if (o1.compare(o2)<0){
                return -1;
            } else {
                return 0;
            }
        }
    }
}
