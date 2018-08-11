package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;

public class FractionMeaningQuestion extends FractionQuestionClass {
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

    public FractionMeaningQuestion() {
        randomizeFraction();
    }
    private void randomizeFraction(){
        int num = (int) (Math.random() * 9 + 1);
        int denom = (int) (Math.random() * 9 + 1);
        while (denom<num){
            num = (int) (Math.random() * 9 + 1);
            denom = (int) (Math.random() * 9 + 1);
        }
        setNumerator(num);
        setDenominator(denom);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FractionMeaningQuestion) {
            FractionMeaningQuestion fmq = (FractionMeaningQuestion) obj;
            if (fmq.getNumerator() == this.getNumerator()){
                if (fmq.getDenominator() == this.getDenominator()){
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return super.equals(obj);
    }
}
