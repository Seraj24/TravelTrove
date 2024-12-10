package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.Booking;
import com.example.prjtraveltrovesprint.model.Date;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.Hotel;
import com.example.prjtraveltrovesprint.model.HotelBooking;
import com.example.prjtraveltrovesprint.model.Room;
import com.example.prjtraveltrovesprint.model.TripPackage;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;
import com.example.prjtraveltrovesprint.utils.DataUtils;
import com.example.prjtraveltrovesprint.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class HotelPackageActivity extends AppCompatActivity implements ActivityEssentials,
        AdapterView.OnItemSelectedListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private RadioGroup rgHotel;
    private Spinner spinnerRoomType, spinnerRoomsCount, spinnerCheckIn, spinnerCheckOut;
    private TextView txtTitle, txtAvailableRooms, txtRoomCost, txtTotalCost;
    private Button nextBtn, returnBtn;

    Booking booking;
    HotelBooking hotelBooking = new HotelBooking();
    private TripPackage tripPackage;
    private Room selectedRoom;
    private Destination currentDestination;
    private Destination.DestinationType destinationType;
    private Date selectedCheckInDate, selectedCheckOutDate;
    int roomsCount = 0;

    private HashMap<Integer, String> hotelTypeOptions = new HashMap<>();
    private ArrayList<String> hotelRoomCountOptions = new ArrayList<>();
    private ArrayList<Date> availableCheckInDates = new ArrayList<>(),
            availableCheckOutDates = new ArrayList<>();
    private boolean hasSelectedRoomType = false, hasSelectedRoomCount = false;

    private static final ActivityName ACTIVITY_NAME = ActivityName.HOTEL;
    private static final String LOG_TAG = ACTIVITY_NAME + " ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hotel_package);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hotel_package), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            booking = ActivitiesUtils.retrieveBooking(this);
            currentDestination = booking.getCurrentDestination();
            destinationType = currentDestination.getDestinationType();

            if (booking.getBookingType() == Booking.BookingType.PACKAGE) {
                tripPackage = ActivitiesUtils.retrieveTripPackage(this);
            }

            initialize();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error while initializing hotel package activity: " + e.getMessage(), e);
            finish();
        }
    }

    @Override
    public void initialize() {
        txtTitle = findViewById(R.id.txtHotelPackageTitle);
        txtAvailableRooms = findViewById(R.id.txtAvailableRooms);
        txtRoomCost = findViewById(R.id.txtRoomCost);
        txtTotalCost = findViewById(R.id.txtTotalCostHotelPackage);
        rgHotel = findViewById(R.id.rg_hotel_package);
        spinnerRoomType = findViewById(R.id.hotel_package_room_type_spinner);
        spinnerRoomsCount = findViewById(R.id.hotel_package_room_count_spinner);
        spinnerCheckIn = findViewById(R.id.spinnerCheckIn);
        spinnerCheckOut = findViewById(R.id.spinnerCheckOut);
        nextBtn = findViewById(R.id.next_btn_hotel_package);
        returnBtn = findViewById(R.id.return_btn_hotel_package);

        if (booking.getBookingType() == Booking.BookingType.HOTEL) {
            txtTitle.setText("Book Your Hotel");
        }

        ArrayList<Hotel> hotels = DataUtils.getHotelsList(destinationType);

        initializeHotelRadioGroup(hotels);
        rgHotel.setOnCheckedChangeListener(this);

        addSpinnerItems(spinnerCheckIn);
        addSpinnerItems(spinnerCheckOut);

        selectedCheckInDate = availableCheckInDates.get(0);
        selectedCheckOutDate = availableCheckOutDates.get(0);

        nextBtn.setOnClickListener(v -> launchAdditionalServicesActivity());
        returnBtn.setOnClickListener(v -> ActivitiesUtils.returnToPreviousView(this));
    }

    private void initializeHotelRadioGroup(ArrayList<Hotel> hotels) {
        for (int i = 0; i < rgHotel.getChildCount(); i++) {
            View child = rgHotel.getChildAt(i);
            if (child instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) child;
                radioButton.setText(hotels.get(i).getName());
                radioButton.setTag(hotels.get(i));
                radioButton.setOnClickListener(this);
            }
        }
    }

    private void addSpinnerItems(Spinner spinner) {
        Hotel selectedHotel = hotelSelected();
        int spinnerId = spinner.getId();

        if (spinnerId == R.id.hotel_package_room_type_spinner) {
            hotelTypeOptions.clear();
            for (int i = 0; i < selectedHotel.getRooms().size(); i++) {
                Room hotelRoom = selectedHotel.getRooms().get(i);
                hotelTypeOptions.put(i, i + 1 + ". " + hotelRoom.getRoomType().toString());
            }

            ArrayAdapter<String> adapterHT = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, new ArrayList<>(hotelTypeOptions.values()));
            spinner.setAdapter(adapterHT);
            spinner.setOnItemSelectedListener(this);
        } else if (spinnerId == R.id.hotel_package_room_count_spinner) {
            hotelRoomCountOptions.clear();
            if (selectedRoom != null) {
                for (int i = 0; i < selectedRoom.getAvailableRooms(); i++) {
                    hotelRoomCountOptions.add(i + 1 + "");
                }

                ArrayAdapter<String> adapterRC = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item, hotelRoomCountOptions);
                spinner.setAdapter(adapterRC);
                spinner.setOnItemSelectedListener(this);
            }
        }
        else if (spinnerId == R.id.spinnerCheckIn) {
            if (booking.getBookingType() == Booking.BookingType.PACKAGE) {
                availableCheckInDates.add(tripPackage.getAirlineBooking().getDepartureDate());
            }
            else {
                availableCheckInDates = generateDateList();
            }
            ArrayAdapter<Date> adapterCI = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, availableCheckInDates);
            spinner.setAdapter(adapterCI);
            spinner.setOnItemSelectedListener(this);

        }
        else if (spinnerId == R.id.spinnerCheckOut) {
            if (booking.getBookingType() == Booking.BookingType.PACKAGE) {
                availableCheckOutDates.add(tripPackage.getAirlineBooking().getReturnDate());
            }
            else {
                availableCheckOutDates = generateDateList();
            }

            ArrayAdapter<Date> adapterCO = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, availableCheckOutDates);
            spinner.setAdapter(adapterCO);
            spinner.setOnItemSelectedListener(this);

        }
    }

    private Hotel hotelSelected() {
        int selectedId = rgHotel.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            return (Hotel) selectedRadioButton.getTag();
        }
        return null;
    }

    private void launchAdditionalServicesActivity() {
        Hotel selectedHotel = hotelSelected();
        if (selectedHotel == null || !hasSelectedRoomType || !hasSelectedRoomCount) {
            Toast.makeText(this, "Please select a hotel, room type, and room count.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        hotelBooking.setHotel(selectedHotel);
        hotelBooking.setRoom(selectedRoom);
        Intent intent = null;
        switch (booking.getBookingType()) {
            case PACKAGE:
                intent = new Intent(HotelPackageActivity.this,
                        AdditionalServicesActivity.class);
                tripPackage.setHotelBooking(hotelBooking);
                intent.putExtra("trip_package", tripPackage);
                break;
            case HOTEL:
                intent = new Intent(HotelPackageActivity.this,
                        HotelBookingSummaryActivity.class);
                intent.putExtra("hotel_booking", hotelBooking);
                break;
            default:
                break;
        }

        intent.putExtra("booking", booking);

        startActivity(intent);
    }

    private ArrayList<Date> generateDateList() {
        ArrayList<Date> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 10; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date date = new Date(calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
            dateList.add(date);
        }
        return dateList;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int adapterId = adapterView.getId();
        if (adapterId == R.id.hotel_package_room_type_spinner) {
            hasSelectedRoomType = true;
            Room hotelRoom = hotelSelected().getRooms().get(i);
            selectedRoom = hotelRoom;

            String availableRoomsText = "Available Rooms: " + hotelRoom.getAvailableRooms();
            String roomCostTxt = "Room Cost: $" + hotelRoom.getCost();
            txtAvailableRooms.setText(availableRoomsText);
            txtRoomCost.setText(roomCostTxt);


            addSpinnerItems(spinnerRoomsCount);


        } else if (adapterId == R.id.hotel_package_room_count_spinner) {
            hasSelectedRoomCount = true;
            String selectedRoomCount = (String) adapterView.getItemAtPosition(i);

            roomsCount = Integer.parseInt(selectedRoomCount);
            updateCost(roomsCount);
            hotelBooking.setRoomsCount(Integer.parseInt(selectedRoomCount));
        }
        else if (adapterId == R.id.spinnerCheckIn) {
            Date selectedDate = availableCheckInDates.get(i);
            selectedCheckInDate = selectedDate;
            if (selectedDate.compareTo(selectedCheckOutDate) > 0) {
                Toast.makeText(this, "Check In date cannot be after Check Out date, " +
                                "please selected valid date"
                        , Toast.LENGTH_SHORT).show();

                selectedCheckInDate = availableCheckInDates.get(0);

                spinnerCheckIn.setSelection(0);

                return;
            }

            hotelBooking.setCheckInDate(selectedDate);
        }
        else if (adapterId == R.id.spinnerCheckOut) {
            Date selectedDate = availableCheckOutDates.get(i);
            selectedCheckOutDate = selectedDate;
            if (selectedDate.compareTo(selectedCheckInDate) < 0) {
                Toast.makeText(this, "Check Out date cannot be before Check In date, " +
                                "please selected valid date"
                        , Toast.LENGTH_SHORT).show();

                selectedCheckOutDate = availableCheckOutDates.get(0);
                selectedCheckInDate = availableCheckInDates.get(0);

                spinnerCheckIn.setSelection(0);
                spinnerCheckOut.setSelection(0);

                return;
            }
            if (selectedRoom != null) {
                updateCost(roomsCount);
            }

            hotelBooking.setCheckOutDate(selectedDate);
        }
    }

    private void updateCost(int selectedRoomCount) {
        int MINIMUM_STAY_DAYS = 1;
        int calculateStayDays = DateUtils.getDaysCountBetweenDates(selectedCheckInDate,
                selectedCheckOutDate);
        int stayDays = calculateStayDays == 0
                ? MINIMUM_STAY_DAYS
                : calculateStayDays;
        Toast.makeText(this, "Stay Days: " + stayDays
                , Toast.LENGTH_SHORT).show();
        int totalCost = selectedRoom.getCost() * selectedRoomCount
                * stayDays;
        String totalCostString = "Total Cost: $" + totalCost;
        txtTotalCost.setText(totalCostString);
        hotelBooking.setTotalCost(totalCost);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void onClick(View view) {
        ArrayAdapter<String> adapterHT = (ArrayAdapter<String>) spinnerRoomType.getAdapter();
        ArrayAdapter<String> adapterRC = (ArrayAdapter<String>) spinnerRoomsCount.getAdapter();

        if (adapterHT != null && adapterRC != null) {
            adapterHT.clear(); adapterRC.clear();
            adapterHT.notifyDataSetChanged();
            adapterRC.notifyDataSetChanged();
        }

        hotelTypeOptions.clear();
        hotelRoomCountOptions.clear();
        addSpinnerItems(spinnerRoomType);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        hasSelectedRoomType = false;
        hasSelectedRoomCount = false;
        selectedRoom = null;

        txtAvailableRooms.setText("Please select a room type first");
        txtRoomCost.setText("Room Cost:");
    }
}
