package com.example.laher.learnfractions.teacher.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.teacher.list_adapters.StudentListAdapter;

import java.util.ArrayList;

public class StatOptionsFragment extends Fragment {
    View view;
    Button btnExercises;
    Button btnSeatworks;
    Button btnExams;

    public StatOptionsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_list_options, container, false);
        btnExercises = view.findViewById(R.id.list_options_btnExercises);
        btnSeatworks = view.findViewById(R.id.list_options_btnSeatworks);
        btnExams = view.findViewById(R.id.list_options_btnExams);

        return view;
    }


}
