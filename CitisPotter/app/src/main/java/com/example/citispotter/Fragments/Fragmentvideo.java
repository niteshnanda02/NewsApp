package com.example.citispotter.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.citispotter.R;

public class Fragmentvideo extends Fragment {
    public Fragmentvideo(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_video,container,false);
        String[] menu={"Description1","Description2","Description3","Description4","Description5","Description6"};
        ListView listView=view.findViewById(R.id.videoList);
        ArrayAdapter<String> listViewAdapter=new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menu
        );
        listView.setAdapter(listViewAdapter);
        return view;
    }
}
