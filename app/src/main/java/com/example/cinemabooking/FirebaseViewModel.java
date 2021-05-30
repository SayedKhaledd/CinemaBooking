package com.example.cinemabooking;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cinemabooking.Model.Cinema;
import com.example.cinemabooking.Model.Film;
import com.example.cinemabooking.Model.MovieCinemaSchedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class FirebaseViewModel extends ViewModel {
    private MutableLiveData<List<Film>> films;
    private MutableLiveData<List<Cinema>> cinemas;
    private MutableLiveData<List<MovieCinemaSchedule>> movieCinemaSchedules;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRoot = database.getReference();
    CountDownLatch latch = new CountDownLatch(1);
    boolean status = false;

    public CountDownLatch getLatch() {
        return latch;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public MutableLiveData<List<Film>> getFilms() throws InterruptedException {
        if (films == null) {
            films = new MutableLiveData<>();
            loadMovies();

        }


        return films;
    }


    public MutableLiveData<List<Cinema>> getCinemas() {
        if (cinemas == null) {
            cinemas = new MutableLiveData<>();
            loadCinemas();

        }

        return cinemas;
    }

    public MutableLiveData<List<MovieCinemaSchedule>> getMovieCinemaSchedules() {
        if (movieCinemaSchedules == null) {
            movieCinemaSchedules = new MutableLiveData<>();
            loadSchedule();
        }


        return movieCinemaSchedules;
    }

    private  void loadMovies() throws InterruptedException {
        // latch.await();
        DatabaseReference databasefilm = databaseRoot.child("Film");
        ArrayList<Film> myFilms = new ArrayList<>();
        databasefilm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Film myFilm = snap.getValue(Film.class);
                    if (myFilm != null) {
                        Log.d("TAG", "my name is : " + myFilm.getName());
                        Log.d("TAG", "my description is : " + myFilm.getDescription());
                        Log.d("TAG", "my imageURL is : " + myFilm.getImageURL());

                        myFilms.add(myFilm);
                    }

                }
                status = true;

                Log.d("TAG", "finished ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        films.setValue(myFilms);

//        latch.countDown();
//        Log.d("TAG", "latch: "+latch.getCount());
    }

    private void loadCinemas() {
    }

    private void loadSchedule() {
    }
}
