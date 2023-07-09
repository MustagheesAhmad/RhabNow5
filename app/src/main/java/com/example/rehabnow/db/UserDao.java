package com.example.rehabnow.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rehabnow.data.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE role LIKE 'PATIENT'")
    List<User> getAllPatients();

    @Query("SELECT * FROM user WHERE role LIKE 'DOCTOR'")
    List<User> getAllDoctors();

    @Query("SELECT * FROM user WHERE email IS :email LIMIT 1")
    User findByEmail(String email);

    @Query("SELECT * FROM user WHERE city IS :city ")
    List<User> findByCity(String city);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
    @Update
    void updateUser(User user);

}
