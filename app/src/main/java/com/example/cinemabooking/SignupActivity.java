package com.example.cinemabooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cinemabooking.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username, password, email, firstname, lastname;
    Button button;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseuser = database.getReference().child("User");
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username = (EditText) findViewById(R.id.create_username);
        password = (EditText) findViewById(R.id.create_password);
        firstname = (EditText) findViewById(R.id.first_name);
        lastname = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email);

        Log.d("TA", "onCreate: " + username.getText().toString());
        Log.d("TA", "onCreate: " + password.getText().toString());

        button = (Button) findViewById(R.id.next_button);
        button.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        user = new User();
        databaseuser.addListenerForSingleValueEvent(new ValueEventListener() {
            private static final String TAG = "oncreate";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean check = false;
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    User user = snap.getValue(User.class);
                    if (user.getEmail().equals(email.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "email exists", Toast.LENGTH_LONG).show();
                        check = true;
                        break;

                    } else if (user.getUsername().equals(username.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "username exists", Toast.LENGTH_LONG).show();
                        check = true;

                        break;

                    }

                }
                if (!check) {
                    if (email.getText().toString().equals("")
                            || username.getText().toString().equals("")
                            || password.getText().toString().equals("")
                            || firstname.getText().toString().equals("")
                            || lastname.getText().toString().equals("")
                            || email.getText().toString().equals(" ")
                            || username.getText().toString().equals(" ")
                            || password.getText().toString().equals(" ")
                            || firstname.getText().toString().equals(" ")
                            || lastname.getText().toString().equals(" ")
                    ) {
                        Toast.makeText(getApplicationContext(), "all fields are required", Toast.LENGTH_LONG).show();

                    } else {

                        user.setEmail(email.getText().toString());
                        user.setUsername(username.getText().toString());
                        user.setPassword(password.getText().toString());
                        user.setFirstname(firstname.getText().toString());
                        user.setLastname(lastname.getText().toString());
                        databaseuser.child((dataSnapshot.getChildrenCount() + 1) + "").setValue(user);
                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                    }
                }


                //    Log.d(TAG, "onDataChange: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }


}