<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Meals</title>
    <link href="main.css" rel="stylesheet" type="text/css">
</head>

<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table>
    <tr>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2">Action</th>
    </tr>
    <c:set var="count" value="0"/> <!-- counter for dynamic row number in UI -->
    <c:forEach items="${meals}" var="meal">
        <tr class="${meal.exceed ? "red" : "green"}">

            <td><c:out value="${fn:replace(meal.dateTime, 'T', ' ')}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>

            <td>
                <form action="${pageContext.request.contextPath}/meals" method="post">
                    <input class="button" type="submit" name="action" value="edit"/>
                    <input type="hidden" name="count" value="${count}"/>
                    <input type="hidden" name="dateTime" value="${meal.dateTime}"/>
                    <input type="hidden" name="description" value="${meal.description}"/>
                    <input type="hidden" name="calories" value="${meal.calories}"/>

                </form>
            </td>

            <td>
                <form action="${pageContext.request.contextPath}/meals" method="post">
                    <input class="button" type="submit" name="action" value="delete"/>
                    <input type="hidden" name="count" value="${count}"/>
                </form>
            </td>
        </tr>
        <c:set var="count" value="${count + 1}"/>
    </c:forEach>
</table>

<h3>Add meal</h3>

<table>
    <tr>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Action</th>
    </tr>
    <tr>
        <form action="${pageContext.request.contextPath}/meals" method="post">
            <td>
                <input type="date" name="date" id="date" value="2018-01-01"
                       pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"
                       placeholder="Enter a date in format yyyy-MM-dd" required/>
                <input type="time" name="time" id="time" value="00:00"
                       pattern="^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$" placeholder="Enter a time in format HH:mm"
                       required/>
            </td>
            <td>
                <input type="text" name="description" id="description" value="Sample Description" required/>
            </td>
            <td>
                <input type="number" min="0" name="calories" id="calories" value="0" required/>
            </td>
            <td>
                <input class="button" type="submit" name="action" value="add"/>
                <input type="hidden" name="count" value="${count}"/>
            </td>
        </form>
    </tr>
</table>
</body>
</html>