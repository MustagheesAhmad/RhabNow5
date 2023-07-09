package com.example.rehabnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.AdapterCard;
import com.example.rehabnow.adapter.OnItemClick;
import com.example.rehabnow.adapter.PersonAdapter;
import com.example.rehabnow.data.User;

import java.util.ArrayList;
import java.util.List;

public class DoctorList extends AppCompatActivity implements OnItemClick {

    RecyclerView rvDoctorList;
    RecyclerView.LayoutManager manager;

    PersonAdapter doctorsListAdapter;

    ArrayList<DoctorInformation> doctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        rvDoctorList = findViewById(R.id.rvDoctorList);
        rvDoctorList.setHasFixedSize(true);

        doctors = new ArrayList<>();
        doctors.add(new DoctorInformation("doctorImage"));


        manager = new LinearLayoutManager(this);
        doctorsListAdapter = new PersonAdapter(getDoctorsList(), this);
        rvDoctorList.setHasFixedSize(true);
        rvDoctorList.setLayoutManager(manager);
        rvDoctorList.setAdapter(doctorsListAdapter);
    }

    private List<User> getDoctorsList() {
        return MyApplication.getInstance().getDb(
                getApplicationContext()
        ).userDao().getAllDoctors();
    }

    @Override
    public void onItemClick(User user) {
        Toast.makeText(this, "On Doctor Item Clicked" + user.getFullName(), Toast.LENGTH_SHORT).show();
        Intent result = new Intent();
        result.putExtra(PatientHome.SELECTED_DOCTOR, user);
        setResult(PatientHome.CHOOSE_DOCTOR_RESULT, result);
        finish();
    }
}