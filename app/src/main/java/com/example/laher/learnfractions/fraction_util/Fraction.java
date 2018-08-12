package com.example.laher.learnfractions.fraction_util;

import android.support.annotation.NonNull;

public class Fraction implements Comparable<Fraction>{
    private int numerator;
    private int denominator;

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

    public void generateFraction(){
        int numerator = (int) (Math.random() * 9 + 1);
        int denominator = (int) (Math.random() * 9 + 1);
        setNumerator(numerator);
        setDenominator(denominator);
    }

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
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

    public int compare(Fraction fraction){
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
    public int compareTo(@NonNull Fraction o) {
        if (this.compare(o)>0){
            return -1;
        } else if (this.compare(o)<0){
            return 1;
        } else {
            return 0;
        }
    }
}
