package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.Airline;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.TripPackage;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;
import com.example.prjtraveltrovesprint.utils.DataUtils;

import java.util.Random;

public class AirlinesActivity extends AppCompatActivity implements ActivityEssentials,
        RadioGroup.OnCheckedChangeListener {

    RadioGroup rgAirline;
    TextView txtDetails1, txtDetails2, txtDetails3;
    Button nextBtn, returnBtn;

    TripPackage tripPackage;
    Airline airline;
    Destination currentDestination;
    Destination.DestinationType destinationType;

    private static final int MIN_AIRLINES_PRICE = 400, MAX_AIRLINES_PRICE = 1500
            ,MAX_SEATS_COUNT = 40;

    private static final String LOG_TAG = "AIRLINES ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_airlines);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.airlines), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            currentDestination = ActivitiesUtils.retrieveCurrentDestination(this);
            tripPackage = ActivitiesUtils.retrieveTripPackage(this);

            destinationType = currentDestination.getDestinationType();

            initialize();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error while initializing destination activity: " + e.getMessage(), e);
            finish();
        }
    }

    public void initialize() {

        rgAirline = findViewById(R.id.rg_airline);
        txtDetails1 = findViewById(R.id.details_airline1);
        txtDetails2 = findViewById(R.id.details_airline2);
        txtDetails3 = findViewById(R.id.details_airline3);
        nextBtn = findViewById(R.id.next_btn_airlines);
        returnBtn = findViewById(R.id.return_btn_airlines);

        String[] airlines = DataUtils.getAirlinesList();

        InitializeAirlines(airlines);

        nextBtn.setOnClickListener(v ->
                launchHotelPackageSelectionActivity()
        );

        returnBtn.setOnClickListener(v ->
                ActivitiesUtils.returnToPreviousView(this)
                );

        rgAirline.setOnCheckedChangeListener(this);
    }

    private void InitializeAirlines(String[] airlines) {
        Random random = new Random();

        for (int i = 0; i < rgAirline.getChildCount(); i++) {

            RadioButton radioBtn = (RadioButton) rgAirline.getChildAt(i);

            if (radioBtn != null) {
                Airline newAirline = new Airline();

                // Set airline name
                radioBtn.setText(airlines[i]);
                newAirline.setName(airlines[i]);

                // Set available seats
                int randomSeatsCount = random.nextInt(MAX_SEATS_COUNT);
                newAirline.setSeatsCount(randomSeatsCount);

                // Set price
                int randomPrice = random.nextInt(MAX_AIRLINES_PRICE) + MIN_AIRLINES_PRICE;
                newAirline.setCost(randomPrice);

                // Store the Airline object in the tag of the radio btn
                radioBtn.setTag(newAirline);
            }
        }
    }

    private void launchHotelPackageSelectionActivity() {
    if (airline != null) {
        if (tripPackage.getGuests() > airline.getSeatsCount()) {
            Toast.makeText(this, "Insufficient seats for " + tripPackage.getGuests()
                    + " Travelers", Toast.LENGTH_SHORT).show();
            return;
        }
            tripPackage.setAirline(airline);
            Intent intent = new Intent(AirlinesActivity.this, HotelPackageActivity.class);
            intent.putExtra("destination_details", currentDestination);
            intent.putExtra("trip_package", tripPackage);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Please select a airline.", Toast.LENGTH_SHORT).show();
        }

    }

    private void showAirlineDetails(Airline selectedAirline) {
        final String AN_PLACE_HOLDER = "Airline: ", ASC_PLACE_HOLDER = "Available Seats: ",
                AC_PLACE_HOLDER = "Ticket Price: ";

        String airlineName = AN_PLACE_HOLDER + selectedAirline.getName();
        String airlineSeatsCount = ASC_PLACE_HOLDER + selectedAirline.getSeatsCount();
        String airlineTicketPrice = AC_PLACE_HOLDER + String.valueOf(selectedAirline.getCost());

        txtDetails1.setText(airlineName);
        txtDetails2.setText(airlineSeatsCount);
        txtDetails3.setText(airlineTicketPrice);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        RadioButton selectedRadioButton = findViewById(i);

        if (selectedRadioButton != null) {
            Airline selectedAirline = (Airline) selectedRadioButton.getTag();
            if (selectedAirline != null) {
                airline = new Airline();
                airline.setName(selectedAirline.getName());
                airline.setCost(selectedAirline.getCost());
                airline.setSeatsCount(selectedAirline.getSeatsCount());
                showAirlineDetails(airline);

                Log.d(LOG_TAG, "Selected Airline: " + selectedAirline.getName());
                Log.d(LOG_TAG, "Price: " + selectedAirline.getCost());
                Log.d(LOG_TAG, "Seats Available: " + selectedAirline.getSeatsCount());
            } else {
                Log.e(LOG_TAG, "Selected RadioButton has no Airline tag.");
            }
        }
    }
}