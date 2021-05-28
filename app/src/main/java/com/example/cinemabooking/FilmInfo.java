package com.example.cinemabooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.cinemabooking.Model.Cinema;
import com.example.cinemabooking.Model.Film;
import com.example.cinemabooking.Model.MovieCinemaSchedule;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class FilmInfo extends AppCompatActivity implements MovieCinemaClickListenter , View.OnClickListener, OnLikeListener {
ImageView imageViewFilm;
TextView title;
    String vId;
    CircleImageView circleImageView ;
    Film film;
    LikeButton likeButton;
    private ArrayList<Cinema> cinemaList = new ArrayList<>();
    private ArrayList<MovieCinemaSchedule> movieCinemaSchedules = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_info);

        title =findViewById(R.id.textView2);
        imageViewFilm =findViewById(R.id.imageViewInf);
        circleImageView=(CircleImageView)findViewById(R.id.play_image);
        Intent i = getIntent();
         film = (Film) i.getSerializableExtra("MovieFragment");
        if(film !=null){
        title.setText(film.getName());
        Glide.with(this).asBitmap().load(film.getImageUML()).into(imageViewFilm);

        }
        circleImageView.setOnClickListener(this);
likeButton= (LikeButton)findViewById(R.id.heart_button);
likeButton.setOnLikeListener(this);

        initImageBitmaps();
        extractFilms();

    }


    private void initImageBitmaps(){
        cinemaList.add(
                new Cinema("City Stars Cinema",
                "Omar Ibn Al Khattab Street - City Stars Mall - The 5th floor - Nasr City",
                "https://assets.cairo360.com/app/uploads/2016/07/starscinema-211x211-1482419807.png")
                  );
        cinemaList.add(new Cinema("Cairo Metro Cinema",
                "35 Talaat Harb Street - Downtown ",
                "https://media.elcinema.com/uploads/_310x310_77c85d4c88a249517eb4b6a0787729a6accb6cb28888949b55ed88d52d5b738a.jpg"));
        cinemaList.add(new Cinema("Cosmos Cinema",
                "12 Emad El-Din Street - Downtown",
                "https://www.shorouknews.com/uploadedimages/Gallery/original/1873272.jpg"));


        cinemaList.add(new Cinema("Galaxy Cinema"
                ,"Abdul Aziz Al Saud Street - Manial Al-Rawda",
                "https://media-exp1.licdn.com/dms/image/C560BAQG2JSNNKC-M7g/company-logo_200_200/0/1561753326625?e=2159024400&v=beta&t=TPo293PrmPD7JeJYH4p1DrYkjwhHlCK6B652oI_-NVU"));
        cinemaList.add(new Cinema("Hilton Ramses Cinema"
                ,"The Commercial Annex of Hilton Ramses Building, El-Shaheed Abdel Moneim Riyad Square - Downtown",
                "https://media.filbalad.com/Places/logos/Large/944_hiltonramsis-cinema.png"));





        for (int i=0;i<cinemaList.size();i++)
        {
            Calendar calendar= new GregorianCalendar();
            calendar.set(Calendar.HOUR,(int)(Math.random()*100)%24);
            calendar.set(Calendar.MINUTE,25);
            calendar.set(calendar.DAY_OF_WEEK,i%7);


            movieCinemaSchedules.add(new MovieCinemaSchedule(film,cinemaList.get(i),calendar.getTime(),5,(int)(Math.random()*100)+50));
        }

        intRecyclerView();
    }
    private void intRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.cinema_recycler_view_info_film);
        MovieCinemaScheduleAdapter adapter = new MovieCinemaScheduleAdapter(getApplicationContext(), movieCinemaSchedules,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //GridView
        // recyclerView.setLayoutManager (new GridLayoutManager(getContext(), 3));
    }



    @Override
    public void onClick(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse( "https://youtu.be/"+vId));
        startActivity(browserIntent);
    }

    private void extractFilms() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q="+
                formatUrl(title.getText().toString()+"trailer")
                +"&key=AIzaSyCUk_aWpY0xipuc7WSjLgpFfJquHS8hsJA";



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results =   response.getJSONArray("items");

                                JSONObject filmObj =results.getJSONObject(0);
                                Film film =new Film();
                            JSONObject idObj = filmObj.getJSONObject("id");
                           vId=  idObj.getString("videoId");
                            Log.d("tag"+"25", vId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("tag", "eeedfce");

                    }
                });
        queue.add(jsonObjectRequest);

    }

    private String formatUrl(String s) {
        String x = "";
        String[] a =s.split(" ");
        for (int i = 0; i < a.length; i++) {

          x= x+ a[i];
            if(i !=a.length-1)
                x= x+ "%20";

        }
        return x;
    }


    @Override
    public void movieCinemaOnClickListener(MovieCinemaSchedule movieCinemaSchedule) {
        Intent intent =new Intent(getApplicationContext(),Booking.class);
        intent.putExtra("FilmInfo",movieCinemaSchedule);
        startActivity(intent);
    }

    @Override
    public void liked(LikeButton likeButton) {
        //write to add  to favorite
        Toast.makeText(getApplicationContext(),"added",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void unLiked(LikeButton likeButton) {
//write to move  to favorite
    }
}