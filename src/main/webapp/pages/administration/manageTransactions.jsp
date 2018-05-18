<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
<div class="widemenu">
    <p><c:out value="${user.name}, ${user.userRole}"/></p>
    <form name="addProductForm" method="post" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageTransactions" />
        <input type="hidden" name="type" value="payment" />
        <button type="submit" class="menubutton">Оплаты</button>
    </form>
    <form name="addProductForm" method="post" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageTransactions" />
        <input type="hidden" name="type" value="refund" />
        <button type="submit" class="menubutton">Возвраты</button>
    </form>
    <form name="addProductForm" method="post" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageTransactions" />
        <input type="hidden" name="type" value="all" />
        <button type="submit" class="menubutton">Все</button>
    </form>
    <form name="addProductForm" method="post" action="project" class="menuitem">
        <input type="hidden" name="command" value="administration" />
        <button type="submit" class="menubutton">Назад</button>
    </form>
</div>
<div>
    <table class="widetable">
        <tr>
            <th style="width: 15%;">Код заказа</th>
            <th style="width: 15%;">Имя пользователя</th>
            <th style="width: 15%;">Тип транзакции</th>
            <th style="width: 10%;">Сумма</th>
            <th style="width: 20%;">Дата</th>
            <th style="width: 25%;">Примечания</th>
        </tr>
        <c:forEach items="${transactions}" var="transaction">
            <tr>
                <td class="tdc"><c:out value="${transaction.invoiceCode}"/></td>
                <td class="tdl"><c:out value="${transaction.userName}"/></td>
                <c:if test="${transaction.transactionType == 'PAYMENT'}">
                    <td class="tdl" style="color: green">Оплата</td>
                </c:if>
                <c:if test="${transaction.transactionType != 'PAYMENT'}">
                    <td class="tdl" style="color: darkred">Возврат средств</td>
                </c:if>
                <td class="tdc">
                    <fmt:formatNumber value="${transaction.paymentValue}" maxFractionDigits="2" minFractionDigits="2"/>
                </td>
                <td class="tdc"><fmt:formatDate type="both" value="${transaction.time}" /></td>
                <td class="tdc"><c:out value="${transaction.notes}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
<footer>
    <p class="footer">Учебный проект по курсу Java Winter, Киев, 2018</p>
</footer>
</body>
</html>