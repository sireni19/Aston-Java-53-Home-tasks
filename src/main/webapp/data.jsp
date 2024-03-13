<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 09.03.2024
  Time: 20:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Weather in your city</title>
</head>
<body>
<form method="get" action="/weather">
    <label>Город:</label>
    <input type="text" name="city">
    <input type="submit" value="Поиск"><br>
</form>
<% request.getAttribute("result"); %>
<c:out value="${result}" />
</body>
</html>
