package com.example.cinemabooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener {
TextView skip,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        skip =findViewById(R.id.skip);
        signup =findViewById(R.id.sign_up);
        skip.setOnClickListener(this);
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.skip){
            Intent intent= new Intent(this,MainActivity.class);
            startActivity(intent);

        }else if(v.getId()==R.id.sign_up){

            Intent intent= new Intent(this,Signup.class);
            startActivity(intent);
        }
    }
}