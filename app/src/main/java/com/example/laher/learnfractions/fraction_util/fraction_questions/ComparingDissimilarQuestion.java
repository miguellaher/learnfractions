package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;
import com.example.laher.learnfractions.util.AppConstants;

public class ComparingDissimilarQuestion extends FractionQuestionClass {
    private int numerator1;
    private int numerator2;
    private int denominator1;
    private int denominator2;
    private int product1;
    private int product2;

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

    public int getDenominator1() {
        return denominator1;
    }

    public void setDenominator1(int denominator1) {
        this.denominator1 = denominator1;
    }

    public int getDenominator2() {
        return denominator2;
    }

    public void setDenominator2(int denominator2) {
        this.denominator2 = denominator2;
    }

    public ComparingDissimilarQuestion() {
        generateFractions();
    }

    public void generateFractions(){
        int numerator1 = (int) (Math.random() * 9 + 1);
        int numerator2 = (int) (Math.random() * 9 + 1);
        while (numerator1==numerator2){
            numerator1 = (int) (Math.random() * 9 + 1);
            numerator2 = (int) (Math.random() * 9 + 1);
        }
        int denominator1 = (int) (Math.random() * 9 + 1);
        int denominator2 = (int) (Math.random() * 9 + 1);
        while (denominator1==denominator2){
            denominator1 = (int) (Math.random() * 9 + 1);
            denominator2 = (int) (Math.random() * 9 + 1);
        }
        this.product1 = denominator2 * numerator1;
        this.product2 = denominator1 * numerator2;
        setNumerator1(numerator1);
        setNumerator2(numerator2);
        setDenominator1(denominator1);
        setDenominator2(denominator2);
    }

    @Override
    public int getNumeratorAnswer() {
        if (this.product1>this.product2){
            return this.numerator1;
        } else if (this.product1<this.product2){
            return this.numerator2;
        } else {
            return AppConstants.EQUAL;
        }
    }

    @Override
    public int getDenominatorAnswer() {
        if (this.product1>this.product2){
            return this.denominator1;
        } else if (this.product1<this.product2){
            return this.denominator2;
        } else {
            return AppConstants.EQUAL;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComparingDissimilarQuestion){
            ComparingDissimilarQuestion comparingDissimilarQuestion = (ComparingDissimilarQuestion) obj;
            if (comparingDissimilarQuestion.getNumerator1()==this.getNumerator1()&&
                    comparingDissimilarQuestion.getNumerator2()==this.getNumerator2()&&
                    comparingDissimilarQuestion.getDenominator1()==this.getDenominator1()&&
                    comparingDissimilarQuestion.getDenominator2()==this.getDenominator2()){
                return true;
            } else {
                return false;
            }
        }
        return super.equals(obj);
    }
}
