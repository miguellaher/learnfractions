package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;
import com.example.laher.learnfractions.util.AppConstants;

public class ComparingDissimilarQuestion extends FractionQuestionClass {
    private Fraction fraction1;
    private Fraction fraction2;
    private int product1;
    private int product2;

    public Fraction getFraction1() {
        return fraction1;
    }

    public void setFraction1(Fraction fraction1) {
        this.fraction1 = fraction1;
    }

    public Fraction getFraction2() {
        return fraction2;
    }

    public void setFraction2(Fraction fraction2) {
        this.fraction2 = fraction2;
    }

    public ComparingDissimilarQuestion() {
        Range range = new Range();
        generateFractions(range);
    }

    public ComparingDissimilarQuestion(Range range) {
        super(range);
        generateFractions(range);
    }

    public void generateFractions(Range range){
        setFraction1(new Fraction(range));
        setFraction2(new Fraction(range));
        while (getFraction1().getNumerator()==getFraction2().getNumerator() ||
                getFraction1().getDenominator()==getFraction2().getDenominator()){
            setFraction1(new Fraction(range));
            setFraction2(new Fraction(range));
        }
        int numerator1 = getFraction1().getNumerator();
        int numerator2 = getFraction2().getNumerator();
        int denominator1 = getFraction1().getDenominator();
        int denominator2 = getFraction2().getDenominator();
        this.product1 = denominator2 * numerator1;
        this.product2 = denominator1 * numerator2;
    }

    @Override
    public Fraction getFractionAnswer() {
        if (this.product1>this.product2){
            return fraction1;
        } else if (this.product1<this.product2){
            return fraction2;
        } else {
            return AppConstants.EQUAL_FRACTIONS;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComparingDissimilarQuestion){
            ComparingDissimilarQuestion comparingDissimilarQuestion = (ComparingDissimilarQuestion) obj;
            return comparingDissimilarQuestion.getFraction1().equals(this.getFraction1()) &&
                    comparingDissimilarQuestion.getFraction2().equals(this.getFraction2());
        }
        return super.equals(obj);
    }
}
