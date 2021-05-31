package com.example.cinemabooking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cinemabooking.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView skip, signup;
    EditText username, password;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseuser = database.getReference().child("User");
    User user = new User();

    Button logIn;
    final boolean check[] = new boolean[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        skip = findViewById(R.id.skip);
        signup = findViewById(R.id.sign_up);
        username = (EditText) findViewById(R.id.username_view);
        password = (EditText) findViewById(R.id.password_view);

        skip.setOnClickListener(this);
        signup.setOnClickListener(this);

        logIn = (Button) findViewById(R.id.next_button);
        logIn.setOnClickListener(this);
        //  checklogin();
    }

    @Override
    public void onClick(View v) {
        final boolean[] check = {false};
        if (v.getId() == R.id.skip) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.sign_up) {

            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.next_button) {

            databaseuser.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Toast.makeText(getApplicationContext(), "username exists", Toast.LENGTH_LONG).show();

                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        User user = snap.getValue(User.class);
                        if (user.getUsername().equals(username.getText().toString()) && user.getPassword().equals(password.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "logged in successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplication(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(user.getUsername().equals(username.getText().toString()) && !user.getPassword().equals(password.getText().toString())){
                            Toast.makeText(getApplicationContext(), "wrong password", Toast.LENGTH_LONG).show();

                        }



                    }
                    Toast.makeText(getApplicationContext(), "didn't find username", Toast.LENGTH_LONG).show();


                }

                @Override
                public void onCancelled(DatabaseError error) {

                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });

        }
    }


}