package com.shrimadbhagwat.vitrideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;


public class GetStarted extends AppCompatActivity {
    private Button getStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        FirebaseMessaging.getInstance().subscribeToTopic("notification");
        getStarted = findViewById(R.id.button);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Test", "Button Clicked");

                startActivity(new Intent(GetStarted.this,LoginActivity.class) );

            }
        });
    }


}