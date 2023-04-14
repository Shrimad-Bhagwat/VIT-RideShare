package com.shrimadbhagwat.vitrideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView from_tv, to_tv, name_tv, date_tv;
    String contact;
    Button contact_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        from_tv = findViewById(R.id.from_tv);
        to_tv = findViewById(R.id.to_tv);
        name_tv = findViewById(R.id.name_tv);
        date_tv = findViewById(R.id.date_tv);
        contact_btn = findViewById(R.id.contact_button);


        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            from_tv.setText(bundle.getString("From"));
            to_tv.setText(bundle.getString("To"));
            name_tv.setText(bundle.getString("Name"));
            date_tv.setText(bundle.getString("Date"));
            contact=(bundle.getString("Contact"));
        }
        contact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                Uri x = Uri.parse("tel:(+91)"+contact);
//                Log.d(Uri.parse(String.valueOf(x)));
                callIntent.setData(Uri.parse(String.valueOf(x)));
                startActivity(callIntent);
            }
        });

    }
}