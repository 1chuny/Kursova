package com.example.mbd.web;

import com.example.mbd.impl.inmemory.InMemoryDoctorDao;
import com.example.mbd.model.Doctor;
import com.example.mbd.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;


@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    

    private InMemoryDoctorDao doctorDao;
    

    @Override
    public void init() throws ServletException {
        doctorDao = (InMemoryDoctorDao) getServletContext().getAttribute("doctorDao");
    }

    private void viewDoctors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Map<String, Doctor>> schedule = doctorDao.getSchedule();
        request.setAttribute("schedule", schedule);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
        dispatcher.forward(request, response);
    }

    private void saveDoctors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Map<String, String[]> parameters = request.getParameterMap();

    

    String[] days = {"Понеділок", "Вівторок", "Середа", "Четвер", "П'ятниця", "Субота", "Неділя"};
    String[] intervals = {"9:00-12:00", "12:00-15:00", "15:00-18:00"};

    try {
        for (String day : days) {
            for (String interval : intervals) {
                String nameKey = String.format("doctor[%s][%s][name]", day, interval);
                String specializationKey = String.format("doctor[%s][%s][specialization]", day, interval);

                String name = request.getParameter(nameKey);
                String specialization = request.getParameter(specializationKey);

                

                if (name != null && !name.trim().isEmpty() && specialization != null && !specialization.trim().isEmpty()) {
                    Doctor existingDoctor = doctorDao.getDoctor(day, interval);

                    int id;
                    if (existingDoctor != null) {
                        id = existingDoctor.getId(); // Keep the existing ID if the doctor already exists
                    } else {
                        id = generateNewDoctorId();
                    }
                    Doctor doctor = new Doctor(id, name, specialization);
                    
                    doctorDao.saveDoctor(day, interval, doctor);
                } 
            }
        }
        response.sendRedirect("admin?action=view");
    } catch (Exception e) {
        
        request.setAttribute("errorMessage", "An error occurred while saving the doctors.");
        viewDoctors(request, response);
    }
}

private int generateNewDoctorId() {
    // Implement a logic to generate a new ID. This is a simple example using current time in milliseconds.
    return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("guest");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
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
        if ("save".equals(action)) {
            saveDoctors(request, response);
        } else {
            response.sendRedirect("guest");
        }
    }
}
