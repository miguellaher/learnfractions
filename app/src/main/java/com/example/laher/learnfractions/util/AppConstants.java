package com.example.laher.learnfractions.util;

import android.graphics.Color;

import com.example.laher.learnfractions.fraction_util.Fraction;

import java.util.ArrayList;

public class AppConstants {
    public static final String METHOD_GET = "METHOD_GET";
    public static final String METHOD_POST = "METHOD_POST";

    public static final String USER = "User";
    public static final String STUDENT = "Student";
    public static final String TEACHER = "Teacher";

    public static final String MALE= "MALE";
    public static final String FEMALE= "FEMALE";

    public static final String LOG_OUT = "Log out";
    public static final String SEAT_WORKS = "Seat Works";
    public static final String CHAPTER_EXAM = "Chapter Exam";
    public static final String TEACHER_MAIN = "Teacher Main";
    public static final String USER_CODE = "THIS_IS_A_USER";
    public static final String USER_MAIN = "User Main";
    public static final String EXERCISE_RANKING = "Exercise Ranking";
    public static final String SW_RANKING = "Seatwork Ranking";
    public static final String EXAM_RANKING = "Chapter Exam Ranking";
    public static final String E_PROGRESSES = "Exercise Data";
    public static final String EXAM_PROGRESSES = "Exam Data";
    public static final String LESSONS = "Lessons";
    public static final String STUDENT_MAIN = "Student Main";
    public static final String SW_PROGRESSES = "Seat Work Data";
    public static final String SAVE = "Save";

    public static final String C_EXAM1 = "Chapter Exam 1";
    public static final String C_EXAM2 = "Chapter Exam 2";
    public static final String C_EXAM3 = "Chapter Exam 3";
    public static final String C_EXAM4 = "Chapter Exam 4";
    public static final String C_EXAM5 = "Chapter Exam 5";
    public static final String C_EXAM6 = "Chapter Exam 6";

    public static final String NUMERATOR = "Numerator";
    public static final String DENOMINATOR = "Denominator";

    public static final String CLASS_RANKS = "Class Ranks";
    public static final String GLOBAL_RANKS = "Global User Ranks";


    //LESSONS
    public static final String FRACTION_MEANING="Fraction Meaning";
    public static final String NON_VISUAL_FRACTION="Non-Visual Fraction";
    public static final String COMPARING_SIMILAR_FRACTIONS="Comparing Similar Fraction";
    public static final String COMPARING_DISSIMILAR_FRACTIONS="Comparing Dissimilar Fraction";
    public static final String COMPARING_FRACTIONS="Comparing Fraction";
    public static final String ORDERING_SIMILAR="Ordering Similar Fraction";
    public static final String ORDERING_DISSIMILAR="Ordering Dissimilar Fraction";
    public static final String CLASSIFYING_FRACTIONS="Classifying Fraction";
    public static final String CONVERTING_FRACTIONS="Converting Fraction";
    public static final String ADDING_SIMILAR="Adding Similar Fraction";
    public static final String ADDING_DISSIMILAR="Adding Dissimilar Fraction";
    public static final String SUBTRACTING_SIMILAR="Subtracting Similar Fraction";
    public static final String SUBTRACTING_DISSIMILAR="Subtracting Dissimilar Fraction";
    public static final String MULTIPLYING_FRACTIONS="Multiplying Fraction";
    public static final String DIVIDING_FRACTIONS="Dividing Fraction";
    public static final String ADDING_SUBTRACTING_MIXED="Adding and Subtracting with Mixed Fraction";
    public static final String MULTIPLYING_DIVIDING_MIXED="Multiplying and Dividing with Mixed Fraction";


    //MESSAGES
    public static final String DONE = "DONE";
    public static final String CORRECT = "Correct.";
    public static final String ERROR = "Wrong.";
    public static final String FINISHED_EXERCISE = "You have finished the exercise. Click next and proceed.";
    public static final String FINISHED_LESSON = "Congratulations, you have finished the lesson.";
    public static final String INVALID_INPUT = "Invalid input.";
    public static final String LOGOUT_CONFIRMATION = "Are you sure you want to Logout?";


    public static final String GREATER_THAN = ">";
    public static final String LESS_THAN = "<";
    public static final String EQUAL_TO = "=";

    public static final Fraction EQUAL_FRACTIONS = new Fraction(0,0);
    public static final String SERVER_DOWN = "Internal Server Error.";


    public static String FAILED_CONSECUTIVE(int error){
        return "You had " + error + " consecutive error/s. Preparing to start previous activity.";
    }
    public static String FAILED(int error){
        return "You had " + error + " error/s. Preparing to start previous activity.";
    }
    public static String SCORE(int correct, int items){
        if (items>=correct) {
            return correct + " / " + items;
        } else {
            return null;
        }
    }
    public static String ITEM(int currentItem, int items){
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


    //COLORS
    public static final int BG_DEFAULT_NOT_FINISHED = Color.rgb(169,169,169);

    public static ArrayList<String> getSecurityQuestions(){
        ArrayList<String> securityQuestionsList = new ArrayList<>();

        securityQuestionsList.add("What was your childhood nickname?");
        securityQuestionsList.add("What is the name of your childhood dog?");
        securityQuestionsList.add("What is your oldest sibling's middle name?");
        securityQuestionsList.add("What was the name of your first stuffed animal?");
        securityQuestionsList.add("What street did you live on in third grade?");
        securityQuestionsList.add("What is your mother's middle name?");
        securityQuestionsList.add("Who was your childhood superhero?");
        securityQuestionsList.add("What is your favorite movie?");

        return securityQuestionsList;
    }
}
