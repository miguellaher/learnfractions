package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;
import com.example.laher.learnfractions.util.FractionUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class SubtractingDissimilarFractionsQuestion extends FractionQuestionClass {
    private Fraction fraction1;
    private Fraction fraction2;
    private int equationLcd;

    public Fraction getFraction1() {
        return fraction1;
    }

    public Fraction getFraction2() {
        return fraction2;
    }

    public int getEquationLcd() {
        return equationLcd;
    }

    public SubtractingDissimilarFractionsQuestion() {
        generateFractions();
    }

    public SubtractingDissimilarFractionsQuestion(Range range) {
        super(range);
        generateFractions(range);
    }

    private void generateFractions(){
        fraction1 = new Fraction();
        fraction2 = new Fraction();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        ArrayList<Fraction> fractions = new ArrayList<>();
        fractions.add(fraction1);
        fractions.add(fraction2);
        int lcd = FractionUtil.getLcd(fractions);
        int quotient1 = lcd / denominator1;
        int quotient2 = lcd / denominator2;
        while (denominator1==denominator2 || (quotient1>10||quotient2>10) || fraction1.compare(fraction2)<1){
            //while fraction1 is less than fraction2
            fraction1 = new Fraction();
            fraction2 = new Fraction();
            denominator1 = fraction1.getDenominator();
            denominator2 = fraction2.getDenominator();
            fractions = new ArrayList<>();
            fractions.add(fraction1);
            fractions.add(fraction2);
            lcd = FractionUtil.getLcd(fractions);
            quotient1 = lcd / denominator1;
            quotient2 = lcd / denominator2;
        }
        setFractionAnswer();
    }

    public static void main (String[] args){
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.print("Enter range: ");
        int n = reader.nextInt();
        Range range = new Range(1,n);
        for (int i = 0; i < 100; i++) {
            SubtractingDissimilarFractionsQuestion question = new SubtractingDissimilarFractionsQuestion(range);
            Fraction fraction1 = question.getFraction1();
            Fraction fraction2 = question.getFraction2();
            Fraction fractionAnswer = question.getFractionAnswer();
            if (fraction1.equals(new Fraction(2,1))) {
                System.out.println("Fraction 1: " + fraction1.getNumerator() + "/" + fraction1.getDenominator());
                System.out.println("Fraction 2: " + fraction2.getNumerator() + "/" + fraction2.getDenominator());
                System.out.println("Fraction Answer: " + fractionAnswer.getNumerator() + "/" + fractionAnswer.getDenominator());
                System.out.println("<----------------------------------------------------->");
            }
        }
    }

    private void generateFractions(Range range){
        fraction1 = new Fraction(range);
        fraction2 = new Fraction(range);
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        ArrayList<Fraction> fractions = new ArrayList<>();
        fractions.add(fraction1);
        fractions.add(fraction2);
        int lcd = FractionUtil.getLcd(fractions);
        int quotient1 = lcd / denominator1;
        int quotient2 = lcd / denominator2;
        int quotientLimit = range.getMaximum();
        while (denominator1==denominator2 || (quotient1>quotientLimit||quotient2>quotientLimit) || fraction1.compare(fraction2)<1){
            //while fraction1 is less than fraction2
            if (fraction2.compare(fraction1)>0){
                Fraction fractionOne = fraction1;
                fraction1 = fraction2;
                fraction2 = fractionOne;
            } else {
                fraction1 = new Fraction(range);
                fraction2 = new Fraction(range);
            }
            denominator1 = fraction1.getDenominator();
            denominator2 = fraction2.getDenominator();
            fractions = new ArrayList<>();
            fractions.add(fraction1);
            fractions.add(fraction2);
            lcd = FractionUtil.getLcd(fractions);
            quotient1 = lcd / denominator1;
            quotient2 = lcd / denominator2;
        }
        setFractionAnswer();
    }

    private void setFractionAnswer(){ //exclusive method for generateFractions()
        ArrayList<Fraction> fractions = new ArrayList<>();
        fractions.add(fraction1);
        fractions.add(fraction2);
        int numerator1 = fraction1.getNumerator();
        int numerator2 = fraction2.getNumerator();
        int denominator1 = fraction1.getDenominator();
        int denominator2 = fraction2.getDenominator();
        int lcd = FractionUtil.getLcd(fractions);
        equationLcd = lcd;
        int quotient1 = lcd / denominator1;
        int newNumerator1 = quotient1 * numerator1;
        int quotient2 = lcd / denominator2;
        int newNumerator2 = quotient2 * numerator2;
        int numeratorAnswer = newNumerator1 - newNumerator2;
        Fraction fractionAnswer = new Fraction(numeratorAnswer, lcd);
        setFractionAnswer(fractionAnswer);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SubtractingDissimilarFractionsQuestion){
            SubtractingDissimilarFractionsQuestion fractionsQuestion = (SubtractingDissimilarFractionsQuestion) obj;
            Fraction thisFraction1 = this.getFraction1();
            Fraction thisFraction2 = this.getFraction2();
            Fraction objFraction1 = fractionsQuestion.getFraction1();
            Fraction objFraction2 = fractionsQuestion.getFraction2();
            return thisFraction1.equals(objFraction1) && thisFraction2.equals(objFraction2);
        }
        return super.equals(obj);
    }
}
