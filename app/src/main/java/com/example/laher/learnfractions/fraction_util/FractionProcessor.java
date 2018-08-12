package com.example.laher.learnfractions.fraction_util;

public class FractionProcessor {
    public FractionClass getGreaterFraction(FractionClass fraction1, FractionClass fraction2){
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
