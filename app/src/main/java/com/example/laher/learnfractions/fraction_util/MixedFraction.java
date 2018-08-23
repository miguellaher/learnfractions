package com.example.laher.learnfractions.fraction_util;

public class MixedFraction extends Fraction {
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

    public MixedFraction(int wholeNumber, int numerator, int denominator) {
        super(numerator, denominator);
        this.wholeNumber = wholeNumber;
        setModifier(MIXED);
    }

    public Fraction getImproperFraction(){
        int product = getDenominator() * getWholeNumber();
        int sum = product + getNumerator();
        Fraction improperFraction = new Fraction(sum, getDenominator());
        return improperFraction;
    }

    @Override
    public void generateFraction() {
        super.generateFraction();
        int wholeNumber = (int) (Math.random() * 9 + 1);
        setWholeNumber(wholeNumber);
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
