package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.Booking;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.TripPackage;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;

import java.util.ArrayList;
import java.util.Random;

public class TravelersNumberActivity extends AppCompatActivity implements ActivityEssentials,
        AdapterView.OnItemSelectedListener, View.OnClickListener {

    Button btnReturn, btnNext;
    Spinner guestSpinner;


    int randomTravelersCount = -1;
    ArrayList<String> guestCountOptions = new ArrayList<>();
    Booking booking;
    Destination currentDestination;
    Destination.DestinationType destinationType;
    TripPackage tripPackage;
    int selectedGuestsCount = -1;

    private static final ActivityName ACTIVITY_NAME = ActivityName.TRAVELERS_NUMBER;
    private static final String LOG_TAG = ACTIVITY_NAME + " ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guest_number);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.guest_number), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            booking = ActivitiesUtils.retrieveBooking(this);
            currentDestination = booking.getCurrentDestination();
            tripPackage = ActivitiesUtils.retrieveTripPackage(this);
            destinationType = currentDestination.getDestinationType();

            initialize();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error while initializing guests number activity: " + e.getMessage(), e);
            finish();
        }
    }


    @Override
    public void initialize() {

        guestSpinner = findViewById(R.id.guests_spinner);
        btnReturn = findViewById(R.id.return_btn_guests);
        btnNext = findViewById(R.id.next_btn_guests);

        generateRandomTravelersCount();
        addSpinnerItems();

        btnReturn.setOnClickListener(this);
        btnNext.setOnClickListener(this);

    }

    private void generateRandomTravelersCount() {
        randomTravelersCount = new Random().nextInt(40) + 1;
    }

    private void addSpinnerItems() {

        for (int i = 1; i <= randomTravelersCount; i++) {
            guestCountOptions.add(i + "");
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, guestCountOptions);


        guestSpinner.setAdapter(adapter);

        guestSpinner.setOnItemSelectedListener(this);


    }

    private void launchAirlinesSelectionActivity() {
        Intent intent = new Intent(TravelersNumberActivity.this, AirlinesActivity.class);
        intent.putExtra("booking", booking);
        intent.putExtra("trip_package", tripPackage);
        startActivity(intent);
    }

    private void handleNext() {

        if (selectedGuestsCount < 0) {
            Toast.makeText(this, "Please select guest count", Toast.LENGTH_LONG).show();
            return;
        }

        tripPackage.setGuests(selectedGuestsCount);
        launchAirlinesSelectionActivity();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedGuestsCount = Integer.parseInt(guestCountOptions.get(i));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.return_btn_guests) {
            ActivitiesUtils.returnToPreviousView(this);
        }
        else {
            handleNext();
        }
    }
}