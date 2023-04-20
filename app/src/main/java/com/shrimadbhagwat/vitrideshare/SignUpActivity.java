package com.shrimadbhagwat.vitrideshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    boolean email_accepted=true;
    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword;
    private Button signupButton;
    private TextView loginRedirectTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        signupEmail = (EditText) findViewById(R.id.signup_email);
        signupPassword = (EditText) findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectTextView = findViewById(R.id.loginRedirectText);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_accepted=true;
                String user = signupEmail.getText().toString();
                String[] user_det = user.split("@");
                String pass = signupPassword.getText().toString();
//                Log.d(user,pass);
                if(user.isEmpty() ){
                    signupEmail.setError("Email cannot be empty!");
                }else if(!Patterns.EMAIL_ADDRESS.matcher(user).matches() ){
                    signupEmail.setError("Enter valid Email!");
                } else if (!user_det[1].toString().equals("vitbhopal.ac.in")) {
                    signupEmail.setError("Email not accepted!");
                    email_accepted=false;
                }
                Log.d("",user_det[1].toString());
                if(pass.isEmpty()){
                    signupPassword.setError("Password cannot be empty!");
                }
                if(pass.toString().length()<6){
                    signupPassword.setError("Password cannot be less than 6 letters!");

                }
                Log.d("Email Accepted ", String.valueOf(email_accepted));
                if(email_accepted) {
                    auth
                            .createUserWithEmailAndPassword(user, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),
                                                        "Registration successful!",
                                                        Toast.LENGTH_SHORT)
                                                .show();

                                        Intent intent
                                                = new Intent(SignUpActivity.this,
                                                HomeActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(
                                                        getApplicationContext(),
                                                        "Registration failed! "
                                                                +task.getException().getMessage().toString(),
                                                        Toast.LENGTH_LONG)
                                                .show();

                                    }
                                }
                            });
                }
            }

        });

        loginRedirectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });
    }
}