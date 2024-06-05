package com.example.mbd.dao;

import com.example.mbd.model.Doctor;

import java.util.Map;

public interface DoctorDao extends AbstractDao<Doctor>{
    void saveDoctor(String day, String timeInterval, Doctor doctor);
    Doctor getDoctor(String day, String timeInterval);
    Map<String, Map<String, Doctor>> getSchedule();
    Doctor getDoctorById(int id);
}
