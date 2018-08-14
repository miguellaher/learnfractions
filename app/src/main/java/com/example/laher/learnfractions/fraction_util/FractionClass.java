package com.example.laher.learnfractions.fraction_util;

import android.support.annotation.NonNull;

public class FractionClass implements Comparable<FractionClass>{
    private int numerator;
    private int denominator;
    private int wholeNum;
    private String context;
    public final static String MIXED = "MIXED";
    public final static String IMPROPER = "IMPROPER";
    public final static String PROPER = "PROPER";

    public FractionClass(){
        generateRandFraction(9);
        if (getNumerator()>getDenominator()){
            context = IMPROPER;
        } else {
            context = PROPER;
        }
    }
    public FractionClass(String context){
        if (context.equals(MIXED)){
            generateMixedFraction(9);
        }
        if (context.equals(IMPROPER)){
            generateImproperFraction(9);
        }
        if (context.equals(PROPER)){
            generateProperFraction(9);
        }
        this.context = context;
    }

    public FractionClass(int num, int denom) {
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
    private void generateMixedFraction(int maximum){
        generateRandFraction(9);
        while (numerator>=denominator){
            generateRandFraction(9);
        }
        wholeNum = (int) (Math.random() * 10 + 1);
    }
    public void generateMixedFraction(int minimum, int maximum){
        generateRandFraction(9);
        while (numerator>=denominator){
            generateRandFraction(9);
        }
        wholeNum = (int) (Math.random() * 2 + minimum);
    }
    private void generateImproperFraction(int maximum){
        generateRandFraction(9);
        while (numerator<=denominator){
            generateRandFraction(9);
        }
    }
    private void generateProperFraction(int maximum){
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
            this.context = FractionClass.MIXED;
        }
    }
    public void toImproper(){
        if (this.context.equals(FractionClass.MIXED)) {
            int newNum = (denominator * wholeNum) + numerator;
            this.wholeNum = 0;
            this.numerator = newNum;
            this.context = FractionClass.IMPROPER;
        }
    }
    public FractionClass mixed(){
        int newNum = numerator%denominator;
        int newWholeNum = numerator/denominator;
        if (wholeNum>0) {
            FractionClass newFraction = new FractionClass();
            newFraction.wholeNum = newWholeNum;
            newFraction.numerator = newNum;
            newFraction.denominator = denominator;
            newFraction.setContext(FractionClass.MIXED);
            return newFraction;
        }
        return this;
    }
    public static FractionClass improper(FractionClass f){
        if (f.getContext().equals(MIXED)){
            return new FractionClass((f.getDenominator()*f.getWholeNum())+f.getNumerator(),
                    f.getDenominator());
        }
        return null;
    }
    public Double getValue(){
        Double num = (double) numerator;
        Double denom = (double) denominator;
        if (context.equals(MIXED)){
            Double wholeNum = (double) getWholeNum();
            return ((denom*wholeNum)+num)/denom;
        }
        return num/denom;
    }
    public void lcdConvert(int lcd){
        int q = lcd/this.denominator;
        int p = q*this.numerator;
        setNumerator(p);
        setDenominator(lcd);
    }

    @Override
    public int compareTo(@NonNull FractionClass o) {
        if(this.getValue()>o.getValue()){
            return 1;
        } else if(this.getValue()<o.getValue()){
            return -1;
        }
        return 0;
    }
}