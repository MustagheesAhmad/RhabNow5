package com.example.rehabnow;

public class DoctorInformation {
    public DoctorInformation(String doctorPhotos) {
        this.doctorPhotos = doctorPhotos;
    }




    public String getDoctorPhotos() {
        return doctorPhotos;
    }

    public void setDoctorPhotos(String doctorPhotos) {
        this.doctorPhotos = doctorPhotos;
    }

    String doctorPhotos;
    String name;

    String location;
    String speciality;
    String education;

    public DoctorInformation() {
    }

    public DoctorInformation(String name,  String speciality, String education) {
        this.name = name;
        this.speciality = speciality;
        this.education = education;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getEducation() {
        return education;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "DoctorInformation{" +
                "doctorPhotos='" + doctorPhotos + '\'' +
                ", name='" + name + '\'' +
                ", speciality='" + speciality + '\'' +
                ", education='" + education + '\'' +
                '}';
    }

    public void setEducation(String education) {
        this.education = education;
    }


}
