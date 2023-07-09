package com.example.rehabnow.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.example.rehabnow.Utils.Constants;

import java.io.Serializable;


@Entity
public class User implements Serializable {

    @ColumnInfo(name = "full_name")
    public String fullName;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "role")
    public String role;
    @ColumnInfo(name = "city")
    public String city;
    @ColumnInfo(name="location")
    public String location;
    @ColumnInfo(name="speciality")
    public String speciality;
    @ColumnInfo(name="qualification")
    public String qualification;
    @ColumnInfo(name="Province")
    public String province;


    public User(String fullName, @NonNull String email, String role, String location, String speciality,
                String qualification, String province) {
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.location = location;
        this.qualification = qualification;
        this.speciality = speciality;
        this.province = province;
    }

    ;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setLocation(String location){this.location=location;};
    public void setProvince(String province){this.province=province;}

    public String getCity() {
        return city;
    }

    public String getLocation() {
        return location;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getQualification() {
        return qualification;
    }
    public String getProvince(){return province;}

    public void setQualification(String qualification){this.qualification=qualification;};
    public void setSpeciality(String speciality){this.speciality=speciality;};

    public String getWelcomeMessage() {
        return "Welcome Dear " + role+ " " + getFullName() + "!";
    }
}
