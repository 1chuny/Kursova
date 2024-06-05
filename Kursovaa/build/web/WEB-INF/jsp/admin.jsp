<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.mbd.model.User" %>
<%@ page import="com.example.mbd.model.Doctor" %>
<%@ page import="com.example.mbd.model.Appointment" %>
<%@ page import="com.example.mbd.impl.inmemory.InMemoryTestData" %>
<%@ page import="com.example.mbd.impl.inmemory.InMemoryDataBase" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Админ - Список лікарів</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h2>Админ - Список лікарів</h2>
    <form action="admin?action=save" method="post">
        <table>
            <tr>
                <th>Інтервали часу</th>
                <% 
                    String[] daysOfWeek = {"Понеділок", "Вівторок", "Середа", "Четвер", "П'ятниця", "Субота", "Неділя"};
                    for (String day : daysOfWeek) {
                %>
                <th><%= day %></th>
                <% 
                    }
                %>
            </tr>
            <% 
                String[] timeIntervals = {"9:00-12:00", "12:00-15:00", "15:00-18:00"};
                
                InMemoryDataBase database = new InMemoryDataBase();
                InMemoryTestData.generateTo(database);
                Map<String, Map<String, Doctor>> schedule = database.getSchedule();
                
                for (String interval : timeIntervals) {
            %>
            <tr>
                <td><%= interval %></td>
                <% 
                    for (String day : daysOfWeek) { 
                %>
                <td>
                    <%
                        Doctor doctor = schedule.getOrDefault(day, new HashMap<>()).get(interval);
                        String name = doctor != null ? doctor.getName() : "";
                        String specialization = doctor != null ? doctor.getSpecialization() : "";
                    %>
                    <input type="text" name="doctor[<%= day %>][<%= interval %>][name]" value="<%= name %>" placeholder="Имя"><br>
                    <input type="text" name="doctor[<%= day %>][<%= interval %>][specialization]" value="<%= specialization %>" placeholder="Специализация">
                </td>
                <% 
                    } 
                %>
            </tr>
            <% 
                } 
            %>
        </table>

        <h2>Admin - Записи</h2>
        <table>
            <tr>
                <th>Appointment ID</th>
                <th>Guest</th>
                <th>Doctor</th>
                <th>Time</th>
            </tr>
            <%
                Map<Integer, Appointment> appointmentsMap = database.getAppointments();
                List<Appointment> appointments = new ArrayList<>(appointmentsMap.values());
                for (Appointment appointment : appointments) {
            %>
            <tr>
                <td><%= appointment.getAppointmentId() %></td>
                <td><%= appointment.getUser().getName() %></td>
                <td><%= appointment.getDoctor() %></td>
                <td><%= appointment.getTime() %></td>
            </tr>
            <%
                }
            %>
        </table>

        <input type="submit" value="Зберегти">
    </form>
    
    <header>
        <c:if test="${!empty user}">
            <form class="login-form" action="guest?action=logout" method="POST">            
                <c:out value="${user.name}"/><br>
                <input type="submit" value="Log Out" />
            </form>
        </c:if>
        <c:if test="${empty user}">
            <form class="login-form" action="guest?action=login" method="POST">
                <input type="text" name="login" value="" />
                <input type="password" name="password" value="" />
                <input type="submit" value="Log In" />
            </form>
        </c:if>
    </header>
</body>
</html>
