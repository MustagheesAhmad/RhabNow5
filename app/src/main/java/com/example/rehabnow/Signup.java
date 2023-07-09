package com.example.rehabnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rehabnow.Utils.Constants;
import com.example.rehabnow.data.User;
import com.example.rehabnow.db.AppDatabase;
import com.example.rehabnow.db.UserDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Signup extends AppCompatActivity {

    TextView already_account;
    EditText emailIs, passwordIs, repasswordIs, name;
    Button btnsignup;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+";

    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    RadioButton patientRadioButton;
    RadioButton doctorRadioButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        AppDatabase db = MyApplication.getInstance().getDb(getApplicationContext());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        already_account = findViewById(R.id.already_account);
        passwordIs = findViewById(R.id.passwordIs);
        repasswordIs = findViewById(R.id.repasswordIs);
        name = findViewById(R.id.name);
        emailIs = findViewById(R.id.emailIs);
        btnsignup = findViewById(R.id.btnsignup);
        patientRadioButton = findViewById(R.id.radioPatient);
        doctorRadioButton = findViewById(R.id.radioDoctor);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);
        already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginPage = new Intent(Signup.this, MainActivity.class);
                startActivity(LoginPage);
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });

    }

    private void PerforAuth() {
        String email = emailIs.getText().toString();
        String password = passwordIs.getText().toString();
        String repassword = repasswordIs.getText().toString();

        if (email.matches(emailPattern)) {
            emailIs.setError("Please Enter Correct Email!");


        } else if (password.isEmpty() || password.length() < 6) {
            passwordIs.setError("Please enter correct password and 6 digits Password");

        } else if (!password.equals(repassword)) {

            repasswordIs.setError("Password does not match");
        } else {
            progressDialog.setMessage("Please wait for the Registration..");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        updateFirebaseUserDisplayName(name.getText().toString());
                        Toast.makeText(Signup.this, "Registration Successfully Done", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Signup.this, "" + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void updateFirebaseUserDisplayName(String displayName) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates =
                new UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName)
                        .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        storeToDb(new User(user.getDisplayName(),
                                user.getEmail(),
                                getRole(), "", "", "", ""));
                        sendUserToNextActivity();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Signup.this, "Profile Update Failed for Display name.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getRole() {
        if(patientRadioButton.isChecked()) {
            return Constants.PATIENT;
        }
        if(doctorRadioButton.isChecked()) {
            return Constants.DOCTOR;
        }
        return "NULL";
    }

    private void storeToDb(User user) {
        AppDatabase db = MyApplication.getInstance().getDb(getApplicationContext());
        UserDao userDao = db.userDao();

        User dbUser = userDao.findByEmail(user.email);
        if(dbUser !=null && !dbUser.getFullName().isEmpty()) {
            Toast.makeText(this, "Duplicate Sign up", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, " Sign up now putting in DB", Toast.LENGTH_SHORT).show();
            userDao.insertAll(user);
        }
    }

    private void sendUserToNextActivity() {
        Intent LoginPage = new Intent(Signup.this, MainActivity.class);
        startActivity(LoginPage);
        finish();

    }

}