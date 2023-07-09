package com.example.rehabnow;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rehabnow.Utils.Constants;
import com.example.rehabnow.Utils.Utils;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements OnSuccessListener<BeginSignInResult>, OnFailureListener {
    EditText username;
    EditText password;
    Button button_login;
    TextView signupText;
    LinearLayout btnlogingoogle;
    private FirebaseAuth mAuth;



    private static final int REQ_ONE_TAP = 90001;

    private boolean showOneTapUI = true;


    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        button_login = findViewById(R.id.button_login);
        signupText = findViewById(R.id.signupText);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Fill details", Toast.LENGTH_SHORT).show();
                } else {
                    login(username.getText().toString(), password.getText().toString());
                }
            }
        });


        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(MainActivity.this, Signup.class);
                startActivity(signup);
            }
        });


    }

    public void initiateGoogleSignIn(View view) {
        if(showOneTapUI) {
            showOneTapUI = false;
            oneTapClient = Identity.getSignInClient(this);
            signInRequest = BeginSignInRequest.builder()
                    .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                            .setSupported(true)
                            .build())
                    .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                            .setSupported(true)
                            // Your server's client ID, not your Android client ID.
                            .setServerClientId(getString(R.string.server_client_id))
                            // Only show accounts previously used to sign in.
                            .setFilterByAuthorizedAccounts(false)
                            .build())
                    // Automatically sign in when exactly one credential is retrieved.
                    .setAutoSelectEnabled(true)
                    .build();

            oneTapClient.beginSignIn(signInRequest)
                    .addOnSuccessListener(this).addOnFailureListener(this);
            // ...
        }
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            MyApplication.getInstance().setCurrentUser(Utils.getCurrentUser(getApplicationContext(), email));
                            Intent homeIntent;
                            switch (MyApplication.getInstance().getCurrentUser().role) {
                                case Constants.PATIENT:
                                    homeIntent = PatientHome.getInstance(
                                            MyApplication.getInstance().getCurrentUser(), MainActivity.this);
                                    break;
                                case Constants.DOCTOR:
                                    homeIntent = new Intent(MainActivity.this, DoctorInfo.class);
                                    break;
                                default:
                                    Toast.makeText(MainActivity.this, "No Role Found", Toast.LENGTH_SHORT).show();
                                    return;
                            }
                            startActivity(homeIntent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }

    @Override
    public void onSuccess(BeginSignInResult beginSignInResult) {
        try {
            startIntentSenderForResult(
                    beginSignInResult.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                    null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "Couldn't start One Tap UI: " + e.getLocalizedMessage());
        }

    }

    @Override
    public void onFailure(@NonNull Exception e) {
        // No saved credentials found. Launch the One Tap sign-up flow, or
        // do nothing and continue presenting the signed-out UI.
        //to toggle on/off this cooldown on a test device and/or emulator alike,
        // simply go to the Dialer app and input the following code: *#*#66382723#*#*.
        Log.d(TAG, e.getLocalizedMessage());
        showOneTapUI = true;
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    String email = credential.getId();
                    String password = credential.getPassword();
                    showOneTapUI = true;
                    MyApplication.getInstance().setCurrentUser(
                            Utils.getCurrentUser(getApplicationContext(), email)
                    );
                    startActivity(PatientHome.getInstance(MyApplication.getInstance().getCurrentUser(), MainActivity.this));
                    if (idToken != null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with your backend.
                        Log.d(TAG, "Got ID token.");
                    } else if (password != null) {
                        // Got a saved username and password. Use them to authenticate
                        // with your backend.
                        Log.d(TAG, "Got password.");
                    }
                } catch (ApiException e) {
                    // ...
                    Log.i(TAG, "Failed pass`word.");
                    Log.d(TAG, e.getLocalizedMessage());

                    switch (e.getStatusCode()) {
                        case CommonStatusCodes.CANCELED:
                            Log.d(TAG, "One-tap dialog was closed.");
                            // Don't re-prompt the user.
                            showOneTapUI = false;
                            break;
                        case CommonStatusCodes.NETWORK_ERROR:
                            Log.d(TAG, "One-tap encountered a network error.");
                            // Try again or just ignore.
                            break;
                        default:
                            Log.d(TAG, "Couldn't get credential from result."
                                    + e.getLocalizedMessage());
                            break;
                    }

                }
                break;
        }

    }

}


// on below line we are getting
// the instance of our calendar.
//*final Calendar c = Calendar.getInstance();

// on below line we are getting
// our day, month and year.
//*int year = c.get(Calendar.YEAR);
//*int month = c.get(Calendar.MONTH);
//*int day = c.get(Calendar.DAY_OF_MONTH);

// on below line we are creating a variable for date picker dialog.
//*DatePickerDialog datePickerDialog = new DatePickerDialog(
// on below line we are passing context.
//*     MainActivity.this,
//*new DatePickerDialog.OnDateSetListener() {
//*         @Override
//*public void onDateSet(DatePicker view, int year,
//*                   int monthOfYear, int dayOfMonth) {
// on below line we are setting date to our text view.
//*             Toast.makeText(MainActivity.this, dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();

//                 //*                       }
//*                          },
// on below line we are passing year,
// month and day for selected date in our date picker.
//*     year, month, day);
// at last we are calling show to
// display our date picker dialog.
//*datePickerDialog.show();
//updateUI(user);