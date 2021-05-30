package com.example.cinemabooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cinemabooking.Model.MovieCinemaSchedule;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {
    TextView price, cinemaName, cinemaAddress, numOfSeats, counter, totalPrice, movieName;
    Button plus, mins, booking;
    ImageView movieImage;
    CircleImageView circleImageCinema;
    MovieCinemaSchedule movieCinemaSchedule;

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

        Intent i = getIntent();

         movieCinemaSchedule = (MovieCinemaSchedule) i.getSerializableExtra("FilmInfo");

        price.setText(movieCinemaSchedule.getPrice() + "L.E");
        cinemaName.setText(movieCinemaSchedule.getCinema().getName());
        cinemaAddress.setText(movieCinemaSchedule.getCinema().getAddress());
        numOfSeats.setText(movieCinemaSchedule.getNumberofseats()+"");
//        movieName.setText(movieCinemaSchedule.getMovie().getName());
//        Glide.with(this).asBitmap().load(movieCinemaSchedule.getMovie().getImageURL()).into(movieImage);
//        Glide.with(this).asBitmap().load(movieCinemaSchedule.getCinema().getImage()).into(circleImageCinema);

        plus.setOnClickListener(this);
        mins.setOnClickListener(this);
        booking.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int x = Integer.parseInt(counter.getText().toString());

        if (v.getId() == R.id.plus) {
            if(x==movieCinemaSchedule.getNumberofseats())
            { Toast.makeText(getApplicationContext(),"max number is "+movieCinemaSchedule.getNumberofseats(),Toast.LENGTH_SHORT).show();
                return;}
            counter.setText((x+1)+"");

        }else if (v.getId() == R.id.minus){
            if(x==1)
            { Toast.makeText(getApplicationContext(),"min number is 1",Toast.LENGTH_SHORT).show();
                return;}

                counter.setText((x-1)+"");

        }else if (v.getId() == R.id.booking_button){
            Toast.makeText(getApplicationContext(),"email with confirm will \n send soon",Toast.LENGTH_LONG).show();
           // movieCinemaSchedule.setNumOfEmptySeats(movieCinemaSchedule.getNumOfEmptySeats()-x);

            Intent intent =new Intent(this,MainActivity.class);
            startActivity(intent);
        }

        updateTotal();
    }

    private void updateTotal() {
        int x = Integer.parseInt(counter.getText().toString());
        totalPrice.setText("Total Price : "+(x*movieCinemaSchedule.getPrice()));

    }
}