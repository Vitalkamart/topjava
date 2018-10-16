<%--
  Created by IntelliJ IDEA.
  User: Mart
  Date: 10.10.2018
  Time: 22:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .normal {color: green}
        .exceeded {color: red}
    </style>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals:</h2>
    <hr>
    <a href="meals?action=create">Add meal</a>
    <hr>
    <table cellpadding="1" cellspacing="1">
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceed' : 'normal'}">
                <td>
                    <%=  TimeUtil.toString(meal.getDateTime())%>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a> </td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a> </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
