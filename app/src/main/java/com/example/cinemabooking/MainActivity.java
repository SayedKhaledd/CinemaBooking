package com.example.cinemabooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.cinemabooking.Fragments.CinemaFragment;
import com.example.cinemabooking.Fragments.FavoritesFragment;
import com.example.cinemabooking.Fragments.FilmFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Button button;
    Dialog dialog;
    BottomNavigationView bottomNavigationView;
    private SharedPreferences sharedPreferences;

    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new FilmFragment()).commit();
            Log.d("Main Activity:", "on create");

        }

        dialog = new Dialog(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        if (item.getItemId() == R.id.page_1) {
            selectedFragment = new FilmFragment();
            Log.d("Main Activity:", "Page 1");
        } else if (item.getItemId() == R.id.page_2) {
            selectedFragment = new CinemaFragment();
            Log.d("Main Activity:", "Page 2");
        } else if (item.getItemId() == R.id.page_3) {
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
                showDialog();
                return true;
            case R.id.search_menu:
                Intent intent2 = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent2);
                return true;
        }


        return false;
    }

    public void showDialog() {
        dialog.setContentView(R.layout.dialog_logout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imageViewDio = dialog.findViewById(R.id.image_dialog);
        Button dialogLogOut = dialog.findViewById(R.id.dialog_logout);
        TextView textView = dialog.findViewById(R.id.user_account);
        if(sharedPreferences.getString(Constants.EMAIL, "DEFAULT")!=null)
        textView.setText(sharedPreferences.getString(Constants.EMAIL, "DEFAULT"));

        dialogLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString(Constants.EMAIL, "DEFAULT");
                edit.putBoolean(Constants.REMEMBER_ME, false);
                edit.apply();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }

        });
        dialog.show();
    }

}