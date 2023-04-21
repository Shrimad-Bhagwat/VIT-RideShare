package com.shrimadbhagwat.vitrideshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
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

    TextView from_tv, to_tv, name_tv, date_tv,desc_tv;

    FloatingActionButton deleteButton;

    Button contactButton,whatsappButton;
    ImageView back_button;
    String key="",contact;
    boolean call_permission=true;

    private FirebaseAuth auth;
    private FirebaseUser user;
    String currentUser,creator;
    String[] permissions = {"android.permission.CALL_PHONE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        from_tv = findViewById(R.id.from_tv);
        to_tv = findViewById(R.id.to_tv);
        name_tv = findViewById(R.id.name_tv);
        date_tv = findViewById(R.id.date_tv);
        desc_tv = findViewById(R.id.desc_tv);

        back_button = findViewById(R.id.back_button);

        deleteButton = findViewById(R.id.deleteButton);
        contactButton = findViewById(R.id.contact_button);
        whatsappButton = findViewById(R.id.whatsapp_button);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        currentUser = user.getEmail().toString();
        if(ContextCompat.checkSelfPermission(this,permissions[0])!=PackageManager.PERMISSION_GRANTED){
            call_permission=false;
        }

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            from_tv.setText(bundle.getString("From"));
            to_tv.setText(bundle.getString("To"));
            name_tv.setText(bundle.getString("Name"));
            key = bundle.getString("Key");
            creator = bundle.getString("Creator");
            date_tv.setText(bundle.getString("Date"));
            contact = bundle.getString("Contact");
            desc_tv.setText(bundle.getString("Desc"));
        }

        if(currentUser.equals(creator)){
            deleteButton.setEnabled(true);
//            Log.d("Same",currentUser.toString()+" "+creator.toString());

        }else {
            deleteButton.setEnabled(false);
            deleteButton.setVisibility(View.INVISIBLE);
//            Log.d("Not Same",currentUser.toString()+" "+creator.toString());
        }

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                }
        });

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestPermissions(permissions,80);
                if(call_permission){
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    Uri x = Uri.parse("tel:+91"+contact.toString());
                    callIntent.setData(Uri.parse(String.valueOf(x)));
                    startActivity(callIntent);
                }else {

                    Toast.makeText(DetailActivity.this,"Call Permission needed!",Toast.LENGTH_SHORT).show();;
                }
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
                new AlertDialog.Builder(DetailActivity.this)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                reference.child(key).removeValue();
                                Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==80){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(DetailActivity.this,"Permission Granted",Toast.LENGTH_SHORT).show();
                call_permission=true;
            }else {
//                Toast.makeText(DetailActivity.this,"Permission Needed",Toast.LENGTH_SHORT).show();
                call_permission=false;
            }
        }
    }
}