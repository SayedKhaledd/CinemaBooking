package com.example.cinemabooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.cinemabooking.Model.Cinema;
import com.example.cinemabooking.Model.Film;

public class CinemaInfo extends AppCompatActivity implements CinemaOnclicklistener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_info);

        Intent i = getIntent();
        Cinema cinema = (Cinema) i.getSerializableExtra("MovieFragment");

        if(cinema !=null){
            //nothing
            //write what you want do
        }
    }

    @Override
    public void cinemaOnClickListener(Cinema cinema) {

    }
}