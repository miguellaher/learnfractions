package com.example.laher.learnfractions.util;

public class AppConstants {
    public static final String METHOD_GET = "METHOD_GET";
    public static final String METHOD_POST = "METHOD_POST";

    public static final String USER = "User";
    public static final String STUDENT = "Student";
    public static final String TEACHER = "Teacher";
    public static final String ADMIN = "Admin";

    public static final String ENABLE = "Enable";
    public static final String DISABLE = "Disable";

    public static final String MALE= "MALE";
    public static final String FEMALE= "FEMALE";



    //LESSONS
    public static final String FRACTION_MEANING="Fraction Meaning";
    public static final String NON_VISUAL_FRACTION="Non-Visual Fraction";
    public static final String COMPARING_SIMILAR_FRACTIONS="Comparing Similar Fractions";
    public static final String COMPARING_DISSIMILAR_FRACTIONS="Comparing Dissimilar Fractions";
    public static final String COMPARING_FRACTIONS="Comparing Fractions";
    public static final String ORDERING_SIMILAR="Ordering Similar Fractions";
    public static final String ORDERING_DISSIMILAR="Ordering Dissimilar Fractions";
    public static final String CLASSIFYING_FRACTIONS="Classifying Fractions";
    public static final String CONVERTING_FRACTIONS="Converting Fractions";
    public static final String ADDING_SIMILAR="Adding Similar Fractions";
    public static final String ADDING_DISSIMILAR="Adding Dissimilar Fractions";
    public static final String SUBTRACTING_SIMILAR="Subtracting Similar Fractions";
    public static final String SUBTRACTING_DISSIMILAR="Subtracting Dissimilar Fractions";
    public static final String MULTIPLYING_FRACTIONS="Multiplying Fractions";
    public static final String DIVIDING_FRACTIONS="Dividing Fractions";
    public static final String ADDING_SUBTRACTING_MIXED="Adding and Subtracting with Mixed Fractions";
    public static final String MULTIPLYING_DIVIDING_MIXED="Multiplying and Dividing with Mixed Fractions";


    //MESSAGES
    public static final String DONE = "DONE";
    public static final String CORRECT = "Correct.";
    public static final String ERROR = "Wrong.";
    public static final String FINISHED_EXERCISE = "You have finished the exercise. Click next and proceed.";
    public static final String FINISHED_LESSON = "Congratulations, you have finished the lesson.";


    public static final String FAILED_CONSECUTIVE(int error){
        return "You had " + error + " consecutive error/s. Preparing to start previous activity.";
    }
    public static final String FAILED(int error){
        return "You had " + error + " error/s. Preparing to start previous activity.";
    }
    public static final String SCORE(int correct, int items){
        if (items>=correct) {
            return correct + " / " + items;
        } else {
            return null;
        }
    }
    public static final String ITEM(int currentItem, int items){
        if (items>=currentItem) {
            return "#" + currentItem + " of " + items;
        } else {
            return null;
        }
    }


    public static final int STARTING_NUM = 1;
    public static final int DEFAULT_ITEMS_NUM = 10;

    //INSTRUCTIONS
    public static final String I_COMPARE = "Compare the two fractions";
}