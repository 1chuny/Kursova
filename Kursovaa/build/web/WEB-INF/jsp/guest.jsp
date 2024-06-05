<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.mbd.model.Doctor" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Список врачей</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <header>
        <c:if test="${!empty user}">
            <form class="login-form" action="guest?action=logout" method="POST">
                <c:out value="${user.name}"/><br>
                <input type="submit" value="Log Out" />
            </form>
        </c:if>
        <c:if test="${empty user}">
            <form class="login-form" action="guest?action=login" method="POST">
                <input type="text" name="login" value="" placeholder="Username" required />
                <input type="password" name="password" value="" placeholder="Password" required />
                <input type="submit" value="Log In" />
            </form>
        </c:if>
    </header>

    <h2>Список лікарів</h2>
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
            Map<String, Map<String, Doctor>> schedule = (Map<String, Map<String, Doctor>>) request.getAttribute("schedule");
            for (String interval : timeIntervals) {
        %>
        <tr>
            <td><%= interval %></td>
            <% for (String day : daysOfWeek) { %>
            <td>
                <%
                    Doctor doctor = schedule.getOrDefault(day, new HashMap<>()).get(interval);
                    String name = doctor != null ? doctor.getName() : "Немає даних";
                    String specialization = doctor != null ? doctor.getSpecialization() : "";
                %>
                <%= name %> <br> <%= specialization %>
            </td>
            <% } %>
        </tr>
        <% } %>
        
    </table>
    
</body>
</html>
