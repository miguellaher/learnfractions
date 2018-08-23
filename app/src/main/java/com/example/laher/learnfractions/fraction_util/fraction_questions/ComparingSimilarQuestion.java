package com.example.laher.learnfractions.fraction_util.fraction_questions;

import android.util.Log;

import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;
import com.example.laher.learnfractions.util.AppConstants;

public class ComparingSimilarQuestion extends FractionQuestionClass {
    private Fraction fraction1;
    private Fraction fraction2;

    public Fraction getFraction1() {
        return fraction1;
    }

    public Fraction getFraction2() {
        return fraction2;
    }

    public ComparingSimilarQuestion() {
        generateFractions();
    }

    private void generateFractions(){
        this.fraction1 = new Fraction();
        this.fraction2 = new Fraction();
        while (this.fraction1.getNumerator()!=this.fraction2.getNumerator() &&
                this.fraction1.getDenominator()!=this.fraction2.getDenominator()){
            this.fraction1 = new Fraction();
            this.fraction2 = new Fraction();
        }
    }

    @Override
    public Fraction getFractionAnswer() {
        if (this.fraction1.compare(this.fraction2)>0){
            return this.fraction1;
        } else if (this.fraction1.compare(this.fraction2)<0){
            return this.fraction2;
        } else {
            return AppConstants.EQUAL_FRACTIONS;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComparingSimilarQuestion){
            ComparingSimilarQuestion comparingSimilarQuestion = (ComparingSimilarQuestion) obj;
            return this.fraction1.equals(comparingSimilarQuestion.fraction1) &&
                    this.fraction2.equals(comparingSimilarQuestion.fraction2);
        }
        return super.equals(obj);
    }
}
