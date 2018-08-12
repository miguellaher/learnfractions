package com.example.laher.learnfractions.fraction_util;

import android.support.annotation.NonNull;

public class Fraction implements Comparable<Fraction>{
    private int numerator;
    private int denominator;
    private String modifier;
    public static final String PROPER = "PROPER";
    public static final String IMPROPER = "IMPROPER";
    public static final String MIXED = "MIXED";

    public String getModifier() {
        return modifier;
    }

    public final void setModifier(String modifier){
        if (modifier.equals(PROPER)){
            this.modifier = PROPER;
        } else if (modifier.equals(IMPROPER)){
            this.modifier = IMPROPER;
        } else if (modifier.equals(MIXED)){
            this.modifier = MIXED;
        }
    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    public Fraction() {
        generateFraction();
    }

    public Fraction(String modifier) {
        if (modifier.equals(PROPER)){
            generateProperFraction();
        } else if (modifier.equals(IMPROPER)){
            generateImproperFraction();
        }
        generateFraction();
    }

    public void generateFraction(){
        int numerator = (int) (Math.random() * 9 + 1);
        int denominator = (int) (Math.random() * 9 + 1);
        setModifier();
        setNumerator(numerator);
        setDenominator(denominator);
    }

    private final void setModifier(){
        if (numerator>denominator){
            this.modifier = IMPROPER;
        } else {
            this.modifier = PROPER;
        }
    }

    public final void generateProperFraction(){
        int numerator = (int) (Math.random() * 9 + 1);
        int denominator = (int) (Math.random() * 9 + 1);
        while (denominator>numerator){
            numerator = (int) (Math.random() * 9 + 1);
            denominator = (int) (Math.random() * 9 + 1);
        }
        this.modifier = PROPER;
        setNumerator(numerator);
        setDenominator(denominator);
    }
    public final void generateImproperFraction(){
        int numerator = (int) (Math.random() * 9 + 1);
        int denominator = (int) (Math.random() * 9 + 1);
        while (numerator<=denominator){
            numerator = (int) (Math.random() * 9 + 1);
            denominator = (int) (Math.random() * 9 + 1);
        }
        this.modifier = IMPROPER;
        setNumerator(numerator);
        setDenominator(denominator);
    }

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        setModifier();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Fraction){
            Fraction fraction = (Fraction) obj;
            if (fraction.getNumerator() == this.getNumerator() &&
                    fraction.getDenominator() == this.getDenominator()){
                return true;
            } else {
                return false;
            }
        }
        return super.equals(obj);
    }

    public final int compare(Fraction fraction){
        int product1 = fraction.getDenominator() * this.getNumerator();
        int product2 = this.getDenominator() * fraction.getDenominator();
        if (product1>product2){
            return 1;
        } else if (product1<product2){
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public final int compareTo(@NonNull Fraction o) {
        if (this.compare(o)>0){
            return -1;
        } else if (this.compare(o)<0){
            return 1;
        } else {
            return 0;
        }
    }
}
