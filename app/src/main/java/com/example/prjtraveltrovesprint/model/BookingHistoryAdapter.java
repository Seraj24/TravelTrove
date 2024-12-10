package com.example.prjtraveltrovesprint.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.prjtraveltrovesprint.R;
import com.example.prjtraveltrovesprint.model.BookingHistory;

import java.util.List;

public class BookingHistoryAdapter extends ArrayAdapter<BookingHistory> {

    private Context context;
    private List<BookingHistory> bookingHistoryList;

    public BookingHistoryAdapter(Context context, List<BookingHistory> bookingHistoryList) {
        super(context, 0, bookingHistoryList);
        this.context = context;
        this.bookingHistoryList = bookingHistoryList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_booking_history, parent, false);
        }

        BookingHistory bookingHistory = getItem(position);

        TextView txtDestination = convertView.findViewById(R.id.txtDestination);
        TextView txtBookingType = convertView.findViewById(R.id.txtBookingType);
        TextView txtBookingDetails = convertView.findViewById(R.id.txtBookingDetails);

        if (bookingHistory != null) {
            txtDestination.setText(bookingHistory.getDestination());
            txtBookingType.setText(bookingHistory.getBookingType());
            txtBookingDetails.setText(bookingHistory.getBookingDetails());
        }

        return convertView;
    }
}
