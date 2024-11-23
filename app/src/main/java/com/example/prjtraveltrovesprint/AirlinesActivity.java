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

public class AirlinesActivity extends AppCompatActivity implements ActivityEssentials {

    RadioGroup rgAirline;
    TripPackage tripPackage;
    Airline airline;
    Destination currentDestination;
    Destination.DestinationType destinationType;
    Button nextBtn, returnBtn;
    int minPrice = 400, maxPrice = 1500;

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
        Random random = new Random();

        rgAirline = findViewById(R.id.rg_airline);
        nextBtn = findViewById(R.id.next_btn_airlines);
        returnBtn = findViewById(R.id.return_btn_airlines);

        airline = new Airline();

        String[] airlines = DataUtils.getAirlinesList();

        for (int i = 0; i < rgAirline.getChildCount(); i++) {
            LinearLayout airlineLayout = (LinearLayout) rgAirline.getChildAt(i);

            // Get the RadioButton inside the LinearLayout
            RadioButton radioButton = airlineLayout.findViewById(airlineLayout.getChildAt(0).getId());
            TextView textView = airlineLayout.findViewById(airlineLayout.getChildAt(1).getId());

            if (radioButton instanceof RadioButton) {
                radioButton.setText(airlines[i]);
            }
            if (textView instanceof TextView) {
                int randomPrice = random.nextInt(maxPrice) + minPrice;
                String newRandomPriceText = "$" + randomPrice;
                textView.setText(newRandomPriceText);
            }

        }

        nextBtn.setOnClickListener(v ->
                launchHotelPackageSelectionActivity()
        );

        returnBtn.setOnClickListener(v ->
                ActivitiesUtils.returnToPreviousView(this)
                );
    }

    private boolean airlineSelected() {
        boolean airlineSelected = false;
        for (int i = 0; i < rgAirline.getChildCount(); i++) {
            LinearLayout airlineLayout = (LinearLayout) rgAirline.getChildAt(i);
            RadioButton radioButton = airlineLayout.findViewById(airlineLayout.getChildAt(0).getId());

            if (radioButton != null && radioButton.isChecked()) {
                airline.setName(radioButton.getText().toString());

                TextView hotelCost = airlineLayout.findViewById(airlineLayout.getChildAt(1).getId());
                if (hotelCost != null) {
                    String costString = hotelCost.getText().toString().trim();
                    costString = costString.replaceAll("\\D", "");

                    if (!costString.isEmpty()) {
                        airline.setCost(Double.parseDouble(costString));
                    } else {
                        Log.e(LOG_TAG, "No valid digits found in cost.");
                    }
                }
                airlineSelected = true;
                break;
            }
        }
        return airlineSelected;
    }


    private void launchHotelPackageSelectionActivity() {
        if (airlineSelected()) {
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
}