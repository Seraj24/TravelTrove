package com.example.prjtraveltrovesprint.interfaces;

public interface ActivityEssentials {
    enum ActivityName {
        SIGN_IN, SIGN_UP, PROFILE_MANAGEMENT, SAVED_DESTINATIONS, DESTINATION, DESTINATION_PACKAGE,
        DATE_SELECTION, TRAVELERS_NUMBER,AIRLINES, HOTEL, ADDITIONAL_SERVICES, BOOKING_SUMMARY,
        CONFIRM_BOOKING, BOOKING_HISTORY
    }

    void initialize();
}
