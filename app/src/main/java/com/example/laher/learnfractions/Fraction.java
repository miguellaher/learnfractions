package com.example.laher.learnfractions;

public class Fraction {
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
}
