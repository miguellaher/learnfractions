package com.example.laher.learnfractions.teacher.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.teacher.list_adapters.StudentListAdapter;

import java.util.ArrayList;

public class StatListFragment extends Fragment {
    View view;
    ListView listView;
    private ArrayAdapter adapter;

    public void setAdapter(ArrayAdapter adapter) {
        this.adapter = adapter;
    }

    public StatListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_list_students, container, false);
        listView = view.findViewById(R.id.list_students);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        return view;
    }


}
