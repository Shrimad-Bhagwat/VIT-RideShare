package com.shrimadbhagwat.vitrideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView from_tv, to_tv, name_tv, date_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        from_tv = findViewById(R.id.from_tv);
        to_tv = findViewById(R.id.to_tv);
        name_tv = findViewById(R.id.name_tv);
        date_tv = findViewById(R.id.date_tv);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            from_tv.setText(bundle.getString("From"));
            to_tv.setText(bundle.getString("To"));
            name_tv.setText(bundle.getString("Name"));
            date_tv.setText(bundle.getString("Date"));
        }

    }
}