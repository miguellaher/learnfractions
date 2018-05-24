package com.example.laher.learnfractions;

import android.support.annotation.NonNull;

public class Fraction implements Comparable<Fraction>{
    private int numerator;
    private int denominator;

    public Fraction(){
        generateRandFraction(9);
    }
    public int getNumerator() {
        return numerator;
    }
    public int getDenominator() {
        return denominator;
    }
    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }
    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }
    public void generateRandFraction(int maximum){
        numerator = (int) (Math.random() * maximum + 1);
        denominator = (int) (Math.random() * maximum + 1);
    }
    public void generateRandFraction(int minimum, int maximum){
        numerator = (int) (Math.random() * maximum + minimum);
        denominator = (int) (Math.random() * maximum + minimum);
    }
    public Double getValue(){
        return Double.valueOf(numerator/denominator);
    }

    @Override
    public int compareTo(@NonNull Fraction o) {
        if(this.getValue()>o.getValue()){
            return 1;
        } else if(this.getValue()>o.getValue()){
            return -1;
        }
        return 0;
    }
}