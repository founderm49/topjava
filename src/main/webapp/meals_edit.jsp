<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <link href="main.css" rel="stylesheet" type="text/css">
</head>

<body>
<h3><a href="index.html">Home</a></h3>
<h2>Edit Meal</h2>
<table>
    <tr>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2">Action</th>
    </tr>
    <c:set var="count" value="${count}"/>
    <form action="${pageContext.request.contextPath}/meals" method="post">
        <td>
            <input type="date" name="date" id="date" value="${date}"
                   pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"
                   placeholder="Enter a date in format yyyy-MM-dd" required/>
            <input type="time" name="time" id="time" value="${time}" pattern="^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"
                   placeholder="Enter a time in format HH:mm" required/>
        </td>
        <td>
            <input type="text" name="description" id="description" value="${description}" required/>
        </td>
        <td>
            <input type="number" min="0" name="calories" id="calories" value="${calories}" required/>
        </td>
        <td>
            <input class="button" type="submit" name="action" value="save"/>
            <input type="hidden" name="count" value="${count}"/>
        </td>
    </form>
</table>
</body>
</html>
