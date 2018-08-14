package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;
import com.example.laher.learnfractions.util.FractionUtil;

import java.util.ArrayList;

public class AddingDissimilarFractionsQuestion extends FractionQuestionClass {
    private Fraction fraction1;
    private Fraction fraction2;
    private int equationLcd;

    public int getEquationLcd() {
        return equationLcd;
    }

    public Fraction getFraction1() {
        return fraction1;
    }

    public Fraction getFraction2() {
        return fraction2;
    }

    public AddingDissimilarFractionsQuestion() {
        generateFractions();
    }

    public void generateFractions(){
        fraction1 = new Fraction();
        fraction2 = new Fraction();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        ArrayList<Fraction> fractions = new ArrayList<>();
        fractions.add(fraction1);
        fractions.add(fraction2);
        int lcd = FractionUtil.getLcd(fractions);
        int quotient1 = lcd / denominator1;
        int quotient2 = lcd / denominator2;
        while (denominator1==denominator2 && (quotient1>10||quotient2>10)){
            fraction1 = new Fraction();
            fraction2 = new Fraction();
            denominator1 = fraction1.getDenominator();
            denominator2 = fraction2.getDenominator();
            fractions = new ArrayList<>();
            fractions.add(fraction1);
            fractions.add(fraction2);
            lcd = FractionUtil.getLcd(fractions);
            quotient1 = lcd / denominator1;
            quotient2 = lcd / denominator2;
        }
        setFractionAnswer();
    }
    private final void setFractionAnswer(){ //exclusive method for generateFractions()
        ArrayList<Fraction> fractions = new ArrayList<>();
        fractions.add(fraction1);
        fractions.add(fraction2);
        int numerator1 = fraction1.getNumerator();
        int numerator2 = fraction2.getNumerator();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        int lcd = FractionUtil.getLcd(fractions);
        equationLcd = lcd;
        int quotient1 = lcd / denominator1;
        int newNumerator1 = quotient1 * numerator1;
        int quotient2 = lcd / denominator2;
        int newNumerator2 = quotient2 * numerator2;
        int numeratorAnswer = newNumerator1 + newNumerator2;
        int denominatorAnswer = lcd;
        Fraction fractionAnswer = new Fraction(numeratorAnswer, denominatorAnswer);
        setFractionAnswer(fractionAnswer);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AddingDissimilarFractionsQuestion){
            AddingDissimilarFractionsQuestion fractionsQuestion = (AddingDissimilarFractionsQuestion) obj;
            Fraction thisFraction1 = this.getFraction1();
            Fraction thisFraction2 = this.getFraction2();
            Fraction objFraction1 = fractionsQuestion.getFraction1();
            Fraction objFraction2 = fractionsQuestion.getFraction2();
            return thisFraction1==objFraction1 && thisFraction2==objFraction2;
        }
        return super.equals(obj);
    }
}
