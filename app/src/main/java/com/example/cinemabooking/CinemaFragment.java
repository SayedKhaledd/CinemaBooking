package com.example.cinemabooking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinemabooking.Model.Cinema;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CinemaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CinemaFragment extends Fragment implements CinemaOnclicklistener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Cinema> cinemaList = new ArrayList<>();

    public CinemaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CinemaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CinemaFragment newInstance(String param1, String param2) {
        CinemaFragment fragment = new CinemaFragment();
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
        view = inflater.inflate(R.layout.fragment_cinema, container, false);


        initImageBitmaps();
        return view;
    }
    private void initImageBitmaps(){
        cinemaList.add(new Cinema("City Stars Cinema",
                "Omar Ibn Al Khattab Street - City Stars Mall - The 5th floor - Nasr City",
                "https://assets.cairo360.com/app/uploads/2016/07/starscinema-211x211-1482419807.png"));
        cinemaList.add(new Cinema("Cairo Metro Cinema",
                "35 Talaat Harb Street - Downtown ",
                "https://media.elcinema.com/uploads/_310x310_77c85d4c88a249517eb4b6a0787729a6accb6cb28888949b55ed88d52d5b738a.jpg"));
        cinemaList.add(new Cinema("Cosmos Cinema",
                "12 Emad El-Din Street - Downtown",
                "https://www.shorouknews.com/uploadedimages/Gallery/original/1873272.jpg"));


        cinemaList.add(new Cinema("Galaxy Cinema"
                ,"Abdul Aziz Al Saud Street - Manial Al-Rawda",
                "https://media-exp1.licdn.com/dms/image/C560BAQG2JSNNKC-M7g/company-logo_200_200/0/1561753326625?e=2159024400&v=beta&t=TPo293PrmPD7JeJYH4p1DrYkjwhHlCK6B652oI_-NVU"));
        cinemaList.add(new Cinema("Hilton Ramses Cinema"
                ,"The Commercial Annex of Hilton Ramses Building, El-Shaheed Abdel Moneim Riyad Square - Downtown",
                "https://media.filbalad.com/Places/logos/Large/944_hiltonramsis-cinema.png"));
        cinemaList.get(0).setGeo(30.07423037801673,31.343892538623045);
        cinemaList.get(1).setGeo(30.051745465669406, 31.24137311662314);
        cinemaList.get(2).setGeo(30.05504588664474, 31.244142204180953);
        cinemaList.get(3).setGeo(30.018237788549214, 31.223409044657256);
        cinemaList.get(4).setGeo(30.0514063078594, 31.23271481926267);


        intRecyclerView();
    }
    private void intRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cinema_recycler_view);
        CinemaAdapter adapter = new CinemaAdapter(getContext(), cinemaList,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //GridView
       // recyclerView.setLayoutManager (new GridLayoutManager(getContext(), 3));
    }

    @Override
    public void cinemaOnClickListener(Cinema cinema) {
        Intent intent =new Intent(getContext(),CinemaInfo.class);
        intent.putExtra("CinemaFragment",cinema);
        startActivity(intent);
    }
}