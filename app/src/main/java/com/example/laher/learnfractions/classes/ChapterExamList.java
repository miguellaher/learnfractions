package com.example.laher.learnfractions.classes;

import com.example.laher.learnfractions.model.ChapterExam;

import java.util.ArrayList;

public class ChapterExamList extends ArrayList<ChapterExam> {

    @Override
    public boolean add(ChapterExam chapterExam) {
        int size = size();
        chapterExam.setExamNumber(size);
        return super.add(chapterExam);
    }
}
