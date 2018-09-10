package com.example.laher.learnfractions.model;

public class Student extends BaseModel {
    String teacher_code;
    int age;
    String gender;

    public String getTeacher_code() {
        return teacher_code;
    }

    public void setTeacher_code(String teacher_code) {
        this.teacher_code = teacher_code;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student){
            Student objStudent = (Student) obj;

            String objStudentID = objStudent.getId();
            String thisStudentID = this.getId();

            return thisStudentID.equals(objStudentID);
        }
        return super.equals(obj);
    }

    public Student() {
    }
}
