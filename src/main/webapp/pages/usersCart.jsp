<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<style>
    <%@include file='main_style.css' %>
</style>
<body>
<h1>Корзина</h1>
<div class="narrowmenu">
    <p><c:out value="${sessionScope.get(\"user\").name}" /></p>
    <form name="remove" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="main" />
        <button type="submit" class="menubutton">Назад</button>
    </form>
</div>
<div>
    <table class="narrowtable">
        <tr>
            <th style="width: 20%;">Артикул</th>
            <th style="width: 20%;">Количество</th>
            <th style="width: 20%;">Цена</th>
            <th style="width: 40%;"></th>
        </tr>
        <c:forEach items="${sessionScope.get(\"cart\").products}" var="product">
        <tr>
            <td class="tdc">${product.key}</td>
            <td class="tdc">${product.value}</td>
            <td class="tdc"></td>
            <td class="tdc">
                <form name="remove" method="get" action="project" >
                    <input type="hidden" name="command" value="removeProductFromCart" />
                    <button type="submit" name="productCode" value="${product.key}" class="menu-button">Удалить</button>
                </form>
            </td>
        </tr>
        </c:forEach>
    </table>
</div>
    <div class="button_div">
        <form name="remove" method="get" action="project" >
            <input type="hidden" name="command" value="createInvoice" />
            <button type="submit" class="bigbutton">Оформить заказ</button>
        </form>
        <form name="remove" method="get" action="project" >
            <input type="hidden" name="command" value="createInvoiceAndPay" />
            <button type="submit" class="bigbutton">Оплатить заказ</button>
        </form>
    </div>
<footer>
    <p class="footer">Учебный проект по курсу Java Winter, Киев, 2018</p>
</footer>
</body>
</html>
