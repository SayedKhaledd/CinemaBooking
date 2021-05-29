package com.example.cinemabooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cinemabooking.Model.Cinema;
import com.example.cinemabooking.Model.Film;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CinemaInfo extends AppCompatActivity implements FilmOnClickListener, View.OnClickListener {


    ImageView ImageViewCinema;
    TextView CinemaName;
    TextView Address;
    private ArrayList<Film> filmList = new ArrayList<>();

    Cinema cinema;
    CircleImageView circleImageLocation;

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
        initImageBitmaps();
    }

    private void initImageBitmaps() {


        filmList.add(new Film("The shawshank redemption",
                "https://images-na.ssl-images-amazon.com/images/I/519NBNHX5BL._SY445_.jpg"));

        filmList.add(new Film("JOKER"
                , "https://m.media-amazon.com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg"));
        filmList.add(new Film("Grave of the fireflies",
                "https://www.reelviews.net/resources/img/posters/thumbs/grave_poster.jpg"));


        filmList.add(new Film("Avengers Endgame",
                "https://images-na.ssl-images-amazon.com/images/I/71niXI3lxlL._AC_SY679_.jpg"));


        filmList.add(new Film("Violet Evergarden", "https://i.redd.it/pkk9guou7ot41.jpg"));

        filmList.add(new Film("Lord of the rings",
                "https://images-na.ssl-images-amazon.com/images/I/51uKITEiT1L._AC_.jpg"));


        filmList.add(new Film("Inception", "https://movieposters2.com/images/704089-b.jpg"));
        intRecyclerView();
    }

    private void intRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.film_recycler_view_info_cinema);
        MoviesAdapter adapter = new MoviesAdapter(getApplicationContext(), filmList, this);
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //GridView
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
    }


    @Override
    public void filmAddOnClickListener(Film film) {
        Intent intent = new Intent(getApplicationContext(), FilmInfo.class);
        intent.putExtra("MovieFragment", film);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Uri gmmIntentUri = Uri.parse("geo:" + cinema.getLatitude() + "," + cinema.getLongitude());
        Intent intent = new Intent((Intent.ACTION_VIEW), gmmIntentUri);

        startActivity(intent);
    }
}