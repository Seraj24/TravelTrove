package com.example.prjtraveltrovesprint.model;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prjtraveltrovesprint.ConfirmBookingActivity;
import com.example.prjtraveltrovesprint.DestinationActivity;
import com.example.prjtraveltrovesprint.R;
import com.example.prjtraveltrovesprint.TripPackageBookingSummary;
import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;

public abstract class BaseBookingSummaryActivity extends AppCompatActivity {

    protected TextView tvPackage, tvTripDate, tvTravelersCount, tvAirline, tvHotel, tvServices, tvTotalCost;
    protected Button nextBtn, returnBtn, cancelBtn;

    protected Booking booking;
    protected TripPackage tripPackage;
    protected Destination currentDestination;

    protected int travelersCount;
    protected double totalCost;

    private static final ActivityEssentials.ActivityName activityName = ActivityEssentials.ActivityName.BOOKING_SUMMARY;
    protected static final String LOG_TAG = activityName + " ACTIVITY";

    protected void initializeViews() {
        tvPackage = findViewById(R.id.txtPackage);
        tvTripDate = findViewById(R.id.txtTripDate);
        tvTravelersCount = findViewById(R.id.txtGuests);
        tvAirline = findViewById(R.id.txtAirline);
        tvHotel = findViewById(R.id.txtHotel);
        tvServices = findViewById(R.id.txtServices);
        tvTotalCost = findViewById(R.id.txtTotalCost);

        nextBtn = findViewById(R.id.next_button_booking_summary);
        returnBtn = findViewById(R.id.return_btn_booking_summary);
        cancelBtn = findViewById(R.id.cancel_btn_booking_summary);

        returnBtn.setOnClickListener(v -> returnToPreviousView());
        cancelBtn.setOnClickListener(v -> returnToBookFragment());
        nextBtn.setOnClickListener(v -> launchConfirmBookingActivity());
    }

    protected void hideUnnecessaryViews(Booking booking) {

        if (booking.getBookingType() == Booking.BookingType.HOTEL) {
            tvAirline.setVisibility(View.GONE);
            tvTravelersCount.setVisibility(View.GONE);
        }
        else if (booking.getBookingType() == Booking.BookingType.AIRLINES){
            tvHotel.setVisibility(View.GONE);
        }

        if (booking.getBookingType() != Booking.BookingType.PACKAGE) {
            tvServices.setVisibility(View.GONE);
        }
    }


    protected void returnToPreviousView() {
        ActivitiesUtils.returnToPreviousView(this);
    }

    protected void returnToBookFragment() {
        Intent intent = new Intent(BaseBookingSummaryActivity.this,
                DestinationActivity.class);
        intent.putExtra("booking", booking);
        intent.putExtra("last_activity", "BookingSummaryActivity");
        ActivitiesUtils.returnToViewAndClear(this, intent);
    }

    protected abstract void launchConfirmBookingActivity();

}
