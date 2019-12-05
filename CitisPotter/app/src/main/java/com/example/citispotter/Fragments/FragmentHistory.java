package com.example.citispotter.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citispotter.Adapter.HistoryArticleAdapter;
import com.example.citispotter.Model.HistoryClass;
import com.example.citispotter.R;
import com.example.citispotter.sqlLiteDatabase.HistoryDatabaseHelper;

import java.util.ArrayList;

public class FragmentHistory extends Fragment {
    HistoryDatabaseHelper databaseHelper;
    HistoryArticleAdapter adapter;
    private ListView listView;
    private HistoryClass datamodel;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        databaseHelper=new HistoryDatabaseHelper(getActivity());
        recyclerView=(RecyclerView)view.findViewById(R.id.history_recyclerView);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        final ArrayList<HistoryClass> history_list=new ArrayList<>(databaseHelper.getAllData());
        adapter=new HistoryArticleAdapter(history_list);
        recyclerView.setAdapter(adapter);

        return view;
    }


}
