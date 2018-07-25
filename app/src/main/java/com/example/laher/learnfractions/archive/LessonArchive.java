package com.example.laher.learnfractions.archive;


import android.util.Log;

import com.example.laher.learnfractions.lessons.converting_fractions.ConvertingFractionsVideoActivity;
import com.example.laher.learnfractions.lessons.non_visual_fraction.NonVisualVideoActivity;
import com.example.laher.learnfractions.lessons.ordering_dissimilar.OrderingDissimilarExercise2Activity;
import com.example.laher.learnfractions.model.ExerciseStat;
import com.example.laher.learnfractions.model.Lesson;
import com.example.laher.learnfractions.lessons.adding_dissimilar.AddingDissimilarVideoActivity;
import com.example.laher.learnfractions.lessons.adding_similar.AddingSimilarVideoActivity;
import com.example.laher.learnfractions.lessons.classifying_fractions.ClassifyingFractionsVideoActivity;
import com.example.laher.learnfractions.lessons.comparing_dissimilar_fractions.ComparingDissimilarVideoActivity;
import com.example.laher.learnfractions.lessons.comparing_fractions.ComparingFractionsVideoActivity;
import com.example.laher.learnfractions.lessons.comparing_similar_fractions.ComparingSimilarVideoActivity;
import com.example.laher.learnfractions.lessons.dividing_fractions.DividingFractionsVideoActivity;
import com.example.laher.learnfractions.lessons.fraction_meaning.FractionMeaningVideoActivity;
import com.example.laher.learnfractions.lessons.multiplying_fractions.MultiplyingFractionsVideoActivity;
import com.example.laher.learnfractions.lessons.ordering_similar.OrderingSimilarVideoActivity;
import com.example.laher.learnfractions.lessons.solving_mixed.SolvingMixedVideoActivity;
import com.example.laher.learnfractions.lessons.solving_mixed2.SolvingMixed2VideoActivity;
import com.example.laher.learnfractions.lessons.subtracting_dissimilar.SubtractingDissimilarVideoActivity;
import com.example.laher.learnfractions.lessons.subtracting_similar.SubtractingSimilarVideoActivity;
import com.example.laher.learnfractions.util.AppConstants;

import java.util.ArrayList;

public abstract class LessonArchive {
    private static final String TAG = "L_ARV";
    //CHANGE STARTING ACTIVITIES
    private static final Lesson fractionMeaning = new Lesson(AppConstants.FRACTION_MEANING, FractionMeaningVideoActivity.class);
    private static final Lesson nonVisualFraction = new Lesson(AppConstants.NON_VISUAL_FRACTION, NonVisualVideoActivity.class);
    private static final Lesson comparingSimilarFractions = new Lesson(AppConstants.COMPARING_SIMILAR_FRACTIONS, ComparingSimilarVideoActivity.class);
    private static final Lesson comparingDissimilarFractions = new Lesson(AppConstants.COMPARING_DISSIMILAR_FRACTIONS, ComparingDissimilarVideoActivity.class);
    private static final Lesson comparingFractions = new Lesson(AppConstants.COMPARING_FRACTIONS, ComparingFractionsVideoActivity.class);

    private static final Lesson orderingSimilarFractions = new Lesson(AppConstants.ORDERING_SIMILAR, OrderingSimilarVideoActivity.class);
    private static final Lesson orderingDissimilarFractions = new Lesson(AppConstants.ORDERING_DISSIMILAR, OrderingDissimilarExercise2Activity.class);
    private static final Lesson classifyingFractions = new Lesson(AppConstants.CLASSIFYING_FRACTIONS, ClassifyingFractionsVideoActivity.class);
    private static final Lesson convertingFractions = new Lesson(AppConstants.CONVERTING_FRACTIONS, ConvertingFractionsVideoActivity.class);
    private static final Lesson addingSimilarFractions = new Lesson(AppConstants.ADDING_SIMILAR, AddingSimilarVideoActivity.class);

    private static final Lesson addingDissimilarFractions = new Lesson(AppConstants.ADDING_DISSIMILAR, AddingDissimilarVideoActivity.class);
    private static final Lesson subtractingSimilarFractions = new Lesson(AppConstants.SUBTRACTING_SIMILAR, SubtractingSimilarVideoActivity.class);
    private static final Lesson subtractingDissimilarFractions = new Lesson(AppConstants.SUBTRACTING_DISSIMILAR, SubtractingDissimilarVideoActivity.class);
    private static final Lesson multiplyingFractions = new Lesson(AppConstants.MULTIPLYING_FRACTIONS, MultiplyingFractionsVideoActivity.class);
    private static final Lesson dividingFractions = new Lesson(AppConstants.DIVIDING_FRACTIONS, DividingFractionsVideoActivity.class);

    private static final Lesson solvingWithMixed1 = new Lesson(AppConstants.ADDING_SUBTRACTING_MIXED, SolvingMixedVideoActivity.class);
    private static final Lesson solvingWithMixed2 = new Lesson(AppConstants.MULTIPLYING_DIVIDING_MIXED, SolvingMixed2VideoActivity.class);

    public static Lesson getLesson(String lessonTitle){
        if (fractionMeaning.getTitle().equals(lessonTitle)){
            ArrayList<ExerciseStat> exerciseStats = new ArrayList<>();
            exerciseStats.add(new ExerciseStat(fractionMeaning.getTitle(),1,6,
                            true,3,true));
            exerciseStats.add(new ExerciseStat(fractionMeaning.getTitle(),2,5,
                            true,2,true));
            fractionMeaning.setExercises(exerciseStats);
            /*fractionMeaning.setExercises(new ArrayList<ExerciseStat>());
            fractionMeaning.getExercises().add(
                    new ExerciseStat(fractionMeaning.getTitle(),1,6,
                            true,3,true));
            Log.d(TAG, fractionMeaning.getExercises().get(0).getTopicName() + " : " +
                    fractionMeaning.getExercises().get(0).getExerciseNum());
            fractionMeaning.getExercises().add(
                    new ExerciseStat(fractionMeaning.getTitle(),2,5,
                            true,2,true));
            Log.d(TAG, fractionMeaning.getExercises().get(1).getTopicName() + " : " +
                    fractionMeaning.getExercises().get(1).getExerciseNum());
                    */
            return fractionMeaning;
        }
        if (nonVisualFraction.getTitle().equals(lessonTitle)){
            nonVisualFraction.setExercises(new ArrayList<ExerciseStat>());
            nonVisualFraction.getExercises().add(new ExerciseStat(nonVisualFraction.getTitle(),
                    1, 8, true, 4, true));
            nonVisualFraction.getExercises().add(new ExerciseStat(nonVisualFraction.getTitle(),
                    2, 8, true, 4, true));
            return nonVisualFraction;
        }
        if (comparingSimilarFractions.getTitle().equals(lessonTitle)){
            comparingSimilarFractions.setExercises(new ArrayList<ExerciseStat>());
            comparingSimilarFractions.getExercises().add(new ExerciseStat(comparingSimilarFractions.getTitle(),
                    1, 5, true, 3, true));
            comparingSimilarFractions.getExercises().add(new ExerciseStat(comparingSimilarFractions.getTitle(),
                    2,8 , true, 4, true));
            return comparingSimilarFractions;
        }
        if (comparingDissimilarFractions.getTitle().equals(lessonTitle)){
            comparingDissimilarFractions.setExercises(new ArrayList<ExerciseStat>());
            comparingDissimilarFractions.getExercises().add(new ExerciseStat(comparingDissimilarFractions.getTitle(),
                    1,8));
            comparingDissimilarFractions.getExercises().add(
                    new ExerciseStat(comparingDissimilarFractions.getTitle(),
                            2,
                            5,
                            true,
                            3,
                            true));
            return comparingDissimilarFractions;
        }
        if (comparingFractions.getTitle().equals(lessonTitle)){
            comparingFractions.setExercises(new ArrayList<ExerciseStat>());
            comparingFractions.getExercises().add(
                    new ExerciseStat(comparingFractions.getTitle()
                            ,1
                            ,10
                            ,true
                            ,3
                            ,true
                    ));
            comparingFractions.getExercises().add(
                    new ExerciseStat(comparingFractions.getTitle()
                            ,2
                            ,10
                            ,true
                            ,5
                            ,true
                    ));
            return comparingFractions;
        }
        if (orderingSimilarFractions.getTitle().equals(lessonTitle)){
            orderingSimilarFractions.setExercises(new ArrayList<ExerciseStat>());
            orderingSimilarFractions.getExercises().add(
                    new ExerciseStat(orderingSimilarFractions.getTitle()
                            ,1
                            ,10
                            ,true
                            ,4
                            ,true
                    ));
            orderingSimilarFractions.getExercises().add(
                    new ExerciseStat(orderingSimilarFractions.getTitle()
                            ,2
                            ,10
                            ,true
                            ,4
                            ,true
                    ));
            return orderingSimilarFractions;
        }
        if (orderingDissimilarFractions.getTitle().equals(lessonTitle)){
            orderingDissimilarFractions.setExercises(new ArrayList<ExerciseStat>());
            orderingDissimilarFractions.getExercises().add(
                    new ExerciseStat(orderingDissimilarFractions.getTitle()
                            ,1
                            ,10
                            ,true
                            ,3
                            ,true
                    ));
            orderingDissimilarFractions.getExercises().add(
                    new ExerciseStat(orderingDissimilarFractions.getTitle()
                            ,2
                            ,10
                            ,true
                            ,4
                            ,true
                    ));
            return orderingDissimilarFractions;
        }
        if (classifyingFractions.getTitle().equals(lessonTitle)){
            classifyingFractions.setExercises(new ArrayList<ExerciseStat>());
            classifyingFractions.getExercises().add(
                    new ExerciseStat(classifyingFractions.getTitle()
                            ,1
                            ,10
                            ,true
                            ,3
                            ,true
                    ));
            return classifyingFractions;
        }
        if (convertingFractions.getTitle().equals(lessonTitle)){
            convertingFractions.setExercises(new ArrayList<ExerciseStat>());
            convertingFractions.getExercises().add(
                    new ExerciseStat(convertingFractions.getTitle()
                            ,1
                            ,10
                    ));
            convertingFractions.getExercises().add(
                    new ExerciseStat(convertingFractions.getTitle()
                            ,2
                            ,10
                    ));
            return convertingFractions;
        }
        if (addingSimilarFractions.getTitle().equals(lessonTitle)){
            addingSimilarFractions.setExercises(new ArrayList<ExerciseStat>());
            addingSimilarFractions.getExercises().add(
                    new ExerciseStat(addingSimilarFractions
                            .getTitle()
                            ,1
                            ,10
                            ,true
                            ,3
                            ,true
                    ));
            return addingSimilarFractions;
        }
        if (addingDissimilarFractions.getTitle().equals(lessonTitle)){
            addingDissimilarFractions.setExercises(new ArrayList<ExerciseStat>());
            addingDissimilarFractions.getExercises().add(
                    new ExerciseStat(addingDissimilarFractions
                            .getTitle()
                    ,1
                    ,10
                    ,true
                    ,3
                    ,true
            ));
            return addingDissimilarFractions;
        }
        if (subtractingSimilarFractions.getTitle().equals(lessonTitle)){
            subtractingSimilarFractions.setExercises(new ArrayList<ExerciseStat>());
            subtractingSimilarFractions.getExercises().add(
                    new ExerciseStat(subtractingSimilarFractions
                            .getTitle()
                    ,1
                    ,10
                    ,true
                    ,3
                    ,true
            ));
            return subtractingSimilarFractions;
        }
        if (subtractingDissimilarFractions.getTitle().equals(lessonTitle)){
            subtractingDissimilarFractions.setExercises(new ArrayList<ExerciseStat>());
            subtractingDissimilarFractions.getExercises().add(
                    new ExerciseStat(subtractingDissimilarFractions
                            .getTitle()
                    ,1
                    ,10
                    ,true
                    ,3
                    ,true
            ));
            return subtractingDissimilarFractions;
        }
        if (multiplyingFractions.getTitle().equals(lessonTitle)){
            multiplyingFractions.setExercises(new ArrayList<ExerciseStat>());
            multiplyingFractions.getExercises().add(
                    new ExerciseStat(multiplyingFractions
                            .getTitle()
                    ,1
                    ,10
                    ,true
                    ,5
                    ,true
            ));
            return multiplyingFractions;
        }
        if (dividingFractions.getTitle().equals(lessonTitle)){
            dividingFractions.setExercises(new ArrayList<ExerciseStat>());
            dividingFractions.getExercises().add(
                    new ExerciseStat(dividingFractions
                            .getTitle()
                    ,1
                    ,10
                    ,true
                    ,5
                    ,true
            ));
            return dividingFractions;
        }
        if (solvingWithMixed1.getTitle().equals(lessonTitle)){
            solvingWithMixed1.setExercises(new ArrayList<ExerciseStat>());
            solvingWithMixed1.getExercises().add(
                    new ExerciseStat(solvingWithMixed1
                            .getTitle()
                    ,1
                    ,10
                    ,true
                    ,5
                    ,true
            ));
            return solvingWithMixed1;
        }
        if (solvingWithMixed2.getTitle().equals(lessonTitle)){
            solvingWithMixed2.setExercises(new ArrayList<ExerciseStat>());
            solvingWithMixed2.getExercises().add(
                    new ExerciseStat(solvingWithMixed2
                            .getTitle()
                    ,1
                    ,10
                    ,true
                    ,5
                    ,true
            ));
            return solvingWithMixed2;
        }

        return null;
    }
    public static ArrayList<Lesson> getAllLessons(){
        ArrayList<Lesson> lessons = new ArrayList<>();
        lessons.add(getLesson(fractionMeaning.getTitle()));
        lessons.add(getLesson(nonVisualFraction.getTitle()));
        lessons.add(getLesson(comparingSimilarFractions.getTitle()));
        lessons.add(getLesson(comparingDissimilarFractions.getTitle()));
        lessons.add(getLesson(comparingFractions.getTitle()));

        lessons.add(getLesson(orderingSimilarFractions.getTitle()));
        lessons.add(getLesson(orderingDissimilarFractions.getTitle()));
        lessons.add(getLesson(classifyingFractions.getTitle()));
        lessons.add(getLesson(convertingFractions.getTitle()));
        lessons.add(getLesson(addingSimilarFractions.getTitle()));

        lessons.add(getLesson(addingDissimilarFractions.getTitle()));
        lessons.add(getLesson(subtractingSimilarFractions.getTitle()));
        lessons.add(getLesson(subtractingDissimilarFractions.getTitle()));
        lessons.add(getLesson(multiplyingFractions.getTitle()));
        lessons.add(getLesson(dividingFractions.getTitle()));

        lessons.add(solvingWithMixed1);
        lessons.add(solvingWithMixed2);
        return lessons;
    }

}
