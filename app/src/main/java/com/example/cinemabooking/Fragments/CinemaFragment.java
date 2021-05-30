package com.example.cinemabooking.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinemabooking.CinemaAdapter;
import com.example.cinemabooking.CinemaInfo;
import com.example.cinemabooking.CinemaOnclicklistener;
import com.example.cinemabooking.Model.Cinema;
import com.example.cinemabooking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    RecyclerView recyclerView;
    CinemaAdapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseCinema = database.getReference().child("Cinema");

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

    private void initImageBitmaps() {
        databaseCinema.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Cinema myCinema = snap.getValue(Cinema.class);
                    if (myCinema != null) {
                        Log.d("TAG", "my name is : " + myCinema.getName());


                        cinemaList.add(myCinema);
                        adapter.notifyDataSetChanged();
                    }

                }

                Log.d("TAG", "finished ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        intRecyclerView();
    }

    private void intRecyclerView() {

        recyclerView = (RecyclerView) view.findViewById(R.id.cinema_recycler_view);
        adapter = new CinemaAdapter(getContext(), cinemaList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //GridView
        // recyclerView.setLayoutManager (new GridLayoutManager(getContext(), 3));
    }

    @Override
    public void cinemaOnClickListener(Cinema cinema) {
        Intent intent = new Intent(getContext(), CinemaInfo.class);
        intent.putExtra("CinemaFragment", cinema);
        startActivity(intent);
    }
}