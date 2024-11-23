package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.TripPackage;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;

import java.util.Locale;

public class BookingSummary extends AppCompatActivity implements ActivityEssentials {

    TripPackage tripPackage;
    Destination currentDestination;
    TextView packageTextView, tripDateTextView, guestsTextView, airlineTextView, hotelTextView,
            servicesTextView, totalCostTextView;
    Button nextBtn, returnBtn, cancelBtn;

    int guestsCount;
    double hotelCost, airlineCost, totalCost = -1;

    private static final String LOG_TAG = "BOOKING SUMMARY ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_summary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.booking_summary), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            currentDestination = ActivitiesUtils.retrieveCurrentDestination(this);
            tripPackage = ActivitiesUtils.retrieveTripPackage(this);

            initialize();
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Error while initializing booking summary activity: " + e.getMessage(), e);
            Toast.makeText(this, "Failed to load booking summary.", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void initialize() {
        packageTextView = findViewById(R.id.txtPackage);
        tripDateTextView = findViewById(R.id.txtTripDate);
        guestsTextView = findViewById(R.id.txtGuests);
        airlineTextView = findViewById(R.id.txtAirline);
        hotelTextView = findViewById(R.id.txtHotel);
        servicesTextView = findViewById(R.id.txtServices);
        totalCostTextView = findViewById(R.id.txtTotalCost);
        String emptySpace = " ", dollarSign = "$";

        guestsCount = tripPackage.getGuests();
        double totalCost = calculateTripCost();

        String packageTxt = packageTextView.getText().toString() + emptySpace + currentDestination.getTitle();
        String tripDateTxt = tripDateTextView.getText().toString() + emptySpace + tripPackage.getDate();
        String guestsCountTxt = guestsTextView.getText().toString() + emptySpace + guestsCount;
        String airlineTxt = airlineTextView.getText().toString() + emptySpace + tripPackage.getAirline().getName();
        String hotelTxt = hotelTextView.getText().toString() + emptySpace + tripPackage.getHotel().getName();
        String servicesTxt = servicesTextView.getText().toString() + emptySpace + tripPackage.getAdditionalServices().get(0);
        String totalCostTxt = String.format(Locale.US, "Hotel Cost (%d nights): ",
                tripPackage.getDays()) + dollarSign + hotelCost + "\n\n\n\nAirline Cost (Two-way): "  +
                dollarSign + airlineCost + "\n/Per Person\n\n\n\n" + totalCostTextView.getText().toString()  +
                emptySpace + dollarSign + totalCost + " for " + guestsCount + " guest/s";


        packageTextView.setText(packageTxt);
        tripDateTextView.setText(tripDateTxt);
        guestsTextView.setText(guestsCountTxt);
        airlineTextView.setText(airlineTxt);
        hotelTextView.setText(hotelTxt);
        servicesTextView.setText(servicesTxt);
        totalCostTextView.setText(totalCostTxt);

        nextBtn = findViewById(R.id.next_button_booking_summary);
        returnBtn = findViewById(R.id.return_btn_booking_summary);
        cancelBtn = findViewById(R.id.cancel_btn_booking_summary);

        nextBtn.setOnClickListener(v -> launchConfirmBookingActivity());
        returnBtn.setOnClickListener(v -> ActivitiesUtils.returnToPreviousView(this));
        cancelBtn.setOnClickListener(v -> returnToBookFragment());
    }

    private void launchConfirmBookingActivity() {
        if (totalCost != -1) {
            tripPackage.setTotalCost(totalCost);
            Intent intent = new Intent(BookingSummary.this, ConfirmBookingActivity.class);
            intent.putExtra("destination_details", currentDestination);
            intent.putExtra("trip_package", tripPackage);
            startActivity(intent);
        }

    }

    private double calculateTripCost(){
        hotelCost = tripPackage.getRoom().getCost() * tripPackage.getDays();
        airlineCost = tripPackage.getAirline().getCost();
        totalCost = (hotelCost + airlineCost) * guestsCount;
        return totalCost;

    }

    private void returnToBookFragment() {
        Intent intent = new Intent(BookingSummary.this, DestinationActivity.class);
        intent.putExtra("destination_details", currentDestination);
        intent.putExtra("last_activity", "BookingSummaryActivity");
        ActivitiesUtils.returnToViewAndClear(this, intent);
    }
}