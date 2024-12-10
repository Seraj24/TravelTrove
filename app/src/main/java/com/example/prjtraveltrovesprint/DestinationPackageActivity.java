package com.example.prjtraveltrovesprint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.Booking;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.TripPackage;
import com.example.prjtraveltrovesprint.ui.home.HomeFragment;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;
import com.example.prjtraveltrovesprint.utils.LayoutUtils;

import java.util.Objects;

public class DestinationPackageActivity extends AppCompatActivity implements ActivityEssentials {

    String lastActivity;
    TextView packageTitle;
    ImageView packageBanner;
    Button bookButton, returnButton;

    Booking booking;
    TripPackage tripPackage;
    Destination currentDestination;
    Destination.DestinationType destinationType;

    private static final ActivityName ACTIVITY_NAME = ActivityName.DESTINATION_PACKAGE;
    private static final String LOG_TAG = ACTIVITY_NAME + " ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_destination_package);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.destination_package), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            booking = ActivitiesUtils.retrieveBooking(this);
            currentDestination = booking.getCurrentDestination();
            destinationType = currentDestination.getDestinationType();

            lastActivity = getIntent().getStringExtra("last_activity");
            if (lastActivity == null) {
                Log.e(LOG_TAG, "Last Activity is null");
                finish();
            }



            initialize();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error while initializing destination "
                    + "package activity: " + e.getMessage(), e);
            finish();
        }
    }

    public void initialize() {
        packageTitle = findViewById(R.id.package_title);
        packageBanner = findViewById(R.id.package_image);
        bookButton = findViewById(R.id.book_button);
        returnButton = findViewById(R.id.trip_package_return_button);

        packageTitle.setText(currentDestination.getTitle());
        packageBanner.setImageResource(currentDestination.getImage());

        bookButton.setOnClickListener(v ->
                launchDateSelectionActivity()
                );

        returnButton.setOnClickListener(v ->
                returnToDestinationActivity()
        );
    }

    private void returnToDestinationActivity() {
        Intent intent;
        if (Objects.equals(lastActivity, "DestinationActivity")) {
            if (booking.getBookingType() != null) { booking.setBookingType(null); }
            intent = new Intent(DestinationPackageActivity.this, DestinationActivity.class);
            intent.putExtra("booking", booking);
            ActivitiesUtils.returnToViewAndClear(this, intent);
            return;
        }
        finish();

    }


    private void launchDateSelectionActivity() {
        booking.setBookingType(Booking.BookingType.PACKAGE);
        tripPackage = new TripPackage();
        Intent intent = new Intent(DestinationPackageActivity.this, DateSelectionActivity.class);
        intent.putExtra("booking", booking);
        intent.putExtra("trip_package", tripPackage);
        startActivity(intent);
    }

}