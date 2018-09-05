package com.example.laher.learnfractions.classes;

public class Range {
    private int minimum;
    private int maximum;
    private int range;

    public Range(int minimum, int maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
        fixMinMax();
        int difference = this.maximum - this.minimum;
        this.range = difference+1;
    }

    private void fixMinMax(){
        // this.minimum, this.maximum ** IMPORTANT DETAIL
        if (minimum<1||maximum<1){
            minimum=1;
            maximum=2;
        }
        if (maximum<minimum){
            int min = minimum;
            minimum = maximum;
            maximum = min;
        }
        if (maximum==minimum){
            maximum++;
        }
    }

    public Range() {
        // default
        this.minimum = 1;
        this.maximum = 9;
        setRange();
    }

    public int getRange() {
        if (range<=1){
            range = 2;
        }
        return range;
    }

    private void setRange(){
        fixMinMax();
        int difference = this.maximum - this.minimum;
        this.range = difference+1;
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
        setRange();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Range){
            Range objRange = (Range) obj;
            int objMinimum = objRange.getMinimum();
            int objMaximum = objRange.getMaximum();

            int thisMinimum = getMinimum();
            int thisMaximum = getMaximum();

            return objMinimum==thisMinimum && objMaximum==thisMaximum;
        }
        return super.equals(obj);
    }
}
