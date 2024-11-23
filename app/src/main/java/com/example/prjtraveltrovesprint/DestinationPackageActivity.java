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
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.ui.home.HomeFragment;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;
import com.example.prjtraveltrovesprint.utils.LayoutUtils;

import java.util.Objects;

public class DestinationPackageActivity extends AppCompatActivity implements ActivityEssentials {

    String lastActivity;
    TextView packageTitle;
    ImageView packageBanner;
    Destination currentDestination;
    Destination.DestinationType destinationType;
    Button bookButton, returnButton;

    private static final String LOG_TAG = "DESTINATION PACKAGE ACTIVITY";

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

            currentDestination = ActivitiesUtils.retrieveCurrentDestination(this);
            lastActivity = getIntent().getStringExtra("last_activity");
            if (lastActivity == null) {
                Log.e(LOG_TAG, "Last Activity is null");
                finish();
            }

            destinationType = currentDestination.getDestinationType();

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
            intent = new Intent(DestinationPackageActivity.this, DestinationActivity.class);
            intent.putExtra("destination_details", currentDestination);
            ActivitiesUtils.returnToViewAndClear(this, intent);
            return;
        }
        finish();

    }


    private void launchDateSelectionActivity() {
        Intent intent = new Intent(DestinationPackageActivity.this, DateSelectionActivity.class);
        intent.putExtra("destination_details", currentDestination);
        startActivity(intent);
    }

}