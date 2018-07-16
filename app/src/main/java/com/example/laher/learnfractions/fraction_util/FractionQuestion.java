package com.example.laher.learnfractions.fraction_util;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class FractionQuestion {
    private static final String TAG = "fraction_question";

    private Fraction fractionOne, fractionTwo, fractionThree, fractionAnswer;
    private String context;
    private String answer;
    private ArrayList<Fraction> fractions;
    public final static String COMPARING_FRACTION = "COMPARING_FRACTION";
    public final static String COMPARING_SIMILAR = "COMPARING_SIMILAR";
    public final static String COMPARING_DISSIMILAR = "COMPARING_DISSIMILAR";
    public final static String ORDERING_SIMILAR = "ORDERING_SIMILAR";
    public final static String ORDERING_DISSIMILAR = "ORDERING_DISSIMILAR";
    private final static String ADDING_FRACTION = "ADDING_FRACTION";
    public final static String ADDING_SIMILAR = "ADDING_SIMILAR";
    public final static String ADDING_DISSIMILAR = "ADDING_DISSIMILAR";
    public final static String SUBTRACTING_SIMILAR = "SUBTRACTING_SIMILAR";
    public final static String SUBTRACTING_DISSIMILAR = "SUBTRACTING_DISSIMILAR";
    public final static String MULTIPLYING_FRACTIONS = "MULTIPLYING_FRACTIONS";
    public final static String DIVIDING_FRACTIONS = "DIVIDING_FRACTIONS";
    public final static String ADDING_WITH_MIXED = "ADDING_WITH_MIXED";
    public final static String SUBTRACTING_WITH_MIXED = "SUBTRACTING_WITH_MIXED";
    public final static String MULTIPLYING_WITH_MIXED = "MULTIPLYING_WITH_MIXED";
    public final static String DIVIDING_WITH_MIXED = "DIVIDING_WITH_MIXED";
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
    /*
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
    */

    public FractionQuestion(){
        fractionOne = new Fraction();
        fractionTwo = new Fraction();
    }
    public FractionQuestion(String context){
        fractionOne = new Fraction();
        fractionTwo = new Fraction();
        this.context = context;
        if (context.equals(COMPARING_FRACTION)){
            compareTwoFractions();
        }
        if (context.equals(COMPARING_SIMILAR)){
            while (fractionOne.getDenominator()!=fractionTwo.getDenominator() &&
                    fractionOne.getNumerator()!=fractionTwo.getNumerator()){
                fractionTwo = new Fraction();
            }
            compareTwoFractions();
        }
        if (context.equals(COMPARING_DISSIMILAR)){
            while (fractionOne.getDenominator()==fractionTwo.getDenominator() ||
                    fractionOne.getNumerator()==fractionTwo.getNumerator()){
                fractionTwo = new Fraction();
            }
            compareTwoFractions();
        }
        if (context.equals(ORDERING_SIMILAR)){
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
        if (context.equals(ORDERING_DISSIMILAR)){
            fractionThree = new Fraction();
            while (((fractionOne.getNumerator()==fractionTwo.getNumerator()||
                        fractionTwo.getNumerator()==fractionThree.getNumerator()||
                        fractionOne.getNumerator()==fractionThree.getNumerator())||
                        fractionOne.getDenominator()==fractionTwo.getDenominator()||
                        fractionTwo.getDenominator()==fractionThree.getDenominator()||
                        fractionOne.getDenominator()==fractionThree.getDenominator())
                        ||
                        (
                                fractionOne.getValue().equals(fractionTwo.getValue()) ||
                                fractionTwo.getValue().equals(fractionThree.getValue()) ||
                                fractionOne.getValue().equals(fractionThree.getValue())))
            {
                fractionOne = new Fraction();
                fractionTwo = new Fraction();
                fractionThree = new Fraction();
            }
            addAllToFractionList();
            Collections.sort(fractions);
            int[] denominators = {fractionOne.getDenominator(), fractionTwo.getDenominator(), fractionThree.getDenominator()};
            answer = String.valueOf(Question.getLCM(denominators));
        }
        if (context.equals(ADDING_FRACTION)){
            int [] denominators = {fractionOne.getDenominator(), fractionTwo.getDenominator()};
            int lcd = (int) Question.getLCM(denominators);
            fractionOne.lcdConvert(lcd);
            fractionTwo.lcdConvert(lcd);
            int num = fractionOne.getNumerator() + fractionTwo.getNumerator();
            fractionThree.setNumerator(num);
            fractionThree.setDenominator(lcd);
        }
        if (context.equals(ADDING_SIMILAR)) {
            while (fractionOne.getDenominator() != fractionTwo.getDenominator()){
                fractionTwo = new Fraction();
            }
            int numerator = fractionOne.getNumerator()+fractionTwo.getNumerator();
            fractionThree = new Fraction(numerator,fractionOne.getDenominator());
        }
        if (context.equals(ADDING_DISSIMILAR)) {
            while (fractionOne.getDenominator() == fractionTwo.getDenominator()){
                fractionTwo = new Fraction();
            }
            int [] denominators = {fractionOne.getDenominator(), fractionTwo.getDenominator()};
            int lcd = (int) Question.getLCM(denominators);
            int sumNum = ((lcd/fractionOne.getDenominator())*fractionOne.getNumerator()) +
                    ((lcd/fractionTwo.getDenominator())*fractionTwo.getNumerator());
            fractionAnswer = new Fraction(sumNum,lcd);
        }
        if (context.equals(SUBTRACTING_SIMILAR)) {
            while (fractionOne.getDenominator() != fractionTwo.getDenominator() ||
                    fractionOne.getNumerator()<=fractionTwo.getNumerator()){
                fractionOne = new Fraction();
                fractionTwo = new Fraction();
            }
            int numerator = fractionOne.getNumerator()-fractionTwo.getNumerator();
            fractionAnswer = new Fraction(numerator,fractionOne.getDenominator());
        }
        if (context.equals(SUBTRACTING_DISSIMILAR)) {
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
        if (context.equals(MULTIPLYING_FRACTIONS)){
            int prodNum = fractionOne.getNumerator() * fractionTwo.getNumerator();
            int prodDenom = fractionOne.getDenominator() * fractionTwo.getDenominator();
            fractionAnswer = new Fraction(prodNum,prodDenom);
        }
        if (context.equals(DIVIDING_FRACTIONS)){
            int prodNum = fractionOne.getNumerator() * fractionTwo.getDenominator();
            int prodDenom = fractionOne.getDenominator() * fractionTwo.getNumerator();
            fractionAnswer = new Fraction(prodNum,prodDenom);
        }
        if (context.equals(ADDING_WITH_MIXED)){
            int random = (int) (Math.random() * 3 + 1);
            int sumNum = 0;
            int num;
            int lcd = 0;
            if (random==1){
                fractionOne = new Fraction(Fraction.MIXED);
                num = (fractionOne.getDenominator()*fractionOne.getWholeNum()) + fractionOne.getNumerator();
                while ((num>10 && fractionTwo.getDenominator()>1)&&(fractionOne.getDenominator()!=fractionTwo.getDenominator())){
                    fractionOne = new Fraction(Fraction.MIXED);
                    Log.d(TAG, ADDING_WITH_MIXED + ":first fraction renewed.");
                    num = (fractionOne.getDenominator()*fractionOne.getWholeNum()) + fractionOne.getNumerator();
                }
                int [] denominators = {fractionOne.getDenominator(), fractionTwo.getDenominator()};
                lcd = (int) Question.getLCM(denominators);
                sumNum = ((lcd/fractionOne.getDenominator())*num) +
                        ((lcd/fractionTwo.getDenominator())*fractionTwo.getNumerator());
                fractionAnswer = new Fraction(sumNum,lcd);
            } else if (random==2){
                fractionTwo = new Fraction(Fraction.MIXED);
                num = (fractionTwo.getDenominator()*fractionTwo.getWholeNum()) + fractionTwo.getNumerator();
                while ((num>10 && fractionOne.getDenominator()>1) && (fractionOne.getDenominator()!=fractionTwo.getDenominator())){
                    fractionTwo = new Fraction(Fraction.MIXED);
                    num = (fractionTwo.getDenominator()*fractionTwo.getWholeNum()) + fractionTwo.getNumerator();
                }
                int [] denominators = {fractionOne.getDenominator(), fractionTwo.getDenominator()};
                lcd = (int) Question.getLCM(denominators);
                sumNum = ((lcd/fractionOne.getDenominator())*fractionOne.getNumerator()) +
                        ((lcd/fractionTwo.getDenominator())*num);
                fractionAnswer = new Fraction(sumNum,lcd);
            } else if (random==3){
                fractionOne = new Fraction(Fraction.MIXED);
                fractionTwo = new Fraction(Fraction.MIXED);
                num = (fractionOne.getDenominator()*fractionOne.getWholeNum()) + fractionOne.getNumerator();
                int num2 = (fractionTwo.getDenominator()*fractionTwo.getWholeNum()) + fractionTwo.getNumerator();
                while (((num>10 && fractionTwo.getDenominator()>1)||(num2>10 && fractionOne.getDenominator()>1)) && (fractionOne.getDenominator()!=fractionTwo.getDenominator())){
                    fractionOne = new Fraction(Fraction.MIXED);
                    fractionTwo = new Fraction(Fraction.MIXED);
                    num = (fractionOne.getDenominator()*fractionOne.getWholeNum()) + fractionOne.getNumerator();
                    num2 = (fractionTwo.getDenominator()*fractionTwo.getWholeNum()) + fractionTwo.getNumerator();
                }
                int [] denominators = {fractionOne.getDenominator(), fractionTwo.getDenominator()};
                lcd = (int) Question.getLCM(denominators);
                sumNum = ((lcd/fractionOne.getDenominator())*num) +
                        ((lcd/fractionTwo.getDenominator())*num2);
            }
            fractionAnswer = new Fraction(sumNum,lcd);

        }
        if (context.equals(SUBTRACTING_WITH_MIXED)){
            int random = (int) (Math.random() * 2 + 1);
            int difNum = 0;
            int newNum;
            int lcd = 0;
            if (random==1){
                fractionOne = new Fraction(Fraction.MIXED);
                Log.d(TAG, SUBTRACTING_WITH_MIXED + ":first fraction to mixed");
                while (fractionOne.getValue()<=fractionTwo.getValue()){
                    fractionOne = new Fraction(Fraction.MIXED);
                    fractionTwo = new Fraction();
                    Log.d(TAG, SUBTRACTING_WITH_MIXED + ":two fractions renewed");
                }
                newNum = (fractionOne.getDenominator()*fractionOne.getWholeNum()) + fractionOne.getNumerator();
                while ((newNum>10 && fractionTwo.getDenominator()>1) && (fractionOne.getDenominator()!=fractionTwo.getDenominator())){
                    fractionOne = new Fraction(Fraction.MIXED);
                    while (fractionOne.getValue()<=fractionTwo.getValue()){
                        fractionOne = new Fraction(Fraction.MIXED);
                        fractionTwo = new Fraction();
                    }
                    newNum = (fractionOne.getDenominator()*fractionOne.getWholeNum()) + fractionOne.getNumerator();
                    Log.d(TAG, SUBTRACTING_WITH_MIXED + ":numerator of first fraction renewed");
                }
                int [] denominators = {fractionOne.getDenominator(), fractionTwo.getDenominator()};
                lcd = (int) Question.getLCM(denominators);
                difNum = ((lcd/fractionOne.getDenominator())*newNum) -
                        ((lcd/fractionTwo.getDenominator())*fractionTwo.getNumerator());
            } else if (random==2){
                fractionOne = new Fraction(Fraction.MIXED);
                fractionTwo = new Fraction(Fraction.MIXED);
                while (fractionOne.getValue()<=fractionTwo.getValue()){
                    fractionOne = new Fraction(Fraction.MIXED);
                    fractionTwo = new Fraction(Fraction.MIXED);
                }
                newNum = (fractionOne.getDenominator()*fractionOne.getWholeNum()) + fractionOne.getNumerator();
                int newNum2 = (fractionTwo.getDenominator()*fractionTwo.getWholeNum()) + fractionTwo.getNumerator();
                while (((newNum>10 && fractionTwo.getDenominator()>1)||(newNum2>10 && fractionOne.getDenominator()>1)) && (fractionOne.getDenominator()!=fractionTwo.getDenominator())){
                    fractionOne = new Fraction(Fraction.MIXED);
                    fractionTwo = new Fraction(Fraction.MIXED);
                    while (fractionOne.getValue()<=fractionTwo.getValue()){
                        fractionOne = new Fraction(Fraction.MIXED);
                        fractionTwo = new Fraction(Fraction.MIXED);
                    }
                    newNum = (fractionOne.getDenominator()*fractionOne.getWholeNum()) + fractionOne.getNumerator();
                    newNum2 = (fractionTwo.getDenominator()*fractionTwo.getWholeNum()) + fractionTwo.getNumerator();
                }
                int [] denominators = {fractionOne.getDenominator(), fractionTwo.getDenominator()};
                lcd = (int) Question.getLCM(denominators);
                difNum = ((lcd/fractionOne.getDenominator())*newNum) -
                        ((lcd/fractionTwo.getDenominator())*newNum2);
            }
            fractionAnswer = new Fraction(difNum,lcd);

        }
        if (context.equals(MULTIPLYING_WITH_MIXED)){
            int random = (int) (Math.random() * 3 + 1);
            int prodNum = 0;
            int prodDenom = 0;

            if (random==1){
                fractionOne = new Fraction(Fraction.MIXED);
                while (Fraction.improper(fractionOne).getNumerator()>10 && fractionTwo.getNumerator()>1){
                    fractionOne = new Fraction(Fraction.MIXED);
                }
                prodNum = Fraction.improper(fractionOne).getNumerator()*fractionTwo.getNumerator();
                prodDenom = Fraction.improper(fractionOne).getDenominator()*fractionTwo.getDenominator();
            } else if (random==2){
                fractionTwo = new Fraction(Fraction.MIXED);
                while (Fraction.improper(fractionTwo).getNumerator()>10 && fractionOne.getNumerator()>1){
                    fractionTwo = new Fraction(Fraction.MIXED);
                }
                prodNum = Fraction.improper(fractionTwo).getNumerator()*fractionOne.getNumerator();
                prodDenom = Fraction.improper(fractionTwo).getDenominator()*fractionOne.getDenominator();
            } else if (random==3){
                fractionOne = new Fraction(Fraction.MIXED);
                fractionTwo = new Fraction(Fraction.MIXED);
                while ((Fraction.improper(fractionOne).getNumerator()>10 && Fraction.improper(fractionTwo).getNumerator()>1)||
                        (Fraction.improper(fractionTwo).getNumerator()>10 && Fraction.improper(fractionOne).getNumerator()>1)){
                    fractionOne = new Fraction(Fraction.MIXED);
                    fractionTwo = new Fraction(Fraction.MIXED);
                }
                prodNum = Fraction.improper(fractionOne).getNumerator()*Fraction.improper(fractionTwo).getNumerator();
                prodDenom = Fraction.improper(fractionOne).getDenominator()*Fraction.improper(fractionTwo).getDenominator();
            }

            fractionAnswer = new Fraction(prodNum,prodDenom);
        }
        if (context.equals(DIVIDING_WITH_MIXED)){
            int random = (int) (Math.random() * 2 + 1);
            int prodNum = 0;
            int prodDenom = 0;

            if (random==1){
                fractionOne = new Fraction(Fraction.MIXED);
                while (Fraction.improper(fractionOne).getNumerator()>10&&fractionTwo.getDenominator()>1){
                    fractionOne = new Fraction(Fraction.MIXED);
                }
                prodNum = Fraction.improper(fractionOne).getNumerator()*fractionTwo.getDenominator();
                prodDenom = Fraction.improper(fractionOne).getDenominator()*fractionTwo.getNumerator();
            } else if (random==2){
                fractionTwo = new Fraction(Fraction.MIXED);
                while (Fraction.improper(fractionTwo).getNumerator()>10&&fractionOne.getDenominator()>1){
                    fractionTwo = new Fraction(Fraction.MIXED);
                }
                prodNum = fractionOne.getNumerator()*Fraction.improper(fractionTwo).getDenominator();
                prodDenom = fractionOne.getDenominator()*Fraction.improper(fractionTwo).getNumerator();
            } else if (random==3){
                fractionOne = new Fraction(Fraction.MIXED);
                fractionTwo = new Fraction(Fraction.MIXED);
                while ((Fraction.improper(fractionOne).getNumerator()>10&&fractionTwo.getDenominator()>1)||
                        (Fraction.improper(fractionTwo).getNumerator()>10&&fractionOne.getDenominator()>1)){
                    fractionOne = new Fraction(Fraction.MIXED);
                    fractionTwo = new Fraction(Fraction.MIXED);
                }
                prodNum = Fraction.improper(fractionOne).getNumerator()*Fraction.improper(fractionTwo).getDenominator();
                prodDenom = Fraction.improper(fractionOne).getDenominator()*Fraction.improper(fractionTwo).getNumerator();
            }

            fractionAnswer = new Fraction(prodNum,prodDenom);
        }
    }
    public FractionQuestion(Fraction fractionOne, Fraction fractionTwo, String Context){
        this.fractionOne = fractionOne;
        this.fractionTwo = fractionTwo;
        if (Context.equals(COMPARING_FRACTION)){
            compareTwoFractions();
        }
    }
    private void compareTwoFractions(){
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
    private void addAllToFractionList(){
        fractions = new ArrayList<>();
        fractions.add(fractionOne);
        fractions.add(fractionTwo);
        fractions.add(fractionThree);
    }


}
