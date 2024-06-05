package com.example.mbd.web;

import com.example.mbd.dao.AppointmentDao;
import com.example.mbd.dao.DoctorDao;
import com.example.mbd.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;


@WebServlet(name = "LoggedGuestServlet", urlPatterns = {"/loggedGuest"})
public class LoggedGuestServlet extends HttpServlet {

    private DoctorDao doctorDao;
    private AppointmentDao appointmentDao;

    @Override
    public void init() throws ServletException {
        doctorDao = (DoctorDao) getServletContext().getAttribute("doctorDao");
        appointmentDao = (AppointmentDao) getServletContext().getAttribute("appointmentDao");
    }

    private void viewDoctors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Map<String, Doctor>> schedule = doctorDao.getSchedule();
        request.setAttribute("schedule", schedule);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/loggedGuest.jsp");
        dispatcher.forward(request, response);
    }

    private void makeAppointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("guest");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("guest");
            return;
        }

        int doctorId = Integer.parseInt(request.getParameter("doctorId"));
        String day = request.getParameter("day");
        String interval = request.getParameter("interval");
        String time = request.getParameter("time");
        Integer id = 1;
        String doctorname = request.getParameter("doctorname");

        Doctor doctor = doctorDao.getDoctorById(doctorId);
        if (doctor != null && user != null) {
            String appointmentText = String.format("Appointment with Doctor %s on %s during %s", doctor.getName(), day, interval);
            Appointment appointment = new Appointment(id, user, doctorname, time);
            appointmentDao.addAppointment(appointment);
            
        }

        response.sendRedirect("loggedGuest?action=view");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("guest");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("guest");
            return;
        }

        String action = request.getParameter("action");
        if ("view".equals(action)) {
            viewDoctors(request, response);
        } else {
            response.sendRedirect("guest");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("makeAppointment".equals(action)) {
            makeAppointment(request, response);
        } else {
            response.sendRedirect("guest");
        }
    }
}

