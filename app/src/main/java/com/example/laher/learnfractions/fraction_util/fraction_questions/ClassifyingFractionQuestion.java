package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;
import com.example.laher.learnfractions.fraction_util.MixedFraction;

public class ClassifyingFractionQuestion extends FractionQuestionClass{
    private Fraction fraction;

    public ClassifyingFractionQuestion() {
        generateRandomFraction();
    }

    public ClassifyingFractionQuestion(String modifier){
        generateFraction(modifier);
    }

    public Fraction getFraction() {
        return fraction;
    }

    private void generateRandomFraction(){
        int randomNumber = (int) (Math.random() * 3 + 1);
        if (randomNumber==1){
            fraction = new Fraction(Fraction.PROPER);
        } else if (randomNumber==2){
            fraction = new Fraction(Fraction.IMPROPER);
        } else if (randomNumber==3){
            fraction = new MixedFraction();
        }
    }

    private void generateFraction(String modifier){
        if (modifier.equals(Fraction.MIXED)){
            fraction = new MixedFraction();
        } else {
            fraction = new Fraction(modifier);
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClassifyingFractionQuestion){
            ClassifyingFractionQuestion classifyingFractionQuestion = (ClassifyingFractionQuestion) obj;
            if (this.fraction instanceof MixedFraction && classifyingFractionQuestion.fraction instanceof MixedFraction){
                MixedFraction mixedFraction1 = (MixedFraction) this.fraction;
                MixedFraction mixedFraction2 = (MixedFraction) classifyingFractionQuestion.fraction;
                return mixedFraction1.equals(mixedFraction2);
            } else {
                Fraction fraction1 = this.fraction;
                Fraction fraction2 = classifyingFractionQuestion.fraction;
                String modifier1 = fraction1.getModifier();
                String modifier2 = fraction2.getModifier();
                if (modifier1.equals(modifier2)) {
                    return fraction1.equals(fraction2);
                }
            }
            return false;
        }
        return super.equals(obj);
    }
}
