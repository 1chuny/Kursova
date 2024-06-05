package com.example.mbd.impl.inmemory;

import com.example.mbd.dao.DaoFactory;
import com.example.mbd.model.Appointment;
import com.example.mbd.model.User;
import com.example.mbd.model.Doctor;

import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;

public class InMemoryDataBase {
    Map<Integer, Appointment> appointments;
    Map<Integer, User> users;
    Map<Integer, Doctor> doctors;
    Map<String, Map<String, Doctor>> schedule;

    public InMemoryDataBase() {
        appointments = new TreeMap<>();
        users = new TreeMap<>();
        doctors = new TreeMap<>();
        schedule = new HashMap<>();
    }

    public DaoFactory getDaoFactory() {
        return new InMemoryDaoFactory(this);
    }

    public Map<Integer, Appointment> getAppointments() {
        return appointments;
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public Map<Integer, Doctor> getDoctors() {
        return doctors;
    }

    public Map<String, Map<String, Doctor>> getSchedule() {
        return schedule;
    }
}
