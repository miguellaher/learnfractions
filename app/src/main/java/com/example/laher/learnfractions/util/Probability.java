package com.example.laher.learnfractions.util;

import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.MixedFraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Probability {
    private int outComes; // number of outcomes
    private String equation;
    private int possibilities; // might have used the idea of possibilities wrong
    private Range range;

    public static final String P_RAISED_TO_3 = "0";
    public static final String TWO_DISSIMILAR_FRACTIONS = "1";
    public static final String SUBTRACTING_2SIMILAR_F = "2";
    public static final String SUBTRACTING_2DISSIMILAR_F = "3";
    public static final String P_RAISED_TO_4 = "4";
    public static final String SOLVING_MIXED1 = "5";
    public static final String SOLVING_MIXED2 = "6";
    public static final String SUMMATION_NOTATION_1 = "7"; // starts with 1  OR  i = 1
    public static final String P_RAISED_TO_2 = "8";
    public static final String COMPARING_FRACTIONS = "9";
    public static final String ORDERING_NUMBERS = "10";
    public static final String ORDERING_SIMILAR = "11";
    public static final String GETTING_LCM = "12";
    public static final String ORDERING_DISSIMILAR = "13";
    public static final String TWO_DISSIMILAR_NUMBERS = "14";
    public static final String CLASSIFYING_FRACTIONS = "15";
    public static final String CONVERTING_FRACTIONS1 = "16";

    public static void main(String[] args){
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter equation number: ");
        String s = reader.next();
        System.out.println("Enter range: ");
        int n = reader.nextInt();
        Range range = new Range(2,n);
        Probability probability = new Probability(s,range);
        System.out.println("Outcomes: " + probability.getOutComes());
    }

    public String getEquation() {
        return equation;
    }

    public int getOutComes() {
        return outComes;
    }

    public Range getRange() {
        return range;
    }

    public Probability(String equation, Range range) {
        this.equation = equation;
        this.range = range;
        this.possibilities = range.getRange();
        equate();
    }

    private void equate(){
        String equation = this.equation;
        int possibilities = this.possibilities;
        switch (equation) {
            case P_RAISED_TO_3:
                this.outComes = possibilities * possibilities * possibilities;
                break;

            case TWO_DISSIMILAR_FRACTIONS:
                this.outComes = (possibilities * (possibilities - 1)) * (possibilities * (possibilities - 1));
                break;

            case SUBTRACTING_2SIMILAR_F:
                int sum = 0;
                int intRange = possibilities;
                while (intRange > 1) {
                    intRange--;
                    sum = sum + intRange;
                }
                this.outComes = sum * possibilities;
                break;

            case SUBTRACTING_2DISSIMILAR_F:
                ArrayList<Fraction> fractions = new ArrayList<>();
                Range range = getRange();
                int minimum = range.getMinimum();
                int maximum = range.getMaximum();
                for (int i = maximum; i > (minimum-1); i--){
                    for (int i2 = maximum; i2 > (minimum-1); i2--){
                        Fraction fraction = new Fraction(i,i2);
                        fractions.add(fraction);
                    }
                }
                Collections.sort(fractions);
                sum = 0;
                for (int i = 0; i < fractions.size(); i++){
                    Fraction fraction = fractions.get(i);
                    for (Fraction fraction1 : fractions){
                        if (fraction.compare(fraction1)>0){
                            int denominator = fraction.getDenominator();
                            int denominator1 = fraction1.getDenominator();
                            if (denominator!=denominator1){
                                sum++;
                            }
                        }
                    }
                }
                this.outComes = sum;
                break;

            case P_RAISED_TO_4:
                this.outComes = possibilities * possibilities * possibilities * possibilities;
                break;

            case SOLVING_MIXED1:
                ArrayList<MixedFraction> mixedFractions = new ArrayList<>();
                range = getRange();
                minimum = range.getMinimum();
                maximum = range.getMaximum();
                for (int i = maximum; i > (minimum-1); i--){
                    for (int i2 = maximum; i2 > (minimum-1); i2--){
                        for (int i3 = maximum; i3 > (minimum-1); i3--){
                            MixedFraction mixedFraction = new MixedFraction(i,i3,i2);
                            mixedFractions.add(mixedFraction);
                        }
                    }
                }
                Collections.sort(mixedFractions);
                sum = 0;
                for (int i = 0; i < mixedFractions.size(); i++){
                    Fraction mixedFraction = mixedFractions.get(i);
                    for (MixedFraction mixedFraction1 : mixedFractions){
                        if (mixedFraction.compare(mixedFraction1)>0){
                            sum++;
                        }
                    }
                }
                this.outComes = sum * 2; // for adding and subtracting
                break;

            case SOLVING_MIXED2:
                double a = Math.pow(possibilities,6);
                int intOutComes = (int) a;
                this.outComes = intOutComes * 2; // for dividing and multiplying
                break;

            case SUMMATION_NOTATION_1:
                sum = 0;
                for (int i = 1; i <= possibilities; i++){ // starts with 1  OR  i = 1
                    sum = sum + i;
                }
                this.outComes = sum;
                break;

            case P_RAISED_TO_2:
                a = Math.pow(possibilities,2);
                intOutComes = (int) a;
                this.outComes = intOutComes;
                break;

            case COMPARING_FRACTIONS:
                int dissimilar = (possibilities * (possibilities - 1)) * (possibilities * (possibilities - 1));
                int similar = possibilities * possibilities * possibilities;
                if (dissimilar<similar){
                    intOutComes = dissimilar;
                } else {
                    intOutComes = similar;
                }
                this.outComes = intOutComes * 2; // for dividing and multiplying
                break;

            case ORDERING_NUMBERS:
                sum = 0; // fixed range of 1 to 9
                for (int i = possibilities; i > 0; i--) {
                    for (int i2 = i-1; i2 > 0; i2--) {
                        for (int i3 = i2-1; i3 > 0; i3--) {
                            sum++;
                        }
                    }
                }
                this.outComes = sum;
                break;

            case ORDERING_SIMILAR:
                sum = 0;
                if (possibilities<3){
                    possibilities = 3;
                }
                for (int i = possibilities; i > 0; i--) {
                    for (int i2 = i-1; i2 > 0; i2--) {
                        for (int i3 = i2-1; i3 > 0; i3--) {
                            sum++;
                        }
                    }
                }
                this.outComes = sum * possibilities; // possibilities = "x"
                break;

            case GETTING_LCM:
                range = getRange();
                minimum = range.getMinimum();
                maximum = range.getMaximum();
                sum = 0;
                for (int i = maximum; i > (minimum-1); i--){
                    for (int i2 = i-1; i2 > 0; i2--){
                        for (int i3 = i2-1; i3 > 0; i3--){
                            ArrayList<Integer> numbers = new ArrayList<>();
                            numbers.add(i);
                            numbers.add(i2);
                            numbers.add(i3);
                            int lcm = FractionUtil.getLcm(numbers);
                            if (lcm<=(9*9)){
                                sum++;
                            }
                        }
                    }
                }
                this.outComes = sum;
                break;

            case ORDERING_DISSIMILAR:
                this.outComes = 38246; // == according to trial runs, 38246 is the number of outcomes if
                                        // the range is 1 to 9 for a dissimilar question.
                                        // an exact value is provided because of performance issues
                break;

            case TWO_DISSIMILAR_NUMBERS:
                this.outComes = possibilities * (possibilities-1);
                break;

            case CLASSIFYING_FRACTIONS:
                sum = 0;
                for (int i = 1; i < possibilities; i++){ // starts with 1  OR  i = 1
                    sum = sum + i;
                }
                this.outComes = sum * 3; // for classifying proper, improper and mixed
                break;

            case CONVERTING_FRACTIONS1:
                fractions = new ArrayList<>();
                range = getRange();
                maximum = range.getMaximum();
                minimum = range.getMinimum();
                for (int i = maximum; i >= minimum; i--){
                    for (int i2 = (i-1); i2 >= minimum; i2--){
                        Fraction fraction = new Fraction(i,i2);
                        fractions.add(fraction);
                    }
                }
                sum = 0;
                for (Fraction fraction : fractions){
                    int numerator = fraction.getNumerator();
                    int denominator = fraction.getDenominator();
                    if (numerator%denominator!=0){
                        sum++;
                    }
                }
                this.outComes = sum;
                break;
        }
    }
}
