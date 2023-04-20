package com.shrimadbhagwat.vitrideshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UploadActivity extends AppCompatActivity {
    Button saveButton;
    EditText nameEdt,fromEdt,toEdt,contactEdt,descEdt;
    EditText dateEdt;
    private FirebaseAuth auth;
    private FirebaseUser user;
    boolean allcheck= false;
    String creator;
    String name,from,to,date,dateTxt,contact,desc;
    final Calendar myCalendar= Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        nameEdt = findViewById(R.id.person_name);
        fromEdt = findViewById(R.id.from_location);
        toEdt = findViewById(R.id.to_location);
        dateEdt = findViewById(R.id.idEdtDate);
        contactEdt = findViewById(R.id.contact);
        saveButton = findViewById(R.id.saveButton);
        descEdt = findViewById(R.id.desc);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        creator = user.getEmail().toString();

        name = nameEdt.getText().toString();
        from = fromEdt.getText().toString();
        to = toEdt.getText().toString();
        date = dateEdt.getText().toString();
        contact = contactEdt.getText().toString();
        desc = descEdt.getText().toString();

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDate = new DatePickerDialog (UploadActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                mDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDate.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 allcheck = CheckAllFields();
                 if(allcheck){
                     saveData();
                 }

            }
        });

    }
    private boolean CheckAllFields() {
        name = nameEdt.getText().toString();
        from = fromEdt.getText().toString();
        to = toEdt.getText().toString();
        date = dateEdt.getText().toString();
        contact = contactEdt.getText().toString();
        if (name.length() == 0) {
            nameEdt.setError("This field is required");
            return false;
        }

        if (from.length() == 0) {
            fromEdt.setError("This field is required");
            return false;
        }

        if (to.length() == 0) {
            toEdt.setError("This field is required");
            return false;
        }

        if (date.length() == 0) {
            dateEdt.setError("This field is required");
            return false;
        }
        if (contact.length() < 8) {
            contactEdt.setError("This field is required");
            return false;
        }

        // after all validation return true.
        return true;
    }
    public void saveData(){

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();

        dialog.show();

        name = nameEdt.getText().toString();
        from = fromEdt.getText().toString();
        to = toEdt.getText().toString();
        date = dateEdt.getText().toString();
        contact = contactEdt.getText().toString();
//        Log.d(name,from+":"+to+":"+date+":"+contact);
        desc = descEdt.getText().toString();
        DataClass dataClass = new DataClass(name,from, to, date, contact,creator,desc);


        if(allcheck) {

            FirebaseDatabase.getInstance().getReference("Ride Data").child(name).setValue(dataClass)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(UploadActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            Toast.makeText(UploadActivity.this,"Enter all details",Toast.LENGTH_SHORT).show();

        }
    }
    private void updateLabel(){
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        dateEdt.setText(dateFormat.format(myCalendar.getTime()));
    }
}