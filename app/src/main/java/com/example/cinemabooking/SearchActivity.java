package com.example.cinemabooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.cinemabooking.Adapters.FilmAdapter;
import com.example.cinemabooking.Model.Film;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements TextWatcher {
    private final int VOICE_REQUEST = 1999;
    EditText searchText;
    private java.util.List<Film> list;
    private java.util.List<Film> films;

    int id;
    FilmAdapter filmAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<>();
        films = new ArrayList<>();
        list.add(new Film("spider- man","spider"));
        list.add(new Film("super- man","super human"));
        list.add(new Film("bat- man","bat"));
         filmAdapter =new FilmAdapter(films,this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        searchText = findViewById(R.id.search_edit_text);
        searchText.addTextChangedListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(filmAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;    }


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
                Log.d("key",key);
                int i = 0;
                while ( list.size()!=i) {

                    if (    list.get(i).getName().contains(key) || list.get(i).getDescription().contains(key)   ) {

                        films.add(new Film(list.get(i).getName(),list.get(i).getDescription()));

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
}