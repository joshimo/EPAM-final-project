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
        <input type="hidden" name="command" value="manageInvoices" />
        <!-- SHALL BE HANDLED IN COMMAND CommandOpenInvoiceMngPage() -->
        <input type="hidden" name="type" value="payed" />
        <button type="submit" class="menubutton">Оплаченные заказы</button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageInvoices" />
        <!-- SHALL BE HANDLED IN COMMAND CommandOpenInvoiceMngPage() -->
        <input type="hidden" name="type" value="new" />
        <button type="submit" class="menubutton">Новые заказы</button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageInvoices" />
        <!-- SHALL BE HANDLED IN COMMAND CommandOpenInvoiceMngPage() -->
        <input type="hidden" name="type" value="closed" />
        <button type="submit" class="menubutton">Завершенные заказы</button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageInvoices" />
        <!-- SHALL BE HANDLED IN COMMAND CommandOpenInvoiceMngPage() -->
        <input type="hidden" name="type" value="cancelled" />
        <button type="submit" class="menubutton">Отмененные заказы</button>
    </form>
    <form name="addProductForm" method="post" action="project" class="menuitem">
        <input type="hidden" name="command" value="administration" />
        <button type="submit" class="menubutton">Назад</button>
    </form>
</div>
<div>
    <table>
        <tr>
            <th style="width: 10%;">Код заказа</th>
            <th style="width: 15%;">Дата заказа</th>
            <th style="width: 15%;">Имя пользоватля</th>
            <th style="width: 10%;">Оплачено</th>
            <th style="width: 10%;">Статус</th>
            <th style="width: 25%;">Примечания</th>
            <th style="width: 15%;"></th>
        </tr>
        <c:forEach items="${invoices}" var="invoice">
            <tr>
                <td class="tdc"><c:out value="${invoice.invoiceCode}"/></td>
                <td class="tdc"><c:out value="${invoice.date}"/></td>
                <td class="tdc"><c:out value="${invoice.userName}"/></td>
                <td class="tdc">
                    <form>
                        <c:if test="${invoice.paid}"><input type="checkbox" checked disabled/></c:if>
                        <c:if test="${!invoice.paid}"><input type="checkbox" unchecked disabled/></c:if>
                    </form>
                </td>
                <td class="tdc"><c:out value="${invoice.status}"/></td>
                <td class="tdl"><c:out value="${invoice.invoiceNotes}"/></td>
                <td class="tdc">
                    <form>
                        <form name="details" method="post" action="project" >
                            <input type="hidden" name="command" value="showInvoiceDetails" />
                            <input type="hidden" name="code" value="${invoice.invoiceCode}" />
                            <button type="submit" class="smallbutton">Подробно</button>
                        </form>
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