package com.example.mbd.impl.inmemory;

import com.example.mbd.model.User;
import com.example.mbd.model.Doctor;
import com.example.mbd.model.Appointment;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Test data generator.
 */
public class InMemoryTestData {
    public static void generateTo(InMemoryDataBase database) {
        // Clear previous data
        database.getUsers().clear();
        database.getDoctors().clear();
        database.getSchedule().clear();
        database.getAppointments().clear();

        // Users
        User alice = new User(1, "Alice", "alice@example.com", "passwordhash", true, false);
        User bob = new User(2, "Bob", "bob@example.com", "passwordhash", true, false);
        User charlie = new User(3, "Charlie", "charlie@example.com", "passwordhash", false, true);
        User diana = new User(4, "Diana", "diana@example.com", "passwordhash", false, true);
        User evil = new User(5, "Evil Emperor", "evil@example.com", "passwordhash", false, true);
        List<User> users = Arrays.asList(alice, bob, charlie, diana, evil);
        users.forEach(user -> database.getUsers().put(user.getUserId(), user));

        // Doctors
        Doctor drSmith = new Doctor(1, "Dr. Smith", "Cardiology");
        Doctor drJones = new Doctor(2, "Dr. Jones", "Dermatology");
        Doctor drBrown = new Doctor(3, "Dr. Brown", "Neurology");
        List<Doctor> doctors = Arrays.asList(drSmith, drJones, drBrown);
        doctors.forEach(doctor -> database.getDoctors().put(doctor.getId(), doctor));

        // Schedule
        Map<String, Map<String, Doctor>> schedule = database.getSchedule();

        schedule.computeIfAbsent("Понеділок", k -> new HashMap<>()).put("9:00-12:00", drSmith);
        schedule.computeIfAbsent("Вівторок", k -> new HashMap<>()).put("12:00-15:00", drJones);
        schedule.computeIfAbsent("Середа", k -> new HashMap<>()).put("15:00-18:00", drBrown);
        schedule.computeIfAbsent("Четвер", k -> new HashMap<>()).put("9:00-12:00", drSmith);
        schedule.computeIfAbsent("П'ятниця", k -> new HashMap<>()).put("12:00-15:00", drJones);
        schedule.computeIfAbsent("Субота", k -> new HashMap<>()).put("15:00-18:00", drBrown);
        schedule.computeIfAbsent("Неділя", k -> new HashMap<>()).put("9:00-12:00", drSmith);
        
        // Appointments
        InMemoryAppointmentDao appointmentDao = new InMemoryAppointmentDao(database);
        Appointment charlieAppointment = new Appointment(1, charlie, "Dr. Smith", "10:00");
        appointmentDao.addAppointment(charlieAppointment);
    }
}
