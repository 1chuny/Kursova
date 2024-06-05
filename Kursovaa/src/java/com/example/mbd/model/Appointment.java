package com.example.mbd.model;



public class Appointment {
    private int appointmentId;
    private User user;
    private String doctor;
    private String time;


    public Appointment(Integer appointmentId, User user , String doctor, String time) {
        this.appointmentId = appointmentId;
        this.user = user;
        this.doctor = doctor;
        this.time = time;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String text) {
        this.doctor = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
