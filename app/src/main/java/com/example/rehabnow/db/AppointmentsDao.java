package com.example.rehabnow.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rehabnow.data.Appointments;
import com.example.rehabnow.data.User;

import java.util.List;

@Dao
public interface AppointmentsDao {

    @Query("SELECT * FROM appointments WHERE doctor_email IS :email ")
    List <Appointments> getDoctorAppointments(String email);


    @Query("SELECT * FROM appointments WHERE user_email IS :email ")
    List <Appointments> getUserAppointments(String email);

    @Insert
    void insertAll(Appointments... appointments);

    @Delete
    void delete(Appointments appointments);
    @Update
    void updateUser(Appointments appointments);

}
