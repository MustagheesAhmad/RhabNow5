package com.example.rehabnow.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.rehabnow.MyApplication;
import com.example.rehabnow.data.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Utils {
    public static String getRoleByEmail(Context context, String email) {
        String role = "null";
        User user = MyApplication.getInstance().getDb(context).userDao().findByEmail(email);
        if (user !=null) {
            return user.role;
        }
        return role;
    }

    public static User getCurrentUser(Context context, String email) {
        User user = MyApplication.getInstance().getDb(context).userDao().findByEmail(email);
        return user;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long[] getAppointments() {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Set the target time to the next date's start time
        LocalDate nextDate = now.toLocalDate().plusDays(1);
        LocalDateTime targetDateTimeNextDay = LocalDateTime.of(nextDate, LocalTime.MIN);

        // Calculate the difference in milliseconds
        long millisecondsUntilDateChange = ChronoUnit.MILLIS.between(now, targetDateTimeNextDay);

        // Calculate the interval between milliseconds
        long interval = millisecondsUntilDateChange / 30;

        // Create an array to store the milliseconds
        long[] millisecondsArray = new long[30];

        // Calculate the milliseconds for each interval
        for (int i = 0; i < 30; i++) {
            millisecondsArray[i] = now.plus(i * interval, ChronoUnit.MILLIS).toInstant(ZoneOffset.UTC).toEpochMilli();
        }

        // Print the array of milliseconds
        for (long milliseconds : millisecondsArray) {
            System.out.println(milliseconds);
        }
        return millisecondsArray;
    }

    @SuppressLint("NewApi")
    public static CharSequence[] getAppointmentsInFormat(String[] millisecondsStrings) {
        // Create an array to store the formatted date-time strings
        String[] formattedDateTimeStrings = new String[30];
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 30; i++) {
            LocalDateTime dateTime = LocalDateTime.ofInstant(
                    java.time.Instant.ofEpochMilli(Long.parseLong(millisecondsStrings[i])),
                    java.time.ZoneId.systemDefault()
            );
            formattedDateTimeStrings[i] = dateTime.format(formatter);
        }
        return formattedDateTimeStrings;
    }
}
