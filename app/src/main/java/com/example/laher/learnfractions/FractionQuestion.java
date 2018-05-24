package com.example.laher.learnfractions;

import java.util.ArrayList;
import java.util.Collections;

public class FractionQuestion {
    Fraction fractionOne, fractionTwo, fractionThree;
    String context;
    String answer;
    ArrayList<Fraction> fractions;
    public final static String COMPARING_FRACTION = "COMPARING_FRACTION";
    public final static String COMPARING_SIMILAR = "COMPARING_SIMILAR";
    public final static String COMPARING_DISSIMILAR = "COMPARING_DISSIMILAR";
    public final static String ORDERING_SIMILAR = "ORDERING_SIMILAR";
    public final static String ANSWER_GREATER = ">";
    public final static String ANSWER_EQUAL = "=";
    public final static String ANSWER_LESS = "<";

    public Fraction getFractionOne() {
        return fractionOne;
    }
    public Fraction getFractionTwo() {
        return fractionTwo;
    }
    public String getAnswer() {
        return answer;
    }
    public String getContext() {
        return context;
    }
    public void setFractionOne(Fraction fractionOne) {
        this.fractionOne = fractionOne;
    }
    public void setFractionTwo(Fraction fractionTwo) {
        this.fractionTwo = fractionTwo;
    }

    public FractionQuestion(){
        fractionOne = new Fraction();
        fractionTwo = new Fraction();
    }
    public FractionQuestion(String context){
        fractionOne = new Fraction();
        fractionTwo = new Fraction();
        this.context = context;
        if (context == COMPARING_FRACTION){
            compareTwoFractions();
        }
        if (context == COMPARING_SIMILAR){
            while (fractionOne.getDenominator()!=fractionTwo.getDenominator() &&
                    fractionOne.getNumerator()!=fractionTwo.getNumerator()){
                fractionTwo = new Fraction();
            }
            compareTwoFractions();
        }
        if (context == COMPARING_DISSIMILAR){
            while (fractionOne.getDenominator()==fractionTwo.getDenominator() ||
                    fractionOne.getNumerator()==fractionTwo.getNumerator()){
                fractionTwo = new Fraction();
            }
            compareTwoFractions();
        }
        if (context == ORDERING_SIMILAR){
            fractionThree = new Fraction();
            fractions.add(fractionOne);
            fractions.add(fractionTwo);
            fractions.add(fractionThree);
            Collections.sort(fractions);
        }
    }
    public FractionQuestion(Fraction fractionOne, Fraction fractionTwo, String Context){
        this.fractionOne = fractionOne;
        this.fractionTwo = fractionTwo;
        if (Context == COMPARING_FRACTION){
            compareTwoFractions();
        }
    }
    public void compareTwoFractions(){
        if ((fractionTwo.getDenominator()*fractionOne.getNumerator()) >
                (fractionOne.getDenominator()*fractionTwo.getNumerator()) ){
            answer = ANSWER_GREATER;
        } else if ((fractionTwo.getDenominator()*fractionOne.getNumerator()) <
                (fractionOne.getDenominator()*fractionTwo.getNumerator()) ) {
            answer = ANSWER_LESS;
        } else if ((fractionTwo.getDenominator()*fractionOne.getNumerator()) ==
                (fractionOne.getDenominator()*fractionTwo.getNumerator()) ) {
            answer = ANSWER_EQUAL;
        }
    }


}
