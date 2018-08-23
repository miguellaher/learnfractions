package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;

public class AddingSimilarFractionsQuestion extends FractionQuestionClass {
    private Fraction fraction1;
    private Fraction fraction2;

    public Fraction getFraction1() {
        return fraction1;
    }

    public Fraction getFraction2() {
        return fraction2;
    }

    public AddingSimilarFractionsQuestion() {
        generateFractions();
    }

    public AddingSimilarFractionsQuestion(Range range) {
        super(range);
        generateFractions(range);
    }

    public void generateFractions(Range range){
        fraction1 = new Fraction(range);
        fraction2 = new Fraction(range);
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        while (denominator1!=denominator2){
            fraction1 = new Fraction(range);
            fraction2 = new Fraction(range);
            denominator1 = fraction1.getDenominator();
            denominator2 = fraction2.getDenominator();
        }
        int numerator1 = fraction1.getNumerator();
        int numerator2 = fraction2.getNumerator();
        int numeratorAnswer = numerator1 + numerator2;
        int denominatorAnswer = denominator1;// denominator1 and denominator2 are/should be always similar...
        Fraction fractionAnswer = new Fraction(numeratorAnswer,denominatorAnswer);//...due to the while block
        setFractionAnswer(fractionAnswer);
    }

    public void generateFractions(){
        fraction1 = new Fraction();
        fraction2 = new Fraction();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        while (denominator1!=denominator2){
            fraction1 = new Fraction();
            fraction2 = new Fraction();
            denominator1 = fraction1.getDenominator();
            denominator2 = fraction2.getDenominator();
        }
        int numerator1 = fraction1.getNumerator();
        int numerator2 = fraction2.getNumerator();
        int numeratorAnswer = numerator1 + numerator2;
        int denominatorAnswer = denominator1;// denominator1 and denominator2 are/should be always similar...
        Fraction fractionAnswer = new Fraction(numeratorAnswer,denominatorAnswer);//...due to the while block
        setFractionAnswer(fractionAnswer);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AddingSimilarFractionsQuestion){
            AddingSimilarFractionsQuestion fractionsQuestion = (AddingSimilarFractionsQuestion) obj;
            Fraction thisFraction1 = this.getFraction1();
            Fraction thisFraction2 = this.getFraction2();
            Fraction objFraction1 = fractionsQuestion.getFraction1();
            Fraction objFraction2 = fractionsQuestion.getFraction2();
            return thisFraction1.equals(objFraction1) && thisFraction2.equals(objFraction2);
        }
        return super.equals(obj);
    }
}
