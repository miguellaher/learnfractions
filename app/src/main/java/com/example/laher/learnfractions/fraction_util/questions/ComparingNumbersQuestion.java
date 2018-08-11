package com.example.laher.learnfractions.fraction_util.questions;

public class ComparingNumbersQuestion {
    private int number1;
    private int number2;

    public int getAnswer() {
        if (this.number1>this.number2){
            return this.number1;
        } else if (this.number1<this.number2){
            return this.number2;
        } else {
            return -1;
        }
    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    public ComparingNumbersQuestion() {
        generateNumbers();
    }

    private void generateNumbers(){
        int number1 = (int) (Math.random() * 9 + 1);
        int number2 = (int) (Math.random() * 9 + 1);
        setNumber1(number1);
        setNumber2(number2);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComparingNumbersQuestion){
            ComparingNumbersQuestion comparingNumbersQuestion = (ComparingNumbersQuestion) obj;
            if (comparingNumbersQuestion.getNumber1()==this.getNumber1() &&
                    comparingNumbersQuestion.getNumber2()==this.getNumber2()){
                return true;
            } else {
                return false;
            }
        }
        return super.equals(obj);
    }
}
