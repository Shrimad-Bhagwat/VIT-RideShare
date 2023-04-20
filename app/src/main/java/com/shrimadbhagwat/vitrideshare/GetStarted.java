package com.shrimadbhagwat.vitrideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;


public class GetStarted extends AppCompatActivity {
    private Button getStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.activity_get_started);
        FirebaseMessaging.getInstance().subscribeToTopic("notification");
        getStarted = findViewById(R.id.button);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user != null)
                    startActivity(new Intent(GetStarted.this,HomeActivity.class) );
                else {
                    startActivity(new Intent(GetStarted.this, LoginActivity.class));
                }
                finish();
            }
        }, 2000);

//        getStarted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("Test", "Button Clicked");
//                if(user != null)
//                    startActivity(new Intent(GetStarted.this,HomeActivity.class) );
//                else {
//                    startActivity(new Intent(GetStarted.this,LoginActivity.class) );
//
//                }
//
//            }
//        });
    }


}