package com.example.laher.learnfractions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class FractionQuestion {
    Fraction fractionOne, fractionTwo, fractionThree, fractionAnswer;
    String context;
    String answer;
    ArrayList<Fraction> fractions;
    public final static String COMPARING_FRACTION = "COMPARING_FRACTION";
    public final static String COMPARING_SIMILAR = "COMPARING_SIMILAR";
    public final static String COMPARING_DISSIMILAR = "COMPARING_DISSIMILAR";
    public final static String ORDERING_SIMILAR = "ORDERING_SIMILAR";
    public final static String ORDERING_DISSIMILAR = "ORDERING_DISSIMILAR";
    public final static String ADDING_FRACTION = "ADDING_FRACTION";
    public final static String ADDING_SIMILAR = "ADDING_SIMILAR";
    public final static String ADDING_DISSIMILAR = "ADDING_DISSIMILAR";
    public final static String SUBTRACTING_SIMILAR = "SUBTRACTING_SIMILAR";
    public final static String SUBTRACTING_DISSIMILAR = "SUBTRACTING_DISSIMILAR";
    public final static String MULTIPLYING_FRACTIONS = "MULTIPLYING_FRACTIONS";
    public final static String DIVIDING_FRACTIONS = "DIVIDING_FRACTIONS";
    public final static String ANSWER_GREATER = ">";
    public final static String ANSWER_EQUAL = "=";
    public final static String ANSWER_LESS = "<";

    public Fraction getFractionOne() {
        return fractionOne;
    }
    public Fraction getFractionTwo() {
        return fractionTwo;
    }
    public Fraction getFractionThree() {
        return fractionThree;
    }
    public Fraction getFractionAnswer() {
        return fractionAnswer;
    }
    public ArrayList<Fraction> getFractions() {
        return fractions;
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
    public void setFractionThree(Fraction fractionThree) {
        this.fractionThree = fractionThree;
    }
    public void setFractionAnswer(Fraction fractionAnswer) {
        this.fractionAnswer = fractionAnswer;
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
            while (!(fractionOne.getDenominator()==fractionTwo.getDenominator()
                    &&fractionTwo.getDenominator()==fractionThree.getDenominator()&&
            fractionOne.getNumerator()!=fractionTwo.getNumerator()&&
            fractionTwo.getNumerator()!=fractionThree.getNumerator()&&
            fractionOne.getNumerator()!=fractionThree.getNumerator()) &&
                    !(fractionOne.getNumerator()==fractionTwo.getNumerator()&&
                            fractionTwo.getNumerator()==fractionThree.getNumerator()&&
                            fractionOne.getDenominator()!=fractionTwo.getDenominator()&&
                            fractionTwo.getDenominator()!=fractionThree.getDenominator()&&
                            fractionOne.getDenominator()!=fractionThree.getDenominator())){
                fractionTwo = new Fraction();
                fractionThree = new Fraction();
            }
            addAllToFractionList();
            Collections.sort(fractions);
        }
        if (context == ORDERING_DISSIMILAR){
            fractionThree = new Fraction();
            while((fractionOne.getNumerator()==fractionTwo.getNumerator()||
                    fractionTwo.getNumerator()==fractionThree.getNumerator()||
                    fractionOne.getNumerator()==fractionThree.getNumerator())||
                    fractionOne.getDenominator()==fractionTwo.getDenominator()||
                    fractionTwo.getDenominator()==fractionThree.getDenominator()||
                    fractionOne.getDenominator()==fractionThree.getDenominator()){
                fractionTwo = new Fraction();
                fractionThree = new Fraction();
            }
            addAllToFractionList();
            Collections.sort(fractions);
            int[] denominators = {fractionOne.getDenominator(), fractionTwo.getDenominator(), fractionThree.getDenominator()};
            answer = String.valueOf(Question.getLCM(denominators));
        }
        if (context == ADDING_FRACTION){
            int [] denominators = {fractionOne.getDenominator(), fractionTwo.getDenominator()};
            int lcd = (int) Question.getLCM(denominators);
            fractionOne.lcdConvert(lcd);
            fractionTwo.lcdConvert(lcd);
            int num = fractionOne.getNumerator() + fractionTwo.getNumerator();
            fractionThree.setNumerator(num);
            fractionThree.setDenominator(lcd);
        }
        if (context == ADDING_SIMILAR) {
            while (fractionOne.getDenominator() != fractionTwo.getDenominator()){
                fractionTwo = new Fraction();
            }
            int numerator = fractionOne.getNumerator()+fractionTwo.getNumerator();
            fractionThree = new Fraction(numerator,fractionOne.getDenominator());
        }
        if (context == ADDING_DISSIMILAR) {
            while (fractionOne.getDenominator() == fractionTwo.getDenominator()){
                fractionTwo = new Fraction();
            }
            int [] denominators = {fractionOne.getDenominator(), fractionTwo.getDenominator()};
            int lcd = (int) Question.getLCM(denominators);
            int sumNum = ((lcd/fractionOne.getDenominator())*fractionOne.getNumerator()) +
                    ((lcd/fractionTwo.getDenominator())*fractionTwo.getNumerator());
            fractionAnswer = new Fraction(sumNum,lcd);
        }
        if (context == SUBTRACTING_SIMILAR) {
            while (fractionOne.getDenominator() != fractionTwo.getDenominator() ||
                    fractionOne.getNumerator()<=fractionTwo.getNumerator()){
                fractionOne = new Fraction();
                fractionTwo = new Fraction();
            }
            int numerator = fractionOne.getNumerator()-fractionTwo.getNumerator();
            fractionAnswer = new Fraction(numerator,fractionOne.getDenominator());
        }
        if (context == SUBTRACTING_DISSIMILAR) {
            while (fractionOne.getDenominator() == fractionTwo.getDenominator() ||
                    fractionOne.getValue() <= fractionTwo.getValue()){
                fractionOne = new Fraction();
                fractionTwo = new Fraction();
            }
            int [] denominators = {fractionOne.getDenominator(), fractionTwo.getDenominator()};
            int lcd = (int) Question.getLCM(denominators);
            int difNum = ((lcd/fractionOne.getDenominator())*fractionOne.getNumerator()) -
                    ((lcd/fractionTwo.getDenominator())*fractionTwo.getNumerator());
            fractionAnswer = new Fraction(difNum,lcd);
        }
        if (context == MULTIPLYING_FRACTIONS){
            int prodNum = fractionOne.getNumerator() * fractionTwo.getNumerator();
            int prodDenom = fractionOne.getDenominator() * fractionTwo.getDenominator();
            fractionAnswer = new Fraction(prodNum,prodDenom);
        }
        if (context == DIVIDING_FRACTIONS){
            int prodNum = fractionOne.getNumerator() * fractionTwo.getDenominator();
            int prodDenom = fractionOne.getDenominator() * fractionTwo.getNumerator();
            fractionAnswer = new Fraction(prodNum,prodDenom);
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
    public void addAllToFractionList(){
        fractions = new ArrayList<>();
        fractions.add(fractionOne);
        fractions.add(fractionTwo);
        fractions.add(fractionThree);
    }


}
