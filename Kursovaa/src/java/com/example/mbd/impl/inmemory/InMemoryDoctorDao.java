package com.example.mbd.impl.inmemory;

import com.example.mbd.dao.DoctorDao;
import com.example.mbd.model.Doctor;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class InMemoryDoctorDao implements DoctorDao {
    

    private final InMemoryDataBase database;

    public InMemoryDoctorDao(InMemoryDataBase database) {
        this.database = database;
    }

    @Override
    public Doctor get(Integer id) {
        return database.getDoctors().get(id);
    }

    @Override
    public Collection<Doctor> findAll() {
        return database.getDoctors().values();
    }

    @Override
    public void insert(Doctor doctor, boolean generateId) {
        if (generateId) {
            int id = database.getDoctors().size() + 1;
            doctor.setId(id);
        }
        database.getDoctors().put(doctor.getId(), doctor);
    }

    @Override
    public void delete(Doctor doctor) {
        database.getDoctors().remove(doctor.getId());
    }

    @Override
    public void update(Doctor doctor) {
        database.getDoctors().put(doctor.getId(), doctor);
    }

    @Override
    public Doctor getDoctor(String day, String timeInterval) {
        return database.getSchedule().getOrDefault(day, new HashMap<>()).get(timeInterval);
    }

    @Override
    public void saveDoctor(String day, String timeInterval, Doctor doctor) {
        
        database.getSchedule().computeIfAbsent(day, k -> new HashMap<>()).put(timeInterval, doctor);
    }

    @Override
    public Map<String, Map<String, Doctor>> getSchedule() {
        return database.getSchedule();
    }
    
    @Override
    public Doctor getDoctorById(int id) {
        return database.getDoctors().get(id);
    }
}
