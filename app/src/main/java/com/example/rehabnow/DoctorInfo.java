package com.example.rehabnow;

import static com.example.rehabnow.Utils.Constants.Qualification;
import static com.example.rehabnow.Utils.Constants.Specialization;
import static com.example.rehabnow.Utils.Constants.city;
import static com.example.rehabnow.Utils.Constants.provinces;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rehabnow.data.User;
import com.example.rehabnow.db.AppDatabase;
import com.example.rehabnow.db.UserDao;

public class DoctorInfo extends AppCompatActivity {
    TextView tvBack, cptv, cctv, qatv, sptv;
    Button submit;
    AutoCompleteTextView autocompletetv, autocompletecitytv, autoqa, autosp;
    User user;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);

        submit = findViewById(R.id.submit);
        user = MyApplication.getInstance().getCurrentUser();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, Qualification);
        //Getting the instance of AutoCompleteTextView
        autoqa = findViewById(R.id.autoqa);
        sptv = findViewById(R.id.sptv);
        autoqa.setThreshold(1);//will start working from first character
        autoqa.setAdapter(adapter2);//setting the adapter data into the AutoCompleteTextView
        autoqa.setTextColor(Color.BLACK);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, Specialization);
        //Getting the instance of AutoCompleteTextView
        autosp = findViewById(R.id.autosp);
        autosp.setThreshold(1);//will start working from first character
        autosp.setAdapter(adapter3);//setting the adapter data into the AutoCompleteTextView
        autosp.setTextColor(Color.BLACK);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, provinces);
        //Getting the instance of AutoCompleteTextView
        autocompletetv = findViewById(R.id.autocompletetv);
        autocompletetv.setThreshold(1);//will start working from first character
        autocompletetv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        autocompletetv.setTextColor(Color.BLACK);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, city);
        autocompletecitytv = findViewById((R.id.autocompletecitytv));
        autocompletecitytv.setThreshold(1);
        autocompletecitytv.setAdapter(adapter1);
        autocompletecitytv.setTextColor(Color.BLACK);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setQualification(autoqa.getText().toString());
                user.setSpeciality(sptv.getText().toString());
                user.setProvince(autocompletetv.getText().toString());
                user.setCity(autocompletecitytv.getText().toString());
                storeToDb(user);
                Toast.makeText(DoctorInfo.this, "Data has been updated", Toast.LENGTH_SHORT).show();
                sendUserToNextActivity();
            }
        });


    }

    public void storeToDb(User user) {
        AppDatabase db = MyApplication.getInstance().getDb(getApplicationContext());
        UserDao userDao = db.userDao();
        userDao.updateUser(user);

    }

    public void sendUserToNextActivity() {
        Intent doctorHome = new Intent(DoctorInfo.this, DoctorHome.class);
        startActivity(doctorHome);
        finish();

    }
}