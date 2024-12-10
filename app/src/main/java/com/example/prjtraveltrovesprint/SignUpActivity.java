package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prjtraveltrovesprint.MainActivity;
import com.example.prjtraveltrovesprint.R;
import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.User;
import com.example.prjtraveltrovesprint.model.UserSession;
import com.example.prjtraveltrovesprint.utils.ValidatorUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity implements ActivityEssentials, OnSuccessListener, OnFailureListener {

    EditText edTextEmail, edTextPassword, edFirstName, edLastName;
    String email, firstName, lastName, password;
    Button buttonSignUp;
    TextView textViewSignIn;

    FirebaseDatabase ttDatabase;
    DatabaseReference userRef, counterRef;

    private static final ActivityName ACTIVITY_NAME = ActivityName.SIGN_UP;
    private static final String LOG_TAG = ACTIVITY_NAME + " ACTIVITY FIREBASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initialize();


    }

    public void initialize() {
        edTextEmail = findViewById(R.id.editTextEmail);
        edTextPassword = findViewById(R.id.editTextPassword);
        edFirstName = findViewById(R.id.editTextFN);
        edLastName = findViewById(R.id.editTextLN);
        buttonSignUp = findViewById(R.id.btn_sign_up);
        textViewSignIn = findViewById(R.id.textViewSignIn);

        ttDatabase = FirebaseDatabase.getInstance();
        userRef = ttDatabase.getReference("user");
        counterRef = ttDatabase.getReference("userDocumentIdCounter");



        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validUserInput()) {
                    handleUserAdd();
                }
            }
        });

        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validUserInput() {
        email = edTextEmail.getText().toString();
        firstName = edFirstName.getText().toString();
        lastName = edLastName.getText().toString();
        password = edTextPassword.getText().toString();

        if (!ValidatorUtils.isValidEmail(email)) {
            Toast.makeText(this, "Invalid Email: ", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!ValidatorUtils.isValidFirstName(firstName)) {
            Toast.makeText(this,
                    "Invalid First Name: Make sure first name is more than 1 character, " +
                            "max is 20, first should not contain empty spaces", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!ValidatorUtils.isValidLastName(lastName)) {
            Toast.makeText(this,
                    "Invalid Last Name: Make sure last name is more than 1 character " +
                            "and max is 30", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!ValidatorUtils.isValidPassword(password)) {
            Toast.makeText(this,
                    "Invalid Password: Password must be at least 6 characters long", Toast.LENGTH_LONG).show();
            return false;
        }



        return true;

    }

    private void addUser(User u) {
        User user = u;
        Log.d("User Object", user.toString());

        userRef.child(String.valueOf(user.getId())).setValue(user)
                .addOnSuccessListener(this)
                .addOnFailureListener(this);
    }

    private void handleUserAdd() {
        ArrayList<Destination> destinations = new ArrayList<>();
        destinations.add(new Destination(0, "", Destination.DestinationType.PARIS));

        counterRef.child("currentId").get().addOnCompleteListener(t -> {
            if (t.isSuccessful()) {
                Integer currentCounter = t.getResult().getValue(Integer.class);
                if (currentCounter != null) {
                    int newCounter = currentCounter + 100;
                    User user = new User(newCounter, firstName, lastName, email, password, destinations);
                    counterRef.child("currentId").setValue(newCounter);
                    addUser(user);

                } else {
                    Log.d(LOG_TAG, "No counter ID found");
                }
            } else {
                Log.e(LOG_TAG, "Error fetching counterId", t.getException());
            }
        });


    }

    private void startMainActivity() {
        // If sign-up is successful:
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccess(Object o) {
        Toast.makeText(this, "Your account has been created successfully ", Toast.LENGTH_LONG).show();
        startMainActivity();
    }


    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(this, "Error while adding your account to the database ", Toast.LENGTH_LONG).show();
    }
}
