package com.shrimadbhagwat.vitrideshare;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
public class DetailActivity extends AppCompatActivity {

    TextView from_tv, to_tv, name_tv, date_tv;

    FloatingActionButton deleteButton;

    Button contactButton,whatsappButton;
    String key="",contact;

    private FirebaseAuth auth;
    private FirebaseUser user;
    String currentUser,creator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        from_tv = findViewById(R.id.from_tv);
        to_tv = findViewById(R.id.to_tv);
        name_tv = findViewById(R.id.name_tv);
        date_tv = findViewById(R.id.date_tv);

        deleteButton = findViewById(R.id.deleteButton);
        contactButton = findViewById(R.id.contact_button);
        whatsappButton = findViewById(R.id.whatsapp_button);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        currentUser = user.getEmail().toString();


        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            from_tv.setText(bundle.getString("From"));
            to_tv.setText(bundle.getString("To"));
            name_tv.setText(bundle.getString("Name"));
            key = bundle.getString("Key");
            creator = bundle.getString("Creator");
            date_tv.setText(bundle.getString("Date"));
            contact = bundle.getString("Contact");
        }

        if(currentUser.equals(creator)){
            deleteButton.setEnabled(true);
//            Log.d("Same",currentUser.toString()+" "+creator.toString());

        }else {
            deleteButton.setEnabled(false);
            deleteButton.setVisibility(View.INVISIBLE);
//            Log.d("Not Same",currentUser.toString()+" "+creator.toString());
        }

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);

                Uri x = Uri.parse("tel:+91"+contact.toString());
                callIntent.setData(Uri.parse(String.valueOf(x)));
                startActivity(callIntent);
            }
        });
        whatsappButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone=+91"+contact.toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Ride Data");

                reference.child(key).removeValue();
                Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });

    }
}