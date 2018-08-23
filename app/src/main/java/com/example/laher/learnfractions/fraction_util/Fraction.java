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

    final void setModifier(String modifier){
        switch (modifier) {
            case PROPER:
                this.modifier = modifier;
                break;
            case IMPROPER:
                this.modifier = modifier;
                break;
            case MIXED:
                this.modifier = modifier;
                break;
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
        } else {
            generateFraction();
        }
    }

    public void generateFraction(){
        int numerator = (int) (Math.random() * 9 + 1);
        int denominator = (int) (Math.random() * 9 + 1);
        setNumerator(numerator);
        setDenominator(denominator);
        setModifier();
    }

    private void setModifier(){
        if (this.numerator!=0 && this.denominator!=0) {
            if (this.numerator > this.denominator) {
                this.modifier = IMPROPER;
            } else {
                this.modifier = PROPER;
            }
        } else {
            this.modifier = null;
        }
    }

    public double toDouble(){
        double numerator = (double) getNumerator();
        double denominator = (double) getDenominator();
        return numerator/denominator;
    }

    private void generateProperFraction(){
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
    private void generateImproperFraction(){
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
            return fraction.getNumerator() == this.getNumerator() &&
                    fraction.getDenominator() == this.getDenominator();
        }
        return super.equals(obj);
    }

    public final int compare(Fraction fraction){
        int product1 = fraction.getDenominator() * this.getNumerator();
        int product2 = this.getDenominator() * fraction.getNumerator();
        return Integer.compare(product1, product2);
    }

    public String toString(){
        int numerator = getNumerator();
        int denominator = getDenominator();
        String strNumerator = String.valueOf(numerator);
        String strDenominator = String.valueOf(denominator);

        return strNumerator + "/" + strDenominator;
    }

    @Override
    public final int compareTo(@NonNull Fraction o) {
        return Integer.compare(0, this.compare(o));
    }
}
