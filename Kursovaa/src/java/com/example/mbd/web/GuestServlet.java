package com.example.mbd.web;

import com.example.mbd.impl.inmemory.InMemoryDoctorDao;
import com.example.mbd.model.Doctor;
import com.example.mbd.model.User;
import com.example.mbd.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;


@WebServlet(name = "GuestServlet", urlPatterns = {"/guest"})
public class GuestServlet extends HttpServlet {

    

    private InMemoryDoctorDao doctorDao;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        doctorDao = (InMemoryDoctorDao) getServletContext().getAttribute("doctorDao");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);

        if (session == null) {
            
            session = request.getSession();
        }

        Map<String, Map<String, Doctor>> schedule = (Map<String, Map<String, Doctor>>) session.getAttribute("schedule");
        if (schedule == null) {
            
            schedule = doctorDao.getSchedule();
            session.setAttribute("schedule", schedule);
        }
        request.setAttribute("schedule", schedule);

        User user = (User) session.getAttribute("user");

        if (user != null && user.isAdmin()) {
            
            response.sendRedirect("admin?action=view");
        } 
        
        else if (user != null && user.isLogged()){
            response.sendRedirect("loggedguest?action=view");
        }
        
        else if (user == null) {
            
            request.getRequestDispatcher("/WEB-INF/jsp/guest.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        
        if ("login".equals(action)) {
            login(request, response);
        } else if ("logout".equals(action)) {
            logout(request, response);
        } else {
            doGet(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("login");
        String password = request.getParameter("password");
        

        User user = userService.authenticate(username, password);
        if (user != null) {
            
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            if (user.isAdmin()) {
                response.sendRedirect("admin");
            } else {
                response.sendRedirect("guest");
            }
        } else {
            
            request.setAttribute("errorMessage", "Invalid credentials");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("guest");
    }

    @Override
    public String getServletInfo() {
        return "Guest Servlet";
    }

    private void elif(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
