package com.example.laher.learnfractions;

public class FractionProcessor {
    public Fraction getGreaterFraction(Fraction fraction1, Fraction fraction2){
        if ((fraction2.getDenominator()*fraction1.getNumerator())
                >fraction1.getDenominator()*fraction2.getNumerator()){
            return fraction1;
        } else if ((fraction2.getDenominator()*fraction1.getNumerator())
                <fraction1.getDenominator()*fraction2.getNumerator()){
            return fraction2;
        }
        return fraction1;
    }
}
