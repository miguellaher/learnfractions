package com.example.laher.learnfractions.classes;

public class Range {
    private int minimum;
    private int maximum;
    private int range;

    public Range(int minimum, int maximum) {
        if (maximum<minimum){
            int min = minimum;
            minimum = maximum;
            maximum = min;
        }
        if (maximum==minimum){
            maximum++;
        }
        this.minimum = minimum;
        this.maximum = maximum;
        int difference = maximum - minimum;
        this.range = difference+1;
    }

    public int getRange() {
        return range;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }
}
