package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;

public class ComparingFractionsQuestion extends FractionQuestionClass{
    private Fraction fraction1;
    private Fraction fraction2;
    public static final String SIMILAR = "SIMILAR";
    public static final String DISSIMILAR = "DISSIMILAR";
    private String modifier;

    public String getModifier() {
        return modifier;
    }

    public Fraction getFraction1() {
        return fraction1;
    }

    public void setFraction1(Fraction fraction1) {
        this.fraction1 = fraction1;
    }

    public Fraction getFraction2() {
        return fraction2;
    }

    public void setFraction2(Fraction fraction2) {
        this.fraction2 = fraction2;
    }

    public ComparingFractionsQuestion(String tag) {
        if (tag.equals(SIMILAR)){
            generateSimilarFractions();
        }
        if (tag.equals(DISSIMILAR)){
            generateDissimilarFractions();
        }
    }

    private void generateSimilarFractions(){
        this.fraction1 = new Fraction();
        this.fraction2 = new Fraction();
        while (this.fraction1.getNumerator()!=this.fraction2.getNumerator() &&
                this.fraction1.getDenominator()!=this.fraction2.getDenominator()){
            this.fraction1 = new Fraction();
            this.fraction2 = new Fraction();
        }
        this.modifier = SIMILAR;
    }

    private void generateDissimilarFractions(){
        this.fraction1 = new Fraction();
        this.fraction2 = new Fraction();
        while (this.fraction1.getNumerator()==this.fraction2.getNumerator() ||
                this.fraction1.getDenominator()==this.fraction2.getDenominator()){
            this.fraction1 = new Fraction();
            this.fraction2 = new Fraction();
        }
        this.modifier = DISSIMILAR;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComparingFractionsQuestion){
            ComparingFractionsQuestion comparingFractionsQuestion = (ComparingFractionsQuestion) obj;
            return this.getFraction1().equals(comparingFractionsQuestion.getFraction1()) &&
                    this.getFraction2().equals(comparingFractionsQuestion.getFraction2());
        }
        return super.equals(obj);
    }
}
