package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdditionalServicesActivity extends AppCompatActivity implements ActivityEssentials,
        CompoundButton.OnCheckedChangeListener {

    CheckBox service1, service2, service3;
    Button nextBtn, returnBtn;

    Booking booking;
    TripPackage tripPackage;
    Destination currentDestination;
    HashMap<Integer, String> additionalServices = new HashMap<>();

    private static final ActivityName activityName = ActivityName.ADDITIONAL_SERVICES;
    private static final String LOG_TAG = activityName + " ACTIVITY";


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
            booking = ActivitiesUtils.retrieveBooking(this);
            currentDestination = booking.getCurrentDestination();
            tripPackage = ActivitiesUtils.retrieveTripPackage(this);

            initialize();
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Error while initializing additional services activity: " + e.getMessage(), e);
            finish();
        }

    }

    public void initialize() {

        service1 = findViewById(R.id.service1);
        service2 = findViewById(R.id.service2);
        service3 = findViewById(R.id.service3);

        nextBtn = findViewById(R.id.next_btn_additional_services);
        returnBtn = findViewById(R.id.return_btn_additional_services);

        service1.setOnCheckedChangeListener(this);
        service2.setOnCheckedChangeListener(this);
        service3.setOnCheckedChangeListener(this);

        nextBtn.setOnClickListener(v ->
                launchAirlineSelectionActivity()
        );

        returnBtn.setOnClickListener(v ->
                ActivitiesUtils.returnToPreviousView(this)
                );
    }

    private void launchAirlineSelectionActivity() {
        List<String> services = new ArrayList<>(additionalServices.values());
        tripPackage.setAdditionalServices(services);
        Intent intent = new Intent(AdditionalServicesActivity.this, TripPackageBookingSummary.class);
        intent.putExtra("booking", booking);
        intent.putExtra("trip_package", tripPackage);
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        Map<Integer, Integer> serviceMapping = new HashMap<>();
        serviceMapping.put(R.id.service1, 1);
        serviceMapping.put(R.id.service2, 2);
        serviceMapping.put(R.id.service3, 3);

        Integer serviceKey = serviceMapping.get(compoundButton.getId());

        if (serviceKey != null) {
            if (isChecked) {
                additionalServices.put(serviceKey, compoundButton.getText().toString());
            } else {
                additionalServices.remove(serviceKey);
            }
        }
    }

}