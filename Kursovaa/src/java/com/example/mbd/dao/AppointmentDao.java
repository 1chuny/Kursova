/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.mbd.dao;

import com.example.mbd.model.Appointment;
import java.util.List;

public interface AppointmentDao extends AbstractDao<Appointment>{
    void addAppointment(Appointment appointment);
    Appointment getAppointment(int appointmentId);
    List<Appointment> getAllAppointments();
    void update(Appointment appointment);
    void delete(Appointment appointment);
    void deleteAppointment(int appointmentId);
}

