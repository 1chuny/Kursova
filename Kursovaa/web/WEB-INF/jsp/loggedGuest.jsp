<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.mbd.model.Doctor" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Guest - Doctor Schedule</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            text-align: center;
        }
        th, td {
            padding: 10px;
        }
        #popup {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: white;
            border: 1px solid black;
            padding: 20px;
        }
        #successMessage {
            color: green;
            font-weight: bold;
            display: none;
        }
    </style>
    <script>
        function showTimeSlots(doctorId, day, interval) {
            document.getElementById("popup").style.display = "block";
            document.getElementById("doctorId").value = doctorId;
            document.getElementById("day").value = day;
            document.getElementById("interval").value = interval;
        }

        function closePopup() {
            document.getElementById("popup").style.display = "none";
        }

        function makeAppointment() {
            document.getElementById("appointmentButton").innerText = "Запис зроблено";
            document.getElementById("successMessage").style.display = "block";
            closePopup();
        }
    </script>
</head>
<body>
    <h2>Guest - Doctor Schedule</h2>
    <table>
        <tr>
            <th>Time Intervals</th>
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
            <% 
                for (String day : daysOfWeek) { 
            %>
            <td>
                <%
                    Doctor doctor = schedule.getOrDefault(day, new HashMap<>()).get(interval);
                    if (doctor != null) {
                %>
                    <button id="appointmentButton" onclick="showTimeSlots(<%= doctor.getId() %>, '<%= day %>', '<%= interval %>')">
                        <%= doctor.getName() %><br>
                        <%= doctor.getSpecialization() %><br>
                        Зробити запис
                    </button>
                <%
                    }
                %>
            </td>
            <% 
                } 
            %>
        </tr>
        <% 
            } 
        %>
    </table>

    <!-- Popup form -->
    <div id="popup">
        <h3>Select Time Slot</h3>
        <form id="appointmentForm" action="loggedGuest?action=makeAppointment" method="post" onsubmit="event.preventDefault(); makeAppointment();">
            <input type="hidden" id="doctorId" name="doctorId" value="">
            <input type="hidden" id="day" name="day" value="">
            <input type="hidden" id="interval" name="interval" value="">
            <select name="time">
                <option value="9:00">9:00</option>
                <option value="10:00">10:00</option>
                <option value="11:00">11:00</option>
                <option value="12:00">12:00</option>
            </select><br><br>
            <input type="submit" value="Зробити запис">
            <button type="button" onclick="closePopup()">Cancel</button>
        </form>
    </div>

    <div id="successMessage">Запис зроблено</div>

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
