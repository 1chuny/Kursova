package com.example.mbd.impl.inmemory;

import com.example.mbd.dao.AppointmentDao;
import com.example.mbd.model.Appointment;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryAppointmentDao implements AppointmentDao {
    private final InMemoryDataBase database;

    public InMemoryAppointmentDao(InMemoryDataBase database) {
        this.database = database;
    }

    @Override
    public Appointment get(Integer id) {
        return database.getAppointments().get(id);
    }

    @Override
    public Collection<Appointment> findAll() {
        return database.getAppointments().values();
    }

    @Override
    public void insert(Appointment appointment, boolean generateId) {
        if (generateId) {
            int id = database.getAppointments().size() + 1;
            appointment.setAppointmentId(id);
        }
        database.getAppointments().put(appointment.getAppointmentId(), appointment);
    }


    @Override
    public void addAppointment(Appointment appointment) {
        insert(appointment, true);
    }

    @Override
    public Appointment getAppointment(int appointmentId) {
        return get(appointmentId);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return database.getAppointments().values().stream().collect(Collectors.toList());
    }

    @Override
    public void update(Appointment appointment) {
        database.getAppointments().put(appointment.getAppointmentId(), appointment);
    }

    @Override
    public void delete(Appointment appointment) {
        database.getAppointments().remove(appointment.getAppointmentId());
    }
    
    @Override
    public void deleteAppointment(int appointmentId) {
        database.getAppointments().remove(appointmentId);
    }
}
