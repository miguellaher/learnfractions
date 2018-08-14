package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;

public class MultiplyingFractionsQuestion extends FractionQuestionClass {
    private Fraction fraction1;
    private Fraction fraction2;

    public Fraction getFraction1() {
        return fraction1;
    }

    public Fraction getFraction2() {
        return fraction2;
    }

    public MultiplyingFractionsQuestion() {
        generateFractions();
    }

    public void generateFractions(){
        fraction1 = new Fraction();
        fraction2 = new Fraction();
        int numerator1 = fraction1.getNumerator();
        int numerator2 = fraction2.getNumerator();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        int numeratorAnswer = numerator1 * numerator2;
        int denominatorAnswer = denominator1 * denominator2;
        Fraction fractionAnswer = new Fraction(numeratorAnswer, denominatorAnswer);
        setFractionAnswer(fractionAnswer);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MultiplyingFractionsQuestion){
            MultiplyingFractionsQuestion fractionsQuestion = (MultiplyingFractionsQuestion) obj;
            Fraction thisFraction1 = this.getFraction1();
            Fraction thisFraction2 = this.getFraction2();
            Fraction objFraction1 = fractionsQuestion.getFraction1();
            Fraction objFraction2 = fractionsQuestion.getFraction2();
            return thisFraction1.equals(objFraction1) && thisFraction2.equals(objFraction2);
        }
        return super.equals(obj);
    }
}
