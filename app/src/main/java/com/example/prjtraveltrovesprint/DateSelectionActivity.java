package com.example.prjtraveltrovesprint;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.AirlineBooking;
import com.example.prjtraveltrovesprint.model.Booking;
import com.example.prjtraveltrovesprint.model.Date;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.TripPackage;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;
import com.example.prjtraveltrovesprint.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class DateSelectionActivity extends AppCompatActivity implements ActivityEssentials, View.OnClickListener {

    Booking booking;
    AirlineBooking airlineBooking;
    TripPackage tripPackage;
    Destination currentDestination;
    Destination.DestinationType destinationType;
    Button nextBtn, returnBtn, departureDateBtn, returnDateBtn;
    Date depDate1, depDate2, returnDate1, returnDate2;
    Date selectedDepartureDate, selectedReturnDate;
    ArrayList<Integer> generatedDays = new ArrayList<>();

    private static final ActivityName ACTIVITY_NAME = ActivityName.DATE_SELECTION;
    private static final String LOG_TAG = ACTIVITY_NAME + " ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_date_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.date_selection), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            booking = ActivitiesUtils.retrieveBooking(this);
            currentDestination = booking.getCurrentDestination();
            destinationType = currentDestination.getDestinationType();

            initialize();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error while initializing date selection activity: " + e.getMessage(), e);
            finish();
        }
    }

    @Override
    public void initialize() {
        departureDateBtn = findViewById(R.id.departure_date_package);
        returnDateBtn = findViewById(R.id.return_date_package);
        nextBtn = findViewById(R.id.next_btn_date_selection);
        returnBtn = findViewById(R.id.return_btn_date_selection);

        depDate1 = getRandomDateWithinMonth(Date.DateType.DEPARTURE);
        depDate2 = getRandomDateWithinMonth(Date.DateType.DEPARTURE);
        returnDate1 = getRandomDateWithinMonth(Date.DateType.RETURN);
        returnDate2 = getRandomDateWithinMonth(Date.DateType.RETURN);

        departureDateBtn.setOnClickListener(this);
        returnDateBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        returnBtn.setOnClickListener(this);
    }

    private void launchGuestsCountActivity() {
        Intent intent = new Intent(DateSelectionActivity.this, TravelersNumberActivity.class);
        intent.putExtra("booking", booking);
        intent.putExtra("trip_package", tripPackage);
        startActivity(intent);
    }

    private void launchAirlinesActivity() {
        Intent intent = new Intent(DateSelectionActivity.this, AirlinesActivity.class);
        intent.putExtra("booking", booking);
        intent.putExtra("airline_booking", airlineBooking);
        startActivity(intent);
    }

    private void handleNext() {
        Date departureDate = selectedDepartureDate;
        Date returnDate = selectedReturnDate;


        if (departureDate == null) {
            Toast.makeText(this, "Please select a departure date", Toast.LENGTH_LONG).show();
            return;
        }
        if (returnDate == null) {
            Toast.makeText(this, "Please select a return date", Toast.LENGTH_LONG).show();
            return;
        }
        if (departureDate.compareTo(returnDate) > 0 || departureDate.equals(returnDate)) {
            Toast.makeText(this, "Please choose valid dates, " +
                            "return date must be after departure!", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        int totalDays = DateUtils.getDaysCountBetweenDates(departureDate, returnDate);

        if (totalDays < 0) {
            Toast.makeText(this, "Error occurred while getting the days count",
                    Toast.LENGTH_LONG).show();
            return;
        }

        airlineBooking = new AirlineBooking();
        airlineBooking.setDepartureDate(departureDate);
        airlineBooking.setReturnDate(returnDate);

        if (booking.getBookingType() == Booking.BookingType.PACKAGE) {
            tripPackage = new TripPackage();
            tripPackage.setDate(departureDate.toString() + " - " + returnDate.toString());
            tripPackage.setDays(totalDays);
            tripPackage.setAirlineBooking(airlineBooking);
            launchGuestsCountActivity();
        }
        else {

            launchAirlinesActivity();
        }

    }

    private void showDateSelection(Date.DateType dateType, Button btn) {

        View dialogView = getLayoutInflater().inflate(R.layout.date_select, null);

        final Date selectedDate1 = (dateType == Date.DateType.DEPARTURE) ? depDate1 : returnDate1;
        final Date selectedDate2 = (dateType == Date.DateType.DEPARTURE) ? depDate2 : returnDate2;


        Dialog dialog = new Dialog(this);
        dialog.setContentView(dialogView);
        dialog.setTitle("Select a Date");

        Button btnDate1 = dialogView.findViewById(R.id.btnDate1);
        btnDate1.setText(selectedDate1.toString());
        btnDate1.setOnClickListener(v -> selectDateAndClose(dialog, selectedDate1));

        Button btnDate2 = dialogView.findViewById(R.id.btnDate2);
        btnDate2.setText(selectedDate2.toString());
        btnDate2.setOnClickListener(v -> selectDateAndClose(dialog, selectedDate2));

        dialog.show();
    }

    private void selectDateAndClose(Dialog dialog, Date selectedDate) {
        Button btn = selectedDate.getDateType() == Date.DateType.DEPARTURE ?
                departureDateBtn : returnDateBtn;
        configureDateButton(btn, R.color.green, selectedDate.toString());
        AssignUserSelection(selectedDate);
        dialog.dismiss();
    }

    /* Save selected date from the dialog into a class level variables selectedDepartureDate
       and selectedReturnDate depending on the selected date type
     */
    private void AssignUserSelection(Date selectedDate) {

        if (selectedDate.getDateType() == Date.DateType.DEPARTURE) {
            selectedDepartureDate = selectedDate;
        }
        // Using else if to avoid assigning dates of type OTHER
        else if (selectedDate.getDateType() == Date.DateType.RETURN) {
            selectedReturnDate = selectedDate;
        }
    }

    private void configureDateButton(@NonNull Button btn, int colorId, String text) {
        btn.setBackgroundColor(getResources().getColor(colorId));
        btn.setText(text.toString());
    }

    private Date getRandomDateWithinMonth(Date.DateType dateType) {
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        final int MAX_DAY = 28, WEEK_GAP = 7;

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int randomDay = random.nextInt(MAX_DAY - day + 1) + day;


        // Make sure to generate a return days that has a week gap from the departure
        if (dateType == Date.DateType.RETURN) {
            // the trip duration is one week. So, each return date is 7 days ahead
            // of each departure date accordingly
            int elementIndexToCompare = 0;
            if (generatedDays.size() >= 3) {
                elementIndexToCompare = 1;
            }
            int gapBetweenTwoDays = randomDay - generatedDays.get(elementIndexToCompare);
            if (gapBetweenTwoDays < WEEK_GAP) {
                int distance = WEEK_GAP - gapBetweenTwoDays;
                randomDay += distance;
            }
        }
        else if (depDate2 != null && dateType == Date.DateType.DEPARTURE){
            while (depDate1 == depDate2) {
                randomDay = random.nextInt(MAX_DAY - day + 1) + day;
            }
        }

        // Simple logic to handle transactions to next month, while keeping a gap between dep and return
        if (randomDay >= 28) {
            int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            int addDifference = 0;
            int daysCountOfThisMonth = daysInMonth[month - 1];
            // Ensure to store the gap to add it later to the new month
            if (daysCountOfThisMonth <= randomDay) {
                addDifference = randomDay - daysCountOfThisMonth;
                // Set the random day to the count of its month
                randomDay = daysCountOfThisMonth;
                // Decrement random day of its month which equal to 0, and the gap
                randomDay += -daysCountOfThisMonth + addDifference;
                randomDay = randomDay == 0 ? randomDay + 1 : randomDay;
                month++;

                // Resets to january
                if (month > 12) {
                    month = 1;
                    year++;
                }
            }


        }

        generatedDays.add(randomDay);

        return new Date(randomDay, month, year, dateType);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.next_btn_date_selection) {
            handleNext();
        } else if (viewId == R.id.departure_date_package) {
            showDateSelection(Date.DateType.DEPARTURE, departureDateBtn);
        } else if (viewId == R.id.return_date_package) {
            showDateSelection(Date.DateType.RETURN, returnDateBtn);
        } else {
            ActivitiesUtils.returnToPreviousView(this);
        }
    }
}
