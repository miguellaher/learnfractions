package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;

public class FractionMeaningQuestion extends FractionQuestionClass {
    private int numerator;
    private int denominator;

    public FractionMeaningQuestion() {
        super(1,9);
        randomizeFraction();
    }

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

    public FractionMeaningQuestion(int minimumNumber, int maximumNumber) {
        super(minimumNumber, maximumNumber);
        randomizeFraction();
    }

    private void randomizeFraction(){
        int minimumNumber = getMinimumNumber();
        int maximumNumber = getMaximumNumber();
        int numerator = (int) (Math.random() * maximumNumber + minimumNumber);
        int denominator = (int) (Math.random() * maximumNumber + minimumNumber);
        while (denominator<numerator){
            numerator = (int) (Math.random() * maximumNumber + minimumNumber);
            denominator = (int) (Math.random() * maximumNumber + minimumNumber);
        }
        setNumerator(numerator);
        setDenominator(denominator);
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
