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
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.Hotel;
import com.example.prjtraveltrovesprint.model.Room;
import com.example.prjtraveltrovesprint.model.TripPackage;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;
import com.example.prjtraveltrovesprint.utils.DataUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class HotelPackageActivity extends AppCompatActivity implements ActivityEssentials,
        AdapterView.OnItemSelectedListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static final String LOG_TAG = "HOTEL PACKAGE ACTIVITY";

    private RadioGroup rgHotel;
    private Spinner roomTypeSpinner, roomsCountSpinner;
    private TextView txtAvailableRooms, txtRoomCost, txtTotalCost;
    private Button nextBtn, returnBtn;

    private TripPackage tripPackage;
    private Hotel hotel;
    private Room selectedRoom;
    private Destination currentDestination;
    private Destination.DestinationType destinationType;

    private HashMap<Integer, String> hotelTypeOptions = new HashMap<>();
    private ArrayList<String> hotelRoomCountOptions = new ArrayList<>();
    private boolean hasSelectedRoomType = false, hasSelectedRoomCount = false;

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
            currentDestination = ActivitiesUtils.retrieveCurrentDestination(this);
            tripPackage = ActivitiesUtils.retrieveTripPackage(this);
            destinationType = currentDestination.getDestinationType();
            initialize();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error while initializing hotel package activity: " + e.getMessage(), e);
            finish();
        }
    }

    @Override
    public void initialize() {
        txtAvailableRooms = findViewById(R.id.txtAvailableRooms);
        txtRoomCost = findViewById(R.id.txtRoomCost);
        txtTotalCost = findViewById(R.id.txtTotalCostHotelPackage);
        rgHotel = findViewById(R.id.rg_hotel_package);
        roomTypeSpinner = findViewById(R.id.hotel_package_room_type_spinner);
        roomsCountSpinner = findViewById(R.id.hotel_package_room_count_spinner);
        nextBtn = findViewById(R.id.next_btn_hotel_package);
        returnBtn = findViewById(R.id.return_btn_hotel_package);

        ArrayList<Hotel> hotels = DataUtils.getHotelsList(destinationType);
        hotel = new Hotel();

        initializeHotelRadioGroup(hotels);
        rgHotel.setOnCheckedChangeListener(this);

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

        if (spinner.getId() == R.id.hotel_package_room_type_spinner) {
            hotelTypeOptions.clear();
            for (int i = 0; i < selectedHotel.getRooms().size(); i++) {
                Room hotelRoom = selectedHotel.getRooms().get(i);
                hotelTypeOptions.put(i, i + 1 + ". " + hotelRoom.getRoomType().toString());
            }

            ArrayAdapter<String> adapterHT = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, new ArrayList<>(hotelTypeOptions.values()));
            spinner.setAdapter(adapterHT);
            spinner.setOnItemSelectedListener(this);
        } else if (spinner.getId() == R.id.hotel_package_room_count_spinner) {
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
            Toast.makeText(this, "Please select a hotel, room type, and room count.", Toast.LENGTH_SHORT).show();
            return;
        }

        tripPackage.setHotel(selectedHotel);
        tripPackage.setRoom(selectedRoom);

        Intent intent = new Intent(HotelPackageActivity.this, AdditionalServicesActivity.class);
        intent.putExtra("destination_details", currentDestination);
        intent.putExtra("trip_package", tripPackage);
        startActivity(intent);
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

            addSpinnerItems(roomsCountSpinner);


        } else if (adapterId == R.id.hotel_package_room_count_spinner) {
            hasSelectedRoomCount = true;
            String selectedRoomCount = (String) adapterView.getItemAtPosition(i);
            int totalCost = selectedRoom.getCost() * Integer.parseInt(selectedRoomCount);
            txtTotalCost.setText("Total Cost: $" + totalCost);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void onClick(View view) {
        ArrayAdapter<String> adapterHT = (ArrayAdapter<String>) roomTypeSpinner.getAdapter();
        ArrayAdapter<String> adapterRC = (ArrayAdapter<String>) roomsCountSpinner.getAdapter();

        if (adapterHT != null && adapterRC != null) {
            adapterHT.clear();
            adapterRC.clear();
            adapterHT.notifyDataSetChanged();
            adapterRC.notifyDataSetChanged();
        }

        hotelTypeOptions.clear();
        hotelRoomCountOptions.clear();
        addSpinnerItems(roomTypeSpinner);
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
