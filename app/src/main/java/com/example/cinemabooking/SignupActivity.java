package com.example.cinemabooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cinemabooking.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username, password;
    Button button;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseuser = database.getReference().child("User");
    User user;
    long maxID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username = (EditText) findViewById(R.id.create_username);
        password = (EditText) findViewById(R.id.create_password);

        Log.d("TA", "onCreate: " + username.getText().toString());
        Log.d("TA", "onCreate: " + password.getText().toString());

        button = (Button) findViewById(R.id.next_button);
        button.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        user = new User(username.getText().toString(), password.getText().toString());

        databaseuser.addListenerForSingleValueEvent(new ValueEventListener() {
            private static final String TAG = "oncreate";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    maxID = dataSnapshot.getChildrenCount();


                //    Log.d(TAG, "onDataChange: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        databaseuser.child((maxID + 1) + "").setValue(user);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}