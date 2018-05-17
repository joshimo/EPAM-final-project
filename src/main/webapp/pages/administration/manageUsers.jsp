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
<h1>Управление пользователями</h1>
<div class="widemenu">
    <p><c:out value="${user.name}, ${user.userRole}"/></p>
    <form name="addProductForm" method="post" action="project" class="menuitem">
        <input type="hidden" name="command" value="administration" />
        <button type="submit" class="menubutton">Назад</button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageUsers" />
        <input type="hidden" name="type" value="all" />
        <button type="submit" class="menubutton">Все пользователи</button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageUsers" />
        <input type="hidden" name="type" value="user" />
        <button type="submit" class="menubutton">Пользователи</button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageUsers" />
        <input type="hidden" name="type" value="cashier" />
        <button type="submit" class="menubutton">Кассиры</button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageUsers" />
        <input type="hidden" name="type" value="seniorCashier" />
        <button type="submit" class="menubutton">Старшие кассиры</button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageUsers" />
        <input type="hidden" name="type" value="merchant" />
        <button type="submit" class="menubutton">Товароведы</button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageUsers" />
        <input type="hidden" name="type" value="admin" />
        <button type="submit" class="menubutton">Администраторы</button>
    </form>
</div>
<div>
    <table class="widetable">
        <tr>
            <th style="width: 15%;">Имя пользователя</th>
            <th style="width: 10%;">Тип учетной записи</th>
            <th style="width: 15%;">Телефон</th>
            <th style="width: 15%;">e-mail</th>
            <th style="width: 25%;">Адрес</th>
            <th style="width: 20%;">Примечания</th>
            <th></th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td class="tdl"><c:out value="${user.name}"/></td>
                <td class="tdl"><c:out value="${user.userRole}"/></td>
                <td class="tdl"><c:out value="${user.phoneNumber}"/></td>
                <td class="tdl"><c:out value="${user.email}"/></td>
                <td class="tdl"><c:out value="${user.address}"/></td>
                <td class="tdl"><c:out value="${user.notes}"/></td>
                <td class="tdc">
                    <form name="details" method="post" action="project" >
                        <input type="hidden" name="command" value="editUserByAdmin" />
                        <input type="hidden" name="id" value="${user.id}" />
                        <!-- SHALL BE HANDLED BY ADDING CommandEditUserByAdmin() -->
                        <button type="submit" class="smallbutton">Подробно</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<footer>
    <p class="footer">Учебный проект по курсу Java Winter, Киев, 2018</p>
</footer>
</body>
</html>