<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ошибка</title>
</head>
<style>
    <%@include file='main_style.css' %>
</style>
<body>
<h1>Авторизация</h1>
<div class = index_div>
    <form action="<c:url value="/project"/>" method="post">
        <h2>Ошибка безопасности: у вас нет прав доступа на эту страницу!</h2>
        <h2>Security error: You are not permitted to visit this page!</h2>
        <input type="submit" class="bigbutton" value="На главную страницу"/>
    </form>
</div>
</body>
</html>