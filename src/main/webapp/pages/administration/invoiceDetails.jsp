<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <p>Код заказа:<br/>
            <b><i><c:out value="${invoice.invoiceCode}"/></i></b>
        </p>
        <p>Дата заказа:<br/>
            <b><i><c:out value="${invoice.date}"/></i></b>
        </p>
        <p>Имя пользователя:<br/>
            <b><i><c:out value="${invoice.userName}"/></i></b>
        </p>
        <p>Заказ оплачен:<br/>
            <c:if test="${invoice.paid}"><b><i><c:out value="Да"/></i></b></c:if>
            <c:if test="${!invoice.paid}"><b><i><c:out value="Нет"/></i></b></c:if>
        </p>
        <p>Примечания:<br/>
            <b><i><c:out value="${invoice.invoiceNotes}"/></i></b>
        </p>
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
                    <td class="tdc">${payment.value.paymentValue}</td>
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
