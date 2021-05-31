package com.example.cinemabooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.cinemabooking.Adapters.MovieCinemaScheduleAdapter;
import com.example.cinemabooking.Listeners.MovieCinemaClickListenter;
import com.example.cinemabooking.Model.Cinema;
import com.example.cinemabooking.Model.Film;
import com.example.cinemabooking.Model.MovieCinemaSchedule;
import com.example.cinemabooking.Model.UserFavorite;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class FilmInfoActivity extends AppCompatActivity implements MovieCinemaClickListenter, View.OnClickListener, OnLikeListener {
    ImageView imageViewFilm;
    TextView title;
    String vId;
    CircleImageView circleImageView;
    Film film;
    LikeButton likeButton;
    private ArrayList<MovieCinemaSchedule> movieCinemaSchedules = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myDatabase = database.getReference();
    ArrayList<Integer> cinemaIndecies = new ArrayList<>();
    RecyclerView recyclerView;
    MovieCinemaScheduleAdapter adapter;
    int current = 0;
    private SharedPreferences sharedPreferences;
    int filmIndex;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_info);

        title = findViewById(R.id.textView2);
        imageViewFilm = findViewById(R.id.imageViewInf);
        circleImageView = (CircleImageView) findViewById(R.id.play_image);
        sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);

        Intent i = getIntent();
        film = (Film) i.getSerializableExtra("MovieFragment");
        if (film != null) {
            title.setText(film.getName());
            Glide.with(this).asBitmap().load(film.getImageURL()).into(imageViewFilm);

        }

        filmIndex = (int) (i.getSerializableExtra("Filmindex"));
        Log.d("TAG", "filmIndex: " + filmIndex);
        circleImageView.setOnClickListener(this);
        likeButton = (LikeButton) findViewById(R.id.heart_button);
        likeButton.setOnLikeListener(this);

        initImageBitmaps();
        extractFilms();

    }


    private void initImageBitmaps() {
        myDatabase.child("MovieCinemaSchedule").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {
                    MovieCinemaSchedule mySchedule = snap.getValue(MovieCinemaSchedule.class);
                    if (mySchedule != null && mySchedule.getFilmId() == filmIndex) {
                        cinemaIndecies.add(mySchedule.getCinemaId());
                        movieCinemaSchedules.add(mySchedule);
                    }


                }

                Log.d("TAG", "finished ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myDatabase.child("Cinema").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 0;
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if (cinemaIndecies.contains(Integer.parseInt(snap.getKey()))) {
                        Cinema mycinema = snap.getValue(Cinema.class);

                        movieCinemaSchedules.get(i).setCinema(mycinema);
                        i++;
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


        recyclerView = findViewById(R.id.cinema_recycler_view_info_film);
        adapter = new MovieCinemaScheduleAdapter(getApplicationContext(), movieCinemaSchedules, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //GridView
        // recyclerView.setLayoutManager (new GridLayoutManager(getContext(), 3));
    }


    @Override
    public void onClick(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse("https://youtu.be/" + vId));
        startActivity(browserIntent);
    }

    private void extractFilms() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" +
                formatUrl(title.getText().toString() + "trailer")
                + "&key=AIzaSyCUk_aWpY0xipuc7WSjLgpFfJquHS8hsJA";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("items");

                            JSONObject filmObj = results.getJSONObject(0);
                            Film film = new Film();
                            JSONObject idObj = filmObj.getJSONObject("id");
                            vId = idObj.getString("videoId");
                            Log.d("tag" + "25", vId);
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
        String[] a = s.split(" ");
        for (int i = 0; i < a.length; i++) {

            x = x + a[i];
            if (i != a.length - 1)
                x = x + "%20";

        }
        return x;
    }


    @Override
    public void movieCinemaOnClickListener(MovieCinemaSchedule movieCinemaSchedule) {
        Intent intent = new Intent(getApplicationContext(), BookingActivity.class);
        intent.putExtra("FilmInfo", movieCinemaSchedule);
        startActivity(intent);
    }

    @Override
    public void liked(LikeButton likeButton) {
        if (sharedPreferences.getString(Constants.EMAIL, "DEFAULT") != null && !sharedPreferences.getString(Constants.EMAIL, "DEFAULT").equals("DEFAULT")) {
            Log.d("TAG", "liked: " + "entereed ");
            myDatabase.child("UserFavorite").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean check = false;
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        UserFavorite userFavorite = snap.getValue(UserFavorite.class);
                        if (userFavorite.getEmail().equals(sharedPreferences.getString(Constants.EMAIL, "DEFAULT"))) {
                            if (userFavorite.getMovieId() == filmIndex) {
                                Toast.makeText(getApplicationContext(), "already added", Toast.LENGTH_SHORT).show();

                                check = true;
                            }


                        }


                    }
                    if (!check) {
                        UserFavorite userFavorite = new UserFavorite(filmIndex, sharedPreferences.getString(Constants.EMAIL, "DEFAULT"));
                        myDatabase.child("UserFavorite").child((snapshot.getChildrenCount() + 1) + "").setValue(userFavorite);
                        Toast.makeText(getApplicationContext(), "added", Toast.LENGTH_SHORT).show();


                    }


                    Log.d("TAG", "finished ");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            Log.d("TAG", "liked: " + sharedPreferences.getString(Constants.EMAIL, "DEFAULT"));
            Toast.makeText(getApplicationContext(), "not added, no email", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void unLiked(LikeButton likeButton) {
//write to move  to favorite
        if (sharedPreferences.getString(Constants.EMAIL, "DEFAULT") != null && !sharedPreferences.getString(Constants.EMAIL, "DEFAULT").equals("DEFAULT")) {
            myDatabase.child("UserFavorite").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int i = 1;
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        UserFavorite userFavorite = snap.getValue(UserFavorite.class);

                        if (userFavorite.getEmail().equals(sharedPreferences.getString(Constants.EMAIL, "DEFAULT"))) {
                            if (userFavorite.getMovieId() == filmIndex) {
                                myDatabase.child("UserFavorite").child(i + "").removeValue();

                            }


                        }

                        i++;
                    }


                    Log.d("TAG", "finished ");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Toast.makeText(getApplicationContext(), "deleted", Toast.LENGTH_SHORT).show();
        }
    }
}