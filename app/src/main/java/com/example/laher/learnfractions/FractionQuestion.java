package com.example.laher.learnfractions;

public class FractionQuestion {
    Fraction fractionOne, fractionTwo;
    String answer;
    public final static String COMPARING_FRACTION = "COMPARING_FRACTION";
    public final static String COMPARING_SIMILAR = "COMPARING_SIMILAR";
    public final static String COMPARING_DISSIMILAR = "COMPARING_DISSIMILAR";
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
    public void setFractionOne(Fraction fractionOne) {
        this.fractionOne = fractionOne;
    }
    public void setFractionTwo(Fraction fractionTwo) {
        this.fractionTwo = fractionTwo;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public FractionQuestion(){
        fractionOne = new Fraction();
        fractionTwo = new Fraction();
    }
    public FractionQuestion(String Context){
        fractionOne = new Fraction();
        fractionTwo = new Fraction();
        if (Context == COMPARING_FRACTION){
            compareTwoFractions();
        }
        if (Context == COMPARING_SIMILAR){
            while (fractionOne.getDenominator()!=fractionTwo.getDenominator() ||
                    fractionOne.getNumerator()!=fractionTwo.getNumerator()){
                fractionTwo = new Fraction();
            }
            compareTwoFractions();
        }
        if (Context == COMPARING_DISSIMILAR){
            while (fractionOne.getDenominator()==fractionTwo.getDenominator() &&
                    fractionOne.getNumerator()==fractionTwo.getNumerator()){
                fractionTwo = new Fraction();
            }
            compareTwoFractions();
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
