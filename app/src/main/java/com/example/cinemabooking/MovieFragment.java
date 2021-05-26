package com.example.cinemabooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String>mImageUrls = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieFragment newInstance(String param1, String param2) {
        MovieFragment fragment = new MovieFragment();
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
        view =inflater.inflate(R.layout.fragment_movie, container, false);
        //Button button=view.findViewById(R.id.button_movie);
        //button.setOnClickListener(new View.OnClickListener() {
        initImageBitmaps();



        return view;
    }
    private void initImageBitmaps(){
        mImageUrls.add("https://images-na.ssl-images-amazon.com/images/I/519NBNHX5BL._SY445_.jpg");
        mNames.add("The shawshank redemption");
        mImageUrls.add("https://m.media-amazon.com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg");
        mNames.add("JOKER");
        mImageUrls.add("https://www.reelviews.net/resources/img/posters/thumbs/grave_poster.jpg");
        mNames.add("Grave of the fireflies");
        mImageUrls.add("https://images-na.ssl-images-amazon.com/images/I/71niXI3lxlL._AC_SY679_.jpg");
        mNames.add("Avengers Endgame");
        mImageUrls.add("https://i.redd.it/pkk9guou7ot41.jpg");
        mNames.add("Violet Evergarden");
        mImageUrls.add("https://images-na.ssl-images-amazon.com/images/I/51uKITEiT1L._AC_.jpg");
        mNames.add("Lord of the rings");
        mImageUrls.add("https://movieposters2.com/images/704089-b.jpg");
        mNames.add("Inception");
        intRecyclerView();
    }
    private void intRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.movies_recycler_view);
        MoviesAdapter adapter = new MoviesAdapter(getContext(), mNames,mImageUrls);
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //GridView
        recyclerView.setLayoutManager (new GridLayoutManager(getContext(), 3));
    }
}