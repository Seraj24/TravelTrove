package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.BookingHistory;
import com.example.prjtraveltrovesprint.model.BookingHistoryAdapter;
import com.example.prjtraveltrovesprint.model.UserSession;
import com.example.prjtraveltrovesprint.ui.account.AccountFragment;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryActivity extends AppCompatActivity implements ActivityEssentials,
        ValueEventListener {

    Button btnReturn;

    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user");

    private ListView lvBookingHistory;
    private List<BookingHistory> bookingHistoryList;

    private static final ActivityName ACTIVITY_NAME = ActivityName.BOOKING_HISTORY;
    private static final String LOG_TAG = ACTIVITY_NAME + " ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initialize();
    }

    @Override
    public void initialize() {

        btnReturn = findViewById(R.id.btn_return_BH);
        lvBookingHistory = findViewById(R.id.lvBookingHistory);

        bookingHistoryList = new ArrayList<>();

        btnReturn.setOnClickListener(v -> ActivitiesUtils.returnToPreviousView(this));

        fetchBookingHistory();

    }

    private void fetchBookingHistory() {
        int userId = UserSession.getInstance().getUser().getId();
        DatabaseReference userBookingsRef = userRef.child(String.valueOf(userId)).child("bookingHistory");

        userBookingsRef.addValueEventListener(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        bookingHistoryList.clear();

        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

            String destination = dataSnapshot.child("destination").getValue(String.class);
            String bookingType = dataSnapshot.child("bookingType").getValue(String.class);
            String bookingDetails = dataSnapshot.child("bookingDetails").getValue(String.class);


            if (destination != null && bookingType != null && bookingDetails != null) {

            }

            BookingHistory bookingHistory = new BookingHistory(destination, bookingType, bookingDetails);
            bookingHistoryList.add(bookingHistory);
        }


        BookingHistoryAdapter adapter = new BookingHistoryAdapter(BookingHistoryActivity.this, bookingHistoryList);
        lvBookingHistory.setAdapter(adapter);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Log.e(LOG_TAG, "Failed to read booking history: " + error.getMessage());
        Toast.makeText(BookingHistoryActivity.this, "Failed to load booking history", Toast.LENGTH_SHORT).show();
    }
}