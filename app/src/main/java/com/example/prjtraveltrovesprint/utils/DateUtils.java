package com.example.prjtraveltrovesprint.utils;


import android.util.Log;

import com.example.prjtraveltrovesprint.model.Date;

import java.util.Calendar;
import java.util.Random;

public class DateUtils {

    public static int getDaysCountBetweenDates(Date date1, Date date2) {

        if (date1.compareTo(date2) > 0) {
            Log.e("DATE UTILS", "Date 1 cannot be earlier than date 2");
            return -1;
        }

        int checkMonth = 28;
        int finalDay = date1.getDay();

        // if both dates are in the same month just decrement the bigger - smaller
        if (date1.getMonth() == date2.getMonth()) {
            return date2.getDay() - date1.getDay();
        }

        // In case they are in different months, then, we have to check the ending day, decrement it
        // from days count from date 1 - ending day. Finally, we we add the remaining days from date 2
        while (finalDay < checkMonth) {
            finalDay++;
        }
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int addDifference = 0;
        int daysCountOfThisMonth = daysInMonth[date1.getMonth() - 1];
        // Ensure to store the gap to add it later to the new month
        if (daysCountOfThisMonth != finalDay) {
            finalDay = daysCountOfThisMonth - date1.getDay();
            finalDay += date2.getDay();
        }

        return finalDay;
    }
}
