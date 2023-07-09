package com.example.rehabnow.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Appointments {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "time")
    public long time;

    @NonNull
    @ColumnInfo(name = "doctor_email")
    public String doctorEmail;

    @NonNull
    @ColumnInfo(name = "user_email")
    public String userEmail;

    @NonNull
    @ColumnInfo(name = "type")
    public String type;

    public Appointments(long time, String doctorEmail, String userEmail, String type) {
        this.time = time;
        this.doctorEmail = doctorEmail;
        this.userEmail = userEmail;
        this.type = type;
    }

    @NonNull
    public long getTime() {
        return time;
    }

    public void setTime(@NonNull long time) {
        this.time = time;
    }

    @NonNull
    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(@NonNull String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    @NonNull
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@NonNull String userEmail) {
        this.userEmail = userEmail;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }
}

