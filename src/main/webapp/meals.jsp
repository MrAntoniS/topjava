<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <jsp:useBean id="meals" scope="request" type="java.util.List"/>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr style="${meal.excess==true ? 'color: red' : 'color: green'}">
                <td><a>${meal.formattedDateTime}</a></td>
                <td><a>${meal.description}</a></td>
                <td><a>${meal.calories}</a></td>
                <td><a>Update</a></td>
                <td><a>Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>