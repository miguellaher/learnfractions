package com.example.laher.learnfractions;

import android.support.annotation.NonNull;

public class Fraction implements Comparable<Fraction>{
    private int numerator;
    private int denominator;

    public Fraction(){
        generateRandFraction(9);
    }
    public Fraction(int num, int denom) {
        this.numerator = num;
        this.denominator = denom;
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
        Double num = Double.valueOf(numerator);
        Double denom = Double.valueOf(denominator);
        return num/denom;
    }
    public void lcdConvert(int lcd){
        int q = lcd/this.denominator;
        int p = q*this.numerator;
        setNumerator(p);
        setDenominator(lcd);
    }

    @Override
    public int compareTo(@NonNull Fraction o) {
        if(this.getValue()>o.getValue()){
            return 1;
        } else if(this.getValue()<o.getValue()){
            return -1;
        }
        return 0;
    }
}