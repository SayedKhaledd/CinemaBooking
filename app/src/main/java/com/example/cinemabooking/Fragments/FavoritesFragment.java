package com.example.cinemabooking.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cinemabooking.Adapters.MoviesAdapter;
import com.example.cinemabooking.Constants;
import com.example.cinemabooking.FilmInfoActivity;
import com.example.cinemabooking.Listeners.FilmOnClickListener;
import com.example.cinemabooking.Model.Film;
import com.example.cinemabooking.Model.UserFavorite;
import com.example.cinemabooking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment implements FilmOnClickListener {

    View view;
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRoot = database.getReference();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Film> filmArrayList = new ArrayList<>();
    ArrayList<Integer> filmIndecies = new ArrayList<>();
    MoviesAdapter adapter;
    private SharedPreferences sharedPreferences;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
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
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        //Button button=view.findViewById(R.id.button_movie);
        //button.setOnClickListener(new View.OnClickListener() {
        recyclerView = (RecyclerView) view.findViewById(R.id.movies_recycler_view);
        sharedPreferences = getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);


        initImageBitmaps();


        return view;
    }

    private void initImageBitmaps() {
        if (sharedPreferences.getString(Constants.EMAIL, "DEFAULT").equals("DEFAULT"))
            Toast.makeText(getContext(), "log out and check remember me first", Toast.LENGTH_SHORT).show();
        else {
            databaseRoot.child("UserFavorite").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot snap : snapshot.getChildren()) {
                        UserFavorite userFavorite = snap.getValue(UserFavorite.class);
                        if (userFavorite.getEmail().equals(sharedPreferences.getString(Constants.EMAIL, "DEFAULT"))) {
                            filmIndecies.add(userFavorite.getMovieId());

                        }


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
            databaseRoot.child("Film").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot snap : snapshot.getChildren()) {
                        if (filmIndecies.contains(Integer.parseInt(snap.getKey()))) {
                            Film film = snap.getValue(Film.class);
                            filmArrayList.add(film);
                        }


                    }
                    adapter.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });


            intRecyclerView();
        }

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
        intent.putExtra("Filmindex", filmArrayList.indexOf(film) + 1);
        Log.d("TAG", "Filmindex in film fragment: " + filmArrayList.indexOf(film) + 1);
        startActivity(intent);
    }


}