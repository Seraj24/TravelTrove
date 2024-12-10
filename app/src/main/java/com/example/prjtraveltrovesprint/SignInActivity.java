package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity implements ActivityEssentials {

    EditText edTextEmail, edTextPassword;
    Button btnSignIn;
    TextView txtViewSignUp;
    FirebaseDatabase ttDatabase;
    DatabaseReference userRef;

    private static final ActivityName ACTIVITY_NAME = ActivityName.SIGN_IN;
    private static final String LOG_TAG = ACTIVITY_NAME + " ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initialize();

        //checkUserInput();

    }

    public void initialize() {
        edTextEmail = findViewById(R.id.editTextEmail);
        edTextPassword = findViewById(R.id.editTextPassword);
        btnSignIn = findViewById(R.id.btn_sign_in);
        txtViewSignUp = findViewById(R.id.textViewSignUp);
        ttDatabase = FirebaseDatabase.getInstance();
        userRef = ttDatabase.getReference("user");


        btnSignIn.setOnClickListener(v -> {
            /*
            UserSession userSession =  UserSession.getInstance();
            userSession.setUser(new User(300, "Katty", "Crane", "katty@tt.com", ""));
            */
            checkUserInput();
        });

        txtViewSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void checkUserInput() {
        String email = edTextEmail.getText().toString().strip();
        String password = edTextPassword.getText().toString().strip();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        userRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String storedPassword = userSnapshot.child("password").getValue(String.class);

                        if (storedPassword != null && storedPassword.equals(password)) {
                            // Check if the user session was initialized successfully
                            boolean successUserSessionInitialize = initializeUserSession(userSnapshot, email);

                            if (successUserSessionInitialize) {
                                // Successful login
                                startMainActivity();
                                return;
                            }

                        }
                    }
                    // If password doesn't match
                    Toast.makeText(SignInActivity.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                } else {
                    // No user found with this email
                    Toast.makeText(SignInActivity.this, "No user found with this email.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(LOG_TAG, "Error querying database", error.toException());
            }
        });

    }

    private boolean initializeUserSession(DataSnapshot userSnapshot, String email) {
        int id = 0;
        String firstName = null, lastName= null, password = null;
        ArrayList<Destination> destinations = new ArrayList<>();
        DataSnapshot destinationsSnapShot;
        UserSession userSession =  UserSession.getInstance();

        try {
            id = Integer.parseInt(userSnapshot.child("id").getValue().toString());
            firstName = userSnapshot.child("firstName").getValue().toString();
            lastName = userSnapshot.child("lastName").getValue().toString();
            password = userSnapshot.child("password").getValue().toString();
        }
        catch (NullPointerException e) {
            Log.d(LOG_TAG, "Null value/s occurred while initializing user session");
        }
        catch (NumberFormatException e1) {
            Log.d(LOG_TAG, "Could not parse id while initializing user session");
        }
        catch (Exception e2) {
            Log.d(LOG_TAG, "An unexpected error occurred while initializing user session");
        }


        if (id == 0 || firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
            Toast.makeText(this, "Error: Invalid coordinates from database", Toast.LENGTH_SHORT).show();
            return false;
        }

        destinationsSnapShot = userSnapshot.child("destinations");

        if (destinationsSnapShot.exists()) {
            for (DataSnapshot destinationSnapshot: destinationsSnapShot.getChildren()) {

                Destination destination = destinationSnapshot.getValue(Destination.class);
                if (destination != null) {
                    destinations.add(destination);
                }
            }
        }


        User user = new User(id, firstName, lastName, email, password, destinations);

        userSession.setUser(user);

        return true;


    }

    private void startMainActivity() {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
