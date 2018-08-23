package com.example.laher.learnfractions.util;

import com.example.laher.learnfractions.classes.Range;

public class Limit {
    public static int rangeUp3(Range range){
        int intRange = range.getRange();
        return intRange * intRange * intRange;
    }
}
