package com.example.rehabnow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rehabnow.Utils.Constants;
import com.example.rehabnow.Utils.Utils;
import com.example.rehabnow.data.Appointments;
import com.example.rehabnow.data.User;
import com.example.rehabnow.db.AppDatabase;
import com.example.rehabnow.db.AppointmentsDao;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class PatientHome extends AppCompatActivity {

    private TextView floction, tvChooseDoctor;
    TextView abtv;
    public static final int CHOOSE_DOCTOR_RESULT = 713;
    public static final String SELECTED_DOCTOR = "SELECTED_DOCTOR";

    private User selectedDoctor;

    public static Intent getInstance(User user, Context context) {
        PatientHome myActivity = new PatientHome();
        Intent myIntent = new Intent(context, PatientHome.class);
        myIntent.putExtra(Constants.USER_KEY, user);
        return myIntent;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Bundle extras = getIntent().getExtras();
        String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        floction = findViewById(R.id.flocation);
        tvChooseDoctor = findViewById(R.id.tvChooseDoctor);

        tvChooseDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientHome.this, DoctorList.class);
                startActivityForResult(intent, CHOOSE_DOCTOR_RESULT);
            }
        });

        User user;
        if (extras != null) {
            user = (User) extras.getSerializable(Constants.USER_KEY);
            Toast.makeText(this, user.getWelcomeMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Data has been Found", Toast.LENGTH_SHORT).show();
        }

    }

    public void chooseLocation(View view) {
        List<String> optionsList = Arrays.asList(Constants.city);
        // Clear the list before adding new options
//        optionsList.clear();

        // Create the ArrayAdapter with the dynamic options list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(PatientHome.this, android.R.layout.simple_list_item_1, optionsList);

        // Build and show the dialog with the ArrayAdapter
        AlertDialog.Builder builder = new AlertDialog.Builder(PatientHome.this);
        builder.setTitle("Options")
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle item click
                        String selectedOption = optionsList.get(which);
                        floction.setText(selectedOption);
                        // Do something with the selected option
                        Toast.makeText(PatientHome.this, "this" + selectedOption, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_DOCTOR_RESULT) {
            selectedDoctor = (User) data.getSerializableExtra(SELECTED_DOCTOR);
            tvChooseDoctor.setText(selectedDoctor.getFullName());

        }
    }
    @SuppressLint("NewApi")
    public void showAppointmentsDialog(View view)  {

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Set the target time to the next date's start time
        LocalDate nextDate = now.toLocalDate().plusDays(1);
        LocalDateTime targetDateTimeNextDay = LocalDateTime.of(nextDate, LocalTime.MIN);

        // Calculate the difference in milliseconds
        long millisecondsUntilDateChange = ChronoUnit.MILLIS.between(now, targetDateTimeNextDay);

        // Calculate the interval between milliseconds
        long interval = millisecondsUntilDateChange / 30;

        // Create an array to store the milliseconds
        final long[] millisecondsArray = new long[30];

        // Calculate the milliseconds for each interval
        for (int i = 0; i < 30; i++) {
            millisecondsArray[i] = now.plus(i * interval, ChronoUnit.MILLIS).toInstant(ZoneOffset.UTC).toEpochMilli();
        }

        // Convert the array to an array of strings
        String[] millisecondsStrings = new String[30];
        for (int i = 0; i < 30; i++) {
            millisecondsStrings[i] = String.valueOf(millisecondsArray[i]);
        }

        // Create an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a Millisecond");

        CharSequence[] availableSlots = Utils.getAppointmentsInFormat(millisecondsStrings);
        builder.setItems(availableSlots, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long selectedMilliseconds = millisecondsArray[which];
                // Perform desired action with the selected millisecond
                // For example, display it in a Toast message
                Toast.makeText(PatientHome.this, "Selected millisecond: " + selectedMilliseconds, Toast.LENGTH_SHORT).show();
            }
        });

        // Display the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void storeAppointmentToDb(long time) {
        AppDatabase db = MyApplication.getInstance().getDb(getApplicationContext());
        AppointmentsDao appointmentsDao = db.appointmentsDao();
        Appointments myAppointment = new Appointments(time, selectedDoctor.email, MyApplication.getInstance().getCurrentUser().email, "VIRTUAL");
        appointmentsDao.insertAll(myAppointment);


        // List of my appointmetns
        //appointmentsDao.getDoctorAppointments(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }
}