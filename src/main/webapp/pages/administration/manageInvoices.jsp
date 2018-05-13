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
<h1>Управление заказами</h1>
<div class="menu">
<form name="addProductForm" method="get" action="project" class="menuitem">
    <!-- SHALL BE ADDED TO COMMANDS -->
    <input type="hidden" name="command" value="showPayedInvoices" />
    <button type="submit" class="menubutton">Оплаченные заказы</button>
</form>
<form name="addProductForm" method="get" action="project" class="menuitem">
    <!-- SHALL BE ADDED TO COMMANDS -->
    <input type="hidden" name="command" value="showNewInvoices" />
    <button type="submit" class="menubutton">Новые заказы</button>
</form>
<form name="addProductForm" method="get" action="project" class="menuitem">
    <!-- SHALL BE ADDED TO COMMANDS -->
    <input type="hidden" name="command" value="showClosedInvoices" />
    <button type="submit" class="menubutton">Завершенные заказы</button>
</form>
<form name="addProductForm" method="get" action="project" class="menuitem">
    <!-- SHALL BE ADDED TO COMMANDS -->
    <input type="hidden" name="command" value="showCancelledInvoices" />
    <button type="submit" class="menubutton">Отмененные заказы</button>
</form>
</div>
<div>
</div>
<footer>
    <p class="footer">Учебный проект Java Winter, Киев, 2018</p>
</footer>
</body>
</html>