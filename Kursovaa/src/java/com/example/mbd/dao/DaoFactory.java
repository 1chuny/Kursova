/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.mbd.dao;


public interface DaoFactory {
    
    AppointmentDao getAppointmentDao();

    UserDao getUserDao();
    
    DoctorDao getDoctorDao();
}
