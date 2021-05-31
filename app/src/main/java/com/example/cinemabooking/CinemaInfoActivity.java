package com.example.cinemabooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cinemabooking.Adapters.MoviesAdapter;
import com.example.cinemabooking.Listeners.FilmOnClickListener;
import com.example.cinemabooking.Model.Cinema;
import com.example.cinemabooking.Model.Film;
import com.example.cinemabooking.Model.MovieCinemaSchedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CinemaInfoActivity extends AppCompatActivity implements FilmOnClickListener, View.OnClickListener {


    ImageView ImageViewCinema;
    TextView CinemaName;
    TextView Address;
    private ArrayList<Film> filmList = new ArrayList<>();
    ArrayList<Integer> filmsIndecies = new ArrayList<>();

    RecyclerView recyclerView;
    MoviesAdapter adapter;
    Cinema cinema;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myDatabase = database.getReference();
    CircleImageView circleImageLocation;
    int cinemaIndex;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_info);
        ImageViewCinema = findViewById(R.id.image_cinema);
        CinemaName = findViewById(R.id.cinema_name);
        Address = findViewById(R.id.address);
        circleImageLocation = findViewById(R.id.location_button);
        circleImageLocation.setOnClickListener(this);
        Intent i = getIntent();


        cinema = (Cinema) i.getSerializableExtra("CinemaFragment");

        if (cinema != null) {
            CinemaName.setText(cinema.getName());
            Address.setText(cinema.getAddress());
            Glide.with(this).asBitmap().load(cinema.getImage()).into(ImageViewCinema);
        }
        cinemaIndex = (int) i.getSerializableExtra("cinemaindex");

        initImageBitmaps();


    }

    private void initImageBitmaps() {
        myDatabase.child("MovieCinemaSchedule").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {
                    MovieCinemaSchedule mySchedule = snap.getValue(MovieCinemaSchedule.class);
                    if (mySchedule != null && mySchedule.getCinemaId() == cinemaIndex) {
                        filmsIndecies.add(mySchedule.getFilmId());
                    }


                }

                Log.d("TAG", "finished ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myDatabase.child("Film").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if (filmsIndecies.contains(Integer.parseInt(snap.getKey()))) {
                        Film myfilm = snap.getValue(Film.class);
                        filmList.add(myfilm);
                    }
                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        intRecyclerView();
    }

    private void intRecyclerView() {

        recyclerView = findViewById(R.id.film_recycler_view_info_cinema);
        adapter = new MoviesAdapter(getApplicationContext(), filmList, this);
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //GridView
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
    }


    @Override
    public void filmAddOnClickListener(Film film) {
        Intent intent = new Intent(getApplicationContext(), FilmInfoActivity.class);
        intent.putExtra("MovieFragment", film);
        intent.putExtra("Filmindex", filmList.indexOf(film) + 1);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Uri gmmIntentUri = Uri.parse("geo:" + cinema.getLatitude() + "," + cinema.getLongitude());
        Intent intent = new Intent((Intent.ACTION_VIEW), gmmIntentUri);

        startActivity(intent);
    }
}