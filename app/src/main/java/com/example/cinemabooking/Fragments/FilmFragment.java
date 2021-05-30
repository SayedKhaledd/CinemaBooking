package com.example.cinemabooking.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinemabooking.FilmInfoActivity;
import com.example.cinemabooking.FilmOnClickListener;
import com.example.cinemabooking.Model.Film;
import com.example.cinemabooking.MoviesAdapter;
import com.example.cinemabooking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmFragment extends Fragment implements FilmOnClickListener {
    View view;
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRoot = database.getReference();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Film> filmArrayList = new ArrayList<>();
    MoviesAdapter adapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FilmFragment() {
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
    public static FilmFragment newInstance(String param1, String param2) {
        FilmFragment fragment = new FilmFragment();
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
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        //Button button=view.findViewById(R.id.button_movie);
        //button.setOnClickListener(new View.OnClickListener() {
        recyclerView = (RecyclerView) view.findViewById(R.id.movies_recycler_view);


            initImageBitmaps();



        return view;
    }

    private void initImageBitmaps()  {

        databaseRoot.child("Film").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Film myFilm = snap.getValue(Film.class);
                    if (myFilm != null) {
                        Log.d("TAG", "my name is : " + myFilm.getName());
                        Log.d("TAG", "my description is : " + myFilm.getDescription());
                        Log.d("TAG", "my imageURL is : " + myFilm.getImageURL());

                        filmArrayList.add(myFilm);
                    }

                }

                Log.d("TAG", "finished ");
                adapter.notifyDataSetChanged();
                Log.d("TAG", "notified ");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        intRecyclerView();



    }

    private void intRecyclerView() {

        for (int i = 0; i < filmArrayList.size(); i++) {
            Log.d("TAG", "intRecyclerView: " + filmArrayList.get(i).getName());
            Log.d("TAG", "intRecyclerView: " + filmArrayList.get(i).getImageURL());
            Log.d("TAG", "intRecyclerView: " + filmArrayList.get(i).getDescription());

        }
        Log.d("TAG", "intRecyclerView: size is  " + filmArrayList.size());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.movies_recycler_view);
        adapter = new MoviesAdapter(getContext(), filmArrayList, this);
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //GridView
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    @Override
    public void filmAddOnClickListener(Film film) {

        Intent intent = new Intent(getContext(), FilmInfoActivity.class);
        intent.putExtra("MovieFragment", film);
        startActivity(intent);
    }
}