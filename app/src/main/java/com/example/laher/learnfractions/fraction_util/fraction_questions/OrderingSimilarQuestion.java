package com.example.laher.learnfractions.fraction_util.fraction_questions;

import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.fraction_util.Fraction;
import com.example.laher.learnfractions.fraction_util.FractionQuestionClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OrderingSimilarQuestion extends FractionQuestionClass {
    private Fraction fraction1;
    private Fraction fraction2;
    private Fraction fraction3;
    private ArrayList<Fraction> fractions;
    private ArrayList<Fraction> sortedFractions;

    public Fraction getFraction1() {
        return fraction1;
    }

    public Fraction getFraction2() {
        return fraction2;
    }

    public Fraction getFraction3() {
        return fraction3;
    }

    public ArrayList<Fraction> getSortedFractions() {
        return sortedFractions;
    }

    public OrderingSimilarQuestion() {
        Range range = new Range();
        generateFractions(range);
    }

    public OrderingSimilarQuestion(Range range) {
        super(range);
        int intRange = range.getRange();
        while (intRange<3){
            int maximum = range.getMaximum();
            maximum = maximum + 1;
            range.setMaximum(maximum);
            intRange = range.getRange();
        }
        generateFractions(range);
    }

    public void generateFractions(Range range){
        fraction1 = new Fraction(range);
        fraction2 = new Fraction(range);
        fraction3 = new Fraction(range);
        while ((fraction1.equals(fraction2) ||
                fraction1.equals(fraction3) ||
                fraction2.equals(fraction3)) ||
                (fraction1.getDenominator()!=fraction2.getDenominator() ||
                fraction1.getDenominator()!=fraction3.getDenominator() ||
                fraction2.getDenominator()!=fraction3.getDenominator())){
            fraction1 = new Fraction(range);
            fraction2 = new Fraction(range);
            fraction3 = new Fraction(range);
        }
        fractions = new ArrayList<>();
        fractions.add(fraction1);
        fractions.add(fraction2);
        fractions.add(fraction3);
        sortedFractions = fractions;
        Collections.sort(sortedFractions, new SortFractions());
    }

    private class SortFractions implements Comparator<Fraction>{

        @Override
        public int compare(Fraction o1, Fraction o2) {
            if (o1.compare(o2)>0){
                return 1;
            } else if (o1.compare(o2)<0){
                return -1;
            }
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OrderingSimilarQuestion){
            OrderingSimilarQuestion orderingFractionsQuestion = (OrderingSimilarQuestion) obj;
            ArrayList<Fraction> fractions1 = this.getSortedFractions();
            ArrayList<Fraction> fractions2 = orderingFractionsQuestion.getSortedFractions();
            return fractions1.equals(fractions2);
        }
        return super.equals(obj);
    }
}
