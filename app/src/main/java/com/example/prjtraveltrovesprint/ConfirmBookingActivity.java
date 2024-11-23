package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.TripPackage;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;

public class ConfirmBookingActivity extends AppCompatActivity implements ActivityEssentials,
        View.OnClickListener {

    TextView txtTripDetails;
    Button btnReturn;

    Destination currentDestination;
    TripPackage tripPackage;

    private static final String LOG_TAG = "CONFIRM BOOKING ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirm_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.confirm_booking), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            currentDestination = ActivitiesUtils.retrieveCurrentDestination(this);
            tripPackage = ActivitiesUtils.retrieveTripPackage(this);
            initialize();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error while initializing confirm booking activity: " + e.getMessage(), e);
            finish();
        }
    }


    @Override
    public void initialize() {

        txtTripDetails = findViewById(R.id.booking_details);
        btnReturn = findViewById(R.id.return_btn_confirm_booking);

        String detailsText = "Your booking details: " + tripPackage.toString();

        txtTripDetails.setText(detailsText);

        btnReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int viewId = view.getId();

        if (viewId == R.id.return_btn_confirm_booking) {
            ActivitiesUtils.returnToViewAndClear(this,
                    new Intent(ConfirmBookingActivity.this, DestinationActivity.class));
        }
    }
}