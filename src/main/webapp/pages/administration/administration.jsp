<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set value="${sessionScope.get(\"user\")}" var="user" scope="page" />

<html>
<head>
    <title>Главная</title>
</head>
<style>
    <%@include file='admin_style.css' %>
</style>
<body>
<h1>Администрирование</h1>
<div class="index_div">
    <form action="project" method="get">
        <input type="hidden" name="command" value="manageInvoices" />
        <input type="hidden" name="type" value="all" />
        <button type="submit" class="giantbutton">Управление заказами</button>
    </form>
    <form action="project" method="get">
        <input type="hidden" name="command" value="manageUsers" />
        <input type="hidden" name="type" value="all" />
        <button type="submit" class="giantbutton">Управление пользователями</button>
    </form>
    <form action="project" method="get">
        <input type="hidden" name="command" value="manageProducts" />
        <button type="submit" class="giantbutton">Управление складом</button>
    </form>
    <form action="project" method="get">
        <input type="hidden" name="command" value="manageTransactions" />
        <input type="hidden" name="type" value="all" />
        <button type="submit" class="giantbutton">Транзакции</button>
    </form>
    <form action="project" method="get">
        <input type="hidden" name="command" value="main" />
        <button type="submit" class="giantbutton">Выход</button>
    </form>
</div>
<footer>
    <p class="footer">Учебный проект Java Winter, Киев, 2018</p>
</footer>
</body>
</html>
