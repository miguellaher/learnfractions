package com.example.laher.learnfractions.model;

public class ExerciseStat extends Exercise {
    private Student student;
    private int students_answered;

    public int getStudents_answered() {
        return students_answered;
    }

    public void setStudents_answered(int students_answered) {
        this.students_answered = students_answered;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ExerciseStat(String title, int i, int i1, boolean b, int i2, boolean b1) {
        super(title,i,i1,b,i2,b1);
    }

    public ExerciseStat(String title, int i, int i1) {
        super(title,i,i1);
    }

    public ExerciseStat() {

    }

    public void incrementError(){
        int error = getErrors();
        error++;
        setErrors(error);
    }
}
