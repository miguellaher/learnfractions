package com.example.laher.learnfractions.fraction_util;

import com.example.laher.learnfractions.classes.Range;

import java.util.Comparator;
import java.util.Random;

public class MixedFraction extends Fraction implements Comparator<MixedFraction> {
    private int wholeNumber;

    public int getWholeNumber() {
        return wholeNumber;
    }

    public void setWholeNumber(int wholeNumber) {
        this.wholeNumber = wholeNumber;
    }

    public MixedFraction() {
        generateFraction();
        setModifier(MIXED);
    }

    public MixedFraction(Range range) {
        super(range);
    }

    public MixedFraction(int wholeNumber, int numerator, int denominator) {
        super(numerator, denominator);
        this.wholeNumber = wholeNumber;
        setModifier(MIXED);
    }

    public Fraction getImproperFraction(){
        int product = getDenominator() * getWholeNumber();
        int sum = product + getNumerator();
        return new Fraction(sum, getDenominator());
    }

    @Override
    public void generateFraction() {
        super.generateFraction();
        int wholeNumber = (int) (Math.random() * 9 + 1);
        setWholeNumber(wholeNumber);
    }

    @Override
    public void generateFraction(Range range) {
        super.generateFraction(range);
        int minimum = range.getMinimum();
        int maximum = range.getMaximum();
        Random random = new Random();
        int wholeNumber = random.nextInt(maximum + 1 - minimum) + minimum;
        setWholeNumber(wholeNumber);
    }

    @Override
    public int compare(MixedFraction o1, MixedFraction o2) {
        return o1.compare(o2);
    }

    public final int compare(MixedFraction mixedFraction){
        Fraction improperFraction1 = this.getImproperFraction();
        Fraction improperFraction2 = mixedFraction.getImproperFraction();
        return Integer.compare(improperFraction1.compare(improperFraction2), 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MixedFraction){
            MixedFraction mixedFraction1 = this;
            MixedFraction mixedFraction2 = (MixedFraction) obj;
            int wholeNumber1 = mixedFraction1.getWholeNumber();
            int wholeNumber2 = mixedFraction2.getWholeNumber();
            int numerator1 = mixedFraction1.getNumerator();
            int numerator2 = mixedFraction2.getNumerator();
            int denominator1 = mixedFraction1.getDenominator();
            int denominator2 = mixedFraction2.getDenominator();
            return wholeNumber1 == wholeNumber2 &&
                    numerator1 == numerator2 &&
                    denominator1 == denominator2;
        }
        return super.equals(obj);
    }
}
