package com.example.laher.learnfractions.teacher.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.teacher.list_adapters.StudentListAdapter;

import java.util.ArrayList;

public class StudentListFragment extends Fragment {
    View view;
    ListView listView;
    private ArrayList<Student> students;

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public StudentListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_list_students, container, false);
        listView = view.findViewById(R.id.list_students);

        listView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        listView.setClickable(false);
        listView.setFocusable(false);

        StudentListAdapter adapter = new StudentListAdapter(getActivity(), R.layout.item_student, students);
        listView.setAdapter(adapter);

        return view;
    }


}
