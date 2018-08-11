package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;

public class ComparingSimilarQuestion extends FractionQuestionClass {
    private int numerator1;
    private int numerator2;
    private int denominator;

    public int getNumerator1() {
        return numerator1;
    }

    public void setNumerator1(int numerator1) {
        this.numerator1 = numerator1;
    }

    public int getNumerator2() {
        return numerator2;
    }

    public void setNumerator2(int numerator2) {
        this.numerator2 = numerator2;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
        setDenominatorAnswer(this.denominator);
    }

    public ComparingSimilarQuestion() {
        generateFraction();
    }

    public void generateFraction(){
        int numerator1 = (int) (Math.random() * 9 + 1);
        int numerator2 = (int) (Math.random() * 9 + 1);
        int denominator = (int) (Math.random() * 9 + 1);
        setNumerator1(numerator1);
        setNumerator2(numerator2);
        setDenominator(denominator);
    }

    @Override
    public int getNumeratorAnswer() {
        if (this.numerator1>this.numerator2){
            return this.numerator1;
        } else if (this.numerator1<this.numerator2){
            return this.numerator2;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComparingSimilarQuestion){
            ComparingSimilarQuestion comparingSimilarQuestion = (ComparingSimilarQuestion) obj;
            if (comparingSimilarQuestion.getNumerator1()==this.getNumerator1() &&
                    comparingSimilarQuestion.getNumerator2()==this.getNumerator2() &&
                    comparingSimilarQuestion.getDenominator()==this.getDenominator()){
                return true;
            } else {
                return false;
            }
        }
        return super.equals(obj);
    }
}
