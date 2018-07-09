<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <section>
        <h3>Filter by date/time</h3>
        <form method="post" action="meals?action=filter">
            <table border="1" cellpadding="8" cellspacing="0">
                <tr>
                    <td><label for="startDate">Start Date:</label></td>
                    <td><input type=date name="startDate" id="startDate"></td>

                    <td><label for="endDate">End Date:</label></td>
                    <td><input type=date name="endDate" id="endDate"></td>
                </tr>
                <tr>
                    <td><label for="startTime">Start Time:</label></td>
                    <td><input type=time name="startTime" id="startTime"></td>

                    <td><label for="endTime">End Time:</label></td>
                    <td><input type=time name="endTime" id="endTime"></td>
                </tr>
            </table>
            <br/>
            <button type="submit">Filter</button>
        </form>
    </section>
    <section>
        <h3>Meals List</h3>
        <a href="meals?action=create">Add Meal</a>
        <br/>
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
                <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                    <td>
                            <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                            <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                            <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                            ${fn:formatDateTime(meal.dateTime)}
                    </td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                    <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </section>
</section>
</body>
</html>