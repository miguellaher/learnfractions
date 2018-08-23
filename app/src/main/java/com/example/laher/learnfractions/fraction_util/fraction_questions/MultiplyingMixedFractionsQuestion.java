package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;
import com.example.laher.learnfractions.fraction_util.MixedFraction;
import com.example.laher.learnfractions.util.AppConstants;

public class MultiplyingMixedFractionsQuestion extends FractionQuestionClass {
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

    public MultiplyingMixedFractionsQuestion(String tag) {
        if (tag.equals(ONE_MIXED)) {
            TAG = ONE_MIXED;
            generateOneMixedFractionEquation();
        } else if (tag.equals(TWO_MIXED)) {
            TAG = TWO_MIXED;
            generateTwoMixedFractionsEquation();
        } else {
            TAG = AppConstants.ERROR;
        }
    }

    public MultiplyingMixedFractionsQuestion() {
        int randomNumber = (int) (Math.random() * 2 + 1);
        if (randomNumber==1){
            TAG = ONE_MIXED;
            generateOneMixedFractionEquation();
        } else if (randomNumber==2){
            TAG = TWO_MIXED;
            generateTwoMixedFractionsEquation();
        } else {
            TAG = AppConstants.ERROR;
        }
    }

    private void generateOneMixedFractionEquation(){
        mixedFraction1 = new MixedFraction();
        fraction = new Fraction();
        Fraction improperFraction1 = mixedFraction1.getImproperFraction();
        int numeratorImproper = improperFraction1.getNumerator();
        while (numeratorImproper>10){
            mixedFraction1 = new MixedFraction();
            improperFraction1 = mixedFraction1.getImproperFraction();
            numeratorImproper = improperFraction1.getNumerator();
        }
        int numerator = fraction.getNumerator();
        int denominator = fraction.getDenominator();
        int denominatorImproper = improperFraction1.getDenominator();
        int numeratorAnswer = numerator * numeratorImproper;
        int denominatorAnswer = denominator * denominatorImproper;
        Fraction fractionAnswer = new Fraction(numeratorAnswer, denominatorAnswer);
        setFractionAnswer(fractionAnswer);
        int wholeNumberMixed = mixedFraction1.getWholeNumber();
        int numeratorMixed = mixedFraction1.getNumerator();
        int denominatorMixed = mixedFraction1.getDenominator();
        String equation = numerator + "/" + denominator + "x" + wholeNumberMixed + "," + numeratorMixed + "/" + denominatorMixed;
        setStringEquation(equation);
    }

    private void generateTwoMixedFractionsEquation(){
        mixedFraction1 = new MixedFraction();
        mixedFraction2 = new MixedFraction();
        Fraction improperFraction1 = mixedFraction1.getImproperFraction();
        Fraction improperFraction2 = mixedFraction2.getImproperFraction();
        int numeratorImproper1 = improperFraction1.getNumerator();
        int numeratorImproper2 = improperFraction2.getNumerator();
        while (numeratorImproper1>10||numeratorImproper2>10){
            mixedFraction1 = new MixedFraction();
            improperFraction1 = mixedFraction1.getImproperFraction();
            numeratorImproper1 = improperFraction1.getNumerator();
            mixedFraction2 = new MixedFraction();
            improperFraction2 = mixedFraction2.getImproperFraction();
            numeratorImproper2 = improperFraction2.getNumerator();
        }
        int denominatorImproper1 = improperFraction1.getDenominator();
        int denominatorImproper2 = improperFraction2.getDenominator();
        int numeratorAnswer = numeratorImproper1 * numeratorImproper1;
        int denominatorAnswer = denominatorImproper1 * denominatorImproper2;
        Fraction fractionAnswer = new Fraction(numeratorAnswer, denominatorAnswer);
        setFractionAnswer(fractionAnswer);
        int wholeNumberMixed1 = mixedFraction1.getWholeNumber();
        int numeratorMixed1 = mixedFraction1.getNumerator();
        int denominatorMixed1 = mixedFraction1.getDenominator();
        int wholeNumberMixed2 = mixedFraction2.getWholeNumber();
        int numeratorMixed2 = mixedFraction2.getNumerator();
        int denominatorMixed2 = mixedFraction2.getDenominator();
        String equation = wholeNumberMixed1 + "," + numeratorMixed1 + "/" + denominatorMixed1;
        equation = equation + "x" + wholeNumberMixed2 + "," + numeratorMixed2 + "/" + denominatorMixed2;
        setStringEquation(equation);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MultiplyingMixedFractionsQuestion){
            MultiplyingMixedFractionsQuestion fractionsQuestion = (MultiplyingMixedFractionsQuestion) obj;
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
