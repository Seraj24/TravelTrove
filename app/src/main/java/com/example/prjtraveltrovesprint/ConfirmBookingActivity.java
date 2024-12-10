package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.AirlineBooking;
import com.example.prjtraveltrovesprint.model.Booking;
import com.example.prjtraveltrovesprint.model.BookingHistory;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.HotelBooking;
import com.example.prjtraveltrovesprint.model.TripPackage;
import com.example.prjtraveltrovesprint.model.UserSession;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmBookingActivity extends AppCompatActivity implements ActivityEssentials,
        View.OnClickListener {

    TextView txtTripDetails;
    Button btnReturn;

    Booking booking;
    Destination currentDestination;
    TripPackage tripPackage;
    HotelBooking hotelBooking;
    AirlineBooking airlinesBooking;

    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user");

    private static final ActivityName ACTIVITY_NAME = ActivityName.CONFIRM_BOOKING;
    private static final String LOG_TAG = ACTIVITY_NAME + " ACTIVITY";

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
            booking = ActivitiesUtils.retrieveBooking(this);
            currentDestination = booking.getCurrentDestination();
            switch (booking.getBookingType()) {
                case PACKAGE:
                    tripPackage = ActivitiesUtils.retrieveTripPackage(this);
                    break;
                case HOTEL:
                    hotelBooking = ActivitiesUtils.retrieveHotelBooking(this);
                    break;
                case AIRLINES:
                    airlinesBooking = ActivitiesUtils.retrieveAirlineBooking(this);
                    break;
            }

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
        String detailsText = "Your booking details: \n";

        switch (booking.getBookingType()) {
            case PACKAGE:
                detailsText = detailsText + tripPackage.toString();
                break;
            case HOTEL:
                detailsText = detailsText + hotelBooking.toString();
                break;
            case AIRLINES:
                detailsText = detailsText + airlinesBooking.toString();
                break;
        }

        saveBookingToHistory();


        txtTripDetails.setText(detailsText);

        btnReturn.setOnClickListener(this);
    }

    private void saveBookingToHistory() {
        int userId = UserSession.getInstance().getUser().getId();
        DatabaseReference userBookingsRef = userRef.child(String.valueOf(userId)).child("bookingHistory");

        String bookingDetails = getBookingDetailsAsString();
        BookingHistory bookingHistory = new BookingHistory(currentDestination.getTitle(),
                booking.getBookingType().toString(), bookingDetails);

        userBookingsRef.push().setValue(bookingHistory)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Booking successfully saved to booking history!"
                            , Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error saving booking to booking history."
                            , Toast.LENGTH_SHORT).show();
                    Log.e(LOG_TAG, "Error saving booking to booking history", e);
                });
    }

    private String getBookingDetailsAsString() {
        StringBuilder details = new StringBuilder();
        switch (booking.getBookingType()) {
            case PACKAGE:
                details.append(tripPackage.toString());
                break;
            case HOTEL:
                details.append(hotelBooking.toString());
                break;
            case AIRLINES:
                details.append(airlinesBooking.toString());
                break;
        }
        return details.toString();
    }

    @Override
    public void onClick(View view) {

        int viewId = view.getId();

        if (viewId == R.id.return_btn_confirm_booking) {
            booking.setBookingType(null);
            Intent intent = new Intent(ConfirmBookingActivity.this, DestinationActivity.class);
            intent.putExtra("booking", booking);
            intent.putExtra("last_activity", ACTIVITY_NAME);
            ActivitiesUtils.returnToViewAndClear(this, intent);

        }
    }
}