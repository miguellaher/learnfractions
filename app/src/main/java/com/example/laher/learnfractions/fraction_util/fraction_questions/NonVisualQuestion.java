package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;

public class NonVisualQuestion extends FractionQuestionClass {
    private int numerator;
    private int denominator;

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
        setNumeratorAnswer(this.numerator);
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
        setDenominatorAnswer(this.denominator);
    }

    public NonVisualQuestion() {
        randomizeFraction();
    }

    public void randomizeFraction(){
        int numerator = (int) (Math.random() * 9 + 1);
        int denominator = (int) (Math.random() * 9 + 1);
        while (numerator==denominator){
            numerator = (int) (Math.random() * 9 + 1);
            denominator = (int) (Math.random() * 9 + 1);
        }
        setNumerator(numerator);
        setDenominator(denominator);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NonVisualQuestion) {
            NonVisualQuestion nvq = (NonVisualQuestion) obj;
            if (nvq.getNumerator()==this.getNumerator()
                    && nvq.getDenominator()==this.getDenominator()){
                return true;
            } else {
                return false;
            }
        }
        return super.equals(obj);
    }
}
