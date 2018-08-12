package com.example.laher.learnfractions.fraction_util;

public class FractionQuestionClass {
    private int numeratorAnswer;
    private int denominatorAnswer;
    private double numberAnswer;
    private Fraction fractionAnswer;

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
    }

    public int getDenominatorAnswer() {
        return denominatorAnswer;
    }

    public void setDenominatorAnswer(int denominatorAnswer) {
        this.denominatorAnswer = denominatorAnswer;
    }
}
