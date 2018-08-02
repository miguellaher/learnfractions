package com.example.laher.learnfractions.model;

public class ExamStat extends ChapterExam {
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public long getTimeSpent() {
        return super.getTimeSpent() / 1000;
    }
}
