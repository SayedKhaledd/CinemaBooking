package com.example.cinemabooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Button button;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new MovieFragment()).commit();
            Log.d("Main Activity:", "on create");

        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        if (item.getItemId() == R.id.page_1) {
            selectedFragment = new MovieFragment();
            Log.d("Main Activity:", "Page 1");
        }
        else if (item.getItemId() == R.id.page_2) {
            selectedFragment = new CinemaFragment();
            Log.d("Main Activity:", "Page 2");
        }
        else if (item.getItemId() == R.id.page_3) {
            selectedFragment = new FavoritesFragment();
            Log.d("Main Activity:", "Page 3");
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account_menu:
               /* Intent intent = new Intent(MainActivity.this, Account.class);
                startActivity(intent);*/
                return true;
            case R.id.search_menu:
                Intent intent2 = new Intent(MainActivity.this, Search.class);
                startActivity(intent2);
                return true;}


        return false;
    }

}