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
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Button button ;
    Dialog dialog;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new FilmFragment()).commit();
            Log.d("Main Activity:", "on create");

        }

        dialog =new Dialog(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference wq = database.getReference();
        DatabaseReference databaseuser = database.getReference().child("User");


        databaseuser.child("1").addValueEventListener(new ValueEventListener() {
            private static final String TAG = "oncreate";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d(TAG, "onDataChange: "+dataSnapshot.getValue()  );


                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
                //   Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        if (item.getItemId() == R.id.page_1) {
            selectedFragment = new FilmFragment();
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
               showDialog();
                return true;
            case R.id.search_menu:
                Intent intent2 = new Intent(MainActivity.this, Search.class);
                startActivity(intent2);
                return true;}


        return false;
    }
    public void showDialog(){
dialog.setContentView(R.layout.dialog_logout);
dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imageViewDio =dialog.findViewById(R.id.image_dialog);
        Button dialogLogOut=dialog.findViewById(R.id.dialog_logout);

        dialogLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }

        });
dialog.show();
    }

}