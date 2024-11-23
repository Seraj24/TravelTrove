package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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
import com.example.prjtraveltrovesprint.utils.LayoutUtils;

import java.util.ArrayList;
import java.util.List;

public class AdditionalServicesActivity extends AppCompatActivity implements ActivityEssentials {

    TripPackage tripPackage;
    Destination currentDestination;
    Button nextBtn, returnBtn;

    private static final String LOG_TAG = "ADDITIONAL SERVICES ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_additional_services);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.additional_services), (v, insets) -> {
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
            Log.e(LOG_TAG, "Error while initializing additional services activity: " + e.getMessage(), e);
            finish();
        }

    }

    public void initialize() {
        nextBtn = findViewById(R.id.next_btn_additional_services);
        returnBtn = findViewById(R.id.return_btn_additional_services);

        nextBtn.setOnClickListener(v ->
                launchAirlineSelectionActivity()
        );

        returnBtn.setOnClickListener(v ->
                ActivitiesUtils.returnToPreviousView(this)
                );
    }

    private void launchAirlineSelectionActivity() {
        List<String> services = new ArrayList<>();
        services.add("None");
        tripPackage.setAdditionalServices(services);
        Intent intent = new Intent(AdditionalServicesActivity.this, BookingSummary.class);
        intent.putExtra("destination_details", currentDestination);
        intent.putExtra("trip_package", tripPackage);
        startActivity(intent);
    }
}