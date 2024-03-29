package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;
import com.example.laher.learnfractions.fraction_util.MixedFraction;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.FractionUtil;

import java.util.ArrayList;

public class AddingMixedFractionsQuestion extends FractionQuestionClass {
    private Fraction fraction;
    private MixedFraction mixedFraction1;
    private MixedFraction mixedFraction2;
    private final String TAG;
    public static final String ONE_MIXED = "0";
    public static final String TWO_MIXED = "1";


    public Fraction getFraction() {
        return fraction;
    }

    public MixedFraction getMixedFraction1() {
        return mixedFraction1;
    }

    public MixedFraction getMixedFraction2() {
        return mixedFraction2;
    }

    public String getTAG() {
        return TAG;
    }

    public AddingMixedFractionsQuestion(String tag) {
        Range range = new Range();
        switch (tag) {
            case ONE_MIXED:
                TAG = ONE_MIXED;
                generateOneMixedFractionEquation(range);
                break;
            case TWO_MIXED:
                TAG = TWO_MIXED;
                generateTwoMixedFractionsEquation(range);
                break;
            default:
                TAG = AppConstants.ERROR;
                break;
        }
    }

    public AddingMixedFractionsQuestion() {
        int randomNumber = (int) (Math.random() * 2 + 1);
        Range range = new Range();
        if (randomNumber==1){
            TAG = ONE_MIXED;
            generateOneMixedFractionEquation(range);
        } else if (randomNumber==2){
            TAG = TWO_MIXED;
            generateTwoMixedFractionsEquation(range);
        } else {
            TAG = AppConstants.ERROR;
        }
    }

    public AddingMixedFractionsQuestion(Range range) {
        super(range);
        int randomNumber = (int) (Math.random() * 2 + 1);
        if (randomNumber==1){
            TAG = ONE_MIXED;
            generateOneMixedFractionEquation(range);
        } else if (randomNumber==2){
            TAG = TWO_MIXED;
            generateTwoMixedFractionsEquation(range);
        } else {
            TAG = AppConstants.ERROR;
        }
    }

    private void generateOneMixedFractionEquation(Range range){
        fraction = new Fraction(range);
        mixedFraction1 = new MixedFraction(range);
        Fraction improperFraction1 = mixedFraction1.getImproperFraction();
        int numeratorImproper = improperFraction1.getNumerator();
        int numerator = fraction.getNumerator();
        int denominator = fraction.getDenominator();
        int denominatorImproper = improperFraction1.getDenominator();
        ArrayList<Fraction> fractions = new ArrayList<>();
        fractions.add(fraction);
        fractions.add(improperFraction1);
        int lcd = FractionUtil.getLcd(fractions);
        int quotient1 = lcd / denominator;
        int quotient2 = lcd / denominatorImproper;
        int newNumerator = quotient1 * numerator;
        int newNumeratorImproper = quotient2 * numeratorImproper;
        int numeratorAnswer = newNumerator + newNumeratorImproper;
        Fraction fractionAnswer = new Fraction(numeratorAnswer, lcd);
        setFractionAnswer(fractionAnswer);
        int wholeNumberMixed = mixedFraction1.getWholeNumber();
        int numeratorMixed = mixedFraction1.getNumerator();
        int denominatorMixed = mixedFraction1.getDenominator();
        String equation = numerator + "/" + denominator + "+" + wholeNumberMixed + "," + numeratorMixed + "/" + denominatorMixed;
        setStringEquation(equation);
    }

    private void generateTwoMixedFractionsEquation(Range range){
        mixedFraction1 = new MixedFraction(range);
        mixedFraction2 = new MixedFraction(range);
        Fraction improperFraction1 = mixedFraction1.getImproperFraction();
        Fraction improperFraction2 = mixedFraction2.getImproperFraction();
        int numeratorImproper1 = improperFraction1.getNumerator();
        int numeratorImproper2 = improperFraction2.getNumerator();
        int denominatorImproper1 = improperFraction1.getDenominator();
        int denominatorImproper2 = improperFraction2.getDenominator();
        ArrayList<Fraction> fractions = new ArrayList<>();
        fractions.add(improperFraction1);
        fractions.add(improperFraction2);
        int lcd = FractionUtil.getLcd(fractions);
        int quotient1 = lcd / denominatorImproper1;
        int quotient2 = lcd / denominatorImproper2;
        int newNumeratorImproper1 = quotient1 * numeratorImproper1;
        int newNumeratorImproper2 = quotient2 * numeratorImproper2;
        int numeratorAnswer = newNumeratorImproper1 + newNumeratorImproper2;
        Fraction fractionAnswer = new Fraction(numeratorAnswer, lcd);
        setFractionAnswer(fractionAnswer);
        int wholeNumberMixed1 = mixedFraction1.getWholeNumber();
        int numeratorMixed1 = mixedFraction1.getNumerator();
        int denominatorMixed1 = mixedFraction1.getDenominator();
        int wholeNumberMixed2 = mixedFraction2.getWholeNumber();
        int numeratorMixed2 = mixedFraction2.getNumerator();
        int denominatorMixed2 = mixedFraction2.getDenominator();
        String equation = wholeNumberMixed1 + "," + numeratorMixed1 + "/" + denominatorMixed1;
        equation = equation + "+" + wholeNumberMixed2 + "," + numeratorMixed2 + "/" + denominatorMixed2;
        setStringEquation(equation);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AddingMixedFractionsQuestion){
            AddingMixedFractionsQuestion fractionsQuestion = (AddingMixedFractionsQuestion) obj;
            String thisTag = this.getTAG();
            String objTag = fractionsQuestion.getTAG();
            if (thisTag.equals(ONE_MIXED) && objTag.equals(ONE_MIXED)){
                Fraction thisFraction = this.getFraction();
                Fraction objFraction = fractionsQuestion.getFraction();
                MixedFraction thisMixed = this.getMixedFraction1();
                MixedFraction objMixed = fractionsQuestion.getMixedFraction1();
                return thisFraction.equals(objFraction) && thisMixed.equals(objMixed);
            } else if (thisTag.equals(TWO_MIXED) && objTag.equals(TWO_MIXED)){
                MixedFraction thisMixed1 = this.getMixedFraction1();
                MixedFraction thisMixed2 = this.getMixedFraction2();
                MixedFraction objMixed1 = fractionsQuestion.getMixedFraction1();
                MixedFraction objMixed2 = fractionsQuestion.getMixedFraction2();
                return thisMixed1.equals(objMixed1) && thisMixed2.equals(objMixed2);
            }
        }
        return super.equals(obj);
    }
}
