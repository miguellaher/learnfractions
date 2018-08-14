package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;
import com.example.laher.learnfractions.fraction_util.MixedFraction;

public class ConvertingFractionsQuestion extends FractionQuestionClass {
    private Fraction fraction;
    private MixedFraction mixedFraction;
    private String modifier;
    public static final String IMPROPER_TO_MIXED = "IMPROPER_TO_MIXED";
    public static final String MIXED_TO_IMPROPER = "MIXED_TO_IMPROPER";

    public MixedFraction getMixedFraction() {
        return mixedFraction;
    }

    public String getModifier() {
        return modifier;
    }

    public Fraction getFraction() {
        return fraction;
    }

    public ConvertingFractionsQuestion(String modifier) {
        if (modifier.equals(IMPROPER_TO_MIXED)){
            this.modifier = IMPROPER_TO_MIXED;
            generateImproperFraction();
        } else if (modifier.equals(MIXED_TO_IMPROPER)){
            this.modifier = MIXED_TO_IMPROPER;
            generateMixedFraction();
        }
    }

    private void generateImproperFraction(){
        fraction = new Fraction(Fraction.IMPROPER);
        int numerator = fraction.getNumerator();
        int denominator = fraction.getDenominator();
        while (numerator%denominator==0){
            fraction = new Fraction(Fraction.IMPROPER);
            numerator = fraction.getNumerator();
            denominator = fraction.getDenominator();
        }
        //convert improper to mixed
        int quotient = numerator / denominator; // whole number of mixed fraction
        int product = quotient * denominator;
        int difference = numerator - product; // numerator of mixed fraction
        mixedFraction = new MixedFraction(quotient, difference, denominator);
    }

    private void generateMixedFraction(){
        mixedFraction = new MixedFraction();
        //convert mixed to improper
        int numerator = mixedFraction.getNumerator();
        int denominator = mixedFraction.getDenominator(); // denominator of converted improper fraction
        int wholeNumber = mixedFraction.getWholeNumber();
        int product = denominator * wholeNumber;
        int sum = product + numerator; // numerator of converted improper fraction
        fraction = new Fraction(sum,denominator);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConvertingFractionsQuestion){
            ConvertingFractionsQuestion convertingFractionsQuestion = (ConvertingFractionsQuestion) obj;
            String modifier1 = this.getModifier();
            String modifier2 = convertingFractionsQuestion.getModifier();
            if (modifier1.equals(IMPROPER_TO_MIXED) &&
                    modifier2.equals(IMPROPER_TO_MIXED)){
                Fraction fraction1 = this.getFraction();
                Fraction fraction2 = convertingFractionsQuestion.getFraction();
                return fraction1.equals(fraction2);
            } else if (modifier1.equals(MIXED_TO_IMPROPER) &&
                    modifier2.equals(MIXED_TO_IMPROPER)){
                MixedFraction mixedFraction1 = this.getMixedFraction();
                MixedFraction mixedFraction2 = convertingFractionsQuestion.getMixedFraction();
                return mixedFraction1.equals(mixedFraction2);
            } else {
                return false;
            }
        }
        return super.equals(obj);
    }
}
