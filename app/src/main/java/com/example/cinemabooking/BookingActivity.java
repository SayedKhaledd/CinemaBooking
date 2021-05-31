package com.example.cinemabooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cinemabooking.Email.GmailSender;
import com.example.cinemabooking.Model.Film;
import com.example.cinemabooking.Model.MovieCinemaSchedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {
    TextView price, cinemaName, cinemaAddress, numOfSeats, counter, totalPrice, movieName;
    Button plus, mins, booking;
    ImageView movieImage;
    CircleImageView circleImageCinema;
    MovieCinemaSchedule movieCinemaSchedule;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myDatabase = database.getReference();
    Film myFilm;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        price = findViewById(R.id.price_booking);
        cinemaName = findViewById(R.id.cinema_name_booking);
        cinemaAddress = findViewById(R.id.address_booking);
        numOfSeats = findViewById(R.id.num_of_seats_booking);
        counter = findViewById(R.id.counter);
        totalPrice = findViewById(R.id.totalprice);
        movieName = findViewById(R.id.moviee_name_booking);
        counter.setText("1");
        plus = findViewById(R.id.plus);
        mins = findViewById(R.id.minus);
        booking = findViewById(R.id.booking_button);
        movieImage = findViewById(R.id.image_movie);
        circleImageCinema = findViewById(R.id.image_cinema);
        sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);

        Intent i = getIntent();

        movieCinemaSchedule = (MovieCinemaSchedule) i.getSerializableExtra("FilmInfo");

        price.setText(movieCinemaSchedule.getPrice() + "L.E");
        cinemaName.setText(movieCinemaSchedule.getCinema().getName());
        cinemaAddress.setText(movieCinemaSchedule.getCinema().getAddress());
        numOfSeats.setText(movieCinemaSchedule.getNumberofseats() + "");
        myDatabase.child("Film").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {
                    if (movieCinemaSchedule.getFilmId() == Integer.parseInt(snap.getKey())) {
                        myFilm = snap.getValue(Film.class);
                        movieName.setText(myFilm.getName());
                        Glide.with(getApplication()).asBitmap().load(myFilm.getImageURL()).into(movieImage);
                        Glide.with(getApplication()).asBitmap().load(movieCinemaSchedule.getCinema().getImage()).into(circleImageCinema);
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        plus.setOnClickListener(this);
        mins.setOnClickListener(this);
        booking.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int x = Integer.parseInt(counter.getText().toString());

        if (v.getId() == R.id.plus) {
            if (x == movieCinemaSchedule.getNumberofseats()) {
                Toast.makeText(getApplicationContext(), "max number is " + movieCinemaSchedule.getNumberofseats(), Toast.LENGTH_SHORT).show();
                return;
            }
            counter.setText((x + 1) + "");

        } else if (v.getId() == R.id.minus) {
            if (x == 1) {
                Toast.makeText(getApplicationContext(), "min number is 1", Toast.LENGTH_SHORT).show();
                return;
            }

            counter.setText((x - 1) + "");

        } else if (v.getId() == R.id.booking_button) {
            myDatabase.child("MovieCinemaSchedule").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int i = 1;
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        MovieCinemaSchedule my = snap.getValue(MovieCinemaSchedule.class);
                        if (movieCinemaSchedule.getFilmId() == my.getFilmId() && my.getCinemaId() == movieCinemaSchedule.getCinemaId()) {
                            Log.d("TAG", "found it : " + i);
                            myDatabase.child("MovieCinemaSchedule")
                                    .child(i + "")
                                    .child("numberofseats")
                                    .setValue(movieCinemaSchedule.getNumberofseats() - Integer.parseInt(counter.getText() + ""));
                        }

                        i++;
                    }
                    GmailSender sender = new GmailSender("onlineshoppingzeroteam@gmail.com", "sdsd12345");
                    try {
                        sender.sendMail("Tickets paid",
                                "you have bought \n" + counter.getText() + "for the movie" + myFilm.getName(),
                                "onlineshoppingzeroteam@gmail.com", sharedPreferences.getString(Constants.EMAIL, "DEFAULTY")
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    finish();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });




            Toast.makeText(getApplicationContext(), "email with confirm will \n send soon", Toast.LENGTH_LONG).show();
            // movieCinemaSchedule.setNumOfEmptySeats(movieCinemaSchedule.getNumOfEmptySeats()-x);


        }

        updateTotal();
    }

    private void updateTotal() {
        int x = Integer.parseInt(counter.getText().toString());
        totalPrice.setText("Total Price : " + (x * movieCinemaSchedule.getPrice()));

    }
}