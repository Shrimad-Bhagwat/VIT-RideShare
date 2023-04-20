package com.shrimadbhagwat.vitrideshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    FloatingActionButton fab;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<DataClass> dataList;
    MyAdapter adapter;
    SearchView searchView;

    private MenuItem userName;
    private FirebaseAuth auth;
    private FirebaseUser user;
    String userEmail;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        userName = menu.findItem(R.id.user_name);
        userName.setTitle(userEmail);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Logout", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();

                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                            Toast.makeText(HomeActivity.this,"Logout Successful",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.cabicon);
//        getSupportActionBar().setTitle("VIT RideShare");


        FirebaseMessaging.getInstance().subscribeToTopic("notification");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userEmail = user.getEmail().toString();

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(HomeActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();
        adapter = new MyAdapter(HomeActivity.this, dataList);
        recyclerView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Ride Data");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -1);
                    Date c = cal.getTime();
                    Date d1,d2;
                    try {
                        d1 = sdf.parse(dataClass.getDate().toString());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    String d = sdf.format(c);
                    try {
                        d2 = sdf.parse(d);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    if(d1.compareTo(d2) >0){

                    dataList.add(dataClass);
                    }
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchList(s);

                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });
    }
    public void searchList(String text){
        ArrayList<DataClass> searchList = new ArrayList<>();
        for (DataClass dataClass: dataList){
            if (dataClass.getFrom_location().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
            else if (dataClass.getTo_location().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
            else if (dataClass.getDate().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
            else if (dataClass.getName().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }

}