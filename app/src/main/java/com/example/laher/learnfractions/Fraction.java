package com.example.laher.learnfractions;

import android.support.annotation.NonNull;

public class Fraction implements Comparable<Fraction>{
    private int numerator;
    private int denominator;
    private int wholeNum;
    private String context;
    public final static String MIXED = "MIXED";
    public final static String IMPROPER = "IMPROPER";
    public final static String PROPER = "PROPER";

    public Fraction(){
        generateRandFraction(9);
    }
    public Fraction(String context){
        if (context == MIXED){
            generateMixedFraction(9);
        }
        if (context == IMPROPER){
            generateImproperFraction(9);
        }
        if (context == PROPER){
            generateProperFraction(9);
        }
        this.context = context;
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
    public int getWholeNum() {
        return wholeNum;
    }
    public String getContext() {
        return context;
    }
    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }
    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }
    public void setWholeNum(int wholeNum) {
        this.wholeNum = wholeNum;
    }
    public void setContext(String context) {
        this.context = context;
    }

    public void generateRandFraction(int maximum){
        numerator = (int) (Math.random() * maximum + 1);
        denominator = (int) (Math.random() * maximum + 1);
    }
    public void generateRandFraction(int minimum, int maximum){
        numerator = (int) (Math.random() * maximum + minimum);
        denominator = (int) (Math.random() * maximum + minimum);
    }
    public void generateMixedFraction(int maximum){
        generateRandFraction(9);
        while (numerator>=denominator){
            generateRandFraction(9);
        }
        wholeNum = (int) (Math.random() * maximum + 1);
    }
    public void generateMixedFraction(int minimum, int maximum){
        generateRandFraction(9);
        while (numerator>=denominator){
            generateRandFraction(9);
        }
        wholeNum = (int) (Math.random() * maximum + minimum);
    }
    public void generateImproperFraction(int maximum){
        generateRandFraction(9);
        while (numerator<=denominator){
            generateRandFraction(9);
        }
    }
    public void generateProperFraction(int maximum){
        generateRandFraction(9);
        while (numerator>=denominator){
            generateRandFraction(9);
        }
    }
    public void toMixed(){
        int newNum = numerator%denominator;
        int newWholeNum = numerator/denominator;
        if (newWholeNum>0){
            this.wholeNum = newWholeNum;
            this.numerator = newNum;
        }
    }
    public Fraction mixed(){
        int newNum = numerator%denominator;
        int newWholeNum = numerator/denominator;
        Fraction newFraction = new Fraction();
        newFraction.wholeNum = newWholeNum;
        newFraction.numerator = newNum;
        newFraction.denominator = denominator;
        newFraction.setContext(Fraction.MIXED);
        return newFraction;
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