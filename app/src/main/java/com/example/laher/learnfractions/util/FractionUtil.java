package com.example.laher.learnfractions.util;

import com.example.laher.learnfractions.fraction_util.Fraction;

import java.util.ArrayList;

/**
 * Created by Laher on 4/23/2018.
 */

public class FractionUtil {
    public static int getLcm(ArrayList<Integer> numbers){ //method's performance level: low
        int tempLcm = 1;                                  //please be careful on maintaining
        boolean lcmAttained = false;
        while (!lcmAttained){
            boolean isDivisible = true;
            for (Integer number : numbers){
                if (tempLcm % number != 0) {
                    isDivisible = false;
                }
            }
            if (isDivisible){
                lcmAttained = true;
                return tempLcm;
            } else {
                tempLcm++;
            }
        }
        return 0;
    }
    public static int getLcd(ArrayList<Fraction> fractions){
        ArrayList<Integer> denominators = new ArrayList<>();
        for (Fraction fraction : fractions){
            int denominator = fraction.getDenominator();
            denominators.add(denominator);
        }
        return getLcm(denominators);
    }

}
