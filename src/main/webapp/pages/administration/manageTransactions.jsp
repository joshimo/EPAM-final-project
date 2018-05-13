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
<h1>Транзакции</h1>
<div class="menu">
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <!-- SHALL BE ADDED TO COMMANDS -->
        <input type="hidden" name="command" value="showPaymentTransactions" />
        <button type="submit" class="menubutton">Оплаты</button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <!-- SHALL BE ADDED TO COMMANDS -->
        <input type="hidden" name="command" value="showRefundedTransactions" />
        <button type="submit" class="menubutton">Возвраты</button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <!-- SHALL BE ADDED TO COMMANDS -->
        <input type="hidden" name="command" value="showAllTransactions" />
        <button type="submit" class="menubutton">Все</button>
    </form>
</div>
<div>
    <table>
        <tr>
            <th style="width: 5%;">Код заказа</th>
            <th style="width: 5%;">Имя пользователя</th>
            <th style="width: 5%;">Тип транзакции</th>
            <th style="width: 5%;">Сумма</th>
            <th style="width: 5%;">Дата</th>
            <th style="width: 5%;">Примечания</th>
        </tr>
        <c:forEach items="${transactions}" var="transaction">
            <tr>
                <td class="tdc"><c:out value="${transaction.invoiceCode}"/></td>
                <td class="tdl"><c:out value="${transaction.userName}"/></td>
                <td class="tdl"><c:out value="${transaction.transactionType}"/></td>
                <td class="tdl"><c:out value="${transaction.paymentValue}"/></td>
                <td class="tdc"><c:out value="${transaction.time}"/></td>
                <td class="tdc"><c:out value="${transaction.notes}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
<footer>
    <p class="footer">Учебный проект Java Winter, Киев, 2018</p>
</footer>
</body>
</html>