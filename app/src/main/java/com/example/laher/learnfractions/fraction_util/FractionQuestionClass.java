package com.example.laher.learnfractions.fraction_util;

import com.example.laher.learnfractions.classes.Range;

public class FractionQuestionClass {
    private int numeratorAnswer;
    private int denominatorAnswer;
    private double numberAnswer;
    private Fraction fractionAnswer;
    private String stringEquation;
    private Range range;

    public void setRange(Range range) {
        this.range = range;
    }

    public String getStringEquation() {
        return stringEquation;
    }

    public void setStringEquation(String stringEquation) {
        this.stringEquation = stringEquation;
    }

    public Fraction getFractionAnswer() {
        return fractionAnswer;
    }

    public void setFractionAnswer(Fraction fractionAnswer) {
        this.fractionAnswer = fractionAnswer;
    }

    public double getNumberAnswer() {
        return numberAnswer;
    }

    public void setNumberAnswer(double numberAnswer) {
        this.numberAnswer = numberAnswer;
    }

    public int getNumeratorAnswer() {
        return numeratorAnswer;
    }

    public void setNumeratorAnswer(int numeratorAnswer) {
        this.numeratorAnswer = numeratorAnswer;
        this.fractionAnswer = new Fraction(this.numeratorAnswer, getDenominatorAnswer());
    }

    public int getDenominatorAnswer() {
        return denominatorAnswer;
    }

    public void setDenominatorAnswer(int denominatorAnswer) {
        this.denominatorAnswer = denominatorAnswer;
        this.fractionAnswer = new Fraction(getNumeratorAnswer(), this.denominatorAnswer);
    }

    public FractionQuestionClass() { // TEMPORARY!
    }

    public FractionQuestionClass(Range range) {
        this.range = range;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FractionQuestionClass){
            FractionQuestionClass fractionQuestionClass = (FractionQuestionClass) obj;
            String thisEquation1 = this.getStringEquation();
            String thisEquation2 = fractionQuestionClass.getStringEquation();
            return thisEquation1.equals(thisEquation2);
        }
        return super.equals(obj);
    }
}
