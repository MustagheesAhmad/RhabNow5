package com.example.rehabnow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.rehabnow.data.User;

public class DoctorHome extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        user = MyApplication.getInstance().getCurrentUser();
        Toast.makeText(this, "Qualification" + user.getQualification(), Toast.LENGTH_SHORT).show();
    }
}