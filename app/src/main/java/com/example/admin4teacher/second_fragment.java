package com.example.admin4teacher;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link second_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class second_fragment extends Fragment implements SearchView.OnQueryTextListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<ListElement> elements;
    private SearchView svSearch;
    ListAdapter listAdapter;
    RecyclerView recyclerView;

    public second_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment second_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static second_fragment newInstance(String param1, String param2) {
        second_fragment fragment = new second_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_second_fragment, container, false);
        //Tarjetas, recycler vie

        svSearch = (SearchView) root.findViewById(R.id.busqueda);
        recyclerView = root.findViewById(R.id.layout_tarjetas);;

        initListener();
        return root;
    }

    private void initListener(){svSearch.setOnQueryTextListener(this);}

    @Override
    public boolean onQueryTextSubmit(String query) {return false;}

    @Override
    public boolean onQueryTextChange(String newText) {
        //listAdapter.filtro(newText);
        return false;
    }
    }
