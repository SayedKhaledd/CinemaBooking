package com.example.cinemabooking;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CinemaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CinemaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String>mImageUrls = new ArrayList<>();
    private ArrayList<String>mAddress = new ArrayList<>();

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
        mImageUrls.add("https://assets.cairo360.com/app/uploads/2016/07/starscinema-211x211-1482419807.png");
        mNames.add("City Stars Cinema");
        mAddress.add("Omar Ibn Al Khattab Street - City Stars Mall - The 5th floor - Nasr City");
        mImageUrls.add("https://media.elcinema.com/uploads/_310x310_77c85d4c88a249517eb4b6a0787729a6accb6cb28888949b55ed88d52d5b738a.jpg");
        mNames.add("Cairo Metro Cinema");
        mAddress.add("35 Talaat Harb Street - Downtown ");
        mImageUrls.add("https://www.shorouknews.com/uploadedimages/Gallery/original/1873272.jpg");
        mNames.add("Cosmos Cinema");
        mAddress.add("12 Emad El-Din Street - Downtown");
        mImageUrls.add("https://media-exp1.licdn.com/dms/image/C560BAQG2JSNNKC-M7g/company-logo_200_200/0/1561753326625?e=2159024400&v=beta&t=TPo293PrmPD7JeJYH4p1DrYkjwhHlCK6B652oI_-NVU");
        mNames.add("Galaxy Cinema");
        mAddress.add("Abdul Aziz Al Saud Street - Manial Al-Rawda");
        mImageUrls.add("https://media.filbalad.com/Places/logos/Large/944_hiltonramsis-cinema.png");
        mNames.add("Hilton Ramses Cinema");
        mAddress.add("The Commercial Annex of Hilton Ramses Building, El-Shaheed Abdel Moneim Riyad Square - Downtown");
        intRecyclerView();
    }
    private void intRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cinema_recycler_view);
        CinemaAdapter adapter = new CinemaAdapter(getContext(), mNames,mImageUrls,mAddress);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //GridView
        //recyclerView.setLayoutManager (new GridLayoutManager(MainActivity.this, 3));
    }
}