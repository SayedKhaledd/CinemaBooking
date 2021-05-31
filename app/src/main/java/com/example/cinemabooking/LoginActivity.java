package com.example.cinemabooking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
            user.setPassword(password.getText().toString());
            user.setUsername(username.getText().toString());
            Log.d("TAG", "onClick: " + username.getText().toString() + " " + password.getText().toString());

            checklogin();
            if (check[0]) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();

            } else {

                Log.d("TAG", "onClick: " + "didn't find");
            }

        }
    }

    public void checklogin() {
        databaseuser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    for (int i = 1; i <= dataSnapshot.getChildrenCount(); i++) {
                        Log.d("TAG", "onClick: in loop " + dataSnapshot.child(i + "").child("password").getValue() + " " + dataSnapshot.child(i + "").child("username").getValue());
                        String username = dataSnapshot.child(i + "").child("password").getValue().toString();
                        String password = dataSnapshot.child(i + "").child("username").getValue().toString();
                        boolean checkk = user.getPassword().equals(password) && user.getUsername().equals(username);
                        Log.d("TAG", "onDataChange: " + checkk);
                        if (checkk) {
                            check[0] = true;
                            break;
                        }

                    }

                else
                    Log.d("TAG", "onDataChange: " + "didn't exist");
            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
}