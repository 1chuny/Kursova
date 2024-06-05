package com.example.mbd.web;

import com.example.mbd.dao.DaoFactory;
import com.example.mbd.impl.inmemory.InMemoryDaoFactory;
import com.example.mbd.impl.inmemory.InMemoryDataBase;
import com.example.mbd.impl.inmemory.InMemoryTestData;

import com.example.mbd.services.UserService;
import com.example.mbd.services.UserServiceImpl;
import com.example.mbd.dao.DoctorDao;
import com.example.mbd.dao.UserDao;
import com.example.mbd.dao.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.function.UnaryOperator;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Инициализация базы данных
        InMemoryDataBase database = new InMemoryDataBase();
        InMemoryTestData.generateTo(database);

        // Сохраняем базу данных в контексте
        sce.getServletContext().setAttribute("database", database);

        // Создание фабрики DAO
        DaoFactory daoFactory = new InMemoryDaoFactory(database);

        // Инициализация сервисов
        

        // Используем DaoFactory в конструкторе UserServiceImpl
        UserDao userDao = daoFactory.getUserDao();
        UserService userService = new UserServiceImpl(userDao, UnaryOperator.identity());
        sce.getServletContext().setAttribute("userService", userService);

        DoctorDao doctorDao = daoFactory.getDoctorDao();
        sce.getServletContext().setAttribute("doctorDao", doctorDao);
        
        AppointmentDao appointmentDao = daoFactory.getAppointmentDao();
        sce.getServletContext().setAttribute("appointmentDao", appointmentDao); 
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Очистка ресурсов, если необходимо
    }
}