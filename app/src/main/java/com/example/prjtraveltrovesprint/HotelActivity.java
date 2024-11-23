package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;
import com.example.prjtraveltrovesprint.utils.DataUtils;
import com.example.prjtraveltrovesprint.utils.LayoutUtils;

import java.util.ArrayList;
import java.util.Random;

/*public class HotelActivity extends AppCompatActivity implements ActivityEssentials {

    Destination currentDestination;
    Destination.DestinationType destinationType;
    RadioGroup rgHotel;
    int minPrice = 40, maxPrice = 400;
    Button nextBtn, cancelBtn;

    private static final String LOG_TAG = "HOTEL ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hotel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hotel), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            currentDestination = ActivitiesUtils.retrieveCurrentDestination(this);

            destinationType = currentDestination.getDestinationType();

            initialize();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error while initializing hotel activity: " + e.getMessage(), e);
            finish();
        }

    }

    public void initialize() {

        Random random = new Random();
        rgHotel = findViewById(R.id.rg_hotel);
        nextBtn = findViewById(R.id.next_btn_hotel);
        cancelBtn = findViewById(R.id.cancel_btn_hotel);

        ArrayList<String> hotels = DataUtils.getHotelsList(destinationType);

        for (int i = 0; i < rgHotel.getChildCount(); i++) {
            LinearLayout hotelLayout = (LinearLayout) rgHotel.getChildAt(i);

            // Get the RadioButton inside the LinearLayout
            RadioButton radioButton = (RadioButton) hotelLayout.findViewById(hotelLayout.getChildAt(0).getId());
            TextView textView = hotelLayout.findViewById(R.id.price1 + i);

            // Make sure the child is a radio button
            if (radioButton instanceof RadioButton) {
                radioButton.setText(hotels.get(i));

                if (textView != null) {
                    int randomPrice = random.nextInt(maxPrice - minPrice + 1) + minPrice;
                    String newText = "$" + randomPrice + " per night";
                    textView.setText(newText);
                }
            }


        }

        nextBtn.setOnClickListener(v ->
                launchAdditionalServicesSelectionActivity()
        );

        cancelBtn.setOnClickListener(v ->
                        ActivitiesUtils.returnToPreviousView(this)
                );
    }

    private void launchAdditionalServicesSelectionActivity() {
        Intent intent = new Intent(HotelActivity.this, AirlinesActivity.class);
        startActivity(intent);
    }
}
*/