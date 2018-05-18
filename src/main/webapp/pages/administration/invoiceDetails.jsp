<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Новый пользователь</title>
</head>
<style>
    <%@include file='admin_style.css' %>
</style>
<body>
<h1>Заказ</h1>
<div class="widemenu">
    <p><c:out value="${user.name}, ${user.userRole}"/></p>
    <form name="addProductForm" method="post" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageInvoices" />
        <input type="hidden" name="type" value="all" />
        <button type="submit" class="menubutton">Назад</button>
    </form>
    <form name="addProductForm" method="post" action="project" class="menuitem">
        <input type="hidden" name="command" value="administration" />
        <button type="submit" class="menubutton">Администрирование</button>
    </form>
</div>
<div class="inner_div">
    <h3>Реквизиты:</h3>
    <ul>
        <li>Код заказа: <b><i><c:out value="${invoice.invoiceCode}"/></i></b></li>
        <li>Имя клиента: <b><i><c:out value="${invoice.userName}"/></i></b></li>
        <li>Дата заказа: <b><i><fmt:formatDate type="both" value="${invoice.date}" /></i></b></li>
        <li>К оплате:
            <b><i><fmt:formatNumber value="${invoice.cost}" maxFractionDigits="2" minFractionDigits="2"/></i></b>
        </li>
        <li>Заказ оплачен:
            <c:if test="${invoice.paid}">
                <b><i><c:out value="Да"/></i></b>
            </c:if>
            <c:if test="${!invoice.paid}">
                <b><i><c:out value="Нет"/></i></b>
            </c:if>
        </li>
        <li>Статус заказа:
            <c:if test="${invoice.status == 'CREATED'}">
                <b><i><c:out value="Новый"/></i></b>
            </c:if>
            <c:if test="${invoice.status == 'FINISHED'}">
                <b><i><c:out value="Закрыт"/></i></b>
            </c:if>
            <c:if test="${invoice.status == 'CANCELLED'}">
                <b><i><c:out value="Отменен"/></i></b>
            </c:if>
        </li>
        <li>Примечания: <b><i><c:out value="${invoice.invoiceNotes}"/></i></b></li>
    </ul>
    <h3>Состав заказа:</h3>
    <table class="widetable">
        <tr>
            <th class="tdc">Артикул</th>
            <th class="tdc">Количество</th>
            <th class="tdc">Стоимость</th>
            <th class="tdc">Примечания</th>
            <th></th>
        </tr>
        <c:forEach items="${invoice.payments}" var="payment">
            <tr>
                <td class="tdl">${payment.key}</td>
                <td class="tdl">${payment.value.quantity}</td>
                <td class="tdc">
                    <fmt:formatNumber value="${payment.value.paymentValue}" maxFractionDigits="2" minFractionDigits="2"/>
                </td>
                <td class="tdl">${payment.value.paymentNotes}</td>
                <td class="tdc">
                    <form name="remove" method="get" action="project" >
                        <input type="hidden" name="command" value="removeProductFromInvoice" />
                        <input type="hidden" name="invCode" value="${payment.value.orderCode}" />
                        <input type="hidden" name="productCode" value="${payment.key}" />
                        <c:if test="${!invoice.paid && invoice.status == 'CREATED'}">
                            <button type="submit" class="smallbutton">Удалить</button>
                        </c:if>
                        <c:if test="${invoice.paid || invoice.status != 'CREATED'}">
                            <button type="submit" class="smallbutton" disabled>Удалить</button>
                        </c:if>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${!invoice.paid && invoice.status == 'CREATED'}">
        <form name="remove" method="get" action="project" >
            <tr>
                <td class="tdl"><input class="input" type="text" name="productCode" size="8" required /></td>
                <td class="tdl"><input class="input" type="text" name="quantity" size="8" required /></td>
                <td class="tdc"><input class="input" type="text" name="paymentValue" size="12" required /></td>
                <td class="tdl"><input class="input" type="text" name="paymentNotes" size="24" /></td>
                <td class="tdc">
                    <input type="hidden" name="command" value="addNewPayment" />
                    <input type="hidden" name="orderCode" value="${invoice.invoiceCode}" />
                    <button type="submit" class="smallbutton">Добавить в заказ</button>
                </td>
            </tr>
        </form>
        </c:if>
    </table>
    <div class="button_div">
        <form action="project" method="get">
            <input type="hidden" name="command" value="cancelInvoice" />
            <input type="hidden" name="invCode" value="${invoice.invoiceCode}" />
            <c:if test="${invoice.status != 'CREATED'}">
                <button type="submit" class="bigbutton" disabled>Отменить заказ</button>
            </c:if>
            <c:if test="${invoice.status == 'CREATED'}">
                <button type="submit" class="bigbutton">Отменить заказ</button>
            </c:if>
        </form>
        <form action="project" method="get">
            <input type="hidden" name="command" value="closeInvoice" />
            <input type="hidden" name="invCode" value="${invoice.invoiceCode}" />
            <c:if test="${invoice.paid && invoice.status == 'CREATED'}">
                <button type="submit" class="bigbutton">Закрыть заказ</button>
            </c:if>
            <c:if test="${!(invoice.paid && invoice.status == 'CREATED')}">
                <button type="submit" class="bigbutton" disabled>Закрыть заказ</button>
            </c:if>
        </form>
    </div>
</div>
<footer>
    <p class="footer">Учебный проект по курсу Java Winter, Киев, 2018</p>
</footer>
</body>
</html>
