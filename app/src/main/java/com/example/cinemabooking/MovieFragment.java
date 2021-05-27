package com.example.cinemabooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cinemabooking.Model.Film;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment implements FilmOnClickListener {
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Film> filmArrayList = new ArrayList<>();


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



        filmArrayList.add(new Film("The shawshank redemption",
                "https://images-na.ssl-images-amazon.com/images/I/519NBNHX5BL._SY445_.jpg"));

        filmArrayList.add(new Film("JOKER"
                ,"https://m.media-amazon.com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg"));
        filmArrayList.add(new Film("Grave of the fireflies",
                "https://www.reelviews.net/resources/img/posters/thumbs/grave_poster.jpg"));


        filmArrayList.add(new Film("Avengers Endgame" ,
                "https://images-na.ssl-images-amazon.com/images/I/71niXI3lxlL._AC_SY679_.jpg"));


        filmArrayList.add(new Film("Violet Evergarden","https://i.redd.it/pkk9guou7ot41.jpg"));

        filmArrayList.add(new Film("Lord of the rings",
                "https://images-na.ssl-images-amazon.com/images/I/51uKITEiT1L._AC_.jpg"));


        filmArrayList.add(new Film("Inception","https://movieposters2.com/images/704089-b.jpg"));
        intRecyclerView();
    }
    private void intRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.movies_recycler_view);
        MoviesAdapter adapter = new MoviesAdapter(getContext(), filmArrayList,this);
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //GridView
        recyclerView.setLayoutManager (new GridLayoutManager(getContext(), 3));
    }

    @Override
    public void filmAddOnClickListener(Film film) {

        Intent intent =new Intent(getContext(),FilmInfo.class);
        intent.putExtra("MovieFragment",film);
        startActivity(intent);
    }
}