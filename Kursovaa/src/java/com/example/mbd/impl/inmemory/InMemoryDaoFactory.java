package com.example.mbd.impl.inmemory;

import com.example.mbd.dao.AppointmentDao;
import com.example.mbd.dao.DoctorDao;
import com.example.mbd.dao.UserDao;
import com.example.mbd.dao.DaoFactory;

public class InMemoryDaoFactory implements DaoFactory {
    private final InMemoryDataBase database;

    public InMemoryDaoFactory(InMemoryDataBase database) {
        this.database = database;
    }

    @Override
    public AppointmentDao getAppointmentDao() {
        return new InMemoryAppointmentDao(database);
    }

    @Override
    public UserDao getUserDao() {
        return new InMemoryUserDao(database);
    }

    @Override
    public DoctorDao getDoctorDao() {
        return new InMemoryDoctorDao(database);
    }
}
