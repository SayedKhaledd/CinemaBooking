package com.example.cinemabooking;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemabooking.Adapters.MoviesAdapter;
import com.example.cinemabooking.Listeners.FilmOnClickListener;
import com.example.cinemabooking.Model.Film;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements TextWatcher, FilmOnClickListener {
    private final int VOICE_REQUEST = 1999;
    EditText searchText;
    private ArrayList<Film> list = new ArrayList<>();
    private ArrayList<Film> films = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRoot = database.getReference();
    int id;
    MoviesAdapter filmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFilms();

        filmAdapter = new MoviesAdapter(this,films ,this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        searchText = findViewById(R.id.search_edit_text);
        searchText.addTextChangedListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(filmAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.voice:
                id = R.id.voice;
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent, VOICE_REQUEST);
                return true;

        }


        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VOICE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null) {
                    searchText.setText(result.get(0));

                }
            }
        }


    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        showProduct();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void showProduct() {

        String key = searchText.getText().toString();
        if (!key.isEmpty()) {
            films.clear();
            Log.d("key", key);
            int i = 0;
            while (list.size() != i) {

                if (list.get(i).getName().toLowerCase().contains(key.toLowerCase()) || list.get(i).getDescription().toLowerCase().contains(key.toLowerCase())) {
                    Film filmT= new Film(list.get(i).getName(), list.get(i).getDescription());
                    filmT.setImageURL(list.get(i).getImageURL());
                    films.add(filmT);

                }


                i++;
            }
            Toast.makeText(getApplicationContext(), "yes", Toast.LENGTH_LONG);
            filmAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "no", Toast.LENGTH_LONG);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void getFilms() {
        databaseRoot.child("Film").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Film myFilm = snap.getValue(Film.class);
                    list.add(myFilm);

                }


                Log.d("TAG", "notified ");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    @Override
    public void filmAddOnClickListener(Film film) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}