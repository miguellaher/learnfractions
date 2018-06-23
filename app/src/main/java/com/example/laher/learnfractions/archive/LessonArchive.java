package com.example.laher.learnfractions.archive;

import com.example.laher.learnfractions.model.Lesson;
import com.example.laher.learnfractions.lessons.adding_dissimilar.AddingDissimilarVideoActivity;
import com.example.laher.learnfractions.lessons.adding_similar.AddingSimilarVideoActivity;
import com.example.laher.learnfractions.lessons.classifying_fractions.ClassifyingFractionsVideoActivity;
import com.example.laher.learnfractions.lessons.comparing_dissimilar_fractions.ComparingDissimilarVideoActivity;
import com.example.laher.learnfractions.lessons.comparing_fractions.ComparingFractionsVideoActivity;
import com.example.laher.learnfractions.lessons.comparing_similar_fractions.ComparingSimilarVideoActivity;
import com.example.laher.learnfractions.lessons.converting_fractions.ConvertingFractionsVideoActivity;
import com.example.laher.learnfractions.lessons.dividing_fractions.DividingFractionsVideoActivity;
import com.example.laher.learnfractions.lessons.fraction_meaning.FractionMeaningVideoActivity;
import com.example.laher.learnfractions.lessons.multiplying_fractions.MultiplyingFractionsVideoActivity;
import com.example.laher.learnfractions.lessons.non_visual_fraction.NonVisualVideoActivity;
import com.example.laher.learnfractions.lessons.ordering_dissimilar.OrderingDissimilarVideoActivity;
import com.example.laher.learnfractions.lessons.ordering_similar.OrderingSimilarVideoActivity;
import com.example.laher.learnfractions.lessons.solving_mixed.SolvingMixedVideoActivity;
import com.example.laher.learnfractions.lessons.solving_mixed2.SolvingMixed2VideoActivity;
import com.example.laher.learnfractions.lessons.subtracting_dissimilar.SubtractingDissimilarVideoActivity;
import com.example.laher.learnfractions.lessons.subtracting_similar.SubtractingSimilarVideoActivity;

import java.util.ArrayList;

public abstract class LessonArchive {
    public static final Lesson fractionMeaning = new Lesson("Fraction Meaning", FractionMeaningVideoActivity.class);
    public static final Lesson nonVisualFraction = new Lesson("Non-Visual Fraction", NonVisualVideoActivity.class);
    public static final Lesson comparingSimilarFractions = new Lesson("Comparing Similar Fractions", ComparingSimilarVideoActivity.class);
    public static final Lesson comparingDissimilarFractions = new Lesson("Comparing Dissimilar Fractions", ComparingDissimilarVideoActivity.class);
    public static final Lesson comparingFractions = new Lesson("Comparing Fractions", ComparingFractionsVideoActivity.class);

    public static final Lesson orderingSimilarFractions = new Lesson("Ordering Similar Fractions", OrderingSimilarVideoActivity.class);
    public static final Lesson orderingDissimilarFractions = new Lesson("Ordering Dissimilar Fractions", OrderingDissimilarVideoActivity.class);
    public static final Lesson classifyingFractions = new Lesson("Classifying Fractions", ClassifyingFractionsVideoActivity.class);
    public static final Lesson convertingFractions = new Lesson("Converting Fractions", ConvertingFractionsVideoActivity.class);
    public static final Lesson addingSimilarFractions = new Lesson("Adding Similar Fractions", AddingSimilarVideoActivity.class);

    public static final Lesson addingDissimilarFractions = new Lesson("Adding Dissimilar Fractions", AddingDissimilarVideoActivity.class);
    public static final Lesson subtractingSimilarFractions = new Lesson("Subtracting Similar Fractions", SubtractingSimilarVideoActivity.class);
    public static final Lesson subtractingDissimilarFractions = new Lesson("Subtracting Dissimilar Fractions", SubtractingDissimilarVideoActivity.class);
    public static final Lesson multiplyingFractions = new Lesson("Multiplying Fractions", MultiplyingFractionsVideoActivity.class);
    public static final Lesson dividingFractions = new Lesson("Dividing Fractions", DividingFractionsVideoActivity.class);

    public static final Lesson solvingWithMixed1 = new Lesson("Adding and Subtracting with Mixed Fractions", SolvingMixedVideoActivity.class);
    public static final Lesson solvingWithMixed2 = new Lesson("Multiplying and Dividing with Mixed Fractions", SolvingMixed2VideoActivity.class);

    public static Lesson getLesson(String lessonTitle){
        if (fractionMeaning.getTitle() == lessonTitle){
            return fractionMeaning;
        }
        if (nonVisualFraction.getTitle() == lessonTitle){
            return nonVisualFraction;
        }
        if (comparingSimilarFractions.getTitle() == lessonTitle){
            return comparingSimilarFractions;
        }
        if (comparingDissimilarFractions.getTitle() == lessonTitle){
            return comparingDissimilarFractions;
        }
        if (comparingFractions.getTitle() == lessonTitle){
            return comparingFractions;
        }
        if (orderingSimilarFractions.getTitle() == lessonTitle){
            return orderingSimilarFractions;
        }
        if (orderingDissimilarFractions.getTitle() == lessonTitle){
            return orderingDissimilarFractions;
        }
        if (classifyingFractions.getTitle() == lessonTitle){
            return classifyingFractions;
        }
        if (convertingFractions.getTitle() == lessonTitle){
            return convertingFractions;
        }
        if (addingSimilarFractions.getTitle() == lessonTitle){
            return addingSimilarFractions;
        }
        if (addingDissimilarFractions.getTitle() == lessonTitle){
            return addingDissimilarFractions;
        }
        if (subtractingSimilarFractions.getTitle() == lessonTitle){
            return subtractingSimilarFractions;
        }
        if (subtractingDissimilarFractions.getTitle() == lessonTitle){
            return subtractingDissimilarFractions;
        }
        if (multiplyingFractions.getTitle() == lessonTitle){
            return multiplyingFractions;
        }
        if (dividingFractions.getTitle() == lessonTitle){
            return dividingFractions;
        }
        if (solvingWithMixed1.getTitle() == lessonTitle){
            return solvingWithMixed1;
        }
        if (solvingWithMixed2.getTitle() == lessonTitle){
            return solvingWithMixed2;
        }

        return null;
    }
    public static ArrayList<Lesson> getAllLessons(){
        ArrayList<Lesson> lessons = new ArrayList<>();
        lessons.add(fractionMeaning);
        lessons.add(nonVisualFraction);
        lessons.add(comparingSimilarFractions);
        lessons.add(comparingDissimilarFractions);
        lessons.add(comparingFractions);

        lessons.add(orderingSimilarFractions);
        lessons.add(orderingDissimilarFractions);
        lessons.add(classifyingFractions);
        lessons.add(convertingFractions);
        lessons.add(addingSimilarFractions);

        lessons.add(addingDissimilarFractions);
        lessons.add(subtractingSimilarFractions);
        lessons.add(subtractingDissimilarFractions);
        lessons.add(multiplyingFractions);
        lessons.add(dividingFractions);

        lessons.add(solvingWithMixed1);
        lessons.add(solvingWithMixed2);
        return lessons;
    }

}
